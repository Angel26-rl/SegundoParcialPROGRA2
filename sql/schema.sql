-- ============================================================
-- Base de datos: AgendaServicios
-- Sistema de gestión de citas para un negocio de servicios
-- ============================================================

CREATE DATABASE IF NOT EXISTS agenda_servicios;

USE agenda_servicios;


-- ============================================================
-- Tabla: citas
-- ============================================================

CREATE TABLE IF NOT EXISTS citas (
    id INT AUTO_INCREMENT PRIMARY KEY,

    nombre_cliente VARCHAR(100) NOT NULL,

    fecha_hora DATETIME NOT NULL,

    servicio VARCHAR(150) NOT NULL,

    duracion_minutos INT NOT NULL,

    estado ENUM('PENDIENTE', 'CONFIRMADA', 'CANCELADA')
        NOT NULL DEFAULT 'PENDIENTE',

    CONSTRAINT chk_nombre_cliente
        CHECK (TRIM(nombre_cliente) <> ''),

    CONSTRAINT chk_servicio
        CHECK (TRIM(servicio) <> ''),

    CONSTRAINT chk_duracion
        CHECK (duracion_minutos > 0)
);


-- ============================================================
-- Decisiones de diseño
-- ============================================================

-- 1. En Java se utilizará LocalDateTime para representar
--    la fecha y hora de una cita.
--
-- 2. En MySQL se utilizará DATETIME para almacenar fecha_hora.
--    Se utiliza una sola columna porque la cita necesita
--    representar conjuntamente la fecha y la hora.
--
-- 3. El estado se representará en Java mediante el enum
--    EstadoCita y en MySQL mediante ENUM.
--
-- 4. Los estados permitidos son:
--       PENDIENTE
--       CONFIRMADA
--       CANCELADA
--
-- 5. El ID se genera automáticamente mediante AUTO_INCREMENT.
--    El usuario no debe introducirlo desde la interfaz.
--
-- 6. El nombre del cliente tendrá una longitud máxima de
--    100 caracteres.
--
-- 7. La descripción del servicio tendrá una longitud máxima
--    de 150 caracteres.
--
-- 8. La duración se almacena en minutos como un valor entero
--    y debe ser mayor que cero.
--
-- 9. La validación de que la fecha y hora no estén en el pasado
--    al crear una cita se realizará en la lógica de negocio
--    de la aplicación, mediante CitaService.
--
-- 10. La cancelación de una cita no elimina el registro.
--     El estado se cambia a CANCELADA. La eliminación física
--     se realizará mediante una operación DELETE independiente.