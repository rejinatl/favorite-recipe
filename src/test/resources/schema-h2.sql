
/*! SET storage_engine=INNODB */;

DROP TABLE IF EXISTS recipe_ingredient;
DROP TABLE IF EXISTS ingredient;
DROP TABLE IF EXISTS recipe;
CREATE TABLE ingredient (
  created_date datetime(6) DEFAULT NULL,
  updated_date datetime(6) DEFAULT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (name)
); 

CREATE TABLE recipe (
  servings int DEFAULT NULL,
  created_date datetime(6) DEFAULT NULL,
  updated_date datetime(6) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  id varchar(255) NOT NULL,
  instruction text,
  is_vegetarian varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE recipe_ingredient (
  ingredient_name varchar(255) NOT NULL,
  recipe_id varchar(255) NOT NULL,
  PRIMARY KEY (ingredient_name,recipe_id),
  CONSTRAINT fk_rc FOREIGN KEY (recipe_id) REFERENCES recipe (id),
  CONSTRAINT fk_in FOREIGN KEY (ingredient_name) REFERENCES ingredient (name)
);
