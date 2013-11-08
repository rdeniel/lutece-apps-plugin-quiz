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
import java.util.Collection;


/**
 * This class provides Data Access methods for QuizQuestion objects
 */
public final class QuizQuestionDAO implements IQuizQuestionDAO
{
    private static final String SQL_QUERY_NEW_PK = " SELECT max( id_question ) FROM quiz_question ";
    private static final String SQL_QUERY_INSERT_QUESTION = "INSERT INTO quiz_question ( id_question, label_question, id_quiz, id_group, explaination, id_image ) VALUES ( ?, ?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_SELECT_QUESTION = "SELECT id_question, label_question, id_quiz, id_group, explaination, id_image FROM quiz_question WHERE id_question = ?";
    private static final String SQL_QUERY_SELECT_LAST_QUESTION = "SELECT id_question, label_question, id_quiz, id_group, explaination, id_image FROM quiz_question WHERE id_question = ( select max(id_question) from quiz_question )";
    private static final String SQL_QUERY_SELECT_QUESTIONS = " SELECT id_question, label_question, id_quiz, id_group, explaination, id_image FROM quiz_question WHERE id_quiz = ?";
    private static final String SQL_QUERY_DELETE_QUESTION = "DELETE FROM quiz_question WHERE id_question = ?";
    private static final String SQL_QUERY_DELETE_QUESTIONS_BY_QUIZ = "DELETE FROM quiz_question WHERE id_quiz = ?";
    private static final String SQL_QUERY_UPDATE_QUESTION = " UPDATE quiz_question SET label_question = ?, id_quiz = ?, id_group = ?, explaination = ?, id_image = ? WHERE id_question = ?";
    private static final String SQL_QUERY_VERIFY_QUESTIONS_BY_GROUP = "SELECT id_question FROM quiz_question WHERE id_quiz = ? AND id_group = ?";
    private static final String SQL_QUERY_DELETE_QUESTIONS_BY_GROUP = "DELETE FROM quiz_question WHERE id_quiz = ? AND id_group = ?";
    private static final String SQL_QUERY_SELECT_QUESTIONS_BY_GROUP = "SELECT id_question, label_question, id_quiz, id_group, explaination, id_image FROM quiz_question WHERE id_group = ?";
    private static final String SQL_QUERY_SELECT_QUESTIONS_BY_ANSWER = "SELECT DISTINCT quest.id_question, quest.label_question, quest.id_quiz, quest.id_group, quest.explaination, quest.id_image, ans.id_question FROM quiz_question as quest, quiz_answer as ans WHERE quest.id_question = ans.id_question AND id_quiz = ?";
    private static final String SQL_QUERY_SELECT_QUESTIONS_BY_ANSWER_AND_GROUP = SQL_QUERY_SELECT_QUESTIONS_BY_ANSWER
            + " AND quest.id_group = ? ";

