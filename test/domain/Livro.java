package test.domain;
import java.text.DecimalFormat;
import database.ext.*;

public class Livro implements Identified 
{
  int id;
  String titulo;
  String autor;
  float preco;

  @Override
  public int getId() { return id; }

  @Override
  public void setId(int id) { this.id = id; }
  
  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) {this.titulo = titulo;}

  public String getAutor() { return autor; }
  public void setAutor(String autor) { this.autor = autor;}
  
  public float getPreco() { return preco; }
  public void setPreco(float preco) { this.preco = preco;}

  public Livro(String titulo, String autor, float preco) {
    this.titulo = titulo;
    this.autor = autor;
    this.preco = preco;
  }
  public Livro() {
  }

  public String toString() {
    DecimalFormat df = new DecimalFormat("#,##0.00");

    return 
      "\nId: " + this.id + 
      "\nTítulo: " + this.titulo + 
      "\nAutor.: " + this.autor +
      "\nPreço.: R$ " + df.format(this.preco);
  }
}