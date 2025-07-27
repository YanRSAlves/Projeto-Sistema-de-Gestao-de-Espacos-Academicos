import java.io.*;
import java.util.*;

public class ReservaDAO {
    private static final String CAMINHO = "arquivos_de_reservas/reservas.txt";

    public static List<Reserva> listarReservas() {
        List<Reserva> reservas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 6) {
                    Reserva r = new Reserva(
                        Integer.parseInt(partes[0]),
                        Integer.parseInt(partes[1]),
                        Integer.parseInt(partes[2]),
                        partes[3],
                        partes[4],
                        partes[5]
                    );
                    reservas.add(r);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler reservas.txt: " + e.getMessage());
        }
        return reservas;
    }

    public static void adicionarReserva(Reserva r) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO, true))) {
            String linha = r.getId() + ";" + r.getIdUsuario() + ";" + r.getIdEspaco() + ";" + r.getData() + ";" + r.getHoraInicio() + ";" + r.getHoraFim();
            bw.write(linha);
            bw.newLine();
        } catch (IOException ex) {
            System.out.println("Erro ao adicionar reserva: " + ex.getMessage());
        }
    }

    public static int gerarNovoId() {
        int id = 1;
        for (Reserva r : listarReservas()) {
            if (r.getId() >= id) {
                id = r.getId() + 1;
            }
        }
        return id;
    }
}
