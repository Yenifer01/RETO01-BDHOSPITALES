import { Component, OnInit } from '@angular/core';
import {
  ActualizarHospital,
  Condicion,
  Distrito,
  Gerente,
  ListaHospital,
  RegistroHospital,
  Sede,
} from '../../../models/modelos';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { HospitalService } from '../../../services/hospital.service';
import { DistritoService } from '../../../services/distrito.service';
import { SedeService } from '../../../services/sede.service';
import { GerenteService } from '../../../services/gerente.service';
import { CondicionService } from '../../../services/condicion.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import Swal from 'sweetalert2';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from "../../navbar/navbar.component";

@Component({
  selector: 'app-editar-hospitales',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule, NavbarComponent,RouterLink],
  templateUrl: './editar-hospitales.component.html',
  styleUrl: './editar-hospitales.component.css',
})
export class EditarHospitalesComponent implements OnInit {
  hospitalForm!: FormGroup;
  distritos: Distrito[] = [];
  sedes: Sede[] = [];
  gerentes: Gerente[] = [];
  condiciones: Condicion[] = [];
  hospital: ListaHospital | undefined;
  originalHospital: ListaHospital | undefined;

  constructor(
    private fb: FormBuilder,
    private hospitalService: HospitalService,
    private distritoService: DistritoService,
    private sedeService: SedeService,
    private gerenteService: GerenteService,
    private condicionService: CondicionService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.inicializarFormulario();
    this.cargarDatosIniciales();
    this.obtenerHospital();
  }
  inicializarFormulario(): void {
    this.hospitalForm = this.fb.group({
      nombre: ['', Validators.required],
      antiguedad: [null, [Validators.required, Validators.min(1)]],
      area: [null, [Validators.required, Validators.min(1)]],
      idDistrito: [null, Validators.required],
      idSede: [null, Validators.required],
      idGerente: [null, Validators.required],
      idCondicion: [null, Validators.required],
    });
  }

  cargarDatosIniciales(): void {
    this.loadDistritos();
    this.loadSedes();
    this.loadGerentes();
    this.loadCondiciones();
  }

  loadDistritos(): void {
    this.distritoService.obtenerTodos().subscribe(
      (data: Distrito[]) => (this.distritos = data),
      (error) => console.error('Error al cargar distritos:', error)
    );
  }

  loadSedes(): void {
    this.sedeService.obtenerTodos().subscribe(
      (data: Sede[]) => (this.sedes = data),
      (error) => console.error('Error al cargar sedes:', error)
    );
  }

  loadGerentes(): void {
    this.gerenteService.obtenerTodos().subscribe(
      (data: Gerente[]) => (this.gerentes = data),
      (error) => console.error('Error al cargar gerentes:', error)
    );
  }

  loadCondiciones(): void {
    this.condicionService.obtenerTodos().subscribe(
      (data: Condicion[]) => (this.condiciones = data),
      (error) => console.error('Error al cargar condiciones:', error)
    );
  }

  obtenerHospital(): void {
    const id = Number(this.route.snapshot.params['id']);
    console.log('ID capturado de la URL:', id);

    if (isNaN(id)) {
      Swal.fire('Error', 'El ID del hospital no es válido.', 'error');
      return;
    }

    this.hospitalService.listarHospitalPorId(id).subscribe(
      (data: ListaHospital) => {
        console.log('Datos recibidos del hospital:', data);
        this.hospital = { ...data };
        this.originalHospital = { ...data };

        if (
          this.distritos.length &&
          this.sedes.length &&
          this.gerentes.length &&
          this.condiciones.length
        ) {
          this.cargarDatosEnFormulario();
        } else {
          setTimeout(() => this.cargarDatosEnFormulario(), 5);
        }
      },
      (error) => {
        console.error('Error al obtener el hospital:', error);
        Swal.fire('Error', 'No se pudo cargar el hospital.', 'error');
      }
    );
  }

