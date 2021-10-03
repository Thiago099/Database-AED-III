package database.domain;

import java.io.File;

public class TablePath 
{
    String path;
    public TablePath(String name) 
    {
        String dir = System.getProperty("user.dir") + "/data/" + name + "/";

        File folder = new File(dir);
        if (!folder.exists())
        {
            folder.mkdirs();
        }
        path = dir;
    }
    public String get() { return path; }
}
