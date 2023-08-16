/**
* Migration script from 1.1.1 to 1.1.2
* CNIL
*/

UPDATE `dco`.`date_format` SET `LABEL`='dd/MM/yyyy', `LABELDISPLAY`='dd/mm/yyyy' WHERE `LABEL`='dd-MM-yyyy';
UPDATE `dco`.`date_format` SET `LABEL`='yyyy/MM/dd', `LABELDISPLAY`='yyyy/mm/dd' WHERE `LABEL`='yyyy-MM-dd';
UPDATE `dco`.`date_format` SET `LABEL`='MM/dd/yyyy', `LABELDISPLAY`='mm/dd/yyyy' WHERE `LABEL`='MM-dd-yyyy';