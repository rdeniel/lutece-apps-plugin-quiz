package fr.paris.lutece.plugins.quiz.business;

/**
 * This class represents business object Quiz profile
 */
public class QuizProfile
{
    private int _nIdProfile;

    private String _strName;

    private String _strDescription;

    private int _nIdQuiz;

    /**
     * Get the id of the profile
     * @return The id of the profile
     */
    public int getIdProfile( )
    {
        return _nIdProfile;
    }

    /**
     * Set the id of the profile
     * @param nIdProfile The id of the profile
     */
    public void setIdProfile( int nIdProfile )
    {
        this._nIdProfile = nIdProfile;
    }

    /**
     * @return the _strName
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * @param strName the _strName to set
     */
    public void setName( String strName )
    {
        this._strName = strName;
    }

    /**
     * @return the _strDescription
     */
    public String getDescription( )
    {
        return _strDescription;
    }

    /**
     * @param strDescription the _strDescription to set
     */
    public void setDescription( String strDescription )
    {
        this._strDescription = strDescription;
    }

    /**
     * @return the _nIdQuiz
     */
    public int getIdQuiz( )
    {
        return _nIdQuiz;
    }

    /**
     * @param nIdQuiz the _nIdQuiz to set
     */
    public void setIdQuiz( int nIdQuiz )
    {
        this._nIdQuiz = nIdQuiz;
    }

}
