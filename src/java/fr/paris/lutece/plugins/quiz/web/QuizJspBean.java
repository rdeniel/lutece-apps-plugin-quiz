/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.quiz.web;

import fr.paris.lutece.plugins.quiz.business.AnswerHome;
import fr.paris.lutece.plugins.quiz.business.QuestionGroup;
import fr.paris.lutece.plugins.quiz.business.QuestionGroupHome;
import fr.paris.lutece.plugins.quiz.business.Quiz;
import fr.paris.lutece.plugins.quiz.business.QuizHome;
import fr.paris.lutece.plugins.quiz.business.QuizProfile;
import fr.paris.lutece.plugins.quiz.business.QuizProfileHome;
import fr.paris.lutece.plugins.quiz.business.QuizQuestion;
import fr.paris.lutece.plugins.quiz.business.QuizQuestionHome;
import fr.paris.lutece.plugins.quiz.business.images.QuizImage;
import fr.paris.lutece.plugins.quiz.business.images.QuizImageHome;
import fr.paris.lutece.plugins.quiz.service.QuizService;
import fr.paris.lutece.plugins.quiz.service.outputprocessor.QuizOutputProcessorManagementService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.upload.MultipartHttpServletRequest;
import fr.paris.lutece.util.date.DateUtil;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;


/**
 * This class provides the user interface to manage quiz ( manage,
 * create, modify, remove) and groups.
 */
public class QuizJspBean extends PluginAdminPageJspBean
{
    /**
     * Right associated with this JspBean
     */
    public static final String RIGHT_MANAGE_QUIZ = "QUIZ_MANAGEMENT";

    private static final long serialVersionUID = 832830320966397744L;

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_QUIZ = "quiz.manage_quiz.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_QUIZ = "quiz.create_quiz.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_QUIZ = "quiz.modify_quiz.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_PROFIL = "quiz.modify_profil.pageTitle";
    private static final String PROPERTY_CONFIRM_DELETE_QUIZ = "quiz.remove_quiz.confirmRemoveQuiz";
    private static final String PROPERTY_PAGE_TITLE_CREATE_GROUP = "quiz.create_group.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_FREE_HTML_GROUP = "quiz.create_group.pageTitleFreeHtml";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_GROUP = "quiz.modify_group.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_FREE_HTML_GROUP = "quiz.modify_group.pageTitleFreeHtml";
    private static final String PROPERTY_CONFIRM_DELETE_GROUP_NO_QUESTION = "quiz.remove_group.confirmRemoveGroupNoQuestion";
    private static final String PROPERTY_CONFIRM_DELETE_GROUP_WITH_QUESTIONS = "quiz.remove_group.confirmRemoveGroupWithQuestion";
    private static final String PROPERTY_CONFIRM_DELETE_QUIZ_PROFIL = "quiz.remove_question.confirmRemoveProfil";
    private static final String PROPERTY_IMPOSSIBLE_DELETE_QUIZ_PROFIL = "quiz.remove_question.impossibleRemoveProfil";
    private static final String PROPERTY_PAGE_TITLE_CREATE_PROFIL = "quiz.create_profil.pageTitle";
    private static final String MESSAGE_CANNOT_DELETE_QUIZ = "quiz.message_quiz.cannotDeleteQuiz";

    //Jsps
    private static final String JSP_DO_REMOVE_QUIZ = "jsp/admin/plugins/quiz/DoRemoveQuiz.jsp";
    private static final String JSP_DO_REMOVE_GROUP = "jsp/admin/plugins/quiz/DoRemoveGroup.jsp";
    private static final String JSP_MANAGE_QUIZ = "jsp/admin/plugins/quiz/ManageQuiz.jsp";
    private static final String JSP_DO_REMOVE_PROFIL = "jsp/admin/plugins/quiz/DoRemoveProfil.jsp";

    //Urls
    private static final String JSP_URL_MANAGE_QUIZ = "ManageQuiz.jsp";
    private static final String JSP_URL_MODIFY_QUIZ = "ModifyQuiz.jsp";
    private static final String JSP_URL_MANAGE_QUESTIONS = "ManageQuestions.jsp";

