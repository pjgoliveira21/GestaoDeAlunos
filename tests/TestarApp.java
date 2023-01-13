import org.junit.jupiter.api.*;

class TestarApp {

    @Test
    @DisplayName("A média dos alunos é calculada corretamente")
    void calculoMedia() {
        Aluno alunoteste = new AlunoGraduacao(0,"Aluno de Teste",7,16.5);
        Assertions.assertEquals(11.75,alunoteste.getMedia());
    }
}
