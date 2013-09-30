<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="quiz" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizJspBean" />

<%
    quiz.init( request, quiz.RIGHT_MANAGE_QUIZ );
    response.sendRedirect( quiz.doModifyProfil( request ) );
%>