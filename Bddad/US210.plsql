create or replace procedure pr_operationRegistracion(codFator FATOR_OPERACAO.CODIGOFATOR%type, codParcela FATOR_OPERACAO.CODIGOPARCELA%type,
day FATOR_OPERACAO.DATA%type, uniProduto FATOR_OPERACAO.UNIDADEPRODUTO%type, quantProduto FATOR_OPERACAO.QUANTIDADEPRODUTO%type,
forAplicacao FATOR_OPERACAO.FORMAAPLICACAO%type, tipoOpera OPERACAO_AGRICOLA.TIPOOPERACAO%type, valorCusto OPERACAO_AGRICOLA.CUSTO%type)
is
begin
    insert into OPERACAO_AGRICOLA (DATA,CODIGOPARCELA,TIPOOPERACAO, CUSTO) values (day,codParcela,tipoOpera, valorCusto);

    insert into FATOR_OPERACAO (CODIGOFATOR, CODIGOPARCELA, DATA, UNIDADEPRODUTO, QUANTIDADEPRODUTO, FORMAAPLICACAO)
    values (codFator,codParcela,day,uniProduto,quantProduto, forAplicacao);

end;
/

begin
    PR_OPERATIONREGISTRACION('FAT-1', 'PAR-2', to_date('28/12/2023 10:00', 'DD/MM/YYYY HH24:MI'), 'Kg', 12.65, 'NO SOLO', 'IRRIGACAO', 3000);
end;

create or replace procedure pr_factorOperationRegistration(codFator FATOR_OPERACAO.CODIGOFATOR%type, codParcela FATOR_OPERACAO.CODIGOPARCELA%type,
day FATOR_OPERACAO.DATA%type, uniProduto FATOR_OPERACAO.UNIDADEPRODUTO%type,
quantProduto FATOR_OPERACAO.QUANTIDADEPRODUTO%type, forAplicacao FATOR_OPERACAO.FORMAAPLICACAO%type)
is
begin
    insert into FATOR_OPERACAO (CODIGOFATOR, CODIGOPARCELA, DATA, UNIDADEPRODUTO, QUANTIDADEPRODUTO, FORMAAPLICACAO) values (codFator, codParcela, day, uniProduto, quantProduto, forAplicacao);
end;
/

begin
    PR_FACTOROPERATIONREGISTRATION('FAT-2', 'PAR-2', to_date('28/12/2023 10:00', 'DD/MM/YYYY HH24:MI'), 'Kg', 12.65, 'NO SOLO');
end;

create or replace procedure pr_restrictionsNoDelete(codFator FATOR_PRODUCAO.CODIGOFATOR%type, codParcela FATOR_OPERACAO.CODIGOPARCELA%type, day FATOR_OPERACAO.DATA%type)
is
    finalDayOfRestriction RESTRICAO_FATOR.DATAFIM%type;
    inicioDayOfRestriction RESTRICAO_FATOR.DATAINICIO%type;
begin
    select dataFim, dataInicio into finalDayOfRestriction, inicioDayOfRestriction
    from RESTRICAO_FATOR
    where CODIGOPARCELA = codParcela and CODIGOFATOR = codFator;

    if(finalDayOfRestriction > day and day > inicioDayOfRestriction) then
        raise_application_error(-20002,'Encontrada uma restrição para o fator de producao de codigo ' || codFator || ' na parcela de código ' || codParcela || '. O fator nao sera aplicado.');
    else
        raise no_data_found;
    end if;

    exception
        when no_data_found then
             dbms_output.PUT_LINE('Nenhuma restrição encontrada para o fator de producao de codigo ' || codFator || ' na parcela de código ' || codParcela);

end;
/


create or replace procedure pr_restrictions(codFator FATOR_PRODUCAO.CODIGOFATOR%type, codParcela FATOR_OPERACAO.CODIGOPARCELA%type, day FATOR_OPERACAO.DATA%type)
is
    finalDayOfRestriction RESTRICAO_FATOR.DATAFIM%type;
    inicioDayOfRestriction RESTRICAO_FATOR.DATAINICIO%type;
