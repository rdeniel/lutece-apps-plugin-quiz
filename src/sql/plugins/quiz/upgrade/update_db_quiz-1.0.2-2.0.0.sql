ALTER TABLE quiz_quiz ADD COLUMN display_step_by_step SMALLINT DEFAULT 0;
ALTER TABLE quiz_quiz ADD COLUMN results_at_the_end SMALLINT DEFAULT 0;

ALTER TABLE quiz_group ADD COLUMN is_free_html SMALLINT DEFAULT 0;
ALTER TABLE quiz_group ADD COLUMN display_score SMALLINT DEFAULT 0;
ALTER TABLE quiz_group ADD COLUMN html_content TEXT default NULL;
ALTER TABLE quiz_group ADD COLUMN id_image INT default 0;
ALTER TABLE quiz_question ADD COLUMN id_image INT default 0;

DROP TABLE IF EXISTS quiz_image;
CREATE TABLE quiz_image (
  id_image INT NOT NULL,
  image_content LONG VARBINARY NOT NULL,
  content_type VARCHAR(255) NOT NULL,
  PRIMARY KEY  (id_image)
);
