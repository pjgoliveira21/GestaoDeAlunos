import java.io.Serializable;
public class AlunoGraduacao extends Aluno implements Serializable {
    private final double nota1;
    private final double nota2;
    private final double media;
    public double getNota1() {return nota1;}
    public double getNota2() {return nota2;}
    public double getMedia() {return media;}

    public AlunoGraduacao(int num, String nome, double nota1, double nota2) {
        super(num, nome);
        this.nota1=nota1;
        this.nota2=nota2;
        this.media=(nota1+nota2)/2;
    }
}
