import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { Condicion, Distrito, Gerente, RegistroHospital, Sede } from '../../../models/modelos';
import { DistritoService } from '../../../services/distrito.service';
import { HospitalService } from '../../../services/hospital.service';
import { SedeService } from '../../../services/sede.service';
import { GerenteService } from '../../../services/gerente.service';
import { CondicionService } from '../../../services/condicion.service';
import { Router, RouterLink } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import Swal from 'sweetalert2';
import { NavbarComponent } from "../../navbar/navbar.component";
@Component({
  selector: 'app-registro-hospitales',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule, NavbarComponent, RouterLink],
  templateUrl: './registro-hospitales.component.html',
  styleUrl: './registro-hospitales.component.css'
})
export class RegistroHospitalesComponent implements OnInit {

  hospitalForm!: FormGroup;
  distritos: Distrito[] = [];
  sedes: Sede[] = [];
  gerentes: Gerente[] = [];
  condiciones: Condicion[] = [];

  constructor(
    private fb: FormBuilder,
    private hospitalService: HospitalService,
    private distritoService: DistritoService,
    private sedeService: SedeService,
    private gerenteService: GerenteService,
    private condicionService: CondicionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.hospitalForm = this.fb.group({
      nombre: ['', Validators.required],
      antiguedad: [null, [Validators.required, Validators.min(1)]],
      area: [null, [Validators.required, Validators.min(1)]],
      idDistrito: [null, Validators.required],
      idSede: [null, Validators.required],
      idGerente: [null, Validators.required],
      idCondicion: [null, Validators.required]
    });

    this.loadDistritos();
    this.loadSedes();
    this.loadGerentes();
    this.loadCondiciones();
  }

  loadDistritos(): void {
    this.distritoService.obtenerTodos().subscribe(
      (data: Distrito[]) => { this.distritos = data; },
      error => console.error('Error al cargar distritos:', error)
    );
  }

  loadSedes(): void {
    this.sedeService.obtenerTodos().subscribe(
      (data: Sede[]) => { this.sedes = data; },
      error => console.error('Error al cargar sedes:', error)
    );
  }

  loadGerentes(): void {
    this.gerenteService.obtenerTodos().subscribe(
      (data: Gerente[]) => { this.gerentes = data; },
      error => console.error('Error al cargar gerentes:', error)
    );
  }

  loadCondiciones(): void {
    this.condicionService.obtenerTodos().subscribe(
      (data: Condicion[]) => { this.condiciones = data; },
      error => console.error('Error al cargar condiciones:', error)
    );
  }

  guardarHospital() {
   const hospitalData = this.hospitalForm.getRawValue();

    if (
        hospitalData.antiguedad === null || hospitalData.antiguedad === undefined || hospitalData.antiguedad <= 0 ||
        hospitalData.area === null || hospitalData.area === undefined || hospitalData.area <= 0
    ) {
        Swal.fire({
            icon: 'warning',
            title: 'Validación',
            text: 'La antigüedad y el área deben ser mayores que 0.'
        });
        return; 
    }

    if (this.hospitalForm.invalid) {
      Swal.fire({
        icon: 'warning',
        title: 'Validación',
        text: 'Por favor, completa todos los campos obligatorios.'
      });
      return;
    }
  

    this.hospitalService.registrarHospital(hospitalData).subscribe({
      next: (res: any) => {
        if (res?.result === 'pass') {
          Swal.fire({
            icon: 'success',
            title: 'Hospital Registrado Correctamente',
          });
          this.router.navigate(['/lista-hospitales']);
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo guardar el hospital.'
          });
        }
      },
      error: (error) => {
        Swal.fire({
          icon: 'warning',
          title: 'Validación',
          text: 'Ya existe un Hospital con el nombre ingresado.'
        });
      }
    });
  }
  
  

}
