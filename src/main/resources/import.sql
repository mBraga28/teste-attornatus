INSERT INTO tb_address (area, cep, number, city) VALUES ('Avenida Domingos Ferreira', '12345678', 12, 'Recife');
INSERT INTO tb_address (area, cep, number, city) VALUES ('Avenida Conselheiro Rosa e SIlva', '10293847', 3456, 'Recife');
INSERT INTO tb_address (area, cep, number, city) VALUES ('Avenida Boa Viagem', '13243546', 131, 'Recife');
INSERT INTO tb_address (area, cep, number, city) VALUES ('Rua Hélio Vasconcelos de Alvarenga', '98765432', 5003, 'Campos dos Goytacazes');

INSERT INTO tb_person (name, birth_date) VALUES ('Maria Silveira', '1990-07-25');
INSERT INTO tb_person (name, birth_date) VALUES ('José Mário Fernandes', '1985-02-13');
INSERT INTO tb_person (name, birth_date) VALUES ('Lucas Leão', '1999-11-23');
INSERT INTO tb_person (name, birth_date) VALUES ('Rafaela Diniz', '1978-09-03');
INSERT INTO tb_person (name, birth_date) VALUES ('Francisco Quirino', '1956-01-31');

INSERT INTO tb_person_address (person_id, address_id) VALUES (1, 1);
INSERT INTO tb_person_address (person_id, address_id) VALUES (2, 3);
INSERT INTO tb_person_address (person_id, address_id) VALUES (3, 4);
INSERT INTO tb_person_address (person_id, address_id) VALUES (4, 2);
INSERT INTO tb_person_address (person_id, address_id) VALUES (5, 4);