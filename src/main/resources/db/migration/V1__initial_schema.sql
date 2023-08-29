create table account
(
    id    varchar(50) not null primary key,
    balance numeric(12, 2)
);

create table card
(
    id          bigserial       not null primary key,
    card_number      varchar(50)     not null,
    pin_code    varchar(50)     not null,
    account_id   varchar(50)    not null references account (id)
);
create index idx_card_account_id on card (account_id);