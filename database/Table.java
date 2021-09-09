package database;
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
        this.path = new TablePath(classe.getName(), "index.bin", "data.bin");
        
        this.adapter = adapter;
        try
        {
            this.index = new SortedList<Index>(new LinkedList<Index>());
            this.garbage = new SortedList<Garbage>(new LinkedList<Garbage>());

            File f = new File(path.getIndex());
            if(f.exists() && !f.isDirectory()) 
            { 
                RandomAccessFile index = new RandomAccessFile(path.getIndex(), "r");
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
            RandomAccessFile index = new RandomAccessFile(path.getIndex(), "rw");
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
    Adapter<T> adapter;
    TablePath path;
    SortedList<Index> index;
    SortedList<Garbage> garbage;

    public T get(int id)
    {
        // TODO add index keys
        Tuple<Boolean, Integer> subject = index.find(id);
        if(!subject.x) return null;
        try
        {
            RandomAccessFile file = new RandomAccessFile(path.getData(), "r");
            Index idx = index.get(subject.y);
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
        for(int i = 0; i < index.size();i++)
        {
            ret.add(get(index.get(i).getId()));
        }
        return ret;
    }


    public void delete(int id)
    {
        Tuple<Boolean, Integer> subject = index.find(id);

        if(!subject.x) return;
        Index y = index.get(subject.y);
        index.remove(subject.y);

        if(y == last)
        {
            last = this.index.get(this.index.size() - 1);
            return;
        }
        
        garbage.append(Garbage.create(y));
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
            RandomAccessFile file = new RandomAccessFile(path.getData(), "rw");
            byte[] obj = adapter.Serialize(object);
            Index current = getPosition(obj.length);
            if(object.getId() != 0)
            {
                current.setId(object.getId());
            }
            index.append(current);
            current.setLength(obj.length);
            last = this.index.get(this.index.size() - 1);
            file.seek(current.getPosition());
            file.write(obj);
            file.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

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
