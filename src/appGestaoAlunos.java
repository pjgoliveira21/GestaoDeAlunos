import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
public class appGestaoAlunos {
    static private final GravadorFicheiros fileOut = new GravadorFicheiros();
    static private final LeitorFicheiros fileIn = new LeitorFicheiros();
    static private final JFileChooser seletorFicheiros = new JFileChooser(".");
    static protected File ficheiroAlunos, ficheiroNotas;
    static private FileNameExtensionFilter filtroRelatorio, filtroTexto;
    static private ArrayList<Aluno> alunos;
    static private Janela janela;
    static private Relatorio relatorio=null;
    public static ArrayList<Aluno> getAlunos() {return alunos;}
    public static Relatorio getRelatorio() {return relatorio;}

    public static void main(String[] args) {
        janela = new Janela();
        validarFicheiros();
    }

    public static void validarFicheiros() {
        //Criar e aplicar o filtro de extensões ao seletor de ficheiros
        filtroRelatorio = new FileNameExtensionFilter("Ficheiro binário (.dat)","dat");
        filtroTexto = new FileNameExtensionFilter("Ficheiro de texto (.txt)","txt");
        seletorFicheiros.setAcceptAllFileFilterUsed(false);
        seletorFicheiros.setFileSelectionMode(JFileChooser.FILES_ONLY);
        seletorFicheiros.setFileFilter(filtroTexto);

        //Criar referências aos ficheiros
        ficheiroAlunos = new File("data/alunos.txt");
        ficheiroNotas = new File("data/notas.txt");

        //Verificar a existência de cada ficheiro, e se não existirem no diretório padrão, é usado o seletor de ficheiros para referenciá-los manualmente
        //Este processo é idêntico em ambos os ficheiros de texto
        if(!ficheiroAlunos.exists()){
            int escolha = JOptionPane.showOptionDialog(janela, "O ficheiro \"alunos.txt\" não existe no diretório predefinido.", "Aviso", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Encontrar Manualmente", "Encerrar Programa"}, 0);
            if(escolha==0) {
                //Referenciar manualmente
                seletorFicheiros.setDialogTitle("Abrir ficheiro de alunos");
                if (seletorFicheiros.showOpenDialog(janela) == JFileChooser.APPROVE_OPTION) {
                    //Guardar ficheiro selecionado
                    ficheiroAlunos=seletorFicheiros.getSelectedFile();
                    JOptionPane.showMessageDialog(janela, "Ficheiro importado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    //Operação interrompida
                    JOptionPane.showMessageDialog(janela,"O utilizador cancelou a operação.\n\n O programa irá encerrar.","Operação Cancelada",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            } else {
                //Encerrar
                System.exit(0);
            }
        }
        if(!ficheiroNotas.exists()) {
            int escolha = JOptionPane.showOptionDialog(janela, "O ficheiro \"notas.txt\" não existe no diretório predefinido.", "Aviso", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Encontrar Manualmente", "Encerrar Programa"}, 0);
            if (escolha == 0) {
                //Referenciar manualmente
                seletorFicheiros.setDialogTitle("Abrir ficheiro de notas");
                if (seletorFicheiros.showOpenDialog(janela) == JFileChooser.APPROVE_OPTION) {
                    //Guardar ficheiro selecionado
                    ficheiroNotas = seletorFicheiros.getSelectedFile();
                    JOptionPane.showMessageDialog(janela, "Ficheiro importado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    //Operação interrompida
                    JOptionPane.showMessageDialog(janela, "O utilizador cancelou a operação.\n\n O programa irá encerrar.", "Operação Cancelada", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            } else {
                //Encerrar
                System.exit(0);
            }
        }

        //Importar os dados dos ficheiros e guardar na lista
        alunos=fileIn.importarAlunos(ficheiroAlunos,ficheiroNotas);

        //O método importarAlunos retornará uma lista nula se existirem erros na leitura de algum dos ficheiros
        if(alunos==null)  {
            JOptionPane.showMessageDialog(janela,"Algo correu mal na leitura dos ficheiros, verifique:\n- Se os ficheiros contêm dados,\n- Se os dados estão organizados entre vírgulas:\nalunos.txt -> (numero,nome nome,grau),\nnotas.txt -> (numero,nota1,nota2),\n- Se não existem linhas em branco nos ficheiros.\n\nO programa irá encerrar.","Erro",JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    public static void gerarRelatorio() {
        //Criar uma lista nova de alunos
        ArrayList<Aluno> alunosGraduacao = new ArrayList<>();

        //Filtrar a lista principal de alunos e preencher a nova lista apenas com alunos de graduação
        for (Aluno aluno : alunos) { if (aluno.getNota1() != -1) alunosGraduacao.add(aluno); }

        //Instanciar um novo relatorio com a lista recém-criada
        relatorio = new Relatorio(alunosGraduacao);
        JOptionPane.showMessageDialog(janela, "Relatório gerado com " + relatorio.getTotalAlunos() + " alunos.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        //Guardar o objeto relatório no ficheiro
        seletorFicheiros.setDialogTitle("Selecione uma pasta para exportar o relatório");
        seletorFicheiros.setFileFilter(filtroRelatorio);
        seletorFicheiros.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (seletorFicheiros.showSaveDialog(janela) == JFileChooser.APPROVE_OPTION) fileOut.guardarRelatorio(relatorio,seletorFicheiros.getSelectedFile().getAbsolutePath());
        else {
            //Operação interrompida
            JOptionPane.showMessageDialog(janela,"O utilizador cancelou a operação.\nO relatório foi exportado no diretório da aplicação.","Operação Cancelada",JOptionPane.WARNING_MESSAGE);

            //Guardar diretório de onde está a ser executado a aplicação
            String path=String.valueOf(new File(appGestaoAlunos.class.getProtectionDomain().getCodeSource().getLocation().getPath()));

            //Retirar o noe do ficheiro do caminho
            path = path.substring(0,path.length()-16);
            fileOut.guardarRelatorio(relatorio, path);
        }
    }
    public static Relatorio carregarRelatorio() {

        //Criar e aplicar o filtro de extensões ao seletor de ficheiros
        seletorFicheiros.setFileFilter(filtroRelatorio);
        seletorFicheiros.setFileSelectionMode(JFileChooser.FILES_ONLY);
        seletorFicheiros.setDialogTitle("Abrir ficheiro de relatório");
        if (seletorFicheiros.showOpenDialog(janela) == JFileChooser.APPROVE_OPTION) {
            relatorio=fileIn.importarRelatorio(seletorFicheiros.getSelectedFile());
            if(relatorio==null) {
                //Erro na leitura
                JOptionPane.showMessageDialog(janela,"Algo correu mal na leitura do relatório, experimente outro ficheiro.","Erro",JOptionPane.WARNING_MESSAGE);
                return null;
            }
            else return relatorio;
        }
        else {
            //Operação interrompida
            JOptionPane.showMessageDialog(janela,"O utilizador cancelou a operação.","Operação Cancelada",JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }
    public static void adicionarAluno(int numero, String nome) {
        //Instanciar um novo aluno com os argumentos recebidos, adicioná-lo à lista de alunos e guardá-lo nos ficheiros de texto
        Aluno novoaluno = new Aluno(numero,nome);
        alunos.add(novoaluno);
        fileOut.guardarAluno(novoaluno,"mestrado");
    }

    //Método adicionarAluno overloaded para receber notas de alunos de graduação
    public static void adicionarAluno(int numero, String nome, double nota1,double nota2) {
        //Instanciar um novo aluno com os argumentos recebidos, adicioná-lo à lista de alunos e guardá-lo nos ficheiros de texto
        AlunoGraduacao novoaluno =new AlunoGraduacao(numero,nome,nota1,nota2);
        alunos.add(novoaluno);
        fileOut.guardarAluno(novoaluno,"graduação");
    }
}