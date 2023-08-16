/**
* Migration script from 1.0.15 to 1.0.16
*/

/** 
 * Remove LEGAL_ENTITY_ID (with FK) and replace with LEGAL_ENTITY_NAME  
 */
ALTER TABLE `dco`.`third_party` 
DROP FOREIGN KEY `FK_LEGAL_ENTITY` ;
ALTER TABLE `dco`.`third_party` 
DROP COLUMN `LEGAL_ENTITY_ID`,
ADD COLUMN `LEGAL_ENTITY_NAME` VARCHAR(50) NULL DEFAULT NULL AFTER `TYPE_CORRESPONDANT`,
DROP INDEX `FK_LEGAL_ENTITY_idx` ;

/**
 * Change constraint option to FK_ENTITY_CONTACT on delete action: NULL
 */
ALTER TABLE `dco`.`entities` 
DROP FOREIGN KEY `FK_ENTITY_CONTACT` ;
ALTER TABLE `dco`.`entities` 
ADD CONSTRAINT `FK_ENTITY_CONTACT`
  FOREIGN KEY (`CONTACT`)
  REFERENCES `dco`.`third_party` (`ID`)
  ON DELETE SET NULL
  ON UPDATE NO ACTION ;
  
  /**
 * Change constraint option to ID_TPA_THIRD_PARTY on delete action: CASCADE
 */
  ALTER TABLE `dco`.`account_third_party` 
DROP FOREIGN KEY `ID_TPA_THIRD_PARTY` ;
ALTER TABLE `dco`.`account_third_party` ADD CONSTRAINT `ID_TPA_THIRD_PARTY`
  FOREIGN KEY (`ID_THIRD_PARTY`)
  REFERENCES `dco`.`third_party` (`ID`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION ;
  
    /**
 * Change constraint option to LEGAL_REPRESENTATIVE_THIRD_PARTY on delete action: CASCADE
 */
  ALTER TABLE `dco`.`entity_legal_representative` 
DROP FOREIGN KEY `LEGAL_REPRESENTATIVE_THIRD_PARTY` ;
ALTER TABLE `dco`.`entity_legal_representative` 
ADD CONSTRAINT `LEGAL_REPRESENTATIVE_THIRD_PARTY`
  FOREIGN KEY (`THIRD_PARTY`)
  REFERENCES `dco`.`third_party` (`ID`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION ;
  