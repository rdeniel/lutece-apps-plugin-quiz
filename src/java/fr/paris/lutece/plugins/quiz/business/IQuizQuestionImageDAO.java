package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;


/**
 * Interface of DAO for question images
 */
public interface IQuizQuestionImageDAO
{
    /**
     * Get the image associated with a given question
     * @param nIdQuestion The id of the question
     * @param plugin The plugin
     * @return The byte array of the image, or null if no image is associated
     *         with this question
     */
    QuizQuestionImage findQuestionImage( int nIdQuestion, Plugin plugin );

    /**
     * Insert an image into the database
     * @param questionImage The image
     * @param plugin The plugin
     */
    void insertQuestionImage( QuizQuestionImage questionImage, Plugin plugin );

    /**
     * Update an image of a question
     * @param questionImage The image
     * @param plugin The plugin
     */
    void updateQuestionImage( QuizQuestionImage questionImage, Plugin plugin );

    /**
     * Remove an image associated with a question
     * @param nIdQuestion The id of the question to remove
     * @param plugin The plugin
     */
    void removeQuestionImage( int nIdQuestion, Plugin plugin );

    /**
     * Check if a question is associated with an image
     * @param nQuestionId The id of the question
     * @param plugin The plugin
     * @return True if the question is associated with an image, false otherwise
     */
    boolean doesQuestionHasImage( int nQuestionId, Plugin plugin );
}