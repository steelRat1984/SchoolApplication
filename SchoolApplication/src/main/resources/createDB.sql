-- Database: schoolapp
-- DROP DATABASE IF EXISTS schoolapp;
CREATE DATABASE schoolapp
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Ukrainian_Ukraine.1251'
    LC_CTYPE = 'Ukrainian_Ukraine.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

GRANT TEMPORARY, CONNECT ON DATABASE schoolapp TO PUBLIC;

GRANT ALL ON DATABASE schoolapp TO postgres;

GRANT ALL ON DATABASE schoolapp TO schooladmin;



-- Role: schooladmin
-- DROP ROLE IF EXISTS schooladmin;

CREATE ROLE schooladmin WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  NOREPLICATION
  ENCRYPTED PASSWORD 'SCRAM-SHA-256$4096:Qvp3ZfBrZGWqCg7ead4Z7g==$OKycLRWCSfHyR94HBugABQDqrIKvGVnjywj4zl90YO0=:LvtIEw2C7MsaSJ82fTdjJm/BNUsZfEuoBSO6ef4eeME=';