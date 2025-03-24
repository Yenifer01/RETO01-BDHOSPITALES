import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ActualizarHospital, ListaHospital, RegistroHospital } from '../models/modelos';

@Injectable({
  providedIn: 'root'
})
export class HospitalService {

  private apiUrl = 'http://localhost:8081/api/hospitales';
  constructor(private http: HttpClient) { }

  listarHospitales(): Observable<ListaHospital[]> {
    return this.http.get<ListaHospital[]>(`${this.apiUrl}/listar`);
  }
  registrarHospital(hospital: RegistroHospital): Observable<RegistroHospital> {
    return this.http.post<RegistroHospital>(`${this.apiUrl}/registrar`, hospital);
  }
  listarHospitalPorId(id: number): Observable<ListaHospital> {
    return this.http.get<ListaHospital>(`${this.apiUrl}/listar/${id}`);
  }
  
  actualizarHospital(id: number, hospital: ActualizarHospital): Observable<ActualizarHospital> {
    return this.http.put<ActualizarHospital>(`${this.apiUrl}/actualizar/${id}`, hospital);
  }

  eliminarHospital(idHospital: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/eliminar/${idHospital}`);
  }
  
  

}
