package database.ext;

public interface Hasher<T> 
{
    public int Hash(T value); 
}