  cargarDatosEnFormulario(): void {
    if (!this.hospital) return;

    this.hospitalForm.patchValue({
      nombre: this.hospital?.nombre,
      antiguedad: this.hospital?.antiguedad,
      area: this.hospital?.area,
      idDistrito:
        this.distritos.find(
          (d) => d.descdistrito === this.hospital?.descDistrito
        )?.iddistrito || null,
      idSede:
        this.sedes.find((s) => s.descsede === this.hospital?.descSede)
          ?.idsede || null,
      idGerente:
        this.gerentes.find((g) => g.descgerente === this.hospital?.descGerente)
          ?.idgerente || null,
      idCondicion:
        this.condiciones.find(
          (c) => c.desccondicion === this.hospital?.descCondicion
        )?.idcondicion || null,
    });

    console.log(
      'Datos iniciales cargados en el formulario:',
      this.hospitalForm.value
    );
    console.log('Datos originales del hospital:', this.originalHospital);
  }

  hayCambios(): boolean {
    if (!this.originalHospital) return false;
    const formularioActual = this.hospitalForm.getRawValue();
    const hospitalOriginal = {
      nombre: this.originalHospital.nombre,
      antiguedad: this.originalHospital.antiguedad,
      area: this.originalHospital.area,
      idDistrito:
        this.distritos.find(
          (d) => d.descdistrito === this.originalHospital?.descDistrito
        )?.iddistrito || null,
      idSede:
        this.sedes.find((s) => s.descsede === this.originalHospital?.descSede)
          ?.idsede || null,
      idGerente:
        this.gerentes.find(
          (g) => g.descgerente === this.originalHospital?.descGerente
        )?.idgerente || null,
      idCondicion:
        this.condiciones.find(
          (c) => c.desccondicion === this.originalHospital?.descCondicion
        )?.idcondicion || null,
    };
    return Object.keys(hospitalOriginal).some((key) => {
      const valorOriginal = String(
        hospitalOriginal[key as keyof typeof hospitalOriginal] || ''
      ).trim();
      const valorActual = String(
        formularioActual[key as keyof typeof formularioActual] || ''
      ).trim();
      return valorOriginal !== valorActual;
    });
  }

  guardarCambios(): void {
    if (!this.hayCambios()) {
      Swal.fire({
        title: 'No se han realizado cambios',
        text: 'No hay cambios que guardar.',
        icon: 'info',
        confirmButtonText: 'OK',
      });
      return;
    }

    const hospitalData = this.hospitalForm.getRawValue();
    if (
      hospitalData.antiguedad === null ||
      hospitalData.antiguedad === undefined ||
      hospitalData.antiguedad <= 0 ||
      hospitalData.area === null ||
      hospitalData.area === undefined ||
      hospitalData.area <= 0
    ) {
      Swal.fire({
        icon: 'warning',
        title: 'Validación',
        text: 'La antigüedad y el área deben ser mayores que 0.',
      });
      return;
    }

    if (this.hospitalForm.invalid) {
      Swal.fire({
        icon: 'warning',
        title: 'Validación',
        text: 'Por favor, completa todos los campos obligatorios.',
      });
      return;
    }

    if (!this.hospital || this.hospital.idHospital === undefined) {
      console.log('No se puede actualizar porque el ID es undefined.');
      return;
    }

    Swal.fire({
      title: '¿Estás seguro?',
      text: '¿Deseas actualizar los cambios?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#007bff',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, actualizar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        const idHospital = this.hospital!.idHospital;
        const hospitalActualizado = this.mapearFormularioAActualizarHospital();

        this.hospitalService
          .actualizarHospital(idHospital, hospitalActualizado)
          .subscribe(
            () => {
              Swal.fire(
                'Actualizado!',
                'El hospital ha sido actualizado.',
                'success'
              );
              this.router.navigate(['/lista-hospitales']);
            },
            (error) => {
              console.error('Error al actualizar hospital:', error);
              Swal.fire(
                'Error!',
                `Hubo un problema: ${error.message}`,
                'error'
              );
            }
          );
      }
    });
  }

  private mapearFormularioAActualizarHospital(): ActualizarHospital {
    return {
      idHospital: this.hospital!.idHospital,
      idDistrito: this.hospitalForm.value.idDistrito,
      nombre: this.hospitalForm.value.nombre,
      antiguedad: this.hospitalForm.value.antiguedad,
      area: this.hospitalForm.value.area,
      idSede: this.hospitalForm.value.idSede,
      idGerente: this.hospitalForm.value.idGerente,
      idCondicion: this.hospitalForm.value.idCondicion,
    };
  }
}