    //Parameters
    private static final String PARAMETER_QUIZ_ID = "quiz_id";
    private static final String PARAMETER_QUIZ_NAME = "quiz_name";
    private static final String PARAMETER_INTRODUCTION = "quiz_introduction";
    private static final String PARAMETER_CONCLUSION = "quiz_conclusion";
    private static final String PARAMETER_CGU = "quiz_cgu";
    private static final String PARAMETER_QUIZ_STATUS = "quiz_status";
    private static final String PARAMETER_GROUP_ID = "group_id";
    private static final String PARAMETER_GROUP_NAME = "group_name";
    private static final String PARAMETER_GROUP_SUBJECT = "group_subject";
    private static final String PARAMETER_DATE_BEGIN_DISPONIBILITY = "date_begin_disponibility";
    private static final String PARAMETER_DISPLAY_STEP_BY_STEP = "display_step_by_step";
    private static final String PARAMETER_DISPLAY_RESULTS_AFTER_EACH_STEP = "display_results_after_each_step";
    private static final String PARAMETER_TYPE = "quiz_type";
    private static final String PARAMETER_DATE_END_DISPONIBILITY = "date_end_disponibility";
    private static final String PARAMETER_ACTIVE_CAPTCHA = "active_captcha";
    private static final String PARAMETER_ACTIVE_REQUIREMENT = "active_requirement";
    private static final String PARAMETER_PROFIL_NAME = "profil_name";
    private static final String PARAMETER_PROFIL_ID = "profil_id";
    private static final String PARAMETER_PROFIL_DESCRIPTION = "profil_description";
    private static final String PARAMETER_APPLY = "apply";
    private static final String PARAMETER_HTML_CONTENT = "html_content";
    private static final String PARAMETER_GROUP_IMAGE = "group_image";
    private static final String PARAMETER_REMOVE_IMAGE = "remove_image";

    // Templates
    private static final String TEMPLATE_MANAGE_QUIZ = "admin/plugins/quiz/manage_quiz.html";
    private static final String TEMPLATE_CREATE_QUIZ = "admin/plugins/quiz/create_quiz.html";
    private static final String TEMPLATE_CREATE_GROUP = "admin/plugins/quiz/create_group.html";
    private static final String TEMPLATE_MODIFY_QUIZ = "admin/plugins/quiz/modify_quiz.html";
    private static final String TEMPLATE_MODIFY_GROUP = "admin/plugins/quiz/modify_group.html";
    private static final String TEMPLATE_CREATE_PROFIL = "admin/plugins/quiz/create_profil.html";
    private static final String TEMPLATE_MODIFY_PROFIL = "admin/plugins/quiz/modify_profil.html";

    //Markers
    private static final String MARK_QUIZ_LIST = "quiz_list";
    private static final String MARK_QUIZ = "quiz";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_GROUP = "group";
    private static final String MARK_IS_ACTIVE_CAPTCHA = "is_active_captcha";
    private static final String MARK_PROFIL = "profil";
    private static final String MARK_FREE_HTML = "freehtml";
    private static final String MARK_INPUT_PREFIX = "input_prefix";

    private static final String JCAPTCHA_PLUGIN = "jcaptcha";

    /**
     * Returns quiz management form
     * 
     * @param request The Http request
     * @return Html form
     */
    public String getManageQuiz( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_QUIZ );

