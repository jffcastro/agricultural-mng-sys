delete from PARCELA;
insert into PARCELA (CODIGOPARCELA,DESIGNACAO,AREA) values ('PAR-1', 'TOMATES3',25);
insert into PARCELA (CODIGOPARCELA,DESIGNACAO,AREA) values ('PAR-2', 'MILHO1',50);
insert into PARCELA (CODIGOPARCELA,DESIGNACAO,AREA) values ('PAR-3', 'BATATAS2',75);

delete from CULTURA;
insert into CULTURA (codigoCultura, permanencia, tipoCultura, especie, ciclo) values ('CUL-1', 'TEMPORARIA', 'ADUBACAO VERDE', 'centeio', 90 );
insert into CULTURA (codigoCultura, permanencia, tipoCultura, especie, ciclo) values ('CUL-2', 'PERMANENTE', 'CONSUMO HUMANO/ANIMAL', 'maçã', 300 );
insert into CULTURA (codigoCultura, permanencia, tipoCultura, especie, ciclo) values ('CUL-3', 'PERMANENTE', 'CONSUMO HUMANO/ANIMAL', 'cereja', 290);
insert into CULTURA (codigoCultura, permanencia, tipoCultura, especie, ciclo) values ('CUL-4', 'TEMPORARIA', 'CONSUMO HUMANO/ANIMAL', 'batata', 100);
insert into CULTURA (codigoCultura, permanencia, tipoCultura, especie, ciclo) values ('CUL-5', 'TEMPORARIA', 'ADUBACAO VERDE', 'feijao', 75);

delete from PARCELA_CULTURA;
insert into PARCELA_CULTURA (codigoParcela, codigoCultura, area, datadePlantacao) values ('PAR-1', 'CUL-5', 10, to_date('02/11/2022','DD/MM/YYYY'));
insert into PARCELA_CULTURA (codigoParcela, codigoCultura, area, datadePlantacao) values ('PAR-1', 'CUL-3', 5, to_date('02/11/2022','DD/MM/YYYY'));
insert into PARCELA_CULTURA (codigoParcela, codigoCultura, area, datadePlantacao) values ('PAR-1', 'CUL-4', 7, to_date('02/11/2022','DD/MM/YYYY'));
insert into PARCELA_CULTURA (codigoParcela, codigoCultura, area, datadePlantacao) values ('PAR-2', 'CUL-1', 13, to_date('02/11/2022','DD/MM/YYYY'));
insert into PARCELA_CULTURA (codigoParcela, codigoCultura, area, datadePlantacao) values ('PAR-2', 'CUL-2', 20, to_date('02/11/2022','DD/MM/YYYY'));
insert into PARCELA_CULTURA (codigoParcela, codigoCultura, area, datadePlantacao) values ('PAR-2', 'CUL-3', 5, to_date('02/11/2022','DD/MM/YYYY'));
insert into PARCELA_CULTURA (codigoParcela, codigoCultura, area, datadePlantacao) values ('PAR-2', 'CUL-4', 9, to_date('02/11/2022','DD/MM/YYYY'));
insert into PARCELA_CULTURA (codigoParcela, codigoCultura, area, datadePlantacao) values ('PAR-3', 'CUL-5', 75, to_date('02/11/2022','DD/MM/YYYY'));

delete from PRODUTO;
insert into PRODUTO (codigoProduto, preco, unidade, quantidade) values ('PRO-1', 2.05, 'Kg', 300);
insert into PRODUTO (codigoProduto, preco, unidade, quantidade) values ('PRO-2', 4.08, 'Kg', 150);
insert into PRODUTO (codigoProduto, preco, unidade, quantidade) values ('PRO-3', 8.30, 'Kg', 260);
insert into PRODUTO (codigoProduto, preco, unidade, quantidade) values ('PRO-4', 2.15, 'Kg', 480);
insert into PRODUTO (codigoProduto, preco, unidade, quantidade) values ('PRO-5', 1.95, 'Kg', 260);

