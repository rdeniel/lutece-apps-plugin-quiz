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
 * InvalidAnswer
 */
public class UserAnswer
{
    private int _nQuestionId;
    private String _strquestion;
    private String _strExplaination;
    private String _strAnswer;
    private String _strValidAnswer;
    private boolean _bIsValid;

    /**
     * @return the questionId
     */
    public int getQuestionId( )
    {
        return _nQuestionId;
    }

    /**
     * @param nQuestionId the questionId to set
     */
    public void setQuestionId( int nQuestionId )
    {
        _nQuestionId = nQuestionId;
    }

    /**
     * @return the question
     */
    public String getQuestion( )
    {
        return _strquestion;
    }

    /**
     * @param strQuestion the question to set
     */
    public void setQuestion( String strQuestion )
    {
        _strquestion = strQuestion;
    }

    /**
     * @return the Explaination
     */
    public String getExplaination( )
    {
        return _strExplaination;
    }

    /**
     * @param strExplaination the Explaination to set
     */
    public void setExplaination( String strExplaination )
    {
        _strExplaination = strExplaination;
    }

    /**
     * @return the invalidAnswer
     */
    public String getAnswer( )
    {
        return _strAnswer;
    }

    /**
     * @param strAnswer the answer to set
     */
    public void setAnswer( String strAnswer )
    {
        _strAnswer = strAnswer;
    }

    /**
     * @return the validAnswer
     */
    public String getValidAnswer( )
    {
        return _strValidAnswer;
    }

    /**
     * @param strValidAnswer the validAnswer to set
     */
    public void setValidAnswer( String strValidAnswer )
    {
        _strValidAnswer = strValidAnswer;
    }

    /**
     * Check if this answer is a valid answer
     * @return True if this answer is valid, false otherwise
     */
    public boolean getIsValid( )
    {
        return _bIsValid;
    }

    /**
     * Set this answer valid or invalid
     * @param bIsValid True if this answer is valid, false otherwise
     */
    public void setIsValid( boolean bIsValid )
    {
        this._bIsValid = bIsValid;
    }
}
