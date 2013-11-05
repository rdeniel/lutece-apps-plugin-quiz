package fr.paris.lutece.plugins.quiz.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * DAO for images of questions
 * @author vbroussard
 */
public class QuizQuestionImageDAO implements IQuizQuestionImageDAO
{
    private static final String SQL_QUERY_FIND_QUESTION_IMAGE = "SELECT image_content, content_type FROM quiz_question_image WHERE id_question = ?";
    private static final String SQL_QUERY_INSERT_QUESTION_IMAGE = "INSERT INTO quiz_question_image(id_question, image_content, content_type) VALUES (?,?,?)";
    private static final String SQL_QUERY_UPDATE_QUESTION_IMAGE = "UPDATE quiz_question_image SET image_content = ?, content_type = ? WHERE id_question = ?";

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
}
