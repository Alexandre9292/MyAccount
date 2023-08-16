/**
* Initialization script
*/
insert into user(login,password,password2,first_name,last_name,email,profile) values ("admin","f99a454b2a7ca35cf2de70dea9c48c02","f99a454b2a7ca35cf2de70dea9c48c02","Admin","ADMIN","dco@aubay.com",1);

insert into role(id,label) values (1,"SA");
insert into role(id,label) values (2,"MGMT-ACCNT");
insert into role(id,label) values (3,"MGMT-DOC");
insert into role(id,label) values (4,"VIEW-CLT-DATA");
insert into role(id,label) values (5,"MGMT-PARAM");
insert into role(id,label) values (6,"VIEW-STAT");

insert into user_role(user,role) values ((select id from user where login="admin"),1);

-- Init data for the first user to save preferences and add other users
insert into language(locale,user_interface) values ("en", 1);
insert into language(locale,user_interface) values ("fr", 1);

-- Init date format to use over the whole application
insert into date_format(id,label) values (1, "dd-MM-yyyy");
insert into date_format(id,label) values (2, "yyyy-MM-dd");
insert into date_format(id,label) values (3, "MM-dd-yyyy");

-- Init parameters functional type list selected through the Parameters setting (via bank user account)
insert into param_func_type(id,label_default) values (2,"Legal status");
insert into param_func_type(id,label_default) values (3,"Account type");
insert into param_func_type(id,label_default) values (4,"Account currency");
insert into param_func_type(id,label_default) values (5,"Account statement periodicity");
insert into param_func_type(id,label_default) values (6,"Account statement support");
insert into param_func_type(id,label_default) values (7,"Account interactions language");
insert into param_func_type(id,label_default) values (8,"Account payment mode");
insert into param_func_type(id,label_default) values (9,"Third party position");
insert into param_func_type(id,label_default) values (10,"Third party signature authorization");

insert into param_func_type(id,label_default) values (1001,"Country");
insert into param_func_type(id,label_default) values (1002,"Language");
insert into param_func_type(id,label_default) values (1003,"Association counrty/language");
insert into param_func_type(id,label_default) values (1004,"Entity");
insert into param_func_type(id,label_default) values (1005,"Association counrty/entity");

insert into param_func_type(id,label_default) values (1010,"Commercial message login");
insert into param_func_type(id,label_default) values (1011,"Commercial message client");