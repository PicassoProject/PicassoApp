package picasso.picassoapp.models;

/**
 * Created by jl on 5/26/16.
 */
//a simple callback class that isn't expecting anything expect name of the drawing and the return status
public class SimpleCallback {
    private String name;
    private String status;

    public SimpleCallback()
    {
        name = "";
        status = "";
    }

    public String getName()
    {
        return this.name;
    }

    public String getStatus()
    {
        return this.status;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
