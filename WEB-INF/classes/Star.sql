-- CREATE USER star IDENTIFIED BY society ;
-- GRANT ALL PRIVILEGES TO star;
-- CONNECT star/society;
-------------------------------------------------------------
drop sequence Element;
drop function getIDElement;
drop sequence compo;
drop function getIDComposant;
drop Table Composant;
drop Table Elements;
drop Table GestionStockage;

CREATE SEQUENCE Element
start with 1
INCREMENT by 1;


create OR REPLACE function getIDElement return number is sequence number ;
begin
sequence:= Element.nextval;
return sequence ;
end;
/


CREATE SEQUENCE Compo
start with 1
INCREMENT by 1;


create OR REPLACE function getIDComposant return number is sequence number ;
begin
sequence:= Compo.nextval;
return sequence ;
end;
/

CREATE Table Elements(
    IDElement varchar(7) primary key,
    Name varchar(20),
    MinQuantity integer,
    PricePerQuantity float,
    Unite varchar(20),
    Primary varchar(3)
);

create Table GestionStockage(
    Daty date,
    IDElement varchar(7),
    entre number,
    sortie number,
    prix number
);

CREATE Table Composant(
    getIDComposant varchar(7),
    IDElement varchar(7),
    Quantity float
);

create Table Inventaire(
    daty date,
    IDElement varchar(7),
    rest float
);


create or replace view EtatStock as 
select
IDElement,
sum(Entre) as Entrer,
sum(Sortie) as Sortie,
(sum(Entre)) - (sum(Sortie)) as reste 
from GestionStockage group by IDElement;

select
IDElement,
sum(Entre) as Entrer,
sum(Sortie) as Sortie,
(sum(Entre)) - (sum(Sortie)) as reste 
from GestionStockage group by IDElement;

insert into Elements values('C1','sucre',1,20,'g','yes');
insert into Elements values('C2','Arome',1,5,'g','yes');
insert into Elements values('C3','Eau',1,20,'l','yes');
insert into Elements values('C4','gaz',1,50,'g','yes');
insert into Elements values('C5','papier',1,50,'nb','yes');
insert into Elements values('C6','impression',1,100,'nb','yes');
insert into Elements values('C7','bouteillePM',1,50,'nb','yes');
insert into Elements values('C8','bouteilleGM',1,60,'nb','yes');
insert into Elements values('C9','levure',1,10,'g','yes');
insert into Elements values('C10','mais',1,5,'g','yes');
insert into Elements values('C11','alcool',1,100,'l','yes');
insert into Elements values('C18','conservateur',1,50,'l','yes');
-----------------------------------------------------------
insert into Elements values('C12','etiquette',1,1,'nb','no');
insert into Elements values('C13','orge',1,1,'g','no');
insert into Elements values('C14','eauGazeuse',1,1,'l','no');
insert into Elements values('C15','Limonade',1,1,'nb','no');
insert into Elements values('C16','BierePM',1,1,'nb','no');
insert into Elements values('C17','BiereGM',1,1,'nb','no');
insert into Elements values('C19','BigBiere',1,1,'nb','no');

------------------etiquette----------------
insert into Composant values('C12','C5',1);
insert into Composant values('C12','C6',1);

----------------orge-------------------
insert into Composant values('C13','C9',10);
insert into Composant values('C13','C10',5);

---------------eauGazeuse------------------
insert into Composant values('C14','C3',1);
insert into Composant values('C14','C4',1);

----------------Limonade-------------------
insert into Composant values('C15','C1',30);
insert into Composant values('C15','C2',5);
insert into Composant values('C15','C14',5);
insert into Composant values('C15','C12',1);
insert into Composant values('C15','C7',1);

---------------BierePM---------------------
insert into Composant values('C16','C7',1);
insert into Composant values('C16','C11',1);
insert into Composant values('C16','C14',5);
insert into Composant values('C16','C13',1);
insert into Composant values('C16','C12',1);
insert into Composant values('C16','C18',5);
---------------BierreGM------------------
insert into Composant values('C17','C16',3);
insert into Composant values('C17','C15',2);
-- insert into Composant values('C17','C8',1);
-- insert into Composant values('C17','C12',1);
--------------Bigbiere------------------
insert into Composant values('C19','C17',3);
insert into Composant values('C19','C15',1);
insert into Composant values('C19','C14',1);
insert into Composant values('C19','C6',1.23);
commit;
