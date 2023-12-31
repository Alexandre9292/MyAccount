-- Modification de la structure des tables : ajout des cascades --
ALTER TABLE statistics
   DROP FOREIGN KEY FK_STAT_USER;
ALTER TABLE statistics
   ADD CONSTRAINT FK_STAT_USER
   FOREIGN KEY (user)
   REFERENCES user (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE user_role
   DROP FOREIGN KEY FK_UR_USER;
ALTER TABLE user_role
   ADD CONSTRAINT FK_UR_USER
   FOREIGN KEY (user)
   REFERENCES user (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE user_entity
   DROP FOREIGN KEY FK_USERENTITY_ENTITY;
ALTER TABLE user_entity
   ADD CONSTRAINT FK_USERENTITY_ENTITY
   FOREIGN KEY (entity)
   REFERENCES entities (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE user_entity
   DROP FOREIGN KEY FK_USERENTITY_USER;
ALTER TABLE user_entity
   ADD CONSTRAINT FK_USERENTITY_USER
   FOREIGN KEY (user)
   REFERENCES user (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE user_legal_entity
   DROP FOREIGN KEY FK_ULE_USER;
ALTER TABLE user_legal_entity
   ADD CONSTRAINT FK_ULE_USER
   FOREIGN KEY (user)
   REFERENCES user (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE account
   DROP FOREIGN KEY FK_ADDRESS_FILIALE;
ALTER TABLE account
   ADD CONSTRAINT FK_ADDRESS_FILIALE
   FOREIGN KEY (id_address)
   REFERENCES address (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE account
   DROP FOREIGN KEY FK_FORM_ACCOUNT;
ALTER TABLE account
   ADD CONSTRAINT FK_FORM_ACCOUNT
   FOREIGN KEY (id_form)
   REFERENCES account_form (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE account
   DROP FOREIGN KEY FK_ADDRESS_FILIALE;
ALTER TABLE account
   ADD CONSTRAINT FK_ADDRESS_FILIALE
   FOREIGN KEY (id_address)
   REFERENCES address (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE account_form
   DROP FOREIGN KEY FK_FORM_ENTITY;
ALTER TABLE account_form
   ADD CONSTRAINT FK_FORM_ENTITY
   FOREIGN KEY (id_entity)
   REFERENCES entities (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE account_third_party
   DROP FOREIGN KEY ID_TPA_ACCOUNT;
ALTER TABLE account_third_party
   ADD CONSTRAINT ID_TPA_ACCOUNT
   FOREIGN KEY (id_account)
   REFERENCES account (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE entity_legal_representative
   DROP FOREIGN KEY LEGAL_REPRESENTATIVE_ENTITY;
ALTER TABLE entity_legal_representative
   ADD CONSTRAINT LEGAL_REPRESENTATIVE_ENTITY
   FOREIGN KEY (entity)
   REFERENCES entities (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE entities
   DROP FOREIGN KEY FK_ADDRESS_MAILING;
ALTER TABLE entities
   ADD CONSTRAINT FK_ADDRESS_MAILING
   FOREIGN KEY (id_address_mailing)
   REFERENCES address (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE entities
   DROP FOREIGN KEY FK_ENTITY_ADDRESS;
ALTER TABLE entities
   ADD CONSTRAINT FK_ENTITY_ADDRESS
   FOREIGN KEY (address)
   REFERENCES address (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

-- set null
ALTER TABLE entities
   DROP FOREIGN KEY FK_ENTITY_CONTACT;
ALTER TABLE entities
   ADD CONSTRAINT FK_ENTITY_CONTACT
   FOREIGN KEY (contact)
   REFERENCES third_party (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

-- set null
ALTER TABLE entities
   DROP FOREIGN KEY FK_ENTITY_CONTACT2;
ALTER TABLE entities
   ADD CONSTRAINT FK_ENTITY_CONTACT2
   FOREIGN KEY (contact2)
   REFERENCES third_party (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE account_statement_type
   DROP FOREIGN KEY FK_ACCOUNT_STATEMENT;
ALTER TABLE account_statement_type
   ADD CONSTRAINT FK_ACCOUNT_STATEMENT
   FOREIGN KEY (account)
   REFERENCES account (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE param_tech
   DROP FOREIGN KEY FK_PARAM_ENTITY;
ALTER TABLE param_tech
   ADD CONSTRAINT FK_PARAM_ENTITY
   FOREIGN KEY (entity)
   REFERENCES entities (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE third_party
   DROP FOREIGN KEY FK_HOME_ADRESS;
ALTER TABLE third_party
   ADD CONSTRAINT FK_HOME_ADRESS
   FOREIGN KEY (id_home_adress)
   REFERENCES address (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

ALTER TABLE third_party
   DROP FOREIGN KEY FK_USER_TP;
ALTER TABLE third_party
   ADD CONSTRAINT FK_USER_TP
   FOREIGN KEY (id_user_tp)
   REFERENCES user (id)
   ON UPDATE NO ACTION
   ON DELETE CASCADE;

-- Suppression des donn�es --
delete from user where not id in (select user from user_role);
delete from address where id not in(select id_address from legal_entity);
delete from third_party;
delete from account;
delete from entities;
update statistics set number = 0;

-- Restauration de la structure des tables --
ALTER TABLE statistics
   DROP FOREIGN KEY FK_STAT_USER;
ALTER TABLE statistics
   ADD CONSTRAINT FK_STAT_USER
   FOREIGN KEY (user)
   REFERENCES user (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE user_role
   DROP FOREIGN KEY FK_UR_USER;
ALTER TABLE user_role
   ADD CONSTRAINT FK_UR_USER
   FOREIGN KEY (user)
   REFERENCES user (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE user_entity
   DROP FOREIGN KEY FK_USERENTITY_ENTITY;
ALTER TABLE user_entity
   ADD CONSTRAINT FK_USERENTITY_ENTITY
   FOREIGN KEY (entity)
   REFERENCES entities (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE user_entity
   DROP FOREIGN KEY FK_USERENTITY_USER;
ALTER TABLE user_entity
   ADD CONSTRAINT FK_USERENTITY_USER
   FOREIGN KEY (user)
   REFERENCES user (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE user_legal_entity
   DROP FOREIGN KEY FK_ULE_USER;
ALTER TABLE user_legal_entity
   ADD CONSTRAINT FK_ULE_USER
   FOREIGN KEY (user)
   REFERENCES user (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE account
   DROP FOREIGN KEY FK_ADDRESS_FILIALE;
ALTER TABLE account
   ADD CONSTRAINT FK_ADDRESS_FILIALE
   FOREIGN KEY (id_address)
   REFERENCES address (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE account
   DROP FOREIGN KEY FK_FORM_ACCOUNT;
ALTER TABLE account
   ADD CONSTRAINT FK_FORM_ACCOUNT
   FOREIGN KEY (id_form)
   REFERENCES account_form (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE account
   DROP FOREIGN KEY FK_ADDRESS_FILIALE;
ALTER TABLE account
   ADD CONSTRAINT FK_ADDRESS_FILIALE
   FOREIGN KEY (id_address)
   REFERENCES address (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE account_form
   DROP FOREIGN KEY FK_FORM_ENTITY;
ALTER TABLE account_form
   ADD CONSTRAINT FK_FORM_ENTITY
   FOREIGN KEY (id_entity)
   REFERENCES entities (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE account_third_party
   DROP FOREIGN KEY ID_TPA_ACCOUNT;
ALTER TABLE account_third_party
   ADD CONSTRAINT ID_TPA_ACCOUNT
   FOREIGN KEY (id_account)
   REFERENCES account (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE entity_legal_representative
   DROP FOREIGN KEY LEGAL_REPRESENTATIVE_ENTITY;
ALTER TABLE entity_legal_representative
   ADD CONSTRAINT LEGAL_REPRESENTATIVE_ENTITY
   FOREIGN KEY (entity)
   REFERENCES entities (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE entities
   DROP FOREIGN KEY FK_ADDRESS_MAILING;
ALTER TABLE entities
   ADD CONSTRAINT FK_ADDRESS_MAILING
   FOREIGN KEY (id_address_mailing)
   REFERENCES address (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE entities
   DROP FOREIGN KEY FK_ENTITY_ADDRESS;
ALTER TABLE entities
   ADD CONSTRAINT FK_ENTITY_ADDRESS
   FOREIGN KEY (address)
   REFERENCES address (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE entities
   DROP FOREIGN KEY FK_ENTITY_CONTACT;
ALTER TABLE entities
   ADD CONSTRAINT FK_ENTITY_CONTACT
   FOREIGN KEY (contact)
   REFERENCES third_party (id)
   ON UPDATE NO ACTION
   ON DELETE SET NULL;

ALTER TABLE entities
   DROP FOREIGN KEY FK_ENTITY_CONTACT2;
ALTER TABLE entities
   ADD CONSTRAINT FK_ENTITY_CONTACT2
   FOREIGN KEY (contact2)
   REFERENCES third_party (id)
   ON UPDATE NO ACTION
   ON DELETE SET NULL;

ALTER TABLE account_statement_type
   DROP FOREIGN KEY FK_ACCOUNT_STATEMENT;
ALTER TABLE account_statement_type
   ADD CONSTRAINT FK_ACCOUNT_STATEMENT
   FOREIGN KEY (account)
   REFERENCES account (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE param_tech
   DROP FOREIGN KEY FK_PARAM_ENTITY;
ALTER TABLE param_tech
   ADD CONSTRAINT FK_PARAM_ENTITY
   FOREIGN KEY (entity)
   REFERENCES entities (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE third_party
   DROP FOREIGN KEY FK_HOME_ADRESS;
ALTER TABLE third_party
   ADD CONSTRAINT FK_HOME_ADRESS
   FOREIGN KEY (id_home_adress)
   REFERENCES address (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;

ALTER TABLE third_party
   DROP FOREIGN KEY FK_USER_TP;
ALTER TABLE third_party
   ADD CONSTRAINT FK_USER_TP
   FOREIGN KEY (id_user_tp)
   REFERENCES user (id)
   ON UPDATE NO ACTION
   ON DELETE NO ACTION;