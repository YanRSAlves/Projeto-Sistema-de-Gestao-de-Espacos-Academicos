import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogDAO {
    private static final String CAMINHO = "arquivos_de_reservas/log.txt";

    public static void registrar(int idUsuario, String acao) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String linha = "[" + timestamp + "] Usuario ID " + idUsuario + " " + acao;
            bw.write(linha);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao escrever no log: " + e.getMessage());
        }
    }
}
