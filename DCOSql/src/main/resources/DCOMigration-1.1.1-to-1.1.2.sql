/**
* Migration script from 1.1.1 to 1.1.2
* CNIL
*/

ALTER TABLE user ADD COLUMN DELETE_DATA tinyint(1) NOT NULL DEFAULT 0 AFTER `XBAS`;

-- First check if mysql scheduler is active.
DELIMITER $$

CREATE 
	EVENT `delete_client_data` 
	ON SCHEDULE EVERY 2 MONTH STARTS '2016-03-01 23:00:00' 
	DO BEGIN
		-- Remove all information from clients who want to leave the website.
		DELETE FROM user_entity where user in (select id from user where DELETE_DATA = 1);
		DELETE FROM user_role where user in (select id from user where DELETE_DATA = 1);
		DELETE FROM user_legal_entity where user in (select id from user where DELETE_DATA = 1);
		DELETE FROM statistics where user in (select id from user where DELETE_DATA = 1);
		DELETE FROM third_party where id_user_tp in (select id from user where DELETE_DATA = 1);
		DELETE FROM user where DELETE_DATA = 1;
		DELETE FROM preferences where id not in (select preferences from user where preferences is not null);
	END $$

DELIMITER ;
