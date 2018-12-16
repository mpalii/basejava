CREATE TABLE resume (
  uuid      VARCHAR(36) PRIMARY KEY NOT NULL,
  full_name VARCHAR                 NOT NULL
);

CREATE TABLE contact (
  id          SERIAL,
  resume_uuid VARCHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        VARCHAR     NOT NULL,
  value       VARCHAR     NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index
  ON contact (resume_uuid, type);

CREATE TABLE section (
  id          SERIAL,
  resume_uuid VARCHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        VARCHAR     NOT NULL,
  content     VARCHAR     NOT NULL
);

CREATE UNIQUE INDEX section_uuid_type_index
  ON section (resume_uuid, type);