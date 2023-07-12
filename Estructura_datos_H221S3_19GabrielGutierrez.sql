-- Canbiamos el idioma
SET LANGUAGE Español
GO

-- Para poder eliminar la base de datos, cambiamos de base de datos.
USE master
GO

--Verificamos si ya existe la base de datos
DROP DATABASE IF EXISTS H221S3_19GabrielGutierrez
GO 

--Creamos la base de datos 
CREATE DATABASE H221S3_19GabrielGutierrez
GO

--Ponemos en uso la base de datos 
USE H221S3_19GabrielGutierrez
GO

--Configurar el formato de fecha 
SET DATEFORMAT dmy
GO

SELECT sysdatetime() as 'Fecha y  hora'
GO



															 --------MAESTRO1---------
														 -- Creamos la TABLA ESTUDIANTE --
CREATE TABLE student(
    student_id int IDENTITY(1,1),
	names varchar(60) NOT NULL,
	lastname varchar(60) NOT NULL,
	email varchar(120) NOT NULL UNIQUE,
    document_type char(3) DEFAULT ('DNI'),
    document_number char(9) NOT NULL UNIQUE,
	semester char(5)  NOT NULL,
    career varchar(50)  NOT NULL,
    active char(1) DEFAULT ('A'),
    CONSTRAINT student_pk PRIMARY KEY (student_id)
)
GO

					--RESTRICCIONES

-- agregamos Restriccion de solo letras en nobre y apellido
ALTER TABLE student
	ADD CONSTRAINT names_student
	CHECK (names NOT LIKE '%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%')
GO
ALTER TABLE student
	ADD CONSTRAINT lastname_student
	CHECK (lastname NOT LIKE '%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%')
GO

ALTER TABLE student
	ADD CONSTRAINT email_student
    CHECK (email LIKE '%@gmail.com' OR email LIKE '%@hotmail.com' OR email LIKE '%@outlook.com' OR email LIKE '%@yahoo.com' OR email LIKE '%@vallegrande.edu.pe')
GO

ALTER TABLE student
	ADD CONSTRAINT document_number_student
	CHECK (document_number like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][^A-Z]')
GO

ALTER TABLE student
	ADD CONSTRAINT states_student 
	CHECK(active ='A' OR active ='I')
GO

---No permite eliminar un registro si esta activo con disparador trigger
-- Crear el disparador (trigger)
CREATE TRIGGER active_student_trigger
ON student
INSTEAD OF DELETE
AS
BEGIN
    -- Verificar si hay intento de eliminar un registro activo
    IF EXISTS (
        SELECT * FROM deleted WHERE active = 'A'
    )
    BEGIN
        RAISERROR ('No se permite eliminar registros activos.', 16, 1)
        ROLLBACK TRANSACTION
        RETURN
    END
    ELSE
    BEGIN
        DELETE FROM student
        WHERE student_id IN (SELECT student_id FROM deleted)
    END
END
GO
-- Table: administrative
CREATE TABLE administrative(
    administrative_id int IDENTITY(1,1),
	names varchar(60) NOT NULL,
	lastname varchar(60) NOT NULL,
	email varchar(120) NOT NULL UNIQUE,
    document_type char(3) DEFAULT ('DNI'),
    document_number char(9) NOT NULL UNIQUE,
	passwords varchar(50)  NOT NULL,
	active char(1) DEFAULT ('A')
    CONSTRAINT administrative_pk PRIMARY KEY (administrative_id)
)
GO

					--RESTRICCIONES

-- agregamos Restriccion de solo letras en nobre y apellido
ALTER TABLE administrative
	ADD CONSTRAINT names_administrative
	CHECK (names NOT LIKE '%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%')
GO
ALTER TABLE administrative
	ADD CONSTRAINT lastname_administrative
	CHECK (lastname NOT LIKE '%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%[^a-zA-Z]%')
GO

ALTER TABLE administrative
	ADD CONSTRAINT email_administrative
    CHECK (email LIKE '%@gmail.com' OR email LIKE '%@hotmail.com' OR email LIKE '%@outlook.com' OR email LIKE '%@yahoo.com' OR email LIKE '%@vallegrande.edu.pe')
GO

ALTER TABLE administrative
	ADD CONSTRAINT document_number_administrative
	CHECK (document_number like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][^A-Z]')
GO

ALTER TABLE administrative
	ADD CONSTRAINT states_administrative 
	CHECK(active ='A' OR active ='I')
GO
							
											----transaccionales---
--- Table: Monthly_payment
CREATE TABLE Monthly_payment (
    id int  IDENTITY (1,1),
    student_student_id int  NOT NULL,
    payment_id int  NOT NULL,
	active char(1)  DEFAULT ('A'),
    CONSTRAINT Monthly_payment_pk PRIMARY KEY  (id)
);

							----Validaciones---- 

ALTER TABLE Monthly_payment
	ADD CONSTRAINT administrative_Monthly_payment 
	CHECK(active ='A' OR active ='I')
GO

-- Table: payment
CREATE TABLE payment (
    id int IDENTITY (1,1),
    type_payment char(1),
    amount decimal(10,2)  NOT NULL,
    dates  date  NOT NULL DEFAULT GETDATE(),
    reference_number char(5)  NOT NULL,
    term_time date  NOT NULL DEFAULT GETDATE(),
    states char(1) DEFAULT ('P'),
    CONSTRAINT payment_pk PRIMARY KEY  (id)
);

							----Validaciones---- 


