import { Component, OnInit } from '@angular/core';
import { Router,RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent{

  constructor(
    private authService: AuthService, 
    private router: Router) {}

  cerrarSesion() {
    this.authService.logout();
    alert('¡Tu sesión se ha cerrado con éxito!');
    this.router.navigate(['/card']); 
  }

}
