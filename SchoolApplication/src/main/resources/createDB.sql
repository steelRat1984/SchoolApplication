-- Database: schoolapp
 DROP DATABASE IF EXISTS school_app;
CREATE DATABASE schoolapp
    WITH
    OWNER = school_admin
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
GRANT TEMPORARY, CONNECT ON DATABASE school_app TO PUBLIC;
GRANT ALL ON DATABASE school_app TO postgres;
GRANT ALL ON DATABASE school_app TO school_admin;
-- Role: school_admin
-- DROP ROLE IF EXISTS school_admin;
CREATE ROLE school_admin WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  NOREPLICATION
  ENCRYPTED PASSWORD 'SCRAM-SHA-256$4096:Qvp3ZfBrZGWqCg7ead4Z7g==$OKycLRWCSfHyR94HBugABQDqrIKvGVnjywj4zl90YO0=:LvtIEw2C7MsaSJ82fTdjJm/BNUsZfEuoBSO6ef4eeME=';