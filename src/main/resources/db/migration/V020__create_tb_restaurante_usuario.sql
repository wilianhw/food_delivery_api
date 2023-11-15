CREATE TABLE restaurante_usuario_responsavel (
	restaurante_id bigint NOT NULL,
	usuario_id bigint NOT NULL,

	PRIMARY KEY (restaurante_id, usuario_id)
);

ALTER TABLE restaurante_usuario_responsavel ADD CONSTRAINT fk_restaurante_usuario_responsavel_restaurante
FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);

ALTER TABLE restaurante_usuario_responsavel ADD CONSTRAINT fk_restaurante_usuario_responsavel_usuario
FOREIGN KEY (usuario_id) REFERENCES usuario (id);