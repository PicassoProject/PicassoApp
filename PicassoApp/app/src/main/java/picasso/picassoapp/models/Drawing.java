package picasso.picassoapp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jl on 4/5/16.
 */
public class Drawing
{
    private String name;
    private List<Cordinates> cord;

    public Drawing()
    {
        name = "";
        cord = new ArrayList<>();
    }

    public void copyDrawing(Drawing copy)
    {
        this.name = copy.getName();
        this.cord = copy.getCord();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setCoord(List<Cordinates> coord)
    {
        this.cord = coord;
    }

    public List<Cordinates> getCord()
    {
        return this.cord;
    }

    public void addCord(Cordinates xy)
    {
        cord.add(xy);
    }
}
