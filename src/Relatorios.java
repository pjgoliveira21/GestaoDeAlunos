import javax.swing.*;
import java.util.Objects;

public class Relatorios {
    protected JPanel rootPanel,cabecalho,imagem,botoes,rodape;
    private JLabel cl1, img,cl2,rl1;
    protected JButton gerarRelatorio,carregarRelatorio, voltar;
    public Relatorios() {
        img.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("report.png"))));
    }
}
