import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Gerente} from '../models/modelos';

@Injectable({
  providedIn: 'root'
})
export class GerenteService {

  private apiUrl = 'http://localhost:8081/api';
     constructor(private http: HttpClient) { }
     
       obtenerTodos():Observable<Gerente[]> {
         return this.http.get<Gerente[]>(`${this.apiUrl}/gerentes`);
       }
}
