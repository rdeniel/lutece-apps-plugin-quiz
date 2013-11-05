package fr.paris.lutece.plugins.quiz.business;

/**
 * Image of a question of a quiz
 * @author vbroussard
 */
public class QuizQuestionImage
{
    private int _nIdQuestion;
    private byte[] _bArrayContent;
    private String _strContentType;

    /**
     * Default constructor
     */
    public QuizQuestionImage( )
    {
        // Default contructor
    }

    /**
     * Constructor with every parameters initialized
     * @param nIdQuestion The id of the question
     * @param bArrayContent The content of the image
     * @param strContentType The content type
     */
    public QuizQuestionImage( int nIdQuestion, byte[] bArrayContent, String strContentType )
    {
        this._nIdQuestion = nIdQuestion;
        this._bArrayContent = bArrayContent;
        this._strContentType = strContentType;
    }

    /**
     * Get the id of the question
     * @return The id of the question
     */
    public int getIdQuestion( )
    {
        return _nIdQuestion;
    }

    /**
     * Set the id of the question
     * @param nIdQuestion The id of the question
     */
    public void setIdQuestion( int nIdQuestion )
    {
        this._nIdQuestion = nIdQuestion;
    }

    /**
     * Get the content of the image
     * @return The content of the image
     */
    public byte[] getContent( )
    {
        return _bArrayContent;
    }

    /**
     * Set the content of the image
     * @param bArrayContent The content of the image
     */
    public void setContent( byte[] bArrayContent )
    {
        this._bArrayContent = bArrayContent;
    }

    /**
     * Get the content type of the image
     * @return The content type of the image
     */
    public String getContentType( )
    {
        return _strContentType;
    }

    /**
     * Set the content type of the image
     * @param strContentType The content type of the image
     */
    public void setContentType( String strContentType )
    {
        this._strContentType = strContentType;
    }
}
