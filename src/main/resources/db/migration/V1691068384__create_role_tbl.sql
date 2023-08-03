CREATE TABLE IF NOT EXISTS role (
  id                bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name             varchar(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS staff_roles (
  staff_id          bigint NOT NULL,
  role_id           bigint NOT NULL,
  CONSTRAINT        FOREIGN KEY (staff_id) REFERENCES staff (id),
  CONSTRAINT        FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE IF NOT EXISTS student_roles (
  student_id        bigint NOT NULL,
  role_id           bigint NOT NULL,
  CONSTRAINT        FOREIGN KEY (student_id) REFERENCES student (id),
  CONSTRAINT        FOREIGN KEY (role_id) REFERENCES role (id)
);