/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import java.sql.Date;

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides Data Access methods for Quiz objects
 */
public class QuizDAO implements IQuizDAO
{
    //Requests
    private static final String SQL_QUERY_NEW_PK = " SELECT max( id_quiz ) FROM quiz_quiz ";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_quiz, label_quiz, introduction, conclusion, status_quiz, activate_captcha, activate_requirement, date_begin_disponibility, date_end_disponibility, date_creation FROM quiz_quiz";
    private static final String SQL_QUERY_DELETE = " DELETE FROM quiz_quiz WHERE id_quiz= ? ";
    private static final String SQL_QUERY_INSERT = " INSERT INTO quiz_quiz ( id_quiz, label_quiz, introduction, conclusion, status_quiz, activate_captcha, activate_requirement, date_begin_disponibility, date_end_disponibility, date_creation, cgu ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_SELECT = " SELECT id_quiz, label_quiz, introduction, conclusion, status_quiz, activate_captcha, activate_requirement, date_begin_disponibility, date_end_disponibility, date_creation, cgu FROM quiz_quiz WHERE id_quiz = ? ";
    private static final String SQL_QUERY_SELECT_LAST_QUIZ = " SELECT id_quiz, label_quiz, introduction, conclusion, activate_captcha, activate_requirement, status_quiz, date_begin_disponibility, date_end_disponibility, date_creation, cgu FROM quiz_quiz WHERE id_quiz = ( SELECT max( id_quiz ) FROM quiz_quiz ) ";
    private static final String SQL_QUERY_UPDATE = " UPDATE quiz_quiz SET label_quiz = ?, introduction = ?, conclusion = ?, status_quiz = ?, activate_captcha = ?, activate_requirement = ?, date_begin_disponibility = ?, date_end_disponibility = ?, cgu = ? WHERE id_quiz = ?  ";
    private static final String SQL_QUERY_SELECTALL_ENABLED = "SELECT id_quiz, label_quiz, introduction, conclusion, status_quiz, cgu FROM quiz_quiz WHERE status_quiz = 1";

