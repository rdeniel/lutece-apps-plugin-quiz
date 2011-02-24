/*
 * Copyright (c) 2002-2010, Mairie de Paris
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
 * This is the business class for the object Answer
 */
public class Answer
{
    // Variables declarations
    private int _nIdAnswer;
    private int _nIdQuestion;
    private String _strLabelAnswer;
    private int _nIsValid;

    /**
     * Returns the IdAnswer
     * @return The IdAnswer
     */
    public int getIdAnswer(  )
    {
        return _nIdAnswer;
    }

    /**
     * Sets the IdAnswer
     * @param nIdAnswer The IdAnswer
     */
    public void setIdAnswer( int nIdAnswer )
    {
        _nIdAnswer = nIdAnswer;
    }

    /**
     * Returns the IdQuestion
     * @return The IdQuestion
     */
    public int getIdQuestion(  )
    {
        return _nIdQuestion;
    }

    /**
     * Sets the IdQuestion
     * @param nIdQuestion The IdQuestion
     */
    public void setIdQuestion( int nIdQuestion )
    {
        _nIdQuestion = nIdQuestion;
    }

    /**
     * Returns the LabelAnswer
     * @return The LabelAnswer
     */
    public String getLabelAnswer(  )
    {
        return _strLabelAnswer;
    }

    /**
     * Sets the LabelAnswer
     * @param strLabelAnswer The LabelAnswer
     */
    public void setLabelAnswer( String strLabelAnswer )
    {
        _strLabelAnswer = strLabelAnswer;
    }

    /**
     * Returns the IsValid
     * @return The IsValid
     */
    public int getValid(  )
    {
        return _nIsValid;
    }

    /**
     * Returns true if it is the correct answer
     * @return true if it is the correct answer
     */
    public boolean isCorrect(  )
    {
        return _nIsValid == 1;
    }

    /**
     * Sets the IsValid
     * @param nIsValid The IsValid
     */
    public void setValid( int nIsValid )
    {
        _nIsValid = nIsValid;
    }
}
