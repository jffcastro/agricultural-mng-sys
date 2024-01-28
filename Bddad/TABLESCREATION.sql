drop table PISTAS_DE_AUDITORIA;
drop table SENSOR_ERRORS;
drop table INPUT_SENSOR;
drop table SENSOR_DATA_READ;
drop table READ_ANALYSIS;
drop table SENSOR;
drop table FICHA_TECNICA;
drop table ELEMENTO_SUBSTANCIA;
drop table FATOR_OPERACAO;
drop table RESTRICAO_FATOR;
drop table FATOR_PRODUCAO;
drop table FORNECEDOR;
drop table OPERACAO_AGRICOLA;
drop table ENCOMENDA_PRODUTO;
drop table ENCOMENDA;
drop table CLIENTE;
drop table HUB;
drop table INPUT_HUB;
drop table COLHEITA;
drop table PARCELA_CULTURA;
drop table PRODUTO;
drop table CULTURA;
drop table PARCELA;

CREATE TABLE PARCELA (
    codigoParcela VARCHAR(20) PRIMARY KEY CONSTRAINT ck_ParcelaPK CHECK (codigoParcela LIKE 'PAR-%'),
    designacao VARCHAR(20) NOT NULL,
    area DECIMAL(*,2) NOT NULL CONSTRAINT ck_area1 CHECK (area > 0)
);

CREATE TABLE CULTURA (
    codigoCultura VARCHAR(20) PRIMARY KEY CONSTRAINT ck_CulturaPK CHECK (codigoCultura LIKE 'CUL-%'),
    permanencia VARCHAR(10) NOT NULL CONSTRAINT ck_Permanencia CHECK (UPPER(permanencia) = 'PERMANENTE' OR UPPER(permanencia) = 'TEMPORARIA'),
    tipoCultura VARCHAR(21) NOT NULL CONSTRAINT ck_TipoCultura CHECK (UPPER(tipoCultura) = 'CONSUMO HUMANO/ANIMAL' OR UPPER(tipoCultura) = 'ADUBACAO VERDE'),
    especie VARCHAR(20) NOT NULL,
    ciclo INTEGER NOT NULL
);


CREATE TABLE PRODUTO (
    codigoProduto VARCHAR(20) PRIMARY KEY CONSTRAINT ck_ProdutoPK CHECK (codigoProduto LIKE 'PRO-%'),
    preco DECIMAL(*,2) NOT NULL CONSTRAINT ck_preco1 CHECK (preco >= 0),
    unidade VARCHAR(10) NOT NULL,
    quantidade DECIMAL(*,2) NOT NULL CONSTRAINT ck_quantidade4 CHECK (quantidade >= 0)
);


CREATE TABLE PARCELA_CULTURA (
    codigoParcela VARCHAR(20),
    codigoCultura VARCHAR(20),
    area DECIMAL(*,2) NOT NULL CONSTRAINT ck_AreaZero CHECK (area > 0),
    dataDePlantacao DATE NOT NULL,
    CONSTRAINT pk_CodigoParcelaCultura PRIMARY KEY (codigoParcela, codigoCultura),
    CONSTRAINT fk_CodigoParcela1 FOREIGN KEY (codigoParcela) REFERENCES PARCELA(codigoParcela),
    CONSTRAINT fk_CodigoCultura1 FOREIGN KEY (codigoCultura) REFERENCES CULTURA(codigoCultura)
);

CREATE TABLE COLHEITA (
    codigoColheita VARCHAR(20) PRIMARY KEY CONSTRAINT ck_ColheitaPK CHECK (codigoColheita LIKE 'COL-%'),
    codigoParcela VARCHAR(20) NOT NULL,
    codigoCultura VARCHAR(20) NOT NULL,
    codigoProduto VARCHAR(20),
    dataDeColheita DATE NOT NULL,
    quantidade DECIMAL(*,2) NOT NULL CONSTRAINT ck_quantidade1 CHECK (quantidade > 0),
    area DECIMAL(*,2) NOT NULL CONSTRAINT ck_area2 CHECK (area > 0),

    CONSTRAINT fk_CodigoParcela2 FOREIGN KEY (codigoParcela) REFERENCES PARCELA (codigoParcela),
    CONSTRAINT fk_CodigoCultura2 FOREIGN KEY (codigoCultura) REFERENCES CULTURA (codigoCultura),
    CONSTRAINT fk_Colheita2 FOREIGN KEY (codigoColheita) REFERENCES COLHEITA (codigoColheita),
    CONSTRAINT fk_codProduto2 FOREIGN KEY (codigoProduto) REFERENCES PRODUTO (codigoProduto)
);

