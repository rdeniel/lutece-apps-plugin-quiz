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
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for QuestionGroup objects
 */
public final class QuestionGroupDAO implements IQuestionGroupDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_group ) FROM quiz_group";
    private static final String SQL_QUERY_SELECT = "SELECT id_group, label_group, subject, id_quiz, pos_group FROM quiz_group WHERE id_group = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO quiz_group ( id_group, label_group, subject, id_quiz, pos_group ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM quiz_group WHERE id_group = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE quiz_group SET id_group = ?, label_group = ?, subject = ?, id_quiz = ?, pos_group = ? WHERE id_group = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_group, label_group, subject, id_quiz, pos_group FROM quiz_group WHERE id_quiz = ?";
    private static final String SQL_QUERY_SELECTALL_GROUPS = "SELECT id_group, label_group FROM quiz_group WHERE id_quiz = ?";
    private static final String SQL_QUERY_DELETE_BY_QUIZ = "DELETE FROM quiz_group WHERE id_quiz = ? ";
    private static final String SQL_QUERY_SELECT_UPPER_GROUP = "SELECT id_group, label_group, subject, id_quiz, pos_group FROM quiz_group WHERE pos_group = ? AND id_quiz = ?";
    private static final String SQL_QUERY_SELECT_DOWNER_GROUP = "SELECT id_group, label_group, subject, id_quiz, pos_group FROM quiz_group WHERE pos_group = ? AND id_quiz = ?";

    //private static final String SQL_QUERY_UPDATE_BY_POSITION = "UPDATE quiz_group SET id_group = ?, label_group = ?, subject = ?, id_quiz = ?, pos_group = ? WHERE pos_group = ?";
    private static final String SQL_QUERY_SELECT_BY_POSITION = "SELECT id_group, label_group, subject, id_quiz, pos_group FROM quiz_group WHERE pos_group > ? AND id_quiz = ?";
    private static final String SQL_QUERY_NEW_POSITION_GROUP = "SELECT max( pos_group ) FROM quiz_group WHERE id_quiz = ?";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * The position of the group
     * @param nIdQuiz The quiz Id
     * @param plugin The plugin
     * @return The new position
     */
    public int newPositionGroup( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_POSITION_GROUP, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * Insert a new record in the table.
     * @param nIdQuiz The quiz Id
     * @param group instance of the QuestionGroup object to insert
     * @param plugin The plugin
     */
    public void insert( int nIdQuiz, QuestionGroup group, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        group.setIdGroup( newPrimaryKey( plugin ) );
        group.setPositionGroup( newPositionGroup( nIdQuiz, plugin ) );

        daoUtil.setInt( 1, group.getIdGroup(  ) );
        daoUtil.setString( 2, group.getLabelGroup(  ) );
        daoUtil.setString( 3, group.getSubject(  ) );
        daoUtil.setInt( 4, group.getIdQuiz(  ) );
        daoUtil.setInt( 5, group.getPositionGroup(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of the group from the table
     * @param nId The identifier of the group
     * @param plugin The plugin
     * @return the instance of the QuestionGroup
     */
    public QuestionGroup load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        QuestionGroup group = null;

        if ( daoUtil.next(  ) )
        {
            group = new QuestionGroup(  );

            group.setIdGroup( daoUtil.getInt( 1 ) );
            group.setLabelGroup( daoUtil.getString( 2 ) );
            group.setSubject( daoUtil.getString( 3 ) );
            group.setIdQuiz( daoUtil.getInt( 4 ) );
            group.setPositionGroup( daoUtil.getInt( 5 ) );
        }

        daoUtil.free(  );

        return group;
    }

    /**
     * Returns a list of group at a given position
     * @param nPosition The position
     * @param nIdQuiz The quiz Id
     * @param plugin The plugin
     * @return The list of group
     */
    private List<QuestionGroup> loadGroupByPosition( int nPosition, int nIdQuiz, Plugin plugin )
    {
        List<QuestionGroup> groupList = new ArrayList<QuestionGroup>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_POSITION, plugin );
        daoUtil.setInt( 1, nPosition );
        daoUtil.setInt( 2, nIdQuiz );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            QuestionGroup group = new QuestionGroup(  );

            group.setIdGroup( daoUtil.getInt( 1 ) );
            group.setLabelGroup( daoUtil.getString( 2 ) );
            group.setSubject( daoUtil.getString( 3 ) );
            group.setIdQuiz( daoUtil.getInt( 4 ) );
            group.setPositionGroup( daoUtil.getInt( 5 ) );

            groupList.add( group );
        }

        daoUtil.free(  );

        return groupList;
    }

    /**
     * Delete a record from the table
     * @param nIdQuiz The quiz Id
     * @param nGroupId The identifier of the group
     * @param plugin The plugin
     */
    public void delete( int nIdQuiz, int nGroupId, Plugin plugin )
    {
        QuestionGroup groupToDelete = load( nGroupId, plugin );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nGroupId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );

        int nPosition = groupToDelete.getPositionGroup(  );

        List<QuestionGroup> groupList = loadGroupByPosition( nPosition, nIdQuiz, plugin );

        for ( QuestionGroup group : groupList )
        {
            daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

            daoUtil.setInt( 1, group.getIdGroup(  ) );
            daoUtil.setString( 2, group.getLabelGroup(  ) );
            daoUtil.setString( 3, group.getSubject(  ) );
            daoUtil.setInt( 4, group.getIdQuiz(  ) );
            daoUtil.setInt( 5, nPosition );
            daoUtil.setInt( 6, group.getIdGroup(  ) );

            daoUtil.executeUpdate(  );
            daoUtil.free(  );

            nPosition++;
        }
    }

    /**
     * Update the record in the table
     * @param group The reference of the group
     * @param plugin The plugin
     */
    public void store( QuestionGroup group, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, group.getIdGroup(  ) );
        daoUtil.setString( 2, group.getLabelGroup(  ) );
        daoUtil.setString( 3, group.getSubject(  ) );
        daoUtil.setInt( 4, group.getIdQuiz(  ) );
        daoUtil.setInt( 5, group.getPositionGroup(  ) );
        daoUtil.setInt( 6, group.getIdGroup(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of all the groups and returns them as a List
     * @param nIdQuiz The quiz Id
     * @param plugin The plugin
     * @return The List which contains the data of all the groups
     */
    public List<QuestionGroup> selectQuestionGroupsList( int nIdQuiz, Plugin plugin )
    {
        List<QuestionGroup> groupList = new ArrayList<QuestionGroup>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            QuestionGroup group = new QuestionGroup(  );

            group.setIdGroup( daoUtil.getInt( 1 ) );
            group.setLabelGroup( daoUtil.getString( 2 ) );
            group.setSubject( daoUtil.getString( 3 ) );
            group.setIdQuiz( daoUtil.getInt( 4 ) );
            group.setPositionGroup( daoUtil.getInt( 5 ) );

            groupList.add( group );
        }

        daoUtil.free(  );

        return groupList;
    }

    /**
     * Gets a group list
     * @param nIdQuiz The quiz Id
     * @param plugin The plugin
     * @return The list
     */
    public ReferenceList selectQuestionGroupsReferenceList( int nIdQuiz, Plugin plugin )
    {
        ReferenceList groupsReferenceList = new ReferenceList(  );
        ReferenceItem itemInit = new ReferenceItem(  );
        itemInit.setCode( "0" );
        itemInit.setName( " " );
        groupsReferenceList.add( itemInit );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_GROUPS, plugin );
        daoUtil.setInt( 1, nIdQuiz );

        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            ReferenceItem item = new ReferenceItem(  );
            item.setCode( daoUtil.getString( 1 ) );
            item.setName( daoUtil.getString( 2 ) );
            groupsReferenceList.add( item );
        }

        daoUtil.free(  );

        return groupsReferenceList;
    }

    /**
     * Delete a record from the table
     * @param nIdQuiz The identifier of the group
     * @param plugin The plugin
     */
    public void deleteByQuiz( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_QUIZ, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc }
     */
    public void changePositionUp( int nIdQuiz, QuestionGroup group, Plugin plugin )
    {
        int nPositionGroup = group.getPositionGroup(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_UPPER_GROUP, plugin );
        daoUtil.setInt( 1, nPositionGroup - 1 );
        daoUtil.setInt( 2, nIdQuiz );

        daoUtil.executeQuery(  );

        QuestionGroup groupUpper = null;

        if ( daoUtil.next(  ) )
        {
            groupUpper = new QuestionGroup(  );

            groupUpper.setIdGroup( daoUtil.getInt( 1 ) );
            groupUpper.setLabelGroup( daoUtil.getString( 2 ) );
            groupUpper.setSubject( daoUtil.getString( 3 ) );
            groupUpper.setIdQuiz( daoUtil.getInt( 4 ) );
            groupUpper.setPositionGroup( daoUtil.getInt( 5 ) );
        }

        daoUtil.free(  );

        int nNewPosition = groupUpper.getPositionGroup(  );

        groupUpper.setPositionGroup( nPositionGroup );

        daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        daoUtil.setInt( 1, groupUpper.getIdGroup(  ) );
        daoUtil.setString( 2, groupUpper.getLabelGroup(  ) );
        daoUtil.setString( 3, groupUpper.getSubject(  ) );
        daoUtil.setInt( 4, groupUpper.getIdQuiz(  ) );
        daoUtil.setInt( 5, groupUpper.getPositionGroup(  ) );
        daoUtil.setInt( 6, groupUpper.getIdGroup(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );

        group.setPositionGroup( nNewPosition );

        daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, group.getIdGroup(  ) );
        daoUtil.setString( 2, group.getLabelGroup(  ) );
        daoUtil.setString( 3, group.getSubject(  ) );
        daoUtil.setInt( 4, group.getIdQuiz(  ) );
        daoUtil.setInt( 5, group.getPositionGroup(  ) );
        daoUtil.setInt( 6, group.getIdGroup(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc }
     */
    public void changePositionDown( int nIdQuiz, QuestionGroup group, Plugin plugin )
    {
        int nPositionGroup = group.getPositionGroup(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_DOWNER_GROUP, plugin );
        daoUtil.setInt( 1, nPositionGroup + 1 );
        daoUtil.setInt( 2, nIdQuiz );

        daoUtil.executeQuery(  );

        QuestionGroup groupDowner = null;

        if ( daoUtil.next(  ) )
        {
            groupDowner = new QuestionGroup(  );

            groupDowner.setIdGroup( daoUtil.getInt( 1 ) );
            groupDowner.setLabelGroup( daoUtil.getString( 2 ) );
            groupDowner.setSubject( daoUtil.getString( 3 ) );
            groupDowner.setIdQuiz( daoUtil.getInt( 4 ) );
            groupDowner.setPositionGroup( daoUtil.getInt( 5 ) );
        }

        daoUtil.free(  );

        int nNewPosition = groupDowner.getPositionGroup(  );

        groupDowner.setPositionGroup( nPositionGroup );
        daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        daoUtil.setInt( 1, groupDowner.getIdGroup(  ) );
        daoUtil.setString( 2, groupDowner.getLabelGroup(  ) );
        daoUtil.setString( 3, groupDowner.getSubject(  ) );
        daoUtil.setInt( 4, groupDowner.getIdQuiz(  ) );
        daoUtil.setInt( 5, groupDowner.getPositionGroup(  ) );
        daoUtil.setInt( 6, groupDowner.getIdGroup(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );

        group.setPositionGroup( nNewPosition );

        daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, group.getIdGroup(  ) );
        daoUtil.setString( 2, group.getLabelGroup(  ) );
        daoUtil.setString( 3, group.getSubject(  ) );
        daoUtil.setInt( 4, group.getIdQuiz(  ) );
        daoUtil.setInt( 5, nNewPosition );
        daoUtil.setInt( 6, group.getIdGroup(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
