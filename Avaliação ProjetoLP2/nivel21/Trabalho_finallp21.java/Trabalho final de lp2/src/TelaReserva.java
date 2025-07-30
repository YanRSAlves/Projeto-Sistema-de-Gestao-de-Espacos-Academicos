import java.awt.*;
import java.util.List;
import java.util.UUID;
import javax.swing.*;

public class TelaReserva extends JFrame {
    private Usuario usuario;
    private JComboBox<Espaco> comboEspacos;
    private JTextField campoData, campoHoraInicio, campoHoraFim;
    private JButton btnReservar, btnCancelar;

    public TelaReserva(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Reserva de Espaço - " + usuario.getNome());
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(40, 40, 40));

        // Título
        JLabel titulo = criarLabelTitulo("Reserva de Espaço");
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(new Color(40, 40, 40));
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        painelTitulo.add(titulo, BorderLayout.CENTER);
        add(painelTitulo, BorderLayout.NORTH);

        // Painel principal com formulário
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 1 - Espaco
        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(criarLabel("Espaço:"), gbc);
        gbc.gridx = 1;
        comboEspacos = new JComboBox<>();
        carregarEspacos();
        comboEspacos.setBackground(new Color(60, 60, 60));
        comboEspacos.setForeground(Color.WHITE);
        painel.add(comboEspacos, gbc);

        // Linha 2 - Data
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(criarLabel("Data (AAAA-MM-DD):"), gbc);
        gbc.gridx = 1;
        campoData = criarCampoTexto();
        painel.add(campoData, gbc);

        // Linha 3 - Hora Início
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(criarLabel("Hora Início (HH:mm):"), gbc);
        gbc.gridx = 1;
        campoHoraInicio = criarCampoTexto();
        painel.add(campoHoraInicio, gbc);

        // Linha 4 - Hora Fim
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(criarLabel("Hora Fim (HH:mm):"), gbc);
        gbc.gridx = 1;
        campoHoraFim = criarCampoTexto();
        painel.add(campoHoraFim, gbc);

        // Linha 5 - Botoes
        gbc.gridx = 0;
        gbc.gridy++;
        btnReservar = criarBotao("Reservar");
        painel.add(btnReservar, gbc);
        gbc.gridx = 1;
        btnCancelar = criarBotao("Cancelar");
        painel.add(btnCancelar, gbc);

        add(painel, BorderLayout.CENTER);

        btnReservar.addActionListener(e -> reservarEspaco());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void carregarEspacos() {
        List<Espaco> espacos = EspacoController.listarTodos();
        DefaultComboBoxModel<Espaco> model = new DefaultComboBoxModel<>();
        for (Espaco e : espacos) {
            model.addElement(e);
        }
        comboEspacos.setModel(model);
        comboEspacos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                        boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Espaco esp) {
                    setText(esp.getNome() + " (" + esp.getTipo() + ")");
                }
                return this;
            }
        });
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField();
        campo.setBackground(new Color(60, 60, 60));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        return campo;
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private JLabel criarLabelTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void reservarEspaco() {
        try {
            Espaco espaco = (Espaco) comboEspacos.getSelectedItem();
            String data = campoData.getText().trim();
            String horaInicio = campoHoraInicio.getText().trim();
            String horaFim = campoHoraFim.getText().trim();

            if (espaco == null || data.isEmpty() || horaInicio.isEmpty() || horaFim.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (horaFim.compareTo(horaInicio) <= 0) {
                JOptionPane.showMessageDialog(this, "Hora fim deve ser maior que hora início.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean disponivel = ReservaController.estaDisponivel(espaco.getId(), data, horaInicio, horaFim);
            if (!disponivel) {
                JOptionPane.showMessageDialog(this, "Espaço não disponível neste horário.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String idReserva = UUID.randomUUID().toString();
            Reserva reserva = new Reserva(idReserva, usuario.getEmail(), espaco.getId(), data, horaInicio, horaFim);
            ReservaController.salvar(reserva);
            LogController.registrar("Reserva criada para espaço: " + espaco.getNome(), usuario.getEmail());

            JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao reservar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
