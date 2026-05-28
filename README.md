# Guía de Ejecución - Backend

## 1. Requisitos

Antes de comenzar, asegúrate de tener instalado lo siguiente:

*   **JDK 21 o superior** (El proyecto fue desarrollado usando Java 25).
*   **SQL Server**
*   **Visual Studio Code** con las siguientes extensiones instaladas:
    *   Extension Pack for Java.
    *   Spring Boot Extension Pack.

## 2. Configuración de la Base de Datos

1.  Abre **SQL Server Management Studio**.
2.  Ejecuta los scripts SQL que se encuentran en el archivo `2. EjercicioPractico.sql`.
    *   Es fundamental crear primero las tablas `roles`, `usuarios`, `productos` y `movimientos`.
    *   También es fundamental insertar los usuarios y roles previos con las instrucciones que se señalan en el mismo archivo.
    *   Después, crea las vistas `view_login` y `view_historial_movimientos`.

## 3. Configuración del Proyecto

1.  Abre la carpeta del proyecto en VS Code.
2.  En a `src/main/resources/application.properties`.
3.  Ajusta las credenciales de conexión según tu instancia local de SQL Server:
    ```properties
    spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=almacenDBCAST;encrypt=true;trustServerCertificate=true
    spring.datasource.username=TuUsuarioSQLServer
    spring.datasource.password=TuContraseñaSQLServer
    ```

## 4. Ejecución del Proyecto


### Dashboard de Spring Boot
1.  En la barra lateral izquierda de VS Code, busca el icono de **Spring Boot Dashboard**.
2.  Identifica el proyecto `pruebaCast`.
3.  Haz clic en el botón de **Play** (Run).


# Guía de Ejecución - Frontend

## 1. Requisitos

Tener instalado lo siguiente:
- **Node.js**: Versión 18.x o superior.
- **Angular CLI**: Instálalo globalmente con `npm install -g @angular/cli`.
- **Backend**: El servidor de la API debe estar en ejecución (por defecto en `http://localhost:8080`).

##  Pasos para ejecutar el proyecto

## 1. Instalación de Dependencias
Abre una terminal en la carpeta raíz del proyecto y ejecuta:
```bash
npm install
```

## 2. Ejecuta el proyecto
Abre una terminal en la carpeta raíz del proyecto y ejecuta:
```bash
ng serve
```
Esto te dará una dirección que debes pegar en el buscador del navegador para que te envie al proyecto funcional.
