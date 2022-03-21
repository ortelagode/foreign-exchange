DROP TABLE IF EXISTS tbl_conversion;

CREATE TABLE tbl_conversion (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  source_currency VARCHAR(3) NOT NULL,
  target_currency VARCHAR(3) NOT NULL,
  source_amount DECIMAL NOT NULL,
  converted_amount DECIMAL NOT NULL,
  created_at TIMESTAMP NOT NULL
);