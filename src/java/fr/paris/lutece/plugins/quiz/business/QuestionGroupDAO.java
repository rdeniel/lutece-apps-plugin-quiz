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
    private static final String SQL_QUERY_SELECT = "SELECT id_group, label_group, subject, id_quiz, pos_group, is_free_html, html_content, id_image, display_score FROM quiz_group WHERE id_group = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO quiz_group ( id_group, label_group, subject, id_quiz, pos_group, is_free_html, html_content, id_image, display_score ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM quiz_group WHERE id_group = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE quiz_group SET label_group = ?, subject = ?, id_quiz = ?, pos_group = ?, is_free_html = ?, html_content = ?, id_image = ?, display_score = ? WHERE id_group = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_group, label_group, subject, id_quiz, pos_group, is_free_html, html_content, id_image, display_score FROM quiz_group WHERE id_quiz = ?";
    private static final String SQL_QUERY_SELECTALL_GROUPS = "SELECT id_group, label_group FROM quiz_group WHERE id_quiz = ? AND is_free_html = 0";
    private static final String SQL_QUERY_DELETE_BY_QUIZ = "DELETE FROM quiz_group WHERE id_quiz = ? ";
    private static final String SQL_QUERY_SELECT_UPPER_GROUP = "SELECT id_group, label_group, subject, id_quiz, pos_group, is_free_html, html_content, id_image, display_score FROM quiz_group WHERE pos_group = ? AND id_quiz = ?";
    private static final String SQL_QUERY_SELECT_DOWNER_GROUP = "SELECT id_group, label_group, subject, id_quiz, pos_group, is_free_html, html_content, id_image, display_score FROM quiz_group WHERE pos_group = ? AND id_quiz = ?";

    private static final String SQL_QUERY_SELECT_BY_ID_QUIZ_AND_POSITION = SQL_QUERY_SELECTALL + " AND pos_group = ?";

    //private static final String SQL_QUERY_UPDATE_BY_POSITION = "UPDATE quiz_group SET id_group = ?, label_group = ?, subject = ?, id_quiz = ?, pos_group = ? WHERE pos_group = ?";
    private static final String SQL_QUERY_SELECT_BY_POSITION = "SELECT id_group, label_group, subject, id_quiz, pos_group, is_free_html, html_content, id_image FROM quiz_group WHERE pos_group > ? AND id_quiz = ?";
    private static final String SQL_QUERY_NEW_POSITION_GROUP = "SELECT max( pos_group ) FROM quiz_group WHERE id_quiz = ?";

    private static final String SQL_FIND_LAST_ID = "SELECT g.id_group FROM quiz_group g WHERE g.pos_group = "
            + "(SELECT MAX(g2.pos_group) FROM quiz_group g2 WHERE g2.id_quiz = ?);";

    private static final String SQL_QUERY_HAS_QUIZ_DISPLAY_SCORE = " SELECT id_group FROM quiz_group WHERE id_quiz = ? AND display_score > 0 AND is_free_html > 0 ";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery( );

        int nKey = 1;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free( );

        return nKey;
    }

    /**
     * The position of the group
     * @param nIdQuiz The quiz Id
     * @param plugin The plugin
     * @return The new position
     */
    private int newPositionGroup( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_POSITION_GROUP, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeQuery( );

        int nKey = 1;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free( );

        return nKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void insert( int nIdQuiz, QuestionGroup group, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        group.setIdGroup( newPrimaryKey( plugin ) );
        group.setPositionGroup( newPositionGroup( nIdQuiz, plugin ) );

        daoUtil.setInt( 1, group.getIdGroup( ) );
        daoUtil.setString( 2, group.getLabelGroup( ) );
        daoUtil.setString( 3, group.getSubject( ) );
        daoUtil.setInt( 4, group.getIdQuiz( ) );
        daoUtil.setInt( 5, group.getPositionGroup( ) );
        daoUtil.setBoolean( 6, group.getIsFreeHtml( ) );
        daoUtil.setString( 7, group.getHtmlContent( ) );
        daoUtil.setInt( 8, group.getIdImage( ) );
        daoUtil.setBoolean( 9, group.getDisplayScore( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestionGroup load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery( );

        QuestionGroup group = null;

        if ( daoUtil.next( ) )
        {
            group = new QuestionGroup( );

            group.setIdGroup( daoUtil.getInt( 1 ) );
            group.setLabelGroup( daoUtil.getString( 2 ) );
            group.setSubject( daoUtil.getString( 3 ) );
            group.setIdQuiz( daoUtil.getInt( 4 ) );
            group.setPositionGroup( daoUtil.getInt( 5 ) );
            group.setIsFreeHtml( daoUtil.getBoolean( 6 ) );
            group.setHtmlContent( daoUtil.getString( 7 ) );
            group.setIdImage( daoUtil.getInt( 8 ) );
            group.setDisplayScore( daoUtil.getBoolean( 9 ) );
        }

        daoUtil.free( );

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
        List<QuestionGroup> groupList = new ArrayList<QuestionGroup>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_POSITION, plugin );
        daoUtil.setInt( 1, nPosition );
        daoUtil.setInt( 2, nIdQuiz );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            QuestionGroup group = new QuestionGroup( );

            group.setIdGroup( daoUtil.getInt( 1 ) );
            group.setLabelGroup( daoUtil.getString( 2 ) );
            group.setSubject( daoUtil.getString( 3 ) );
            group.setIdQuiz( daoUtil.getInt( 4 ) );
            group.setPositionGroup( daoUtil.getInt( 5 ) );
            group.setIsFreeHtml( daoUtil.getBoolean( 6 ) );
            group.setHtmlContent( daoUtil.getString( 7 ) );
            group.setIdImage( daoUtil.getInt( 8 ) );
            group.setDisplayScore( daoUtil.getBoolean( 9 ) );

            groupList.add( group );
        }

        daoUtil.free( );

        return groupList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdQuiz, int nGroupId, Plugin plugin )
    {
        QuestionGroup groupToDelete = load( nGroupId, plugin );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nGroupId );

        daoUtil.executeUpdate( );
        daoUtil.free( );

        int nPosition = groupToDelete.getPositionGroup( );

        List<QuestionGroup> groupList = loadGroupByPosition( nPosition, nIdQuiz, plugin );

        for ( QuestionGroup group : groupList )
        {
            group.setPositionGroup( nPosition );
            store( group, plugin );
            nPosition++;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( QuestionGroup group, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setString( 1, group.getLabelGroup( ) );
        daoUtil.setString( 2, group.getSubject( ) );
        daoUtil.setInt( 3, group.getIdQuiz( ) );
        daoUtil.setInt( 4, group.getPositionGroup( ) );
        daoUtil.setBoolean( 5, group.getIsFreeHtml( ) );
        daoUtil.setString( 6, group.getHtmlContent( ) );
        daoUtil.setInt( 7, group.getIdImage( ) );
        daoUtil.setBoolean( 8, group.getDisplayScore( ) );
        daoUtil.setInt( 9, group.getIdGroup( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QuestionGroup> selectQuestionGroupsList( int nIdQuiz, Plugin plugin )
    {
        List<QuestionGroup> groupList = new ArrayList<QuestionGroup>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            QuestionGroup group = new QuestionGroup( );

            group.setIdGroup( daoUtil.getInt( 1 ) );
            group.setLabelGroup( daoUtil.getString( 2 ) );
            group.setSubject( daoUtil.getString( 3 ) );
            group.setIdQuiz( daoUtil.getInt( 4 ) );
            group.setPositionGroup( daoUtil.getInt( 5 ) );
            group.setIsFreeHtml( daoUtil.getBoolean( 6 ) );
            group.setHtmlContent( daoUtil.getString( 7 ) );
            group.setIdImage( daoUtil.getInt( 8 ) );
            group.setDisplayScore( daoUtil.getBoolean( 9 ) );

            groupList.add( group );
        }

        daoUtil.free( );

        return groupList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferenceList selectQuestionGroupsReferenceList( int nIdQuiz, Plugin plugin )
    {
        ReferenceList groupsReferenceList = new ReferenceList( );
        ReferenceItem itemInit = new ReferenceItem( );
        itemInit.setCode( "0" );
        itemInit.setName( " " );
        groupsReferenceList.add( itemInit );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_GROUPS, plugin );
        daoUtil.setInt( 1, nIdQuiz );

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            ReferenceItem item = new ReferenceItem( );
            item.setCode( daoUtil.getString( 1 ) );
            item.setName( daoUtil.getString( 2 ) );
            groupsReferenceList.add( item );
        }

        daoUtil.free( );

        return groupsReferenceList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestionGroup selectQuestionGroupByPosition( int nIdQuiz, int nPosition, Plugin plugin )
    {
        QuestionGroup group = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_QUIZ_AND_POSITION, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.setInt( 2, nPosition );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            group = new QuestionGroup( );

            group.setIdGroup( daoUtil.getInt( 1 ) );
            group.setLabelGroup( daoUtil.getString( 2 ) );
            group.setSubject( daoUtil.getString( 3 ) );
            group.setIdQuiz( daoUtil.getInt( 4 ) );
            group.setPositionGroup( daoUtil.getInt( 5 ) );
            group.setIsFreeHtml( daoUtil.getBoolean( 6 ) );
            group.setHtmlContent( daoUtil.getString( 7 ) );
            group.setIdImage( daoUtil.getInt( 8 ) );
            group.setDisplayScore( daoUtil.getBoolean( 9 ) );
        }

        daoUtil.free( );

        return group;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByQuiz( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_QUIZ, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void changePositionUp( int nIdQuiz, QuestionGroup group, Plugin plugin )
    {
        int nPositionGroup = group.getPositionGroup( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_UPPER_GROUP, plugin );
        // Putting a group upper in the table means lowering his position
        daoUtil.setInt( 1, nPositionGroup - 1 );
        daoUtil.setInt( 2, nIdQuiz );

        daoUtil.executeQuery( );

        QuestionGroup groupUpper = null;

        if ( daoUtil.next( ) )
        {
            groupUpper = new QuestionGroup( );

            groupUpper.setIdGroup( daoUtil.getInt( 1 ) );
            groupUpper.setLabelGroup( daoUtil.getString( 2 ) );
            groupUpper.setSubject( daoUtil.getString( 3 ) );
            groupUpper.setIdQuiz( daoUtil.getInt( 4 ) );
            groupUpper.setPositionGroup( daoUtil.getInt( 5 ) );
            groupUpper.setIsFreeHtml( daoUtil.getBoolean( 6 ) );
            groupUpper.setHtmlContent( daoUtil.getString( 7 ) );
            groupUpper.setIdImage( daoUtil.getInt( 8 ) );
            groupUpper.setDisplayScore( daoUtil.getBoolean( 9 ) );
        }

        daoUtil.free( );
        if ( groupUpper != null )
        {
            int nNewPosition = groupUpper.getPositionGroup( );

            groupUpper.setPositionGroup( nPositionGroup );
            store( groupUpper, plugin );

            group.setPositionGroup( nNewPosition );
            store( group, plugin );
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void changePositionDown( int nIdQuiz, QuestionGroup group, Plugin plugin )
    {
        int nPositionGroup = group.getPositionGroup( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_DOWNER_GROUP, plugin );
        // Putting a group downer in the table means increasing his position
        daoUtil.setInt( 1, nPositionGroup + 1 );
        daoUtil.setInt( 2, nIdQuiz );

        daoUtil.executeQuery( );

        QuestionGroup groupDowner = null;

        if ( daoUtil.next( ) )
        {
            groupDowner = new QuestionGroup( );

            groupDowner.setIdGroup( daoUtil.getInt( 1 ) );
            groupDowner.setLabelGroup( daoUtil.getString( 2 ) );
            groupDowner.setSubject( daoUtil.getString( 3 ) );
            groupDowner.setIdQuiz( daoUtil.getInt( 4 ) );
            groupDowner.setPositionGroup( daoUtil.getInt( 5 ) );
            groupDowner.setIsFreeHtml( daoUtil.getBoolean( 6 ) );
            groupDowner.setHtmlContent( daoUtil.getString( 7 ) );
            groupDowner.setIdImage( daoUtil.getInt( 8 ) );
            groupDowner.setDisplayScore( daoUtil.getBoolean( 9 ) );
        }

        daoUtil.free( );
        if ( groupDowner != null )
        {
            int nNewPosition = groupDowner.getPositionGroup( );

            groupDowner.setPositionGroup( nPositionGroup );
            store( groupDowner, plugin );

            group.setPositionGroup( nNewPosition );
            store( group, plugin );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int findLastByQuiz( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_FIND_LAST_ID, plugin );
        daoUtil.setInt( 1, nIdQuiz );

        daoUtil.executeQuery( );

        int nKey = 0;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 );
        }

        daoUtil.free( );

        return nKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGroupDisplayScore( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_HAS_QUIZ_DISPLAY_SCORE, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeQuery( );
        boolean bResult = false;
        if ( daoUtil.next( ) )
        {
            bResult = daoUtil.getInt( 1 ) > 0;
        }
        daoUtil.free( );
        return bResult;
    }
}
