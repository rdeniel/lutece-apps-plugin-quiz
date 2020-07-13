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
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.Collection;

/**
 * Home for profiles
 */
public final class QuizProfileHome
{
    private static IQuizProfileDAO _dao = SpringContextService.getBean( "quiz.quizProfileDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private QuizProfileHome( )
    {
    }

    /**
     * Creation of an instance of an article Quiz profile
     * 
     * @param profile
     *            An instance of the Quiz profile which contains the informations to store
     * @param plugin
     *            the plugin
     * @return The instance of the Quiz profile which has been created
     */
    public static QuizProfile create( QuizProfile profile, Plugin plugin )
    {
        _dao.insert( profile, plugin );

        return profile;
    }

    /**
     * Select the list of profile
     * 
     * @param nIdQuiz
     *            The quiz ID
     * @param plugin
     *            plugin Quiz
     * @return profiles list
     */
    public static ReferenceList selectQuizProfilsReferenceList( int nIdQuiz, Plugin plugin )
    {
        return _dao.selectQuizProfilsReferenceList( nIdQuiz, plugin );
    }

    /**
     * Load a profile with its id
     * 
     * @param nKey
     *            the id to load
     * @param plugin
     *            plugin Quiz
     * @return corresponding profile
     */
    public static QuizProfile findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Return the name of a profile
     * 
     * @param nIdProfile
     *            The profile ID
     * @param plugin
     *            plugin Quiz
     * @return name of the corresponding profile
     */
    public static String getName( int nIdProfile, Plugin plugin )
    {
        return _dao.getName( nIdProfile, plugin );
    }

    /**
     * Find all profiles for a quiz
     * 
     * @param nIdQuiz
     *            the id quiz
     * @param plugin
     *            plugin Quiz
     * @return profiles list
     */
    public static Collection<QuizProfile> findAll( int nIdQuiz, Plugin plugin )
    {
        return _dao.findAll( nIdQuiz, plugin );
    }

    /**
     * Remove a profile
     * 
     * @param nIdProfile
     *            The id of the profile to remove
     * @param plugin
     *            The plugin
     */
    public static void remove( int nIdProfile, Plugin plugin )
    {
        _dao.delete( nIdProfile, plugin );
    }

    /**
     * Update a profile
     * 
     * @param quizProfile
     *            The profile to update
     * @param plugin
     *            The plugin
     */
    public static void update( QuizProfile quizProfile, Plugin plugin )
    {
        _dao.store( quizProfile, plugin );
    }

    /**
     * Remove every profiles associated with a given quiz
     * 
     * @param nIdQuiz
     *            The id of the quiz
     * @param plugin
     *            The plugin
     */
    public static void removeProfilesByQuiz( int nIdQuiz, Plugin plugin )
    {
        _dao.deleteByQuiz( nIdQuiz, plugin );
    }

}
