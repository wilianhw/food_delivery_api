SET session_replication_role = 'replica';

DELETE FROM estado;
DELETE FROM cidade;
DELETE FROM cozinha;
DELETE FROM forma_pagamento;
DELETE FROM grupo;
DELETE FROM produto;
DELETE FROM restaurante;
DELETE FROM restaurante_forma_pagamento;
DELETE FROM usuario;
DELETE FROM usuario_grupo;
DELETE FROM permissao;

SET session_replication_role = 'origin';

SELECT setval(pg_get_serial_sequence('grupo', 'id'), 1);
SELECT setval(pg_get_serial_sequence('usuario', 'id'), 1);

-- Estado
INSERT INTO estado (id, nome) VALUES (1, 'Minas Gerais');
INSERT INTO estado (id, nome) VALUES (2, 'São Paulo');
SELECT setval(pg_get_serial_sequence('estado', 'id'), 2);

-- Cidade
INSERT INTO cidade (id, nome, estado_id) VALUES(1, 'Itumirim', 1);
INSERT INTO cidade (id, nome, estado_id) VALUES(2, 'Lavras', 1);
INSERT INTO cidade (id, nome, estado_id) VALUES(3, 'São Paulo', 2);
SELECT setval(pg_get_serial_sequence('cidade', 'id'), 3);

-- Cozinha
INSERT INTO cozinha (id, nome) VALUES (1, 'Italiana');
INSERT INTO cozinha (id, nome) VALUES (2, 'Portuguesa');
INSERT INTO cozinha (id, nome) VALUES (3, 'Japonesa');
SELECT setval(pg_get_serial_sequence('cozinha', 'id'), 3);

-- Restaurante
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao) VALUES (1, 'Italiano', 9.0, 1, '37212-000', 'Logradouro', '10', 'casa', 'centro', 1, current_timestamp, current_timestamp);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id) VALUES (2, 'Japonês', 10, 1, current_timestamp, current_timestamp, null);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id) VALUES (3, 'Portugues', 12, 2, current_timestamp, current_timestamp, null);
SELECT setval(pg_get_serial_sequence('restaurante', 'id'), 3);

-- Produto
INSERT INTO produto(id, nome, descricao, preco, ativo, restaurante_id) VALUES(1, 'Batata', 'Doce', 1.99, true, 1);
INSERT INTO produto(id, nome, descricao, preco, ativo, restaurante_id) VALUES(2, 'Doce', 'Abobora', 1.99, true, 1);
INSERT INTO produto(id, nome, descricao, preco, ativo, restaurante_id) VALUES(3, 'Caramelo', 'Salgado', 1.99, true, 2);
SELECT setval(pg_get_serial_sequence('produto', 'id'), 3);

-- Forma de pagamento
INSERT INTO forma_pagamento (id, descricao) VALUES (1, 'Crédito');
INSERT INTO forma_pagamento (id, descricao) VALUES (2, 'Debito');
SELECT setval(pg_get_serial_sequence('forma_pagamento', 'id'), 2);

-- Permissão
INSERT INTO permissao (id, nome, descricao) VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO permissao (id, nome, descricao) VALUES (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
SELECT setval(pg_get_serial_sequence('permissao', 'id'), 2);

-- Restaurante forma pagamento
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(1, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(2, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(3, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(1, 2);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(3, 2);
