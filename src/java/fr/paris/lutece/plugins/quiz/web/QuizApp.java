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

import fr.paris.lutece.plugins.quiz.business.Quiz;
import fr.paris.lutece.plugins.quiz.service.QuizService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.text.MessageFormat;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class manages Quiz page.
 */
public class QuizApp implements XPageApplication
{
    private static final String TEMPLATE_QUIZ_LIST = "skin/plugins/quiz/quiz_list.html";
    private static final String TEMPLATE_QUIZ_RESULTS = "skin/plugins/quiz/quiz_results.html";
    private static final String TEMPLATE_QUIZ_ERROR = "skin/plugins/quiz/quiz_error.html";
    private static final String TEMPLATE_QUESTIONS_LIST = "skin/plugins/quiz/quiz.html";
    private static final String PROPERTY_QUIZ_LIST_PAGE_PATH = "quiz.xpage.pageQuizListPath";
    private static final String PROPERTY_QUIZ_LIST_PAGE_TITLE = "quiz.xpage.pageQuizListTitle";
    private static final String PROPERTY_QUIZ_PAGE_PATH = "quiz.xpage.pageQuizPath";
    private static final String PROPERTY_QUIZ_PAGE_TITLE = "quiz.xpage.pageQuizTitle";
    private static final String PROPERTY_QUIZ_RESULTS_PAGE_PATH = "quiz.xpage.pageQuizResultsPath";
    private static final String PROPERTY_QUIZ_RESULTS_PAGE_TITLE = "quiz.xpage.pageQuizResultsTitle";
    private static final String PROPERTY_QUIZ_ERROR_PAGE_PATH = "quiz.xpage.pageQuizErrorPath";
    private static final String PROPERTY_QUIZ_ERROR_PAGE_TITLE = "quiz.xpage.pageQuizErrorTitle";
    private static final String PARAMETER_RESULTS = "results";
    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_ID_QUIZ = "quiz_id";
    private static final String BEAN_QUIZ_SERVICE = "quiz.quizService";
    private static final String MARK_ERROR = "error";
    private static final String MARK_ID_QUIZ = "quiz_id";

    /**
     * Returns the Quiz XPage content depending on the request parameters and the current mode.
     *
     * @param request The HTTP request.
     * @param nMode The current mode.
     * @param plugin The Plugin
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException Message displayed if an exception occures
     * @return The page content.
     */
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
        throws SiteMessageException
    {
        String strIdQuiz = request.getParameter( PARAMETER_ID_QUIZ );
        String strAction = request.getParameter( PARAMETER_ACTION );
        XPage page = new XPage(  );

        if ( strIdQuiz != null )
        {
            int nIdQuiz = Integer.parseInt( strIdQuiz );

            if ( ( strAction != null ) && strAction.equals( PARAMETER_RESULTS ) )
            {
                page = getQuizResults( nIdQuiz, request.getLocale(  ), request.getParameterMap(  ) );
            }
            else
            {
                page = getQuizPage( nIdQuiz, request.getLocale(  ) );
            }
        }
        else
        {
            page = getQuizList( request.getLocale(  ) );
        }

        return page;
    }

    /**
     * Get the quiz list
     * @param locale The locale
     * @return The XPage
     */
    private XPage getQuizList( Locale locale )
    {
        XPage page = new XPage(  );
        QuizService serviceQuiz = (QuizService) SpringContextService.getBean( BEAN_QUIZ_SERVICE );
        Map model = serviceQuiz.getQuizList(  );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUIZ_LIST, locale, model );
        page.setContent( template.getHtml(  ) );
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
    private XPage getQuizPage( int nQuizId, Locale locale )
    {
        XPage page = new XPage(  );
        QuizService serviceQuiz = (QuizService) SpringContextService.getBean( BEAN_QUIZ_SERVICE );
        Map model = serviceQuiz.getQuiz( nQuizId );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUESTIONS_LIST, locale, model );
        page.setContent( template.getHtml(  ) );

        String strPath = I18nService.getLocalizedString( PROPERTY_QUIZ_PAGE_PATH, locale );
        String strTitle = I18nService.getLocalizedString( PROPERTY_QUIZ_PAGE_TITLE, locale );
        Quiz quiz = (Quiz) model.get( QuizService.KEY_QUIZ );
        Object[] args = { quiz.getName(  ) };
        page.setPathLabel( MessageFormat.format( strPath, args ) );
        page.setTitle( MessageFormat.format( strTitle, args ) );

        return page;
    }

    /**
     * Return The Answers list
     * @param nQuizId  The quiz id
     * @param locale  The current locale
     * @param mapParameters  request parameters as a map
     * @return The XPage
     */
    private XPage getQuizResults( int nQuizId, Locale locale, Map mapParameters )
    {
        XPage page = new XPage(  );

        QuizService serviceQuiz = (QuizService) SpringContextService.getBean( BEAN_QUIZ_SERVICE );
        Map model = serviceQuiz.getResults( nQuizId, mapParameters, locale );

        String strError = (String) model.get( QuizService.KEY_ERROR );

        if ( strError != null )
        {
            return getErrorPage( nQuizId, strError, locale );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUIZ_RESULTS, locale, model );

        page.setContent( template.getHtml(  ) );

        String strPath = I18nService.getLocalizedString( PROPERTY_QUIZ_RESULTS_PAGE_PATH, locale );
        String strTitle = I18nService.getLocalizedString( PROPERTY_QUIZ_RESULTS_PAGE_TITLE, locale );
        Quiz quiz = (Quiz) model.get( QuizService.KEY_QUIZ );
        Object[] args = { quiz.getName(  ) };
        page.setPathLabel( MessageFormat.format( strPath, args ) );
        page.setTitle( MessageFormat.format( strTitle, args ) );

        return page;
    }

    /**
     * Returns an error page
     * @param strError The error message
     * @param locale The current locale
     * @return The XPage
     */
    private XPage getErrorPage( int nIdQuiz, String strError, Locale locale )
    {
        XPage page = new XPage(  );
        Map model = new HashMap(  );
        model.put( MARK_ERROR, strError );
        model.put( MARK_ID_QUIZ, Integer.toString(nIdQuiz) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUIZ_ERROR, locale, model );

        page.setContent( template.getHtml(  ) );

        String strPath = I18nService.getLocalizedString( PROPERTY_QUIZ_ERROR_PAGE_PATH, locale );
        String strTitle = I18nService.getLocalizedString( PROPERTY_QUIZ_ERROR_PAGE_TITLE, locale );
        page.setPathLabel( strPath );
        page.setTitle( strTitle );

        return page;
    }
}
