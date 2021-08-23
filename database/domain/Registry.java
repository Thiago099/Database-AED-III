package database.domain;

public class Registry 
{
    public Registry(int id, long position, int length) 
    {
        this.id = id;
        this.position = position;
        this.length = length;
    }
    protected int id;
    protected long position;
    protected int length;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public long getPosition() { return position; }
    public void setPosition(long position) { this.position = position; }
    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }
}
