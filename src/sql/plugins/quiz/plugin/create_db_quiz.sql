-- Table structure for table quiz_answer

DROP TABLE IF EXISTS quiz_answer;

CREATE TABLE quiz_answer (
  id_answer INT NOT NULL,
  id_question INT NOT NULL,
  label_answer varchar(255) NOT NULL,
  is_valid INT default NULL,
  id_profil INT DEFAULT 0,
  PRIMARY KEY  (id_answer)
);

-- Table structure for table quiz_question

DROP TABLE IF EXISTS quiz_question;

CREATE TABLE quiz_question (
  id_question INT NOT NULL,
  label_question varchar(255) NOT NULL,
  id_quiz INT NOT NULL,
  id_group INT NOT NULL,
  explaination varchar(255) NULL,
  PRIMARY KEY  (id_question)
);

-- Table structure for table quiz_quiz

DROP TABLE IF EXISTS quiz_quiz;

CREATE TABLE quiz_quiz (
  id_quiz INT NOT NULL,
  label_quiz varchar(50) NOT NULL,
  introduction varchar(255) default NULL,
  conclusion varchar(255) default NULL,
  status_quiz INT default '0',
  date_begin_disponibility date default NULL,
  date_end_disponibility date default NULL,
  date_creation date default NULL,
  activate_captcha int default NULL,
  activate_requirement int default NULL,
  cgu varchar(255) default NULL,
  type_quiz  VARCHAR(25) DEFAULT 'REPONSE',
  display_step_by_step SMALLINT DEFAULT 0,
  results_at_the_end SMALLINT DEFAULT 0,
  PRIMARY KEY  (id_quiz)
);

-- Table structure for table quiz_group

DROP TABLE IF EXISTS quiz_group;

CREATE TABLE quiz_group (
  id_group INT NOT NULL,
  label_group varchar(50) NOT NULL,
  subject varchar(255) default NULL,
  id_quiz INT NOT NULL,
  pos_group INT NOT NULL,
  PRIMARY KEY  (id_group)
);

-- Table structure for table quiz_type_question

DROP TABLE IF EXISTS quiz_type_question;

CREATE TABLE quiz_type_question (
  id_type_question INT NOT NULL,
  label_type_question varchar(50) NOT NULL default '',
  PRIMARY KEY  (id_type_question)
);

DROP TABLE IF EXISTS quiz_profil;
CREATE TABLE quiz_profil (
  id_profil INT NOT NULL,
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  id_quiz INT default NULL,
  PRIMARY KEY  (id_profil)
);

DROP TABLE IF EXISTS quiz_question_image;
CREATE TABLE quiz_question_image (
  id_question INT NOT NULL,
  image_content LONG VARBINARY NOT NULL,
  PRIMARY KEY  (id_question)
);

