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

import fr.paris.lutece.plugins.quiz.service.outputprocessor.IQuizOutputProcessor;
import fr.paris.lutece.plugins.quiz.service.outputprocessor.QuizOutputProcessorManagementService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;


/**
 * Jsp bean to manage output processors
 */
public class QuizOutputProcessorJspBean extends PluginAdminPageJspBean
{
    /**
     * Right to manage quiz
     */
    public static final String RIGHT_MANAGE_QUIZ = QuizJspBean.RIGHT_MANAGE_QUIZ;

    private static final long serialVersionUID = -1939759974542460097L;

    private static final String MESSAGE_MANAGE_PROCESSORS_PAGE_TITLE = "quiz.manage_processord.pageTitle";

    private static final String PARAMETER_QUIZ_ID = "quiz_id";

    private static final String MARK_LIST_CONFIGURATION = "list_configuration";
    private static final String MARK_LIST_DISBALED_PROCESSORS = "list_disabled_processors";
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_ID_PROCESSOR = "idProcessor";
    private static final String MARK_CONTENT = "content";
    private static final String MARK_TITLE = "title";

    private static final String TEMPLATE_MANANGE_OUTPUT_PROCESSORS = "admin/plugins/quiz/processors/manage_processors.html";

    private static final String JSP_URL_MANAGE_QUIZ = "jsp/admin/plugins/quiz/ManageQuiz.jsp";
    private static final String JSP_URL_MANAGE_OUTPUT_PROCESSORS = "jsp/admin/plugins/quiz/processors/ManageOutputProcessors.jsp";

    /**
     * Get the page to manage output processors of a given quiz
     * @param request The request
     * @param response The response
     * @return The HTML content to display, or null if the user has been
     *         redirected top another URL
     */
    public String getManageOutputProcessors( HttpServletRequest request, HttpServletResponse response )
    {
        String strQuizId = request.getParameter( PARAMETER_QUIZ_ID );

        if ( StringUtils.isNotEmpty( strQuizId ) && StringUtils.isNumeric( strQuizId ) )
        {
            setPageTitleProperty( MESSAGE_MANAGE_PROCESSORS_PAGE_TITLE );
            int nQuizId = Integer.parseInt( strQuizId );
            //            Quiz quiz = _quizService.findQuizById( nQuizId );

            List<IQuizOutputProcessor> listProcessors = QuizOutputProcessorManagementService.getInstance( )
                    .getProcessorsList( );

            List<Map<String, Object>> listProcessorForm = new ArrayList<Map<String, Object>>( );
            List<IQuizOutputProcessor> listDisabledProcessors = new ArrayList<IQuizOutputProcessor>( );

            for ( IQuizOutputProcessor processor : listProcessors )
            {
                // If the processor is enabled
                if ( QuizOutputProcessorManagementService.getInstance( ).isProcessorEnabled(
                        processor.getProcessorId( ), nQuizId ) )
                {
                    Map<String, Object> mapProcessorContent = new HashMap<String, Object>( 2 );
                    mapProcessorContent.put( MARK_ID_PROCESSOR, processor.getProcessorId( ) );
                    mapProcessorContent.put( MARK_CONTENT, processor.getProcessorConfigurationHtml( request, nQuizId ) );
                    mapProcessorContent.put( MARK_TITLE, processor.getTitle( getLocale( ) ) );

                    listProcessorForm.add( mapProcessorContent );
                }
                else
                {
                    listDisabledProcessors.add( processor );
                }
            }

            Map<String, Object> model = new HashMap<String, Object>( );
            model.put( MARK_LIST_CONFIGURATION, listProcessorForm );
            model.put( MARK_LIST_DISBALED_PROCESSORS, listDisabledProcessors );
            model.put( MARK_LOCALE, getLocale( ) );
            model.put( PARAMETER_QUIZ_ID, nQuizId );

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANANGE_OUTPUT_PROCESSORS, getLocale( ),
                    model );
            return getAdminPage( template.getHtml( ) );
        }

        try
        {
            response.sendRedirect( AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_QUIZ );
        }
        catch ( IOException e )
        {
            AppLogService.error( e.getMessage( ), e );
        }
        return null;
    }

    /**
     * Do change the enabling of a processor for a given quiz
     * @param request The request
     * @return The next URL to redirect to
     */
    public String doEnableDisableProcessor( HttpServletRequest request )
    {
        String strIdQuiz = request.getParameter( PARAMETER_QUIZ_ID );
        if ( StringUtils.isNotEmpty( strIdQuiz ) && StringUtils.isNumeric( strIdQuiz ) )
        {
            int nIdQuiz = Integer.parseInt( strIdQuiz );
            String strIdProcessor = request.getParameter( MARK_ID_PROCESSOR );
            if ( nIdQuiz > 0 )
            {
                QuizOutputProcessorManagementService.getInstance( ).changeProcessorEnabling( strIdProcessor, nIdQuiz );

                UrlItem urlItem = new UrlItem( AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_OUTPUT_PROCESSORS );
                urlItem.addParameter( PARAMETER_QUIZ_ID, strIdQuiz );
                return urlItem.getUrl( );
            }
        }
        return AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_QUIZ;
    }

    /**
     * Do modify the configuration of a processor
     * @param request The request
     * @return The next URL to redirect to
     */
    public String doModifyProcessorConfig( HttpServletRequest request )
    {
        String strIdQuiz = request.getParameter( PARAMETER_QUIZ_ID );
        String strIdProcessor = request.getParameter( MARK_ID_PROCESSOR );
        if ( StringUtils.isNotEmpty( strIdProcessor ) && StringUtils.isNotEmpty( strIdQuiz )
                && StringUtils.isNumeric( strIdQuiz ) )
        {
            int nIdQuiz = Integer.parseInt( strIdQuiz );
            IQuizOutputProcessor processor = QuizOutputProcessorManagementService.getInstance( ).getProcessor(
                    strIdProcessor );
            if ( processor != null )
            {
                processor.doUpdateConfiguration( request, nIdQuiz );
            }
        }
        return AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_QUIZ;
    }
}
