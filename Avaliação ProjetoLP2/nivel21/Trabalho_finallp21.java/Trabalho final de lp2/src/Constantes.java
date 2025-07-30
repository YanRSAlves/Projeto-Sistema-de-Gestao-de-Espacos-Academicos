
// Classe final que define constantes de caminhos para os arquivos usados no sistema usuários, espaços, reservas e log.

import java.io.File;
import java.io.IOException;

public final class Constantes {
    public static final String CAMINHO = "arquivos_de_reservas" + File.separator;

    public static final String ARQ_USUARIOS = CAMINHO + "usuarios.txt";
    public static final String ARQ_ESPACOS = CAMINHO + "espacos.txt";
    public static final String ARQ_RESERVAS = CAMINHO + "reservas.txt";
    public static final String ARQ_LOG = CAMINHO + "log.txt";

    // Método para criar pasta e arquivos vazios
    public static void garantirArquivos() {
        File pasta = new File(CAMINHO);
        if (!pasta.exists()) {
            pasta.mkdirs(); // cria a pasta
        }

        criarArquivoSeNaoExistir(ARQ_USUARIOS);
        criarArquivoSeNaoExistir(ARQ_ESPACOS);
        criarArquivoSeNaoExistir(ARQ_RESERVAS);
        criarArquivoSeNaoExistir(ARQ_LOG);
    }

    private static void criarArquivoSeNaoExistir(String caminho) {
        File f = new File(caminho);
        if (!f.exists()) {
            try {
                f.createNewFile(); // cria o arquivo vazio
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo: " + caminho);
                e.printStackTrace();
            }
        }
    }
}
