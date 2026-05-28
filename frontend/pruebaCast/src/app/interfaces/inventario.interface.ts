export interface InventarioInterface {
    idProducto?: number | string;
    nombre: string;
    descripcion: string;
    precio: number;
    cantidad: number;
    estatus: boolean;
    observaciones?: string;
}
