
// Menu para admin com opcoes de cadastro, reserva, relatorios, usuarios e logout.

import java.awt.*;
import javax.swing.*;

public class TelaMenuAdmin extends JFrame {
    private Usuario usuario;

    public TelaMenuAdmin(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Menu Admin - " + usuario.getNome());
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Titulo no topo
        JLabel titulo = criarLabelTitulo("Menu Administrativo");
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(new Color(40, 40, 40));
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        painelTitulo.add(titulo, BorderLayout.CENTER);
        add(painelTitulo, BorderLayout.NORTH);

        // Painel central com botoes (GridLayout para manter tamanho uniforme)
        JPanel painelBotoes = new JPanel(new GridLayout(5, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        painelBotoes.setBackground(new Color(40, 40, 40));

        JButton btnCadastrarEspaco = criarBotao("Cadastrar Espaço");
        JButton btnReservarEspaco = criarBotao("Reservar Espaço");
        JButton btnRelatorios = criarBotao("Relatórios");
        JButton btnGerenciarUsuarios = criarBotao("Gerenciar Usuários");
        JButton btnSair = criarBotao("Sair");

        painelBotoes.add(btnCadastrarEspaco);
        painelBotoes.add(btnReservarEspaco);
        painelBotoes.add(btnRelatorios);
        painelBotoes.add(btnGerenciarUsuarios);
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.CENTER);

        // Acoes dos botoes
        btnCadastrarEspaco.addActionListener(e -> {
            TelaCadastroEspaco telaCadastroEspaco = new TelaCadastroEspaco();
            telaCadastroEspaco.setVisible(true);
        });

        btnReservarEspaco.addActionListener(e -> {
            TelaReserva telaReserva = new TelaReserva(usuario);
            telaReserva.setVisible(true);
        });

        btnRelatorios.addActionListener(e -> {
            TelaRelatorios telaRelatorios = new TelaRelatorios();
            telaRelatorios.setVisible(true);
        });

        btnGerenciarUsuarios.addActionListener(e -> {
            TelaGerenciarUsuarios telaGerenciarUsuarios = new TelaGerenciarUsuarios();
            telaGerenciarUsuarios.setVisible(true);
        });

        btnSair.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
    }

    private JLabel criarLabelTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}
