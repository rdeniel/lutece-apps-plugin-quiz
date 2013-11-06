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
 * This is the business class for the object QuestionGroup
 */
public class QuestionGroup
{
    // Variables declarations
    private int _nIdGroup;
    private String _strLabelGroup;
    private String _strSubject;
    private int _nIdQuiz;
    private int _nPositionGroup;
    private boolean _bIsFreeHtml;
    private String _strHtmlContent;

    /**
     * Returns the IdGroup
     * @return The IdGroup
     */
    public int getIdGroup( )
    {
        return _nIdGroup;
    }

    /**
     * Sets the IdGroup
     * @param nIdGroup The IdGroup
     */
    public void setIdGroup( int nIdGroup )
    {
        _nIdGroup = nIdGroup;
    }

    /**
     * Returns the LabelGroup
     * @return The LabelGroup
     */
    public String getLabelGroup( )
    {
        return _strLabelGroup;
    }

    /**
     * Sets the LabelGroup
     * @param strLabelGroup The LabelGroup
     */
    public void setLabelGroup( String strLabelGroup )
    {
        _strLabelGroup = strLabelGroup;
    }

    /**
     * Returns the Subject
     * @return The Subject
     */
    public String getSubject( )
    {
        return _strSubject;
    }

    /**
     * Sets the Subject
     * @param strSubject The Subject
     */
    public void setSubject( String strSubject )
    {
        _strSubject = strSubject;
    }

    /**
     * Returns the identifier of this Quiz.
     * @return _nIdQuiz The Quiz identifier
     */
    public int getIdQuiz( )
    {
        return _nIdQuiz;
    }

    /**
     * Sets the identifier of the quiz to the specified integer.
     * @param nIdQuiz The quiz identifier
     */
    public void setIdQuiz( int nIdQuiz )
    {
        _nIdQuiz = nIdQuiz;
    }

    /**
     * Returns the identifier of this Quiz.
     * @return _nIdQuiz The Quiz identifier
     */
    public int getPositionGroup( )
    {
        return _nPositionGroup;
    }

    /**
     * Sets the identifier of the quiz to the specified integer.
     * @param nPositionGroup The group position
     */
    public void setPositionGroup( int nPositionGroup )
    {
        _nPositionGroup = nPositionGroup;
    }

    /**
     * Check if this group is a group with free HTML
     * @return True if this group is a group with free HTML, false if it has
     *         questions
     */
    public boolean getIsFreeHtml( )
    {
        return _bIsFreeHtml;
    }

    /**
     * Set this group free HTML group or a question group
     * @param bIsFreeHtml True if this group is a group with free HTML, false if
     *            it has questions
     */
    public void setIsFreeHtml( boolean bIsFreeHtml )
    {
        this._bIsFreeHtml = bIsFreeHtml;
    }

    /**
     * Get the HTMl content of this group, or null if this group is not a free
     * HTML group
     * @return the HTMl content of this group, or null if this group is not a
     *         free HTML group
     */
    public String getHtmlContent( )
    {
        return _strHtmlContent;
    }

    /**
     * Set the HTMl content of this group, or null if this group is not a free
     * HTML group
     * @param strHtmlContent the HTMl content of this group, or null if this
     *            group is not a free HTML group
     */
    public void setHtmlContent( String strHtmlContent )
    {
        this._strHtmlContent = strHtmlContent;
    }
}
