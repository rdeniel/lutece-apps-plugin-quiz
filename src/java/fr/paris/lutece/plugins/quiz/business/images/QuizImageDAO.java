/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.quiz.business.images;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * DAO for images
 */
public class QuizImageDAO implements IQuizImageDAO
{
    private static final String SQL_QUERY_NEX_PRIMARY_KEY = "SELECT MAX(id_image) FROM quiz_image";
    private static final String SQL_QUERY_FIND_QUIZ_IMAGE = "SELECT image_content, content_type FROM quiz_image WHERE id_image = ?";
    private static final String SQL_QUERY_INSERT_QUIZ_IMAGE = "INSERT INTO quiz_image(id_image, image_content, content_type) VALUES (?,?,?)";
    private static final String SQL_QUERY_UPDATE_QUIZ_IMAGE = "UPDATE quiz_image SET image_content = ?, content_type = ? WHERE id_image = ?";
    private static final String SQL_QUERY_REMOVE_QUIZ_IMAGE = "DELETE FROM quiz_image WHERE id_image = ?";

    /**
     * Get a new primary key
     * @param plugin The plugin
     * @return the new primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEX_PRIMARY_KEY, plugin );
        daoUtil.executeQuery( );
        int nId = 1;
        if ( daoUtil.next( ) )
        {
            nId = daoUtil.getInt( 1 ) + 1;
        }
        daoUtil.free( );
        return nId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizImage findQuizImage( int nIdImage, Plugin plugin )
    {
        QuizImage quizImage = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_QUIZ_IMAGE, plugin );
        daoUtil.setInt( 1, nIdImage );
        daoUtil.executeQuery( );
        if ( daoUtil.next( ) )
        {
            quizImage = new QuizImage( nIdImage, daoUtil.getBytes( 1 ), daoUtil.getString( 2 ) );
        }
        daoUtil.free( );
        return quizImage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void insertQuizImage( QuizImage quizImage, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_QUIZ_IMAGE, plugin );
        quizImage.setIdImage( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, quizImage.getIdImage( ) );
        daoUtil.setBytes( 2, quizImage.getContent( ) );
        daoUtil.setString( 3, quizImage.getContentType( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQuizImage( QuizImage quizImage, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_QUIZ_IMAGE, plugin );
        daoUtil.setBytes( 1, quizImage.getContent( ) );
        daoUtil.setString( 2, quizImage.getContentType( ) );
        daoUtil.setInt( 3, quizImage.getIdImage( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeQuizImage( int nIdImage, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_REMOVE_QUIZ_IMAGE, plugin );
        daoUtil.setInt( 1, nIdImage );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

}
