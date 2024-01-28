-- Exemplo correto
-- INSERT INTO PARCELA (CODIGOPARCELA,DESIGNACAO,AREA) VALUES ('PAR-2', 'TOMATES3',25);

-- A area tem de ser inserida obrigatoriamente, pois não pode ser null
INSERT INTO PARCELA (CODIGOPARCELA,DESIGNACAO) VALUES ('PAR-1', 'TOMATES1');

-- O código da parcela está incorreto, uma vez que tem de ser do tipo 'PAR-%'
INSERT INTO PARCELA (CODIGOPARCELA,DESIGNACAO,AREA) VALUES ('PSP-2','TOMATES2', 25);


-- Exemplo correto
-- INSERT INTO CULTURA (codigoCultura, permanencia, tipoCultura, especie, ciclo) VALUES ('CUL-2', 'PERMANENTE', 'CONSUMO HUMANO/ANIMAL', 'maçã', 300);

-- A permanÊncia tem de ser obrigatóriamente de um dos 2 tipos seguintes: 'PERMANENTE' ou 'TEMPORÁRIA' (que podem ser escritos em lowercase, pois o sistema colocará em uppercase de forma automática)
INSERT INTO CULTURA (codigoCultura, permanencia, tipoCultura, especie, ciclo) VALUES ('CUL-1', 'não sei', 'tangerina', 'tangerina', 300);


-- Exemplo correto
-- INSERT INTO PARCELA_CULTURA (codigoParcela, codigoCultura, area, dataPlantacao) VALUES ('PAR-2', 'CUL-2', 50, to_date('02/11/2022','DD/MM/YYYY'));

-- A área tem de ser um número e não uma String
INSERT INTO PARCELA_CULTURA (codigoParcela, codigoCultura, area, dataDePlantacao) VALUES ('PAR-2', 'CUL-2', '50', to_date('02/11/2022','DD/MM/YYYY'));

-- Exemplo correto
-- INSERT INTO PRODUTO (codigoProduto, codigoCultura, preco, unidade) VALUES ('PROD-2', 'CUL-2', 2.05, 'Kg');

-- O código da cultura é inválido, uma vez que tem de ser do tipo 'CUL-%'
INSERT INTO PRODUTO (codigoProduto, codigoCultura, preco, unidade) VALUES ('PROD-1', '123', 2.5, 'Kg');


-- Exemplo correto
-- INSERT INTO COLHEITA (codigoColheita, codigoParcela, codigoCultura, dataDeColheita, quantidade, area) VALUES ('COL-2', 'PAR-2', 'CUL-2', to_date('04/04/2017', 'DD/MM/YYYY'), 105, 15);

-- A quantidade tem de ser um número e não uma String
INSERT INTO COLHEITA (codigoColheita, codigoParcela, codigoCultura, dataDeColheita, quantidade, area) VALUES ('COL-1', 'PAR-2', 'CUL-2', to_date('04/04/2017', 'DD/MM/YYYY'), '105,00', 15);


-- Exemplo correto
-- INSERT INTO CLIENTE (idCliente, nome, nif, email, moradaCorrespondencia, moradaEntrega, plafond, nivel) VALUES ('ID-1', 'Maria Albertina Soares', 264875923, 'albertinas@gmail.com', 'Rua Doutor Fragoso nº123', 1000, 'A');

-- O id do cliente é inválido, uma vez que tem de ser do tipo 'ID-%'
INSERT INTO CLIENTE (idCliente, nome, nif, email, moradaCorrespondencia, moradaEntrega, plafond, nivel) VALUES ('325674', 'Beatriz Assunção Neves', 264879174, 'beatriz@gmail.com', 'Rua Doutor Fragoso nº123', 1000, 'B');

-- O NIF do cliente é inválido, uma vez que tem de ser constituído por 9 dígitos
INSERT INTO CLIENTE (idCliente, nome, nif, email, moradaCorrespondencia, moradaEntrega, plafond, nivel) VALUES ('ID-2', 'Beatriz Assunção Neves', 26487917, 'beatriz@gmail.com', 'Rua Doutor Fragoso nº123', 1000, 'C');

