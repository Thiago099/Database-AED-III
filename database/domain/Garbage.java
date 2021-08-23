package database.domain;
import database.ext.Comparable;

public class Garbage extends Registry implements Comparable<Garbage> 
{
    public Garbage(int id, long position, int length) 
    {
        super(id, position, length);
    }
    
    @Override
    public int compareTo(Garbage o) 
    {
        return this.length - o.length;
    }
    
    @Override
    public int compareTo(int o) 
    {
        return this.length - o;
    }

    public static Garbage create(Index i)
    {
        return new Garbage(i.getId(), i.getPosition(), i.getLength());
    }

    
}
