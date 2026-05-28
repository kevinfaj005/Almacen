import { Component, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class NavbarComponent {
  private loginService = inject(LoginService);
  private router = inject(Router);

  userRole = computed(() => this.loginService.session()?.nombreRol || '');

  isLoggedIn = computed(() => this.loginService.isLoggedIn());

  isAdmin = computed(() => this.userRole() === 'Administrador');
  isAlmacenista = computed(() => this.userRole() === 'Almacenista');

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['/login']);
  }
}