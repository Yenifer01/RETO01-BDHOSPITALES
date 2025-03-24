import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Distrito} from '../models/modelos';

@Injectable({
  providedIn: 'root'
})
export class DistritoService {

  private apiUrl = 'http://localhost:8081/api';
    constructor(private http: HttpClient) { }
  
    obtenerTodos(): Observable<Distrito[]> {
      return this.http.get<Distrito[]>(`${this.apiUrl}/distritos`);
    }
   
}
