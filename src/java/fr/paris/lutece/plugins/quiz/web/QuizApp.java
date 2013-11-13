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

import fr.paris.lutece.plugins.quiz.business.QuestionGroupHome;
import fr.paris.lutece.plugins.quiz.business.Quiz;
import fr.paris.lutece.plugins.quiz.business.images.QuizImage;
import fr.paris.lutece.plugins.quiz.business.images.QuizImageHome;
import fr.paris.lutece.plugins.quiz.service.QuizService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;


/**
 * This class manages Quiz page.
 */
public class QuizApp implements XPageApplication
{
    private static final String TEMPLATE_QUIZ_LIST = "skin/plugins/quiz/quiz_list.html";
    private static final String TEMPLATE_QUIZ_RESULTS = "skin/plugins/quiz/quiz_results.html";
    private static final String TEMPLATE_QUIZ_RESULTS_STEP = "skin/plugins/quiz/quiz_results_step.html";
    private static final String TEMPLATE_QUIZ_ERROR = "skin/plugins/quiz/quiz_error.html";
    private static final String TEMPLATE_QUESTIONS_LIST = "skin/plugins/quiz/quiz.html";
    private static final String TEMPLATE_QUESTIONS_LIST_STEP = "skin/plugins/quiz/quiz_step.html";

    private static final String PROPERTY_QUIZ_LIST_PAGE_PATH = "quiz.xpage.pageQuizListPath";
    private static final String PROPERTY_QUIZ_LIST_PAGE_TITLE = "quiz.xpage.pageQuizListTitle";
    private static final String PROPERTY_QUIZ_PAGE_PATH = "quiz.xpage.pageQuizPath";
    private static final String PROPERTY_QUIZ_PAGE_TITLE = "quiz.xpage.pageQuizTitle";
    private static final String PROPERTY_QUIZ_RESULTS_PAGE_PATH = "quiz.xpage.pageQuizResultsPath";
    private static final String PROPERTY_QUIZ_RESULTS_PAGE_TITLE = "quiz.xpage.pageQuizResultsTitle";
    private static final String PROPERTY_QUIZ_ERROR_PAGE_PATH = "quiz.xpage.pageQuizErrorPath";
    private static final String PROPERTY_QUIZ_ERROR_PAGE_TITLE = "quiz.xpage.pageQuizErrorTitle";

    private static final String PARAMETER_RESULTS = "results";
    private static final String PARAMETER_ID_QUIZ = "quiz_id";
    private static final String PARAMETER_OLD_STEP = "old_step";
    private static final String PARAMETER_ID_IMAGE = "id_image";
    private static final String ACTION_NEXT_STEP = "nextStep";
    private static final String ACTION_BACK_FROM_ERROR = "backFromError";

    private static final String SESSION_KEY_QUIZ_STEP = "quiz.savedQuizResult";

    private static final String MARK_ERROR = "error";
    private static final String MARK_ID_QUIZ = "quiz_id";
    private static final String MARK_HAS_NEXT_STEP = "hasNextStep";

    private QuizService _quizService = SpringContextService.getBean( QuizService.BEAN_QUIZ_SERVICE );

    /**
     * Do download a quiz image
     * @param request The request
     * @param response The response
     */
    public static void doDownloadQuizImage( HttpServletRequest request, HttpServletResponse response )
    {
        String strIdImage = request.getParameter( PARAMETER_ID_IMAGE );
        if ( StringUtils.isNotEmpty( strIdImage ) && StringUtils.isNumeric( strIdImage ) )
        {
            OutputStream out = null;
            try
            {
                Plugin plugin = PluginService.getPlugin( QuizService.PLUGIN_NAME );
                int nIdImage = Integer.parseInt( strIdImage );
                byte[] bArrayResult = {};
                String strContentType = StringUtils.EMPTY;
                QuizImage quizImage = QuizImageHome.getImage( nIdImage, plugin );

                if ( quizImage != null && quizImage.getContent( ) != null )
                {
                    bArrayResult = quizImage.getContent( );
                    strContentType = quizImage.getContentType( );
                }
                // Add Cache Control HTTP header
                response.setHeader( "Cache-Control", "no-cache" ); // HTTP 1.1
                response.setDateHeader( "Expires", 0 ); // HTTP 1.0
                response.setContentLength( bArrayResult.length );
                response.setContentType( strContentType );

                // Write the resource content
                out = response.getOutputStream( );
                out.write( bArrayResult );
            }
            catch ( IOException e )
            {
                AppLogService.error( e.getMessage( ), e );
            }
            finally
            {
                if ( out != null )
                {
                    try
                    {
                        out.flush( );
                        out.close( );
                    }
                    catch ( IOException e )
                    {
                        AppLogService.error( e.getMessage( ), e );
                    }
                }
            }
        }
    }

