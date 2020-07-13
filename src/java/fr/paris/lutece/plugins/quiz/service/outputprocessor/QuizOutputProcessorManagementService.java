/*
 * Copyright (c) 2002-2020, City of Paris
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

import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Service to manage quiz output processors.
 */
public final class QuizOutputProcessorManagementService
{
    private static final String DATASTORE_KEY_QUIZ_OUTPUT_PROCESSOR_ENABLED = "quiz.outputProcessor.enabled.";
    private static final String DATASTORE_KEY_QUIZ_OUTPUT_PROCESSOR_ENABLED_QUIZ = ".quiz.";
    private static QuizOutputProcessorManagementService _instance = new QuizOutputProcessorManagementService( );

    /**
     * Private constructor
     */
    private QuizOutputProcessorManagementService( )
    {
        // Do nothing
    }

    /**
     * Get the instance of the service
     * 
     * @return The instance of the service
     */
    public static QuizOutputProcessorManagementService getInstance( )
    {
        return _instance;
    }

    /**
     * Get the list of every processors
     * 
     * @return the list of every processors
     */
    public List<IQuizOutputProcessor> getProcessorsList( )
    {
        return SpringContextService.getBeansOfType( IQuizOutputProcessor.class );
    }

    /**
     * Get a processor from its id
     * 
     * @param strProcessorId
     *            The id of the processor to get
     * @return The processor, or null if no processor has the given id
     */
    public IQuizOutputProcessor getProcessor( String strProcessorId )
    {
        for ( IQuizOutputProcessor processor : getProcessorsList( ) )
        {
            if ( StringUtils.equals( strProcessorId, processor.getProcessorId( ) ) )
            {
                return processor;
            }
        }
        return null;
    }

    /**
     * Check if a processor is enabled or not for a given quiz
     * 
     * @param strProcessorId
     *            The id of the processor to check
     * @param nIdQuiz
     *            The id of the quiz
     * @return True if the processor is enabled, false otherwise
     */
    public boolean isProcessorEnabled( String strProcessorId, int nIdQuiz )
    {
        String strDatastoreKey = getProcessorEnablingDatastoreKey( strProcessorId, nIdQuiz );
        String strEnabled = DatastoreService.getDataValue( strDatastoreKey, null );
        if ( strEnabled == null )
        {
            DatastoreService.setDataValue( strDatastoreKey, Boolean.FALSE.toString( ) );
            return false;
        }
        return Boolean.parseBoolean( strEnabled );
    }

    /**
     * Enable or disable a processor for a given quiz. If the processor was enabled, it is disabled and vice versa
     * 
     * @param nIdQuiz
     *            The id of the quiz
     * @param strProcessorId
     *            The id of the processor to enable or disable
     */
    public void changeProcessorEnabling( String strProcessorId, int nIdQuiz )
    {
        boolean isEnabled = isProcessorEnabled( strProcessorId, nIdQuiz );
        IQuizOutputProcessor processor = getProcessor( strProcessorId );
        DatastoreService.setDataValue( getProcessorEnablingDatastoreKey( strProcessorId, nIdQuiz ), Boolean.toString( !isEnabled ) );
        if ( isEnabled )
        {
            processor.notifyProcessorDisabling( nIdQuiz );
        }
        else
        {
            processor.notifyProcessorEnabling( nIdQuiz );
        }
    }

    /**
     * Disable every enabled processors for a given quiz
     * 
     * @param nIdQuiz
     *            the id of the quiz to disable processors of
     */
    public void disableProcessors( int nIdQuiz )
    {
        for ( IQuizOutputProcessor processor : getProcessorsList( ) )
        {
            if ( isProcessorEnabled( processor.getProcessorId( ), nIdQuiz ) )
            {
                changeProcessorEnabling( processor.getProcessorId( ), nIdQuiz );
            }
        }
    }

    /**
     * Process every enabled processors for a given quiz over answers made by a user
     * 
     * @param mapAnswers
     *            the map containing answers
     * @param nIdQuiz
     *            The id of the answered quiz
     */
    public void processEnabledProcessors( Map<String, String [ ]> mapAnswers, String strScore, int nIdQuiz )
    {
        for ( IQuizOutputProcessor processor : getProcessorsList( ) )
        {
            if ( isProcessorEnabled( processor.getProcessorId( ), nIdQuiz ) )
            {
                processor.doProcessOutputProcessor( mapAnswers, strScore, nIdQuiz );
            }
        }
    }

    /**
     * Get the datastore key to save the enabling of a processor for a quiz
     * 
     * @param strProcessorId
     *            The id of the processor
     * @param nIdQuiz
     *            The id of the quiz
     * @return The datastore key
     */
    private String getProcessorEnablingDatastoreKey( String strProcessorId, int nIdQuiz )
    {
        return DATASTORE_KEY_QUIZ_OUTPUT_PROCESSOR_ENABLED + strProcessorId + DATASTORE_KEY_QUIZ_OUTPUT_PROCESSOR_ENABLED_QUIZ + nIdQuiz;
    }
}
