-- DROP TABLE algafood.cozinha;

CREATE TABLE algafood.cozinha (
	id bigserial NOT NULL,
	nome varchar(255) NOT NULL,
	CONSTRAINT cozinha_pkey PRIMARY KEY (id)
);
