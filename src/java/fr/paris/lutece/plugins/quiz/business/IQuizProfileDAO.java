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
import fr.paris.lutece.util.ReferenceList;

import java.util.Collection;

/**
 * Interface of quiz profile DAO
 */
public interface IQuizProfileDAO
{
    /**
     * Creation of an instance of an article Quiz profile
     * 
     * @param profil
     *            An instance of the Quiz profile which contains the informations to store
     * @param plugin
     *            the plugin
     */
    void insert( QuizProfile profil, Plugin plugin );

    /**
     * Select the list of profile
     * 
     * @param nIdQuiz
     *            The quiz ID
     * @param plugin
     *            plugin Quiz
     * @return profils list
     */
    ReferenceList selectQuizProfilsReferenceList( int nIdQuiz, Plugin plugin );

    /**
     * Return the name of a profile
     * 
     * @param nIdProfil
     *            The profile ID
     * @param plugin
     *            plugin Quiz
     * @return name of the corresponding profile
     */
    String getName( int nIdProfil, Plugin plugin );

    /**
     * Load a profile with its id
     * 
     * @param nKey
     *            the id to load
     * @param plugin
     *            plugin Quiz
     * @return corresponding profile
     */
    QuizProfile load( int nKey, Plugin plugin );

    /**
     * Find all profiles for a quiz
     * 
     * @param nIdQuiz
     *            the id quiz
     * @param plugin
     *            plugin Quiz
     * @return profile list
     */
    Collection<QuizProfile> findAll( int nIdQuiz, Plugin plugin );

    /**
     * Delete a profile
     * 
     * @param nIdProfil
     *            the profile id
     * @param plugin
     *            plugin Quiz
     */
    void delete( int nIdProfil, Plugin plugin );

    /**
     * Updates of the {@link QuizProfile} instance specified in parameter
     * 
     * @param quizProfil
     *            An instance of the {@link QuizProfile} which contains the informations to store
     * @param plugin
     *            the plugin
     */
    void store( QuizProfile quizProfil, Plugin plugin );

    /**
     * Delete profiles linked to a quiz
     * 
     * @param nIdQuiz
     *            the quiz id
     * @param plugin
     *            plugin Quiz
     */
    void deleteByQuiz( int nIdQuiz, Plugin plugin );
}
