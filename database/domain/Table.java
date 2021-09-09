package database.domain;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;


import database.domain.*;
import database.ext.*;

public class Table<T extends Identified> 
{
    public Table(Class<T> classe, Adapter<T> adapter) 
    {
        this.adapter = adapter;
        this.index = new MainIndex(classe.getName());
    }

    
    public void close()
    {
        index.close();
    }
    
    MainIndex index;
    Adapter<T> adapter;
    

    public T get(int id)
    {
        // TODO add index keys
        Tuple<Boolean, Integer> subject = index.getIndex().find(id);
        if(!subject.x) return null;
        try
        {
            RandomAccessFile file = new RandomAccessFile(index.getPath(), "r");
            Index idx = index.getIndex().get(subject.y);
            file.seek(idx.getPosition());
            byte[] data = new byte[idx.getLength()];
            file.read(data);
            file.close();
            T ret = adapter.Deserialize(data);
            ret.setId(id);
            file.close();
            return ret;
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    public List<T> get()
    {
        List<T> ret = new LinkedList<T>();
        for(int i = 0; i < index.getIndex().size();i++)
        {
            ret.add(get(index.getIndex().get(i).getId()));
        }
        return ret;
    }


    public void delete(int id)
    {
        Tuple<Boolean, Integer> subject = index.getIndex().find(id);

        if(!subject.x) return;
        Index y = index.getIndex().get(subject.y);
        index.getIndex().remove(subject.y);

        if(y == index.getLast())
        {
            index.updateLast();
            return;
        }
        
        index.getGarbage().append(Garbage.create(y));
    }

    public void insert(T object)
    {
        write(object);
    }

    public void update(T object)
    {
        // TODO optimize: deleting and re-adding the same item from the index list
        delete(object.getId());
        write(object);
    }

    void write(T object)
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile(index.getPath(), "rw");
            byte[] obj = adapter.Serialize(object);
            Index current = index.getPosition(obj.length);
            if(object.getId() != 0)
            {
                current.setId(object.getId());
            }
            index.getIndex().append(current);
            current.setLength(obj.length);
            index.updateLast();
            file.seek(current.getPosition());
            file.write(obj);
            file.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
