CREATE TABLE item_pedido (
	id bigserial NOT NULL,
	pedido_id bigint NOT NULL,
	produto_id bigint NOT NULL,
	quantidade int NOT NULL,
	preco_unitario decimal(10, 2) NOT NULL,
	preco_total decimal(10, 2) NOT NULL,
	observacao varchar(255),

	PRIMARY KEY (id)
);

ALTER TABLE item_pedido
ADD CONSTRAINT uk_item_pedido_produto UNIQUE (pedido_id, produto_id);

ALTER TABLE item_pedido
ADD CONSTRAINT fk_item_pedido FOREIGN KEY (pedido_id) REFERENCES pedido (id);

ALTER TABLE item_pedido
ADD CONSTRAINT fk_item_produto FOREIGN KEY (produto_id) REFERENCES produto (id);