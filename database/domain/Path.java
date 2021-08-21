package database.domain;

public class Path 
{
    String index;
    String data;
    public Path(String index, String data) 
    {
        this.index = index;
        this.data = data;
    }
    public String getData() { return data; }
    public String getIndex() { return index; }
}
