--
-- Dumping data for table `quiz_answer`
--

INSERT INTO `quiz_answer` VALUES  (1,1,'BSD',1),
 (2,1,'GPL',2),
 (8,3,'Java',1),
 (3,2,'2001',0),
 (4,2,'2002',1),
 (5,2,'2003',0),
 (6,3,'PHP',0),
 (7,3,'Python',0),
 (9,4,'Un CMS (Content Management System)',0),
 (10,4,'Un portail',0),
 (11,4,'Les deux à la fois',1),
 (12,1,'Cecill',0);


--
-- Dumping data for table `quiz_group`
--

INSERT INTO `quiz_group` VALUES  (2,'Lutece et le Libre','Default',1,1),
 (3,'Socle technique','',1,2);

--
-- Dumping data for table `quiz_question`
--

INSERT INTO `quiz_question` VALUES  (1,'Sous quelle licence Lutece est-il diffusé ?\r\n<br />',1,2,'Lutece est diffusé sous licence BSD'),
 (2,'En quelle année le code source de Lutèce a été diffusé sous licence Open Source ?',1,2,'Le Conseil de Paris a voté la diffusion du code source de Lutèce en Open Source lors de la séance du 20 septembre 2002.'),
 (3,'En quel langage est écrit Lutèce ?',1,3,'Lutèce est majoritairement écrit en Java. Il contient également du HTML, XML et du javascript.'),
 (4,'Quelle est la fonction de Lutèce ?',1,3,'Lutèce est à la fois un outil de gestion de contenu permettant la production de documents et la gestion de leur cycle de vie. Il assure également la fonction de portail, permettant ainsi l\'intégration d\'applications au sein de contenus éditoriaux.');

--
-- Dumping data for table `quiz_quiz`
--

INSERT INTO `quiz_quiz` VALUES  (1,'Connaissances générales sur Lutèce','Bienvenue sur ce petit quiz pour tester vos connaissances générales de Lutèce.\r\n<br />','Découvrez maintenant vos résultats',1,NULL,NULL,'2010-12-10',1,0,'');

