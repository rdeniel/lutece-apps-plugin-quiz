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
package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;


/**
 * This class provides instances management methods (create, find, ...) for
 * QuestionGroup objects
 */
public final class QuestionGroupHome
{
    // Static variable pointed at the DAO instance
    private static IQuestionGroupDAO _dao = SpringContextService.getBean( "quiz.questionGroupDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private QuestionGroupHome( )
    {
    }

    /**
     * Create an instance of the group class
     * @param nIdQuiz The quiz ID
     * @param group The instance of the QuestionGroup which contains the
     *            informations to store
     * @param plugin the Plugin
     * @return The instance of group which has been created with its primary
     *         key.
     */
    public static QuestionGroup create( int nIdQuiz, QuestionGroup group, Plugin plugin )
    {
        _dao.insert( nIdQuiz, group, plugin );

        return group;
    }

    /**
     * Update of the group which is specified in parameter
     * @param group The instance of the QuestionGroup which contains the data to
     *            store
     * @param plugin the Plugin
     * @return The instance of the group which has been updated
     */
    public static QuestionGroup update( QuestionGroup group, Plugin plugin )
    {
        _dao.store( group, plugin );

        return group;
    }

    /**
     * Remove the group whose identifier is specified in parameter
     * @param nIdQuiz The quiz ID
     * @param nGroupId The group Id
     * @param plugin the Plugin
     */
    public static void remove( int nIdQuiz, int nGroupId, Plugin plugin )
    {
        _dao.delete( nIdQuiz, nGroupId, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a group whose identifier is specified in parameter
     * @param nKey The group primary key
     * @param plugin the Plugin
     * @return an instance of QuestionGroup
     */
    public static QuestionGroup findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Load the data of every group objects attached to a given quiz and returns
     * them in a list
     * @param nIdQuiz The quiz ID
     * @param plugin the Plugin
     * @return the list which contains the data of all the group objects
     */
    public static List<QuestionGroup> getGroupsList( int nIdQuiz, Plugin plugin )
    {
        return _dao.selectQuestionGroupsList( nIdQuiz, plugin );
    }

    /**
     * Find a group by its quiz and position
     * @param nIdQuiz The quiz ID
     * @param nPosition The position of the required group
     * @param plugin the Plugin
     * @return The requested group, or null if no group has the given position
     */
    public static QuestionGroup getGroupByPosition( int nIdQuiz, int nPosition, Plugin plugin )
    {
        return _dao.selectQuestionGroupByPosition( nIdQuiz, nPosition, plugin );
    }

    /**
     * Select the list of Groups
     * @param nIdQuiz The quiz ID
     * @return Groups list
     * @param plugin plugin Quiz
     */
    public static ReferenceList getGroupsReferenceList( int nIdQuiz, Plugin plugin )
    {
        return _dao.selectQuestionGroupsReferenceList( nIdQuiz, plugin );
    }

    /**
     * Remove a group for a given quiz
     * @param nIdQuiz The quiz id
     * @param plugin The plugin
     */
    public static void removeByQuiz( int nIdQuiz, Plugin plugin )
    {
        _dao.deleteByQuiz( nIdQuiz, plugin );
    }

    /**
     * Move up a group
     * @param nIdQuiz The quiz id
     * @param group The group
     * @param plugin The plugin
     */
    public static void moveUpGroup( int nIdQuiz, QuestionGroup group, Plugin plugin )
    {
        _dao.changePositionUp( nIdQuiz, group, plugin );
    }

    /**
     * Move down a group
     * @param nIdQuiz The quiz id
     * @param group The group
     * @param plugin The plugin
     */
    public static void moveDownGroup( int nIdQuiz, QuestionGroup group, Plugin plugin )
    {
        _dao.changePositionDown( nIdQuiz, group, plugin );
    }

    /**
     * Find the id of last group in quiz
     * @param nIdQuiz the quiz id
     * @param plugin the plugin
     * @return the id of corresponding group
     */
    public static int findLastByQuiz( int nIdQuiz, Plugin plugin )
    {
        return _dao.findLastByQuiz( nIdQuiz, plugin );
    }

    /**
     * Check if a quiz has a free HTML group that will display the result of the
     * quiz
     * @param nIdQuiz The id of the quiz
     * @param plugin The plugin
     * @return True if the quiz has a free HTML group that will display the
     *         result of the quiz, false otherwise
     */
    public static boolean hasGroupDisplayScore( int nIdQuiz, Plugin plugin )
    {
        return _dao.hasGroupDisplayScore( nIdQuiz, plugin );
    }
}
