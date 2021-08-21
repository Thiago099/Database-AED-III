package database;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.File;
import java.util.*;

import database.domain.*;

public class Database<T> {
    public Database(String name, Adapter<T> adapter) {
        this.path = new Path("database/" + name + "/index.bin",  "database/" + name  + "/data.bin");
        this.adapter = adapter;
        try
        {
            this.id = new ArrayList<Index>();
            File f = new File(path.getIndex());
            if(f.exists() && !f.isDirectory()) 
            { 
                RandomAccessFile idx = new RandomAccessFile(path.getIndex(), "r");
                int len = idx.readInt();
                for(int i = 0; i < len; i++) 
                {
                    id.add(new Index(idx.readInt(), idx.readInt()));
                }
                idx.close();
            }
        }
        catch (IOException ex)
        {
            
        }
    }
    private Adapter<T> adapter;
    private Path path;
    private List<Index> id;

    public void Add(T object)
    {
        try
            {
                RandomAccessFile file = new RandomAccessFile(path.getData(), "rw");
                file.write(adapter.Serialize(object));
                file.close();
            }
            catch(IOException ex)
            {

            }
    }
}