-- O email do cliente é inválido, uma vez que não contém '@'
INSERT INTO CLIENTE (idCliente, nome, nif, email, moradaCorrespondencia, moradaEntrega, plafond, nivel) VALUES ('ID-3', 'Beatriz Assunção Neves', 264879175, 'beatrizgmail.com', 'Rua Doutor Fragoso nº123', 1000, 'A');

-- O nível do cliente é inválido, uma vez que tem de ser um dos 3 níveis ou null, caso ainda não lhe tenha sido atribuído nenhum nível: 'A', 'B' ou 'C' ou null
INSERT INTO CLIENTE (idCliente, nome, nif, email, moradaCorrespondencia, moradaEntrega, plafond, nivel) VALUES ('ID-4', 'Beatriz Assunção Neves', 264879171, 'beatriz@gmail.com', 'Rua Doutor Fragoso nº123', 1000, 'K');


-- Exemplos corretos
-- Esta encomenda não apresenta dataEntrega nem dataPagamento, pois ainda não foi entregue nem paga (este insert é válido)
-- INSERT INTO ENCOMENDA (codigoEncomenda, idCliente, enderecoEntrega, estadoEncomenda, dataRegisto, dataVencimento,estadoPagamento) VALUES ('ENC-1', 'ID-1', 'Rua António José dos Carvalhos nº34', 'EM PROCESSAMENTO', to_date('03/12/2022', 'DD/MM/YYYY'), to_date('05/12/2022', 'DD/MM/YYYY'), 'PAGAMENTO PENDENTE');
-- Apesar desta encomenda não possuir o campo de enderecoEntrega preenchido, ou seja, a null, a encomenda é validada na mesma, pois o sistema vai buscar por defeito o endereço de entrega associado ao cliente
-- INSERT INTO ENCOMENDA (codigoEncomenda, idCliente, dataEntrega, estadoEncomenda, dataRegisto, dataVencimento, dataPagamento, estadoPagamento) VALUES ('ENC-1', 'ID-1', to_date('05/12/2022', 'DD/MM/YYYY'), 'ENTREGUE', to_date('29/11/2022', 'DD/MM/YYYY'), to_date('01/12/2022', 'DD/MM/YYYY'), to_date('30/11/2022', 'DD/MM/YYYY'),'PAGO');

-- O código da encomenda está incorreto, uma vez que tem de ser do tipo 'ENC-%'
INSERT INTO ENCOMENDA (codigoEncomenda, idCliente, enderecoEntrega, dataRegisto, estadoEncomenda, dataVencimento, estadoPagamento) VALUES ('1234', 'ID-1', 'Rua António José dos Carvalhos nº34', to_date('29/11/2022', 'DD/MM/YYYY'), 'EM PROCESSAMENTO', to_date('01/12/2022', 'DD/MM/YYYY'), 'PAGAMENTO PENDENTE');

-- O id do cliente é inválido, uma vez que tem de ser do tipo 'ID-%'
INSERT INTO ENCOMENDA (codigoEncomenda, idCliente, enderecoEntrega, dataRegisto, estadoEncomenda, dataVencimento, estadoPagamento) VALUES ('ENC-1', '325674', 'Rua António José dos Carvalhos nº34', to_date('29/11/2022', 'DD/MM/YYYY'), 'EM PROCESSAMENTO', to_date('01/12/2022', 'DD/MM/YYYY'), 'PAGAMENTO PENDENTE');

-- A data de registo da encomenta tem de ser do tipo date e esta data de registo é colocada de forma automática pelo sistema, correspondendo ao dia atual/em que se está a efetuar o registo da mesma
INSERT INTO ENCOMENDA (codigoEncomenda, idCliente, enderecoEntrega, dataEntrega, estadoEncomenda, dataRegisto, dataVencimento, dataPagamento, estadoPagamento) VALUES ('ENC-1', 'ID-1', 'Rua António José dos Carvalhos nº34', to_date('05/12/2022', 'DD/MM/YYYY'), 'ENTREGUE', '', to_date('01/11/2022', 'DD/MM/YYYY'), to_date('30/11/2022', 'DD/MM/YYYY'), 'PAGO');

