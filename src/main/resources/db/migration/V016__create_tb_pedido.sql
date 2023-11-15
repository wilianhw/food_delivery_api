CREATE TABLE pedido (
	id bigserial NOT NULL,
	subtotal decimal(10, 2) NOT NULL,
	taxa_frete decimal(10, 2) NOT NULL,
	valor_total decimal(10, 2) NOT NULL,

	restaurante_id bigint NOT NULL,
	usuario_id bigint NOT NULL,
	forma_pagamento_id bigint NOT NULL,

	endereco_cidade_id bigint not null,
    endereco_cep varchar(9) not null,
    endereco_logradouro varchar(100) not null,
    endereco_numero varchar(20) not null,
    endereco_complemento varchar(60) null,
    endereco_bairro varchar(60) not null,

    status varchar(10) NOT null,
	data_criacao timestamp NOT NULL,
	data_confirmacao timestamp,
	data_cancelamento timestamp,
	data_entrega timestamp,

	PRIMARY KEY (id),

	CONSTRAINT fk_pedido_endereco_cidade FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id),
	CONSTRAINT fk_pedido_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
	CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
	CONSTRAINT fk_forma_pagamento FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id)
);