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

-----             CREAMOS LAS TABLAS 

---														-------Maestros
-- Table: student
CREATE TABLE student (
    student_id int  IDENTITY (1,1),
    document_number char(9) UNIQUE,
    names varchar(65)  NOT NULL,
    lastname varchar(70)  NOT NULL,
    email varchar(120)  NOT NULL UNIQUE,
    semester char(2)  NOT NULL,
    career varchar(50)  NOT NULL,
    active char(1) DEFAULT ('A'),
    CONSTRAINT student_pk PRIMARY KEY  (student_id)
);

                             ----Validaciones---- 
ALTER TABLE student
	ADD CONSTRAINT document_number_student
	CHECK (document_number like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
GO

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
	ADD CONSTRAINT states_student 
	CHECK(active ='A' OR active ='I')
GO

ALTER TABLE student
	ADD CONSTRAINT career_student 
	CHECK(career ='Análisis de Sistemas' OR career ='Produción Agraria')
GO

ALTER TABLE student
	ADD CONSTRAINT semester_student 
	CHECK(semester ='I' OR semester ='II' OR semester ='III' OR semester ='IV' OR semester ='IV')
GO
-- Table: administrative
CREATE TABLE administrative (
    administrative_id int  IDENTITY (1,1),
    document_number char(9)  NOT NULL UNIQUE,
    names varchar(65)  NOT NULL,
    lastname varchar(70)  NOT NULL,
    email varchar(120)  NOT NULL UNIQUE,
    passwords varchar(50)  NOT NULL,
    active char(1)  NOT NULL,
    CONSTRAINT administrative_pk PRIMARY KEY  (administrative_id)
);
			----Validaciones---- 
ALTER TABLE administrative
	ADD CONSTRAINT administrative_student 
	CHECK(active ='A' OR active ='I')
GO

ALTER TABLE administrative
	ADD CONSTRAINT document_number_administrative
	CHECK (document_number like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
GO

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
							
											----transaccionales---
--- Table: Monthly_payment
CREATE TABLE Monthly_payment (
    id int  IDENTITY (1,1),
    active char(1)  NOT NULL,
    student_student_id int  NOT NULL,
    payment_id int  NOT NULL,
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
    type_payment varchar(40)  NOT NULL,
    amount decimal(10,2)  NOT NULL,
    dates  date  NOT NULL DEFAULT GETDATE(),
    reference_number varchar(28)  NOT NULL,
    term_time date  NOT NULL DEFAULT GETDATE(),
    states char(2)  NOT NULL,
    CONSTRAINT payment_pk PRIMARY KEY  (id)
);

							----Validaciones---- 


ALTER TABLE payment
	ADD CONSTRAINT states_payment
	CHECK(type_payment ='EFECTIVO' OR type_payment ='TARGETA')
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

