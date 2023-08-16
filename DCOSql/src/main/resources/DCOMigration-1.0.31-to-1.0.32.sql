-- Add new column to date format table for the label
ALTER TABLE `dco`.`date_format` 
ADD COLUMN `LABELDISPLAY` VARCHAR(45) NULL DEFAULT NULL AFTER `LABEL`,
ADD COLUMN `LABELLONG` VARCHAR(45) NULL DEFAULT NULL AFTER `LABELDISPLAY`;

UPDATE `dco`.`date_format` SET `LABELDISPLAY` = LOWER(`LABEL`);

UPDATE `dco`.`date_format` SET `LABELLONG`='dd MMMMM yyyy' WHERE `LABEL`='dd-MM-yyyy';
UPDATE `dco`.`date_format` SET `LABELLONG`='yyyy, MMMMM dd' WHERE `LABEL`='yyyy-MM-dd';
UPDATE `dco`.`date_format` SET `LABELLONG`='MMMMM dd, yyyy' WHERE `LABEL`='MM-dd-yyyy';
