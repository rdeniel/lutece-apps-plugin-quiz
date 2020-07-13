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
package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.Collection;

/**
 * This class provides instances management methods (create, find, ...) for QuizQuestion objects
 */
public final class QuizQuestionHome
{
    private static IQuizQuestionDAO _dao = SpringContextService.getBean( "quiz.questionDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private QuizQuestionHome( )
    {
    }

    /**
     * Creation of an instance of an article QuizQuestion
     * 
     * @param quizQuestion
     *            An instance of the QuizQuestion which contains the informations to store
     * @param plugin
     *            the plugin
     * @return The instance of the QuizQuestion which has been created
     */
    public static QuizQuestion create( QuizQuestion quizQuestion, Plugin plugin )
    {
        _dao.insert( quizQuestion, plugin );

        return quizQuestion;
    }

    /**
     * Updates of the QuizQuestion instance specified in parameter
     * 
     * @param question
     *            An instance of the QuizQuestion which contains the informations to store
     * @param plugin
     *            the plugin
     * @return The instance of the QuizQuestion which has been updated.
     */
    public static QuizQuestion update( QuizQuestion question, Plugin plugin )
    {
        _dao.store( question, plugin );

        return question;
    }

    /**
     * Deletes the QuizQuestion instance whose identifier is specified in parameter
     * 
     * @param nIdQuestion
     *            The identifier of the article QuizQuestion to delete in the database
     * @param plugin
     *            the plugin
     */
    public static void remove( int nIdQuestion, Plugin plugin )
    {
        _dao.delete( nIdQuestion, plugin );
    }

    /**
     * Deletes the QuizQuestion instance whose identifier is specified in parameter
     * 
     * @param nQuiz
     *            The identifier of the article QuizQuestion to delete in the database
     * @param plugin
     *            the plugin
     */
    public static void removeQuestionsByQuiz( int nQuiz, Plugin plugin )
    {
        _dao.deleteQuestionsByQuiz( nQuiz, plugin );
    }

    /**
     * Returns an instance of the article QuizQuestion whose identifier is specified in parameter
     * 
     * @param nKey
     *            The primary key of the article to find in the database
     * @param plugin
     *            the plugin
     * @return An instance of the QuizQuestion which corresponds to the key
     */
    public static QuizQuestion findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Returns QuizQuestion list
     * 
     * @param plugin
     *            the plugin
     * @param nIdQuiz
     *            The identifier of the article Quiz to find it in the database
     * @return the list of the QuizQuestion of the database in form of a QuizQuestion Collection object
     */
    public static Collection<QuizQuestion> findAll( int nIdQuiz, Plugin plugin )
    {
        return _dao.selectQuestionsList( nIdQuiz, plugin );
    }

    /**
     * Gets a list of questions for a group
     * 
     * @param nIdQuiz
     *            The Quiz id
     * @param nIdGroup
     *            The group id
     * @param plugin
     *            The plugin
     * @return The list
     */
    public static Collection<Integer> findIdQuestionsByGroup( int nIdQuiz, int nIdGroup, Plugin plugin )
    {
        return _dao.getVerifyQuestionsByGroup( nIdQuiz, nIdGroup, plugin );
    }

    /**
     * Gets a list of questions for a group
     * 
     * @param nIdQuiz
     *            The Quiz id
     * @param nIdGroup
     *            The group id
     * @param plugin
     *            The plugin
     * @return The list
     */
    public static Collection<QuizQuestion> findQuestionsByGroup( int nIdQuiz, int nIdGroup, Plugin plugin )
    {
        return _dao.selectQuestionsByGroup( nIdQuiz, nIdGroup, plugin );
    }

    /**
     * Deletes the QuizQuestions instance by Group
     * 
     * @param nIdQuiz
     *            The identifier of the article Quiz to delete in the database
     * @param nIdGroup
     *            the id of the group
     * @param plugin
     *            the plugin
     */
    public static void removeQuestionsByGroup( int nIdQuiz, int nIdGroup, Plugin plugin )
    {
        _dao.deleteQuestionsByGroup( nIdQuiz, nIdGroup, plugin );
    }

    /**
     * Returns an instance of the article QuizQuestion whose identifier is specified in parameter
     * 
     * @param plugin
     *            the plugin
     * @return An instance of the QuizQuestion which corresponds to the key
     */
    public static QuizQuestion findLastQuestion( Plugin plugin )
    {
        return _dao.loadLastQuestion( plugin );
    }

    /**
     * Gets a list of questions that has at least one answer
     * 
     * @param nIdQuiz
     *            the Quiz Id
     * @param plugin
     *            The plugin
     * @return a list of questions
     */
    public static Collection<QuizQuestion> findQuestionsWithAnswer( int nIdQuiz, Plugin plugin )
    {
        return _dao.selectQuestionsListWithAnswer( nIdQuiz, plugin );
    }

    /**
     * Gets a list of questions that has at least one answer and re associated with a given group
     * 
     * @param nIdQuiz
     *            the Quiz Id
     * @param nIdGroup
     *            The id of the group
     * @param plugin
     *            The plugin
     * @return a list of questions
     */
    public static Collection<QuizQuestion> findQuestionsWithAnswerByIdGroup( int nIdQuiz, int nIdGroup, Plugin plugin )
    {
        return _dao.selectQuestionsListWithAnswerByIdGroup( nIdQuiz, nIdGroup, plugin );
    }
}
