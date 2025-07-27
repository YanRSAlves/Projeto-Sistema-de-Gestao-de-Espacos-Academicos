import java.io.*;
import java.util.*;

public class EspacoDAO {
    private static final String CAMINHO = "arquivos_de_reservas/espacos.txt";

    public static List<Espaco> listarEspacos() {
        List<Espaco> espacos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 5) {
                    Espaco e = new Espaco(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        partes[2],
                        Integer.parseInt(partes[3]),
                        partes[4]
                    );
                    espacos.add(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler espacos.txt: " + e.getMessage());
        }
        return espacos;
    }

    public static void adicionarEspaco(Espaco e) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO, true))) {
            String linha = e.getId() + ";" + e.getNome() + ";" + e.getTipo() + ";" + e.getCapacidade() + ";" + e.getDescricao();
            bw.write(linha);
            bw.newLine();
        } catch (IOException ex) {
            System.out.println("Erro ao adicionar espaço: " + ex.getMessage());
        }
    }
}
