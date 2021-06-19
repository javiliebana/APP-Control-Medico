--
-- BORRAR BBDD
--
DROP DATABASE IF EXISTS PRMEDICAPP;

--
-- CREAR BBDD
--
CREATE DATABASE PRMEDICAPP
CHARACTER SET utf8
COLLATE utf8_spanish_ci;

USE PRMEDICAPP;

--
-- Creacion de tablas
--
CREATE TABLE Usuario (
  ID_USUARIO 	INT 		AUTO_INCREMENT,
  USERNAME 		VARCHAR(60) NOT NULL,
  PASSWORD 		VARCHAR(60) NOT NULL,
  NOMBRE 		VARCHAR(60) NOT NULL,
  APELLIDO 		VARCHAR(60) NOT NULL,
  TELEFONO 		VARCHAR(60) NOT NULL,
  DNI 			VARCHAR(60) NOT NULL,
  ROL 			VARCHAR(60) NOT NULL,
  CONSTRAINT	PK_Usuario					PRIMARY KEY (ID_USUARIO)
)ENGINE=INNODB;

CREATE TABLE Medico (
  ID_MEDICO	INT			AUTO_INCREMENT,
  ID_USUARIO 	INT 		NULL,
  CONSTRAINT	PK_Medico				PRIMARY KEY (ID_MEDICO),
  CONSTRAINT 	FK_Usuario2				FOREIGN KEY (ID_USUARIO)
				REFERENCES 					Usuario	(ID_USUARIO)
				ON DELETE CASCADE
				ON UPDATE CASCADE
)ENGINE=INNODB;

CREATE TABLE Paciente (
  ID_PACIENTE	INT			AUTO_INCREMENT,
  ID_USUARIO 	INT 		NULL,
  ID_MEDICO 	INT 		NULL,
  DESCRIPCION 		VARCHAR(1250) NOT NULL,
  CONSTRAINT	PK_Paciente				PRIMARY KEY (ID_PACIENTE),
  CONSTRAINT 	FK_Medico			FOREIGN KEY (ID_MEDICO)
				REFERENCES 					Medico	(ID_MEDICO),
  CONSTRAINT 	FK_Usuario3			FOREIGN KEY (ID_USUARIO)
				REFERENCES 					Usuario	(ID_USUARIO)
				ON DELETE CASCADE
				ON UPDATE CASCADE
)ENGINE=INNODB;

CREATE TABLE Familiar (
  ID_FAMILIAR 	INT			AUTO_INCREMENT,
  ID_USUARIO	INT 		NULL,
  ID_PACIENTE	INT 		NULL,
  CONSTRAINT	PK_Familiar				PRIMARY KEY (ID_FAMILIAR),
  CONSTRAINT 	FK_Usuario				FOREIGN KEY (ID_USUARIO)
				REFERENCES 					Usuario	(ID_USUARIO),
  CONSTRAINT 	FK_Paciente0				FOREIGN KEY (ID_PACIENTE)
				REFERENCES 					Paciente	(ID_PACIENTE)
				ON DELETE CASCADE
				ON UPDATE CASCADE
)ENGINE=INNODB;

CREATE TABLE Historial_Medico (
  ID_HISTORIA 	INT			AUTO_INCREMENT,
  ID_PACIENTE	INT 		NULL,
  DESCRIPCION 		VARCHAR(1250) NOT NULL,
  FECHA_EVENTO DATE NOT NULL,
  CONSTRAINT	PK_Familiar				PRIMARY KEY (ID_HISTORIA),
  CONSTRAINT 	FK_Paciente				FOREIGN KEY (ID_PACIENTE)
				REFERENCES 					Paciente	(ID_PACIENTE)
				ON DELETE CASCADE
				ON UPDATE CASCADE
)ENGINE=INNODB;

CREATE TABLE Temperatura (
  ID_TEMPERATURA 	INT			AUTO_INCREMENT,
  ID_PACIENTE	INT 		NULL,
  FECHA_TEMP 	DATETIME  DEFAULT current_timestamp(),
  TEMPERATURA 			VARCHAR(10) NULL,
  HUMEDAD 		VARCHAR(10) NULL,
  CONSTRAINT	PK_Familiar				PRIMARY KEY (ID_TEMPERATURA),
  CONSTRAINT 	FK_Paciente2				FOREIGN KEY (ID_PACIENTE)
				REFERENCES 					Paciente	(ID_PACIENTE)
				ON DELETE CASCADE
				ON UPDATE CASCADE
)ENGINE=INNODB;

CREATE TABLE Movimiento (
  ID_MOVIMIENTO 	INT			AUTO_INCREMENT,
  ID_PACIENTE	INT 		NULL,
  FECHA_EVENTO DATETIME  DEFAULT current_timestamp(),
  SENSOR 		VARCHAR(55) NOT NULL,
  CONSTRAINT	PK_Familiar				PRIMARY KEY (ID_MOVIMIENTO),
  CONSTRAINT 	FK_Paciente3				FOREIGN KEY (ID_PACIENTE)
				REFERENCES 					Paciente	(ID_PACIENTE)
				ON DELETE CASCADE
				ON UPDATE CASCADE
)ENGINE=INNODB;


CREATE TABLE Chat (
  ID_CHAT 	INT 		AUTO_INCREMENT,
  ID_USER_MEDIC INT,
  ID_USER INT,
  CONSTRAINT	PK_Chat					PRIMARY KEY (ID_CHAT)
)ENGINE=INNODB;

