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

/**
 * Image of a question of a quiz
 * @author vbroussard
 */
public class QuizQuestionImage
{
    private int _nIdQuestion;
    private byte[] _bArrayContent;
    private String _strContentType;

    /**
     * Default constructor
     */
    public QuizQuestionImage( )
    {
        // Default contructor
    }

    /**
     * Constructor with every parameters initialized
     * @param nIdQuestion The id of the question
     * @param bArrayContent The content of the image
     * @param strContentType The content type
     */
    public QuizQuestionImage( int nIdQuestion, byte[] bArrayContent, String strContentType )
    {
        this._nIdQuestion = nIdQuestion;
        this._bArrayContent = bArrayContent;
        this._strContentType = strContentType;
    }

    /**
     * Get the id of the question
     * @return The id of the question
     */
    public int getIdQuestion( )
    {
        return _nIdQuestion;
    }

    /**
     * Set the id of the question
     * @param nIdQuestion The id of the question
     */
    public void setIdQuestion( int nIdQuestion )
    {
        this._nIdQuestion = nIdQuestion;
    }

    /**
     * Get the content of the image
     * @return The content of the image
     */
    public byte[] getContent( )
    {
        return _bArrayContent;
    }

    /**
     * Set the content of the image
     * @param bArrayContent The content of the image
     */
    public void setContent( byte[] bArrayContent )
    {
        this._bArrayContent = bArrayContent;
    }

    /**
     * Get the content type of the image
     * @return The content type of the image
     */
    public String getContentType( )
    {
        return _strContentType;
    }

    /**
     * Set the content type of the image
     * @param strContentType The content type of the image
     */
    public void setContentType( String strContentType )
    {
        this._strContentType = strContentType;
    }
}
