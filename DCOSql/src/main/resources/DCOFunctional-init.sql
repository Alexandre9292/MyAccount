/**
* Functional Initialization script
*/

-- Init data for legal entities
insert into legal_entity(label) values ("BNP");
insert into legal_entity(label) values ("FORTIS");
insert into legal_entity(label) values ("BNL");
insert into legal_entity(label) values ("BGL");

-- Init the 19 BNP countries --
insert into country(locale, leagl_entity) values ("GB", 1);
insert into country(locale, leagl_entity) values ("FR", 1);
insert into country(locale, leagl_entity) values ("LU", 4);
insert into country(locale, leagl_entity) values ("DK", 1);
insert into country(locale, leagl_entity) values ("CZ", 1);
insert into country(locale, leagl_entity) values ("DE", 1);
insert into country(locale, leagl_entity) values ("NL", 2);
insert into country(locale, leagl_entity) values ("PT", 1);
insert into country(locale, leagl_entity) values ("NO", 2);
insert into country(locale, leagl_entity) values ("RO", 1);
insert into country(locale, leagl_entity) values ("ES", 1);
insert into country(locale, leagl_entity) values ("SE", 2);
insert into country(locale, leagl_entity) values ("IT", 3);
insert into country(locale, leagl_entity) values ("BE", 2);
insert into country(locale, leagl_entity) values ("AT", 1);
insert into country(locale, leagl_entity) values ("BG", 1);
insert into country(locale, leagl_entity) values ("CH", 1);
insert into country(locale, leagl_entity) values ("HU", 2);
insert into country(locale, leagl_entity) values ("IE", 1);

-- Type of documents for research and managment documents
insert into document_type (id,label) values (1,"Account Agreements");
insert into document_type (id,label) values (2,"Additional Documents");
insert into document_type (id,label) values (3,"Customer Requirements");
insert into document_type (id,label) values (4,"Common supporting");