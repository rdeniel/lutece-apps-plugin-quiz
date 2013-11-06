ALTER TABLE quiz_quiz ADD COLUMN display_step_by_step SMALLINT DEFAULT 0;
ALTER TABLE quiz_quiz ADD COLUMN results_at_the_end SMALLINT DEFAULT 0;

ALTER TABLE quiz_group ADD COLUMN is_free_html SMALLINT DEFAULT 0;
ALTER TABLE quiz_group ADD COLUMN html_content TEXT default NULL;

DROP TABLE IF EXISTS quiz_question_image;
CREATE TABLE quiz_question_image (
  id_question INT NOT NULL,
  image_content LONG VARBINARY NOT NULL,
  content_type VARCHAR(255),
  PRIMARY KEY  (id_question)
);