create or replace procedure pr_insertProduction(codFator FATOR_PRODUCAO.CODIGOFATOR%type, precoTotal FATOR_PRODUCAO.PRECO%type,
nome FATOR_PRODUCAO.NOMECOMERCIAL%type, tipoFator FATOR_PRODUCAO.FORMULACAO%type, formula FATOR_PRODUCAO.FORMULACAO%type, codFornecedor FATOR_PRODUCAO.CODIGOFORNECEDOR%type)
is
begin
    INSERT INTO FATOR_PRODUCAO (CODIGOFATOR, PRECO, NOMECOMERCIAL, TIPODEFATOR, FORMULACAO, CODIGOFORNECEDOR) VALUES (codFator, precoTotal, nome,tipoFator, formula, codFornecedor);
end;

create or replace procedure pr_updateProduction(codFator FATOR_PRODUCAO.CODIGOFATOR%type, precoTotal FATOR_PRODUCAO.PRECO%type,
nome FATOR_PRODUCAO.NOMECOMERCIAL%type, tipoFator FATOR_PRODUCAO.FORMULACAO%type, formula FATOR_PRODUCAO.FORMULACAO%type, codFornecedor FATOR_PRODUCAO.CODIGOFORNECEDOR%type)
is
begin
    update FATOR_PRODUCAO
    set PRECO = precoTotal, NOMECOMERCIAL = nome, TIPODEFATOR = tipoFator, FORMULACAO = formula, CODIGOFORNECEDOR = codFornecedor
    where codFator = CODIGOFATOR;
end;

create or replace procedure pr_deleteProduction(codFator FATOR_PRODUCAO.CODIGOFATOR%type)
is
begin
    delete
    from FATOR_PRODUCAO
    where codFator = CODIGOFATOR;
end;


create or replace procedure pr_insertElementoSubstancia(codSubstancia ELEMENTO_SUBSTANCIA.CODIGOSUBSTANCIA%type, nome ELEMENTO_SUBSTANCIA.NOME%type, tipo ELEMENTO_SUBSTANCIA.TIPO%type)
is
begin
    INSERT INTO ELEMENTO_SUBSTANCIA (CODIGOSUBSTANCIA, NOME, TIPO) VALUES (codSubstancia, nome, tipo);
end;

create or replace procedure pr_updateElementoSubstancia(codSubstancia ELEMENTO_SUBSTANCIA.CODIGOSUBSTANCIA%type, nome ELEMENTO_SUBSTANCIA.NOME%type,
tipo ELEMENTO_SUBSTANCIA.TIPO%type)
is
begin
    update ELEMENTO_SUBSTANCIA
    set NOME = nome, TIPO = tipo
    where codSubstancia = CODSUBSTANCIA;
end;

create or replace procedure pr_deleteElementoSubstancia(codSubstancia ELEMENTO_SUBSTANCIA.CODIGOSUBSTANCIA%type)
is
begin
    delete
    from ELEMENTO_SUBSTANCIA
    where codSubstancia = CODIGOSUBSTANCIA;
end;


create or replace procedure pr_insertFichaTecnica(codFator FICHA_TECNICA.CODIGOFATOR%type, codSubstancia FICHA_TECNICA.CODIGOSUBSTANCIA%type, quantidade FICHA_TECNICA.QUANTIDADE%type, unidade FICHA_TECNICA.UNIDADE%type)
is
begin
    INSERT INTO FICHA_TECNICA (CODIGOFATOR, CODIGOSUBSTANCIA, QUANTIDADE, UNIDADE) VALUES (codFator, codSubstancia, quantidade, unidade);
end;

create or replace procedure pr_updateFICHATECNICA(codFator FICHA_TECNICA.CODIGOFATOR%type, codSubstancia FICHA_TECNICA.CODIGOSUBSTANCIA%type, quantidade FICHA_TECNICA.QUANTIDADE%type, unidade FICHA_TECNICA.UNIDADE%type)
is
begin
    update FICHA_TECNICA
    set QUANTIDADE = quantidade, UNIDADE = unidade
    where codFator = CODFATOR and codSubstancia = CODSUBSTANCIA;
end;

create or replace procedure pr_deleteFichaTecnica(codFator FICHA_TECNICA.CODIGOFATOR%type, codSubstancia FICHA_TECNICA.CODIGOSUBSTANCIA%type)
is
begin
    delete
    from FICHA_TECNICA
    where codFator = CODIGOFATOR and codSubstancia = CODIGOSUBSTANCIA;
end;


create or replace procedure pr_Datasheet(codigoDoFator FICHA_TECNICA.CODIGOFATOR%type)
is
    cursor dadosSub is select es.NOME, es.TIPO, ft.QUANTIDADE, ft.UNIDADE
                    from FICHA_TECNICA ft
                    inner join ELEMENTO_SUBSTANCIA es on es.CODIGOSUBSTANCIA = ft.CODIGOSUBSTANCIA
                    where ft.CODIGOFATOR = codigoDoFator;
    subNome ELEMENTO_SUBSTANCIA.NOME%type;
    subTipo ELEMENTO_SUBSTANCIA.TIPO%type;
    quant FICHA_TECNICA.QUANTIDADE%type;
    uni FICHA_TECNICA.UNIDADE%type;

    fornecedorN FORNECEDOR.NOME%type;
    nomeTipo FATOR_PRODUCAO.NOMECOMERCIAL%type;
    tipoF FATOR_PRODUCAO.TIPODEFATOR%type;
begin

    select f.NOME, fp.NOMECOMERCIAL, fp.TIPODEFATOR
    into fornecedorN, nomeTipo, tipoF
    from FATOR_PRODUCAO fp
    inner join FORNECEDOR f on f.CODIGOFORNECEDOR = fp.CODIGOFORNECEDOR
    where fp.CODIGOFATOR = codigoDoFator;

    dbms_output.PUT_LINE('Ficha Técnica');
    dbms_output.PUT_LINE('Fator: ' ||nomeTipo || ' | Fornecedor: ' || fornecedorN || ' | Tipo do Fator: ' || tipoF);

    open dadosSub;
        loop
            fetch dadosSub into subNome, subTipo, quant, uni;
            exit when dadosSub%notfound;
            dbms_output.PUT_LINE('Nome da substância: ' || subNome || ' Tipo da substância: ' || subTipo || ' Quantidade: ' || quant || ' Unidade: ' || uni);
        end loop;
    close dadosSub;
end;

begin
    PR_INSERTPRODUCTION('FAT-5', 2.65, 'Adubo', 'Corretivo Mineral', 'liquido', 'FOR-1');
end;

begin
    PR_UPDATEPRODUCTION('FAT-5', 2.65, 'Adubo', 'Corretivo Mineral', 'liquido', 'FOR-1');
end;

begin
    PR_DELETEPRODUCTION('FAT-5');
end;

begin
    PR_INSERTELEMENTOSUBSTANCIA('SUB-5', 'substancia5', 'SUBSTANCIA ORGANICA');
end;

begin
    PR_UPDATEELEMENTOSUBSTANCIA('SUB-5', 'substancia05', 'SUBSTANCIA ORGANICA');
end;

begin
    PR_DELETEELEMENTOSUBSTANCIA('SUB-5');
end;

begin
    PR_INSERTFICHATECNICA('FAT-5', 'SUB-5', 0.8, 'g');
end;

begin
    PR_UPDATEFICHATECNICA('FAT-5', 'SUB-5', 0.92, 'g');
end;

begin
    PR_DELETEFICHATECNICA('FAT-5', 'SUB-5');
end;

begin
    PR_DATASHEET('FAT-5');
end;