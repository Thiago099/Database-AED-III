package database.domain;
import java.io.File;
import database.ext.Comparable;
import database.ext.Hasher;

public class HashIndex<T> implements Comparable<HashIndex<T>>  
{
    public HashIndex(TablePath path, Hasher<T> hasher, String name)
    {
        this.hasher = hasher;
        this.name = name;
    }
    String name;


    public int get(T value)
    {
        return hasher.Hash(value);
    }

    Hasher<T> hasher;

    @Override
    public int compareTo(HashIndex o) {
        return name.compareTo(o.name);
    }

    //SOLID violation
    @Override
    public int compareTo(int a) {
        return 0;
    }
}
