package database.domain;

import java.io.File;

public class TablePath 
{
    String index;
    String data;
    public TablePath(String name) 
    {
        String dir = System.getProperty("user.dir") + "/data/" + name + "/";

        File folder = new File(dir);
        if (!folder.exists())
        {
            folder.mkdirs();
        }

        this.index = dir + "index.bin";
        this.data = dir +  "data.bin";

    }
    public String getData() { return data; }
    public String getIndex() { return index; }
}
