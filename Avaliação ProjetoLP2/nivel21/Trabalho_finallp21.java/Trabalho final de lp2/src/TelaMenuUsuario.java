// Importa classes graficas
import java.awt.*;
import javax.swing.*;

public class TelaMenuUsuario extends JFrame {
    private Usuario usuario;

    // Construtor
    public TelaMenuUsuario(Usuario usuario) {
        this.usuario = usuario;

        // Configuracoes da janela
        setTitle("Menu Usuário - " + usuario.getNome());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Titulo
        JLabel titulo = criarLabelTitulo("Menu do Usuário");
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(new Color(40, 40, 40));
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        painelTitulo.add(titulo, BorderLayout.CENTER);
        add(painelTitulo, BorderLayout.NORTH);

        // Botoes
        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        painelBotoes.setBackground(new Color(40, 40, 40));

        JButton btnReservarEspaco = criarBotao("Reservar Espaço");
        JButton btnRelatorios = criarBotao("Relatórios");
        JButton btnSair = criarBotao("Sair");

        painelBotoes.add(btnReservarEspaco);
        painelBotoes.add(btnRelatorios);
        painelBotoes.add(btnSair);
        add(painelBotoes, BorderLayout.CENTER);

        // Acoes dos botoes
        btnReservarEspaco.addActionListener(e -> new TelaReserva(usuario).setVisible(true));
        btnRelatorios.addActionListener(e -> new TelaRelatorios().setVisible(true));
        btnSair.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
    }

    // Cria título com estilo
    private JLabel criarLabelTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        return label;
    }

    // Cria botao com estilo escuro
    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}
