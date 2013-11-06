package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * DAO for images of questions
 */
public class QuizQuestionImageDAO implements IQuizQuestionImageDAO
{
    private static final String SQL_QUERY_FIND_QUESTION_IMAGE = "SELECT image_content, content_type FROM quiz_question_image WHERE id_question = ?";
    private static final String SQL_QUERY_INSERT_QUESTION_IMAGE = "INSERT INTO quiz_question_image(id_question, image_content, content_type) VALUES (?,?,?)";
    private static final String SQL_QUERY_UPDATE_QUESTION_IMAGE = "UPDATE quiz_question_image SET image_content = ?, content_type = ? WHERE id_question = ?";
    private static final String SQL_QUERY_REMOVE_QUESTION_IMAGE = "DELETE FROM quiz_question_image WHERE id_question = ?";
    private static final String SQL_QUERY_SELECT_QUESTION_ID = "SELECT id_question FROM quiz_question_image WHERE id_question = ?";

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestionImage findQuestionImage( int nIdQuestion, Plugin plugin )
    {
        QuizQuestionImage questionImage = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_QUESTION_IMAGE, plugin );
        daoUtil.setInt( 1, nIdQuestion );
        daoUtil.executeQuery( );
        if ( daoUtil.next( ) )
        {
            questionImage = new QuizQuestionImage( nIdQuestion, daoUtil.getBytes( 1 ), daoUtil.getString( 2 ) );
        }
        daoUtil.free( );
        return questionImage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertQuestionImage( QuizQuestionImage questionImage, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_QUESTION_IMAGE, plugin );
        daoUtil.setInt( 1, questionImage.getIdQuestion( ) );
        daoUtil.setBytes( 2, questionImage.getContent( ) );
        daoUtil.setString( 3, questionImage.getContentType( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQuestionImage( QuizQuestionImage questionImage, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_QUESTION_IMAGE, plugin );
        daoUtil.setBytes( 1, questionImage.getContent( ) );
        daoUtil.setString( 2, questionImage.getContentType( ) );
        daoUtil.setInt( 3, questionImage.getIdQuestion( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeQuestionImage( int nIdQuestion, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_REMOVE_QUESTION_IMAGE, plugin );
        daoUtil.setInt( 1, nIdQuestion );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doesQuestionHasImage( int nQuestionId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_QUESTION_ID, plugin );
        daoUtil.setInt( 1, nQuestionId );
        boolean bResult;
        daoUtil.executeQuery( );
        if ( daoUtil.next( ) )
        {
            bResult = true;
        }
        else
        {
            bResult = false;
        }
        daoUtil.free( );
        return bResult;
    }
}
