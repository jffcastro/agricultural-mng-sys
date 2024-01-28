create or replace trigger TR_AREA
before insert
on PARCELA_CULTURA
for each row
declare
    acumulador PARCELA_CULTURA.AREA%type;
    areaParcela PARCELA.AREA%type;
begin

    select count(AREA)
    into acumulador
    from PARCELA_CULTURA
    where :new.CODIGOPARCELA = CODIGOPARCELA;

    if (acumulador != 0) then
        select sum(AREA)
        into acumulador
        from PARCELA_CULTURA
        where :new.CODIGOPARCELA = CODIGOPARCELA;
    end if;

    select AREA
    into areaParcela
    from PARCELA
    where :new.CODIGOPARCELA = CODIGOPARCELA;

    if (:new.AREA + acumulador > areaParcela) then
        raise_application_error(-20004,'There isn´t enough space to this culture in this space.');
    end if;
end;
/

create or replace trigger tr_Colheita
before insert or update
on COLHEITA
for each row
declare
    areaCulturaOcupada PARCELA_CULTURA.AREA%type;
    areaSobra COLHEITA.AREA%type;
    quantProduto COLHEITA.QUANTIDADE%type;

begin

    if (updating) then
        if ((:new.CODIGOPARCELA != :old.CODIGOPARCELA) or (:new.CODIGOCULTURA != :old.CODIGOCULTURA) or (:new.CODIGOPRODUTO != :old.CODIGOPRODUTO)) then
            raise_application_error(-20011,'You cannot alter the sector, culture or product of the harvest!');
        end if;
    end if;

    select area
    into areaCulturaOcupada
    from PARCELA_CULTURA
    where :new.CODIGOCULTURA = CODIGOCULTURA and :new.CODIGOPARCELA = CODIGOPARCELA;

    if (updating) then
        areaSobra := areaCulturaOcupada - (:new.AREA - :old.AREA);
        quantProduto := :new.QUANTIDADE - :old.QUANTIDADE;
    else
        areaSobra := areaCulturaOcupada - :new.AREA;
        quantProduto := :new.QUANTIDADE;
    end if;

    if (areaSobra < 0) then
        raise_application_error(-20001,'Area exceeded!');
    else

        update PRODUTO
        set QUANTIDADE = QUANTIDADE + quantProduto
        where CODIGOPRODUTO = :new.CODIGOPRODUTO;

        if (areaSobra = 0) then
            delete
            from PARCELA_CULTURA
            where :new.CODIGOCULTURA = CODIGOCULTURA and :new.CODIGOPARCELA = CODIGOPARCELA;
        else
            update PARCELA_CULTURA
            set AREA = areaSobra
            where :new.CODIGOCULTURA = CODIGOCULTURA and :new.CODIGOPARCELA = CODIGOPARCELA;
        end if;
    end if;
end;
/

create or replace trigger TR_NIVEL
before insert
on ENCOMENDA
for each row
declare
    contador int;
    acumulador int;
    auxiliar ENCOMENDA.CODIGOENCOMENDA%type;
    cursor alteracaoIncidente is select CODIGOENCOMENDA from ENCOMENDA enc
                              where enc.IDCLIENTE = :new.idCliente and
                              enc.ESTADOPAGAMENTO = 'PAGAMENTO PENDENTE' and
                              sysdate > enc.DATAVENCIMENTO;
begin
    open alteracaoIncidente;
        loop
            fetch alteracaoIncidente into auxiliar;
            exit when alteracaoIncidente%notfound;
            update ENCOMENDA enc
            set ESTADOPAGAMENTO = 'INCIDENTE'
            where enc.CODIGOENCOMENDA = auxiliar;
        end loop;
    close alteracaoIncidente;

    select count(*) into contador from ENCOMENDA enc
    where enc.IDCLIENTE = :new.IDCLIENTE and
    enc.ESTADOPAGAMENTO = 'INCIDENTE' and
    enc.DATAVENCIMENTO > sysdate - 365;

    select sum(ep.QUANTIDADE * p.PRECO) into acumulador
    from ENCOMENDA enc
    inner join ENCOMENDA_PRODUTO ep on enc.CODIGOENCOMENDA = ep.CODIGOENCOMENDA
    inner join PRODUTO p on ep.CODIGOPRODUTO  = p.CODIGOPRODUTO
    where enc.IDCLIENTE = :new.IDCLIENTE and enc.DATAVENCIMENTO > sysdate - 365;

    if (contador = 0) then
        if(acumulador >= 10000) then
            update CLIENTE c
            set NIVEL = 'A'
            where :new.IDCLIENTE = c.IDCLIENTE;
        else if (acumulador >= 5000) then
            update CLIENTE c
            set NIVEL = 'B'
            where :new.IDCLIENTE = c.IDCLIENTE;
            end if;
        end if;
    else
        update CLIENTE c
        set NIVEL = 'C'
        where :new.IDCLIENTE = c.IDCLIENTE;
    end if;

end;
/

