package database.domain;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;


import database.domain.*;
import database.ext.*;

public class MainIndex {
    
    public MainIndex(TablePath path) 
    {
        this.path = path;
        
        try
        {
            this.index = new SortedList<Index>(new LinkedList<Index>());
            this.garbage = new SortedList<Garbage>(new LinkedList<Garbage>());

            File f = new File(path.get() + "index.bin");
            if(f.exists() && !f.isDirectory()) 
            { 
                RandomAccessFile index = new RandomAccessFile(path.get() + "index.bin", "r");
                int len = index.readInt();
                for(int i = 0; i < len; i++) 
                {
                    this.index.add(new Index(index.readInt(), index.readLong(), index.readInt()));
                }
                len = index.readInt();
                
                for(int i = 0; i < len; i++) 
                {
                    this.garbage.add(new Garbage(index.readInt(), index.readLong(), index.readInt()));
                }
                index.close();
                last = this.index.get(this.index.size() - 1);
            }
            else
            {
                last = new Index(0, 0, 0);
            }
        }
        catch (IOException ex)
        {
            
        }
    }
    public void close()
    {
        try
        {
            RandomAccessFile index = new RandomAccessFile(path.get() + "index.bin", "rw");
            index.writeInt(this.index.size());
            for (int i = 0; i < this.index.size(); i++) {
                Index ii = this.index.get(i);
                index.writeInt(ii.getId());
                index.writeLong(ii.getPosition());
                index.writeInt(ii.getLength());
            }
            index.writeInt(this.garbage.size());
            for (int i = 0; i < this.garbage.size(); i++) {
                Garbage ii = this.garbage.get(i);
                index.writeInt(ii.getId());
                index.writeLong(ii.getPosition());
                index.writeInt(ii.getLength());
            }
            index.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    Index last;
    TablePath path;
    SortedList<Index> index;
    SortedList<Garbage> garbage;

    public Index getLast() { return last; }
    public void setLast(Index value) { last = value; }

    public String getPath() { return path.get() + "data.bin"; }
    public SortedList<Garbage> getGarbage(){ return garbage; }
    public SortedList<Index> getIndex() { return index; }
    public void updateLast(){ last = index.get(index.size() - 1); };

    Index getPosition(int length)
    {
        // TODO repalce sequential query to something else
        
        for(int i = 0; i < garbage.size(); i++)
        {
            if(garbage.get(i).getLength() >= length)
            {
                Index ret = Index.create(garbage.get(i));
                ret.setId(last.getId() + 1);
                garbage.remove(i);
                return ret;
            }
            
        }
        Index ret = new Index(last.getId() + 1, last.getPosition() + last.getLength(), length);
        return ret;
    }
}
