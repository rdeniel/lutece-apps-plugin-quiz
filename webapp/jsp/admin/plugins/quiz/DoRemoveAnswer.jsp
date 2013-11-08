<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="quizQuestion" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizQuestionJspBean" />

<%
	quizQuestion.init( request, quizQuestion.RIGHT_MANAGE_QUIZ );
    response.sendRedirect( quizQuestion.doRemoveAnswer( request ) );
%>