delete from COLHEITA;
insert into COLHEITA (codigoColheita, codigoParcela, codigoCultura, CODIGOPRODUTO, dataDeColheita, quantidade, area) values ('COL-1', 'PAR-1', 'CUL-4', 'PRO-3', to_date('04/04/2017', 'DD/MM/YYYY'), 105, 5);
insert into COLHEITA (codigoColheita, codigoParcela, codigoCultura, CODIGOPRODUTO, dataDeColheita, quantidade, area) values ('COL-2', 'PAR-1', 'CUL-5', 'PRO-2', to_date('04/04/2017', 'DD/MM/YYYY'), 50, 8);
insert into COLHEITA (codigoColheita, codigoParcela, codigoCultura, CODIGOPRODUTO, dataDeColheita, quantidade, area) values ('COL-3', 'PAR-2', 'CUL-2', 'PRO-1', to_date('04/04/2017', 'DD/MM/YYYY'), 133, 15);
insert into COLHEITA (codigoColheita, codigoParcela, codigoCultura, CODIGOPRODUTO, dataDeColheita, quantidade, area) values ('COL-4', 'PAR-2', 'CUL-4', 'PRO-3', to_date('04/04/2017', 'DD/MM/YYYY'), 34, 7);
insert into COLHEITA (codigoColheita, codigoParcela, codigoCultura, CODIGOPRODUTO, dataDeColheita, quantidade, area) values ('COL-5', 'PAR-3', 'CUL-5', 'PRO-2', to_date('04/04/2017', 'DD/MM/YYYY'), 345, 50);
insert into COLHEITA (codigoColheita, codigoParcela, codigoCultura, CODIGOPRODUTO, dataDeColheita, quantidade, area) values ('COL-6', 'PAR-1', 'CUL-4', 'PRO-3', to_date('04/04/2017', 'DD/MM/YYYY'), 105, 1);
insert into COLHEITA (codigoColheita, codigoParcela, codigoCultura, CODIGOPRODUTO, dataDeColheita, quantidade, area) values ('COL-7', 'PAR-1', 'CUL-5', 'PRO-2', to_date('04/04/2017', 'DD/MM/YYYY'), 50, 1);
insert into COLHEITA (codigoColheita, codigoParcela, codigoCultura, CODIGOPRODUTO, dataDeColheita, quantidade, area) values ('COL-8', 'PAR-2', 'CUL-2', 'PRO-1', to_date('04/04/2017', 'DD/MM/YYYY'), 133, 1);
insert into COLHEITA (codigoColheita, codigoParcela, codigoCultura, CODIGOPRODUTO, dataDeColheita, quantidade, area) values ('COL-9', 'PAR-2', 'CUL-4', 'PRO-3', to_date('04/04/2017', 'DD/MM/YYYY'), 34, 1);
insert into COLHEITA (codigoColheita, codigoParcela, codigoCultura, CODIGOPRODUTO, dataDeColheita, quantidade, area) values ('COL-10', 'PAR-3', 'CUL-5', 'PRO-1', to_date('04/04/2017', 'DD/MM/YYYY'), 345, 1);

delete from INPUT_HUB;
insert into INPUT_HUB (input_string) values ('CT1;40.6389;-8.6553;C1');
insert into INPUT_HUB (input_string) values ('CT2;38.0333;-7.8833;C2');
insert into INPUT_HUB (input_string) values ('CT14;38.5243;-8.8926;E1');
insert into INPUT_HUB (input_string) values ('CT11;39.3167;-7.4167;E2');
insert into INPUT_HUB (input_string) values ('CT10;39.7444;-8.8072;P3');