create or replace trigger TR_EnoughProduct
before insert or update
on ENCOMENDA_PRODUTO
for each row
declare
    proQuantity PRODUTO.QUANTIDADE%type;
    proQuantityToBuy ENCOMENDA_PRODUTO.QUANTIDADE%type;
begin

    if (updating) then
        proQuantityToBuy := :new.QUANTIDADE - :old.QUANTIDADE;
    else
        proQuantityToBuy := :new.QUANTIDADE;
    end if;

    select QUANTIDADE
    into proQuantity
    from PRODUTO
    where CODIGOPRODUTO = :new.CODIGOPRODUTO;

    if (proQuantityToBuy > proQuantity) then
        raise_application_error(-20010,'There is not enough product!');
    end if;

    update PRODUTO
    set QUANTIDADE = QUANTIDADE - proQuantityToBuy
    where CODIGOPRODUTO = :new.CODIGOPRODUTO;

end;
/

create or replace trigger TR_PlafoundExceeded
before insert or update
on ENCOMENDA_PRODUTO
for each row
declare
    totalPendente PRODUTO.PRECO%type;
    totalplafond CLIENTE.PLAFOND%type;
    clienteEncomenda CLIENTE.IDCLIENTE%type;
    priceNewProduct PRODUTO.PRECO%type;
    totalProdutos int;
    estadoAtualEncomenda ENCOMENDA.ESTADOPAGAMENTO%type;
begin

    select e.ESTADOPAGAMENTO
    into estadoAtualEncomenda
    from ENCOMENDA e
    where e.CODIGOENCOMENDA = :new.CODIGOENCOMENDA;

    if (estadoAtualEncomenda != 'PAGO') then
        select IDCLIENTE
        into clienteEncomenda
        from ENCOMENDA
        where :new.CODIGOENCOMENDA = CODIGOENCOMENDA;

        select count(*)
        into totalProdutos
        from ENCOMENDA e
        inner join ENCOMENDA_PRODUTO ep on ep.CODIGOENCOMENDA = e.CODIGOENCOMENDA
        where clienteEncomenda = e.IDCLIENTE and e.ESTADOPAGAMENTO = 'PAGAMENTO PENDENTE';
        if (totalProdutos > 0) then
            select sum(trunc((p.PRECO * ep.QUANTIDADE),2))
            into totalPendente
            from ENCOMENDA e
            inner join ENCOMENDA_PRODUTO ep on ep.CODIGOENCOMENDA = e.CODIGOENCOMENDA
            inner join PRODUTO p on p.CODIGOPRODUTO = ep.CODIGOPRODUTO
            where clienteEncomenda = e.IDCLIENTE and e.ESTADOPAGAMENTO = 'PAGAMENTO PENDENTE';
        else
            totalPendente := 0;
        end if;

        select Preco
        into priceNewProduct
        from PRODUTO
        where :new.CODIGOPRODUTO = CODIGOPRODUTO;

        if (updating ) then
            totalPendente := totalPendente + (priceNewProduct * (:new.QUANTIDADE - :old.QUANTIDADE));
        else
            totalPendente := totalPendente + (priceNewProduct * :new.QUANTIDADE);
        end if;

        select plafond
        into totalplafond
        from CLIENTE
        where clienteEncomenda = IDCLIENTE;

        if (totalplafond < totalPendente) then
            raise_application_error(-20002,'Plafond Exceeded!');
        else
            dbms_output.put_Line('Your Product was added with success to your order!');
        end if;
    end if;
end;
/

create or replace trigger tr_deliveryAddress
before insert or update
on ENCOMENDA
for each row
declare
    morada CLIENTE.MORADAENTREGA%type;
begin

    if (:new.ENDERECOENTREGA is null) then
        select MORADAENTREGA
        into MORADA
        from CLIENTE
        where :new.IDCLIENTE = IDCLIENTE;

        :new.ENDERECOENTREGA := morada;

     end if;
end;
/

create or replace trigger tr_changeDataOrder
before insert
on ENCOMENDA
for each row
begin
    :new.DATAREGISTO := sysdate;
    :new.DATAVENCIMENTO := sysdate + 2;
end;
/

create or replace trigger tr_ClientNoLevel
before insert
on CLIENTE
for each row
begin
    :new.NIVEL := null;
end;
/

create or replace trigger tr_dataPagamentoInverso
before insert or update of DATAPAGAMENTO on ENCOMENDA
for each row
begin
    if (:new.ESTADOPAGAMENTO != 'PAGO' and :new.DATAPAGAMENTO is not null) then
        if (:new.DATAPAGAMENTO is not null) then
            if (:old.DATAPAGAMENTO is not null) then
                raise_application_error(-20002,'It is already paid');
            else
                if (:new.DATAPAGAMENTO > sysdate) then
                    raise_application_error(-20002,'The date is after today');
                end if;
                if (:new.DATAPAGAMENTO < :new.DATAREGISTO) then
                    raise_application_error(-20002,'Invalid Date');
                else
                    if (:new.DATAPAGAMENTO > :new.DATAVENCIMENTO) then
                        raise_application_error(-20002,'You missed the pay date');
                    end if;
                end if;
            end if;
            :new.ESTADOPAGAMENTO := 'PAGO';
        else
            raise_application_error(-20002,'You did not insert a pay day');
        end if;
    else
        if (:new.ESTADOPAGAMENTO = 'PAGO' and :new.DATAPAGAMENTO is null) then
            raise_application_error(-20002,'You did not insert a pay day');
        end if;
    end if;
