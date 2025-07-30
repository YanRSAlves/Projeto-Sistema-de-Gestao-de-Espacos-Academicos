
// Utilitário para criar a pasta base onde os arquivos do sistema serao armazenados, caso ainda nao exista.

import java.io.File;

public class PastaUtils {
    public static void criarPastaBase() {
        File dir = new File(Constantes.CAMINHO);
        if (!dir.exists()) {
            boolean criou = dir.mkdirs();
            if (criou) {
                System.out.println("Pasta base criada em: " + Constantes.CAMINHO);
            } else {
                System.out.println("Falha ao criar pasta base: " + Constantes.CAMINHO);
            }
        }
    }
}

