create table user_list
(
	id uuid not null constraint user_pk primary key,
	name varchar(100) not null,
	role varchar(100) not null
);
