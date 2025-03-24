import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Condicion, Distrito, ListaHospital, Sede } from '../../../models/modelos';
import { HospitalService } from '../../../services/hospital.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { DistritoService } from '../../../services/distrito.service';
import { SedeService } from '../../../services/sede.service';
import { DataTablesModule } from 'angular-datatables';
import { CondicionService } from '../../../services/condicion.service';
import { NavbarComponent } from "../../navbar/navbar.component";

@Component({
  selector: 'app-lista-hospitales',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, DataTablesModule, NavbarComponent,RouterLink],
  templateUrl: './lista-hospitales.component.html',
  styleUrl: './lista-hospitales.component.css'
})
export class ListaHospitalesComponent implements OnInit {

  hospitales: ListaHospital[] = [];
  distritos: Distrito[] = [];
  hospitalesFiltrados: ListaHospital[] = []; 
  sedes: Sede[] = [];
  condiciones: Condicion[] = [];

  distritoSeleccionado: number | null = null;
  sedeSeleccionada: number | null = null;
  condicionSeleccionada: number | null = null;
  filtroTexto: string='';
  

  constructor(
    private hospitalService: HospitalService,
    private distritoService: DistritoService,
    private sedeService: SedeService,
    private condicionService: CondicionService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.hospitalService.listarHospitales().subscribe( 
      (data: ListaHospital[]) => {
        this.hospitales = data;

        this.hospitalesFiltrados = data;
      },
      (error) => {
        console.error('Error al obtener hospitales', error);
      }
    );
    this.loadDistritos();
    this.loadSedes();
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
  loadCondiciones(): void {
    this.condicionService.obtenerTodos().subscribe(
      (data: Condicion[]) => { this.condiciones = data; },
      error => console.error('Error al cargar condiciones:', error)
    );
  }

  
  buscarHospitales(event: Event): void {
    event.preventDefault();
  
    if (!this.distritoSeleccionado && !this.sedeSeleccionada && !this.condicionSeleccionada) {
      this.hospitalesFiltrados = [...this.hospitales]; 
      return;
    }
  
    this.hospitalesFiltrados = this.hospitales.filter(hospital => {
      const coincideDistrito = this.distritoSeleccionado 
        ? hospital.descDistrito.trim().toLowerCase() === 
          this.distritos.find(d => d.iddistrito === this.distritoSeleccionado)?.descdistrito.trim().toLowerCase()
        : true;
  
      const coincideSede = this.sedeSeleccionada 
        ? hospital.descSede.trim().toLowerCase() === 
          this.sedes.find(s => s.idsede === this.sedeSeleccionada)?.descsede.trim().toLowerCase()
        : true;

        const coincideCondicion = this.condicionSeleccionada
        ? hospital.descCondicion.trim().toLowerCase() === 
          this.condiciones.find(s => s.idcondicion === this.condicionSeleccionada)?.desccondicion.trim().toLowerCase()
        : true;
  
      return coincideDistrito && coincideSede && coincideCondicion;
    });
  
    console.log('Hospitales filtrados:', this.hospitalesFiltrados);
  }

  filtrarHospitales(): void {
    if (!this.distritoSeleccionado && !this.sedeSeleccionada && !this.condicionSeleccionada) {
      this.hospitalesFiltrados = [...this.hospitales];
    } else {
      this.hospitalesFiltrados = this.hospitales.filter(hospital => {
        const coincideDistrito = this.distritoSeleccionado 
          ? hospital.descDistrito.trim().toLowerCase() === 
            this.distritos.find(d => d.iddistrito === this.distritoSeleccionado)?.descdistrito.trim().toLowerCase()
          : true;
  
        const coincideSede = this.sedeSeleccionada 
          ? hospital.descSede.trim().toLowerCase() === 
            this.sedes.find(s => s.idsede === this.sedeSeleccionada)?.descsede.trim().toLowerCase()
          : true;

        const coincideCondicion = this.condicionSeleccionada
          ? hospital.descCondicion.trim().toLowerCase() === 
            this.condiciones.find(s => s.idcondicion === this.condicionSeleccionada)?.desccondicion.trim().toLowerCase()
          : true;
    
        return coincideDistrito && coincideSede && coincideCondicion;
      });
    }
  }

  filtrarPorNombreOGerente(): void {
    const textoFiltro = this.filtroTexto.trim().toLowerCase();
  
    if (!textoFiltro) {
      this.hospitalesFiltrados = [...this.hospitales];
    } else {
      this.hospitalesFiltrados = this.hospitales.filter((hospital) => {
        const nombreHospital = hospital.nombre?.trim().toLowerCase() || '';
        const nombreGerente = hospital.descGerente?.trim().toLowerCase() || '';
  
        return (
          nombreHospital.includes(textoFiltro) || nombreGerente.includes(textoFiltro)
        );
      });
    }
  
    console.log('Hospitales filtrados:', this.hospitalesFiltrados);
    this.cdr.detectChanges(); 
  }
  


  editarHospital(hospital: ListaHospital): void {
    if (hospital?.idHospital) { 
      this.router.navigate(['/editar-hospitales', hospital.idHospital]);
    } else {
      console.log('No se ha seleccionado ningún hospital para editar.');
    }
  }

  eliminarHospital(idHospital: number): void {
    if (!idHospital) {
      Swal.fire('Hospital no seleccionado', 'Por favor selecciona un hospital.', 'warning');
      return;
    }
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'No podrás revertir esta acción.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#007bff',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.hospitalService.eliminarHospital(idHospital).subscribe({
          next: (response) => {
            this.hospitalesFiltrados = this.hospitales.filter(h => h.idHospital !== idHospital);
  
            Swal.fire('¡Eliminado!', 'El hospital ha sido eliminado correctamente.', 'success');
          },
          error: (error) => {
            console.error('Error al eliminar el hospital:', error);
            let mensaje = error.error?.message || 'Hubo un problema al eliminar el hospital.';
            Swal.fire('Error', mensaje, 'error');
          }
        });
      }
    });
  }

}