        Collection<Quiz> listQuiz = QuizHome.findAll( getPlugin( ) );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ_LIST, listQuiz );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_QUIZ, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Returns the Quiz creation form
     * 
     * @param request The Http request
     * @return Html creation form
     */
    public String getCreateQuiz( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_QUIZ );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale( ).getLanguage( ) );
        model.put( MARK_IS_ACTIVE_CAPTCHA, PluginService.isPluginEnable( JCAPTCHA_PLUGIN ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_QUIZ, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Process quiz creation
     * 
     * @param request The Http request
     * @return URL
     */
    public String doCreateQuiz( HttpServletRequest request )
    {
        String strQuizName = request.getParameter( PARAMETER_QUIZ_NAME );
        String strIntroduction = request.getParameter( PARAMETER_INTRODUCTION );
        String strConclusion = request.getParameter( PARAMETER_CONCLUSION );
        String strCGU = request.getParameter( PARAMETER_CGU );
        String strDateBeginDisponibility = request.getParameter( PARAMETER_DATE_BEGIN_DISPONIBILITY );
        String strType = request.getParameter( PARAMETER_TYPE );
        boolean bDisplayStepByStep = Boolean.parseBoolean( request.getParameter( PARAMETER_DISPLAY_STEP_BY_STEP ) );
        boolean bDisplayResultAfterEachStep = Boolean.parseBoolean( request
                .getParameter( PARAMETER_DISPLAY_RESULTS_AFTER_EACH_STEP ) );

        java.util.Date tDateBeginDisponibility = null;
        tDateBeginDisponibility = DateUtil.formatDate( strDateBeginDisponibility, getLocale( ) );

        String strDateEndDisponibility = request.getParameter( PARAMETER_DATE_END_DISPONIBILITY );
        java.util.Date tDateEndDisponibility = null;
        tDateEndDisponibility = DateUtil.formatDate( strDateEndDisponibility, getLocale( ) );

        int nCaptcha;
        int nRequirement;

        if ( request.getParameter( PARAMETER_ACTIVE_CAPTCHA ) == null )
        {
            nCaptcha = 0;
        }
        else
        {
            nCaptcha = Integer.parseInt( request.getParameter( PARAMETER_ACTIVE_CAPTCHA ) );
        }

        if ( request.getParameter( PARAMETER_ACTIVE_REQUIREMENT ) == null )
        {
            nRequirement = 0;
        }
        else
        {
            nRequirement = Integer.parseInt( request.getParameter( PARAMETER_ACTIVE_REQUIREMENT ) );
        }

        Quiz quiz = new Quiz( );

        // Mandatory field
        if ( strQuizName.equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        quiz.setName( strQuizName );
        quiz.setIntroduction( strIntroduction );
        quiz.setConclusion( strConclusion );
        quiz.setCgu( strCGU );
        quiz.setDateBeginDisponibility( tDateBeginDisponibility );
        quiz.setDateEndDisponibility( tDateEndDisponibility );
        quiz.setDateCreation( new Timestamp( GregorianCalendar.getInstance( ).getTimeInMillis( ) ) );
        quiz.setActiveCaptcha( nCaptcha );
        quiz.setActiveRequirement( nRequirement );
        quiz.setTypeQuiz( strType );
        quiz.setDisplayStepByStep( bDisplayStepByStep );
        quiz.setDisplayResultAfterEachStep( bDisplayResultAfterEachStep );
        QuizHome.create( quiz, getPlugin( ) );

        Quiz quizCreated = QuizHome.findLastQuiz( getPlugin( ) );

        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, quizCreated.getIdQuiz( ) );

        return url.getUrl( );
    }

    /**
     * Returns the form for quiz modification
     * 
     * @param request The Http request
     * @return Html form
     */
    public String getModifyQuiz( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_QUIZ );

        int nQuizId = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nQuizId, getPlugin( ) );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale( ).getLanguage( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_QUIZ, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Process the Quiz modifications
     * 
     * @param request The Http request
     * @return Html form
     */
    public String doModifyQuiz( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        String strIntroduction = request.getParameter( PARAMETER_INTRODUCTION );
        String strConclusion = request.getParameter( PARAMETER_CONCLUSION );
        String strDateBeginDisponibility = request.getParameter( PARAMETER_DATE_BEGIN_DISPONIBILITY );
        boolean bDisplayStepByStep = Boolean.parseBoolean( request.getParameter( PARAMETER_DISPLAY_STEP_BY_STEP ) );
        boolean bDisplayResultAfterEachStep = Boolean.parseBoolean( request
                .getParameter( PARAMETER_DISPLAY_RESULTS_AFTER_EACH_STEP ) );

        java.util.Date tDateBeginDisponibility = null;
        tDateBeginDisponibility = DateUtil.formatDate( strDateBeginDisponibility, getLocale( ) );

        String strDateEndDisponibility = request.getParameter( PARAMETER_DATE_END_DISPONIBILITY );
        java.util.Date tDateEndDisponibility = null;
        tDateEndDisponibility = DateUtil.formatDate( strDateEndDisponibility, getLocale( ) );

        int nCaptcha;
        int nRequirement;

        if ( request.getParameter( PARAMETER_ACTIVE_CAPTCHA ) == null )
        {
            nCaptcha = 0;
        }
        else
        {
            nCaptcha = Integer.parseInt( request.getParameter( PARAMETER_ACTIVE_CAPTCHA ) );
        }

        if ( request.getParameter( PARAMETER_ACTIVE_REQUIREMENT ) == null )
        {
            nRequirement = 0;
        }
        else
        {
            nRequirement = Integer.parseInt( request.getParameter( PARAMETER_ACTIVE_REQUIREMENT ) );
        }

        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );

        // Mandatory field
        if ( request.getParameter( PARAMETER_QUIZ_NAME ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        quiz.setName( request.getParameter( PARAMETER_QUIZ_NAME ) );

        if ( !( request.getParameter( PARAMETER_QUIZ_STATUS ) == null ) )
        {
            quiz.setStatus( 1 );
        }
        else
        {
            quiz.setStatus( 0 );
        }

        quiz.setIntroduction( strIntroduction );
        quiz.setConclusion( strConclusion );
        quiz.setActiveCaptcha( nCaptcha );
        quiz.setActiveRequirement( nRequirement );
        quiz.setDateBeginDisponibility( tDateBeginDisponibility );
        quiz.setDateEndDisponibility( tDateEndDisponibility );
        quiz.setDisplayStepByStep( bDisplayStepByStep );
        quiz.setDisplayResultAfterEachStep( bDisplayResultAfterEachStep );

        QuizHome.update( quiz, getPlugin( ) );

        if ( StringUtils.isNotEmpty( request.getParameter( PARAMETER_APPLY ) ) )
        {
            UrlItem url = new UrlItem( JSP_URL_MODIFY_QUIZ );
            url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

            return url.getUrl( );
        }
        // if the operation occurred well, redirects towards the view of the User
        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUIZ );

        return url.getUrl( );
    }

    /**
     * Returns the quiz remove page
     * 
     * @param request The Http request
     * @return Html form
     */
    public String getRemoveQuiz( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        String strAdminMessageUrl;
        String strUrl;

        if ( quiz.isEnabled( ) )
        {
            String strMessageKey = MESSAGE_CANNOT_DELETE_QUIZ;
            strAdminMessageUrl = AdminMessageService.getMessageUrl( request, strMessageKey, JSP_MANAGE_QUIZ, "",
                    AdminMessage.TYPE_STOP );
        }
        else
        {
            strUrl = JSP_DO_REMOVE_QUIZ + "?" + PARAMETER_QUIZ_ID + "=" + nIdQuiz;

            String strMessageKey = PROPERTY_CONFIRM_DELETE_QUIZ;
            strAdminMessageUrl = AdminMessageService.getMessageUrl( request, strMessageKey, strUrl, "",
                    AdminMessage.TYPE_CONFIRMATION );
        }

        return strAdminMessageUrl;
    }

    /**
     * Remove a quiz
     * 
     * @param request The Http request
     * @return Html form
     */
    public String doRemoveQuiz( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        Collection<QuizQuestion> questions = QuizQuestionHome.findAll( nIdQuiz, getPlugin( ) );
        quiz.setQuestions( questions );

        QuestionGroupHome.removeByQuiz( nIdQuiz, getPlugin( ) );
        QuizHome.remove( nIdQuiz, getPlugin( ) );
        QuizQuestionHome.removeQuestionsByQuiz( nIdQuiz, getPlugin( ) );
        AnswerHome.removeAnswersByQuestionList( questions, getPlugin( ) );
        QuizProfileHome.removeProfilesByQuiz( nIdQuiz, getPlugin( ) );

        QuizOutputProcessorManagementService.getInstance( ).disableProcessors( nIdQuiz );

        // Go to the parent page
        return getHomeUrl( request );
    }

    /**
     * Returns quiz displaying form
     * 
     * @param request The Http request
     * @return Html form
     */
    public String doDisplayQuiz( HttpServletRequest request )
    {
        int nQuizId = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nQuizId, getPlugin( ) );

        if ( quiz.isEnabled( ) )
        {
            quiz.setStatus( 0 );
        }
        else
        {
            quiz.setStatus( 1 );
        }

        QuizHome.update( quiz, getPlugin( ) );

        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUIZ );

        return url.getUrl( );
    }

    /**
     * Returns the group create page
     * @param request The HTTP request
     * @return The group create page
     */
    public String getCreateGroup( HttpServletRequest request )
    {

        Boolean bIsFreeHtml = Boolean.parseBoolean( request.getParameter( MARK_FREE_HTML ) );
        if ( bIsFreeHtml )
        {
            setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_FREE_HTML_GROUP );
        }
        else
        {
            setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_GROUP );
        }

        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_FREE_HTML, bIsFreeHtml );
        model.put( MARK_INPUT_PREFIX, AppPropertiesService.getProperty( QuizService.PROPERTY_INPUT_PREFIX ) );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_GROUP, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Create a group
     * @param request The HTTP request
     * @return The exit url
     */
    public String doCreateGroup( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        String strGroupName = request.getParameter( PARAMETER_GROUP_NAME );
        String strGroupSubject = request.getParameter( PARAMETER_GROUP_SUBJECT );
        Boolean bIsFreeHtml = Boolean.parseBoolean( request.getParameter( MARK_FREE_HTML ) );

        strGroupName = strGroupName.substring( 0, 1 ).toUpperCase( ) + strGroupName.substring( 1 );

        QuestionGroup group = new QuestionGroup( );
        group.setLabelGroup( strGroupName );
        group.setSubject( strGroupSubject );
        group.setIdQuiz( nIdQuiz );
        group.setIsFreeHtml( bIsFreeHtml );
        if ( bIsFreeHtml )
        {
            String strHtmlContent = request.getParameter( PARAMETER_HTML_CONTENT );
            group.setHtmlContent( strHtmlContent );
        }
        else
        {
            group.setHtmlContent( null );
        }

        if ( request instanceof MultipartHttpServletRequest )
        {
            MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
            FileItem fileItem = multiPartRequest.getFile( PARAMETER_GROUP_IMAGE );
            if ( fileItem != null && fileItem.get( ) != null && fileItem.get( ).length > 0 )
            {
                QuizImage quizImage = new QuizImage( fileItem.get( ), fileItem.getContentType( ) );
                QuizImageHome.insertImage( quizImage, getPlugin( ) );
                group.setIdImage( quizImage.getIdImage( ) );
            }
        }

        QuestionGroupHome.create( nIdQuiz, group, getPlugin( ) );

        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

        return url.getUrl( );
    }

    /**
     * Returns the modify group page
     * @param request The HTTP request
     * @return The page
     */
    public String getModifyGroup( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        int nIdGroup = Integer.parseInt( request.getParameter( PARAMETER_GROUP_ID ) );
        QuestionGroup group = QuestionGroupHome.findByPrimaryKey( nIdGroup, getPlugin( ) );

        if ( group.getIsFreeHtml( ) )
        {
            setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_FREE_HTML_GROUP );
        }
        else
        {
            setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_GROUP );
        }

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_GROUP, group );
        model.put( MARK_FREE_HTML, group.getIsFreeHtml( ) );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale( ) );
        if ( group.getIsFreeHtml( ) )
        {
            model.put( MARK_INPUT_PREFIX, AppPropertiesService.getProperty( QuizService.PROPERTY_INPUT_PREFIX ) );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_GROUP, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Modify a group
     * @param request The HTTP request
     * @return The exit url
     */
    public String doModifyGroup( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdGroup = Integer.parseInt( request.getParameter( PARAMETER_GROUP_ID ) );
        String strGroupName = request.getParameter( PARAMETER_GROUP_NAME );
        String strGroupSubject = request.getParameter( PARAMETER_GROUP_SUBJECT );

        QuestionGroup group = QuestionGroupHome.findByPrimaryKey( nIdGroup, getPlugin( ) );
        group.setLabelGroup( strGroupName );
        group.setSubject( strGroupSubject );
        if ( group.getIsFreeHtml( ) )
        {
            String strHtmlContent = request.getParameter( PARAMETER_HTML_CONTENT );
            group.setHtmlContent( strHtmlContent );
        }
        else
        {
            group.setHtmlContent( null );
        }

        if ( request instanceof MultipartHttpServletRequest )
        {
            MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
            if ( Boolean.parseBoolean( request.getParameter( PARAMETER_REMOVE_IMAGE ) ) )
            {
                QuizImageHome.removeImage( group.getIdImage( ), getPlugin( ) );
                group.setIdImage( 0 );
            }
            else
            {
                FileItem fileItem = multiPartRequest.getFile( PARAMETER_GROUP_IMAGE );
                if ( fileItem != null && fileItem.get( ) != null && fileItem.get( ).length > 0 )
                {
                    QuizImage quizImage = new QuizImage( fileItem.get( ), fileItem.getContentType( ) );
                    // if there is no image associated with the given group, we create one
                    if ( group.getIdImage( ) == 0 )
                    {
                        QuizImageHome.insertImage( quizImage, getPlugin( ) );
                        group.setIdImage( quizImage.getIdImage( ) );
                    }
                    // otherwise, we update the existing image
                    else
                    {
                        quizImage.setIdImage( group.getIdImage( ) );
                        QuizImageHome.updateImage( quizImage, getPlugin( ) );
                    }
                }
            }
        }

        QuestionGroupHome.update( group, getPlugin( ) );

        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

        return url.getUrl( );
    }

    /**
     * Returns the question removing form
     * 
     * @param request The Http request
     * @return Html creation form
     */
    public String getRemoveGroup( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdGroup = Integer.parseInt( request.getParameter( PARAMETER_GROUP_ID ) );
        String strMessageKey = "";
        String strUrl = JSP_DO_REMOVE_GROUP + "?" + PARAMETER_QUIZ_ID + "=" + nIdQuiz + "&" + PARAMETER_GROUP_ID + "="
                + nIdGroup;

        Collection<Integer> idQuestionsList = QuizQuestionHome.findIdQuestionsByGroup( nIdQuiz, nIdGroup, getPlugin( ) );

        if ( idQuestionsList == null )
        {
            strMessageKey = PROPERTY_CONFIRM_DELETE_GROUP_NO_QUESTION;
        }
        else
        {
            strMessageKey = PROPERTY_CONFIRM_DELETE_GROUP_WITH_QUESTIONS;
        }

        String strAdminMessageUrl = AdminMessageService.getMessageUrl( request, strMessageKey, strUrl, "",
                AdminMessage.TYPE_CONFIRMATION );

        return strAdminMessageUrl;
    }

    /**
     * Remove a group
     * 
     * @param request The Http request
     * @return the exit url
     */
    public String doRemoveGroup( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdGroup = Integer.parseInt( request.getParameter( PARAMETER_GROUP_ID ) );

        Collection<QuizQuestion> listQuestions = QuizQuestionHome
                .findQuestionsByGroup( nIdQuiz, nIdGroup, getPlugin( ) );

        for ( QuizQuestion question : listQuestions )
        {
            AnswerHome.removeAnswersByQuestion( question.getIdQuestion( ), getPlugin( ) );
        }

        QuizQuestionHome.removeQuestionsByGroup( nIdQuiz, nIdGroup, getPlugin( ) );
        QuestionGroupHome.remove( nIdQuiz, nIdGroup, getPlugin( ) );

        // Go to the parent page
        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

        return url.getUrl( );
    }

    /**
     * Move up a group
     * @param request The HTTP request
     * @return the exit url
     */
    public String doMoveUpGroup( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdGroup = Integer.parseInt( request.getParameter( PARAMETER_GROUP_ID ) );
        QuestionGroup group = QuestionGroupHome.findByPrimaryKey( nIdGroup, getPlugin( ) );
        QuestionGroupHome.moveUpGroup( nIdQuiz, group, getPlugin( ) );

        // Go to the parent page
        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

        return url.getUrl( );
    }

    /**
     * Move down a group
     * @param request The HTTP request
     * @return the exit url
     */
    public String doMoveDownGroup( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdGroup = Integer.parseInt( request.getParameter( PARAMETER_GROUP_ID ) );
        QuestionGroup group = QuestionGroupHome.findByPrimaryKey( nIdGroup, getPlugin( ) );
        QuestionGroupHome.moveDownGroup( nIdQuiz, group, getPlugin( ) );

        // Go to the parent page
        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

        return url.getUrl( );
    }

    /**
     * Returns the create profil page
     * @param request The HTTP request
     * @return The page
     */
    public String getCreateProfil( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_PROFIL );

        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale( ).getLanguage( ) );
        model.put( MARK_QUIZ, quiz );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_PROFIL, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Create a profil
     * @param request The HTTP request
     * @return The exit url
     */
    public String doCreateProfil( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        String strProfilName = request.getParameter( PARAMETER_PROFIL_NAME );
        String strProfilDescription = request.getParameter( PARAMETER_PROFIL_DESCRIPTION );

        if ( StringUtils.isEmpty( strProfilName ) || StringUtils.isEmpty( strProfilDescription ) )
        {
            return AdminMessageService.getMessageUrl( request, "quiz.create_profil.error", AdminMessage.TYPE_STOP );
        }

        strProfilName = strProfilName.substring( 0, 1 ).toUpperCase( ) + strProfilName.substring( 1 );

        QuizProfile profil = new QuizProfile( );
        profil.setName( strProfilName );
        profil.setDescription( strProfilDescription );
        profil.setIdQuiz( nIdQuiz );

        QuizProfileHome.create( profil, getPlugin( ) );

        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

        return url.getUrl( );
    }

    /**
     * Returns the profil removing form
     * @param request The Http request
     * @return Html creation form
     */
    public String getRemoveProfil( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdProfil = Integer.parseInt( request.getParameter( PARAMETER_PROFIL_ID ) );
        String strAdminMessageUrl = "";

        if ( AnswerHome.isAnswersWithProfil( nIdProfil, getPlugin( ) ) )
        {
            strAdminMessageUrl = AdminMessageService.getMessageUrl( request, PROPERTY_IMPOSSIBLE_DELETE_QUIZ_PROFIL,
                    AdminMessage.TYPE_STOP );
        }
        else
        {
            String strUrl = JSP_DO_REMOVE_PROFIL + "?" + PARAMETER_QUIZ_ID + "=" + nIdQuiz + "&" + PARAMETER_PROFIL_ID
                    + "=" + nIdProfil;

            strAdminMessageUrl = AdminMessageService.getMessageUrl( request, PROPERTY_CONFIRM_DELETE_QUIZ_PROFIL,
                    strUrl, "", AdminMessage.TYPE_CONFIRMATION );
        }

        return strAdminMessageUrl;
    }

    /**
     * Remove a profil
     * @param request The HTTP request
     * @return The exit url
     */
    public String doRemoveProfil( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdProfil = Integer.parseInt( request.getParameter( PARAMETER_PROFIL_ID ) );
        QuizProfileHome.remove( nIdProfil, getPlugin( ) );

        // Go to the parent page
        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

        return url.getUrl( );
    }

    /**
     * Returns the form for profil modification
     * @param request The Http request
     * @return Html form
     */
    public String getModifyProfil( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_PROFIL );

        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nProfilId = Integer.parseInt( request.getParameter( PARAMETER_PROFIL_ID ) );
        QuizProfile profil = QuizProfileHome.findByPrimaryKey( nProfilId, getPlugin( ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_PROFIL, profil );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale( ).getLanguage( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_PROFIL, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Process the modifications of a profil
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyProfil( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nProfilId = Integer.parseInt( request.getParameter( PARAMETER_PROFIL_ID ) );
        String strProfilName = request.getParameter( PARAMETER_PROFIL_NAME );
        String strProfilDescription = request.getParameter( PARAMETER_PROFIL_DESCRIPTION );

        // Mandatory fields
        if ( StringUtils.isEmpty( strProfilName ) || StringUtils.isEmpty( strProfilDescription ) )
        {
            return AdminMessageService.getMessageUrl( request, "quiz.create_profil.error", AdminMessage.TYPE_STOP );
        }

        QuizProfile quizProfil = QuizProfileHome.findByPrimaryKey( nProfilId, getPlugin( ) );

        quizProfil.setName( strProfilName );
        quizProfil.setDescription( strProfilDescription );

        QuizProfileHome.update( quizProfil, getPlugin( ) );

        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

        return url.getUrl( );
    }
}
