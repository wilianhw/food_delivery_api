CREATE TABLE usuario_grupo (
	usuario_id bigint NOT NULL,
	grupo_id bigint NOT NULL,

	PRIMARY KEY (usuario_id, grupo_id)
);

ALTER TABLE usuario_grupo ADD CONSTRAINT fk_usuario_grupo_usuario
FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE usuario_grupo ADD CONSTRAINT fk_usuario_grupo_grupo
FOREIGN KEY (grupo_id) REFERENCES grupo (id);