-- O estado da encomenda está inválido, uma vez que só existem 3 estados: 'REGISTADA', 'EM PROCESSAMENTO' e 'ENTREGUE' (que podem ser escritos em lowercase, pois o sistema colocará em uppercase de forma automática)
INSERT INTO ENCOMENDA (codigoEncomenda, idCliente, enderecoEntrega, dataEntrega, estadoEncomenda, dataRegisto, dataVencimento, dataPagamento, estadoPagamento) VALUES ('ENC-1', 'ID-1', 'Rua António José dos Carvalhos nº34', to_date('05/12/2022', 'DD/MM/YYYY'), 'ESTÁ ENTREGUE', to_date('29/11/2022', 'DD/MM/YYYY'), to_date('01/11/2022', 'DD/MM/YYYY'), to_date('30/11/2022', 'DD/MM/YYYY'), 'PAGO');

-- A data de vencimento tem de ser do tipo date e esta data é colocada de forma automática pelo sistema, correspondendo a 2 dias depois da data de registo da encomenda, para além disso é impossível haver dataEntrega e o estado estar como 'PAGO' e não haver dataPagamento
INSERT INTO ENCOMENDA (codigoEncomenda, idCliente, enderecoEntrega, dataEntrega, estadoEncomenda, dataRegisto, dataVencimento, estadoPagamento) VALUES ('ENC-1', 'ID-1', 'Rua António José dos Carvalhos nº34', to_date('05/12/2022', 'DD/MM/YYYY'), 'EM PROCESSAMENTO', to_date('29/11/2022', 'DD/MM/YYYY'), to_date('01/11/2022', 'DD/MM/YYYY'), 'PAGO');

-- O estado de pagamento é inválido, uma vez que só existem 3 estados: 'PAGAMENTO PENDENTE', 'AGUARDANDO RESPOSTA' e 'INCIDENTE' (que podem ser escritos em lowercase, pois o sistema colocará em uppercase de forma automática)
INSERT INTO ENCOMENDA (codigoEncomenda, idCliente, enderecoEntrega, dataEntrega, estadoEncomenda, dataRegisto, dataVencimento, dataPagamento, estadoPagamento) VALUES ('ENC-1', 'ID-1', 'Rua António José dos Carvalhos nº34', to_date('05/12/2022', 'DD/MM/YYYY'), 'EM PROCESSAMENTO', to_date('29/11/2022', 'DD/MM/YYYY'), to_date('01/11/2022', 'DD/MM/YYYY'), to_date('30/11/2022', 'DD/MM/YYYY'), 'A PAGAR');


-- Exemplo correto
-- INSERT INTO ENCOMENDA_PRODUTO (codigoEncomenda, codigoProduto, quantidade) VALUES ('ENC-1', 'PROD-2', 200);

-- O código da encomenda é inválido, uma vez que tem de ser do tipo 'ENC-%'
INSERT INTO ENCOMENDA_PRODUTO (codigoEncomenda, codigoProduto, quantidade) VALUES ('ENCOMENDA-1', 'PROD-2', 200);

-- O código do produto é inválido, uma vez que tem de ser do tipo 'PROD-%'
INSERT INTO ENCOMENDA_PRODUTO (codigoEncomenda, codigoProduto, quantidade) VALUES ('ENC-1', 'PRODUTO-2', 200);

-- A quantidade tem de ser um número e não uma String
INSERT INTO ENCOMENDA_PRODUTO (codigoEncomenda, codigoProduto, quantidade) VALUES ('ENCOMENDA-1', 'PROD-2', '200');


-- Exemplo correto
-- INSERT INTO OPERACAO_AGRICOLA (data, codigoParcela, restricoes, tipoOperacao) VALUES (to_date('30/11/2022', 'DD/MM/YYYY'), 'PAR-2', 'Esta parcela não deve ser exposta a cloreto de potassio, nem deve ser irrigada com mais de 5L por dia.', 'IRRIGACAO');

