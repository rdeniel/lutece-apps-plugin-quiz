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
package fr.paris.lutece.plugins.quiz.service;

import fr.paris.lutece.plugins.quiz.business.Answer;
import fr.paris.lutece.plugins.quiz.business.AnswerHome;
import fr.paris.lutece.plugins.quiz.business.InvalidAnswer;
import fr.paris.lutece.plugins.quiz.business.QuestionGroup;
import fr.paris.lutece.plugins.quiz.business.QuestionGroupHome;
import fr.paris.lutece.plugins.quiz.business.Quiz;
import fr.paris.lutece.plugins.quiz.business.QuizHome;
import fr.paris.lutece.plugins.quiz.business.QuizProfil;
import fr.paris.lutece.plugins.quiz.business.QuizProfilHome;
import fr.paris.lutece.plugins.quiz.business.QuizQuestion;
import fr.paris.lutece.plugins.quiz.business.QuizQuestionHome;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Quiz Service
 * 
 */
public class QuizService
{
    public static final String KEY_QUIZ = "quiz";
    public static final String KEY_ERROR = "error";
    private static final String PROPERTY_MSG_MANY_GOOD_ANSWERS = "quiz.message.results.manyGoodAnswers";
    private static final String PROPERTY_MSG_ONE_GOOD_ANSWER = "quiz.message.results.oneGoodAnswer";
    private static final String PROPERTY_MSG_NO_GOOD_ANSWER = "quiz.message.results.noGoodAnswer";
    private static final String PROPERTY_MSG_PROFIL = "quiz.message.results.profils";
    private static final String PROPERTY_NO_ANSWER_FOR_QUESTION = "quiz.message.error.noAnswerForQuestion";
    private static final String MARK_SCORE = "score";
    private static final String MARK_QUESTIONS_COUNT = "questions_count";
    private static final String MARK_SCORE_MESSAGE = "score_message";
    private static final String MARK_MESSAGE = "message";
    private static final String MARK_INVALID_ANSWERS_LIST = "invalid_answers_list";
    private static final String MARK_QUIZ_LIST = "quiz_list";
    private static final String MARK_GROUPS_LIST = "groups_list";
    private static final String MARK_PROFILS_LIST = "profils_list";
    private static final String PLUGIN_NAME = "quiz";
    private static Plugin _plugin;

    /**
     * Gets the list of quiz
     * @return The model
     */
    public Map getQuizList( )
    {
        Collection<Quiz> quizList = QuizHome.findAllEnabled( getPlugin( ) );

        HashMap model = new HashMap( );
        model.put( MARK_QUIZ_LIST, quizList );

        return model;
    }

    /**
     * Gets a quiz as a model map
     * @param nId The quiz Id
     * @return The model
     */
    public Map getQuiz( int nId )
    {
        Quiz quiz = QuizHome.findByPrimaryKey( nId, getPlugin( ) );

        Collection<QuizQuestion> questionsList = QuizQuestionHome.findQuestionsWithAnswer( nId, getPlugin( ) );
        quiz.setQuestions( questionsList );

        List<QuestionGroup> listGroups = QuestionGroupHome.getGroupsList( nId, getPlugin( ) );

        for ( QuizQuestion question : questionsList )
        {
            List<Answer> answerListByQuestion = AnswerHome.getAnswersList( question.getIdQuestion( ), getPlugin( ) );
            question.setAnswers( answerListByQuestion );
        }

        HashMap model = new HashMap( );
        model.put( KEY_QUIZ, quiz );
        model.put( MARK_GROUPS_LIST, listGroups );

        return model;
    }

    /**
     * Gets the plugin object
     * @return The plugin
     */
    private static Plugin getPlugin( )
    {
        if ( _plugin == null )
        {
            _plugin = PluginService.getPlugin( PLUGIN_NAME );
        }

        return _plugin;
    }

