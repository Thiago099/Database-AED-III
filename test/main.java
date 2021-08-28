package test;

import test.adapters.LivroAdapter;
import test.domain.Livro;
import database.*;

class Main {
  public static void main(String[] args) 
  {
    Table<Livro> db = new Table<Livro>(Livro.class, new LivroAdapter());
    // db.insert(new Livro("Eu, Robô", "Isaac Asimov", 14.90F));
    // db.insert(new Livro("Eu Sou a Lenda", "Richard Matheson", 21.99F));
    
    // Livro eu_robo = db.get(1);
    
    // eu_robo.setPreco(25.95f);
    // db.update(eu_robo);
    // db.close();
    
    // System.out.println(db.get(1));
    // System.out.println(db.get(2));

    for(Livro livro : db.getAll())
    {
      System.out.println(livro);
    }

  }
}