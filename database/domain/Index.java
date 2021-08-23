package database.domain;
import database.ext.Comparable;

public class Index extends Registry implements Comparable<Index> 
{
    public Index(int id, long position, int length) 
    {
        super(id, position, length);
    }

    @Override
    public int compareTo(Index o) 
    {
        return this.id - o.id;
    }
    
    @Override
    public int compareTo(int o) 
    {
        return this.id - o;
    }

    public static Index create(Garbage i)
    {
        return new Index(i.getId(), i.getPosition(), i.getLength());
    }
}
