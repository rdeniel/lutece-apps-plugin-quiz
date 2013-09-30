package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.Collection;


public class QuizProfilHome
{
    private static IQuizProfilDAO _dao = (IQuizProfilDAO) SpringContextService.getPluginBean( "quiz",
            "quiz.quizProfilDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private QuizProfilHome( )
    {
    }

    /**
     * Creation of an instance of an article Quiz profil
     * 
     * @param profil An instance of the Quiz profil which contains the
     *            informations to
     *            store
     * @param plugin the plugin
     * @return The instance of the Quiz profil which has been created
     */
    public static QuizProfil create( QuizProfil profil, Plugin plugin )
    {
        _dao.insert( profil, plugin );

        return profil;
    }

    /**
     * Select the list of profile
     * @param nIdQuiz The quiz ID
     * @param plugin plugin Quiz
     * @return profils list
     */
    public static ReferenceList selectQuizProfilsReferenceList( int nIdQuiz, Plugin plugin )
    {
        return _dao.selectQuizProfilsReferenceList( nIdQuiz, plugin );
    }

    /**
     * Load a profil with its id
     * @param nKey the id to load
     * @param plugin plugin Quiz
     * @return corresponding profil
     */
    public static QuizProfil findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Return the name of a profil
     * @param nIdProfil The profil ID
     * @param plugin plugin Quiz
     * @return name of the corresponding profil
     */
    public static String getName( int nIdProfil, Plugin plugin )
    {
        return _dao.getName( nIdProfil, plugin );
    }

    /**
     * Find all profils for a quiz
     * @param nIdQuiz the id quiz
     * @param plugin plugin Quiz
     * @return profils list
     */
    public static Collection<QuizProfil> findAll( int nIdQuiz, Plugin plugin )
    {
        return _dao.findAll( nIdQuiz, plugin );
    }

    public static void remove( int nIdProfil, Plugin plugin )
    {
        _dao.delete( nIdProfil, plugin );
    }

    public static void update( QuizProfil quizProfil, Plugin plugin )
    {
        _dao.store( quizProfil, plugin );
    }

    public static void removeProfilsByQuiz( int nIdQuiz, Plugin plugin )
    {
        _dao.deleteByQuiz( nIdQuiz, plugin );
    }

}
