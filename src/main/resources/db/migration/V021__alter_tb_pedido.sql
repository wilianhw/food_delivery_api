CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE pedido ADD COLUMN codigo varchar(36) DEFAULT uuid_generate_v4() NOT NULL;

ALTER TABLE pedido ALTER COLUMN codigo DROP DEFAULT;

ALTER TABLE pedido ADD CONSTRAINT uk_pedido_codigo UNIQUE (codigo);