    /**
     * Returns the Quiz XPage content depending on the request parameters and
     * the current mode.
     * 
     * @param request The HTTP request.
     * @param nMode The current mode.
     * @param plugin The Plugin
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException
     *             Message displayed if an exception occures
     * @return The page content.
     */
    @SuppressWarnings( "unchecked" )
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin ) throws SiteMessageException
    {
        String strIdQuiz = request.getParameter( PARAMETER_ID_QUIZ );
        String strAction = request.getParameter( QuizService.PARAMETER_ACTION );
        XPage page = null;

        if ( StringUtils.isNotEmpty( strIdQuiz ) && StringUtils.isNumeric( strIdQuiz ) )
        {
            int nIdQuiz = Integer.parseInt( strIdQuiz );

            Quiz quiz = _quizService.findQuizById( nIdQuiz );
            if ( !quiz.isEnabled( ) )
            {
                return getQuizList( request.getLocale( ) );
            }

            if ( quiz.getDisplayStepByStep( ) )
            {
                // We get the position of the old step
                String strOldStep = request.getParameter( PARAMETER_OLD_STEP );
                int nOldStepId;
                if ( StringUtils.isNotEmpty( strOldStep ) && StringUtils.isNumeric( strOldStep ) )
                {
                    nOldStepId = Integer.parseInt( strOldStep );
                }
                else
                {
                    nOldStepId = 0;
                }

                if ( !StringUtils.equals( ACTION_BACK_FROM_ERROR, strAction ) )
                {
                    if ( nOldStepId > 0 )
                    {
                        // We get responses of the user, and save them into the session
                        Map<String, String[]> mapResponsesCurrentStep = null;
                        if ( !StringUtils.equals( ACTION_NEXT_STEP, strAction ) )
                        {
                            mapResponsesCurrentStep = saveAndValidateQuizAnswers( quiz, nOldStepId,
                                    request.getParameterMap( ), request.getLocale( ), plugin, request.getSession( true ) );
                            // We check that the map does not contain errors
                            String[] strError = mapResponsesCurrentStep.get( QuizService.KEY_ERROR );

                            if ( strError != null && strError.length > 0 )
                            {
                                return getErrorPage( quiz.getIdQuiz( ), strError[0], nOldStepId, request.getLocale( ) );
                            }
                        }

                        // If we must display the result of the current step
                        if ( quiz.getDisplayResultAfterEachStep( ) && StringUtils.equals( strAction, PARAMETER_RESULTS ) )
                        {
                            page = getQuizStepResults( quiz, nOldStepId, request.getLocale( ), mapResponsesCurrentStep,
                                    request.getSession( ), plugin );
                        }
                    }
                    else
                    {
                        // this is the first step of the quiz, so we remove any answers to quiz made by the user
                        resetUserAnswers( request.getSession( ) );
                    }
                }

                if ( page == null )
                {
                    // Otherwise, we display the next step
                    page = getQuizNextStep( quiz, nOldStepId, request.getLocale( ) );
                    if ( page == null )
                    {
                        page = getQuizResults( quiz, request.getLocale( ), getUserAnswers( request.getSession( ) ),
                                plugin );
                    }
                }
            }
            else
            {
                if ( strAction != null && strAction.equals( PARAMETER_RESULTS ) )
                {
                    page = getQuizResults( quiz, request.getLocale( ), request.getParameterMap( ), plugin );
                }
                else
                {
                    page = getQuizPage( nIdQuiz, request.getLocale( ) );
                }
            }
        }
        else
        {
            page = getQuizList( request.getLocale( ) );
        }

        return page;
    }

    /**
     * Get the quiz list
     * @param locale The locale
     * @return The XPage
     */
    protected XPage getQuizList( Locale locale )
    {
        XPage page = new XPage( );
        Map<String, Object> model = _quizService.getQuizList( );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUIZ_LIST, locale, model );
        page.setContent( template.getHtml( ) );
        page.setTitle( I18nService.getLocalizedString( PROPERTY_QUIZ_LIST_PAGE_TITLE, locale ) );
        page.setPathLabel( I18nService.getLocalizedString( PROPERTY_QUIZ_LIST_PAGE_PATH, locale ) );

        return page;
    }

    /**
     * Gets the quiz page
     * @param nQuizId The quiz Id
     * @param locale The current locale
     * @return The XPage
     */
    protected XPage getQuizPage( int nQuizId, Locale locale )
    {
        XPage page = new XPage( );
        Map<String, Object> model = _quizService.getQuiz( nQuizId );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUESTIONS_LIST, locale, model );
        page.setContent( template.getHtml( ) );

        setQuizProperties( (Quiz) model.get( QuizService.KEY_QUIZ ), page, model, locale );

        return page;
    }

    /**
     * Get the next step of the quiz
     * @param quiz The quiz to display
     * @param nOldStepId The id of the last displayed step of the quiz, or 0 if
     *            no step was displayed
     * @param locale The locale
     * @return The XPage to display
     */
    protected XPage getQuizNextStep( Quiz quiz, int nOldStepId, Locale locale )
    {
        XPage page = new XPage( );
        Map<String, Object> model = _quizService.getQuizNextStep( quiz, nOldStepId );
        // If the model is null, then there is no more step to display
        if ( model == null )
        {
            return null;
        }
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUESTIONS_LIST_STEP, locale, model );
        page.setContent( template.getHtml( ) );

        setQuizProperties( quiz, page, model, locale );

        return page;
    }

    /**
     * Return The Answers list
     * @param quiz The quiz
     * @param locale The current locale
     * @param mapParameters request parameters as a map
     * @param plugin the plugin
     * @return The XPage
     */
    protected XPage getQuizResults( Quiz quiz, Locale locale, Map<String, String[]> mapParameters, Plugin plugin )
    {
        XPage page = new XPage( );

        Map<String, Object> model;

        if ( "PROFIL".equals( quiz.getTypeQuiz( ) ) )
        {
            model = _quizService.calculateQuizProfile( quiz.getIdQuiz( ), mapParameters, locale );
        }
        else
        {
            model = _quizService.getResults( quiz.getIdQuiz( ), mapParameters, locale );
        }

        String strError = (String) model.get( QuizService.KEY_ERROR );

        if ( strError != null )
        {
            return getErrorPage( quiz.getIdQuiz( ), strError, 0, locale );
        }

        _quizService.processEndOfQuiz( quiz, mapParameters );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUIZ_RESULTS, locale, model );

        page.setContent( template.getHtml( ) );

        setQuizResultProperties( quiz, page, model, locale );

        return page;
    }

    /**
     * Return The Answers list for the given step
     * @param quiz The quiz
     * @param nIdStep The id of the submitted step
     * @param locale The current locale
     * @param mapResponsesCurrentStep Responses of the current step
     * @param session The session
     * @param plugin the plugin
     * @return The XPage
     */
    protected XPage getQuizStepResults( Quiz quiz, int nIdStep, Locale locale,
            Map<String, String[]> mapResponsesCurrentStep, HttpSession session, Plugin plugin )
    {
        XPage page = new XPage( );

        Map<String, Object> model;
        if ( "PROFIL".equals( quiz.getTypeQuiz( ) ) )
        {
            model = _quizService.calculateQuizStepProfile( quiz, nIdStep, mapResponsesCurrentStep, locale, plugin );
        }
        else
        {
            model = _quizService.getStepResults( quiz, nIdStep, mapResponsesCurrentStep, locale, plugin );
        }

        if ( model == null )
        {
            return null;
        }

        model.put( PARAMETER_OLD_STEP, nIdStep );

        // If there is no next step
        if ( QuestionGroupHome.getGroupByPosition( quiz.getIdQuiz( ), nIdStep + 1, plugin ) == null )
        {
            model.put( MARK_HAS_NEXT_STEP, Boolean.FALSE );
            _quizService.processEndOfQuiz( quiz, getUserAnswers( session ) );
        }
        else
        {
            model.put( MARK_HAS_NEXT_STEP, Boolean.TRUE );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUIZ_RESULTS_STEP, locale, model );

        page.setContent( template.getHtml( ) );

        setQuizResultProperties( quiz, page, model, locale );

        return page;
    }

    /**
     * Returns an error page
     * @param nIdQuiz The id of the quiz
     * @param strError The error message
     * @param nStepToRedirect The id of the step to redirect the user to, or 0
     *            to redirect the user to the start of the quiz
     * @param locale The current locale
     * @return The XPage
     */
    protected XPage getErrorPage( int nIdQuiz, String strError, int nStepToRedirect, Locale locale )
    {
        XPage page = new XPage( );
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_ERROR, strError );
        model.put( MARK_ID_QUIZ, Integer.toString( nIdQuiz ) );
        if ( nStepToRedirect > 1 )
        {
            model.put( PARAMETER_OLD_STEP, nStepToRedirect - 1 );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUIZ_ERROR, locale, model );

        page.setContent( template.getHtml( ) );

        String strPath = I18nService.getLocalizedString( PROPERTY_QUIZ_ERROR_PAGE_PATH, locale );
        String strTitle = I18nService.getLocalizedString( PROPERTY_QUIZ_ERROR_PAGE_TITLE, locale );
        page.setPathLabel( strPath );
        page.setTitle( strTitle );

        return page;
    }

    /**
     * Save answers of a given step of a quiz into the session. If an answer is
     * missing, then return an error message in the result map
     * @param quiz The quiz
     * @param nIdStep The id of the step to validate
     * @param mapParameters The map of HTTP parameters
     * @param locale The locale
     * @param plugin The plugin
     * @param session the session
     * @return A map containing associations between question keys and answers
     */
    @SuppressWarnings( "unchecked" )
    private Map<String, String[]> saveAndValidateQuizAnswers( Quiz quiz, int nIdStep,
            Map<String, String[]> mapParameters, Locale locale, Plugin plugin, HttpSession session )
    {
        Map<String, String[]> mapUserAnswers = _quizService.getUserAnswersForGroup( quiz.getIdQuiz( ), nIdStep,
                mapParameters, locale, plugin );
        Map<String, String[]> mapOldAnswers = (Map<String, String[]>) session.getAttribute( SESSION_KEY_QUIZ_STEP );
        if ( mapOldAnswers != null )
        {
            mapOldAnswers.putAll( mapUserAnswers );
        }
        else
        {
            session.setAttribute( SESSION_KEY_QUIZ_STEP, mapUserAnswers );
        }
        return mapUserAnswers;
    }

    /**
     * Get the answers of a user stored in session
     * @param session The session
     * @return A map containing answers to questions of a quiz
     */
    @SuppressWarnings( "unchecked" )
    private Map<String, String[]> getUserAnswers( HttpSession session )
    {
        return (Map<String, String[]>) session.getAttribute( SESSION_KEY_QUIZ_STEP );
    }

    /**
     * Set the properties of the XPage to display a quiz
     * @param quiz The quiz displayed by the XPage
     * @param page The XPage to display
     * @param model The model
     * @param locale The locale
     */
    private void setQuizProperties( Quiz quiz, XPage page, Map<String, Object> model, Locale locale )
    {
        String strPath = I18nService.getLocalizedString( PROPERTY_QUIZ_PAGE_PATH, locale );
        String strTitle = I18nService.getLocalizedString( PROPERTY_QUIZ_PAGE_TITLE, locale );
        Object[] args = { quiz.getName( ) };
        page.setPathLabel( MessageFormat.format( strPath, args ) );
        page.setTitle( MessageFormat.format( strTitle, args ) );
    }

    /**
     * Remove any answers of quiz made by the user
     * @param session The session
     */
    private void resetUserAnswers( HttpSession session )
    {
        session.setAttribute( SESSION_KEY_QUIZ_STEP, null );
    }

    /**
     * Set the properties of the XPage to display a quiz result
     * @param quiz The quiz displayed by the XPage
     * @param page The XPage to display
     * @param model The model
     * @param locale The locale
     */
    private void setQuizResultProperties( Quiz quiz, XPage page, Map<String, Object> model, Locale locale )
    {
        String strPath = I18nService.getLocalizedString( PROPERTY_QUIZ_RESULTS_PAGE_PATH, locale );
        String strTitle = I18nService.getLocalizedString( PROPERTY_QUIZ_RESULTS_PAGE_TITLE, locale );
        Object[] args = { quiz.getName( ) };
        page.setPathLabel( MessageFormat.format( strPath, args ) );
        page.setTitle( MessageFormat.format( strTitle, args ) );
    }
}
