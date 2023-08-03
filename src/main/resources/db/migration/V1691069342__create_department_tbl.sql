CREATE TABLE IF NOT EXISTS department (
  id                bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name             varchar(255) NOT NULL UNIQUE
);

INSERT department(name) VALUES('Computer Science');
INSERT department(name) VALUES('Statistics');
INSERT department(name) VALUES('Microbiology');
INSERT department(name) VALUES('Mathematics');
INSERT department(name) VALUES('Economics');
INSERT department(name) VALUES('Physics');
INSERT department(name) VALUES('Chemistry');
INSERT department(name) VALUES('Biology');
INSERT department(name) VALUES('Anatomy');
INSERT department(name) VALUES('Arts');
INSERT department(name) VALUES('Mechanical Engineering');
INSERT department(name) VALUES('Civil Engineering');
INSERT department(name) VALUES('Medicine');
INSERT department(name) VALUES('Electrical Engineering');
INSERT department(name) VALUES('Accounting');
INSERT department(name) VALUES('Philosophy');