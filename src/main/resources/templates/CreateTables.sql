CREATE TABLE materia (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre varchar,
    creditos integer
);

CREATE TABLE estudiante (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre varchar,
    apellido varchar,
    correo varchar
);

CREATE TABLE profesor (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre varchar,
    apellido varchar,
    correo varchar
);

CREATE TABLE curso (
    codigo varchar NOT NULL PRIMARY KEY,
    materia_id integer NOT NULL,
    profesor_id integer NOT NULL,
    fecha_inicio date,
    fecha_fin date,
    FOREIGN KEY (materia_id) references materia(id),
    FOREIGN KEY (profesor_id) REFERENCES profesor(id)
);

CREATE TABLE estudiante_detalle (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    curso_codigo varchar NOT NULL,
    estudiante_id integer NOT NULL,
    observacion varchar,
    valor numeric(3,2),
    porcentaje numeric(4,2),
    FOREIGN KEY (curso_codigo) references curso(codigo),
    FOREIGN KEY (estudiante_id) references estudiante(id)
);

select en.curso_codigo, ma.nombre, sum(en.valor)
from estudiante_detalle en
         inner join curso cu on(en.curso_codigo = cu.codigo)
         inner join materia ma on(cu.materia_id = ma.id)
where estudiante_id = 1
group by en.curso_codigo, ma.nombre;