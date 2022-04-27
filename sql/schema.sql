create table files (
	id_pk serial not null primary key,
	name varchar(128) not null
);

create table file_log (
	id_pk serial not null primary key,
	file_fk int not null references files(id_pk),
	status varchar(128) not null
);
