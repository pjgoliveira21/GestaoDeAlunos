import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Janela extends JFrame {
    private JPanel painelPrincipal;
    private final JFrame frame = this;
    private MenuPrincipal menuprincipal;
    private Relatorios relatorios;
    private AdicionarAluno addaluno;

    public Janela() {

        //Obter os paineis de cada menu
        this.obterPaineis();

        //Icone, titulo, tamanho, posição, evento de fecho, propriedades e atributos
        this.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icon.png"))).getImage());
        this.setTitle("Gestão de Alunos");
        this.setSize(new Dimension(720, 480));
        this.setLocation(500,250);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Aplicar os listeners aos botões e outros componentes de todos os menus
        this.aplicarListeners();

        //Mudar a janela para o menu principal no inicio do programa
        this.mudarJanela(menuprincipal.rootPanel);
        this.setVisible(true);
    }

    private void aplicarListeners() {
        this.relatorios.voltar.addActionListener(e -> mudarJanela(menuprincipal.rootPanel));
        this.addaluno.voltar.addActionListener(e -> mudarJanela(menuprincipal.rootPanel));
        this.menuprincipal.relatoriosButton.addActionListener(e -> mudarJanela(relatorios.rootPanel));
        this.menuprincipal.adicionarAlunoButton.addActionListener(e -> mudarJanela(addaluno.rootPanel));
        this.menuprincipal.sairButton.addActionListener(e -> System.exit(0));

        this.addaluno.submeter.addActionListener(e -> {
            //Validar número
            int numaluno;
            try {
                numaluno = Integer.parseInt(this.addaluno.numero.getText());
                //Iterar pela lista de alunos e verificar numeros duplicados
                for (Aluno alunoIterado : appGestaoAlunos.getAlunos()) {
                    if (alunoIterado.getNum() == numaluno) {
                        JOptionPane.showMessageDialog(frame,"Já existe um aluno com o número que inseriu.","Erro",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,"O número de aluno que inseriu não é válido","Erro",JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Validar nome
            //Verificar string em branco
            String nomealuno = this.addaluno.nome.getText();
            if(nomealuno.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame,"O campo nome de aluno não pode ficar em branco","Erro",JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Verificar se o nome é composto apenas por letras
            for (int i = 0; i < nomealuno.length(); i++) {
                if ((!(nomealuno.charAt(i)==' ') && !Character.isLetter(nomealuno.charAt(i)))) {
                    JOptionPane.showMessageDialog(frame,"O nome de aluno que inseriu não é válido","Erro",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            //Aplicar grau a partir de um operador ternário
            String grau = this.addaluno.comboGrau.getSelectedIndex()==0 ? "graduação" : "mestrado";

            //Adicionar aluno de graduação
            double nota1,nota2;
            if(grau.equals("graduação")) {
                try {
                    nota1= Double.parseDouble(this.addaluno.nota1.getText());
                    nota2= Double.parseDouble(this.addaluno.nota2.getText());

                    //Verificar se as notas estão dentro dos limites
                    if((nota1<0 || nota1>20) || (nota2<0 || nota2>20)) throw new Exception();
                } catch (Exception ex) {
                    //Este catch será chamado se as notas não forem numéricas ou estiverem fora dos limites
                    JOptionPane.showMessageDialog(frame,"A(s) nota(s) inserida(s) não são válida(s)","Erro",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                //Enviar a informação à função principal
                appGestaoAlunos.adicionarAluno(numaluno,nomealuno,nota1,nota2);
            }
            //Adicionar aluno de mestrado
            else appGestaoAlunos.adicionarAluno(numaluno,nomealuno);
            JOptionPane.showMessageDialog(frame,nomealuno+" adicionado aos alunos.","Sucesso",JOptionPane.INFORMATION_MESSAGE);
        });

        this.relatorios.gerarRelatorio.addActionListener(e -> appGestaoAlunos.gerarRelatorio());

        this.relatorios.carregarRelatorio.addActionListener(e -> {
            if(appGestaoAlunos.carregarRelatorio()!=null) mostrarRelatorio(appGestaoAlunos.getRelatorio());
        });
    }
    private void obterPaineis() {
        menuprincipal = new MenuPrincipal();
        relatorios = new Relatorios();
        addaluno = new AdicionarAluno();
    }

    public void mudarJanela(JPanel janela) {
        this.setContentPane(janela);
        this.revalidate();
    }

    public void mostrarRelatorio(Relatorio rel) {
        String cabecalho = "--------------------------------------------------------------------\nNotas dos alunos de Programação 2022/2023\nProfa. Valéria Pequeno\n--------------------------------------------------------------------\n";

        //É utilizada uma StringBuilder que permite exibir o relatório da forma desejada
        StringBuilder listaalunos= new StringBuilder();
        for (int i = 0; i < rel.getTotalAlunos(); i++) {
            if(rel.getAlunos().get(i) instanceof AlunoGraduacao) {
                listaalunos.append(rel.getAlunos().get(i).getNum());
                listaalunos.append(" ");
                listaalunos.append(rel.getAlunos().get(i).getNome());
                listaalunos.append(" ");
                listaalunos.append(rel.getAlunos().get(i).getMedia());
                listaalunos.append("\n");
            }
        }
        String rodape = "--------------------------------------------------------------------\nTotal de alunos: "+rel.getTotalAlunos()+"     Média da turma: "+rel.getMediaTurma();
        String relatorio=cabecalho+listaalunos+rodape;
        JOptionPane.showMessageDialog(frame,relatorio,"Relatório",JOptionPane.INFORMATION_MESSAGE);
    }
}
