<%@ page errorPage="../../../ErrorPage.jsp" %>

<jsp:useBean id="quizProcessors" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizOutputProcessorJspBean" />

<%
	quizProcessors.init( request, quizProcessors.RIGHT_MANAGE_QUIZ );
	String strContent = quizProcessors.getManageOutputProcessors( request, response );
	if ( strContent != null )
	{
%>

<jsp:include page="../../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../../AdminFooter.jsp" %>

<%
	}
%>
