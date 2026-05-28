export interface MovimientoInterface {
    idMovimiento?: string;
    idProducto: string;
    idUsuario: string;
    tipo: string;
    cantidad: number;
    fecha?: string;
    descripcion?: string;
    observaciones?: string;
    nombreProducto?: string;
    nombreUsuario?: string;
}
