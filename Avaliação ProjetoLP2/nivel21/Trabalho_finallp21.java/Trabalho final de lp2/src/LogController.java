
// Controla o registro de logs, salvando acoes com descricao, usuario e timestamp em arquivo.

import java.time.LocalDateTime;

public class LogController {
    public static void registrar(String descricao, String usuario) {
        String timestamp = LocalDateTime.now().toString();
        LogAcao log = new LogAcao(descricao, usuario, timestamp);
        ArquivoUtils.salvarLinha(Constantes.ARQ_LOG, log.toArquivo());
    }
}
