create SEQUENCE audit_seq
  START WITH 1;

create or replace trigger ALTERATIONS
after insert or update or delete on OPERACAO_AGRICOLA
for each row
declare
    v_action varchar(20);
    sectorCode varchar(20);
    dataOfOperation date;
begin
    if inserting then
        v_action := 'INSERT';
        sectorCode := :new.codigoParcela;
        dataOfOperation := :new.data;
    elsif updating then
        v_action := 'UPDATE';
        sectorCode := :new.codigoParcela;
        dataOfOperation := :new.data;
    else
        v_action := 'DELETE';
        sectorCode := :old.codigoParcela;
        dataOfOperation := :old.data;
    end if;

    insert into PISTAS_DE_AUDITORIA(idendificadorRegistoAuditoria, utilizador, dateTime, operacao, codigoParcela, data)
    values (audit_seq.NEXTVAL, user, sysdate, v_action, sectorCode, dataOfOperation);
end;



create or replace function pistasDeAuditoriaOrganized(v_utilizador PISTAS_DE_AUDITORIA.utilizador%type,
v_dateTime PISTAS_DE_AUDITORIA.dateTime%type, v_operacao PISTAS_DE_AUDITORIA.operacao%type, v_codigoParcela PISTAS_DE_AUDITORIA.codigoParcela%type)
return sys_refcursor
is
    data_cursor sys_refcursor;
begin
    if v_utilizador is not null then
        open data_cursor for
            select *
            from PISTAS_DE_AUDITORIA pa
            where pa.utilizador = v_utilizador;
    elsif v_dateTime is not null then
        open data_cursor for
            select *
            from PISTAS_DE_AUDITORIA pa
            where pa.dateTime = v_dateTime;
    elsif v_operacao is not null then
        open data_cursor for
            select *
            from PISTAS_DE_AUDITORIA pa
            where pa.operacao = v_operacao;
    elsif v_codigoParcela is not null then
        open data_cursor for
            select *
            from PISTAS_DE_AUDITORIA pa
            where pa.codigoParcela = v_codigoParcela;
    end if;

    return data_cursor;

exception
    when NO_DATA_FOUND then
        RAISE_APPLICATION_ERROR(-20001, 'No data found');
end;


DECLARE
  l_cursor SYS_REFCURSOR;
  cursor_rec PISTAS_DE_AUDITORIA%ROWTYPE;
BEGIN

  l_cursor := pistasDeAuditoriaOrganized(null, null, null, 'PAR-1');

  LOOP
    FETCH l_cursor INTO  cursor_rec;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(cursor_rec.idendificadorRegistoAuditoria || ' ' ||cursor_rec.utilizador || ' ' || cursor_rec.dateTime || ' ' || cursor_rec.operacao || ' ' || cursor_rec.codigoParcela|| ' ' ||cursor_rec.data);
  END LOOP;

  CLOSE l_cursor;
END;



CREATE OR REPLACE VIEW pistas_ordered_by_datetime_asc AS
SELECT *
FROM PISTAS_DE_AUDITORIA
ORDER BY dateTime ASC;

select * from pistas_ordered_by_datetime_ASC;


CREATE OR REPLACE VIEW pistas_ordered_by_datetime_desc AS
SELECT *
FROM PISTAS_DE_AUDITORIA
ORDER BY dateTime DESC;

select * from pistas_ordered_by_datetime_desc;
