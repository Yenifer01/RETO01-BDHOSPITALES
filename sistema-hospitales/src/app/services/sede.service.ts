import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Sede } from '../models/modelos';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SedeService {

  private apiUrl = 'http://localhost:8081/api';
    constructor(private http: HttpClient) { }
    
      obtenerTodos():Observable<Sede[]> {
        return this.http.get<Sede[]>(`${this.apiUrl}/sedes`);
      }
}
