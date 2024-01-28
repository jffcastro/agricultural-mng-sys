create or replace procedure pr_transferSensorDataToDataBase
is
    check_dont_exist exception;
    check_constraint_violated exception;
    pragma exception_init(check_constraint_violated, -2290);

    cursor dadosLidos is select INPUT_STRING from INPUT_SENSOR;

    linhaLida INPUT_SENSOR.INPUT_STRING%type;
    ident SENSOR.IDENTIFICADORSENSOR%type;
    valRef SENSOR.VALORREFERENCIA%type;
    tipoSen SENSOR.TIPOSENSOR%type;
    instLei SENSOR_DATA_READ.INSTANTELEITURA%type;
    valLido SENSOR_DATA_READ.VALORLIDO%type;
    codAnalise READ_ANALYSIS.CODIGOANALISE%type;

    dataHoraExecucao date;
    numRegLidos integer;
    numRegComErros integer;
    numErros integer;
    contadorParaCodigo integer;
    sub integer;

begin
    numRegLidos := 0;
    numRegComErros := 0;
    dataHoraExecucao := sysdate;

    select count(*) into contadorParaCodigo from READ_ANALYSIS;
    contadorParaCodigo := contadorParaCodigo + 1;
    codAnalise := 'AN-' || contadorParaCodigo;
    insert into READ_ANALYSIS (codigoAnalise,dataExecucao,nRegistosLidos,nRegistosInseridos,nRegistosComErros) values (codAnalise, dataHoraExecucao, 0, 0, 0);

    open dadosLidos;
    loop

        fetch dadosLidos into linhaLida;
        exit when dadosLidos%notfound;
        numRegLidos := numRegLidos + 1;
        declare
            contador1 integer;
            contador2 integer;
            contador3 integer;
        begin
            contador1 := 0;
            contador2 := 0;
            contador3 := 0;
            ident := substr(linhaLida, 1, 5);
            tipoSen := substr(linhaLida, 6, 2);
            valLido := to_number(substr(linhaLida, 8, 3));
            valRef := to_number(substr(linhaLida, 11, 3));
            instLei := to_date(substr(linhaLida, 14, 12), 'DDMMYYYYHH24MI');

            select count(*) into contador1 from SENSOR where identificadorSensor = ident;
            select count(*) into contador2 from SENSOR where VALORREFERENCIA = valRef;

            if (contador1 = 0 and contador2 = 0) then
                insert into SENSOR(IDENTIFICADORSENSOR, VALORREFERENCIA, TIPOSENSOR) values (ident, valRef, tipoSen);
            else if(contador1 = 0 and contador2 !=0) then
                raise check_dont_exist;
            --else if(contador1 != 0) then
                --raise dup_val_on_index;
           -- end if;
            end if;
            end if;

            select count(*) into contador3 from SENSOR_ERRORS where identificadorSensor = ident and codigoAnalise = codAnalise;

            if(contador3 = 0) then
                insert into SENSOR_ERRORS values (ident, codAnalise, 0);
            end if;

            insert into SENSOR_DATA_READ(INSTANTELEITURA ,IDENTIFICADORSENSOR, CODIGOANALISE, VALORLIDO) values (instLei, ident, codAnalise, valLido);
            delete from INPUT_SENSOR where INPUT_STRING = linhaLida;

            exception
                when dup_val_on_index then
                numRegComErros := numRegComErros + 1;
                delete from INPUT_SENSOR where INPUT_STRING = linhaLida;
                when check_constraint_violated then
                    delete from INPUT_SENSOR where INPUT_STRING = linhaLida;
                    numRegComErros := numRegComErros + 1;
                    update SENSOR_ERRORS set nRegistosComErros = nRegistosComErros + 1 where identificadorSensor = ident and codigoAnalise = codAnalise;
                when check_dont_exist then
                    delete from INPUT_SENSOR where INPUT_STRING = linhaLida;
                    numRegComErros := numRegComErros + 1;
        end;
    end loop;
    close dadosLidos;
    sub := numRegLidos-numRegComErros;
    update READ_ANALYSIS
    set nRegistosLidos = numRegLidos, nRegistosInseridos = sub, nRegistosComErros = numRegComErros
    where codigoAnalise = codAnalise;
    dbms_output.PUT_LINE('Número total de registos: ' || numRegLidos || ' | Número de registos transferidos: ' || sub || ' | Número de registos não transferidos: ' || numRegComErros);
end;
/

create or replace function pr_selectNesimotuplo (n int) return INPUT_SENSOR.input_string%type is
    cursor tuplos is select * from INPUT_SENSOR;
    auxiliar INPUT_SENSOR.input_string%type;
    contador int;
begin
    contador := 0;

    open tuplos;
    loop
        fetch tuplos into auxiliar;
        exit when tuplos%notfound;
        if (contador = n) then
        return auxiliar;
        end if;
        contador := contador + 1;
    end loop;
    close tuplos;


    dbms_output.PUT_LINE('Nao existe o ' || n || '-ésimo tuplo.');
    return null;

end;
/

begin
    pr_transferSensorDataToDataBase();
end;
/

begin
    dbms_output.PUT_LINE(pr_selectNesimotuplo(2));
end;
/