    /**
     * Find the new primary key in the table.
     * 
     * @param plugin the plugin
     * @return the new primary key
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
     * {@inheritDoc}
     */
    @Override
    public synchronized void insert( QuizQuestion quizQuestion, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_QUESTION, plugin );
        quizQuestion.setIdQuestion( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, quizQuestion.getIdQuestion( ) );
        daoUtil.setString( 2, quizQuestion.getQuestionLabel( ) );
        daoUtil.setInt( 3, quizQuestion.getIdQuiz( ) );
        daoUtil.setInt( 4, quizQuestion.getIdGroup( ) );
        daoUtil.setString( 5, quizQuestion.getExplaination( ) );
        daoUtil.setInt( 6, quizQuestion.getIdImage( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdQuestion, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_QUESTION, plugin );
        daoUtil.setInt( 1, nIdQuestion );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteQuestionsByQuiz( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_QUESTIONS_BY_QUIZ, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestion load( int nIdQuizQuestion, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_QUESTION, plugin );
        daoUtil.setInt( 1, nIdQuizQuestion );
        daoUtil.executeQuery( );

        QuizQuestion quizQuestion = null;

        if ( daoUtil.next( ) )
        {
            quizQuestion = new QuizQuestion( );
            quizQuestion.setIdQuestion( daoUtil.getInt( 1 ) );
            quizQuestion.setQuestionLabel( daoUtil.getString( 2 ) );
            quizQuestion.setIdQuiz( daoUtil.getInt( 3 ) );
            quizQuestion.setIdGroup( daoUtil.getInt( 4 ) );
            quizQuestion.setExplaination( daoUtil.getString( 5 ) );
            quizQuestion.setIdImage( daoUtil.getInt( 6 ) );
        }

        daoUtil.free( );

        return quizQuestion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestion loadLastQuestion( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_LAST_QUESTION, plugin );
        daoUtil.executeQuery( );

        QuizQuestion quizQuestion = null;

        if ( daoUtil.next( ) )
        {
            quizQuestion = new QuizQuestion( );
            quizQuestion.setIdQuestion( daoUtil.getInt( 1 ) );
            quizQuestion.setQuestionLabel( daoUtil.getString( 2 ) );
            quizQuestion.setIdQuiz( daoUtil.getInt( 3 ) );
            quizQuestion.setIdGroup( daoUtil.getInt( 4 ) );
            quizQuestion.setExplaination( daoUtil.getString( 5 ) );
            quizQuestion.setIdImage( daoUtil.getInt( 6 ) );
        }

        daoUtil.free( );

        return quizQuestion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( QuizQuestion quizQuestion, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_QUESTION, plugin );
        daoUtil.setString( 1, quizQuestion.getQuestionLabel( ) );
        daoUtil.setInt( 2, quizQuestion.getIdQuiz( ) );
        daoUtil.setInt( 3, quizQuestion.getIdGroup( ) );
        daoUtil.setString( 4, quizQuestion.getExplaination( ) );
        daoUtil.setInt( 5, quizQuestion.getIdImage( ) );
        daoUtil.setInt( 6, quizQuestion.getIdQuestion( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<QuizQuestion> selectQuestionsList( int nIdQuiz, Plugin plugin )
    {
        Collection<QuizQuestion> questionList = new ArrayList<QuizQuestion>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_QUESTIONS, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            QuizQuestion quizQuestion = new QuizQuestion( );
            quizQuestion.setIdQuestion( daoUtil.getInt( 1 ) );
            quizQuestion.setQuestionLabel( daoUtil.getString( 2 ) );
            quizQuestion.setIdQuiz( daoUtil.getInt( 3 ) );
            quizQuestion.setIdGroup( daoUtil.getInt( 4 ) );
            quizQuestion.setExplaination( daoUtil.getString( 5 ) );
            quizQuestion.setIdImage( daoUtil.getInt( 6 ) );

            questionList.add( quizQuestion );
        }

        daoUtil.free( );

        return questionList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Integer> getVerifyQuestionsByGroup( int nIdQuiz, int nIdGroup, Plugin plugin )
    {
        ArrayList<Integer> idQuestionsList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_VERIFY_QUESTIONS_BY_GROUP, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.setInt( 2, nIdGroup );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            int nIdQuestion = daoUtil.getInt( 1 );
            idQuestionsList.add( nIdQuestion );
        }

        daoUtil.free( );

        return idQuestionsList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<QuizQuestion> selectQuestionsByGroup( int nIdQuiz, int nIdGroup, Plugin plugin )
    {
        Collection<QuizQuestion> questionList = new ArrayList<QuizQuestion>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_QUESTIONS_BY_GROUP, plugin );
        daoUtil.setInt( 1, nIdGroup );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            QuizQuestion quizQuestion = new QuizQuestion( );
            quizQuestion.setIdQuestion( daoUtil.getInt( 1 ) );
            quizQuestion.setQuestionLabel( daoUtil.getString( 2 ) );
            quizQuestion.setIdQuiz( daoUtil.getInt( 3 ) );
            quizQuestion.setIdGroup( daoUtil.getInt( 4 ) );
            quizQuestion.setExplaination( daoUtil.getString( 5 ) );
            quizQuestion.setIdImage( daoUtil.getInt( 6 ) );

            questionList.add( quizQuestion );
        }

        daoUtil.free( );

        return questionList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteQuestionsByGroup( int nIdQuiz, int nIdGroup, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_QUESTIONS_BY_GROUP, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.setInt( 2, nIdGroup );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<QuizQuestion> selectQuestionsListWithAnswer( int nIdQuiz, Plugin plugin )
    {
        Collection<QuizQuestion> questionList = new ArrayList<QuizQuestion>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_QUESTIONS_BY_ANSWER, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            QuizQuestion quizQuestion = new QuizQuestion( );
            quizQuestion.setIdQuestion( daoUtil.getInt( 1 ) );
            quizQuestion.setQuestionLabel( daoUtil.getString( 2 ) );
            quizQuestion.setIdQuiz( daoUtil.getInt( 3 ) );
            quizQuestion.setIdGroup( daoUtil.getInt( 4 ) );
            quizQuestion.setExplaination( daoUtil.getString( 5 ) );
            quizQuestion.setIdImage( daoUtil.getInt( 6 ) );

            questionList.add( quizQuestion );
        }

        daoUtil.free( );

        return questionList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<QuizQuestion> selectQuestionsListWithAnswerByIdGroup( int nIdQuiz, int nIdGroup, Plugin plugin )
    {
        Collection<QuizQuestion> questionList = new ArrayList<QuizQuestion>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_QUESTIONS_BY_ANSWER_AND_GROUP, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.setInt( 2, nIdGroup );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            QuizQuestion quizQuestion = new QuizQuestion( );
            quizQuestion.setIdQuestion( daoUtil.getInt( 1 ) );
            quizQuestion.setQuestionLabel( daoUtil.getString( 2 ) );
            quizQuestion.setIdQuiz( daoUtil.getInt( 3 ) );
            quizQuestion.setIdGroup( daoUtil.getInt( 4 ) );
            quizQuestion.setExplaination( daoUtil.getString( 5 ) );
            quizQuestion.setIdImage( daoUtil.getInt( 6 ) );

            questionList.add( quizQuestion );
        }

        daoUtil.free( );

        return questionList;
    }
}
