<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="quiz" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizJspBean" />

<% quiz.init( request, quiz.RIGHT_MANAGE_QUIZ ); %>
<%= quiz.getCreateQuiz ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
