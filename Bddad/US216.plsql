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


create or replace view vw_evolutionMonthlyTypeCropAndHub as
select sum(fvme.quantiaVendas) as Vendas_Mensais, dm.mes, dtc.codigoTipoCultura, dh.codigoHub
from fact_vendas_milhares_euros fvme, dim_tempo dt, dim_mes dm, dim_produto dp, dim_tipo_cultura dtc, dim_hub dh
where fvme.codigoTempo = dt.codigoTempo and dt.codigoMes = dm.codigoMes and fvme.codigoProduto = dp.codigoProduto
and dp.codigoTipoCultura = dtc.codigoTipoCultura  and
fvme.codigoHub = dh.codigoHub
group by dm.mes, dtc.codigoTipoCultura, dh.codigoHub;

select * from vw_evolutionMonthlyTypeCropAndHub;





