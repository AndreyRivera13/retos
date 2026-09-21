-- Tabla de entrada a normalizar hasta 3FN (no la cambies, es el punto de partida):
--
-- Matriculas(id_matricula, estudiante_nombre, estudiante_documento,
--            curso1_nombre, curso1_profesor, curso2_nombre, curso2_profesor)

-- ============================================================
-- TODO 1FN: elimina los grupos repetidos (curso1_*, curso2_*).
-- ============================================================
-- CREATE TABLE ...

-- ============================================================
-- TODO 2FN: revisa si hay dependencias parciales sobre una clave compuesta.
-- ============================================================
-- CREATE TABLE ...

-- ============================================================
-- TODO 3FN: separa cualquier dependencia transitiva (ej. datos del profesor
-- que dependen del profesor, no directamente de la matrícula/curso).
-- ============================================================
-- CREATE TABLE ...

-- Resultado final esperado: tablas con PK/FK marcadas.
