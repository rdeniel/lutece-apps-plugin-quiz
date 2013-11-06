<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="quiz" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizQuestionJspBean" />

<%
    quiz.init( request, quiz.RIGHT_MANAGE_QUIZ );
    response.sendRedirect( quiz.doRemoveAnswer( request ) );
%>

