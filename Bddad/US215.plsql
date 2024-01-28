create or replace procedure pr_replaceClientHub(codHub hub.idHub%type, codCliente cliente.idCliente%type) as
begin
    update cliente set idHub = codHub where idCliente = codCliente;
end;
/
