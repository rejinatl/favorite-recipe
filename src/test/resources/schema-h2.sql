
/*! SET storage_engine=INNODB */;

DROP TABLE IF EXISTS recipe_ingredient;
DROP TABLE IF EXISTS ingredient;
DROP TABLE IF EXISTS recipe;
CREATE TABLE ingredient (
  name varchar(255) NOT NULL,
  created_date datetime(6) NOT NULL,
  updated_date datetime(6) NOT NULL,
  PRIMARY KEY (name)
); 

CREATE TABLE recipe (
  id varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  description varchar(255) DEFAULT NULL,
  servings int NOT NULL,
  instruction text NOT NULL,
  is_vegetarian varchar(255) NOT NULL DEFAULT 'No',
  created_date datetime(6) NOT NULL,
  updated_date datetime(6) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE recipe_ingredient (
  recipe_id varchar(255) NOT NULL,
  ingredient_name varchar(255) NOT NULL,
  PRIMARY KEY (recipe_id,ingredient_name),
  CONSTRAINT fk_rc FOREIGN KEY (recipe_id) REFERENCES recipe (id),
  CONSTRAINT fk_in FOREIGN KEY (ingredient_name) REFERENCES ingredient (name)
);
