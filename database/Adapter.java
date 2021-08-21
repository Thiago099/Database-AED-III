package database;

import java.io.IOException;

public interface Adapter<T>
{
    public byte[] Serialize(T data) throws IOException;
    public T Deserialize(byte[] data) throws IOException;
}
