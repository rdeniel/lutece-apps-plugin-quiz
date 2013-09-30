package fr.paris.lutece.plugins.quiz.business;

/**
 * This class represents business object Quiz profil
 */
public class QuizProfil
{
    private int _nIdProfil;

    private String _strName;

    private String _strDescription;

    private int _nIdQuiz;

    /**
     * @return the _nIdProfil
     */
    public int getIdProfil( )
    {
        return _nIdProfil;
    }

    /**
     * @param nIdProfil the _nIdProfil to set
     */
    public void setIdProfil( int nIdProfil )
    {
        this._nIdProfil = nIdProfil;
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
