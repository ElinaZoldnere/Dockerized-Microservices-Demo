
CREATE TABLE black_listed_persons (
  id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(200) NOT NULL,
  last_name VARCHAR(200) NOT NULL,
  personal_code VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX ix_black_listed_persons
ON black_listed_persons (first_name, last_name, personal_code);
