<%@ page errorPage="../../../ErrorPage.jsp" %>

<jsp:useBean id="quizProcessor" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizOutputProcessorJspBean" />

<%
	quizProcessor.init( request, quizProcessor.RIGHT_MANAGE_QUIZ );
    response.sendRedirect( quizProcessor.doModifyProcessorConfig( request ) );
%>


