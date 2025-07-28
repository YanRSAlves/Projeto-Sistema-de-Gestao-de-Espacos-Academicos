import java.io.*;

public class ComprovanteDAO {
    public static void gerar(Reserva reserva) {
        String caminho = "arquivos_de_reservas/comprovante_reserva_" + reserva.getId() + ".txt";

        // Buscar os dados
        Usuario usuario = null;
        for (Usuario u : UsuarioDAO.listarUsuarios()) {
            if (u.getId() == reserva.getIdUsuario()) {
                usuario = u;
                break;
            }
        }

        Espaco espaco = null;
        for (Espaco e : EspacoDAO.listarEspacos()) {
            if (e.getId() == reserva.getIdEspaco()) {
                espaco = e;
                break;
            }
        }

        if (usuario == null || espaco == null) {
            System.out.println("Erro: dados da reserva incompletos.");
            return;
        }

        // Criar comprovante
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            bw.write("COMPROVANTE DE RESERVA\n\n");
            bw.write("Reserva ID: " + reserva.getId() + "\n");
            bw.write("Usuário: " + usuario.getNome() + " (ID " + usuario.getId() + ")\n");
            bw.write("Espaço: " + espaco.getNome() + " (ID " + espaco.getId() + ")\n");
            bw.write("Data: " + reserva.getData() + "\n");
            bw.write("Horário: " + reserva.getHoraInicio() + " às " + reserva.getHoraFim() + "\n");
            bw.write("\nSistema de Gestão de Espaços");
        } catch (IOException e) {
            System.out.println("Erro ao gerar comprovante: " + e.getMessage());
        }
    }
}
