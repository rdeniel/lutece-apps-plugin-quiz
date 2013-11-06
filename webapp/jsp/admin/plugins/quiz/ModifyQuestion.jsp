<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="quiz" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizQuestionJspBean" />

<% quiz.init( request, quiz.RIGHT_MANAGE_QUIZ ); %>
<%= quiz.getModifyQuizQuestion ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>