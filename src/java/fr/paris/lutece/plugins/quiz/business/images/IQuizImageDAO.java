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


/**
 * Interface of DAO for question images
 */
public interface IQuizImageDAO
{
    /**
     * Get the image
     * @param nIdImage The id of the image
     * @param plugin The plugin
     * @return The byte array of the image, or null if no image was found
     */
    QuizImage findQuizImage( int nIdImage, Plugin plugin );

    /**
     * Insert an image into the database
     * @param quizImage The image
     * @param plugin The plugin
     */
    void insertQuizImage( QuizImage quizImage, Plugin plugin );

    /**
     * Update an image of a question
     * @param quizImage The image
     * @param plugin The plugin
     */
    void updateQuizImage( QuizImage quizImage, Plugin plugin );

    /**
     * Remove an image associated with a question
     * @param nIdImage The id of the question to remove
     * @param plugin The plugin
     */
    void removeQuizImage( int nIdImage, Plugin plugin );
}
