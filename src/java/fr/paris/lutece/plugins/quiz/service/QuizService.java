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
import fr.paris.lutece.plugins.quiz.business.QuizProfile;
import fr.paris.lutece.plugins.quiz.business.QuizProfileHome;
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
 */
public class QuizService
{
    public static final String KEY_QUIZ = "quiz";
    public static final String KEY_ERROR = "error";
    private static final String PROPERTY_MSG_MANY_GOOD_ANSWERS = "quiz.message.results.manyGoodAnswers";
    private static final String PROPERTY_MSG_ONE_GOOD_ANSWER = "quiz.message.results.oneGoodAnswer";
    private static final String PROPERTY_MSG_NO_GOOD_ANSWER = "quiz.message.results.noGoodAnswer";
    private static final String PROPERTY_MSG_PROFILE = "quiz.message.results.profils";
    private static final String PROPERTY_NO_ANSWER_FOR_QUESTION = "quiz.message.error.noAnswerForQuestion";
    private static final String MARK_SCORE = "score";
    private static final String MARK_QUESTIONS_COUNT = "questions_count";
    private static final String MARK_SCORE_MESSAGE = "score_message";
    private static final String MARK_MESSAGE = "message";
    private static final String MARK_INVALID_ANSWERS_LIST = "invalid_answers_list";
    private static final String MARK_QUIZ_LIST = "quiz_list";
    private static final String MARK_GROUP = "group";
    private static final String MARK_GROUPS_LIST = "groups_list";
    private static final String MARK_PROFILES_LIST = "profils_list";
    private static final String PLUGIN_NAME = "quiz";
    private static Plugin _plugin;

    /**
     * Gets the list of quiz
     * @return The model
     */
    public Map<String, Object> getQuizList( )
    {
        Collection<Quiz> quizList = QuizHome.findAllEnabled( getPlugin( ) );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_QUIZ_LIST, quizList );

        return model;
    }

    /**
     * Find a quiz by its primary key
     * @param nQuizId The id of the quiz
     * @return The quiz with the given id, or null if no quiz was found
     */
    public Quiz findQuizById( int nQuizId )
    {
        return QuizHome.findByPrimaryKey( nQuizId, getPlugin( ) );
    }

    /**
     * Gets a quiz as a model map
     * @param nId The quiz Id
     * @return The model
     */
    public Map<String, Object> getQuiz( int nId )
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

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( KEY_QUIZ, quiz );
        model.put( MARK_GROUPS_LIST, listGroups );

        return model;
    }

    /**
     * Gets a quiz step as a model map
     * @param quiz The quiz
     * @param nOldStepId The id of the last displayed step, or 0 to display the
     *            first step
     * @return The model
     */
    public Map<String, Object> getQuizNextStep( Quiz quiz, int nOldStepId )
    {
        Plugin plugin = getPlugin( );
        QuestionGroup group = QuestionGroupHome.getGroupByPosition( quiz.getIdQuiz( ), nOldStepId + 1, plugin );
        // If there is no more group
        if ( group == null )
        {
            return null;
        }
        Collection<QuizQuestion> questionsList = QuizQuestionHome.findQuestionsWithAnswerByIdGroup( quiz.getIdQuiz( ),
                group.getIdGroup( ), plugin );

        for ( QuizQuestion question : questionsList )
        {
            List<Answer> answerListByQuestion = AnswerHome.getAnswersList( question.getIdQuestion( ), getPlugin( ) );
            question.setAnswers( answerListByQuestion );
        }

        quiz.setQuestions( questionsList );

        HashMap<String, Object> model = new HashMap<String, Object>( );
        model.put( KEY_QUIZ, quiz );
        model.put( MARK_GROUP, group );

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
     * Get the answers of a user to questions of a quiz
     * @param nIdQuiz The id of the quiz to get answers of
     * @param nIdOldStep The last validated step
     * @param parameterMap The map of HTTP parameters
     * @param locale The locale to use to display error messages
     * @param plugin The plugin
     * @return A map containing question ids as keys and user answers as values
     */
    public Map<String, String[]> getUserAnswersForGroup( int nIdQuiz, int nIdOldStep,
            Map<String, String[]> parameterMap,
            Locale locale, Plugin plugin )
    {
        //        Collection<QuizQuestion> questionsList = QuizQuestionHome.findAll( nIdQuiz, getPlugin( ) );
        QuestionGroup group = QuestionGroupHome.getGroupByPosition( nIdQuiz, nIdOldStep, plugin );
        // If the group was not found
        if ( group == null )
        {
            return null;
        }
        Collection<QuizQuestion> questionsList = QuizQuestionHome.findQuestionsWithAnswerByIdGroup( nIdQuiz,
                group.getIdGroup( ), plugin );

        Map<String, String[]> mapUserAnswers = new HashMap<String, String[]>( questionsList.size( ) );
        for ( QuizQuestion question : questionsList )
        {
            String strQuestionId = String.valueOf( question.getIdQuestion( ) );
            String[] values = parameterMap.get( strQuestionId );
            if ( values == null )
            {
                String strMsgNoAnswserForQuestion = I18nService.getLocalizedString( PROPERTY_NO_ANSWER_FOR_QUESTION,
                        locale );
                String[] strArray = { strMsgNoAnswserForQuestion + question.getQuestionLabel( ) };
                mapUserAnswers.put( KEY_ERROR, strArray );

                return mapUserAnswers;
            }
            mapUserAnswers.put( strQuestionId, values );
        }
        return mapUserAnswers;
    }

    /**
     * Get results
     * @param nIdQuiz The quiz Id
     * @param parameterMap The parameters map
     * @param locale The current locale
     * @return a model as a map
     */
    public Map<String, Object> getResults( int nIdQuiz, Map<String, String[]> parameterMap, Locale locale )
    {
        Map<String, Object> model = new HashMap<String, Object>( );
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

    /**
     * Compute the profile of a user from his answers
     * @param nIdQuiz The id of the answered quiz
     * @param parameterMap The map containing answers
     * @param locale The locale
     * @return The model
     */
    public Map<String, Object> calculateQuizProfile( int nIdQuiz, Map<String, String[]> parameterMap, Locale locale )
    {
        Map<String, Object> model = new HashMap<String, Object>( );
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
                    profilMap.put( Integer.valueOf( answer.getIdProfil( ) ), Integer.valueOf( 1 ) );
                }
            }
        }

        Integer mainProfile = -1;
        List<Integer> profilesId = new ArrayList<Integer>( );
        List<QuizProfile> profilesList = new ArrayList<QuizProfile>( );

        for ( Map.Entry<Integer, Integer> entry : profilMap.entrySet( ) )
        {
            if ( entry.getValue( ).compareTo( mainProfile ) > 0 )
            {
                mainProfile = entry.getValue( );
                profilesId = new ArrayList<Integer>( );
                profilesId.add( entry.getKey( ) );
            }
            else if ( entry.getValue( ).compareTo( mainProfile ) == 0 )
            {
                profilesId.add( entry.getKey( ) );
            }
        }

        for ( Integer profileId : profilesId )
        {
            QuizProfile profile = QuizProfileHome.findByPrimaryKey( profileId, getPlugin( ) );
            profilesList.add( profile );
        }

        String strMessage = I18nService.getLocalizedString( PROPERTY_MSG_PROFILE, locale );

        model.put( MARK_MESSAGE, strMessage );
        model.put( KEY_QUIZ, quiz );
        model.put( MARK_PROFILES_LIST, profilesList );

        return model;
    }
}