delete from HUB;
insert into HUB (idhub, lat, lng, hubType) values ('CT1',40.6389,-8.6553,'C1');
insert into HUB (idhub, lat, lng, hubType) values ('CT2',38.0333,-7.8833,'C2');
insert into HUB (idhub, lat, lng, hubType) values ('CT14',38.5243,-8.8926,'E1');
insert into HUB (idhub, lat, lng, hubType) values ('CT11',39.3167,-7.4167,'E2');
insert into HUB (idhub, lat, lng, hubType) values ('CT10',39.7444,-8.8072,'P3');

delete from CLIENTE;
insert into CLIENTE (idCliente, nome, nif, email, moradaCorrespondencia, moradaEntrega, plafond) values ('ID-1', 'Maria Albertina Soares', 264816223, 'albertinas@gmail.com', 'Rua Doutor Fragoso nº123', 'Rua Doutor Fragoso nº123', 1000);
insert into CLIENTE (idCliente, nome, nif, email, moradaCorrespondencia, moradaEntrega, plafond) values ('ID-2', 'Jorge Antonio', 264871023, 'jorgeantonio@gmail.com', 'Rua Antonio nº12', 'Rua Antonio nº12', 1500);
insert into CLIENTE (idCliente, nome, nif, email, moradaCorrespondencia, moradaEntrega, plafond, NIVEL) values ('ID-3', 'João Paulo', 264347323, 'joaopaulo@gmail.com', 'Rua Manuel nº45', 'Rua Manuel nº45', 750, 'B');
insert into CLIENTE (idCliente, nome, nif, email, moradaCorrespondencia, moradaEntrega, plafond) values ('ID-4', 'Ricardo Monteiro', 219041323, 'ricardomonteiro@gmail.com', 'Rua da cruz nº280', 'Rua da cruz nº280', 1200);
insert into CLIENTE (idCliente, nome, nif, email, moradaCorrespondencia, moradaEntrega, plafond, NIVEL) values ('ID-5', 'Maria Antunes', 269471323, 'mariaantunes@gmail.com', 'Rua D. Antonio nº3', 'Rua D. Antonio nº3', 2300, 'A');

delete from ENCOMENDA;
insert into ENCOMENDA (codigoEncomenda, idCliente, estadoEncomenda, datapagamento, estadoPagamento) values ('ENC-1', 'ID-1', 'EM PROCESSAMENTO', sysdate, 'PAGO');
insert into ENCOMENDA (codigoEncomenda, idCliente, estadoEncomenda, estadoPagamento) values ('ENC-2', 'ID-2', 'EM PROCESSAMENTO', 'PAGAMENTO PENDENTE');
insert into ENCOMENDA (codigoEncomenda, idCliente, estadoEncomenda, datapagamento, estadoPagamento) values ('ENC-3', 'ID-3', 'EM PROCESSAMENTO', sysdate, 'PAGO');
insert into ENCOMENDA (codigoEncomenda, idCliente, estadoEncomenda, estadoPagamento) values ('ENC-4', 'ID-4', 'EM PROCESSAMENTO', 'PAGAMENTO PENDENTE');
insert into ENCOMENDA (codigoEncomenda, idCliente, estadoEncomenda, estadoPagamento) values ('ENC-5', 'ID-5', 'EM PROCESSAMENTO', 'PAGAMENTO PENDENTE');
insert into ENCOMENDA (codigoEncomenda, idCliente, estadoEncomenda, estadoPagamento) values ('ENC-6', 'ID-5', 'EM PROCESSAMENTO', 'PAGAMENTO PENDENTE');
insert into ENCOMENDA (codigoEncomenda, idCliente, dataregisto, estadoEncomenda, estadoPagamento) values ('ENC-7', 'ID-5', to_date('02/12/2022', 'DD/MM/YYYY'), 'EM PROCESSAMENTO', 'PAGAMENTO PENDENTE');
insert into ENCOMENDA (codigoEncomenda, idCliente, estadoEncomenda, estadoPagamento) values ('ENC-8', 'ID-3', 'EM PROCESSAMENTO', 'PAGAMENTO PENDENTE');
insert into ENCOMENDA (codigoEncomenda, idCliente, estadoEncomenda, estadoPagamento) values ('ENC-9', 'ID-4', 'EM PROCESSAMENTO', 'PAGAMENTO PENDENTE');
insert into ENCOMENDA (codigoEncomenda, idCliente, dataentrega, estadoEncomenda, datapagamento, estadoPagamento) values ('ENC-10', 'ID-3', sysdate , 'ENTREGUE', sysdate, 'PAGO');
insert into ENCOMENDA (codigoEncomenda, idCliente, dataentrega, estadoEncomenda, datapagamento, estadoPagamento) values ('ENC-13', 'ID-3', sysdate , 'ENTREGUE', sysdate, 'INCIDENTE');
insert into ENCOMENDA (codigoEncomenda, idCliente, dataentrega, estadoEncomenda, datapagamento, estadoPagamento) values ('ENC-14', 'ID-3', sysdate , 'ENTREGUE', sysdate, 'PAGO');