CREATE TABLE INPUT_HUB (
input_string VARCHAR(25)
);

CREATE TABLE HUB (
    idHub VARCHAR(10) PRIMARY KEY CONSTRAINT ck_HubPK CHECK (idHub LIKE 'CT%'),
    lat DECIMAL(*,4),
    lng DECIMAL(*,4),
    hubType VARCHAR(10) constraint ck_hubType check (hubType like 'C%' or hubType like 'E%' or hubType like 'P%')
);

CREATE TABLE CLIENTE (
    idCliente VARCHAR(20) PRIMARY KEY CONSTRAINT ck_ClientePK CHECK (idCliente LIKE 'ID-%'),
    nome VARCHAR(40) NOT NULL,
    nif NUMERIC (9,0) NOT NULL UNIQUE,
    email VARCHAR(30) NOT NULL UNIQUE CONSTRAINT CH_EMAIL CHECK (EMAIL LIKE '%@%'),
    moradaCorrespondencia VARCHAR(100) NOT NULL,
    moradaEntrega VARCHAR(100) NOT NULL,
    plafond DECIMAL(*,2) NOT NULL,
    nivel CHAR CONSTRAINT ck_nivel CHECK (nivel = 'A' OR nivel = 'B' OR nivel = 'C' OR nivel = NULL),
    idHub VARCHAR(10),
    CONSTRAINT fk_IdHub FOREIGN KEY (idHub) REFERENCES HUB(idHub)
);

CREATE TABLE ENCOMENDA (
    codigoEncomenda VARCHAR(20) PRIMARY KEY CONSTRAINT ck_EncomendaPK CHECK (codigoEncomenda LIKE 'ENC-%'),
    idCliente VARCHAR(20) NOT NULL,
    enderecoEntrega VARCHAR(100),
    dataEntrega DATE,
    estadoEncomenda VARCHAR(16) CONSTRAINT ck_estadoEncomenda CHECK (UPPER(estadoEncomenda) = 'REGISTADA' OR UPPER(estadoEncomenda) = 'EM PROCESSAMENTO' OR UPPER(estadoEncomenda) = 'ENTREGUE'),
    dataRegisto DATE DEFAULT SYSDATE,
    dataVencimento DATE DEFAULT SYSDATE + 2,
    dataPagamento DATE,
    estadoPagamento VARCHAR(18) CONSTRAINT ck_estadoPagamento CHECK (UPPER(estadoPagamento) = 'PAGAMENTO PENDENTE' OR UPPER(estadoPagamento) = 'PAGO' OR UPPER(estadoPagamento) = 'INCIDENTE'),
    idHub VARCHAR(10),
    CONSTRAINT fk_IdHub2 FOREIGN KEY (idHub) REFERENCES HUB(idHub),
    CONSTRAINT fk_IdCliente FOREIGN KEY (idCliente) REFERENCES CLIENTE(idCliente)
);

CREATE TABLE ENCOMENDA_PRODUTO(
    codigoEncomenda VARCHAR(20),
    codigoProduto VARCHAR(20),
    quantidade DECIMAL(*,2) NOT NULL CONSTRAINT ck_quantidade2 CHECK (quantidade > 0),
    CONSTRAINT pk_CodigoEncomendaProduto PRIMARY KEY (codigoEncomenda, codigoProduto),
    CONSTRAINT fk_CodigoEncomenda FOREIGN KEY (codigoEncomenda) REFERENCES ENCOMENDA(codigoEncomenda),
    CONSTRAINT fk_CodigoProduto FOREIGN KEY (codigoProduto) REFERENCES PRODUTO(codigoProduto)
);

