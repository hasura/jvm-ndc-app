ALTER
SESSION SET CONTAINER=XEPDB1;

CREATE
TABLESPACE MY_TABLESPACE
    DATAFILE 'tablespace.dbf'
    SIZE 1m;

alter
user test quota unlimited on MY_TABLESPACE;

create table TEST."foo"
(
    "id"      number(10) not null
);

grant all
ON TEST."foo" to TEST;
