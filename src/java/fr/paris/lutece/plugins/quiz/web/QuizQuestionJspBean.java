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

import fr.paris.lutece.plugins.quiz.business.Answer;
import fr.paris.lutece.plugins.quiz.business.AnswerHome;
import fr.paris.lutece.plugins.quiz.business.QuestionGroup;
import fr.paris.lutece.plugins.quiz.business.QuestionGroupHome;
import fr.paris.lutece.plugins.quiz.business.Quiz;
import fr.paris.lutece.plugins.quiz.business.QuizHome;
import fr.paris.lutece.plugins.quiz.business.QuizProfile;
import fr.paris.lutece.plugins.quiz.business.QuizProfileHome;
import fr.paris.lutece.plugins.quiz.business.QuizQuestion;
import fr.paris.lutece.plugins.quiz.business.QuizQuestionHome;
import fr.paris.lutece.plugins.quiz.business.QuizQuestionImage;
import fr.paris.lutece.plugins.quiz.business.QuizQuestionImageHome;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.upload.MultipartHttpServletRequest;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;


/**
 * Jsp Bean to manage questions and answers of quiz
 */
public class QuizQuestionJspBean extends PluginAdminPageJspBean
{
    public static final String RIGHT_MANAGE_QUIZ = QuizJspBean.RIGHT_MANAGE_QUIZ;

    private static final long serialVersionUID = 1101658075687495248L;

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_CREATE_QUESTION = "quiz.create_question.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MANAGE_QUESTIONS = "quiz.manage_questions.pageTitle";
    private static final String PROPERTY_CONFIRM_DELETE_QUIZ_QUESTION = "quiz.remove_question.confirmRemoveQuestion";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ANSWER = "quiz.create_answer.pageTitle";
    private static final String PROPERTY_CONFIRM_DELETE_ANSWER = "quiz.remove_answer.confirmRemoveAnswer";
    private static final String PROPERTY_CONFIRM_DELETE_ANSWER_TURN_RED = "quiz.remove_answer.confirmDeleteAnswerTurnRed";
    private static final String PROPERTY_LABEL_NO = "portal.util.labelNo";
    private static final String PROPERTY_LABEL_YES = "portal.util.labelYes";
    private static final String MESSAGE_ONLY_ONE_ANSWER = "quiz.message.noMoreThanOneGoodAnswer";

    //Jsps
    private static final String JSP_DO_REMOVE_QUESTION = "jsp/admin/plugins/quiz/DoRemoveQuestion.jsp";
    private static final String JSP_DO_REMOVE_ANSWER = "jsp/admin/plugins/quiz/DoRemoveAnswer.jsp";

    //Urls
    private static final String JSP_URL_MANAGE_QUESTIONS = "ManageQuestions.jsp";
    private static final String JSP_URL_MODIFY_QUESTION = "ModifyQuestion.jsp";

    //Parameters
    private static final String PARAMETER_QUIZ_ID = "quiz_id";
    private static final String PARAMETER_QUESTION_ID = "question_id";
    private static final String PARAMETER_QUESTION_LABEL = "question_label";
    private static final String PARAMETER_GROUP_ID = "group_id";
    private static final String PARAMETER_ANSWER_LABEL = "answer_label";
    private static final String PARAMETER_ID_VALIDATION = "validity_id";
    private static final String PARAMETER_ID_PROFIL = "profil_id";
    private static final String PARAMETER_EXPLAINATION = "explaination";
    private static final String PARAMETER_ANSWER_ID = "answer_id";
    private static final String PARAMETER_QUESTION_IMAGE = "question_image";
    private static final String PARAMETER_REMOVE_IMAGE = "remove_image";

    // Templates
    private static final String TEMPLATE_CREATE_QUESTION = "admin/plugins/quiz/create_question.html";
    private static final String TEMPLATE_MODIFY_QUESTION = "admin/plugins/quiz/modify_question.html";
    private static final String TEMPLATE_MANAGE_QUESTIONS = "admin/plugins/quiz/manage_questions.html";
    private static final String TEMPLATE_CREATE_ANSWER = "admin/plugins/quiz/create_answer.html";
    private static final String TEMPLATE_MODIFY_ANSWER = "admin/plugins/quiz/modify_answer.html";

