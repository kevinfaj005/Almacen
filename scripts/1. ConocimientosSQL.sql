CREATE DATABASE conocimientosSQL;
GO

USE conocimientosSQL;
GO

CREATE TABLE productos (
	idProducto INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	nombre VARCHAR(40),
	precio DECIMAL(16,2)
);
GO

CREATE TABLE ventas (
	idVenta INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	idProducto INT,
	cantidad INT,

	CONSTRAINT FK_Ventas_Productos
	FOREIGN KEY (idProducto)
	REFERENCES productos(idProducto)
);
GO


INSERT INTO productos(nombre, precio) VALUES
('LAPTOP', 3000.00),
('PC', 4000.00),
('MOUSE', 100.00),
('TECLADO', 150.00),
('MONITOR', 2000.00),
('MICROFONO', 350.00),
('AUDIFONOS', 450.00);
GO

INSERT INTO ventas(idProducto, cantidad) VALUES
(5, 8),
(1, 15),
(6, 13),
(6, 4),
(2, 3),
(5, 1),
(4, 5),
(2, 5),
(6, 2),
(1, 8);
GO

SELECT p.*
FROM productos p
INNER JOIN ventas v
	ON p.idProducto = v.idProducto;


SELECT p.idProducto,p.nombre,SUM(v.cantidad) AS totalVendidos
FROM productos p
INNER JOIN ventas v
	ON p.idProducto = v.idProducto
GROUP BY p.idProducto, p.nombre;



SELECT p.idProducto, p.nombre, p.precio, ISNULL(SUM(p.precio * v.cantidad), 0) AS totalVendido
FROM productos p
LEFT JOIN ventas v
	ON p.idProducto = v.idProducto
GROUP BY p.idProducto, p.nombre, p.precio;