    /**
     * Calculate a new primary key to add a new Quiz
     *
     * @param plugin the plugin
     * @return The new key.
     */
    int newPrimaryKey( Plugin plugin )
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
     * Insert a new record in the table.
     *
     * @param quiz The Instance of the object Quiz
     * @param plugin the plugin
     */
    public void insert( Quiz quiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        quiz.setIdQuiz( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, quiz.getIdQuiz(  ) );
        daoUtil.setString( 2, quiz.getName(  ) );
        daoUtil.setString( 3, quiz.getIntroduction(  ) );
        daoUtil.setString( 4, quiz.getConclusion(  ) );
        daoUtil.setBoolean( 5, false );
        daoUtil.setInt( 6, quiz.getActiveCaptcha(  ) );
        daoUtil.setInt( 7, quiz.getActiveRequirement(  ) );
        daoUtil.setDate( 8,
            ( quiz.getDateBeginDisponibility(  ) != null ) ? new Date( quiz.getDateBeginDisponibility(  ).getTime(  ) )
                                                           : null );
        daoUtil.setDate( 9,
            ( quiz.getDateEndDisponibility(  ) != null ) ? new Date( quiz.getDateEndDisponibility(  ).getTime(  ) ) : null );
        daoUtil.setTimestamp( 10, quiz.getDateCreation(  ) );
        daoUtil.setString( 11, quiz.getCgu(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Return A Quiz Collection
     * @param plugin the plugin
     * @return list The Collection of quiz
     */
    public Collection<Quiz> selectQuizList( Plugin plugin )
    {
        Collection<Quiz> quizList = new ArrayList<Quiz>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            quizList.add( load( daoUtil.getInt( 1 ), plugin ) );
        }

        daoUtil.free(  );

        return quizList;
    }

    /**
     * Return A Quiz Collection
     * @param plugin the plugin
     * @return list The Collection of quiz
     */
    public Collection<Quiz> selectQuizEnabledList( Plugin plugin )
    {
        Collection<Quiz> quizEnabledList = new ArrayList<Quiz>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ENABLED, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            quizEnabledList.add( load( daoUtil.getInt( 1 ), plugin ) );
        }

        daoUtil.free(  );

        return quizEnabledList;
    }

    /**
     * Delete a record from the table
     *
     * @param nIdQuiz The indentifier of the object Quiz
     * @param plugin the plugin
     */
    public void delete( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * load the data of Quiz from the table
     *
     * @param nIdQuiz The indentifier of the object Quiz
     * @param plugin the plugin
     * @return The Instance of the object Quiz
     */
    public Quiz load( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeQuery(  );

        Quiz quiz = null;

        if ( daoUtil.next(  ) )
        {
            quiz = new Quiz(  );
            quiz.setIdQuiz( daoUtil.getInt( 1 ) );
            quiz.setName( daoUtil.getString( 2 ) );
            quiz.setIntroduction( daoUtil.getString( 3 ) );
            quiz.setConclusion( daoUtil.getString( 4 ) );
            quiz.setStatus( daoUtil.getInt( 5 ) );
            quiz.setActiveCaptcha( daoUtil.getInt( 6 ) );
            quiz.setActiveRequirement( daoUtil.getInt( 7 ) );
            quiz.setDateBeginDisponibility( daoUtil.getDate( 8 ) );
            quiz.setDateEndDisponibility( daoUtil.getDate( 9 ) );
            quiz.setDateCreation( daoUtil.getTimestamp( 10 ) );
            quiz.setCgu( daoUtil.getString( 11 ) );
        }

        daoUtil.free(  );

        return quiz;
    }

    /**
     * load the data of Quiz from the table
     *
     * @param plugin the plugin
     * @return The Instance of the object Quiz
     */
    public Quiz loadLastQuiz( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_LAST_QUIZ, plugin );
        daoUtil.executeQuery(  );

        Quiz quiz = null;

        if ( daoUtil.next(  ) )
        {
            quiz = new Quiz(  );
            quiz.setIdQuiz( daoUtil.getInt( 1 ) );
            quiz.setName( daoUtil.getString( 2 ) );
            quiz.setIntroduction( daoUtil.getString( 3 ) );
            quiz.setConclusion( daoUtil.getString( 4 ) );
            quiz.setStatus( daoUtil.getInt( 5 ) );
            quiz.setActiveCaptcha( daoUtil.getInt( 6 ) );
            quiz.setActiveRequirement( daoUtil.getInt( 7 ) );
            quiz.setDateBeginDisponibility( daoUtil.getDate( 8 ) );
            quiz.setDateEndDisponibility( daoUtil.getDate( 9 ) );
            quiz.setDateCreation( daoUtil.getTimestamp( 10 ) );
            quiz.setCgu( daoUtil.getString( 11 ) );
        }

        daoUtil.free(  );

        return quiz;
    }

    /**
     * Update the record in the table
     *
     * @param quiz The instance of the Quiz to update
     * @param plugin the plugin
     */
    public void store( Quiz quiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        daoUtil.setString( 1, quiz.getName(  ) );
        daoUtil.setString( 2, quiz.getIntroduction(  ) );
        daoUtil.setString( 3, quiz.getConclusion(  ) );
        daoUtil.setBoolean( 4, quiz.isEnabled(  ) );
        daoUtil.setInt( 5, quiz.getActiveCaptcha(  ) );
        daoUtil.setInt( 6, quiz.getActiveRequirement(  ) );
        daoUtil.setDate( 7,
            ( quiz.getDateBeginDisponibility(  ) != null ) ? new Date( quiz.getDateBeginDisponibility(  ).getTime(  ) )
                                                           : null );
        daoUtil.setDate( 8,
            ( quiz.getDateEndDisponibility(  ) != null ) ? new Date( quiz.getDateEndDisponibility(  ).getTime(  ) ) : null );
        daoUtil.setString( 9, quiz.getCgu(  ) );
        daoUtil.setInt( 10, quiz.getIdQuiz(  ) );

        daoUtil.executeUpdate(  );

        daoUtil.free(  );
    }
}
