import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from "../navbar/navbar.component";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  username = '';
  email = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}
  register() {
    const user ={
      username: this.username,
      email: this.email, 
      password: this.password
    };
    
    localStorage.setItem('username', user.username);
    localStorage.setItem('email', user.email);

    if (this.authService.register(this.username, this.password,this.email)) {
      alert('Registro exitoso. Ahora puede iniciar sesión.');
      this.router.navigate(['/login']);
    } else {
      alert('Registro fallido. Intente de nuevo.');
    }
   
  }


}
