package picasso.picassoapp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jl on 4/5/16.
 */
public class Drawing
{
    //this is the drawing class, where all the drawings are stored and sent
    private String name;
    private List<Coordinates> coord;

    public Drawing()
    {
        name = "";
        coord = new ArrayList<>();
    }

    public Drawing(String name)
    {
        this.name = name;
        coord = new ArrayList<>();
        coord.add(new Coordinates(0,0));
    }

    public void copyDrawing(Drawing copy)
    {
        this.name = copy.getName();
        this.coord = copy.getCord();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setCoord(List<Coordinates> coord)
    {
        this.coord = coord;
    }

    public List<Coordinates> getCord()
    {
        return this.coord;
    }

    public void addCord(Coordinates xy)
    {
        coord.add(xy);
    }

    public void delete()
    {
        coord = new ArrayList<>();
    }
}
