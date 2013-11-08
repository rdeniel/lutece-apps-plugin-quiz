<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="quizQuestion" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizQuestionJspBean" />

<% quizQuestion.init( request, quizQuestion.RIGHT_MANAGE_QUIZ ); %>
<%= quizQuestion.getCreateAnswer ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
