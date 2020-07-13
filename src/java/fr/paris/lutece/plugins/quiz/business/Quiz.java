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

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

/**
 * This class represents the business object Quiz
 */
public class Quiz
{
    private int _nIdQuiz;
    private String _strName;
    private boolean _bIsEnabled;
    private Collection<QuizQuestion> _questions;
    private String _strIntroduction;
    private String _strConclusion;
    private String _strCgu;
    private Date _tDateBeginDisponibility;
    private Date _tDateEndDisponibility;
    private Timestamp _tDateCreation;
    private byte [ ] _strImg;
    private int _nCaptcha;
    private int _nRequirement;
    private boolean _bDisplayStepByStep;
    private boolean _bDisplayResultAfterEachStep;

    /** Quiz type. */
    private String _strTypeQuiz;

    /**
     * Creates a new Quiz object.
     */
    public Quiz( )
    {
    }

    /**
     * Returns the identifier of this Quiz.
     * 
     * @return _nIdQuiz The Quiz identifier
     */
    public int getIdQuiz( )
    {
        return _nIdQuiz;
    }

    /**
     * Sets the identifier of the quiz to the specified integer.
     * 
     * @param nIdQuiz
     *            The quiz identifier
     */
    public void setIdQuiz( int nIdQuiz )
    {
        _nIdQuiz = nIdQuiz;
    }

    /**
     * Returns the name of this quiz.
     * 
     * @return _strName The name of the quiz
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * Sets the name of the quiz to the specified string
     * 
     * @param strName
     *            The new name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Returns The boolean status of quiz
     * 
     * @return _bIsEnabled The boolean
     */
    public boolean isEnabled( )
    {
        return _bIsEnabled;
    }

    /**
     * Set The Boolean status
     * 
     * @param status
     *            The Status
     */
    public void setStatus( int status )
    {
        if ( status == 1 )
        {
            _bIsEnabled = true;
        }
        else
        {
            _bIsEnabled = false;
        }
    }

    /**
     * Returns a collection of questions
     * 
     * @return _questions The list of questions
     */
    public Collection<QuizQuestion> getQuestions( )
    {
        return _questions;
    }

    /**
     * Sets the list of questions on a Collection
     * 
     * @param questions
     *            The Collection of questions
     */
    public void setQuestions( Collection<QuizQuestion> questions )
    {
        _questions = questions;
    }

    /**
     * Returns the name of this quiz.
     * 
     * @return _strName The name of the quiz
     */
    public String getIntroduction( )
    {
        return _strIntroduction;
    }

    /**
     * Sets the introduction of the quiz to the specified string
     * 
     * @param strIntroduction
     *            The new name
     */
    public void setIntroduction( String strIntroduction )
    {
        _strIntroduction = strIntroduction;
    }

    /**
     * Returns the conclusion of this quiz.
     * 
     * @return The conclusion of the quiz
     */
    public String getConclusion( )
    {
        return _strConclusion;
    }

    /**
     * Sets the conclusion of the quiz to the specified string
     * 
     * @param strConclusion
     *            The conclusion
     */
    public void setConclusion( String strConclusion )
    {
        _strConclusion = strConclusion;
    }

    /**
     * Gets the CGU
     * 
     * @return the CGU
     */
    public String getCgu( )
    {
        return _strCgu;
    }

    /**
     * Sets CGU
     * 
     * @param strCgu
     *            The CGU to set
     */
    public void setCgu( String strCgu )
    {
        _strCgu = strCgu;
    }

    /**
     * Define the date begin of the publication
     * 
     * @param dateBeginDisponibility
     *            The date begin of the publication
     */
    public void setDateBeginDisponibility( Date dateBeginDisponibility )
    {
        _tDateBeginDisponibility = dateBeginDisponibility;
    }

    /**
     * Return the date begin of the publication
     * 
     * @return The date begin of the publication
     */
    public Date getDateBeginDisponibility( )
    {
        return _tDateBeginDisponibility;
    }

    /**
     * 
     * @return the date of end diosponibility
     */
    public Date getDateEndDisponibility( )
    {
        return _tDateEndDisponibility;
    }

    /**
     * set the date of end disponibility
     * 
     * @param dateEndDisponibility
     *            the date of end disponibility
     */
    public void setDateEndDisponibility( Date dateEndDisponibility )
    {
        _tDateEndDisponibility = dateEndDisponibility;
    }

    /**
     * 
     * @return the creation date
     */
    public Timestamp getDateCreation( )
    {
        return _tDateCreation;
    }

    /**
     * set the creation date
     * 
     * @param dateCreation
     *            the creation date
     */
    public void setDateCreation( Timestamp dateCreation )
    {
        _tDateCreation = dateCreation;
    }

    /**
     * Returns the Value
     * 
     * @return The Value
     */
    public byte [ ] getImg( )
    {
        return _strImg;
    }

    /**
     * Sets the Value
     * 
     * @param strImg
     *            The Value
     */
    public void setValue( byte [ ] strImg )
    {
        _strImg = strImg;
    }

    /**
     * Gest captcha status
     * 
     * @return true if active
     */
    public int getActiveCaptcha( )
    {
        return _nCaptcha;
    }

    /**
     * Sets captcha status
     * 
     * @param nCaptcha
     *            The captcha status
     */
    public void setActiveCaptcha( int nCaptcha )
    {
        _nCaptcha = nCaptcha;
    }

    /**
     * Gets the requirement status
     * 
     * @return the requirement status
     */
    public int getActiveRequirement( )
    {
        return _nRequirement;
    }

    /**
     * Sets the requirement status
     * 
     * @param nRequirement
     *            the requirement status
     */
    public void setActiveRequirement( int nRequirement )
    {
        _nRequirement = nRequirement;
    }

    /**
     * @return the _strTypeQuiz
     */
    public String getTypeQuiz( )
    {
        return _strTypeQuiz;
    }

    /**
     * @param strTypeQuiz
     *            the _strTypeQuiz to set
     */
    public void setTypeQuiz( String strTypeQuiz )
    {
        this._strTypeQuiz = strTypeQuiz;
    }

    /**
     * Check if this quiz should be displayed step by step or in a block
     * 
     * @return True if this quiz should be displayed step by step, false if it should be displayed in a block
     */
    public boolean getDisplayStepByStep( )
    {
        return _bDisplayStepByStep;
    }

    /**
     * Set the display method of this quiz
     * 
     * @param bDisplayStepByStep
     *            True if this quiz should be displayed step by step, false if it should be displayed in a block
     */
    public void setDisplayStepByStep( boolean bDisplayStepByStep )
    {
        this._bDisplayStepByStep = bDisplayStepByStep;
    }

    /**
     * Check if results of the quiz should be displayed after each step or at the end of the quiz
     * 
     * @return True if results of the quiz should be displayed after each step or at the end of the quiz
     */
    public boolean getDisplayResultAfterEachStep( )
    {
        return _bDisplayResultAfterEachStep;
    }

    /**
     * Display results of the quiz after each step or at the end of the quiz
     * 
     * @param bDisplayResultAfterEachStep
     *            True if results of the quiz should be displayed after each step or at the end of the quiz
     */
    public void setDisplayResultAfterEachStep( boolean bDisplayResultAfterEachStep )
    {
        this._bDisplayResultAfterEachStep = bDisplayResultAfterEachStep;
    }
}
