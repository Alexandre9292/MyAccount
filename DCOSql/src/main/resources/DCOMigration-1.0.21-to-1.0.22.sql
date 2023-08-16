-- add a new date format to be applied over the application
insert into date_format(id,label) values (3, "MM-dd-yyyy");

-- change some type documents and delete others
UPDATE `dco`.`document_type` SET `LABEL`='Account Agreements' WHERE `ID`='3';
UPDATE `dco`.`document_type` SET `LABEL`='Additional Documents' WHERE `ID`='4';
DELETE FROM `dco`.`document_type` WHERE `ID`='1';
DELETE FROM `dco`.`document_type` WHERE `ID`='2';