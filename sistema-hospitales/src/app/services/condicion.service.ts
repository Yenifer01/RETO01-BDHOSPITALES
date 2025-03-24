import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Condicion } from '../models/modelos';

@Injectable({
  providedIn: 'root'
})
export class CondicionService {

  private apiUrl = 'http://localhost:8081/api';
    constructor(private http: HttpClient) { }
      
        obtenerTodos():Observable<Condicion[]> {
          return this.http.get<Condicion[]>(`${this.apiUrl}/condiciones`);
        }
}
