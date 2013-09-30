ALTER TABLE quiz_quiz ADD COLUMN type_quiz  VARCHAR(25) DEFAULT 'REPONSE';

ALTER TABLE quiz_answer ADD COLUMN id_profil INT DEFAULT 0;

DROP TABLE IF EXISTS quiz_profil;
CREATE TABLE quiz_profil (
  id_profil INT NOT NULL,
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  id_quiz INT default NULL,
  PRIMARY KEY  (id_profil)
);