ALTER TABLE payment
	ADD CONSTRAINT type_payment
	CHECK(type_payment ='E' OR type_payment ='T')  ---E=efectivo T=targeta
GO

ALTER TABLE payment
	ADD CONSTRAINT states_payment
	CHECK(states ='C' OR states ='D' OR states ='P')  ---C=Cancelado D=Deuda P=PENDIENTE
GO


				----------- insertar datos  ----------------
				------administrative---
INSERT INTO administrative
		(names, lastname,email, document_number,passwords)
VALUES
		('Henry Gustavo', 'Hinostroza Gutierrez','ghistroza@vallegrande.edu.pe', '56237238','henry2023'),
		('Gabriel Esteban', 'Santos Pablo','spablo@vallegrande.edu.pe', '52738749','santos2023'),
		('Luis Owen', 'Huaman Bermat','lhuman@vallegrande.edu.pe', '67238932','luis2023'),
		('Sebastian', 'Hinostroza Luyo','shinostroza@vallegrande.edu.pe', '43749501','sebastian2023')

	GO		
-- Listamos la tabla estudiante.
SELECT * FROM administrative
GO

				------student---
INSERT INTO student
		(names, lastname,email, document_number,semester,career)
VALUES
		('Jose Megun', 'Cama la madrid','jose.cama@gmail.com', '73931475','I','Análisis de Sistemas'),
		('Gabriel Esteban', 'Gutierrez Quispe','gabriel.gutierrez@gmail.com', '56743973','II','Análisis de Sistemas'),
		('Mario luis', 'Flores Huaman','mario.flores@gmail.com', '67548475','I','Produción Agraria'),
		('Daniel Sebastian', 'Santa cruz Hinostroza','daniel.santa@gmail.com', '64892056','I','Análisis de Sistemas'),
		('Fernando Paolo', 'Rodriguez Mendoza','fernando.rodriguez@gmail.com', '32674896','I','Produción Agraria')

	GO	
-- Listamos la tabla administrative.
SELECT * FROM student
GO

							------Pago---
INSERT INTO payment
		(type_payment, amount, reference_number, states)
VALUES
		('E', 400.00, '00001', 'C'),
		('T', 400.00, '00002', 'C'),
		('E', 400.00, '00003', 'C'),
		('E', 400.00, '00004', 'C'),
		('E', 400.00, '00005', 'C')

	GO	
-- Listamos la tabla administrative.
SELECT * FROM payment
GO

							------mensualidad---
INSERT INTO Monthly_payment
		(student_student_id, payment_id)
VALUES
		('1', '1'),
		('2', '2'),
		('3', '3'),
		('4', '4'),
		('5', '5')
GO	
-- Listamos la tabla Monthly_payment.
SELECT * FROM Monthly_payment
GO
			-----LISTAR--
---STUDENT
CREATE VIEW List_Student AS
SELECT student_id AS 'ID', CONCAT(lastname, ' ', names) AS 'ESTUDIANTE', email AS 'CORREO', document_type AS 'T DOCUMENTO', document_number AS 'N° DOCUMENTO', semester AS 'SEMESTRE', career AS 'CARRERA', active AS 'ESTADO'
FROM student;
GO

SELECT * FROM List_Student
GO

---ADMINISTRATIVE
CREATE VIEW List_Administrative AS
SELECT administrative_id AS 'ID', CONCAT(lastname, ' ', names) AS 'TESORERO', email AS 'CORREO', document_type AS 'T DOCUMENTO', document_number AS 'N° DOCUMENTO',passwords AS 'CONTRASEÑA', active AS 'ESTADO'
FROM administrative;
GO

SELECT * FROM List_Administrative
GO

---- Pago
CREATE VIEW List_payment AS
SELECT
    id AS ID,
    CASE 
        WHEN type_payment = 'E' THEN 'Efectivo'
        WHEN type_payment = 'T' THEN 'Tarjeta'
    END AS "Tipo Pago",
    CONCAT('S/ ', ROUND(amount * 1, 2)) AS CANTIDAD,
    dates AS "FEC PAGO",
    reference_number AS "N° PAGO",
    term_time AS "ULTIMO PLAZO",
    CASE 
        WHEN states = 'C' THEN 'Cancelado'
        WHEN states = 'D' THEN 'Deuda'
        WHEN states = 'P' THEN 'Pendiente'
    END AS ESTADO
FROM payment;
GO

SELECT * FROM List_payment
GO

CREATE VIEW List_Mensualidad AS
SELECT
    mp.id,
	s.document_number AS 'N° DOCUMENTO',
    CONCAT(s.names, ' ', s.lastname) AS Estudiante,
    CONCAT(' S/', p.amount) AS Cantidad,
    p.dates AS "FEC PAGO",
    CASE 
        WHEN mp.active = 'A' THEN 'Activo'
        WHEN mp.active = 'I' THEN 'Inactivo'
    END AS Estado
FROM Monthly_payment mp
JOIN Student s ON s.student_id = mp.student_student_id
JOIN Payment p ON p.id = mp.payment_id;
GO

SELECT * FROM List_Mensualidad
GO
-- foreign keys
-- Reference: Monthly_payment_payment (table: Monthly_payment)
ALTER TABLE Monthly_payment ADD CONSTRAINT Monthly_payment_payment
    FOREIGN KEY (payment_id)
    REFERENCES payment (id);

-- Reference: Monthly_payment_student (table: Monthly_payment)
ALTER TABLE Monthly_payment ADD CONSTRAINT Monthly_payment_student
    FOREIGN KEY (student_student_id)
    REFERENCES student (student_id);

-- End of file.
