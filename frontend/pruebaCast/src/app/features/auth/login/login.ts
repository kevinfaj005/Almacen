import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../../services/login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  correo: string = '';
  contrasena: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(private loginService: LoginService, private router: Router) {}

  onLogin() {
    if (!this.correo || !this.contrasena) {
      this.errorMessage = 'Por favor, ingresa correo y contraseña.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.loginService.login(this.correo, this.contrasena).subscribe({
      next: (res) => {
        this.isLoading = false;
        if (res.success) {
          this.router.navigate(['/inventario']);
        } else {
          this.errorMessage = res.message || 'Credenciales incorrectas.';
        }
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = 'Error de conexión con el servidor.';
        console.error(err);
      }
    });
  }
}