CREATE TABLE OPERACAO_AGRICOLA(
    data DATE,
    codigoParcela VARCHAR(20),
    dataRealizacao Date,
    tipoOperacao VARCHAR(30) CONSTRAINT ck_NomeTipoOperacao CHECK (UPPER(tipoOperacao) = 'IRRIGACAO' OR UPPER(tipoOperacao) = 'APLICACAO DE FATOR DE PRODUCAO'),
    custo DECIMAL(*,2) NOT NULL CONSTRAINT ck_custo CHECK (custo >= 0),

    CONSTRAINT pk_operacao PRIMARY KEY (data, codigoParcela),
    CONSTRAINT fk_codParcela FOREIGN KEY (codigoParcela) REFERENCES PARCELA(codigoParcela)
);

CREATE TABLE FORNECEDOR(
    codigoFornecedor VARCHAR(20) PRIMARY KEY CONSTRAINT ck_FornecedorPK CHECK (codigoFornecedor LIKE 'FOR-%'),
    nome VARCHAR(50) NOT NULL,
    contacto NUMERIC(9,0) NOT NULL UNIQUE,
    email VARCHAR(30) NOT NULL UNIQUE CONSTRAINT CH_EmailFornecedor CHECK (EMAIL LIKE '%@%')
);

CREATE TABLE FATOR_PRODUCAO(
    codigoFator VARCHAR(20) PRIMARY KEY CONSTRAINT ck_FatorPK CHECK (codigoFator LIKE 'FAT-%'),
    preco DECIMAL(*,2) NOT NULL CONSTRAINT ck_preco2 CHECK (preco >= 0),
    nomeComercial VARCHAR(20) NOT NULL,
    tipoDeFator VARCHAR(19) CONSTRAINT ck_TipoDeFator CHECK (UPPER(tipoDeFator) = 'CORRETIVO MINERAL' OR UPPER(tipoDeFator) = 'FERTILIZANTE' OR UPPER(tipoDeFator) = 'ADUBO' OR UPPER(tipoDeFator) = 'PRODUTO FITOFORMACO'),
    formulacao VARCHAR(9) CONSTRAINT ck_Formulacao CHECK (UPPER(formulacao) = 'LIQUIDO' OR UPPER(formulacao) = 'PO' OR UPPER(formulacao) = 'GRANULADO'),
    codigoFornecedor VARCHAR(20) NOT NULL,
    CONSTRAINT fk_CodigoFornecedor FOREIGN KEY (codigoFornecedor) REFERENCES FORNECEDOR (codigoFornecedor)
);

CREATE TABLE RESTRICAO_FATOR(
    codigoFator VARCHAR(20),
    codigoParcela VARCHAR(20),
    dataInicio DATE DEFAULT SYSDATE,
    dataFim DATE NOT NULL,

    CONSTRAINT ck_maior CHECK (dataFim > dataInicio),

    CONSTRAINT pk_restricao PRIMARY KEY (codigoFator, codigoParcela),
    CONSTRAINT fk_fatorPro FOREIGN KEY (codigoFator) references FATOR_PRODUCAO (codigoFator),
    CONSTRAINT fk_parc FOREIGN KEY (codigoParcela) references PARCELA (codigoParcela)
);

CREATE TABLE FATOR_OPERACAO (
    codigoFator VARCHAR(20),
    codigoParcela VARCHAR(20),
    data DATE,
    unidadeProduto VARCHAR(10) NOT NULL,
    quantidadeProduto DECIMAL(*, 2) NOT NULL CONSTRAINT ck_quantidade3 CHECK (quantidadeProduto > 0),
    formaAplicacao VARCHAR(10) CONSTRAINT ck_NomeFormaDeAplicacao CHECK (UPPER(formaAplicacao) = 'FOLIAR' OR UPPER(formaAplicacao) = 'FERTIRREGA' OR UPPER(formaAplicacao) = 'NO SOLO'),
    CONSTRAINT pk_CodigoFatorProducao PRIMARY KEY (codigoFator, codigoParcela, data),
    CONSTRAINT fk_CodigoFator FOREIGN KEY (codigoFator) REFERENCES FATOR_PRODUCAO (codigoFator),
    CONSTRAINT fk_operAg FOREIGN KEY (codigoParcela, data) REFERENCES OPERACAO_AGRICOLA (codigoParcela, data)
);

