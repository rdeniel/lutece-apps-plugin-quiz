<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="quiz" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizJspBean" />
<jsp:useBean id="user" scope="session" class="fr.paris.lutece.portal.web.user.UserJspBean" />

<%
    if( user.check( "QUIZ_MANAGEMENT" ) )
    {
%>
    <%= quiz.getDisplayQuestion( request ) %>
<%
    }
    else
    {
        response.sendRedirect( user.getPluginAccessDeniedUrl() );
    }
%>

<%@ include file="../../AdminFooter.jsp" %>

