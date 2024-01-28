create or replace procedure pr_InsertParcela
(codigoParcela in varchar, designacao in varchar, area in decimal)
as
begin
insert into PARCELA values (codigoParcela, designacao, area);
end;

begin
 pr_InsertParcela('PAR-10','TOMATES2', 25);
end;


create or replace procedure pr_insertNewColumn
(nameOfTheColumn in varchar, tipo in varchar)
as
begin
execute IMMEDIATE 'alter table cultura add ' || nameOfTheColumn || ' ' || tipo || ' NULL';
end;

begin
    pr_insertNewColumn('ola123', 'int');
end;



create or replace view vw_orderByAlphabeticOrder as
select * from parcela
order by designacao;

select * from vw_orderByAlphabeticOrder;


create or replace view vw_orderBySizeAsc as
select * from parcela
order by area;

select * from vw_orderBySizeAsc;


create or replace view vw_orderBySizeDsc as
select * from parcela
order by area desc;

select * from vw_orderBySizeDsc;


create or replace view vw_orderByCulture as
select p.codigoparcela, p.designacao, p.area from parcela p
join parcela_cultura pc on p.codigoparcela = pc.codigoparcela
join cultura c on pc.codigocultura = c.codigocultura
order by c.tipoCultura;

select * from vw_orderByCulture;



