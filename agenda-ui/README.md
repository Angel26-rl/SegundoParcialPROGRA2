# agenda-ui

Módulo de interfaz gráfica de AgendaServicios.

## Descripción

`agenda-ui` contiene la interfaz gráfica del sistema desarrollada con Java Swing.

Su responsabilidad principal es permitir al usuario interactuar con el sistema para consultar y gestionar las citas de un negocio de servicios.

## Responsabilidades

Este módulo se encarga de:

- Mostrar las citas registradas.
- Permitir registrar nuevas citas.
- Permitir editar citas existentes.
- Permitir reprogramar citas.
- Permitir cambiar el estado de una cita.
- Permitir eliminar físicamente una cita mediante confirmación.
- Mostrar mensajes de validación y errores al usuario.
- Comunicarse con `agenda-core` para ejecutar las operaciones del sistema.

## Arquitectura

`agenda-ui` no accede directamente a la base de datos.

La comunicación se realiza mediante `agenda-core`:

    Usuario
       ↓
    agenda-ui
       ↓
    agenda-core
       ↓
    MySQL

Esta separación permite mantener la interfaz gráfica independiente de la lógica de negocio y del acceso a datos.

## Dependencia

Este módulo utiliza `agenda-core` como dependencia Maven.

`agenda-core` proporciona:

- Modelo de citas.
- Estados de las citas.
- Reglas de negocio.
- Operaciones de acceso a datos.
- Manejo de validaciones.
- Conexión con la base de datos.

## Interfaz gráfica

La interfaz será desarrollada utilizando Java Swing.

La aplicación contará con una ventana principal desde la cual se podrán consultar y gestionar las citas.

Entre las operaciones previstas se encuentran:

- Nuevo
- Editar
- Eliminar

Los formularios permitirán introducir y modificar la información correspondiente a cada cita.

## Estados de las citas

La interfaz utilizará los estados definidos por `agenda-core`:

- `PENDIENTE`
- `CONFIRMADA`
- `CANCELADA`

Una cita cancelada permanece registrada en la base de datos con el estado `CANCELADA`.

La cancelación es diferente de la eliminación física del registro.

## Validaciones

Las validaciones de las reglas de negocio serán realizadas por `agenda-core`.

La interfaz gráfica mostrará al usuario los mensajes correspondientes cuando una operación no cumpla las reglas establecidas.

## Tecnologías

- Java 25
- Java Swing
- Maven
- JDBC
- MySQL

## Estado

El módulo se encuentra en desarrollo.

La estructura Maven y la dependencia con `agenda-core` ya están preparadas. La construcción de la interfaz gráfica y su integración completa con el módulo central forman parte del desarrollo del sistema.