create or replace procedure pr_InsertEncomenda
(codigoEncomenda in ENCOMENDA.CODIGOENCOMENDA%type , idClient in ENCOMENDA.IDCLIENTE%type , enderecoEntrega in ENCOMENDA.ENDERECOENTREGA%type , dataEntrega in ENCOMENDA.DATAENTREGA%type,
estadoEncomenda in ENCOMENDA.ESTADOENCOMENDA%type, dataPagagamento in ENCOMENDA.DATAPAGAMENTO%type, estadoPagamento in ENCOMENDA.ESTADOPAGAMENTO%type)
as
enderecoEntregaIfNull ENCOMENDA.ENDERECOENTREGA%type;
plafondOfTheClient CLIENTE.PLAFOND%type;
begin
if (enderecoEntrega = null) then
    select cl.moradaEntrega into enderecoEntregaIfNull
    from cliente cl
    where cl.idcliente = idClient;

    insert into encomenda(codigoEncomenda, idCliente, dataEntrega, estadoEncomenda, dataPagamento, estadoPagamento) values(codigoEncomenda, idClient, enderecoEntregaIfNull, dataEntrega, dataPagagamento, estadoPagamento);

else
        insert into encomenda(codigoEncomenda, idCliente, dataEntrega, estadoEncomenda, dataPagamento, estadoPagamento) values(codigoEncomenda, idClient, enderecoEntrega, dataEntrega, dataPagagamento, estadoPagamento);

end if;

select c.PLAFOND into plafondOfTheClient
from CLIENTE c
where c.IDCLIENTE = idclient;

dbms_output.put_line(plafondOfTheClient || '€');
end;
/

begin
    pr_InsertEncomenda('ENC-49', 'ID-1', null, null, 'EM PROCESSAMENTO', null, 'PAGAMENTO PENDENTE');
end;
/


create or replace procedure pr_registerOrderDeliveryDate
(codigoEncomendaNew in ENCOMENDA.CODIGOENCOMENDA%type, dataEntregaNew in ENCOMENDA.DATAENTREGA%type)
as
begin
update encomenda e
set e.dataentrega = dataEntregaNew
where e.codigoencomenda = codigoEncomendaNew;
end;

begin
    pr_registerOrderDeliveryDate('ENC-1', to_date('09/12/2022', 'DD/MM/YYYY'));
end;


create or replace procedure pr_registerOrderOnACertainDate
(codigoEncomendaPar in ENCOMENDA.CODIGOENCOMENDA%type, dataPagamentoPar in ENCOMENDA.DATAPAGAMENTO%type)
as
begin
update encomenda e
set e.dataPagamento = dataPagamentoPar
where e.codigoEncomenda = codigoEncomendaPar;
end;

begin
   pr_REGISTERORDERONACERTAINDATE('ENC-1', to_date('03/12/2022 17:10', 'DD/MM/YYYY HH24:MI'));
end;

create or replace procedure pr_listByStateAndPaymentStatus(estadoEncomendaPar in ENCOMENDA.ESTADOENCOMENDA%type, estadoPagamentoPar in ENCOMENDA.ESTADOPAGAMENTO%type)
is
    cursor lbsaps_dados is select e.ESTADOENCOMENDA, e.ESTADOPAGAMENTO, e.CODIGOENCOMENDA, e.IDCLIENTE, e.DATAREGISTO, sum(trunc((p.PRECO * ep.QUANTIDADE),2))
    from ENCOMENDA e
    inner join ENCOMENDA_PRODUTO ep on ep.CODIGOENCOMENDA = e.CODIGOENCOMENDA
    inner join PRODUTO p on p.CODIGOPRODUTO = ep.CODIGOPRODUTO
    where e.ESTADOENCOMENDA = estadoEncomendaPar and e.ESTADOPAGAMENTO = estadoPagamentoPar
    group by e.ESTADOENCOMENDA, e.ESTADOPAGAMENTO, e.CODIGOENCOMENDA, e.IDCLIENTE, e.DATAREGISTO;

    dadoEncomenda ENCOMENDA.ESTADOENCOMENDA%type;
    dadoPagamento ENCOMENDA.ESTADOPAGAMENTO%type;
    dadoCodEncomenda ENCOMENDA.CODIGOENCOMENDA%type;
    dadoCliente ENCOMENDA.IDCLIENTE%type;
    dadoDataRegisto ENCOMENDA.DATAREGISTO%type;
    dadoValorTotal PRODUTO.PRECO%type ;

begin
       open lbsaps_dados;
           loop
               fetch lbsaps_dados into dadoEncomenda,dadoPagamento,dadoCodEncomenda,dadoCliente,dadoDataRegisto,dadoValorTotal;
               exit when lbsaps_dados%notfound;
               dbms_output.PUT_LINE('Estado: ' || dadoEncomenda || ' Pagamento: ' || dadoPagamento || ' Codigo Encomenda: ' || dadoCodEncomenda || ' ID Cliente: ' || dadoCliente || ' Data Registo: ' || dadoDataRegisto || ' Valor Total Encomenda: ' || dadoValorTotal || '€');
           end loop;
       close lbsaps_dados;
end;

begin
    pr_listByStateAndPaymentStatus('EM PROCESSAMENTO', 'PAGAMENTO PENDENTE');
end;

select * from encomenda







