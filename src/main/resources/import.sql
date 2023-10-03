-- Cozinha
INSERT INTO algafood.cozinha (nome) VALUES ('Italiana');
INSERT INTO algafood.cozinha (nome) VALUES ('Portuguesa');
INSERT INTO algafood.cozinha (nome) VALUES ('Japonesa');

-- Restaurante
INSERT INTO algafood.restaurante (nome, taxa_frete, cozinha_id) VALUES ('Italiano', 9.0, 1);
INSERT INTO algafood.restaurante (nome, taxa_frete, cozinha_id) VALUES ('Japonês', 10, 1);
INSERT INTO algafood.restaurante (nome, taxa_frete, cozinha_id) VALUES ('Portugues', 12, 2);

-- Estado
INSERT INTO algafood.estado (nome) VALUES ('Minas Gerais');
INSERT INTO algafood.estado (nome) VALUES ('São Paulo');

-- Cidade
INSERT INTO algafood.cidade (nome, estado_id) VALUES('Itumirim', 1);
INSERT INTO algafood.cidade (nome, estado_id) VALUES('Lavras', 1);
INSERT INTO algafood.cidade (nome, estado_id) VALUES('São Paulo', 1);

-- Forma de pagamento
INSERT INTO algafood.forma_pagamento (descricao) VALUES ('Crédito');
INSERT INTO algafood.forma_pagamento (descricao) VALUES ('Debito');

-- Permissão
INSERT INTO algafood.permissao (nome, descricao) VALUES ('CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO algafood.permissao (nome, descricao) VALUES ('EDITAR_COZINHAS', 'Permite editar cozinhas')

