import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InventarioService } from '../../services/inventario.service';
import { InventarioInterface } from '../../interfaces/inventario.interface';

@Component({
  selector: 'app-inventario.component',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, FormsModule],
  templateUrl: './inventario.component.html',
  styleUrl: './inventario.component.css'
})
export class InventarioComponent implements OnInit {
  productos = signal<InventarioInterface[]>([]);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string>('');
  modalErrorMessage = signal<string>('');

  showModal = signal<boolean>(false);
  modalMode = signal<'add' | 'edit' | 'stock'>('add');
  productoSeleccionado = signal<InventarioInterface>(this.getEmptyProduct());
  cantActual: number = 0;
  cantAgregar: number = 0;

  isAdmin = computed(() => {
    const session = JSON.parse(localStorage.getItem('user_session') || '{}');
    return session.nombreRol === 'Administrador';
  });

  constructor(private inventarioService: InventarioService) {}

  ngOnInit(): void {
    this.loadProductos();
  }

  private getEmptyProduct(): InventarioInterface {
    return { nombre: '', descripcion: '', precio: 0, cantidad: 0, observaciones: '', estatus: true };
  }

  loadProductos(): void {
    this.isLoading.set(true);
    this.errorMessage.set('');
    
    this.inventarioService.getAllProductos().subscribe({
      next: (res) => {
        const data = Array.isArray(res) ? res : (res?.data && Array.isArray(res.data) ? res.data : []);
        this.productos.set(data);
        if (!Array.isArray(res) && res && res.success === false) {
          this.errorMessage.set(res.message || 'Error al cargar productos.');
        }
        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('Error al cargar productos:', err);
        this.isLoading.set(false);
        this.errorMessage.set('Error de conexión con el servidor.');
      }
    });
  }

  openAddModal(): void {
    this.modalMode.set('add');
    this.cantAgregar = 0;
    const newProduct = this.getEmptyProduct();
    newProduct.cantidad = 0; 
    newProduct.estatus = true;
    this.productoSeleccionado.set(newProduct);
    this.showModal.set(true);
  }

  openEditModal(product: InventarioInterface): void {
    this.modalMode.set('edit');
    this.cantAgregar = 0;
    this.productoSeleccionado.set({ ...product });
    this.showModal.set(true);
  }

  openStockModal(product: InventarioInterface): void {
    this.modalMode.set('stock');
    this.cantActual = product.cantidad;
    this.cantAgregar = 0;
    this.productoSeleccionado.set({ ...product });
    this.showModal.set(true);
  }

  closeModal(): void {
    this.showModal.set(false);
    this.modalErrorMessage.set('');
    this.cantAgregar = 0;
  }

  guardarProducto(): void {
    const session = JSON.parse(localStorage.getItem('user_session') || '{}');
    const userId = session.idUsuario;

    if (!this.isAdmin()) {
      alert('Tu usuario no tiene los permisos correspondientes para realizar esta acción.');
      return;
    }

    this.modalErrorMessage.set('');
    const product = { ...this.productoSeleccionado() };
    if (this.modalMode() === 'stock') {
      if (this.cantAgregar <= 0) {
        this.modalErrorMessage.set('Error: La cantidad a añadir debe ser mayor a 0.');
        return;
      }
      product.cantidad = this.cantActual + this.cantAgregar;
    }

    this.isLoading.set(true);
    const operation = this.modalMode() === 'add'
      ? this.inventarioService.crearProducto(product, userId)
      : this.inventarioService.actProducto(product, userId);

    operation.subscribe({
      next: () => {
        this.loadProductos();
        this.closeModal();
      },
      error: () => {
        this.modalErrorMessage.set('Error al procesar la solicitud.');
        this.isLoading.set(false);
      }
    });
  }

  toggleStatus(product: InventarioInterface): void {
    const session = JSON.parse(localStorage.getItem('user_session') || '{}');
    const userId = session.idUsuario;

    if (!this.isAdmin()) {
      alert('Tu usuario no tiene los permisos correspondientes para realizar esta acción.');
      return;
    }

    const updatedProduct = { ...product, estatus: !product.estatus };
    const action = updatedProduct.estatus ? 'activar' : 'dar de baja';
    
    if (!confirm(`¿Estás seguro de que deseas ${action} este producto?`)) return;

    this.isLoading.set(true);
    this.inventarioService.actProducto(updatedProduct, userId).subscribe({
      next: () => this.loadProductos(),
      error: () => {
        this.errorMessage.set('Error al actualizar el estatus.');
        this.isLoading.set(false);
      }
    });
  }
}
