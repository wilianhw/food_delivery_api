SET session_replication_role = 'replica';

DELETE FROM estado;
DELETE FROM cidade;
DELETE FROM cozinha;
DELETE FROM forma_pagamento;
DELETE FROM grupo;
DELETE FROM produto;
DELETE FROM restaurante;
DELETE FROM restaurante_forma_pagamento;
DELETE FROM grupo_permissao;
DELETE FROM usuario;
DELETE FROM usuario_grupo;
DELETE FROM permissao;
DELETE FROM pedido;
DELETE FROM item_pedido;
DELETE FROM restaurante_usuario_responsavel;

SET session_replication_role = 'origin';

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
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, ativo, aberto, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao) VALUES (1, 'Italiano', 9.0, 1, true, true, '37212-000', 'Logradouro', '10', 'casa', 'centro', 1, current_timestamp, current_timestamp);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, ativo, aberto, data_cadastro, data_atualizacao, endereco_cidade_id) VALUES (2, 'Japonês', 10, 1, true, true, current_timestamp, current_timestamp, null);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, ativo, aberto, data_cadastro, data_atualizacao, endereco_cidade_id) VALUES (3, 'Portugues', 12, 2, false, false, current_timestamp, current_timestamp, null);
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, ativo, aberto, data_cadastro, data_atualizacao, endereco_cidade_id) VALUES (4, 'Brasileiro', 50, 2, false, false, current_timestamp, current_timestamp, null);
SELECT setval(pg_get_serial_sequence('restaurante', 'id'), 4);

-- Produto
INSERT INTO produto(id, nome, descricao, preco, ativo, restaurante_id) VALUES(1, 'Batata', 'Doce', 1.99, true, 1);
INSERT INTO produto(id, nome, descricao, preco, ativo, restaurante_id) VALUES(2, 'Doce', 'Abobora', 1.99, true, 1);
INSERT INTO produto(id, nome, descricao, preco, ativo, restaurante_id) VALUES(3, 'Caramelo', 'Salgado', 1.99, true, 2);
INSERT INTO produto(id, nome, descricao, preco, ativo, restaurante_id) VALUES(4, 'Bolacha', 'Doce', 1.99, true, 2);
INSERT INTO produto(id, nome, descricao, preco, ativo, restaurante_id) VALUES(5, 'Camarão', 'Do mar', 14.99, true, 2);
INSERT INTO produto(id, nome, descricao, preco, ativo, restaurante_id) VALUES(6, 'Maçã', 'Fruta', 3.99, true, 2);
SELECT setval(pg_get_serial_sequence('produto', 'id'), 6);

-- Forma de pagamento
INSERT INTO forma_pagamento (id, descricao) VALUES (1, 'Crédito');
INSERT INTO forma_pagamento (id, descricao) VALUES (2, 'Debito');
SELECT setval(pg_get_serial_sequence('forma_pagamento', 'id'), 2);

-- Grupo
INSERT INTO grupo (id, nome) VALUES (1, 'Gerente');
INSERT INTO grupo (id, nome) VALUES (2, 'Vendedor');
INSERT INTO grupo (id, nome) VALUES (3, 'Secretária');
INSERT INTO grupo (id, nome) VALUES (4, 'Cadastrador');
SELECT setval(pg_get_serial_sequence('grupo', 'id'), 4);

-- Permissão
INSERT INTO permissao (id, nome, descricao) VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO permissao (id, nome, descricao) VALUES (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
SELECT setval(pg_get_serial_sequence('permissao', 'id'), 3);

-- Grupo permissao
INSERT INTO grupo_permissao
(grupo_id, permissao_id)
VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(4, 1);

-- Restaurante forma pagamento
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(1, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(2, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(3, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(1, 2);
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(3, 2);

-- Usuario
INSERT INTO usuario (id, nome, email, senha, data_cadastro) VALUES
(1, 'João da Silva', 'joao.ger@algafood.com', '123', current_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', current_timestamp),
(3, 'José Souza', 'jose.aux@algafood.com', '123', current_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', current_timestamp);
SELECT setval(pg_get_serial_sequence('usuario', 'id'), 4);

--Usuário grupo
INSERT INTO usuario_grupo
(usuario_id, grupo_id)
VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(3, 2),
(3, 3),
(4, 4);

-- Restaurante usuário
INSERT INTO algafood.restaurante_usuario_responsavel
(restaurante_id, usuario_id)
VALUES
(1, 1),
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3);

-- Pedido
INSERT INTO pedido (id, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES (1, 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CRIADO', current_timestamp, 298.90, 10, 308.90);

INSERT INTO pedido (id, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES (2, 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CRIADO', current_timestamp, 79, 0, 79);
SELECT setval(pg_get_serial_sequence('pedido', 'id'), 2);

-- Item pedido
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (1, 1, 1, 1, 78.9, 78.9, null);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (3, 2, 6, 1, 79, 79, 'Ao ponto');
SELECT setval(pg_get_serial_sequence('item_pedido', 'id'), 3);
