package test.adapters;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import database.Adapter;
import test.domain.Livro;

public class LivroAdapter implements Adapter<Livro>
{
    public byte[] Serialize(Livro l) throws IOException 
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(l.titulo);
        dos.writeUTF(l.autor);
        dos.writeFloat(l.preco);
        return baos.toByteArray();
      }
    
      public Livro Deserialize(byte[] ba) throws IOException 
      {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        var l = new Livro();
        l.titulo = dis.readUTF();
        l.autor = dis.readUTF();
        l.preco = dis.readFloat();
        return l;
      }
    
}
