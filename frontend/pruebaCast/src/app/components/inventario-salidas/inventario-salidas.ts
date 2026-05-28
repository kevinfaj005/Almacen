import { Component, OnInit, signal } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InventarioService } from '../../services/inventario.service';
import { InventarioInterface } from '../../interfaces/inventario.interface';
import { MovimientoInterface } from '../../interfaces/movimiento.interface';

@Component({
  selector: 'app-inventario-salidas',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe],
  templateUrl: './inventario-salidas.html',
  styleUrl: './inventario-salidas.css'
})
export class InventarioSalidas implements OnInit {
  productos = signal<InventarioInterface[]>([]);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string>('');
  successMessage = signal<string>('');

  productoSeleccionado = signal<InventarioInterface | null>(null);
  ProductoId: string = '';
  cantSolicitada: number = 0;
  observaciones: string = '';

  constructor(private inventarioService: InventarioService) {}

  ngOnInit(): void {
    this.loadProductosActivos();
  }

  loadProductosActivos(): void {
    this.isLoading.set(true);
    this.inventarioService.getProductosActivos().subscribe({
      next: (res) => {
        const data = Array.isArray(res) ? res : (res?.data || []);
        this.productos.set(data);
        this.isLoading.set(false);
      },
      error: () => {
        this.errorMessage.set('Error al cargar productos activos.');
        this.isLoading.set(false);
      }
    });
  }

  onProductChange(): void {
    const product = this.productos().find(p => p.idProducto === this.ProductoId);
    this.productoSeleccionado.set(product || null);
  }

  processExit(): void {
    this.errorMessage.set('');
    this.successMessage.set('');
    const product = this.productoSeleccionado();
    
    if (!product || this.cantSolicitada <= 0) return;

    if (this.cantSolicitada > product.cantidad) {
      this.errorMessage.set(`Error: Stock insuficiente. Disponible: ${product.cantidad}`);
      return;
    }

    const session = JSON.parse(localStorage.getItem('user_session') || '{}');
    const userId = session.idUsuario;

    const movement: MovimientoInterface = {
      idProducto: product.idProducto as string,
      idUsuario: userId,
      tipo: 'SALIDA',
      cantidad: this.cantSolicitada,
      fecha: new Date().toISOString(),
      observaciones: this.observaciones
    };

    this.inventarioService.registrarMovimiento(movement, 'salida').subscribe({
      next: () => {
        this.successMessage.set('Salida registrada con éxito.');
        this.loadProductosActivos();
        this.productoSeleccionado.set(null);
        this.ProductoId = '';
        this.cantSolicitada = 0;
        this.observaciones = '';
      },
      error: () => this.errorMessage.set('Error al procesar la salida. Verifique stock o conexión.')
    });
  }
}