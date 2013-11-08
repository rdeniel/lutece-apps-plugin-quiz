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


/**
 * DAO for images of questions
 */
public class QuizQuestionImageDAO implements IQuizQuestionImageDAO
{
    private static final String SQL_QUERY_FIND_QUESTION_IMAGE = "SELECT image_content, content_type FROM quiz_question_image WHERE id_question = ?";
    private static final String SQL_QUERY_INSERT_QUESTION_IMAGE = "INSERT INTO quiz_question_image(id_question, image_content, content_type) VALUES (?,?,?)";
    private static final String SQL_QUERY_UPDATE_QUESTION_IMAGE = "UPDATE quiz_question_image SET image_content = ?, content_type = ? WHERE id_question = ?";
    private static final String SQL_QUERY_REMOVE_QUESTION_IMAGE = "DELETE FROM quiz_question_image WHERE id_question = ?";
    private static final String SQL_QUERY_SELECT_QUESTION_ID = "SELECT id_question FROM quiz_question_image WHERE id_question = ?";

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestionImage findQuestionImage( int nIdQuestion, Plugin plugin )
    {
        QuizQuestionImage questionImage = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_QUESTION_IMAGE, plugin );
        daoUtil.setInt( 1, nIdQuestion );
        daoUtil.executeQuery( );
        if ( daoUtil.next( ) )
        {
            questionImage = new QuizQuestionImage( nIdQuestion, daoUtil.getBytes( 1 ), daoUtil.getString( 2 ) );
        }
        daoUtil.free( );
        return questionImage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertQuestionImage( QuizQuestionImage questionImage, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_QUESTION_IMAGE, plugin );
        daoUtil.setInt( 1, questionImage.getIdQuestion( ) );
        daoUtil.setBytes( 2, questionImage.getContent( ) );
        daoUtil.setString( 3, questionImage.getContentType( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQuestionImage( QuizQuestionImage questionImage, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_QUESTION_IMAGE, plugin );
        daoUtil.setBytes( 1, questionImage.getContent( ) );
        daoUtil.setString( 2, questionImage.getContentType( ) );
        daoUtil.setInt( 3, questionImage.getIdQuestion( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeQuestionImage( int nIdQuestion, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_REMOVE_QUESTION_IMAGE, plugin );
        daoUtil.setInt( 1, nIdQuestion );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doesQuestionHasImage( int nQuestionId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_QUESTION_ID, plugin );
        daoUtil.setInt( 1, nQuestionId );
        boolean bResult;
        daoUtil.executeQuery( );
        if ( daoUtil.next( ) )
        {
            bResult = true;
        }
        else
        {
            bResult = false;
        }
        daoUtil.free( );
        return bResult;
    }
}
