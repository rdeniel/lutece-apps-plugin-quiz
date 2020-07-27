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

import java.util.Collection;

/**
 * This class provides instances management methods (create, find, ...) for Quiz objects
 */
public interface IQuizDAO
{
    /**
     * Selection of all Quiz
     *
     * @param plugin
     *            the plugin
     * @return list of quiz
     */
    Collection<Quiz> selectQuizEnabledList( Plugin plugin );

    /**
     * Creation of an instance of an article Quiz
     *
     * @param quiz
     *            An instance of the Quiz which contains the informations to store
     * @param plugin
     *            the plugin
     */
    void insert( Quiz quiz, Plugin plugin );

    /**
     * Updates of the Quiz instance specified in parameter
     *
     * @param quiz
     *            An instance of the Quiz which contains the informations to store
     * @param plugin
     *            the plugin
     */
    void store( Quiz quiz, Plugin plugin );

    /**
     * Deletes the Quiz instance whose identifier is specified in parameter
     *
     * @param nIdQuiz
     *            The identifier of the article Quiz to delete in the database
     * @param plugin
     *            the plugin
     */
    void delete( int nIdQuiz, Plugin plugin );

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of the article Quiz whose identifier is specified in parameter
     *
     * @param nKey
     *            The primary key of the article to find in the database
     * @param plugin
     *            the plugin
     * @return An instance of the Quiz which corresponds to the key
     */
    Quiz load( int nKey, Plugin plugin );

    /**
     * Returns Quiz list
     *
     * @param plugin
     *            the plugin
     * @return the list of the Quiz of the database in form of a Quiz Collection object
     */
    Collection<Quiz> selectQuizList( Plugin plugin );

    /**
     * Returns an instance of the article Quiz whose identifier is specified in parameter
     *
     * @param plugin
     *            the plugin
     * @return An instance of the Quiz which corresponds to the key
     */
    Quiz loadLastQuiz( Plugin plugin );
}
