package database.domain;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import database.domain.*;
import database.ext.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Member;

public class Table<T extends Identified> 
{
    public Table(Class<?> classe, Adapter<T> adapter) 
    {
        this.adapter = adapter;
        this.path = new TablePath(classe.getName());
        this.classe = classe;
        this.index = new MainIndex(path);
        
        Pattern pattern = Pattern.compile("get.+");
        Method[] methos = Arrays.stream(classe.getMethods()).filter(new Predicate<Method>() {
            public boolean test(Method obj) {
              String name = obj.getName();
              boolean matches = pattern.matcher(name).matches();
              return matches ? name != "getClass" : false;
            }}).toArray(Method[]::new);
          
          this.methods = new HashMap<String, Method>();
          for (Method method : methos) 
          {
              this.methods.put(method.getName().replaceAll("get", ""), method);
          }
    }

    public List<T> find(String field, Object value)
    {
        List<T> result = new LinkedList<T>();
        Method criterion = methods.get(field);
        for(Index current : index.getIndex().getList())
        {
            try
            {
                RandomAccessFile file = new RandomAccessFile(index.getPath(), "r");
                file.seek(current.getPosition());
                byte[] data = new byte[current.getLength()];
                file.read(data);
                file.close();
                T ret = adapter.Deserialize(data);
                file.close();
                if(criterion.invoke(ret).equals(value))
                {
                    result.add(ret);
                }
            }
            catch(Exception ex)
            {

            }
        }
        return result;
    }

    
    public void close()
    {
        index.close();
    }
    
    Map<String, Method> methods;
    MainIndex index;
    Adapter<T> adapter;
    TablePath path;
    Class<?> classe;
    

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
