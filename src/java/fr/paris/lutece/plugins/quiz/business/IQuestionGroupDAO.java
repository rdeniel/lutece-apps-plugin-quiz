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
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * IQuestionGroupDAO Interface
 */
public interface IQuestionGroupDAO
{
    /**
     * Insert a new record in the table.
     * 
     * @param nIdQuiz
     *            The quiz ID
     * @param group
     *            instance of the QuestionGroup object to inssert
     * @param plugin
     *            the Plugin
     */
    void insert( int nIdQuiz, QuestionGroup group, Plugin plugin );

    /**
     * Update the record in the table
     * 
     * @param group
     *            the reference of the QuestionGroup
     * @param plugin
     *            the Plugin
     */
    void store( QuestionGroup group, Plugin plugin );

    /**
     * Delete a record from the table
     * 
     * @param nIdQuiz
     *            The Quiz ID
     * @param nIdGroup
     *            int identifier of the QuestionGroup to delete
     * @param plugin
     *            the Plugin
     */
    void delete( int nIdQuiz, int nIdGroup, Plugin plugin );

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the data from the table
     * 
     * @param nKey
     *            The identifier of the group
     * @param plugin
     *            the Plugin
     * @return The instance of the group
     */
    QuestionGroup load( int nKey, Plugin plugin );

    /**
     * Load the data of all the group objects and returns them as a List
     * 
     * @param nIdQuiz
     *            The Quiz Id
     * @param plugin
     *            the Plugin
     * @return The List which contains the data of all the group objects
     */
    List<QuestionGroup> selectQuestionGroupsList( int nIdQuiz, Plugin plugin );

    /**
     * Returns a list of groups
     * 
     * @param nIdQuiz
     *            The Quiz ID
     * @param plugin
     *            The plugin
     * @return The list
     */
    ReferenceList selectQuestionGroupsReferenceList( int nIdQuiz, Plugin plugin );

    /**
     * Delete for a quiz
     * 
     * @param nIdQuiz
     *            The Quiz ID
     * @param plugin
     *            The plugin
     */
    void deleteByQuiz( int nIdQuiz, Plugin plugin );

    /**
     * Move up a group
     * 
     * @param nIdQuiz
     *            The quiz Id
     * @param group
     *            The group
     * @param plugin
     *            The plugin
     */
    void changePositionUp( int nIdQuiz, QuestionGroup group, Plugin plugin );

    /**
     * Move down a group
     * 
     * @param nIdQuiz
     *            The quiz Id
     * @param group
     *            The group
     * @param plugin
     *            The plugin
     */
    void changePositionDown( int nIdQuiz, QuestionGroup group, Plugin plugin );

    /**
     * Find a group by its quiz and position
     * 
     * @param nIdQuiz
     *            The quiz ID
     * @param nPosition
     *            The position of the required group
     * @param plugin
     *            the Plugin
     * @return The requested group, or null if no group has the given position
     */
    QuestionGroup selectQuestionGroupByPosition( int nIdQuiz, int nPosition, Plugin plugin );

    /**
     * Find the id of last group in quiz
     * 
     * @param nIdQuiz
     *            the quiz id
     * @param plugin
     *            the plugin
     * @return the id of corresponding group
     */
    int findLastByQuiz( int nIdQuiz, Plugin plugin );

    /**
     * Check if a quiz has a free HTML group that will display the score of the quiz
     * 
     * @param nIdQuiz
     *            The id of the quiz
     * @param plugin
     *            The plugin
     * @return True if the quiz has a free HTML group that will display the result of the quiz, false otherwise
     */
    boolean hasGroupDisplayScore( int nIdQuiz, Plugin plugin );
}
