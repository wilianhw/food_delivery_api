-- Estado
INSERT INTO algafood.estado (nome) VALUES ('Minas Gerais');
INSERT INTO algafood.estado (nome) VALUES ('São Paulo');

-- Cidade
INSERT INTO algafood.cidade (nome, estado_id) VALUES('Itumirim', 1);
INSERT INTO algafood.cidade (nome, estado_id) VALUES('Lavras', 1);
INSERT INTO algafood.cidade (nome, estado_id) VALUES('São Paulo', 1);

-- Cozinha
INSERT INTO algafood.cozinha (nome) VALUES ('Italiana');
INSERT INTO algafood.cozinha (nome) VALUES ('Portuguesa');
INSERT INTO algafood.cozinha (nome) VALUES ('Japonesa');

-- Restaurante
INSERT INTO algafood.restaurante (id, nome, taxa_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao) VALUES (1, 'Italiano', 9.0, 1, '37212-000', 'Logradouro', '10', 'casa', 'centro', 1, current_timestamp, current_timestamp);
INSERT INTO algafood.restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES (2, 'Japonês', 10, 1, current_timestamp, current_timestamp);
INSERT INTO algafood.restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES (3, 'Portugues', 12, 2, current_timestamp, current_timestamp);

-- Forma de pagamento
INSERT INTO algafood.forma_pagamento (id, descricao) VALUES (1, 'Crédito');
INSERT INTO algafood.forma_pagamento (id, descricao) VALUES (2, 'Debito');

-- Permissão
INSERT INTO algafood.permissao (nome, descricao) VALUES ('CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO algafood.permissao (nome, descricao) VALUES ('EDITAR_COZINHAS', 'Permite editar cozinhas')

-- Restaurante forma pagamento
INSERT INTO algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(1, 1);
INSERT INTO algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(2, 1);
INSERT INTO algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(3, 1);
INSERT INTO algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(1, 2);
INSERT INTO algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(3, 2);
