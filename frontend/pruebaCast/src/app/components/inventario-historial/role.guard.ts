import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const roleGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const session = JSON.parse(localStorage.getItem('user_session') || '{}');
  const userRole = session.nombreRol;
  
  const allowedRoles = route.data['roles'] as Array<string>;

  if (session && allowedRoles.includes(userRole)) {
    return true;
  }
  alert('Tu usuario no tiene los permisos correspondientes para acceder aquí.');
  router.navigate(['/entrada']);
  return false;
};