end;
/

create or replace trigger tr_dataEntregaInverso
before insert or update of DATAENTREGA on ENCOMENDA
for each row
begin
    if (:new.ESTADOENCOMENDA != 'ENTREGUE' and :new.DATAENTREGA is not null) then
        if (:new.DATAENTREGA is not null) then
            if (:old.DATAPAGAMENTO is not null) then
                raise_application_error(-20002,'It is already been delivered');
            else
                if (:new.DATAENTREGA > sysdate) then
                    raise_application_error(-20002,'The date is after today');
                end if;
            end if;
            :new.ESTADOENCOMENDA := 'ENTREGUE';
        else
            raise_application_error(-20002,'You did not insert a delivery day');
        end if;
    else
        if (:new.ESTADOENCOMENDA = 'ENTREGUE' and :new.DATAENTREGA is null) then
            raise_application_error(-20002,'You did not insert a delivery day');
        end if;
    end if;
end;
/

create or replace trigger tr_onlyOneOperationPerDay
before insert or update
on OPERACAO_AGRICOLA
for each row
declare
    dataOfOpe OPERACAO_AGRICOLA.DATA%type;
begin
    select DATA
    into dataOfOpe
    from OPERACAO_AGRICOLA
    where to_char(DATA, 'DD/MM/YYYY') = to_char(:new.DATA, 'DD/MM/YYYY') and CODIGOPARCELA = :new.CODIGOPARCELA;
    raise_application_error(-20002,'You cannot create two operations to the same date and same sector');
    exception
        when no_data_found then
            dbms_output.PUT_LINE('Your operation was created with success');
end;
/

create or replace trigger tr_restrictions
before insert or update
on FATOR_OPERACAO
for each row
begin
    PR_RESTRICTIONSNODELETE(:new.CODIGOFATOR,:new.CODIGOPARCELA,:new.DATA);
end;
/


create or replace trigger tr_restrictionsWeek
after insert or update
on FATOR_OPERACAO
for each row
declare
    subFat char;
    subPar char;
    subData varchar(20);
    PRAGMA autonomous_transaction;
begin

    subFat := substr(:new.CODIGOFATOR, 5);
    subPar := substr(:new.CODIGOPARCELA, 5);
    subData := replace(:new.DATA,'.','_');

    dbms_scheduler.create_job(
                             job_name => 'REST_1' || subFat || subPar || subData,
                             program_name => 'ProRestricao8',
                             start_date => :new.DATA-7,
                             enabled => false,
                             auto_drop => true
                             );
    dbms_scheduler.set_job_argument_value(job_name => 'REST_1' || subFat || subPar || subData,
                                         argument_name => 'codFator',
                                         argument_value => :new.CODIGOFATOR);
    dbms_scheduler.set_job_argument_value(job_name => 'REST_1' || subFat || subPar || subData,
                                         argument_name => 'codParcela',
                                         argument_value => :new.CODIGOPARCELA);
    dbms_scheduler.set_job_argument_value(job_name => 'REST_1' || subFat || subPar || subData,
                                         argument_name => 'day',
                                         argument_value => :new.DATA);
    dbms_scheduler.enable('REST_1' || subFat || subPar || subData);

    commit;

end;
/


create or replace trigger tr_clientDefaultHub
before insert or update
on CLIENTE
for each row
declare
cursor hubs is select idHub from HUB;
chosenHub CLIENTE.idHub%type;
counter integer;
begin
    select count (*) into counter from HUB;
    if  (counter != 0) then
        if (:new.idHub is null) then
        open hubs;
        fetch hubs into chosenHub;
        close hubs;
        :new.idHub := chosenHub;
        end if;
    end if;
end;
/


create or replace trigger tr_fillHub
after insert or update
on INPUT_HUB
for each row
declare
locId HUB.idHub%type;
lat HUB.lat%type;
lng HUB.lng%type;
hubType HUB.hubType%type;
begin

    locId := REGEXP_SUBSTR(:new.input_string, '[^;]+', 1, 1);
    lat := to_number(REGEXP_SUBSTR(:new.input_string, '[^;]+', 1, 2));
    lng := to_number(REGEXP_SUBSTR(:new.input_string, '[^;]+', 1, 3));
    hubType := REGEXP_SUBSTR(:new.input_string, '[^;]+', 1, 4);

    INSERT INTO hub values (locId, lat, lng, hubType);

end;
/


create or replace trigger tr_orderHub
before insert or update
on ENCOMENDA
for each row
begin

    if (:new.idHub is null) then
    select idHub
    into :new.idHub
    from CLIENTE
    where idCliente=:new.idCliente;
    end if;

end;
/
