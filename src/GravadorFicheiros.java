import java.io.*;
public class GravadorFicheiros {
    public GravadorFicheiros() {}
    public void guardarRelatorio(Relatorio rel, String path) {
        try{
            ObjectOutputStream gravadorObjetos = new ObjectOutputStream( new FileOutputStream(path+"\\relatorio.dat"));
            //Gravar objeto no ficheiro
            gravadorObjetos.writeObject(rel);
            gravadorObjetos.close();
        } catch (IOException e) {
            System.out.println("Erro -> "+e);
        }
    }
    public void guardarAluno(Aluno aluno, String grau) {
        try{
            BufferedWriter gravadorTexto = new BufferedWriter(new FileWriter(appGestaoAlunos.ficheiroAlunos,true));
            gravadorTexto.newLine();

            //Gravar os dados do aluno formatados
            gravadorTexto.write(aluno.getNum()+","+aluno.getNome()+","+grau);

            gravadorTexto.close();

            if(grau.equals("graduação")) {
                gravadorTexto = new BufferedWriter(new FileWriter(appGestaoAlunos.ficheiroNotas,true));
                gravadorTexto.newLine();

                //Gravar no ficheiro das notas
                gravadorTexto.write(aluno.getNum() + "," + aluno.getNota1() + "," + aluno.getNota2());

                gravadorTexto.close();
            }
        } catch(IOException ioe) {
            System.out.println("IO Exception -> "+ioe);
        }
    }

}

