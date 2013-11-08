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
package fr.paris.lutece.plugins.quiz.service.outputprocessor;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * Interface for output processor services
 */
public interface IQuizOutputProcessor
{
    /**
     * Do process the output processor
     * @param mapAnswers The map containing answers of the user to the quiz.
     *            Keys of the map can either be ids of questions or keys of free
     *            HTML parameters. Values of the map are data entered by the
     *            user
     * @param nIdQuiz The id of the quiz
     */
    void doProcessOutputProcessor( Map<String, String[]> mapAnswers, int nIdQuiz );

    /**
     * Get the HTML to display the configuration form of the processor for a
     * given quiz. Returns null if this processor does not need to display any
     * configuration form
     * @param request The request
     * @param nIdQuiz The id of the quiz
     * @return The HTML code to display, or null if no configuration is needed
     */
    String getProcessorConfigurationHtml( HttpServletRequest request, int nIdQuiz );

    /**
     * Do update the configuration of the output processor for a given quiz
     * @param request the request
     * @param nIdQuiz The id of the quiz
     */
    void doUpdateConfiguration( HttpServletRequest request, int nIdQuiz );

    /**
     * Notify the processor that it has been enabled for a given quiz
     * @param nIdQuiz The id of the quiz
     */
    void notifyProcessorEnabling( int nIdQuiz );

    /**
     * Notify the processor that it has been disabled for a given quiz. Any
     * configuration for the quiz must be removed once this method has been
     * called.
     * @param nIdQuiz The id of the quiz
     */
    void notifyProcessorDisabling( int nIdQuiz );

    /**
     * Get the unique id of the processor.
     * @return The unique id of the processor
     */
    String getProcessorId( );

    /**
     * Get the title of the processor
     * @param locale The locale to display the title in
     * @return The title of the processor
     */
    String getTitle( Locale locale );
}
