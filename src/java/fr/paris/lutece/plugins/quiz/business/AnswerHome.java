/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.Collection;
import java.util.List;


/**
 * This class provides instances management methods (create, find, ...) for
 * Answer objects
 */
public final class AnswerHome
{
    // Static variable pointed at the DAO instance
    private static IAnswerDAO _dao = SpringContextService.getBean( "quiz.answerDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private AnswerHome( )
    {
    }

    /**
     * Return valid answer count for a given question
     * @param nIdQuestion the question id
     * @param plugin The plugin
     * @return The count of valid answer
     */
    public static int getValidAnswerCount( int nIdQuestion, Plugin plugin )
    {
        int nCount = 0;
        Collection<Answer> answersList = AnswerHome.getAnswersList( nIdQuestion, plugin );

        for ( Answer answer : answersList )
        {
            if ( answer.isCorrect( ) )
            {
                nCount++;
            }
        }

        return nCount;
    }

    /**
     * Create an instance of the answer class
     * @param nIdQuestion The question Id
     * @param answer The instance of the Answer which contains the informations
     *            to store
     * @param plugin the Plugin
     * @return The instance of answer which has been created with its primary
     *         key.
     */
    public static Answer create( int nIdQuestion, Answer answer, Plugin plugin )
    {
        _dao.insert( nIdQuestion, answer, plugin );

        return answer;
    }

    /**
     * Update of the answer which is specified in parameter
     * @param answer The instance of the Answer which contains the data to store
     * @param plugin the Plugin
     * @return The instance of the answer which has been updated
     */
    public static Answer update( Answer answer, Plugin plugin )
    {
        _dao.store( answer, plugin );

        return answer;
    }

    /**
     * Remove the answer whose identifier is specified in parameter
     * @param nAnswerId The answer Id
     * @param plugin the Plugin
     */
    public static void remove( int nAnswerId, Plugin plugin )
    {
        _dao.delete( nAnswerId, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a answer whose identifier is specified in
     * parameter
     * @param nKey The answer primary key
     * @param plugin the Plugin
     * @return an instance of Answer
     */
    public static Answer findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Load the data of all the answer objects and returns them in form of a
     * list
     * @param nIdQuestion the id of the question
     * @param plugin the Plugin
     * @return the list which contains the data of all the answer objects
     */
    public static List<Answer> getAnswersList( int nIdQuestion, Plugin plugin )
    {
        return _dao.selectAnswersList( nIdQuestion, plugin );
    }

    /**
     * Remove answers for a Quiz
     * @param questionList The question list
     * @param plugin The plugin
     */
    public static void removeAnswersByQuestionList( Collection<QuizQuestion> questionList, Plugin plugin )
    {
        for ( QuizQuestion question : questionList )
        {
            removeAnswersByQuestion( question.getIdQuestion( ), plugin );
        }
    }

    /**
     * Remove answer for a given question
     * @param nIdQuestion The question ID
     * @param plugin The plugin
     */
    public static void removeAnswersByQuestion( int nIdQuestion, Plugin plugin )
    {
        _dao.deleteAnswersByQuestion( nIdQuestion, plugin );
    }

    /**
     * Search asnwers with profil
     * @param nIdProfil The identifier of the profil
     * @param plugin The plugin
     * @return <code>true</code> if there is at least one answer with profil,
     *         <code>false</code> otherwise
     */
    public static boolean isAnswersWithProfil( int nIdProfil, Plugin plugin )
    {
        return _dao.isAnswersWithProfil( nIdProfil, plugin );
    }

}
