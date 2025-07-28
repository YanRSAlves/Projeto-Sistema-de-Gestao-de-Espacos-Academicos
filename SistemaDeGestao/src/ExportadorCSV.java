import java.io.*;

public class ExportadorCSV {
    public static void exportarReservasCSV() {
        String caminho = "arquivos_de_reservas/reservas_exportadas.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            // Cabecalho
            bw.write("ID;Nome do Usuário;Espaço;Data;Hora Início;Hora Fim");
            bw.newLine();

            // Dados
            for (Reserva r : ReservaDAO.listarReservas()) {
                String nomeUsuario = buscarNomeUsuario(r.getIdUsuario());
                String nomeEspaco = buscarNomeEspaco(r.getIdEspaco());

                bw.write(r.getId() + ";" + nomeUsuario + ";" + nomeEspaco + ";" + 
                r.getData() + ";" + r.getHoraInicio() + ";" + r.getHoraFim());
                bw.newLine();
            }

            System.out.println("Exportação concluída: reservas_exportadas.csv");
        } catch (IOException e) {
            System.out.println("Erro ao exportar CSV: " + e.getMessage());
        }
    }

    private static String buscarNomeUsuario(int id) {
        for (Usuario u : UsuarioDAO.listarUsuarios()) {
            if (u.getId() == id) return u.getNome();
        }
        return "Desconhecido";
    }

    private static String buscarNomeEspaco(int id) {
        for (Espaco e : EspacoDAO.listarEspacos()) {
            if (e.getId() == id) return e.getNome();
        }
        return "Desconhecido";
    }
}