    /**
     * Get results
     * @param nIdQuiz The quiz Id
     * @param parameterMap The parameters map
     * @param locale The current locale
     * @return a model as a map
     */
    public Map getResults( int nIdQuiz, Map<String, String[]> parameterMap, Locale locale )
    {
        Map model = new HashMap( );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        Collection<QuizQuestion> questionsList = QuizQuestionHome.findAll( nIdQuiz, getPlugin( ) );
        int nScore = 0;
        List<InvalidAnswer> listInvalidAnswers = new ArrayList<InvalidAnswer>( );

        for ( QuizQuestion question : questionsList )
        {
            String strQuestionId = String.valueOf( question.getIdQuestion( ) );
            String[] values = parameterMap.get( strQuestionId );

            if ( values == null )
            {
                String strMsgNoAnswserForQuestion = I18nService.getLocalizedString( PROPERTY_NO_ANSWER_FOR_QUESTION,
                        locale );
                model.put( KEY_ERROR, strMsgNoAnswserForQuestion + question.getQuestionLabel( ) );

                return model;
            }

            String strUserAnswer = values[0];

            if ( strUserAnswer != null )
            {
                int nUserAnswer = Integer.parseInt( strUserAnswer );
                Answer answer = AnswerHome.findByPrimaryKey( nUserAnswer, getPlugin( ) );

                if ( answer.isCorrect( ) )
                {
                    nScore++;
                }
                else
                {
                    InvalidAnswer ia = new InvalidAnswer( );
                    ia.setQuestionId( question.getIdQuestion( ) );
                    ia.setQuestion( question.getQuestionLabel( ) );
                    ia.setExplaination( question.getExplaination( ) );
                    ia.setInvalidAnswer( answer.getLabelAnswer( ) );
                    listInvalidAnswers.add( ia );
                }
            }
        }

        String strMessage = I18nService.getLocalizedString( PROPERTY_MSG_MANY_GOOD_ANSWERS, locale );

        switch ( nScore )
        {
        case 0:
            strMessage = I18nService.getLocalizedString( PROPERTY_MSG_NO_GOOD_ANSWER, locale );

            break;

        case 1:
            strMessage = I18nService.getLocalizedString( PROPERTY_MSG_ONE_GOOD_ANSWER, locale );

            break;

        default:
            break;
        }

        Object[] args = { nScore, questionsList.size( ) };
        String strScoreMessage = MessageFormat.format( strMessage, args );
        model.put( MARK_SCORE_MESSAGE, strScoreMessage );
        model.put( MARK_SCORE, nScore );
        model.put( MARK_QUESTIONS_COUNT, questionsList.size( ) );
        model.put( KEY_QUIZ, quiz );
        model.put( MARK_INVALID_ANSWERS_LIST, listInvalidAnswers );

        return model;
    }

    public Map calculateQuizProfil( int nIdQuiz, Map<String, String[]> parameterMap, Locale locale )
    {
        Map model = new HashMap( );
        Quiz quiz = QuizHome.findByPrimaryKey( nIdQuiz, getPlugin( ) );
        Collection<QuizQuestion> questionsList = QuizQuestionHome.findAll( nIdQuiz, getPlugin( ) );
        Map<Integer, Integer> profilMap = new HashMap<Integer, Integer>( );

        for ( QuizQuestion question : questionsList )
        {
            String strQuestionId = String.valueOf( question.getIdQuestion( ) );
            String[] values = parameterMap.get( strQuestionId );

            if ( values == null )
            {
                String strMsgNoAnswserForQuestion = I18nService.getLocalizedString( PROPERTY_NO_ANSWER_FOR_QUESTION,
                        locale );
                model.put( KEY_ERROR, strMsgNoAnswserForQuestion + question.getQuestionLabel( ) );

                return model;
            }

            String strUserAnswer = values[0];
            if ( strUserAnswer != null )
            {
                int nUserAnswer = Integer.parseInt( strUserAnswer );
                Answer answer = AnswerHome.findByPrimaryKey( nUserAnswer, getPlugin( ) );

                Integer profil = profilMap.get( answer.getIdProfil( ) );

                if ( profil != null )
                {
                    profilMap.put( answer.getIdProfil( ), profil + 1 );
                }
                else
                {
                    profilMap.put( new Integer( answer.getIdProfil( ) ), new Integer( 1 ) );
                }
            }
        }

        Integer mainProfil = -1;
        List<Integer> profilsId = new ArrayList<Integer>( );
        List<QuizProfil> profilsList = new ArrayList<QuizProfil>( );

        for ( Map.Entry<Integer, Integer> entry : profilMap.entrySet( ) )
        {
            if ( entry.getValue( ).compareTo( mainProfil ) > 0 )
            {
                mainProfil = entry.getValue( );
                profilsId = new ArrayList<Integer>( );
                profilsId.add( entry.getKey( ) );
            }
            else if ( entry.getValue( ).compareTo( mainProfil ) == 0 )
            {
                profilsId.add( entry.getKey( ) );
            }
        }

        for ( Integer profilId : profilsId )
        {
            QuizProfil profil = QuizProfilHome.findByPrimaryKey( profilId, getPlugin( ) );
            profilsList.add( profil );
        }

        String strMessage = I18nService.getLocalizedString( PROPERTY_MSG_PROFIL, locale );

        model.put( MARK_MESSAGE, strMessage );
        model.put( KEY_QUIZ, quiz );
        model.put( MARK_PROFILS_LIST, profilsList );

        return model;
    }
}
