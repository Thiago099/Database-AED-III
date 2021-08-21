package test;
import java.io.RandomAccessFile;

import test.adapters.LivroAdapter;
import test.domain.Livro;

import java.io.IOException;

class Main {
  public static void main(String[] args) {
    Livro l1 = new Livro(1, "Eu, Robô", "Isaac Asimov", 14.90F);
    Livro l2 = new Livro(2, "Eu Sou a Lenda", "Richard Matheson", 21.99F);

    RandomAccessFile arq;
    byte ba[];

    try {

      // ESCRITA
      arq = new RandomAccessFile("livros.bin", "rw");

      long pos1 = arq.getFilePointer();
      ba = LivroAdapter.toByteArray(l1);
      arq.writeInt(ba.length);
      arq.write(ba);

      long pos2 = arq.getFilePointer();
      ba = LivroAdapter.toByteArray(l2);
      arq.writeInt(ba.length);
      arq.write(ba);

      // LEITURA
      int tam;
      
      arq.seek(pos1);
      tam = arq.readInt();
      ba = new byte[tam];
      arq.read(ba);
      var l4 = LivroAdapter.fromByteArray(ba);
      
      arq.seek(pos2);
      tam = arq.readInt();
      ba = new byte[tam];
      arq.read(ba);
      var l3 = LivroAdapter.fromByteArray(ba);


      System.out.println(l3);
      System.out.println(l4);

      arq.close();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}