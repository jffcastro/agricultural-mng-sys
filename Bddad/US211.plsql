insert into OPERACAO_AGRICOLA (DATA, CODIGOPARCELA, TIPOOPERACAO, CUSTO) values (to_date('18/11/2023', 'DD/MM/YYYY'), 'PAR-1', 'IRRIGACAO', 2000);
insert into FATOR_OPERACAO (CODIGOFATOR, CODIGOPARCELA, DATA, UNIDADEPRODUTO, QUANTIDADEPRODUTO, FORMAAPLICACAO) values ('FAT-1', 'PAR-1', to_date('18/11/2023', 'DD/MM/YYYY'), 'Kg', 30.23, 'FOLIAR');
insert into FATOR_OPERACAO (CODIGOFATOR, CODIGOPARCELA, DATA, UNIDADEPRODUTO, QUANTIDADEPRODUTO, FORMAAPLICACAO) values ('FAT-3', 'PAR-1', to_date('18/11/2023', 'DD/MM/YYYY'), 'Kg', 12.65, 'NO SOLO');


begin
    pr_updateFactorOperation(to_date('18/11/2023', 'DD/MM/YYYY'),'PAR-1','FAT-1','FOLIAR','1','unit');
end;

begin
    pr_updateOperation(to_date('18/11/2023', 'DD/MM/YYYY'),'PAR-1',to_date('18/11/2029', 'DD/MM/YYYY'),'','','');
end;

begin
    pr_updateOperation(to_date('18/11/2023', 'DD/MM/YYYY'),'PAR-1',to_date('25/11/2025', 'DD/MM/YYYY'),sysdate-1,'IRRIGACAO',2000);
end;


begin
    pr_deleteFactorOperation(to_date('18/11/2023', 'DD/MM/YYYY'),'PAR-1','FAT-1');
end;
begin
    pr_deleteFactorOperation(to_date('18/11/2023', 'DD/MM/YYYY'),'PAR-1','FAT-3');
end;

begin
    PR_CANCELOPERATION(to_date('18/11/2023', 'DD/MM/YYYY'),'PAR-1');
end;


select * from OPERACAO_AGRICOLA;
select * from FATOR_OPERACAO;
select * from PARCELA;


create or replace function func_checkIfOperationExists(dataOperacao OPERACAO_AGRICOLA."DATA"%type, codigoParcelaOperacao OPERACAO_AGRICOLA.CODIGOPARCELA%type)
return integer
is
contador int;
begin
   select count(*) into contador
   from OPERACAO_AGRICOLA oa
   where dataOperacao = oa."DATA" and codigoParcelaOperacao = oa.CODIGOPARCELA;
   return contador;
end;
/

create or replace function func_checkIfProductIsAlreadyScheduled(codFatorSch FATOR_OPERACAO.CODIGOFATOR%type, codigoParcelaSch FATOR_OPERACAO.CODIGOPARCELA%type,dataSch FATOR_OPERACAO."DATA"%type)
return integer
is
contador int;
begin
   select count(*) into contador
   from FATOR_OPERACAO fo
   where codFatorSch = fo.CODIGOFATOR and codigoParcelaSch = fo.CODIGOPARCELA and dataSch = fo."DATA";
   return contador;
end;
/

create or replace trigger tr_checkIfOperationWasAlreadyDone
before delete or update or insert
on OPERACAO_AGRICOLA
for each row
begin
    if (deleting) then
        if :old.DATAREALIZACAO < sysdate and :old.DATAREALIZACAO != null then
            raise_application_error(-20002,'The operation was already done, not able to delete it.');
        else
            delete from FATOR_OPERACAO fo
            where :old."DATA" = fo."DATA" and :old.CODIGOPARCELA = fo.CODIGOPARCELA;
            DBMS_OUTPUT.PUT_LINE('A operacao agricola foi removida juntamente com todas as entradas fator_operacao associadas');
        end if;
    end if;

     if (updating) then

        if :old.DATAREALIZACAO < sysdate and :old.DATAREALIZACAO != null then
            raise_application_error(-20002,'Not possible since this date is in the past');

        elsif :new."DATA" < sysdate or :new.DATAREALIZACAO < sysdate then
            raise_application_error(-20002,'The assumed date is in the past');

        else

            if :new."DATA" is null then
            :new."DATA" := sysdate + 1;
            end if;

            if :new.DATAREALIZACAO is null then
            :new."DATAREALIZACAO" := sysdate + 1;
            end if;

            update FATOR_OPERACAO fo
            set fo.data = :new."DATA"
            where :old."DATA" = fo."DATA"  and :old.CODIGOPARCELA = fo.CODIGOPARCELA;

            DBMS_OUTPUT.PUT_LINE('A operacao agricola foi atualizada juntamente com todas as entradas fator_operacao associadas');


       end if;
       end if;

    if (inserting) then

        if :new."DATA" < sysdate or :new.DATAREALIZACAO < sysdate then
         raise_application_error(-20002,'The date inserted is less than sysdate, please try again.');
         end if;

        if :new.DATA is null then
            :new.DATA := sysdate + 1;
        end if;

        if :new.DATAREALIZACAO is null then
            :new.DATAREALIZACAO := sysdate + 1;
        end if;

    end if;
end;
/


