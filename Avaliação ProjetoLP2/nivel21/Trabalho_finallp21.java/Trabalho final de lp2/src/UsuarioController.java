import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
// Salva um usuario no arquivo (adiciona linha)
    public static void salvar(Usuario u) {
        ArquivoUtils.salvarLinha(Constantes.ARQ_USUARIOS, u.toArquivo());
    }
// Lê todos usuarios do arquivo e retorna como lista de objetos
    public static List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        for (String linha : ArquivoUtils.lerLinhas(Constantes.ARQ_USUARIOS)) {
            usuarios.add(Usuario.fromArquivo(linha));
        }
        return usuarios;
    }
// Sobrescreve arquivo com toda a lista de usuarios
    public static void salvarTodos(List<Usuario> lista) {
        List<String> linhas = new ArrayList<>();
        for (Usuario u : lista) {
            linhas.add(u.toArquivo());
        }
        ArquivoUtils.salvarTodos(Constantes.ARQ_USUARIOS, linhas);
    }
// Autentica usuario pelo email e senha, retorna null se invalido
    public static Usuario buscarPorEmail(String email) {
        for (Usuario u : listarTodos()) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public static Usuario autenticar(String email, String senha) {
        for (Usuario u : listarTodos()) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }
}
