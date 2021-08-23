package database.domain;

import java.io.File;

public class DatabasePath 
{
    String index;
    String data;
    public DatabasePath(String name, String index, String data) 
    {
        String dir = System.getProperty("user.dir") + "/data/" + name + "/";

        File folder = new File(dir);
        if (!folder.exists())
        {
            folder.mkdirs();
        }

        this.index = dir + index;
        this.data = dir + data;

    }
    public String getData() { return data; }
    public String getIndex() { return index; }
}
