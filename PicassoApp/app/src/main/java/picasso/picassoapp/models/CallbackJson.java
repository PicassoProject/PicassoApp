package picasso.picassoapp.models;


/**
 * Created by jl on 4/5/16.
 */
public class CallbackJson
{
    //simple class that is used to store the response from the server API
    private String status;
    private String name;
    private String cordinates;

    public CallbackJson()
    {
        status = "";
        name = "";
        cordinates = "";
    }

    public String getStatus()
    {
        return status;
    }

    public String getName()
    {
        return name;
    }

    public String getCordinates()
    {
        return cordinates;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCordinates(String Cordinates)
    {
        this.cordinates = cordinates;
    }
}
