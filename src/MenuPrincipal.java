import javax.swing.*;
import java.util.Objects;

public class MenuPrincipal {
    protected JPanel rootPanel,cabecalho,imagem,botoes,rodape;
    protected JLabel cl1, img,cl2,rl1;
    protected JButton relatoriosButton,adicionarAlunoButton,sairButton;
    public MenuPrincipal() {
        img.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("logotipo.png"))));
    }
}
