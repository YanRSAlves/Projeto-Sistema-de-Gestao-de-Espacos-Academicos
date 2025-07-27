import java.io.*;
import java.util.*;

public class UsuarioDAO {
    private static final String CAMINHO = "arquivos_de_reservas/usuarios.txt";

    public static List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 5) {
                    Usuario u = new Usuario(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        partes[2],
                        partes[3],
                        partes[4]
                    );
                    usuarios.add(u);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler usuarios.txt: " + e.getMessage());
        }
        return usuarios;
    }

    public static Usuario buscarPorEmailESenha(String email, String senha) {
        List<Usuario> usuarios = listarUsuarios();
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }

    public static void adicionarUsuario(Usuario u) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO, true))) {
            String linha = u.getId() + ";" + u.getNome() + ";" + u.getEmail() + ";" + u.getSenha() + ";" + u.getTipo();
            bw.write(linha);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
        }
    }
}
