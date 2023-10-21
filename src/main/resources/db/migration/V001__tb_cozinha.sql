-- algafood.cozinha definition

-- Drop table

-- DROP TABLE algafood.cozinha;

CREATE TABLE algafood.cozinha (
	id bigserial NOT NULL,
	nome varchar(255) NULL,
	CONSTRAINT cozinha_pkey PRIMARY KEY (id)
);
