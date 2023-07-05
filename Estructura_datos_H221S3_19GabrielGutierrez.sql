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
    semester char(3)  NOT NULL,
    career varchar(50)  NOT NULL,
    active char(1) DEFAULT ('A'),
    CONSTRAINT student_pk PRIMARY KEY  (student_id)
);

                             ----Validaciones---- 
ALTER TABLE student
	ADD CONSTRAINT document_number_student
	CHECK (document_number like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][^A-Z]')
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
-- Table: administrative
CREATE TABLE administrative (
    administrative_id int  IDENTITY (1,1),
    document_number char(9)  NOT NULL UNIQUE,
    names varchar(65)  NOT NULL,
    lastname varchar(70)  NOT NULL,
    email varchar(120)  NOT NULL UNIQUE,
    passwords varchar(50)  NOT NULL,
    active char(1) DEFAULT ('A'),
    CONSTRAINT administrative_pk PRIMARY KEY  (administrative_id)
);
			----Validaciones---- 
ALTER TABLE administrative
	ADD CONSTRAINT administrative_student 
	CHECK(active ='A' OR active ='I')
GO

ALTER TABLE administrative
	ADD CONSTRAINT document_number_administrative
	CHECK (document_number like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][^A-Z]')
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
    states char(2),
    CONSTRAINT payment_pk PRIMARY KEY  (id)
);

							----Validaciones---- 


ALTER TABLE payment
	ADD CONSTRAINT type_payment
	CHECK(type_payment ='E' OR type_payment ='T')  ---E=efectivo T=targeta
GO

ALTER TABLE payment
	ADD CONSTRAINT states_payment
	CHECK(states ='C' OR states ='D')  ---C=Cancelado D=Deuda
GO


				----------- insertar datos  ----------------
				------student---
INSERT INTO administrative
		(document_number,names, lastname, email, passwords)
VALUES
		('76007178','Ariel','Landa Garrido','ariel.lamda@vallegrande.edu.pe','ariel2023'),
		('75162542','Rosa','Rodriguez Ovidio','rosa.rodrigues@vallegrande.edu.pe','rosa2023'),
		('73830737','Patricia','Cuenca Izaguirre','patricia.cuenca@vallegrande.edu.pe','patricia2023'),
		('75165597','Rosendo','Baena Córdoba','rosendo.baena@vallegrande.edu.pe','rose3ndo2023'),
		('71997493','Claudia','Jurado Carpio','cludia.jurado@vallegrande.edu.pe','claudia2023'),
		('72893577','Pascual','Zabaleta Lombardi','pascual.zabaleta@vallegrande.edu.pe','pascual2023'),
		('70145327','Silvio','Verdejo Campusano','silvio.verdejo@vallegrande.edu.pe','silvio2023'),
		('71806086','Ezequiel','Jódar-Noriega Marqués','ezequiel.jorda@vallegrande.edu.pe','adelardo2023'),
		('75261202','Adelardo','Novoa Carrión','adelardo.novoa@vallegrande.edu.pe','ezequiel2023'),
		('70905459','Benjamín','Santana Calvo','benjamin.santa@vallegrande.edu.pe','benjamin2023'),
		('72308430','Reyes','Barroso Padilla','reyes.barrosa@vallegrande.edu.pe','reyes2023'),
		('23444355','Xiomara','Cañas Gordillo','xiomara.cañas@vallegrande.edu.pe','xiomara2023')

	GO	
-- Listamos la tabla estudiante.
SELECT * FROM administrative

				------administrative---
INSERT INTO student
		(document_number,names, lastname, email, semester, career)
VALUES
		('76007178','Ariel','Landa Garrido','ariel.lamda@vallegrande.edu.pe','II','Análisis de Sistemas'),
		('75162542','Rosa','Rodriguez Ovidio','rosa.rodrigues@vallegrande.edu.pe','I','Produción Agraria'),
		('73830737','Patricia','Cuenca Izaguirre','patricia.cuenca@vallegrande.edu.pe','IV','Análisis de Sistemas'),
		('75165597','Rosendo','Baena Córdoba','rosendo.baena@vallegrande.edu.pe','I','Producción Agraria'),
		('71997493','Claudia','Jurado Carpio','cludia.jurado@vallegrande.edu.pe','II','Producción Agraria'),
		('72893577','Pascual','Zabaleta Lombardi','pascual.zabaleta@vallegrande.edu.pe','II','Producción Agraria'),
		('70145327','Silvio','Verdejo Campusano','silvio.verdejo@vallegrande.edu.pe','I','Análisis de Sistemas'),
		('71806086','Ezequiel','Jódar-Noriega Marqués','ezequiel.jorda@vallegrande.edu.pe','IV','Análisis de Sistemas'),
		('75261202','Adelardo','Novoa Carrión','adelardo.novoa@vallegrande.edu.pe','IV','Producción Agraria'),
		('70905459','Benjamín','Santana Calvo','benjamin.santa@vallegrande.edu.pe','V','Análisis de Sistemas'),
		('72308430','Reyes','Barroso Padilla','reyes.barrosa@vallegrande.edu.pe','I','Producción Agraria'),
		('23444355','Xiomara','Cañas Gordillo','xiomara.cañas@vallegrande.edu.pe','IV','Producción Agraria')

	GO	
-- Listamos la tabla estudiante.
SELECT * FROM student


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