create or replace trigger tr_checkIfFactorOperationWasAlreadyDone
before update or insert
on FATOR_OPERACAO
for each row
begin

    if (updating) then
        if :old."DATA" < sysdate then
        raise_application_error(-20002,'The date is in the past, try again.');
        end if;
    end if;

    if (inserting) then
        if :new."DATA" < sysdate then
         raise_application_error(-20002,'The date inserted is less than sysdate, please try again.');
         end if;
    end if;
end;
/


create or replace procedure pr_cancelOperation(dataOperacao OPERACAO_AGRICOLA."DATA"%type, codigoParcelaOperacao OPERACAO_AGRICOLA.CODIGOPARCELA%type)
is

contador int;

begin
    contador := FUNC_CHECKIFOPERATIONEXISTS(DATAOPERACAO, CODIGOPARCELAOPERACAO);

   if contador > 0 then
    delete from OPERACAO_AGRICOLA oa
    where dataOperacao = oa."DATA" and codigoParcelaOperacao = oa.CODIGOPARCELA;

   else
    raise_application_error(-20002,'Não existe nenhuma operação agricola a realizar nessa parcela na data mencionada');
   end if;
end;
/



create or replace procedure pr_deleteFactorOperation(dataOperacao FATOR_OPERACAO."DATA"%type, codigoParcelaOperacao FATOR_OPERACAO.CODIGOPARCELA%type, codFator FATOR_OPERACAO.CODIGOFATOR%type)
is

contador int;

begin
    contador := FUNC_CHECKIFPRODUCTISALREADYSCHEDULED(CODFATOR,CODIGOPARCELAOPERACAO,DATAOPERACAO);

    if contador > 0 then
        delete from FATOR_OPERACAO fo
        where dataOperacao = fo."DATA" and codigoParcelaOperacao = fo.CODIGOPARCELA and codFator = fo.CODIGOFATOR;
        DBMS_OUTPUT.PUT_LINE('A entrada fator operacao foi apagada com sucesso');
    else
    raise_application_error(-20002,'Não existe nenhum fator com essa primary key');
    end if;
end;
/

create or replace procedure pr_updateOperation(dataOperacao OPERACAO_AGRICOLA."DATA"%type, codigoParcelaOperacao OPERACAO_AGRICOLA.CODIGOPARCELA%type,
dataOperacaoQueVamosAlterar OPERACAO_AGRICOLA."DATA"%type, dataRealizacao OPERACAO_AGRICOLA.DATAREALIZACAO%type ,tipoOp OPERACAO_AGRICOLA.TIPOOPERACAO%type, custo OPERACAO_AGRICOLA.CUSTO%type)
is

contadorCheckOpExists int;

begin
   contadorCheckOpExists := FUNC_CHECKIFOPERATIONEXISTS(DATAOPERACAO, CODIGOPARCELAOPERACAO);

   if contadorCheckOpExists > 0 then

        update OPERACAO_AGRICOLA oa
        set oa."DATA" = dataOperacaoQueVamosAlterar,oa.DATAREALIZACAO = dataRealizacao, oa.TIPOOPERACAO = tipoOp, oa.CUSTO = custo
        where oa."DATA" = DATAOPERACAO and oa.CODIGOPARCELA = CODIGOPARCELAOPERACAO;
        DBMS_OUTPUT.PUT_LINE('A operacao agricola foi atualizada');


    else
    raise_application_error(-20002,'Não existe nenhuma operação agricola a ser realizada na data mencionada');
    end if;
end;
/



create or replace procedure pr_updateFactorOperation(dataOperacao OPERACAO_AGRICOLA."DATA"%type, codigoParcelaOperacao OPERACAO_AGRICOLA.CODIGOPARCELA%type, codFator FATOR_OPERACAO.CODIGOFATOR%type,
formaAplicacao FATOR_OPERACAO.FORMAAPLICACAO%type, quantidade FATOR_OPERACAO.QUANTIDADEPRODUTO%type, unidade FATOR_OPERACAO.UNIDADEPRODUTO%type)
is

contadorCheckOpExists int;
contadorCheckIfProdSch int;

begin
   contadorCheckOpExists := FUNC_CHECKIFOPERATIONEXISTS(DATAOPERACAO, CODIGOPARCELAOPERACAO);

   if contadorCheckOpExists > 0 then

    contadorCheckIfProdSch := FUNC_CHECKIFPRODUCTISALREADYSCHEDULED(CODFATOR,CODIGOPARCELAOPERACAO,DATAOPERACAO);

    if contadorCheckIfProdSch > 0 then
        update FATOR_OPERACAO fo
        set fo.QUANTIDADEPRODUTO = quantidade, fo.FORMAAPLICACAO = formaAplicacao, fo.UNIDADEPRODUTO = unidade
        where codFator = fo.CODIGOFATOR and CODIGOPARCELAOPERACAO = fo.CODIGOPARCELA and DATAOPERACAO = fo."DATA";
        DBMS_OUTPUT.PUT_LINE('O fator operacao foi atualizado');
    else
        insert into FATOR_OPERACAO values (codFator,CODIGOPARCELAOPERACAO,DATAOPERACAO,unidade,quantidade,formaAplicacao);
        DBMS_OUTPUT.PUT_LINE('O fator operacao foi criado visto que o codigo de fator introduzido não estava associado a operacao agricola');
    end if;

    else
    raise_application_error(-20002,'Não existe nenhuma operação agricola a ser realizada na data mencionada');
    end if;
end;
/