delete from ENCOMENDA_PRODUTO;
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-1', 'PRO-5', 20.00);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-2', 'PRO-3', 18.30);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-3', 'PRO-2', 32.00);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-4', 'PRO-1', 109.00);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-5', 'PRO-3', 34.92);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-6', 'PRO-1', 223.00);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-7', 'PRO-4', 19.75);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-8', 'PRO-2', 24.87);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-9', 'PRO-1', 20.10);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-7', 'PRO-2', 10.98);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-3', 'PRO-3', 24.20);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-2', 'PRO-4', 63.00);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-2', 'PRO-5', 91.30);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-4', 'PRO-2', 34.99);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-8', 'PRO-4', 132.40);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-1', 'PRO-2', 201.10);
insert into ENCOMENDA_PRODUTO (CODIGOENCOMENDA, CODIGOPRODUTO, QUANTIDADE) values ('ENC-10', 'PRO-2', 201.10);


delete from OPERACAO_AGRICOLA;
insert into OPERACAO_AGRICOLA (DATA, CODIGOPARCELA, TIPOOPERACAO, CUSTO) values (to_date('18/11/2023', 'DD/MM/YYYY'), 'PAR-1', 'IRRIGACAO', 2000);
insert into OPERACAO_AGRICOLA (DATA, CODIGOPARCELA, TIPOOPERACAO, CUSTO) values (to_date('21/11/2023', 'DD/MM/YYYY'), 'PAR-2', 'APLICACAO DE FATOR DE PRODUCAO', 2300);
insert into OPERACAO_AGRICOLA (DATA, CODIGOPARCELA, TIPOOPERACAO, CUSTO) values (to_date('30/11/2023', 'DD/MM/YYYY'), 'PAR-3', 'IRRIGACAO', 1800);

delete from FORNECEDOR;
insert into FORNECEDOR (CODIGOFORNECEDOR, NOME, CONTACTO, EMAIL) values ('FOR-1', 'Manuel', 915634523, 'manuel@gmail.com');
insert into FORNECEDOR (CODIGOFORNECEDOR, NOME, CONTACTO, EMAIL) values ('FOR-2', 'Joaquim', 915109523, 'joaquim@gmail.com');
insert into FORNECEDOR (CODIGOFORNECEDOR, NOME, CONTACTO, EMAIL) values ('FOR-3', 'Gestrudes', 912745362, 'gestrudes@gmail.com');

delete from FATOR_PRODUCAO;
insert into FATOR_PRODUCAO (CODIGOFATOR, PRECO, NOMECOMERCIAL, TIPODEFATOR, FORMULACAO, CODIGOFORNECEDOR) values ('FAT-1', 2.65, 'FatorA', 'Corretivo Mineral', 'liquido', 'FOR-1');
insert into FATOR_PRODUCAO (CODIGOFATOR, PRECO, NOMECOMERCIAL, TIPODEFATOR, FORMULACAO, CODIGOFORNECEDOR) values ('FAT-2', 4.85, 'FatorB', 'Fertilizante', 'po', 'FOR-2');
insert into FATOR_PRODUCAO (CODIGOFATOR, PRECO, NOMECOMERCIAL, TIPODEFATOR, FORMULACAO, CODIGOFORNECEDOR) values ('FAT-3', 1.70, 'FatorC', 'Adubo', 'granulado', 'FOR-3');

