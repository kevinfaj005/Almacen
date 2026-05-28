import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { InventarioInterface } from '../interfaces/inventario.interface';
import { MovimientoInterface } from '../interfaces/movimiento.interface';

@Injectable({
  providedIn: 'root'
})
export class InventarioService {
  private apiUrl = environment.apiUrl + '/inventario';
  private movementsUrl = environment.apiUrl + '/movimientos';

  constructor(private http: HttpClient) { }

  getAllProductos(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

  getProductosActivos(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/activos`);
  }

  crearProducto(product: InventarioInterface, userId: string): Observable<any> {
    const headers = { 'X-User-Id': userId };
    return this.http.post<any>(this.apiUrl, product, { headers });
  }

  actProducto(product: InventarioInterface, userId: string): Observable<any> {
    const headers = { 'X-User-Id': userId };
    return this.http.put<any>(this.apiUrl, product, { headers });
  }

  eliminarProducto(id: number | string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  // Ahora permitimos especificar el subpath ('entrada' o 'salida') independientemente del motivo
  registrarMovimiento(movement: MovimientoInterface, subPath: 'entrada' | 'salida'): Observable<any> {
    return this.http.post<any>(`${this.movementsUrl}/${subPath}`, movement);
  }

  getHistorial(tipo?: string): Observable<any> {
    const url = tipo ? `${this.movementsUrl}/historial?tipo=${tipo}` : `${this.movementsUrl}/historial`;
    return this.http.get<any>(url);
  }
}