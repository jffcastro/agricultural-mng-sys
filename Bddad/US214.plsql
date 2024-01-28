create or replace procedure pr_analyzeInTheLast5Years(codCultura cultura.codigoCultura%type, codParcela fact_producao_toneladas.codigoParcela%type) as
    cursor evolucaoProducao is select sum(fpt.quantidadeProducao), da.ano from fact_producao_toneladas fpt
    inner join dim_tempo dt on fpt.codigoTempo = dt.codigoTempo
    inner join dim_produto dp on fpt.codigoproduto = dp.codigoproduto
    inner join dim_tipo_cultura dtc on dp.codigotipocultura = dtc.codigotipocultura
    inner join dim_cultura dc on dtc.codigocultura = dc.codigocultura
    inner join dim_ano da on dt.codigoAno = da.codigoAno
    where fpt.codigoParcela = codParcela and dc.codigoCultura = codCultura and da.ano+5 >= extract(YEAR FROM sysdate)
    group by da.ano;

    auxiliar1 fact_producao_toneladas.quantidadeProducao%type;
    auxiliar2 dim_ano.ano%type;

begin
    open evolucaoProducao;
    loop
    fetch evolucaoProducao into auxiliar1,auxiliar2;
    exit when evolucaoProducao%notfound;
    dbms_output.PUT_LINE('Ano: ' || auxiliar2 || ' Produção em toneladas: ' || auxiliar1);
    end loop;
end;
/

begin
    pr_analyzeInTheLast5Years('CUL-2','PAR-3');
end;
/

create or replace procedure pr_compare2Years(ano1 int, ano2 int) as
    vendasAno1 fact_vendas_milhares_euros.quantiaVendas%type;
    vendasAno2 fact_vendas_milhares_euros.quantiaVendas%type;
begin
    select sum(fvme.quantiaVendas) into vendasAno1 from fact_vendas_milhares_euros fvme
    inner join dim_tempo dt on fvme.codigoTempo = dt.codigoTempo
    inner join dim_ano da on dt.codigoAno = da.codigoAno
    where da.ano = ano1;

    select sum(fvme.quantiaVendas) into vendasAno2 from fact_vendas_milhares_euros fvme
    inner join dim_tempo dt on fvme.codigoTempo = dt.codigoTempo
    inner join dim_ano da on dt.codigoano = da.codigoAno
    where da.ano = ano2;

    dbms_output.PUT_LINE('Lucros do ano ' || ano1 || ': ' || vendasAno1 || '€');
    dbms_output.PUT_LINE('Lucros do ano ' || ano2 || ': ' || vendasAno2 || '€');
end;
/

begin
    pr_compare2Years(2012,2019);
end;
/


create or replace view vw_evolutionMonthlyTypeCrop as
select sum(fvme.quantiaVendas) as Quantia, dm.mes, dtc.codigoTipocultura from fact_vendas_milhares_euros fvme
inner join dim_tempo dt on fvme.codigoTempo = dt.codigoTempo
inner join dim_mes dm on dt.codigoMes = dm.codigoMes
inner join dim_produto dp on fvme.codigoProduto = dp.codigoProduto
inner join dim_tipo_cultura dtc on dp.codigoTipoCultura = dtc.codigoTipoCultura
inner join dim_cultura dc on dtc.codigoCultura = dc.codigoCultura
group by dm.mes, dtc.codigoTipocultura;

