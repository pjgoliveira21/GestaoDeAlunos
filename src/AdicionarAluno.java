import javax.swing.*;
import java.util.Objects;

public class AdicionarAluno {
    protected JPanel rootPanel,imagem,dados,rodape,cabecalho;
    protected JLabel img,rl1,cl1,cl2,nota1l,nota2l,numerol,nomel,graul;
    protected JTextField numero,nome,nota1,nota2;
    protected JComboBox<String> comboGrau;
    protected JButton voltar,submeter;

    public AdicionarAluno() {
        img.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("student.png"))));
        comboGrau.addItem("Graduação");
        comboGrau.addItem("Mestrado");

        comboGrau.addItemListener(e -> {
            //Permitir a ativação/desativação do campo das notas mediante o grau selecionado
            nota1.setEnabled(comboGrau.getSelectedIndex()==0);
            nota2.setEnabled(comboGrau.getSelectedIndex()==0);
            nota1l.setEnabled(comboGrau.getSelectedIndex()==0);
            nota2l.setEnabled(comboGrau.getSelectedIndex()==0);
        });
    }
}
