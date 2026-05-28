import { Routes } from '@angular/router';
import { InventarioComponent } from './components/inventario.component/inventario.component';
import { InventarioSalidas } from './components/inventario-salidas/inventario-salidas';
import { InventarioHistorial } from './components/inventario-historial/inventario-historial';
import { roleGuard } from './guards/role-guard';
import { authGuard } from './guards/auth.guard';
import { Login } from './features/auth/login/login';

export const routes: Routes = [
  { path: 'login', component: Login, title: 'Iniciar Sesión' },
  { 
    path: 'entrada', 
    component: InventarioComponent, 
    title: 'Entrada de Productos',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['Administrador', 'Almacenista'] } 
  },
  { 
    path: 'salida', 
    component: InventarioSalidas, 
    title: 'Salida de Productos',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['Almacenista'] }
  },
  { 
    path: 'historial', 
    component: InventarioHistorial, 
    title: 'Historial de Movimientos',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['Administrador'] }
  },
  { path: '', redirectTo: 'entrada', pathMatch: 'full' },
  { path: '**', redirectTo: 'entrada' }
];