-- A data tem de ser do tipo date e portanto inserida desta forma: '15/12/2022', 'DD/MM/YYYY'
INSERT INTO OPERACAO_AGRICOLA (data, codigoParcela, codigoOperacao, restricoes, tipoOperacao) VALUES ('15/12/2022', 'PAR-2', 'Esta parcela não deve ser exposta a cloreto de potassio, nem deve ser irrigada com mais de 5L por dia.', 'IRRIGACAO');

-- O código da parcela é inválido, uma vez que tem de ser do tipo 'PAR-%'; Apesar de não terem sido inseridas restrições isso em nada vai prejudicar, pois é possível que uma parcela não tenha qualquer tipo de restrições
INSERT INTO OPERACAO_AGRICOLA (data, codigoParcela, codigoOperacao, tipoOperacao) VALUES (to_date('30/11/2022', 'DD/MM/YYYY'), 'PAR2', 'IRRIGACAO');

-- O tipo da operação é inválido, uma vez que tem de ser obrigatoriamente de um dos 2 tipos seguintes: 'IRRIGACAO' ou 'APLICACAO DE FATOR DE PRODUCAO'
INSERT INTO OPERACAO_AGRICOLA (data, codigoParcela, codigoOperacao, restricoes, tipoOperacao) VALUES (to_date('30/11/2022', 'DD/MM/YYYY'), 'PAR-2', 'Esta parcela não deve ser exposta a cloreto de potassio.', 'REGAR');


-- Exemplo correto
-- INSERT INTO FORNECEDOR (codigoFornecedor, nome, contacto, email) VALUES ('FOR-1', 'SONAE', 936614885, 'sonaept@gmail.com');

-- O código do fornecedor é inválido, uma vez que tem de ser do tipo 'FOR-%'
INSERT INTO FORNECEDOR (codigoFornecedor, nome, contacto, email) VALUES ('FOR1', 'SONAE', 936614885, 'sonaept@gmail.com');

-- O nome do fornecedor está inválido, uma vez que tem de ser inserido entre ' ' para que seja considerado String
INSERT INTO FORNECEDOR (codigoFornecedor, nome, contacto, email) VALUES ('FOR-1', SONAE, 936614885, 'sonaept@gmail.com');

-- O contacto do fornecedor é inválido, uma vez que não cumpre os requisitos de um número de telefone, uma vez que tem de ter 9 digitos
INSERT INTO FORNECEDOR (codigoFornecedor, nome, contacto, email) VALUES ('FOR-1', 'SONAE', 9366148, 'sonaept@gmail.com');

-- O email do fornecedor é inválido, uma vez que não contém '@'
INSERT INTO FORNECEDOR (codigoFornecedor, nome, contacto, email) VALUES ('FOR-1', 'SONAE', 936614885, 'sonaept.com');


-- Exemplo correto
-- INSERT INTO FATOR_PRODUCAO (codigoFator, preco, nomeComercial, tipoDeFator, formulacao, codigoFornecedor) VALUES ('FAT-1', 27.99, 'Guanito', 'ADUBO', 'PO', 'FOR-1');

-- O código do fator de produção é inválido, uma vez que tem de ser do tipo 'FAT-%'
INSERT INTO FATOR_PRODUCAO (codigoFator, preco, nomeComercial, tipoDeFator, formulacao, codigoFornecedor) VALUES ('FAT1', 27.99, 'Guanito', 'ADUBO', 'PO', 'FOR-1');

-- O preço do fator de produção é inválido, uma vez que tem de ser um número (tem de se usar . para separar as casas decimais) e não uma String
INSERT INTO FATOR_PRODUCAO (codigoFator, preco, nomeComercial, tipoDeFator, formulacao, codigoFornecedor) VALUES ('FAT-1', '27,99', 'Guanito', 'ADUBO', 'PO', 'FOR-1');