delete from RESTRICAO_FATOR;
insert into RESTRICAO_FATOR (CODIGOFATOR, CODIGOPARCELA, DATAINICIO, DATAFIM) values ('FAT-3', 'PAR-3', to_date('01/12/2022', 'DD/MM/YYYY'), to_date('30/12/2022', 'DD/MM/YYYY'));
insert into RESTRICAO_FATOR (CODIGOFATOR, CODIGOPARCELA, DATAINICIO, DATAFIM) values ('FAT-1', 'PAR-1', to_date('01/12/2022', 'DD/MM/YYYY'), to_date('30/12/2022', 'DD/MM/YYYY'));

delete from FATOR_OPERACAO;
insert into FATOR_OPERACAO (CODIGOFATOR, CODIGOPARCELA, DATA, UNIDADEPRODUTO, QUANTIDADEPRODUTO, FORMAAPLICACAO) values ('FAT-1', 'PAR-1', to_date('18/11/2023', 'DD/MM/YYYY'), 'Kg', 30.23, 'FOLIAR');
insert into FATOR_OPERACAO (CODIGOFATOR, CODIGOPARCELA, DATA, UNIDADEPRODUTO, QUANTIDADEPRODUTO, FORMAAPLICACAO) values ('FAT-2', 'PAR-2', to_date('21/11/2023', 'DD/MM/YYYY'), 'Kg', 14.90, 'FERTIRREGA');
insert into FATOR_OPERACAO (CODIGOFATOR, CODIGOPARCELA, DATA, UNIDADEPRODUTO, QUANTIDADEPRODUTO, FORMAAPLICACAO) values ('FAT-3', 'PAR-3', to_date('30/11/2023', 'DD/MM/YYYY'), 'Kg', 76.95, 'NO SOLO');
insert into FATOR_OPERACAO (CODIGOFATOR, CODIGOPARCELA, DATA, UNIDADEPRODUTO, QUANTIDADEPRODUTO, FORMAAPLICACAO) values ('FAT-3', 'PAR-1', to_date('18/11/2023', 'DD/MM/YYYY'), 'Kg', 12.65, 'NO SOLO');
insert into FATOR_OPERACAO (CODIGOFATOR, CODIGOPARCELA, DATA, UNIDADEPRODUTO, QUANTIDADEPRODUTO, FORMAAPLICACAO) values ('FAT-1', 'PAR-2', to_date('21/11/2023', 'DD/MM/YYYY'), 'Kg', 7.93, 'FOLIAR');
insert into FATOR_OPERACAO (CODIGOFATOR, CODIGOPARCELA, DATA, UNIDADEPRODUTO, QUANTIDADEPRODUTO, FORMAAPLICACAO) values ('FAT-1', 'PAR-3', to_date('30/11/2023', 'DD/MM/YYYY'), 'Kg', 19.30, 'FERTIRREGA');


delete from ELEMENTO_SUBSTANCIA;
insert into ELEMENTO_SUBSTANCIA (CODIGOSUBSTANCIA, NOME, TIPO) values ('SUB-1', 'Substancia 1', 'Substancia Organica');
insert into ELEMENTO_SUBSTANCIA (CODIGOSUBSTANCIA, NOME, TIPO) values ('SUB-2', 'Substancia 2', 'Substancia Organica');
insert into ELEMENTO_SUBSTANCIA (CODIGOSUBSTANCIA, NOME, TIPO) values ('SUB-3', 'Substancia 3', 'Substancia Organica');
insert into ELEMENTO_SUBSTANCIA (CODIGOSUBSTANCIA, NOME, TIPO) values ('ELE-1', 'Elemento 1', 'ELEMENTO NUTRITIVO ORGANICO');
insert into ELEMENTO_SUBSTANCIA (CODIGOSUBSTANCIA, NOME, TIPO) values ('ELE-2', 'Elemento 2', 'ELEMENTO NUTRITIVO ORGANICO');

