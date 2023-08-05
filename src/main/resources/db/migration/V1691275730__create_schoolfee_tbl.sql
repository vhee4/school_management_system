CREATE TABLE IF NOT EXISTS fees (
  id                bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  amount            double NOT NULL,
  fee_description   text,
  department_id     bigint NOT NULL,
  CONSTRAINT        FOREIGN KEY (department_id) REFERENCES department (id),
  created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);