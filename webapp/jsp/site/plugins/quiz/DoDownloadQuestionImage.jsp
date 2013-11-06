<%@ page errorPage="../../ErrorPagePortal.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="quiz" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizJspBean" />
<%fr.paris.lutece.plugins.quiz.web.QuizApp.doDownloadQuestionImage( request, response );%>