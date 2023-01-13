import java.io.Serializable;
import java.util.ArrayList;
public class Relatorio implements Serializable {
    private final ArrayList<Aluno> alunos;
    private final int totalAlunos;
    private final double mediaTurma;
    public ArrayList<Aluno> getAlunos() {return alunos;}
    public int getTotalAlunos() {return totalAlunos;}
    public double getMediaTurma() {return mediaTurma;}

    public Relatorio(ArrayList<Aluno> alunos) {
        this.alunos=alunos;
        this.totalAlunos = alunos.size();
        this.mediaTurma=this.calcularMedia();
    }
    private double calcularMedia() {
        double total=0;
        for (Aluno aluno : alunos) {
            total+=aluno.getMedia();
        }
        //arredondar para 2 casas decimais
        return Math.round((total/alunos.size())*100.0)/100.0;
    }
}
