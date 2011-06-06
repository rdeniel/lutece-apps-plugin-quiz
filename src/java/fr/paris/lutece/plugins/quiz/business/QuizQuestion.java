/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import java.util.List;


/**
 * This class represents business object QuizQuestion
 */
public class QuizQuestion
{
    private int _nIdQuestion;
    private int _nIdQuiz;
    private String _strQuestion;
    private int _nIdGroup;
    private String _strExplaination;
    private List<Answer> _listAnswers;

    /**
     * Creates a new QuizQuestion object.
     */
    public QuizQuestion(  )
    {
    }

    /**
     * Returns the identifier of this QuizQuestion.
     * @return _nIdQuestion The Question identifier
     */
    public int getIdQuestion(  )
    {
        return _nIdQuestion;
    }

    /**
     * Sets the identifier of the question to the specified integer.
     * @param nIdQuestion The Question identifier
     */
    public void setIdQuestion( int nIdQuestion )
    {
        _nIdQuestion = nIdQuestion;
    }

    /**
     * Return the identifier of the quiz
     * @return _nIdQuiz The Quiz identifier
     */
    public int getIdQuiz(  )
    {
        return _nIdQuiz;
    }

    /**
     * Sets the identifier of the quiz to the specifies integer
     * @param nIdQuiz The Quiz identifier
     */
    public void setIdQuiz( int nIdQuiz )
    {
        _nIdQuiz = nIdQuiz;
    }

    /**
     * Return The Question
     * @return _strQuestion The Question
     */
    public String getQuestionLabel(  )
    {
        return _strQuestion;
    }

    /**
     * Set the Question to the specified string
     * @param strQuestion The Question
     */
    public void setQuestionLabel( String strQuestion )
    {
        _strQuestion = strQuestion;
    }

    /**
       * Returns the IdGroup
       * @return The IdGroup
       */
    public int getIdGroup(  )
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
     * Gets explaination
     * @return The explaination
     */
    public String getExplaination(  )
    {
        return _strExplaination;
    }

    /**
     * Sets the explaination
     * @param strExplication The explaination to set
     */
    public void setExplaination( String strExplication )
    {
        _strExplaination = strExplication;
    }

    /**
     * Sets answsers to the question
     * @param listAnswers A list of answers
     */
    public void setAnswers( List<Answer> listAnswers )
    {
        _listAnswers = listAnswers;
    }

    /**
     * Gest answer to a question
     * @return The answer list
     */
    public List<Answer> getAnswers(  )
    {
        return _listAnswers;
    }
}