    //Markers
    private static final String MARK_QUIZ = "quiz";
    private static final String MARK_QUESTION = "question";
    private static final String MARK_GROUPS_LIST = "groups_list";
    private static final String MARK_GROUPS_REFERENCE_LIST = "groups_list";
    private static final String MARK_QUESTIONS_GROUP = "questions_list_group";
    private static final String MARK_QUESTIONS_WITHOUT_GROUP = "questions_list_without_group";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_ANSWER_LIST = "answer_list";
    private static final String MARK_ANSWER = "answer";
    private static final String MARK_IS_TYPE_PROFIL = "isTypeProfil";
    private static final String MARK_LIST_PROFILS = "listProfils";
    private static final String MARK_HAS_IMAGE = "hasImage";

    private static final String MARK_YESNO_LIST = "yesno_list";

    /**
     * Return the manage questions page
     * @param request The HTTP request
     * @return The page
     */
    public String getManageQuestions( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_QUESTIONS );

        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );

        Collection<QuizQuestion> questionsList = QuizQuestionHome.findAll( nIdQuiz, getPlugin( ) );
        List<QuestionGroup> listGroups = QuestionGroupHome.getGroupsList( nIdQuiz, getPlugin( ) );

        Collection<QuizQuestion> questionsListGroup = new ArrayList<QuizQuestion>( );
        Collection<QuizQuestion> questionsListWithoutGroup = new ArrayList<QuizQuestion>( );
        Collection<Answer> listAnswers = new ArrayList<Answer>( );

        for ( QuizQuestion question : questionsList )
        {
            if ( question.getIdGroup( ) == 0 )
            {
                questionsListWithoutGroup.add( question );
            }
            else
            {
                questionsListGroup.add( question );
            }

            listAnswers = AnswerHome.getAnswersList( question.getIdQuestion( ), getPlugin( ) );

            QuizQuestionHome.update( question, getPlugin( ) );
        }

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_QUESTIONS_GROUP, questionsListGroup );
        model.put( MARK_QUESTIONS_WITHOUT_GROUP, questionsListWithoutGroup );
        model.put( MARK_GROUPS_LIST, listGroups );
        model.put( MARK_ANSWER_LIST, listAnswers );

        if ( "PROFIL".equals( quiz.getTypeQuiz( ) ) )
        {
            Collection<QuizProfile> profilsList = QuizProfileHome.findAll( nIdQuiz, getPlugin( ) );

            model.put( MARK_LIST_PROFILS, profilsList );
            model.put( MARK_IS_TYPE_PROFIL, true );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_QUESTIONS, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Returns the Question creation form
     * 
     * @param request The Http request
     * @return Html creation form
     */
    public String getCreateQuizQuestion( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_QUESTION );

        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        ReferenceList groupsList = QuestionGroupHome.getGroupsReferenceList( nIdQuiz, getPlugin( ) );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_GROUPS_REFERENCE_LIST, groupsList );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale( ).getLanguage( ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_QUESTION, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Process question creation
     * 
     * @param request The Http request
     * @return URL
     */
    public String doCreateQuizQuestion( HttpServletRequest request )
    {
        // Mandatory field
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        String strQuestion = request.getParameter( PARAMETER_QUESTION_LABEL );
        int nIdGroup = Integer.parseInt( request.getParameter( PARAMETER_GROUP_ID ) );
        String strExplication = request.getParameter( PARAMETER_EXPLAINATION );

        // Mandatory fields
        if ( strQuestion.equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, "Vous n'avez pas remplis...", AdminMessage.TYPE_STOP );
        }

        QuizQuestion quizQuestion = new QuizQuestion( );
        quizQuestion.setQuestionLabel( strQuestion );
        quizQuestion.setIdQuiz( nIdQuiz );
        quizQuestion.setIdGroup( nIdGroup );
        quizQuestion.setExplaination( strExplication );
        QuizQuestionHome.create( quizQuestion, getPlugin( ) );

        QuizQuestion questionCreated = QuizQuestionHome.findLastQuestion( getPlugin( ) );

        if ( request instanceof MultipartHttpServletRequest )
        {
            MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
            FileItem fileItem = multiPartRequest.getFile( PARAMETER_QUESTION_IMAGE );
            if ( fileItem != null )
            {
                QuizQuestionImage questionImage = new QuizQuestionImage( questionCreated.getIdQuestion( ),
                        fileItem.get( ), fileItem.getContentType( ) );
                QuizQuestionImageHome.insertQuestionImage( questionImage, getPlugin( ) );
            }
        }

        // if the operation occurred well, redirects towards the view of the Quiz
        UrlItem url = new UrlItem( JSP_URL_MODIFY_QUESTION );
        url.addParameter( PARAMETER_QUIZ_ID, questionCreated.getIdQuiz( ) );
        url.addParameter( PARAMETER_QUESTION_ID, questionCreated.getIdQuestion( ) );

        return url.getUrl( );
    }

    /**
     * Returns the question modification form
     * 
     * @param request The Http request
     * @return Html form
     */
    public String getModifyQuizQuestion( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        QuizQuestion quizQuestion = QuizQuestionHome.findByPrimaryKey( nIdQuestion, getPlugin( ) );
        ReferenceList groupsList = QuestionGroupHome.getGroupsReferenceList( nIdQuiz, getPlugin( ) );
        Collection<Answer> listAnswer = AnswerHome.getAnswersList( nIdQuestion, getPlugin( ) );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_GROUPS_REFERENCE_LIST, groupsList );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale( ).getLanguage( ) );
        model.put( MARK_QUESTION, quizQuestion );
        model.put( MARK_ANSWER_LIST, listAnswer );
        model.put( MARK_HAS_IMAGE, QuizQuestionImageHome.doesQuestionHasImage( nIdQuestion, getPlugin( ) ) );
        if ( "PROFIL".equals( quiz.getTypeQuiz( ) ) )
        {
            for ( Answer answer : listAnswer )
            {
                String profil = QuizProfileHome.getName( answer.getIdProfile( ), getPlugin( ) );
                answer.setProfil( profil );
            }

            model.put( MARK_IS_TYPE_PROFIL, true );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_QUESTION, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Process the modifications of a question
     * 
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyQuizQuestion( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nQuestionId = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        String strQuestion = request.getParameter( PARAMETER_QUESTION_LABEL );
        int nIdGroup = Integer.parseInt( request.getParameter( PARAMETER_GROUP_ID ) );
        String strExplaination = request.getParameter( PARAMETER_EXPLAINATION );

        // Mandatory fields
        if ( strQuestion.equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        QuizQuestion quizQuestion = QuizQuestionHome.findByPrimaryKey( nQuestionId, getPlugin( ) );

        quizQuestion.setQuestionLabel( strQuestion );
        quizQuestion.setIdQuiz( nIdQuiz );
        quizQuestion.setIdGroup( nIdGroup );
        quizQuestion.setExplaination( strExplaination );
        QuizQuestionHome.update( quizQuestion, getPlugin( ) );

        if ( request instanceof MultipartHttpServletRequest )
        {
            MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
            if ( Boolean.parseBoolean( request.getParameter( PARAMETER_REMOVE_IMAGE ) ) )
            {
                QuizQuestionImageHome.removeQuestionImage( quizQuestion.getIdQuestion( ), getPlugin( ) );
            }
            else
            {
                FileItem fileItem = multiPartRequest.getFile( PARAMETER_QUESTION_IMAGE );
                if ( fileItem != null )
                {
                    QuizQuestionImage questionImage = new QuizQuestionImage( nQuestionId, fileItem.get( ),
                            fileItem.getContentType( ) );
                    // if there is no image associated with the given question, we create one
                    if ( QuizQuestionImageHome.getQuestionImage( nQuestionId, getPlugin( ) ) == null )
                    {
                        QuizQuestionImageHome.insertQuestionImage( questionImage, getPlugin( ) );
                    }
                    // otherwise, we update the existing image
                    else
                    {
                        QuizQuestionImageHome.updateQuestionImage( questionImage, getPlugin( ) );
                    }
                }
            }
        }

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
    public String getRemoveQuizQuestion( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdQuizQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        String strUrl = JSP_DO_REMOVE_QUESTION + "?" + PARAMETER_QUIZ_ID + "=" + nIdQuiz + "&" + PARAMETER_QUESTION_ID
                + "=" + nIdQuizQuestion;
        String strMessageKey = PROPERTY_CONFIRM_DELETE_QUIZ_QUESTION;
        String strAdminMessageUrl = AdminMessageService.getMessageUrl( request, strMessageKey, strUrl, "",
                AdminMessage.TYPE_CONFIRMATION );

        return strAdminMessageUrl;
    }

    /**
     * Remove a question
     * @param request The HTTP request
     * @return The exit url
     */
    public String doRemoveQuizQuestion( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdQuizQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        QuizQuestionHome.remove( nIdQuizQuestion, getPlugin( ) );
        AnswerHome.removeAnswersByQuestion( nIdQuizQuestion, getPlugin( ) );

        // Go to the parent page
        UrlItem url = new UrlItem( JSP_URL_MANAGE_QUESTIONS );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );

        return url.getUrl( );
    }

    /**
     * Returns the create answer page
     * @param request The HTTP request
     * @return The page
     */
    public String getCreateAnswer( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_ANSWER );

        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        QuizQuestion quizQuestion = QuizQuestionHome.findByPrimaryKey( nIdQuestion, getPlugin( ) );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale( ).getLanguage( ) );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_QUESTION, quizQuestion );

        if ( "PROFIL".equals( quiz.getTypeQuiz( ) ) )
        {
            ReferenceList profilsList = QuizProfileHome.selectQuizProfilsReferenceList( nIdQuiz, getPlugin( ) );

            if ( profilsList.isEmpty( ) )
            {
                return AdminMessageService.getMessageUrl( request, "quiz.create_answer.errorProfil",
                        AdminMessage.TYPE_STOP );
            }
            model.put( MARK_LIST_PROFILS, profilsList );
        }
        else
        {
            model.put( MARK_YESNO_LIST, getYesNoList( ) );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_ANSWER, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Create an answer
     * @param request The HTTP request
     * @return The exit url
     */
    public String doCreateAnswer( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        String strLabelAnswer = request.getParameter( PARAMETER_ANSWER_LABEL );
        int nValid = 0;
        int profilId = 0;

        if ( request.getParameter( PARAMETER_ID_VALIDATION ) != null )
        {
            nValid = Integer.parseInt( request.getParameter( PARAMETER_ID_VALIDATION ) );
        }

        if ( request.getParameter( PARAMETER_ID_PROFIL ) != null )
        {
            profilId = Integer.parseInt( request.getParameter( PARAMETER_ID_PROFIL ) );
        }

        if ( ( nValid == 1 ) && ( AnswerHome.getValidAnswerCount( nIdQuestion, getPlugin( ) ) > 0 ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_ONLY_ONE_ANSWER, AdminMessage.TYPE_STOP );
        }

        if ( ( strLabelAnswer == null ) || ( strLabelAnswer.equals( "" ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        Answer answer = new Answer( );
        answer.setIdQuestion( nIdQuestion );
        answer.setLabelAnswer( strLabelAnswer );
        answer.setValid( nValid );
        answer.setIdProfile( profilId );
        AnswerHome.create( nIdQuestion, answer, getPlugin( ) );

        // Go to the parent page
        UrlItem url = new UrlItem( JSP_URL_MODIFY_QUESTION );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );
        url.addParameter( PARAMETER_QUESTION_ID, nIdQuestion );

        return url.getUrl( );
    }

    /**
     * Returns the Modify answer page
     * @param request The HTTP request
     * @return The page
     */
    public String getModifyAnswer( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        int nIdQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        QuizQuestion question = QuizQuestionHome.findByPrimaryKey( nIdQuestion, getPlugin( ) );
        int nIdAnswer = Integer.parseInt( request.getParameter( PARAMETER_ANSWER_ID ) );
        Answer answer = AnswerHome.findByPrimaryKey( nIdAnswer, getPlugin( ) );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ, quiz );
        model.put( MARK_QUESTION, question );
        model.put( MARK_ANSWER, answer );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale( ).getLanguage( ) );
        if ( "PROFIL".equals( quiz.getTypeQuiz( ) ) )
        {
            ReferenceList profilsList = QuizProfileHome.selectQuizProfilsReferenceList( nIdQuiz, getPlugin( ) );

            if ( profilsList.isEmpty( ) )
            {
                return AdminMessageService.getMessageUrl( request, "quiz.create_answer.errorProfil",
                        AdminMessage.TYPE_STOP );
            }
            model.put( MARK_LIST_PROFILS, profilsList );
        }
        else
        {
            model.put( MARK_YESNO_LIST, getYesNoList( ) );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_ANSWER, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Modify the answer
     * @param request The HHTP request
     * @return The exit url
     */
    public String doModifyAnswer( HttpServletRequest request )
    {
        int nIdAnswer = Integer.parseInt( request.getParameter( PARAMETER_ANSWER_ID ) );
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        String strLabelAnswer = request.getParameter( PARAMETER_ANSWER_LABEL );
        Answer answer = AnswerHome.findByPrimaryKey( nIdAnswer, getPlugin( ) );

        int nValid = 0;
        int profilId = 0;

        if ( request.getParameter( PARAMETER_ID_VALIDATION ) != null )
        {
            nValid = Integer.parseInt( request.getParameter( PARAMETER_ID_VALIDATION ) );
        }

        if ( request.getParameter( PARAMETER_ID_PROFIL ) != null )
        {
            profilId = Integer.parseInt( request.getParameter( PARAMETER_ID_PROFIL ) );
        }

        if ( ( nValid == 1 ) && ( AnswerHome.getValidAnswerCount( nIdQuestion, getPlugin( ) ) > 0 ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_ONLY_ONE_ANSWER, AdminMessage.TYPE_STOP );
        }

        if ( ( strLabelAnswer == null ) || ( strLabelAnswer.equals( "" ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        if ( ( answer.getValid( ) == 0 ) && ( nValid == 1 )
                && ( AnswerHome.getValidAnswerCount( nIdQuestion, getPlugin( ) ) > 0 ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_ONLY_ONE_ANSWER, AdminMessage.TYPE_STOP );
        }

        answer.setIdQuestion( nIdQuestion );
        answer.setLabelAnswer( strLabelAnswer );
        answer.setValid( nValid );
        answer.setIdProfile( profilId );
        AnswerHome.update( answer, getPlugin( ) );

        // Go to the parent page
        UrlItem url = new UrlItem( JSP_URL_MODIFY_QUESTION );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );
        url.addParameter( PARAMETER_QUESTION_ID, nIdQuestion );

        return url.getUrl( );
    }

    /**
     * Returns the question removing form
     * 
     * @param request The Http request
     * @return Html creation form
     */
    public String getRemoveAnswer( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        QuizQuestion question = QuizQuestionHome.findByPrimaryKey( nIdQuestion, getPlugin( ) );
        int nIdAnswer = Integer.parseInt( request.getParameter( PARAMETER_ANSWER_ID ) );
        Answer answer2 = AnswerHome.findByPrimaryKey( nIdAnswer, getPlugin( ) );
        Collection<Answer> answersList = AnswerHome.getAnswersList( nIdQuestion, getPlugin( ) );
        int i = 0;
        String strMessageKey = "";

        for ( Answer answer : answersList )
        {
            if ( answer.isCorrect( ) )
            {
                i++;
            }
        }

        if ( ( i == 1 ) && ( answer2.getValid( ) == 1 ) ) //si 1 r�ponse bonne et r�ponse ajout�e bonne
        {
            strMessageKey = PROPERTY_CONFIRM_DELETE_ANSWER_TURN_RED;
            QuizQuestionHome.update( question, getPlugin( ) );
        }
        else
        {
            strMessageKey = PROPERTY_CONFIRM_DELETE_ANSWER;
        }

        String strUrl = JSP_DO_REMOVE_ANSWER + "?" + PARAMETER_QUIZ_ID + "=" + nIdQuiz + "&" + PARAMETER_QUESTION_ID
                + "=" + nIdQuestion + "&" + PARAMETER_ANSWER_ID + "=" + nIdAnswer;

        String strAdminMessageUrl = AdminMessageService.getMessageUrl( request, strMessageKey, strUrl, "",
                AdminMessage.TYPE_CONFIRMATION );

        return strAdminMessageUrl;
    }

    /**
     * Remove an answer
     * 
     * @param request The HTTP request
     * @return The exit url
     */
    public String doRemoveAnswer( HttpServletRequest request )
    {
        int nIdQuiz = Integer.parseInt( request.getParameter( PARAMETER_QUIZ_ID ) );
        int nIdAnswer = Integer.parseInt( request.getParameter( PARAMETER_ANSWER_ID ) );
        int nIdQuestion = Integer.parseInt( request.getParameter( PARAMETER_QUESTION_ID ) );
        AnswerHome.remove( nIdAnswer, getPlugin( ) );

        // Go to the parent page
        UrlItem url = new UrlItem( JSP_URL_MODIFY_QUESTION );
        url.addParameter( PARAMETER_QUIZ_ID, nIdQuiz );
        url.addParameter( PARAMETER_QUESTION_ID, nIdQuestion );

        return url.getUrl( );
    }

    /**
     * Return a reference list with Yes/No choice
     * @return The list
     */
    private ReferenceList getYesNoList( )
    {
        ReferenceList list = new ReferenceList( );
        list.addItem( 0, I18nService.getLocalizedString( PROPERTY_LABEL_NO, getLocale( ) ) );
        list.addItem( 1, I18nService.getLocalizedString( PROPERTY_LABEL_YES, getLocale( ) ) );

        return list;
    }
}
