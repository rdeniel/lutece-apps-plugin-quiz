/*==============================================================*/
/*	Init  table core_admin_right								*/
/*==============================================================*/
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url, documentation_url) VALUES ('QUIZ_MANAGEMENT','quiz.adminFeature.quiz_management.name',2,'jsp/admin/plugins/quiz/ManageQuiz.jsp','quiz.adminFeature.quiz_management.description',0,'quiz','APPLICATIONS','images/admin/skin/plugins/quiz/quiz.png', 'jsp/admin/documentation/AdminDocumentation.jsp?doc=admin-quiz');


INSERT INTO core_user_right (id_right,id_user) VALUES ('QUIZ_MANAGEMENT',1);
INSERT INTO core_user_right (id_right,id_user) VALUES('QUIZ_MANAGEMENT',2);

