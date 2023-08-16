-- create new fields into account_third_party table
ALTER TABLE `dco`.`account_third_party` 
ADD COLUMN `STATUS_AMOUNT_LIMIT` INT(11) NULL DEFAULT NULL AFTER `ID_SIGNATURE_AUTHORIZATION`,
ADD COLUMN `POWER_TYPE` INT(11) NULL DEFAULT NULL AFTER `STATUS_AMOUNT_LIMIT`,
ADD COLUMN `PUBLIC_DEED_REFERENCE` VARCHAR(50) NULL DEFAULT NULL AFTER `POWER_TYPE`,
ADD COLUMN `BOARD_RESOLUTION_DATE` DATETIME NULL DEFAULT NULL AFTER `PUBLIC_DEED_REFERENCE`;

-- create a new table authorization_third_party to fill rights on association account-thirdParty
CREATE TABLE IF NOT EXISTS `dco`.`authorization_third_party` (
  `ATP_ID` INT(11) NOT NULL,
  `THIRD_PARTY_ID` INT(11) NOT NULL,
  PRIMARY KEY (`ATP_ID`, `THIRD_PARTY_ID`),
  CONSTRAINT `FK_ACCOUNT_THIRD_PARTY`
    FOREIGN KEY (`ATP_ID`)
    REFERENCES `dco`.`account_third_party` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

-- empty table to fix previous version compatiblity of thirdParties association
DELETE FROM  `dco`.`account_third_party` WHERE `ID` > 0 ;