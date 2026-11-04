drop database if exists citas_clinicas;
drop user if exists usuario_citas;

CREATE database citas_clinicas
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

create user 'usuario_citas'@'%' identified by 'Usuar1o_Clave.';

grant select, insert, update, delete on citas_clinicas.* to 'usuario_citas'@'%';
flush privileges;

use citas_clinicas;

create table especialidad (
  id_especialidad INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(50) NOT NULL,
  activo boolean NOT NULL DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_especialidad),
  unique (descripcion))
  ENGINE = InnoDB;

create table profesional (
  id_profesional INT NOT NULL AUTO_INCREMENT,
  id_especialidad INT NOT NULL,
  nombre VARCHAR(30) NOT NULL,
  apellidos VARCHAR(40) NOT NULL,
  cedula VARCHAR(20) NOT NULL,
  correo VARCHAR(75) NOT NULL,
  telefono VARCHAR(25),
  activo boolean NOT NULL DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_profesional),
  unique (cedula),
  foreign key fk_profesional_especialidad (id_especialidad) references especialidad(id_especialidad))
  ENGINE = InnoDB;

create table paciente (
  id_paciente INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(30) NOT NULL,
  apellidos VARCHAR(40) NOT NULL,
  cedula VARCHAR(20) NOT NULL,
  correo VARCHAR(75) NOT NULL,
  telefono VARCHAR(25),
  activo boolean NOT NULL DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_paciente),
  unique (cedula),
  unique (correo))
  ENGINE = InnoDB;

create table disponibilidad (
  id_disponibilidad INT NOT NULL AUTO_INCREMENT,
  id_profesional INT NOT NULL,
  fecha DATE NOT NULL,
  hora_inicio TIME NOT NULL,
  hora_fin TIME NOT NULL,
  disponible boolean NOT NULL DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CHECK (hora_fin > hora_inicio),
  PRIMARY KEY (id_disponibilidad),
  index ndx_profesional_fecha (id_profesional, fecha),
  foreign key fk_disponibilidad_profesional (id_profesional) references profesional(id_profesional))
  ENGINE = InnoDB;

create table cita (
  id_cita INT NOT NULL AUTO_INCREMENT,
  id_paciente INT NOT NULL,
  id_disponibilidad INT NOT NULL,
  motivo VARCHAR(150),
  estado ENUM('PENDIENTE', 'CONFIRMADA', 'EN_CURSO', 'ATENDIDA', 'CANCELADA', 'AUSENTE') NOT NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_cita),
  index ndx_paciente (id_paciente),
  index ndx_disponibilidad (id_disponibilidad),
  foreign key fk_cita_paciente (id_paciente) references paciente(id_paciente),
  foreign key fk_cita_disponibilidad (id_disponibilidad) references disponibilidad(id_disponibilidad))
  ENGINE = InnoDB;

create table nota_clinica (
  id_nota INT NOT NULL AUTO_INCREMENT,
  id_cita INT NOT NULL,
  descripcion TEXT NOT NULL,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_nota),
  unique (id_cita),
  foreign key fk_nota_cita (id_cita) references cita(id_cita))
  ENGINE = InnoDB;

create table pago (
  id_pago INT NOT NULL AUTO_INCREMENT,
  id_cita INT NOT NULL,
  monto DECIMAL(12,2) NOT NULL CHECK (monto > 0),
  metodo ENUM('EFECTIVO', 'TARJETA', 'SINPE') NOT NULL,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_pago),
  unique (id_cita),
  foreign key fk_pago_cita (id_cita) references cita(id_cita))
  ENGINE = InnoDB;

INSERT INTO especialidad (descripcion,activo) VALUES
('Medicina General', true),
('Odontología', true),
('Pediatría', true),
('Psicología', true);

INSERT INTO profesional (id_especialidad,nombre,apellidos,cedula,correo,telefono,activo) VALUES
(1,'Andrea','Solís Mora','1-1111-1111','asolis@citasclinicas.com','8888-1111',true),
(2,'Carlos','Jiménez Rojas','2-2222-2222','cjimenez@citasclinicas.com','8888-2222',true),
(3,'Mariana','Vargas Castro','3-3333-3333','mvargas@citasclinicas.com','8888-3333',true),
(4,'Luis','Fernández Alpízar','4-4444-4444','lfernandez@citasclinicas.com','8888-4444',true);

INSERT INTO paciente (nombre,apellidos,cedula,correo,telefono,activo) VALUES
('Juan','Castro Mora','1-0101-0101','jcastro@gmail.com','4556-8978',true),
('Rebeca','Contreras Mora','1-0202-0202','rcontreras@gmail.com','5456-8789',true),
('Pedro','Mena Loría','1-0303-0303','pmena@gmail.com','7898-8936',true),
('Sofía','Ramírez Ulate','1-0404-0404','sramirez@gmail.com','6123-4567',true);

INSERT INTO disponibilidad (id_profesional,fecha,hora_inicio,hora_fin,disponible) VALUES
(1, CURDATE(), '08:00', '08:30', false),
(1, CURDATE(), '08:30', '09:00', false),
(1, CURDATE(), '09:00', '09:30', true),
(1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '08:00', '08:30', true),
(2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:00', '10:30', false),
(2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:30', '11:00', true),
(3, DATE_ADD(CURDATE(), INTERVAL 2 DAY), '13:00', '13:30', true),
(3, DATE_ADD(CURDATE(), INTERVAL 2 DAY), '13:30', '14:00', true),
(4, DATE_ADD(CURDATE(), INTERVAL 3 DAY), '15:00', '16:00', true),
(4, DATE_ADD(CURDATE(), INTERVAL 3 DAY), '16:00', '17:00', true);

INSERT INTO cita (id_paciente,id_disponibilidad,motivo,estado) VALUES
(1, 1, 'Control general', 'CONFIRMADA'),
(2, 2, 'Dolor de cabeza frecuente', 'PENDIENTE'),
(3, 5, 'Limpieza dental', 'PENDIENTE');
