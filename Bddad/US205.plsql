create or replace procedure pr_InsertUser
(idCliente in varchar, nome in varchar, nif in numeric, email in varchar,
moradaCorrespondencia in varchar, moradaEntrega in varchar, plafond in numeric, nivel in char)
as
begin
insert into Cliente values (idCliente,nome,nif,email,moradaCorrespondencia,moradaEntrega,plafond,nivel);
dbms_output.PUT_LINE('Cliente inserido com sucesso');
exception
    when others then
          raise_application_error(-20002,'Erro a inserir cliente');
end;

begin
 pr_InsertUser('ID-2313','Joao',230331221,'joaoc@test.com', 'Famacity', 'Fama', 2200, 'C');
end;

create or replace procedure pr_refreshTotalNumberAndValueOfSalesLastYear
is
    cursor rtnavosly_dados is select e.IDCLIENTE, count(distinct e.CODIGOENCOMENDA), sum(trunc((p.PRECO * ep.QUANTIDADE),2))
    from ENCOMENDA e, ENCOMENDA_PRODUTO ep, PRODUTO p
    where e.CODIGOENCOMENDA = ep.CODIGOENCOMENDA and ep.CODIGOPRODUTO = p.CODIGOPRODUTO and e.DATAREGISTO > sysdate - 365
    group by e.IDCLIENTE
    order by IDCLIENTE;

    dadoCliente ENCOMENDA.IDCLIENTE%type;
    dadoNumeroEncomendas int;
    dadoValorTotal PRODUTO.PRECO%type ;
begin
    open rtnavosly_dados;
        loop
            fetch rtnavosly_dados into dadoCliente, dadoNumeroEncomendas, dadoValorTotal;
            exit when rtnavosly_dados%notfound;
            dbms_output.PUT_LINE('ID Cliente = ' || dadoCliente || ' Numero de Encomendas = ' || dadoNumeroEncomendas || ' Valor Total: ' || dadoValorTotal || '€');
        end loop;
    close rtnavosly_dados;
end;

begin
    pr_REFRESHTOTALNUMBERANDVALUEOFSALESLASTYEAR;
end;

select * from produto



create or replace function func_returnRiskFactor(ClienteID in CLIENTE.IDCLIENTE%type)
return PRODUTO.PRECO%type
is

FatorRisco1 PRODUTO.PRECO%type;
FatorRisco2 int;
FatorRiscoFinal PRODUTO.PRECO%type;
DataUltimoIncidente ENCOMENDA.DATAREGISTO%type;
Contador int;

begin

    select count(*) into Contador
    from CLIENTE
    where CLIENTE.IDCLIENTE = CLIENTEID;

    if Contador = 0 then
    raise_application_error(-20002,'Cliente nao existe');
    end if;


    select sum(trunc((p.PRECO * ep.QUANTIDADE),2)) into FatorRisco1
    from ENCOMENDA e, PRODUTO p, ENCOMENDA_PRODUTO ep
    where e.ESTADOENCOMENDA = 'INCIDENTE' and ClienteID = e.IDCLIENTE and e.DATAVENCIMENTO > sysdate - 365 ;

    select max(e.DATAVENCIMENTO) into DataUltimoIncidente
    from ENCOMENDA e
    where e.ESTADOENCOMENDA = 'INCIDENTE' and ClienteID = e.IDCLIENTE and e.DATAVENCIMENTO > sysdate - 365;

    select count(distinct e.CODIGOENCOMENDA) into FatorRisco2
    from ENCOMENDA e
    where e.DATAREGISTO > DataUltimoIncidente  and ClienteID = e.IDCLIENTE;

    if FatorRisco2 = 0 then
    raise_application_error(-20002,'O cliente em questao nao tem encomendas incidentes ou então nao tem nenhuma encomenda depois da ultima data de incidencia');
    end if;

    FatorRiscoFinal := FatorRisco1/FatorRisco2;

    return FatorRiscoFinal;

end;

select * from encomenda

declare
riskFactor PRODUTO.PRECO%type;
begin
riskFactor:=func_RETURNRISKFACTOR('ID-1');
dbms_output.PUT_LINE('Risk Factor of that client is: ' || riskFactor || '€');
end;

create or replace view vw_us205a as
select c.idcliente, c.nome, c.nivel
from cliente c

select * from us205a

create or replace view vw_us205b as
select c.idcliente, c.nome, e.codigoencomenda, max(e.datavencimento) as "Data ultimo incidente"
from cliente c, encomenda e
where c.idcliente = e.idcliente and e.estadopagamento = 'INCIDENTE'
group by c.idcliente, c.nome, e.codigoencomenda

select * from us205b

create or replace view vw_us205c as
select c.idcliente, c.nome, count(distinct e.codigoencomenda) as "Volume total de encomendas pagas no ultimo ano"
from cliente c, encomenda e
where c.idcliente = e.idcliente and e.dataregisto > sysdate -365 and e.estadopagamento = 'PAGO'
group by c.idcliente, c.nome

select * from us205c


create or replace view vw_us205d as
select c.idcliente, c.nome, count(distinct e.codigoencomenda) as "Volume total de encomendas entregues mas ainda pendentes de pagamento"
from cliente c, encomenda e
where c.idcliente = e.idcliente and e.estadoencomenda = 'ENTREGUE' and e.estadopagamento = 'PAGAMENTO PENDENTE'
group by c.idcliente, c.nome

select * from us205d