-- O tipo de fator está inválido, uma vez que só existem 4 tipos: 'CORRETIVO MINERAL', 'FERTILIZANTE', 'ADUBO' e 'PRODUTO FITOFORMACO' (que podem ser escritos em lowercase, pois o sistema colocará em uppercase de forma automática)
INSERT INTO FATOR_PRODUCAO (codigoFator, preco, nomeComercial, tipoDeFator, formulacao, codigoFornecedor) VALUES ('FAT-1', 27.99, 'Guanito', 'PRODUTO', 'PO', 'FOR-1');

-- A formulação está inválida, uma vez que só existem 3 formulações: 'LIQUIDO', 'GRANULADO' e 'PO' (que podem ser escritos em lowercase, pois o sistema colocará em uppercase de forma automática)
INSERT INTO FATOR_PRODUCAO (codigoFator, preco, nomeComercial, tipoDeFator, formulacao, codigoFornecedor) VALUES ('FAT-1', 27.99, 'Guanito', 'ADUBO', 'EM PO', 'FOR-1');

-- O código do fornecedor é inválido, uma vez que tem de ser do tipo 'FOR-%'
INSERT INTO FATOR_PRODUCAO (codigoFator, preco, nomeComercial, tipoDeFator, formulacao, codigoFornecedor) VALUES ('FAT-1', 27.99, 'Guanito', 'ADUBO', 'PO', 'FORNEC1');


-- Exemplo correto
INSERT INTO FATOR_OPERACAO (codigoFator, codigoParcela, data, unidadeProduto, quantidadeProduto, formaAplicacao) VALUES ('FAT-1', 'PAR-2', to_date('30/11/2022', 'DD/MM/YYYY'), 'Kg', 1, 'NO SOLO');

-- A forma de aplicação está inválida, uma vez que só existem 3 formas de aplicação: 'FOLIAR', 'FERTIRREGA' e 'NO SOLO' (que podem ser escritos em lowercase, pois o sistema colocará em uppercase de forma automática)
INSERT INTO FATOR_OPERACAO (codigoFator, codigoParcela, data, unidadeProduto, quantidadeProduto, formaAplicacao) VALUES ('FAT-1', 'PAR-2', to_date('30/11/2022', 'DD/MM/YYYY'), 'Kg', 1, 'SOLO');


-- Exemplos corretos
-- INSERT INTO ELEMENTO_SUBSTANCIA (codigoSubstancia, nome, tipo) VALUES ('SUB-1', 'sulfato de cobre', 'SUBSTANCIA ORGANICA');
-- INSERT INTO ELEMENTO_SUBSTANCIA (codigoSubstancia, nome, tipo) VALUES ('ELE-1', 'cloreto de magnesio', 'ELEMENTO NUTRITIVO ORGANICO');

-- O tipo de elemento/substãncia está inválida, uma vez que só existem 2 tipos: 'SUBSTANCIA ORGANICA' e 'ELEMENTO NUTRITIVO ORGANICO' (que podem ser escritos em lowercase, pois o sistema colocará em uppercase de forma automática)
INSERT INTO ELEMENTO_SUBSTANCIA (codigoSubstancia, nome, tipo) VALUES ('SUB-1', 'sulfato de cobre', 'CARBOIDRATO');

-- O código da substãncia e o tipo são incompatíveis, portanto o elemento/substãncia inserido é inválido, terá de inserir o tipo 'ELE-%' juntamente com 'ELEMENTO NUTRITIVO ORGANICO' ou 'SUB-%' juntamente com 'SUBSTANCIA ORGANICA'
INSERT INTO ELEMENTO_SUBSTANCIA (codigoSubstancia, nome, tipo) VALUES ('ELE-1', 'sulfato de cobre', 'SUBSTANCIA ORGANICA');


-- Exemplo correto
-- INSERT INTO FICHA_TECNICA (codigoFator, codigoSubstancia, quantidade, unidade) VALUES ('FAT-1', 'SUB-1', 1, 'kg');

-- O código do taror de produção e o código da substância estão incorretos, pois deveriam estar sob a forma 'FAT-%' e 'SUB-% ou 'ELE-%', bem como a quantidade deveria ser um número e não uma String
INSERT INTO FICHA_TECNICA (codigoFator, codigoSubstancia, quantidade, unidade) VALUES ('FAT1', 'SUB1', '1', 'kg');



