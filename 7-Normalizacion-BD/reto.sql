-- Tabla de entrada a normalizar hasta 3FN (no la cambies, es el punto de partida):
--
-- Matriculas(id_matricula, estudiante_nombre, estudiante_documento,
--            curso1_nombre, curso1_profesor, curso2_nombre, curso2_profesor)

-- ============================================================
-- TODO 1FN: elimina los grupos repetidos (curso1_*, curso2_*).
-- ============================================================
CREATE TABLE Matriculas (
    id_matricula INT PRIMARY KEY,
    estudiante_nombre VARCHAR(100),
    estudiante_documento VARCHAR(20)
);

CREATE TABLE MatriculaCursos (
    id_matricula INT,
    curso_nombre VARCHAR(100),
    profesor_nombre VARCHAR(100),

    PRIMARY KEY (id_matricula, curso_nombre),

    FOREIGN KEY (id_matricula)
        REFERENCES Matriculas(id_matricula)
);
--1FN: eliminé los grupos repetidos curso1_* y curso2_*, convirtiendo cada curso matriculado en un registro independiente.

-- ============================================================
-- TODO 2FN: revisa si hay dependencias parciales sobre una clave compuesta.
-- ============================================================
CREATE TABLE Matriculas (
    id_matricula INT PRIMARY KEY,
    estudiante_nombre VARCHAR(100),
    estudiante_documento VARCHAR(20)
);

CREATE TABLE Cursos (
    curso_nombre VARCHAR(100) PRIMARY KEY,
    profesor_nombre VARCHAR(100)
);

CREATE TABLE MatriculaCursos (
    id_matricula INT,
    curso_nombre VARCHAR(100),

    PRIMARY KEY (id_matricula, curso_nombre),

    FOREIGN KEY (id_matricula)
        REFERENCES Matriculas(id_matricula),

    FOREIGN KEY (curso_nombre)
        REFERENCES Cursos(curso_nombre)
);
--2FN: eliminé la dependencia parcial curso_nombre → profesor_nombre, dejando los datos propios del curso en Cursos.

-- ============================================================
-- TODO 3FN: separa cualquier dependencia transitiva (ej. datos del profesor
-- que dependen del profesor, no directamente de la matrícula/curso).
-- ============================================================

CREATE TABLE Estudiantes (
    estudiante_documento VARCHAR(20) PRIMARY KEY,
    estudiante_nombre VARCHAR(100)
);

CREATE TABLE Profesores (
    profesor_id INT PRIMARY KEY,
    profesor_nombre VARCHAR(100)
);

CREATE TABLE Cursos (
    curso_id INT PRIMARY KEY,
    curso_nombre VARCHAR(100),
    profesor_id INT,

    FOREIGN KEY (profesor_id)
        REFERENCES Profesores(profesor_id)
);

CREATE TABLE Matriculas (
    id_matricula INT PRIMARY KEY,
    estudiante_documento VARCHAR(20),

    FOREIGN KEY (estudiante_documento)
        REFERENCES Estudiantes(estudiante_documento)
);

CREATE TABLE MatriculaCursos (
    id_matricula INT,
    curso_id INT,

    PRIMARY KEY (id_matricula, curso_id),

    FOREIGN KEY (id_matricula)
        REFERENCES Matriculas(id_matricula),

    FOREIGN KEY (curso_id)
        REFERENCES Cursos(curso_id)
);
--3FN: separé Profesor de Curso para que los datos del profesor dependan únicamente de su propia clave y no de una matrícula o curso.

-- Resultado final esperado: tablas con PK/FK marcadas.
--  Estudiantes
--      │
--      │ 1:N
--      ▼
--  Matriculas
--      │
--      │ N:M
--      ▼
--  MatriculaCursos
--      │
--      │ N:1
--      ▼
--  Cursos
--      │
--      │ N:1
--      ▼
--  Profesores