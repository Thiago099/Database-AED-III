package test.domain;
import java.text.DecimalFormat;
import database.ext.*;

public class Livro implements Identified {
  int id;
  String titulo;
  String autor;
  float preco;

  @Override
  public void setId(int id) { this.id = id; }
  
  @Override
  public int getId() { return id; }

  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) {this.titulo = titulo;}

  public String getAutor() { return autor; }
  public float getPreco() { return preco; }
  
  public void setPreco(float preco) { this.preco = preco;}
  public void setAutor(String autor) { this.autor = autor;}

  public Livro(String t, String a, float p) {
    this.titulo = t;
    this.autor = a;
    this.preco = p;
  }

  public Livro() {
    this.titulo = "";
    this.autor = "";
    this.preco = 0F;
  }

  public String toString() {
    DecimalFormat df = new DecimalFormat("#,##0.00");

    return 
    "\nTítulo: " + this.id + 
    "\nTítulo: " + this.titulo + 
    "\nAutor.: " + this.autor +
     "\nPreço.: R$ " + df.format(this.preco);
  }



  
}