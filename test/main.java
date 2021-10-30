package test;

import test.adapters.LivroAdapter;
import test.domain.Livro;
import database.*;
import database.domain.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Member;
import static java.lang.System.out;

import java.util.Arrays;
import java.util.regex.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
class Main {
  
  public static void main(String[] args) 
  {
    Table<Livro> db = new Table<Livro>(Livro.class, new LivroAdapter());
    Class<?> livros = Livro.class;


    
    
        
    // db.insert(new Livro("Eu, Robô", "Isaac Asimov", 14.90F));
    // db.insert(new Livro("Eu Sou a Lenda", "Richard Matheson", 21.99F));
    
    // Livro eu_robo = db.get(1);

    // System.out.println(eu_robo);
    
    // eu_robo.setTitulo("Nós, robôs");
    // db.update(eu_robo);

    // db.insert(new Livro("Test", "test", 74.90F));
    // db.close();
    
    // System.out.println(db.get(1));
    // System.out.println(db.get(2));

    // for(Livro livro : db.get())
    // {
    //   System.out.println(livro);
    // }

    for(Livro livro : db.find("Titulo", "Eu, Robô"))
    {
      System.out.println(livro);
    }

  }
}