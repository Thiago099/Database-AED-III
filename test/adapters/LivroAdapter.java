package test.adapters;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import database.ext.Adapter;
import test.domain.Livro;

public class LivroAdapter implements Adapter<Livro>
{
    public byte[] Serialize(Livro l) throws IOException 
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(l.getTitulo());
        dos.writeUTF(l.getAutor());
        dos.writeFloat(l.getPreco());
        return baos.toByteArray();
      }
    
      public Livro Deserialize(byte[] ba) throws IOException 
      {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        var l = new Livro();
        l.setTitulo(dis.readUTF());
        l.setAutor(dis.readUTF());
        l.setPreco(dis.readFloat());
        return l;
      }
    
}
