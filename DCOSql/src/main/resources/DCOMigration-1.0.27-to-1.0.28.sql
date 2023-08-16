--ADD A NEW CONTACT TO THE ENTITIY

ALTER TABLE `dco`.`entities` 
ADD COLUMN `CONTACT2` INT(11) NULL DEFAULT NULL AFTER `CONTACT`,
ADD INDEX `FK_ENTITY_CONTACT2_idx` (`CONTACT2` ASC)
  
ALTER TABLE `dco`.`entities` 
ADD CONSTRAINT `FK_ENTITY_CONTACT2`
  FOREIGN KEY (`CONTACT2`)
  REFERENCES `dco`.`third_party` (`ID`)
  ON DELETE SET NULL
  ON UPDATE NO ACTION