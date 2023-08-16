/**
* Migration script from 1.1.1 to 1.1.2
* CNIL
*/

alter table account_third_party;
add column DEVISE_AMOUNT_LIMIT varchar(255);