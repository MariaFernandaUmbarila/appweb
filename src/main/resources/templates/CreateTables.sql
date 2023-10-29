CREATE TABLE materia (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre varchar,
    creditos integer
);

CREATE TABLE persona (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre varchar,
    apellido varchar,
    correo varchar,
    rol char(1) DEFAULT 'E'
);

CREATE TABLE curso (
    materia_id integer NOT NULL,
    profesor_id integer NOT NULL,
    codigo varchar,
    estudiante_id integer,
    fecha_inicio date,
    fecha_fin date,
    PRIMARY KEY (materia_id, profesor_id, numero)
);

CREATE TABLE nota (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    materia_id integer NOT NULL,
    profesor_id integer NOT NULL,
    curso_codigo varchar NOT NULL,
    estudiante_id integer NOT NULL,
    observacion varchar,
    valor numeric(3, 2),
    porcentaje numeric(4,2)
);

ALTER TABLE persona ADD CHECK (rol in ( 'E', 'P'));
ALTER TABLE curso ADD FOREIGN KEY (estudiante_id) REFERENCES persona (id);
ALTER TABLE curso ADD FOREIGN KEY (profesor_id) REFERENCES persona (id);
ALTER TABLE curso ADD FOREIGN KEY (materia_id) REFERENCES materia (id);
ALTER TABLE nota ADD FOREIGN KEY (materia_id, professor_id, curso_codigo) REFERENCES curso
    ((materia_id, profesor_id, numero);
ALTER TABLE nota ADD FOREIGN KEY (estudiante_id) REFERENCES persona (id);

CREATE VIEW estudiante AS
SELECT id, nombre, apellido, correo
FROM persona
WHERE rol = 'E';

CREATE VIEW profesor AS
SELECT id, nombre, apellido, correo
FROM persona
WHERE rol = 'P';