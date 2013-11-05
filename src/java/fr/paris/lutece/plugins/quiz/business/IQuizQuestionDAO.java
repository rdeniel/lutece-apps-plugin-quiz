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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.Collection;


/**
 * 
 * @author coudercx
 */
interface IQuizQuestionDAO
{
    /**
     * Delete an instance of a question by Quiz
     * 
     * @param nQuiz An instance of the Quiz which contains the informations to
     *            store
     * @param plugin the plugin
     */
    void deleteQuestionsByQuiz( int nQuiz, Plugin plugin );

    /**
     * Creation o of an article Quiz
     * 
     * @param quizQuestion An if an instancenstance of the Quiz which contains
     *            the informations to store
     * @param plugin the plugin
     */
    void insert( QuizQuestion quizQuestion, Plugin plugin );

    /**
     * Updates of the Quiz instance specified in parameter
     * 
     * @param quizQuestion An instance of the Quiz which contains the
     *            informations to store
     * @param plugin the plugin
     */
    void store( QuizQuestion quizQuestion, Plugin plugin );

    /**
     * Deletes the Quiz instance whose identifier is specified in parameter
     * @param plugin the plugin
     * @param nIdQuizQuestion The identifier of the article Quiz to delete in
     *            the database
     */
    void delete( int nIdQuizQuestion, Plugin plugin );

    /**
     * Returns an instance of the article Quiz whose identifier is specified in
     * parameter
     * 
     * @param nKey The primary key of the article to find in the database
     * @param plugin the plugin
     * @return An instance of the Quiz which corresponds to the key
     */
    QuizQuestion load( int nKey, Plugin plugin );

    /**
     * Returns an instance of the article Quiz whose identifier is specified in
     * parameter
     * 
     * @param nIdQuiz The primary key of the article to find in the database
     * @param plugin the plugin
     * @return An instance of the Quiz which corresponds to the key
     */
    Collection<QuizQuestion> selectQuestionsList( int nIdQuiz, Plugin plugin );

    /**
     * Returns an instance of the article Quiz whose identifier is specified in
     * parameter
     * 
     * @param nIdQuiz The primary key of the article to find in the database
     * @param nIdGroup The primary key of the group
     * @param plugin the plugin
     * @return The list of ids of questions
     */
    Collection<Integer> getVerifyQuestionsByGroup( int nIdQuiz, int nIdGroup, Plugin plugin );

    /**
     * Deletes the Quiz instances whose identifier is specified in parameter by
     * group
     * @param nIdGroup the id of a group
     * @param nIdQuiz The identifier of the article Quiz to delete in the
     *            database
     * @param plugin the plugin
     */
    void deleteQuestionsByGroup( int nIdQuiz, int nIdGroup, Plugin plugin );

    /**
     * Returns an instance of the question which just created
     * 
     * @param plugin the plugin
     * @return An instance of the Quiz which corresponds to the key
     */
    QuizQuestion loadLastQuestion( Plugin plugin );

    /**
     * Returns a list of questions for a given group
     * @param nIdQuiz The quiz ID
     * @param nIdGroup The group Id
     * @param plugin The plugin
     * @return The list
     */
    Collection<QuizQuestion> selectQuestionsByGroup( int nIdQuiz, int nIdGroup, Plugin plugin );

    /**
     * Returns a list of questions with answers
     * @param nIdQuiz The quiz Id
     * @param plugin The plugin
     * @return The list
     */
    Collection<QuizQuestion> selectQuestionsListWithAnswer( int nIdQuiz, Plugin plugin );

    /**
     * Gets a list of questions that has at least one answer and re associated
     * with a given group
     * @param nIdQuiz the Quiz Id
     * @param nIdGroup The id of the group
     * @param plugin The plugin
     * @return a list of questions
     */
    Collection<QuizQuestion> selectQuestionsListWithAnswerByIdGroup( int nIdQuiz, int nIdGroup, Plugin plugin );
}
