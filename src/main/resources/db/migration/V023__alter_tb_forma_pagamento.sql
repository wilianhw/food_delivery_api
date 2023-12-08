ALTER TABLE forma_pagamento ADD COLUMN data_atualizacao timestamp NULL;
UPDATE forma_pagamento SET data_atualizacao = current_timestamp;
ALTER TABLE forma_pagamento ALTER COLUMN data_atualizacao SET NOT NULL;