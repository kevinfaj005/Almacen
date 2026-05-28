CREATE DATABASE almacenDBCAST;
GO
USE almacenDBCAST;
GO

CREATE TABLE roles (
  idRol   UNIQUEIDENTIFIER  NOT NULL DEFAULT NEWID(),
  nombre  VARCHAR(50)       NOT NULL,
  CONSTRAINT PK_roles PRIMARY KEY (idRol)
);
GO

INSERT INTO roles (nombre) VALUES ('Administrador');
INSERT INTO roles (nombre) VALUES ('Almacenista');
GO

CREATE TABLE usuarios (
  idUsuario   UNIQUEIDENTIFIER  NOT NULL DEFAULT NEWID(),
  nombre      VARCHAR(100)      NOT NULL,
  correo      VARCHAR(50)       NOT NULL,
  contrasena  VARCHAR(255)      NOT NULL,
  idRol       UNIQUEIDENTIFIER  NOT NULL,
  estatus     BIT               NOT NULL DEFAULT 1,
  CONSTRAINT PK_usuarios  PRIMARY KEY (idUsuario),
  CONSTRAINT UQ_correo    UNIQUE      (correo),
  CONSTRAINT FK_usuario_rol FOREIGN KEY (idRol) REFERENCES roles(idRol)
);
GO

CREATE TABLE productos (
  idProducto   UNIQUEIDENTIFIER  NOT NULL DEFAULT NEWID(),
  nombre       VARCHAR(100)      NOT NULL,
  descripcion  VARCHAR(255)      NULL,
  precio       DECIMAL(16,2)     NOT NULL DEFAULT 0.00,
  cantidad     INT               NOT NULL DEFAULT 0,
  estatus      BIT               NOT NULL DEFAULT 1,
  CONSTRAINT PK_productos PRIMARY KEY (idProducto)
);
GO

CREATE TABLE movimientos (
  idMovimiento  UNIQUEIDENTIFIER  NOT NULL DEFAULT NEWID(),
  idProducto    UNIQUEIDENTIFIER  NOT NULL,
  idUsuario     UNIQUEIDENTIFIER  NOT NULL,
  tipo          VARCHAR(7)        NOT NULL,
  cantidad      INT               NOT NULL,
  fechaHora     DATETIME          NOT NULL DEFAULT GETDATE(),
  observaciones TEXT              NULL,
  CONSTRAINT PK_movimientos   PRIMARY KEY (idMovimiento),
  CONSTRAINT CK_tipo          CHECK (tipo IN ('entrada', 'salida')),
  CONSTRAINT FK_mov_producto  FOREIGN KEY (idProducto) REFERENCES productos(idProducto),
  CONSTRAINT FK_mov_usuario   FOREIGN KEY (idUsuario)  REFERENCES usuarios(idUsuario)
);
GO


INSERT INTO roles (nombre) VALUES ('Administrador');
INSERT INTO roles (nombre) VALUES ('Almacenista');

-- haga una consulta a SELECT * FROM roles; paara obtener la id exacta del rol, ya que este se genera automaticamente
INSERT INTO usuarios (nombre, correo, contrasena, idRol, estatus) VALUES ('Kevin Avila', 'kevin@gmail.com', '12345678', [id de la consulta que se indica antes], '1');
-- vuelva a hacer la consulta a SELECT * FROM roles; para seleccionar el rol faltante y tener los dos diferentes tipos de usuario
INSERT INTO usuarios (nombre, correo, contrasena, idRol, estatus) VALUES ('Almacenista Juan', 'juan@gmail.com', '12345678', [id de la consulta que se indica antes], '1');



CREATE VIEW view_login AS
SELECT  u.idUsuario, u.nombre, u.correo, u.contrasena, u.estatus, r.nombre AS nombreRol
FROM usuarios u
INNER JOIN roles r ON u.idRol = r.idRol;

CREATE VIEW view_historial_movimientos AS
SELECT m.idMovimiento, m.idProducto, p.nombre AS nombreProducto, m.idUsuario, u.nombre AS nombreUsuario, m.tipo, m.cantidad, m.fechaHora, m.observaciones
FROM movimientos m
JOIN productos p ON m.idProducto = p.idProducto
JOIN usuarios u ON m.idUsuario = u.idUsuario;
