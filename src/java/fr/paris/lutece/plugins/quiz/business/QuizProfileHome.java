package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.Collection;


/**
 * Home for profiles
 */
public final class QuizProfileHome
{
    private static IQuizProfileDAO _dao = SpringContextService.getBean( "quiz.quizProfileDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private QuizProfileHome( )
    {
    }

    /**
     * Creation of an instance of an article Quiz profile
     * 
     * @param profile An instance of the Quiz profile which contains the
     *            informations to
     *            store
     * @param plugin the plugin
     * @return The instance of the Quiz profile which has been created
     */
    public static QuizProfile create( QuizProfile profile, Plugin plugin )
    {
        _dao.insert( profile, plugin );

        return profile;
    }

    /**
     * Select the list of profile
     * @param nIdQuiz The quiz ID
     * @param plugin plugin Quiz
     * @return profiles list
     */
    public static ReferenceList selectQuizProfilsReferenceList( int nIdQuiz, Plugin plugin )
    {
        return _dao.selectQuizProfilsReferenceList( nIdQuiz, plugin );
    }

    /**
     * Load a profile with its id
     * @param nKey the id to load
     * @param plugin plugin Quiz
     * @return corresponding profile
     */
    public static QuizProfile findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Return the name of a profile
     * @param nIdProfile The profile ID
     * @param plugin plugin Quiz
     * @return name of the corresponding profile
     */
    public static String getName( int nIdProfile, Plugin plugin )
    {
        return _dao.getName( nIdProfile, plugin );
    }

    /**
     * Find all profiles for a quiz
     * @param nIdQuiz the id quiz
     * @param plugin plugin Quiz
     * @return profiles list
     */
    public static Collection<QuizProfile> findAll( int nIdQuiz, Plugin plugin )
    {
        return _dao.findAll( nIdQuiz, plugin );
    }

    public static void remove( int nIdProfile, Plugin plugin )
    {
        _dao.delete( nIdProfile, plugin );
    }

    public static void update( QuizProfile quizProfile, Plugin plugin )
    {
        _dao.store( quizProfile, plugin );
    }

    public static void removeProfilesByQuiz( int nIdQuiz, Plugin plugin )
    {
        _dao.deleteByQuiz( nIdQuiz, plugin );
    }

}
