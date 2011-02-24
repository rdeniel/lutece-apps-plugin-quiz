/*Table structure for table `quiz_answer` */

DROP TABLE IF EXISTS `quiz_answer`;

CREATE TABLE `quiz_answer` (
  `id_answer` int(11) NOT NULL,
  `id_question` int(11) NOT NULL,
  `label_answer` varchar(255) collate utf8_unicode_ci NOT NULL,
  `is_valid` int(11) default NULL,
  PRIMARY KEY  (`id_answer`)
);

/*Table structure for table `quiz_question` */

DROP TABLE IF EXISTS `quiz_question`;

CREATE TABLE `quiz_question` (
  `id_question` int(11) NOT NULL,
  `label_question` varchar(255) collate utf8_unicode_ci NOT NULL,
  `id_quiz` int(11) NOT NULL,
  `id_group` int(11) NOT NULL,
  `explaination` varchar(255) collate utf8_unicode_ci NULL,
  PRIMARY KEY  (`id_question`)
);

/*Table structure for table `quiz_quiz` */

DROP TABLE IF EXISTS `quiz_quiz`;

CREATE TABLE `quiz_quiz` (
  `id_quiz` int(11) NOT NULL,
  `label_quiz` varchar(50) collate utf8_unicode_ci NOT NULL,
  `introduction` varchar(255) collate utf8_unicode_ci default NULL,
  `conclusion` varchar(255) collate utf8_unicode_ci default NULL,
  `status_quiz` int(11) default '0',
  `date_begin_disponibility` date default NULL,
  `date_end_disponibility` date default NULL,
  `date_creation` date default NULL,
  `activate_captcha` int default NULL,
  `activate_requirement` int default NULL,
  `cgu` varchar(255) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`id_quiz`)
);

/*Table structure for table `quiz_group` */

DROP TABLE IF EXISTS `quiz_group`;

CREATE TABLE `quiz_group` (
  `id_group` int(11) NOT NULL,
  `label_group` varchar(50) collate utf8_unicode_ci NOT NULL,
  `subject` varchar(255) collate utf8_unicode_ci default NULL,
  `id_quiz` int(11) NOT NULL,
  `pos_group` int(11) NOT NULL,
  PRIMARY KEY  (`id_group`)
);

/*Table structure for table `quiz_type_question` */

DROP TABLE IF EXISTS `quiz_type_question`;

CREATE TABLE `quiz_type_question` (
  `id_type_question` int(11) NOT NULL,
  `label_type_question` varchar(50) collate utf8_unicode_ci NOT NULL default '',
  PRIMARY KEY  (`id_type_question`)
);
