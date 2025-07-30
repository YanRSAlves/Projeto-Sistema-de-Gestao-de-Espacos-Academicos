import javax.swing.*;
import java.awt.*;

public class TelaNovaReserva extends JDialog {
    private JTextField campoData, campoHoraInicio, campoHoraFim;
    private JButton botaoConfirmar;
    private Espaco espaco;
    private Usuario usuario;
    private boolean reservaFeita = false;

    public TelaNovaReserva(Frame owner, Espaco espaco, Usuario usuario) {
        super(owner, "Fazer Reserva para: " + espaco.getNome(), true);
        this.espaco = espaco;
        this.usuario = usuario;

        setSize(400, 220);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        // --- Painel do Formulário ---
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Linha Data
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Data (dd/mm/yyyy):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        campoData = new JTextField(10);
        painelFormulario.add(campoData, gbc);

        // Linha Hora Início
        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        painelFormulario.add(new JLabel("Hora Início (HH:MM):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        campoHoraInicio = new JTextField(10);
        painelFormulario.add(campoHoraInicio, gbc);

        // Linha Hora Fim
        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        painelFormulario.add(new JLabel("Hora Fim (HH:MM):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        campoHoraFim = new JTextField(10);
        painelFormulario.add(campoHoraFim, gbc);

        // --- Botão de Confirmação ---
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoConfirmar = new JButton("Confirmar Reserva");
        painelBotao.add(botaoConfirmar);

        add(painelFormulario, BorderLayout.CENTER);
        add(painelBotao, BorderLayout.SOUTH);

        // --- Ação do Botão ---
        botaoConfirmar.addActionListener(e -> confirmarReserva());
    }

    private void confirmarReserva() {
        String data = campoData.getText().trim();
        String horaInicio = campoHoraInicio.getText().trim();
        String horaFim = campoHoraFim.getText().trim();

        if (data.isEmpty() || horaInicio.isEmpty() || horaFim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Utiliza o mesmo método de verificação de conflito do console
        boolean conflito = false;
        for (Reserva r : ReservaDAO.listarReservas()) {
            if (r.getIdEspaco() == espaco.getId() && r.getData().equals(data)) {
                if (horariosConflitam(horaInicio, horaFim, r.getHoraInicio(), r.getHoraFim())) {
                    conflito = true;
                    break;
                }
            }
        }

        if (conflito) {
            JOptionPane.showMessageDialog(this, "Já existe uma reserva para este espaço no horário selecionado.", "Conflito de Horário", JOptionPane.WARNING_MESSAGE);
        } else {
            Reserva novaReserva = new Reserva(0, usuario.getId(), espaco.getId(), data, horaInicio, horaFim);
            ReservaDAO.adicionarReserva(novaReserva);
            LogDAO.registrar(usuario.getId(), "reservou o espaço ID " + espaco.getId() + " via GUI");
            JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!");
            this.reservaFeita = true;
            dispose();
        }
    }

    // Copiamos o método de verificação para cá também
    private boolean horariosConflitam(String inicio1, String fim1, String inicio2, String fim2) {
        return inicio1.compareTo(fim2) < 0 && fim1.compareTo(inicio2) > 0;
    }

    public boolean isReservaFeita() {
        return this.reservaFeita;
    }
}