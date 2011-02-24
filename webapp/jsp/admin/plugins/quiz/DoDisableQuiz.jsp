<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../CheckSession.jsp" />

<jsp:useBean id="quiz" scope="session" class="fr.paris.lutece.plugins.quiz.web.QuizJspBean" />
<jsp:useBean id="user" scope="session" class="fr.paris.lutece.portal.web.user.UserJspBean" />

<%
    if( user.check( "DEF_PLUGINS_MANAGEMENT" ) )
    {
        response.sendRedirect( quiz.doDisablePlugin( request , getServletContext()) );
    }
    else
    {
        response.sendRedirect( user.getPluginAccessDeniedUrl() );
    }
%>


