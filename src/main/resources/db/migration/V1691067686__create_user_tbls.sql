CREATE TABLE IF NOT EXISTS staff (
  id                bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email             varchar(255) NOT NULL UNIQUE,
  first_name        varchar(255) DEFAULT NULL,
  last_name         varchar(255) DEFAULT NULL,
  password          varchar(255) DEFAULT NULL,
  phone_number      varchar(255) DEFAULT NULL,
  staff_id          varchar(255) DEFAULT NULL,
  is_enabled        bit(1) NOT NULL,
  created_on        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_on  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS student (
  id                bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email             varchar(255) NOT NULL UNIQUE,
  first_name        varchar(255) NOT NULL,
  last_name         varchar(255) NOT NULL,
  password          varchar(255) NOT NULL,
  phone_number      varchar(255) NOT NULL,
  staff_id          varchar(255) NOT NULL,
  department_id     bigint NOT NULL,
  is_enabled        bit(1) NOT NULL,
  created_on        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_on  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);