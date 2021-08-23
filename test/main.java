package test;
import java.io.RandomAccessFile;

import test.adapters.LivroAdapter;
import test.domain.Livro;
import database.*;

class Main {
  public static void main(String[] args) {
    Table<Livro> db = new Table<Livro>("livro", new LivroAdapter());
    // db.insert(new Livro("Eu, Robô", "Isaac Asimov", 14.90F));
    // db.insert(new Livro("Eu Sou a Lenda", "Richard Matheson", 21.99F));
    // db.close();

    // System.out.println(db.get(1));
    // System.out.println(db.get(2));

    for(Livro livro : db.getAll())
    {
      System.out.println(livro);
    }

  }
}