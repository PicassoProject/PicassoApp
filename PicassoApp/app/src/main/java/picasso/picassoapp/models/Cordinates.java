package picasso.picassoapp.models;

/**
 * Created by jl on 4/5/16.
 */
public class Cordinates
{
    private float x;
    private float y;

    public Cordinates()
    {
        this.x=0;
        this.y=0;
    }

    public Cordinates(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }
}
