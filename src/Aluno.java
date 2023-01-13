import java.io.Serializable;

public class Aluno implements Serializable{
    public String getNome() {
        return nome;
    }

    public int getNum() {
        return num;
    }

    public double getNota1() {return -1;}
    public double getNota2() {return -1;}
    public double getMedia() {return -1;}

    private final String nome;
    private final int num;

    public Aluno(int num,String nome) {
        this.nome = nome;
        this.num = num;
    }
}
