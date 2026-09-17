# AgendaServicios

Sistema de gestión de citas para un negocio de servicios, desarrollado como proyecto de Programación 2.

## Descripción

AgendaServicios permite gestionar las citas de un negocio de servicios. Cada cita registra el nombre del cliente, la fecha y hora programada, la descripción del servicio, la duración estimada y su estado.

El sistema permite crear, consultar, actualizar y eliminar citas, aplicando reglas de validación para mantener la integridad de la información.

## Arquitectura

El proyecto utiliza una arquitectura Maven de dos módulos:

- **agenda-core:** contiene el modelo de datos, las reglas de negocio, el acceso a la base de datos y las operaciones relacionadas con las citas.
- **agenda-ui:** contiene la interfaz gráfica desarrollada con Java Swing y utiliza `agenda-core` como dependencia.

La comunicación entre los módulos sigue la siguiente estructura:

    agenda-ui
        ↓
    agenda-core
        ↓
    MySQL

## Tecnologías utilizadas

- Java 25
- Maven
- Java Swing
- JDBC
- MySQL
- Eclipse
- Git y GitHub

## Base de datos

El sistema utiliza una base de datos MySQL llamada `agenda_servicios`.

La estructura de la base de datos se encuentra en:

    sql/schema.sql

La tabla principal del sistema es:

    citas

## Funcionalidades

El sistema contempla las siguientes operaciones:

- Registrar una nueva cita.
- Listar las citas existentes.
- Buscar una cita por su ID.
- Editar una cita.
- Reprogramar una cita.
- Cambiar el estado de una cita.
- Eliminar físicamente una cita con confirmación.
- Validar los datos antes de registrar o actualizar una cita.

Los estados permitidos para una cita son:

- `PENDIENTE`
- `CONFIRMADA`
- `CANCELADA`

## Reglas de negocio

Las citas deben cumplir las siguientes reglas:

- El nombre del cliente no puede estar vacío.
- La descripción del servicio no puede estar vacía.
- Al crear una cita, la fecha y hora no pueden estar en el pasado.
- La duración debe ser mayor que cero.
- El estado debe corresponder a uno de los estados permitidos.

La cancelación de una cita es diferente de su eliminación física. Una cita cancelada permanece almacenada con el estado `CANCELADA`.

## Estructura del proyecto

    AgendaServicios/
    ├── pom.xml
    ├── .gitignore
    ├── README.md
    ├── agenda-core/
    │   ├── pom.xml
    │   └── src/
    ├── agenda-ui/
    │   ├── pom.xml
    │   └── src/
    └── sql/
        └── schema.sql

## Módulos

### agenda-core

Módulo reutilizable que contiene la lógica central de la aplicación:

- Modelo de citas.
- Estados de las citas.
- Acceso a datos mediante DAO.
- Conexión con MySQL.
- Validaciones y reglas de negocio.
- Manejo de excepciones.

### agenda-ui

Módulo encargado de la interfaz gráfica del sistema mediante Java Swing.

Este módulo depende de `agenda-core` para utilizar el modelo, las reglas de negocio y las operaciones de acceso a datos.

## Base de datos y seguridad

Las credenciales reales de acceso a MySQL son configuraciones locales y no forman parte del repositorio.

El archivo `schema.sql` contiene únicamente la estructura necesaria para crear la base de datos y la tabla utilizada por el sistema.

## Estado del proyecto

El proyecto se encuentra en desarrollo. La estructura Maven, el módulo central, la conexión con la base de datos y las operaciones principales de acceso a datos ya se encuentran preparadas.

La interfaz gráfica y la integración completa entre `agenda-ui` y `agenda-core` forman parte del desarrollo del proyecto.