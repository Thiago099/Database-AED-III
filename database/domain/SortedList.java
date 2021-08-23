package database.domain;
import java.util.*;

import database.ext.Comparable;

public class SortedList<T extends Comparable<T>>
{
    List<T> list;
    public SortedList(List<T> list) 
    {
        this.list = list;
    }

    public int size()
    {
        return list.size();
    }

    public void add(T item) 
    {
        list.add(item);
    }
    
    public T get(int id)
    {
        return list.get(id);
    }

    public void remove(T item)
    {
        Vector<Boolean, Integer> res = find(item);
        if(res.x)
        {
            list.remove(res.y);
        }
    }

    public void remove(int id)
    {
        list.remove(id);
    }

    public void append(T item) 
    {
        int l = 0;
        int r = list.size() - 1;

        int c = 0;

        var com = 0;
        while (l <= r)
        {
            c = (l + r) / 2;
            com = list.get(c).compareTo(item);
            if (com > 0) r = c - 1;
            else if (com < 0) l = c + 1;
            else
            {
                list.add(c,item);
            }
        }

        if (c >= list.size())
            list.add(item);

        else
        {
            if (com < 0) c++;
            list.add(c, item);
        }

    }

    public Vector<Boolean, T> find(int id)
    {
        int l = 0;
        int r = list.size() - 1;

        int c = 0;
        while (l <= r)
        {
            c = (l + r) / 2;
            var com = list.get(c).compareTo(id);
            if (com > 0) r = c - 1;
            else if (com < 0) l = c + 1;
            else
            {
                return new Vector<Boolean, T>(true, list.get(c));
            }
        }
        return new Vector<Boolean, T>(false, list.get(c));
    }

    public Vector<Boolean, Integer> find(T element)
    {
        int l = 0;
        int r = list.size() - 1;

        int c = 0;
        while (l <= r)
        {
            c = (l + r) / 2;
            var com = list.get(c).compareTo(element);
            if (com > 0) r = c - 1;
            else if (com < 0) l = c + 1;
            else
            {
                return new Vector<Boolean, Integer>(true, c);
            }
        }
        return new Vector<Boolean, Integer>(false, c);
    }
}
