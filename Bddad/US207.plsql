create or replace procedure pr_profit(codigoDaCultura CULTURA.CODIGOCULTURA%type)
is
    cursor cr_dados is select sum(c.QUANTIDADE / c.AREA), c.CODIGOPARCELA, p.DESIGNACAO, p.AREA
    from COLHEITA c
    inner join PARCELA p on p.CODIGOPARCELA = c.CODIGOPARCELA
    where c.CODIGOCULTURA = codigoDaCultura
    group by c.CODIGOPARCELA, p.DESIGNACAO, p.AREA
    order by sum(c.QUANTIDADE / c.AREA) desc;

    dadoSum COLHEITA.QUANTIDADE%type;
    dadoParcelaCod COLHEITA.CODIGOPARCELA%type;
    dadoParcelaDes PARCELA.DESIGNACAO%type;
    dadoParcelaArea PARCELA.AREA%type;

begin
    open cr_dados;
        loop
            fetch cr_dados into dadoSum, dadoParcelaCod, dadoParcelaDes, dadoParcelaArea;
            exit when cr_dados%notfound;
            dbms_output.PUT_LINE('Cod Parcela = ' || dadoParcelaCod || ' Designacao = ' || dadoParcelaDes || ' Area (hec) = ' || dadoParcelaArea || ' Quantity per hectar = ' || dadoSum || ' ton/hec');
        end loop;
    close cr_dados;
end;
/

begin
    PR_PROFIT('CUL-5');
end;
/

create or replace procedure pr_profitEuros(codigoDaCultura CULTURA.CODIGOCULTURA%type)
is
    cursor cr_dados is select  sum(((pro.PRECO * c.QUANTIDADE) - (op.CUSTO * c.AREA / p.AREA)) / (c.AREA * 1000)), p.CODIGOPARCELA, p.DESIGNACAO, p.AREA
    from COLHEITA c
    inner join PARCELA p on p.CODIGOPARCELA = c.CODIGOPARCELA
    inner join PRODUTO pro on pro.CODIGOPRODUTO = c.CODIGOPRODUTO
    inner join OPERACAO_AGRICOLA op on op.CODIGOPARCELA = c.CODIGOPARCELA
    where c.CODIGOCULTURA = codigoDaCultura
    group by p.CODIGOPARCELA, p.DESIGNACAO, p.AREA
    order by sum(pro.PRECO * c.QUANTIDADE / c.AREA) desc;

    dadoSum COLHEITA.QUANTIDADE%type;
    dadoParcelaCod COLHEITA.CODIGOPARCELA%type;
    dadoParcelaDes PARCELA.DESIGNACAO%type;
    dadoParcelaArea PARCELA.AREA%type;

begin
    open cr_dados;
        loop
            fetch cr_dados into dadoSum, dadoParcelaCod, dadoParcelaDes, dadoParcelaArea;
            exit when cr_dados%notfound;
            dbms_output.PUT_LINE('Cod Parcela = ' || dadoParcelaCod || ' Designacao = ' || dadoParcelaDes || ' Area (hec) = ' || dadoParcelaArea || ' Price per hectar = ' || dadoSum || ' K€/hec');
        end loop;
    close cr_dados;
end;
/

begin
    PR_PROFITEUROS('CUL-5');
end;
/