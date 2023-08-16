/**
* change id_position fk to position_name property
*/
ALTER TABLE `dco`.`third_party` 
DROP FOREIGN KEY `FK_POSITION`;

ALTER TABLE `dco`.`third_party` 
DROP COLUMN `ID_POSITION`,
ADD COLUMN `POSITION_NAME` VARCHAR(50) NULL DEFAULT NULL AFTER `LEGAL_ENTITY_NAME`,
DROP INDEX `FK_POSITION_idx` ;