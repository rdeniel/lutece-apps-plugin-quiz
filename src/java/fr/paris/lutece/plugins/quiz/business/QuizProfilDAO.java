package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;


public class QuizProfilDAO implements IQuizProfilDAO
{
    private static final String SQL_QUERY_NEW_PK = " SELECT max( id_profil ) FROM quiz_profil ";
    private static final String SQL_QUERY_INSERT_PROFIL = "INSERT INTO quiz_profil ( id_profil, name, description, id_quiz ) VALUES ( ?, ?, ?, ? )";
    private static final String SQL_QUERY_SELECT_ALL = "SELECT id_profil, name, description FROM quiz_profil WHERE id_quiz = ? ORDER BY id_profil";
    private static final String SQL_QUERY_LOAD_PROFIL_BY_ID = "SELECT name FROM quiz_profil WHERE id_profil = ?";
    private static final String SQL_QUERY_SELECT_PROFIL = "SELECT id_profil, name, description FROM quiz_profil WHERE id_profil = ?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM quiz_profil WHERE id_profil = ? ";
    private static final String SQL_QUERY_DELETE_BY_QUIZ = "DELETE FROM quiz_profil WHERE id_quiz = ? ";
    private static final String SQL_QUERY_UPDATE = " UPDATE quiz_profil SET name = ?, description = ? WHERE id_profil = ?  ";

    /**
     * Find the new primary key in the table.
     * 
     * @param plugin the plugin
     * @return the new primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery( );

        int nKey;

        if ( !daoUtil.next( ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free( );

        return nKey;
    }

    @Override
    public void insert( QuizProfil profil, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_PROFIL, plugin );
        profil.setIdProfil( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, profil.getIdProfil( ) );
        daoUtil.setString( 2, profil.getName( ) );
        daoUtil.setString( 3, profil.getDescription( ) );
        daoUtil.setInt( 4, profil.getIdQuiz( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public ReferenceList selectQuizProfilsReferenceList( int nIdQuiz, Plugin plugin )
    {
        ReferenceList profilsReferenceList = new ReferenceList( );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, plugin );
        daoUtil.setInt( 1, nIdQuiz );

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            ReferenceItem item = new ReferenceItem( );
            item.setCode( String.valueOf( daoUtil.getInt( 1 ) ) );
            item.setName( daoUtil.getString( 2 ) );
            profilsReferenceList.add( item );
        }

        daoUtil.free( );

        return profilsReferenceList;
    }

    @Override
    public String getName( int nIdProfil, Plugin plugin )
    {
        String name = "";

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_LOAD_PROFIL_BY_ID, plugin );
        daoUtil.setInt( 1, nIdProfil );

        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            name = daoUtil.getString( 1 );
        }

        daoUtil.free( );

        return name;
    }

    @Override
    public QuizProfil load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PROFIL, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );

        QuizProfil quizProfil = null;

        if ( daoUtil.next( ) )
        {
            quizProfil = new QuizProfil( );
            quizProfil.setIdProfil( daoUtil.getInt( 1 ) );
            quizProfil.setName( daoUtil.getString( 2 ) );
            quizProfil.setDescription( daoUtil.getString( 3 ) );
        }

        daoUtil.free( );

        return quizProfil;
    }

    @Override
    public Collection<QuizProfil> findAll( int nIdQuiz, Plugin plugin )
    {
        Collection<QuizProfil> result = new ArrayList<QuizProfil>( );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, plugin );
        daoUtil.setInt( 1, nIdQuiz );

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            QuizProfil profil = new QuizProfil( );
            profil.setIdProfil( daoUtil.getInt( 1 ) );
            profil.setName( daoUtil.getString( 2 ) );
            profil.setDescription( daoUtil.getString( 3 ) );
            result.add( profil );
        }

        daoUtil.free( );

        return result;
    }

    @Override
    public void delete( int nIdProfil, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nIdProfil );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public void store( QuizProfil quizProfil, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        daoUtil.setString( 1, quizProfil.getName( ) );
        daoUtil.setString( 2, quizProfil.getDescription( ) );
        daoUtil.setInt( 3, quizProfil.getIdProfil( ) );

        daoUtil.executeUpdate( );

        daoUtil.free( );
    }

    @Override
    public void deleteByQuiz( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_QUIZ, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }
}
