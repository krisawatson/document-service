CREATE SCHEMA IF NOT EXISTS document_details;


CREATE TABLE IF NOT EXISTS document_details."document"
(
	doc_id bigint primary key,
	name varchar(64) not null,
	type varchar(32) not null,
	storage_location varchar(1024) not null,
	size bigint not null,
	owner_id varchar(32) not null,
	created timestamp with time zone default now() not null,
	updated timestamp with time zone
);

CREATE UNIQUE INDEX IF NOT EXISTS document_docid_uindex
	on document_details."document" (doc_id);

ALTER TABLE document_details."document" OWNER TO doc_user;