delete from FICHA_TECNICA;
insert into FICHA_TECNICA (CODIGOFATOR, CODIGOSUBSTANCIA, QUANTIDADE, UNIDADE) values ('FAT-1', 'SUB-1', 17, '%');
insert into FICHA_TECNICA (CODIGOFATOR, CODIGOSUBSTANCIA, QUANTIDADE, UNIDADE) values ('FAT-1', 'ELE-1', 45, '%');
insert into FICHA_TECNICA (CODIGOFATOR, CODIGOSUBSTANCIA, QUANTIDADE, UNIDADE) values ('FAT-1', 'ELE-2', 38, '%');
insert into FICHA_TECNICA (CODIGOFATOR, CODIGOSUBSTANCIA, QUANTIDADE, UNIDADE) values ('FAT-2', 'SUB-1', 29, '%');
insert into FICHA_TECNICA (CODIGOFATOR, CODIGOSUBSTANCIA, QUANTIDADE, UNIDADE) values ('FAT-2', 'SUB-3', 71, '%');
insert into FICHA_TECNICA (CODIGOFATOR, CODIGOSUBSTANCIA, QUANTIDADE, UNIDADE) values ('FAT-3', 'SUB-2', 28, '%');
insert into FICHA_TECNICA (CODIGOFATOR, CODIGOSUBSTANCIA, QUANTIDADE, UNIDADE) values ('FAT-3', 'SUB-3', 20, '%');
insert into FICHA_TECNICA (CODIGOFATOR, CODIGOSUBSTANCIA, QUANTIDADE, UNIDADE) values ('FAT-3', 'ELE-1', 52, '%');

delete from INPUT_SENSOR;
insert into INPUT_SENSOR (INPUT_STRING) values ('sensoHS100123211220221733');
insert into INPUT_SENSOR (INPUT_STRING) values ('sensoHS101123211220221745');
insert into INPUT_SENSOR (INPUT_STRING) values ('sensjHS100923211220221745');
insert into INPUT_SENSOR (INPUT_STRING) values ('sensiHS100123211220221745');
insert into INPUT_SENSOR (INPUT_STRING) values ('senskHS100623211220221745');
insert into INPUT_SENSOR (INPUT_STRING) values ('senslHS100523211220221745');
insert into INPUT_SENSOR (INPUT_STRING) values ('sensmHS100423211220221745');
insert into INPUT_SENSOR (INPUT_STRING) values ('snsmHS1042321120221745');
delete from INPUT_SENSOR where INPUT_STRING = 'sensiHS100123211220221745';

delete from SENSOR_DATA_READ;
delete from SENSOR;
delete from SENSOR_ERRORS;
delete from READ_ANALYSIS;

select * from PARCELA;
select * from CULTURA;
select * from PARCELA_CULTURA;
select * from PRODUTO;
select * from COLHEITA;
select * from HUB;
select * from CLIENTE;
select * from ENCOMENDA;
select * from ENCOMENDA_PRODUTO;
select * from RESTRICAO_FATOR;
select * from OPERACAO_AGRICOLA;
select * from FORNECEDOR;
select * from RESTRICAO_FATOR;
select * from FATOR_PRODUCAO;
select * from FATOR_OPERACAO;
select * from ELEMENTO_SUBSTANCIA;
select * from FICHA_TECNICA;
select * from INPUT_SENSOR;
select * from SENSOR_DATA_READ;
select * from SENSOR;
select * from READ_ANALYSIS;
select * from SENSOR_ERRORS;

ALTER SESSION set current_schema = SEM3;