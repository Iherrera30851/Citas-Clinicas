# Citas Clínicas

Proyecto del curso SC-403 Desarrollo de Aplicaciones Web y Patrones.

Isaac Herrera Vargas

Aplicación web para manejar las citas de un consultorio pequeño: horarios de los profesionales, agendar citas, confirmarlas, atenderlas, notas clínicas y pagos.

## Requisitos

- Java 21
- NetBeans
- MySQL y MySQL Workbench

## Cómo ejecutar

1. En Workbench, con el usuario root, ejecutar el archivo `src/main/resources/creaTablas.sql`.
2. Abrir el proyecto en NetBeans y hacer Clean and Build.
3. Darle Run.
4. Entrar a http://localhost

## Estado del avance

Hecho:

- HU-01 Registro de pacientes
- HU-05 Especialidades
- HU-06 Profesionales
- HU-07 Horarios de atención
- HU-09 Ver horarios libres
- HU-10 Agendar cita
- HU-11 Reagendar o cancelar cita
- HU-12 Confirmar, reprogramar o cancelar citas
- HU-14 Registrar llegada del paciente
- HU-15 Agenda del día del profesional
- HU-16 Marcar cita atendida o ausente
- HU-17 Notas clínicas
- HU-20 Registro de pagos
- HU-24 Cambio de idioma

Pendiente:

- Inicio de sesión, usuarios y roles (HU-02 y HU-04)
- HU-03, HU-08, HU-13, HU-18, HU-19, HU-21, HU-22, HU-23 y HU-25
