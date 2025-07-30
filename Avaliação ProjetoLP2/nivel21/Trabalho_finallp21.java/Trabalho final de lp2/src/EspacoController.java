
// Controlador responsavel por salvar e listar objetos do tipo Espaco, usando operacoes de arquivo via ArquivoUtils.

import java.util.*;

public class EspacoController {
    public static void salvar(Espaco e) {
        ArquivoUtils.salvarLinha(Constantes.ARQ_ESPACOS, e.toArquivo());
    }

    public static List<Espaco> listarTodos() {
        List<Espaco> espacos = new ArrayList<>();
        for (String linha : ArquivoUtils.lerLinhas(Constantes.ARQ_ESPACOS)) {
            espacos.add(Espaco.fromArquivo(linha));
        }
        return espacos;
    }
}
