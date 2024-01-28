DROP TABLE FACT_VENDAS_MILHARES_EUROS;
DROP TABLE FACT_PRODUCAO_TONELADAS;
DROP TABLE DIM_PARCELA;
DROP TABLE DIM_CLIENTE;
DROP TABLE DIM_TEMPO;
DROP TABLE DIM_ANO;
DROP TABLE DIM_MES;
DROP TABLE DIM_PRODUTO;
DROP TABLE DIM_TIPO_CULTURA;
DROP TABLE DIM_CULTURA;
DROP TABLE DIM_TIPO_HUB;
DROP TABLE DIM_HUB;


CREATE TABLE DIM_TIPO_HUB(
    codigoTipoHub VARCHAR(8) PRIMARY KEY CONSTRAINT ck_codigoTipoHub CHECK (codigoTipoHub LIKE 'E%'),
    descricaoTipo VARCHAR(20)
);

CREATE TABLE DIM_HUB(
    codigoHub VARCHAR(8) PRIMARY KEY CONSTRAINT ck_codigoHub CHECK (codigoHub LIKE 'HUB-%'),
    nomeHub VARCHAR(20),
    codigoTipoHub VARCHAR(8),

    CONSTRAINT fk_codigoTipoHub FOREIGN KEY (codigoTipoHub) REFERENCES DIM_TIPO_HUB(codigoTipoHub)
);

CREATE TABLE DIM_CULTURA(
    codigoCultura VARCHAR(15) PRIMARY KEY CONSTRAINT ck_culturaCUL CHECK (codigoCultura LIKE 'CUL-%'),
    nomeCultura VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE DIM_TIPO_CULTURA(
    codigoTipoCultura VARCHAR(15) PRIMARY KEY CONSTRAINT ck_tipoCulturaTC CHECK (codigoTipoCultura LIKE 'TC-%'),
    codigoCultura VARCHAR(15),
    nomeTipoCultura VARCHAR(30) NOT NULL UNIQUE,

    CONSTRAINT fk_codCultura FOREIGN KEY (codigoCultura) REFERENCES DIM_CULTURA (codigoCultura)
);

CREATE TABLE DIM_PRODUTO(
    codigoProduto VARCHAR(15) PRIMARY KEY CONSTRAINT ck_ProdutoPRO CHECK (codigoProduto LIKE 'PRO-%'),
    codigoTipoCultura VARCHAR(15),
    nomeProduto VARCHAR(30) NOT NULL UNIQUE,

    CONSTRAINT fk_codTipoCultura FOREIGN KEY (codigoTipoCultura) REFERENCES DIM_TIPO_CULTURA (codigoTipoCultura)
);

CREATE TABLE DIM_MES(
    codigoMes VARCHAR(4) PRIMARY KEY CONSTRAINT ck_MesM CHECK (codigoMes LIKE 'M-%'),
    mes INTEGER NOT NULL UNIQUE CONSTRAINT ck_mesCorreto CHECK (mes > 0 AND mes < 13)
);

CREATE TABLE DIM_ANO(
    codigoAno VARCHAR(6) PRIMARY KEY CONSTRAINT ck_AnoA CHECK (codigoAno LIKE 'A-%'),
    ano INTEGER NOT NULL UNIQUE CONSTRAINT ck_anoCorreto CHECK (ano > 1800 AND ano < 2300)
);

CREATE TABLE DIM_TEMPO(
    codigoTempo VARCHAR(8) PRIMARY KEY CONSTRAINT ck_TempoT CHECK (codigoTempo LIKE 'T-%'),
    codigoAno VARCHAR(6),
    codigoMes VARCHAR(4),

    CONSTRAINT fk_ano FOREIGN KEY (codigoAno) REFERENCES DIM_ANO(codigoAno),
    CONSTRAINT fk_mes FOREIGN KEY (codigoMes) REFERENCES DIM_MES(codigoMes)
);

CREATE TABLE DIM_CLIENTE(
    codigoCliente VARCHAR(15) PRIMARY KEY CONSTRAINT ck_ClienteID CHECK (codigoCliente LIKE 'ID-%'),
    nomeCliente VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE DIM_PARCELA(
    codigoParcela VARCHAR(15) PRIMARY KEY CONSTRAINT ck_ParcelaPAR CHECK (codigoParcela LIKE 'PAR-%'),
    nomeParcela VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE FACT_PRODUCAO_TONELADAS(
    codigoTempo VARCHAR(8),
    codigoParcela VARCHAR(15),
    codigoProduto VARCHAR(15),
    quantidadeProducao DECIMAL(*,2) NOT NULL CONSTRAINT ck_quantidadePro CHECK (quantidadeProducao >= 0),

    CONSTRAINT pk_producaoToneladas PRIMARY KEY (codigoTempo, codigoParcela, codigoProduto, quantidadeProducao),

    CONSTRAINT fk_tonTempo FOREIGN KEY (codigoTempo) REFERENCES DIM_TEMPO (codigoTempo),
    CONSTRAINT fk_tonParcela FOREIGN KEY (codigoParcela) REFERENCES DIM_PARCELA (codigoParcela),
    CONSTRAINT fk_tonProduto FOREIGN KEY (codigoProduto) REFERENCES DIM_PRODUTO (codigoProduto)
);

CREATE TABLE FACT_VENDAS_MILHARES_EUROS(
    codigoTempo VARCHAR(8),
    codigoProduto VARCHAR(20),
    codigoCliente VARCHAR(20),
    quantiaVendas DECIMAL(*,2) NOT NULL CONSTRAINT ck_quantiaVen CHECK (quantiaVendas >= 0),
    codigoHub VARCHAR(8),

    CONSTRAINT pk_vendasMilharesEuros PRIMARY KEY (codigoTempo, codigoProduto, codigoCliente, quantiaVendas, codigoHub),

    CONSTRAINT fk_MilTempo FOREIGN KEY (codigoTempo) REFERENCES DIM_TEMPO (codigoTempo),
    CONSTRAINT fk_MilProduto FOREIGN KEY (codigoProduto) REFERENCES DIM_PRODUTO (codigoProduto),
    CONSTRAINT fk_MilCliente FOREIGN KEY (codigoCliente) REFERENCES DIM_CLIENTE (codigoCliente),
    CONSTRAINT fk_vmeCodigoHub FOREIGN KEY (codigoHub) REFERENCES DIM_HUB (codigoHub)
);

