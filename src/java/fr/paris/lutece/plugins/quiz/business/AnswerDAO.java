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
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for Answer objects
 */
public final class AnswerDAO implements IAnswerDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_answer ) FROM quiz_answer";
    private static final String SQL_QUERY_SELECT = "SELECT id_answer, id_question, label_answer, is_valid, id_profil FROM quiz_answer WHERE id_answer = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO quiz_answer ( id_answer, id_question, label_answer, is_valid, id_profil ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM quiz_answer WHERE id_answer = ? ";
    private static final String SQL_QUERY_DELETE_ANSWERS_BY_QUESTION = "DELETE FROM quiz_answer WHERE id_question = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE quiz_answer SET id_answer = ?, id_question = ?, label_answer = ?, is_valid = ?, id_profil = ? WHERE id_answer = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_answer, id_question, label_answer, is_valid, id_profil FROM quiz_answer WHERE id_question = ? ORDER BY id_answer";
    private static final String SQL_QUERY_SELECT_BY_PROFIL = "SELECT id_answer FROM quiz_answer WHERE id_profil = ?";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery( );

        int nKey;

        if ( !daoUtil.next( ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free( );

        return nKey;
    }

    /**
     * Insert a new record in the table.
     * @param nIdQuestion Question ID
     * @param answer instance of the Answer object to insert
     * @param plugin The plugin
     */
    public void insert( int nIdQuestion, Answer answer, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        answer.setIdAnswer( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, answer.getIdAnswer( ) );
        daoUtil.setInt( 2, answer.getIdQuestion( ) );
        daoUtil.setString( 3, answer.getLabelAnswer( ) );
        daoUtil.setInt( 4, answer.getValid( ) );
        daoUtil.setInt( 5, answer.getIdProfil( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Load the data of the answer from the table
     * @param nId The identifier of the answer
     * @param plugin The plugin
     * @return the instance of the Answer
     */
    public Answer load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery( );

        Answer answer = null;

        if ( daoUtil.next( ) )
        {
            answer = new Answer( );

            answer.setIdAnswer( daoUtil.getInt( 1 ) );
            answer.setIdQuestion( daoUtil.getInt( 2 ) );
            answer.setLabelAnswer( daoUtil.getString( 3 ) );
            answer.setValid( daoUtil.getInt( 4 ) );
            answer.setIdProfil( daoUtil.getInt( 5 ) );
        }

        daoUtil.free( );

        return answer;
    }

    /**
     * Delete a record from the table
     * @param nAnswerId The identifier of the answer
     * @param plugin The plugin
     */
    public void delete( int nAnswerId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nAnswerId );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Update the record in the table
     * @param answer The reference of the answer
     * @param plugin The plugin
     */
    public void store( Answer answer, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, answer.getIdAnswer( ) );
        daoUtil.setInt( 2, answer.getIdQuestion( ) );
        daoUtil.setString( 3, answer.getLabelAnswer( ) );
        daoUtil.setInt( 4, answer.getValid( ) );
        daoUtil.setInt( 5, answer.getIdProfil( ) );
        daoUtil.setInt( 6, answer.getIdAnswer( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Load the data of all the answers and returns them as a List
     * @param nIdQuestion the id of the question
     * @param plugin The plugin
     * @return The List which contains the data of all the answers
     */
    public List<Answer> selectAnswersList( int nIdQuestion, Plugin plugin )
    {
        List<Answer> answerList = new ArrayList<Answer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.setInt( 1, nIdQuestion );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            Answer answer = new Answer( );

            answer.setIdAnswer( daoUtil.getInt( 1 ) );
            answer.setIdQuestion( daoUtil.getInt( 2 ) );
            answer.setLabelAnswer( daoUtil.getString( 3 ) );
            answer.setValid( daoUtil.getInt( 4 ) );
            answer.setIdProfil( daoUtil.getInt( 5 ) );

            answerList.add( answer );
        }

        daoUtil.free( );

        return answerList;
    }

    /**
     * Delete answers for a given question
     * @param nIdQuestion The question Id
     * @param plugin The plugin
     */
    public void deleteAnswersByQuestion( int nIdQuestion, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_ANSWERS_BY_QUESTION, plugin );
        daoUtil.setInt( 1, nIdQuestion );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Load the data of the answer from the table
     * @param nIdProfil The identifier of the profil
     * @param plugin The plugin
     * @return <code>true</code> if there is at least one answer with profil,
     *         <code>false</code> otherwise
     */
    public boolean isAnswersWithProfil( int nIdProfil, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_PROFIL, plugin );
        daoUtil.setInt( 1, nIdProfil );
        daoUtil.executeQuery( );

        boolean result = false;

        if ( daoUtil.next( ) )
        {
            result = true;
        }

        daoUtil.free( );

        return result;
    }
}
