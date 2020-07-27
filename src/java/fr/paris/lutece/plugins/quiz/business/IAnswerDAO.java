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

import java.util.List;

/**
 * IAnswerDAO Interface
 */
public interface IAnswerDAO
{
    /**
     * Insert a new record in the table.
     * 
     * @param nIdQuestion
     *            The question Id
     * @param answer
     *            instance of the Answer object to inssert
     * @param plugin
     *            the Plugin
     */
    void insert( int nIdQuestion, Answer answer, Plugin plugin );

    /**
     * Update the record in the table
     * 
     * @param answer
     *            the reference of the Answer
     * @param plugin
     *            the Plugin
     */
    void store( Answer answer, Plugin plugin );

    /**
     * Delete a record from the table
     * 
     * @param nIdAnswer
     *            int identifier of the Answer to delete
     * @param plugin
     *            the Plugin
     */
    void delete( int nIdAnswer, Plugin plugin );

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the data from the table
     * 
     * @param nKey
     *            The identifier of the answer
     * @param plugin
     *            the Plugin
     * @return The instance of the answer
     */
    Answer load( int nKey, Plugin plugin );

    /**
     * Load the data of all the answer objects and returns them as a List
     * 
     * @param plugin
     *            the Plugin
     * @param nIdQuestion
     *            the id of the question
     * @return The List which contains the data of all the answer objects
     */
    List<Answer> selectAnswersList( int nIdQuestion, Plugin plugin );

    /**
     * Delete answer for a question
     * 
     * @param nIdQuestion
     *            The question ID
     * @param plugin
     *            The plugin
     */
    void deleteAnswersByQuestion( int nIdQuestion, Plugin plugin );

    /**
     * Load the data of the answer from the table
     * 
     * @param nIdProfil
     *            The identifier of the profil
     * @param plugin
     *            The plugin
     * @return <code>true</code> if there is at least one answer with profil, <code>false</code> otherwise
     */
    boolean isAnswersWithProfil( int nIdProfil, Plugin plugin );

}
