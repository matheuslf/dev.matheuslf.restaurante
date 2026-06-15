ALTER TABLE pedido_itens
    ADD COLUMN data_inicio_preparo TIMESTAMP,
ADD COLUMN data_pronto TIMESTAMP,
ADD COLUMN data_entrega TIMESTAMP;