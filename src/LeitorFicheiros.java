import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class LeitorFicheiros {
    public LeitorFicheiros() {}
    public ArrayList<Aluno> importarAlunos(File ficheiroAlunos, File ficheiroNotas) {
        //Declarar uma lista de alunos que mais tarde vai ser retornada como resultado da função
        ArrayList<Aluno> listaAlunos = new ArrayList<>();
        try {
            Scanner scanneralunos = new Scanner(ficheiroAlunos);
            Scanner scannernotas = new Scanner(ficheiroNotas);
            do {
                //Ler linha a linha
                String linha = scanneralunos.nextLine();

                //Separando os valores da linha a partir da divisão por virgulas
                String[] dados = linha.split(",");

                //Tratamento de dados
                int num = Integer.parseInt(dados[0]);
                String nome = dados[1];
                String grau = dados[2];

                if (grau.equals("graduação")) {

                    //Associar aluno lido à sua nota do outro ficheiro
                    //Enquanto houver notas para ler no ficheiro notas.txt, encontrar associação entre o número das notas e o numero do aluno lido nesta iteração
                    do {
                        String linhaNotas = scannernotas.nextLine();
                        String[] dadosNotas = linhaNotas.split(",");
                        double nota1 = Double.parseDouble(dadosNotas[1]);
                        double nota2 = Double.parseDouble(dadosNotas[2]);
                        int notasNum = Integer.parseInt(dados[0]);
                        if(notasNum==num) {
                            listaAlunos.add(new AlunoGraduacao(num,nome,nota1,nota2));
                            //O loop vai parar quando for encontrada uma correspondência
                            break;
                        }
                    } while(true);
                }
                else if(grau.equals("mestrado")) listaAlunos.add(new Aluno(num,nome));

                //Se o grau não for nem graduação nem mestrado, algo está errado com os ficheiros, portanto é lançado um erro que será interpretado na função principal
                else throw new Exception();

            } while (scanneralunos.hasNext());
            scanneralunos.close();
            scannernotas.close();
            return listaAlunos;
        } catch (Exception e) {return null;}
    }
    public Relatorio importarRelatorio(File rel) {
        Relatorio relatorioImportado;
        try {
            //Tentar obter um objeto do tipo Relatorio a partir do ficheiro argumentado
            ObjectInputStream inp = new ObjectInputStream(new FileInputStream(rel));
            relatorioImportado = (Relatorio) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        //Retorná-lo
        return relatorioImportado;
    }
}
