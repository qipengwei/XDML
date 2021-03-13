
create table user (
   id bigint primary key auto_increment,
   name varchar(100)
) DEFAULT CHARSET=utf8mb4;


create table match (
   id bigint primary key auto_increment,
   user_id bigint,
   score int
) DEFAULT CHARSET=utf8mb4;

insert into user(id, name)values (1, 'AAA');
insert into user(id, name)values (2, 'BBB');
insert into user(id, name)values (3, 'CCC');

insert into match(id, user_id, score) values (1, 1, 800);
insert into match(id, user_id, score) values (2, 2, 5000);
insert into match(id, user_id, score) values (3, 3, 3000);
insert into match(id, user_id, score) values (4, 2, 1000);
