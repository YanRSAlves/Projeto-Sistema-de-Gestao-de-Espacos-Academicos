import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public static List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        // Query SQL para selecionar todos os usuários
        String sql = "SELECT * FROM usuarios;";

        // Usando try-with-resources para garantir o fechamento automático dos recursos
        try (Connection conn = Database.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Itera sobre o resultado da consulta
            while (rs.next()) {
                // Cria um objeto Usuario para cada linha retornada
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("tipo")
                );
                usuarios.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }

    
    public static Usuario buscarPorEmailESenha(String email, String senha) {
        // Query SQL para buscar um usuário com email e senha específicos.
        // Usamos '?' como placeholders para evitar SQL Injection.
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?;";

        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Define os valores para os placeholders '?'
            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Se um resultado for encontrado, cria e retorna o objeto Usuario
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("tipo")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        // Retorna null se nenhum usuário for encontrado ou em caso de erro
        return null;
    }

    public static void adicionarUsuario(Usuario u) {
        // Query SQL para inserir um novo usuário. O ID não é necessário.
        String sql = "INSERT INTO usuarios(nome, email, senha, tipo) VALUES(?, ?, ?, ?);";

        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Define os valores para os placeholders
            pstmt.setString(1, u.getNome());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getSenha());
            pstmt.setString(4, u.getTipo());

            // Executa a inserção
            pstmt.executeUpdate();
            System.out.println("Usuário " + u.getNome() + " adicionado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
        }
    }
    
    public static Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?;";

        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("tipo")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return null;
    }

    public static void atualizarUsuario(Usuario u) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, tipo = ? WHERE id = ?;";

        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, u.getNome());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getSenha());
            pstmt.setString(4, u.getTipo());
            pstmt.setInt(5, u.getId());

            pstmt.executeUpdate();
            System.out.println("Usuário ID " + u.getId() + " atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }
    
    public static void deletarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?;";

        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            // executeUpdate() retorna o número de linhas afetadas.
            // Se for > 0, o usuário foi deletado com sucesso.
            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com o ID " + id + ".");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
            System.err.println("Possível causa: O usuário pode ter reservas ativas. " +
                               "É necessário remover as reservas do usuário antes de deletá-lo.");
        }
    }
    
    
}