CREATE TABLE ELEMENTO_SUBSTANCIA(
    codigoSubstancia VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    tipo VARCHAR(27),
    CONSTRAINT ck_TipoDeSubstancia CHECK ((codigoSubstancia LIKE 'SUB-%' AND  UPPER(tipo) = 'SUBSTANCIA ORGANICA') OR (codigoSubstancia LIKE 'ELE-%' AND  UPPER(tipo) = 'ELEMENTO NUTRITIVO ORGANICO'))
);

CREATE TABLE FICHA_TECNICA(
    codigoFator VARCHAR(20),
    codigoSubstancia VARCHAR(20),
    quantidade DECIMAL(*, 2) NOT NULL,
    unidade VARCHAR(10) NOT NULL,

    CONSTRAINT pk_CodigoFatorSubstancia PRIMARY KEY (codigoFator, codigoSubstancia),
    CONSTRAINT fk_CodigoFator1 FOREIGN KEY (codigoFator) REFERENCES FATOR_PRODUCAO (codigoFator),
    CONSTRAINT fk_CodigoSubstancia1 FOREIGN KEY (codigoSubstancia) REFERENCES ELEMENTO_SUBSTANCIA (codigoSubstancia)
);

CREATE TABLE SENSOR(
    identificadorSensor CHAR(5) PRIMARY KEY,
    valorReferencia INTEGER UNIQUE,
    tipoSensor CHAR(2) CONSTRAINT ck_tipoDeSensor check (UPPER(tipoSensor) = 'HS' or UPPER(tipoSensor) = 'PI' or UPPER(tipoSensor) = 'TS' or UPPER(tipoSensor) = 'VV' or UPPER(tipoSensor) = 'TA' or UPPER(tipoSensor) = 'HA' or UPPER(tipoSensor) = 'PA')
);

CREATE TABLE READ_ANALYSIS(
    codigoAnalise VARCHAR(20) PRIMARY KEY CONSTRAINT ck_readAnalysisPK CHECK (codigoAnalise LIKE 'AN-%'),
    dataExecucao DATE NOT NULL UNIQUE,
    nRegistosLidos INTEGER NOT NULL,
    nRegistosInseridos INTEGER NOT NULL,
    nRegistosComErros INTEGER NOT NULL
);

CREATE TABLE SENSOR_DATA_READ(
    instanteLeitura DATE,
    identificadorSensor CHAR(5),
    codigoAnalise VARCHAR(20),
    valorLido INTEGER CONSTRAINT ck_from0To100 CHECK (valorLido >= 0 and valorLido <= 100),

    CONSTRAINT pk_sensorDataRead PRIMARY KEY (instanteLeitura, identificadorSensor),

    CONSTRAINT fk_sensorCorrespondente FOREIGN KEY (identificadorSensor) REFERENCES SENSOR (identificadorSensor),
    CONSTRAINT fk_analiseRead FOREIGN KEY (codigoAnalise) REFERENCES READ_ANALYSIS (codigoAnalise)
);

CREATE TABLE INPUT_SENSOR(
    input_string VARCHAR(25) PRIMARY KEY
);

CREATE TABLE SENSOR_ERRORS(
    identificadorSensor CHAR(5),
    codigoAnalise VARCHAR(20),
    nRegistosComErros INTEGER NOT NULL,

    CONSTRAINT pk_sensorErrors PRIMARY KEY (identificadorSensor, codigoAnalise),

    CONSTRAINT fk_sendorId FOREIGN KEY (identificadorSensor) REFERENCES SENSOR (identificadorSensor),
    CONSTRAINT fk_analiseSen FOREIGN KEY (codigoAnalise) REFERENCES READ_ANALYSIS (codigoAnalise)
);

CREATE TABLE PISTAS_DE_AUDITORIA(
    idendificadorRegistoAuditoria INTEGER PRIMARY KEY,
    utilizador VARCHAR(20),
    dateTime TIMESTAMP,
    operacao VARCHAR(20),
    codigoParcela VARCHAR(20),
    data DATE,

    CONSTRAINT fk_pistasDeAuditoria FOREIGN KEY (codigoParcela, data) REFERENCES OPERACAO_AGRICOLA (codigoParcela, data)
);