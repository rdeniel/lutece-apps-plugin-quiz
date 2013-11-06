package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;

import java.util.Collection;


/**
 * Interface of quiz profile DAO
 */
public interface IQuizProfileDAO
{
    /**
     * Creation of an instance of an article Quiz profile
     * 
     * @param profil An instance of the Quiz profile which contains the
     *            informations to store
     * @param plugin the plugin
     */
    void insert( QuizProfile profil, Plugin plugin );

    /**
     * Select the list of profile
     * @param nIdQuiz The quiz ID
     * @param plugin plugin Quiz
     * @return profils list
     */
    ReferenceList selectQuizProfilsReferenceList( int nIdQuiz, Plugin plugin );

    /**
     * Return the name of a profile
     * @param nIdProfil The profile ID
     * @param plugin plugin Quiz
     * @return name of the corresponding profile
     */
    String getName( int nIdProfil, Plugin plugin );

    /**
     * Load a profile with its id
     * @param nKey the id to load
     * @param plugin plugin Quiz
     * @return corresponding profile
     */
    QuizProfile load( int nKey, Plugin plugin );

    /**
     * Find all profiles for a quiz
     * @param nIdQuiz the id quiz
     * @param plugin plugin Quiz
     * @return profile list
     */
    Collection<QuizProfile> findAll( int nIdQuiz, Plugin plugin );

    /**
     * Delete a profile
     * @param nIdProfil the profile id
     * @param plugin plugin Quiz
     */
    void delete( int nIdProfil, Plugin plugin );

    /**
     * Updates of the {@link QuizProfile} instance specified in parameter
     * @param quizProfil An instance of the {@link QuizProfile} which contains
     *            the informations to store
     * @param plugin the plugin
     */
    void store( QuizProfile quizProfil, Plugin plugin );

    /**
     * Delete profiles linked to a quiz
     * @param nIdQuiz the quiz id
     * @param plugin plugin Quiz
     */
    void deleteByQuiz( int nIdQuiz, Plugin plugin );
}
