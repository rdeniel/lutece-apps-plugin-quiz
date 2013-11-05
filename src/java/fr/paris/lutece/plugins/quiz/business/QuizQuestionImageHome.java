package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;


/**
 * Home for images of quiz's questions
 * @author vbroussard
 * 
 */
public final class QuizQuestionImageHome
{
    private static IQuizQuestionImageDAO _dao = SpringContextService.getBean( "quiz.questionImageDAO" );

    /**
     * Private constructor
     */
    private QuizQuestionImageHome( )
    {
        // Private constructor
    }

    /**
     * Get the image associated with a question
     * @param nIdQuestion The id of the question
     * @param plugin The plugin
     * @return The question image
     */
    public static QuizQuestionImage getQuestionImage( int nIdQuestion, Plugin plugin )
    {
        return _dao.findQuestionImage( nIdQuestion, plugin );
    }

    /**
     * Insert an image associated with a given question
     * @param questionImage The image to insert
     * @param plugin The plugin
     */
    public static void insertQuestionImage( QuizQuestionImage questionImage, Plugin plugin )
    {
        _dao.insertQuestionImage( questionImage, plugin );
    }

    /**
     * Update an image associated with a given question
     * @param questionImage The image to insert
     * @param plugin The plugin
     */
    public static void updateQuestionImage( QuizQuestionImage questionImage, Plugin plugin )
    {
        _dao.updateQuestionImage( questionImage, plugin );
    }
}
