-- DROP TABLE algafood.cidade;

CREATE TABLE algafood.cidade (
	id bigserial NOT NULL,
	nome_cidade varchar(80) NOT NULL,
	nome_estado varchar(80) NOT NULL,

	PRIMARY KEY (id)
);