CREATE TABLE Mensaje (
  ID_MSG 	INT 		AUTO_INCREMENT,
  ID_CHAT INT,
  USERNAME_SEND VARCHAR(60) NOT NULL,
  MSG VARCHAR(1024) NOT NULL,
  MSG_FECHA 		DATETIME NOT NULL DEFAULT current_timestamp(),
  CONSTRAINT	PK_Mensaje					PRIMARY KEY (ID_MSG),
  CONSTRAINT 	FK_Chat2				FOREIGN KEY (ID_CHAT)
				REFERENCES 					Chat	(ID_CHAT)
				ON DELETE CASCADE
				ON UPDATE CASCADE
)ENGINE=INNODB;


INSERT INTO Usuario values (1,'pepe','1234','Jose','Perez','657345077','3567912V','M');
INSERT INTO Usuario values (2,'rosa','1234','Rosa','Undarigazpi','639301833','34964912X','F');
INSERT INTO Usuario values (3,'javi','1234','Javier','Liebana','647943788','15348677D','P');
INSERT INTO Medico values (1,1);
INSERT INTO Paciente values (1,3,1,'El paciente es un joven de 22 años invidente, también presenta alergía multiples plantas primaverales, cada años debe someterse a un control en dicha época para evitar riesgos.');
INSERT INTO Familiar values (1,2,1);



INSERT INTO Usuario values (4,'ana','1234','Ana','Lopez','642893111','17563489F','P');
INSERT INTO Usuario values (5,'paco','1234','Francisco','Gutierrez','687345980','45337845L','P');
INSERT INTO Paciente values (2,4,1,'La paciente es una mujer de 83 años, ha sufrido multiples bajadas de tensíon a lo largo de 2019 y debe someterse a múltiples pruebas para concer la causa.');
INSERT INTO Paciente values (3,5,1,'El paciente es un hombre de 72 años que se rompió el fémur derecho, se procedió a la operación y ahora realiza rehabilitacion en el centro Santa María de Alcarcón');

INSERT INTO Historial_Medico (ID_PACIENTE,DESCRIPCION,FECHA_EVENTO) values(1,'Se ha instalado la apliacion MedicApp en el hogar del paciente','2020-05-19');
INSERT INTO Temperatura (ID_TEMPERATURA,ID_PACIENTE,TEMPERATURA,HUMEDAD)values (1,1,'34','8');
INSERT INTO Movimiento(ID_MOVIMIENTO,ID_PACIENTE,SENSOR) values (1,1,'Puerta Exterior');


INSERT INTO Chat (ID_USER_MEDIC,ID_USER) values (1,3);
INSERT INTO Chat (ID_USER_MEDIC,ID_USER) values (1,2);
INSERT INTO Chat (ID_USER_MEDIC,ID_USER) values (1,4);
INSERT INTO Mensaje (ID_CHAT,USERNAME_SEND,MSG) values (1,'pepe','Hola, buenos dias. ¿Como se encuentra hoy?');
INSERT INTO Mensaje (ID_CHAT,USERNAME_SEND,MSG) values (1,'javi','Buenos días doctor, bastante mejor que ayer, como verá la humedad del hogar es la perfecta según me comento ayer en consulta.');
INSERT INTO Mensaje (ID_CHAT,USERNAME_SEND,MSG) values (2,'rosa','Hola, ¿podría pasarme hoy a recoger la radiografía de mi padre? ');
INSERT INTO Mensaje (ID_CHAT,USERNAME_SEND,MSG) values (2,'pepe','Buenos días, por supuesto, acerquese sobre las 18:00 y le atenderé encantado.');


INSERT INTO Usuario values (6,'dani','1234','Jose','Perez','674123677','58431254S','P');
INSERT INTO Usuario values (7,'julio','1234','Julio','Perez','674121322','87556312K','P');
INSERT INTO Usuario values (8,'maria','1234','María','Sánchez','648677322','43781207L','P');

INSERT INTO Paciente values (4,6,1,'El paciente es un varón de 56 años que presenta síntomas leves de desgaste en las rodillas.');
INSERT INTO Paciente values (5,7,1,'El paciente es un varón de 73 y 1,65 metros de altura que acarrea una enfermedad crónica del pulmón.');
INSERT INTO Paciente values (6,8,1,'La paciente es una mujer de 91 años de edad que presenta una fuerte alergía a los frutos secos.');
INSERT INTO Historial_Medico (ID_PACIENTE,DESCRIPCION,FECHA_EVENTO) values(4,'El paciene presenta una fuerte tos y fiebre afirma haber tenido fiebre superior a los 40º, se le ha recetado Turministol 15g','2020-05-19');
INSERT INTO Historial_Medico (ID_PACIENTE,DESCRIPCION,FECHA_EVENTO) values(5,'El paciene presenta una fuerte tos y fiebre afirma haber tenido fiebre superior a los 40º, se le ha recetado Turministol 15g','2020-05-19');
INSERT INTO Historial_Medico (ID_PACIENTE,DESCRIPCION,FECHA_EVENTO) values(6,'El paciene presenta una fuerte tos y fiebre afirma haber tenido fiebre superior a los 40º, se le ha recetado Turministol 15g','2020-05-19');
INSERT INTO Historial_Medico (ID_PACIENTE,DESCRIPCION,FECHA_EVENTO) values(1,'El paciene presenta una fuerte tos y fiebre afirma haber tenido fiebre superior a los 40º, se le ha recetado Turministol 15g','2020-05-19');
INSERT INTO Historial_Medico (ID_PACIENTE,DESCRIPCION,FECHA_EVENTO) values(1,'Se va a proceder a la instalacion de MedicApp en su  hogar para poder monitorizar la calidad del vida del pacente.','2020-05-22');



