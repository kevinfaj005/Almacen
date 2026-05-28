import { Component, OnInit, signal } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { InventarioService } from '../../services/inventario.service';
import { MovimientoInterface } from '../../interfaces/movimiento.interface';

@Component({
  selector: 'app-inventario-historial',
  standalone: true,
  imports: [CommonModule, DatePipe],
  templateUrl: './inventario-historial.html',
  styleUrl: './inventario-historial.css'
})
export class InventarioHistorial implements OnInit {
  movimientos = signal<MovimientoInterface[]>([]);
  isLoading = signal<boolean>(false);
  currentFilter = signal<string>('');

  constructor(private inventarioService: InventarioService) {}

  ngOnInit(): void {
    this.filtro('');
  }

  filtro(tipo: string): void {
    this.currentFilter.set(tipo);
    this.isLoading.set(true);
    this.inventarioService.getHistorial(tipo).subscribe({
      next: (res) => {
        this.movimientos.set(Array.isArray(res) ? res : (res?.data || []));
        this.isLoading.set(false);
      },
      error: () => this.isLoading.set(false)
    });
  }
}