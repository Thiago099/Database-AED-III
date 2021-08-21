package test.domain;
import java.text.DecimalFormat;

public class Livro {
  public String titulo;
  public String autor;
  public float preco;

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

    return "\nTítulo: " + this.titulo + "\nAutor.: " + this.autor + "\nPreço.: R$ "
        + df.format(this.preco);
  }

  
}