begin
    select dataFim, dataInicio into finalDayOfRestriction, inicioDayOfRestriction
    from RESTRICAO_FATOR
    where CODIGOPARCELA = codParcela and CODIGOFATOR = codFator;

    if(finalDayOfRestriction > day and day > inicioDayOfRestriction) then
        dbms_output.PUT_LINE('Encontrada uma restrição para o fator de producao de codigo ' || codFator || ' na parcela de código ' || codParcela || '. O fator nao sera aplicado.');
        delete from FATOR_OPERACAO where CODIGOFATOR = codFator and CODIGOPARCELA = codParcela and to_char(DATA) = to_char(day);
    else
        raise no_data_found;
    end if;

    exception
        when no_data_found then
             dbms_output.PUT_LINE('Nenhuma restrição encontrada para o fator de producao de codigo ' || codFator || ' na parcela de código ' || codParcela);
end;
/

begin
    dbms_scheduler.create_program(
                                program_name => 'ProRestricao8',
                                program_type => 'STORED_PROCEDURE',
                                program_action => 'PR_RESTRICTIONS',
                                number_of_arguments => 3,
                                enabled => false);
    dbms_scheduler.define_program_argument(
                                        program_name => 'ProRestricao8',
                                        argument_position => 1,
                                        argument_name => 'codFator',
                                        argument_type => 'VARCHAR',
                                        default_value => 'FAT-1');
    dbms_scheduler.define_program_argument(
                                        program_name => 'ProRestricao8',
                                        argument_position => 2,
                                        argument_name => 'codParcela',
                                        argument_type => 'VARCHAR',
                                        default_value => 'PAR-1');
    dbms_scheduler.define_program_argument(
                                        program_name => 'ProRestricao8',
                                        argument_position => 3,
                                        argument_name => 'day',
                                        argument_type => 'DATE',
                                        default_value => to_date('12/12/2012', 'DD/MM/YYYY'));
    dbms_scheduler.enable (name => 'ProRestricao8' );

end;
/

create or replace procedure pr_listRestrictionsOnADateSector(day date, codParc PARCELA.CODIGOPARCELA%type)
is
    restricoes RESTRICAO_FATOR%rowtype;
    contador int;

    cursor list is select *
                    from RESTRICAO_FATOR
                    where CODIGOPARCELA = codParc and DATAINICIO < day and day < DATAFIM
                    order by CODIGOPARCELA;

begin
    contador := 0;
    open list;
    loop
        fetch list into restricoes;
        exit when list%notFound;
        contador := contador + 1;
        dbms_output.PUT_LINE(' Codigo Fator: ' || restricoes.CODIGOFATOR || ' Codigo Parcela: ' || restricoes.CODIGOPARCELA || ' Data de Início: ' || restricoes.DATAINICIO || ' Data Fim: ' || restricoes.DATAFIM);
    end loop;
    close list;

    if (contador = 0) then
        dbms_output.PUT_LINE('No restrictions found.');
    end if;
end;
/

create or replace procedure pr_listOperationsOnSector(inicialDate date, finalDate date, codParc PARCELA.CODIGOPARCELA%type)
is
    operacao OPERACAO_AGRICOLA%rowtype;
    contador int;

    cursor operationsList is select *
                            from OPERACAO_AGRICOLA
                            where CODIGOPARCELA = codParc and inicialDate < DATA and finalDate > DATA
                            order by DATA;
begin
    contador := 0;
    open operationsList;
    loop
        fetch operationsList into operacao;
        exit when operationsList%notfound;
        contador := contador + 1;
        dbms_output.PUT_LINE('Codigo da Parcela: ' || operacao.CODIGOPARCELA || ' Data: ' || operacao.DATA || ' Tipo de Operacao: ' || operacao.TIPOOPERACAO);
    end loop;
    close operationsList;
    if (contador = 0) then
        dbms_output.PUT_LINE('No operations found.');
    end if;
end;
/

begin
    pr_listOperationsOnSector(to_date('01/12/2022', 'DD/MM/YYYY'), to_date('31/12/2023', 'DD/MM/YYYY'), 'PAR-2');
end;
/

begin
    PR_LISTRESTRICTIONSONADATESECTOR(to_date('12/11/2022', 'DD/MM/YYYY'), 'PAR-1');
end;
/
