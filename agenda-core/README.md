# agenda-core

Módulo central y reutilizable de AgendaServicios.

## Descripción

`agenda-core` contiene la lógica principal necesaria para gestionar las citas del sistema.

El módulo está diseñado para separar la lógica de negocio y el acceso a datos de la interfaz gráfica. De esta manera, otras interfaces o aplicaciones pueden reutilizar las funcionalidades proporcionadas por este módulo.

## Responsabilidades

Este módulo se encarga de:

- Representar las citas mediante clases de modelo.
- Definir los estados permitidos de una cita.
- Validar las reglas de negocio.
- Administrar las operaciones de acceso a datos.
- Establecer la conexión con MySQL.
- Ejecutar operaciones de consulta, registro, actualización y eliminación.
- Manejar excepciones relacionadas con validaciones.

## Estructura

    agenda-core/
    ├── pom.xml
    └── src/
        └── main/
            └── java/
                └── com/
                    └── angelramos/
                        └── agendacore/
                            ├── model/
                            ├── dao/
                            ├── exception/
                            ├── service/
                            └── util/

## Componentes principales

### model

Contiene las clases que representan los datos utilizados por el sistema.

- `Cita`
- `EstadoCita`

### dao

Contiene las interfaces y clases responsables del acceso a los datos.

- `CitaDAO`
- `CitaDAOImpl`

Las operaciones disponibles incluyen:

- Listar citas.
- Buscar una cita por ID.
- Guardar una cita.
- Actualizar una cita.
- Eliminar una cita.

### service

Contiene la lógica de negocio del sistema.

- `CitaService`

`CitaService` valida los datos antes de ejecutar las operaciones correspondientes en el DAO.

### exception

Contiene las excepciones utilizadas para informar errores de validación.

- `ValidacionException`

### util

Contiene las utilidades necesarias para la conexión con la base de datos.

- `ConexionBD`

## Reglas de negocio

El módulo valida que:

- El nombre del cliente no esté vacío.
- La descripción del servicio no esté vacía.
- La fecha y hora de una nueva cita no estén en el pasado.
- La duración sea mayor que cero.
- El estado de la cita sea válido.
- El ID utilizado para actualizar o eliminar una cita sea válido.

## Dependencia de base de datos

El módulo utiliza JDBC para comunicarse con MySQL mediante MySQL Connector/J.

La base de datos utilizada por el proyecto es:

    agenda_servicios

La tabla principal es:

    citas

La estructura de la base de datos se encuentra en:

    ../sql/schema.sql

## Uso desde otros módulos

`agenda-core` está diseñado para ser utilizado como dependencia Maven.

En este proyecto, `agenda-ui` depende de `agenda-core` para acceder al modelo, las reglas de negocio y las operaciones de persistencia.

La relación entre ambos módulos es:

    agenda-ui
        ↓
    agenda-core
        ↓
    MySQL

## Seguridad

Las credenciales reales de conexión a la base de datos deben mantenerse como configuración local y no deben incluirse en el repositorio público.

## Estado

El módulo contiene la lógica central y las operaciones principales de acceso a datos necesarias para el funcionamiento de AgendaServicios.