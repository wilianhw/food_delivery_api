CREATE TABLE restaurante_forma_pagamento (
	restaurante_id bigint NOT NULL,
	forma_pagamento_id bigint NOT NULL,

	PRIMARY KEY (restaurante_id, forma_pagamento_id)
);

ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT fk_restaurante_forma_pagamento_restaurante
FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);

ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT fk_restaurante_forma_pagamento_forma_pagamento
FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id);