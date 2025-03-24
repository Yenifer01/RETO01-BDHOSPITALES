import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly tokenKey = 'authToken';
  private readonly usernameKey = 'username'; 
  private readonly emailKey = 'email'; 
  private readonly isAuthenticatedKey = 'isAuthenticated'; // NUEVO: Guardar estado de autenticación

  constructor() {}

  private isLocalStorageAvailable(): boolean {
    return typeof window !== 'undefined' && !!window.localStorage;
  }

  register(username: string, password: string, email: string): boolean {
    if (this.isLocalStorageAvailable()) {
      const token = btoa(`${email}:${password}`);
      localStorage.setItem(this.tokenKey, token);
      localStorage.setItem(this.usernameKey, username); 
      localStorage.setItem(this.emailKey, email); 
      localStorage.setItem(this.isAuthenticatedKey, 'true'); // NUEVO: Guardar autenticación
      return true;
    }
    return false;
  }

  login(email: string, password: string): boolean {
    if (this.isLocalStorageAvailable()) {
      const token = localStorage.getItem(this.tokenKey);
      if (token) {
        const [storedEmail, storedPassword] = atob(token).split(':');
        if (email === storedEmail && password === storedPassword) {
          localStorage.setItem(this.isAuthenticatedKey, 'true'); // NUEVO: Guardar autenticación
          return true;
        }
      }
    }
    return false;
  }

  logout(): void {
    if (this.isLocalStorageAvailable()) {
      localStorage.removeItem(this.isAuthenticatedKey);
    }
  }

  getAuthStatus(): boolean {
    return this.isLocalStorageAvailable() && localStorage.getItem(this.isAuthenticatedKey) === 'true';
  }

  getUsername(): string | null {
    return this.isLocalStorageAvailable() ? localStorage.getItem(this.usernameKey) : null;
  }

  getEmail(): string | null {
    return this.isLocalStorageAvailable() ? localStorage.getItem(this.emailKey) : null;
  }

}
