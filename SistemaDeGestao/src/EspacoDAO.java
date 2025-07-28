import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EspacoDAO {

    // MÉTODO LISTAR ATUALIZADO
     public static List<Espaco> listarEspacos() {
        List<Espaco> espacos = new ArrayList<>();
        String sql = "SELECT * FROM espacos;";

        try (Connection conn = Database.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // O laço while percorre cada linha retornada pelo banco de dados
            while (rs.next()) {
                // ADICIONE ESTA LINHA DE DEPURAÇÃO AQUI
                //System.out.println("DEBUG: Linha encontrada na tabela espacos -> ID: " + rs.getInt("id") + ", Tipo: " + rs.getString("tipo"));
                
                espacos.add(criarEspacoDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar espaços: " + e.getMessage());
        }
        return espacos;
    }

    // MÉTODO BUSCAR POR ID ATUALIZADO
    public static Espaco buscarPorId(int id) {
        String sql = "SELECT * FROM espacos WHERE id = ?;";
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return criarEspacoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar espaço por ID: " + e.getMessage());
        }
        return null;
    }

    // MÉTODO ADICIONAR ATUALIZADO
    public static void adicionarEspaco(Espaco e) {
        String sql = "INSERT INTO espacos(nome, tipo, capacidade, descricao, lista_equipamentos, possui_projetor) VALUES(?, ?, ?, ?, ?, ?);";
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            prepararStatementParaEspaco(pstmt, e);
            pstmt.executeUpdate();
            System.out.println("Espaço '" + e.getNome() + "' adicionado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao adicionar espaço: " + ex.getMessage());
        }
    }

    // MÉTODO ATUALIZAR ATUALIZADO
    public static void atualizarEspaco(Espaco e) {
        String sql = "UPDATE espacos SET nome = ?, tipo = ?, capacidade = ?, descricao = ?, lista_equipamentos = ?, possui_projetor = ? WHERE id = ?;";
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            prepararStatementParaEspaco(pstmt, e);
            pstmt.setInt(7, e.getId()); // O 7º '?' é o ID
            pstmt.executeUpdate();
            System.out.println("Espaço ID " + e.getId() + " atualizado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar espaço: " + ex.getMessage());
        }
    }

    // MÉTODO DELETAR (continua igual)
    public static void deletarEspaco(int id) {
        String sql = "DELETE FROM espacos WHERE id = ?;";
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Espaço ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum espaço encontrado com o ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar espaço: " + e.getMessage());
            System.err.println("Possível causa: O espaço pode ter reservas ativas.");
        }
    }

    // --- MÉTODOS AUXILIARES ---

    // NOVO MÉTODO AUXILIAR PARA CRIAR O OBJETO CORRETO
    private static Espaco criarEspacoDoResultSet(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    String nome = rs.getString("nome");
    String tipo = rs.getString("tipo");
    int capacidade = rs.getInt("capacidade");
    String descricao = rs.getString("descricao");

    if ("Laboratório".equalsIgnoreCase(tipo)) {
        String equipamentos = rs.getString("lista_equipamentos");
        return new Laboratorio(id, nome, tipo, capacidade, descricao, equipamentos);
    } else if ("Auditório".equalsIgnoreCase(tipo)) {
        boolean projetor = rs.getBoolean("possui_projetor");
        return new Auditorio(id, nome, tipo, capacidade, descricao, projetor);
    } else {
        // Esta é a correção crucial:
        return new EspacoGeral(id, nome, tipo, capacidade, descricao);
    }
}

    // NOVO MÉTODO AUXILIAR PARA PREPARAR O STATEMENT DE INSERT/UPDATE
    private static void prepararStatementParaEspaco(PreparedStatement pstmt, Espaco e) throws SQLException {
        pstmt.setString(1, e.getNome());
        pstmt.setString(2, e.getTipo());
        pstmt.setInt(3, e.getCapacidade());
        pstmt.setString(4, e.getDescricao());

        // Verifica o tipo do objeto para salvar os dados específicos
        if (e instanceof Laboratorio) {
            Laboratorio lab = (Laboratorio) e;
            pstmt.setString(5, lab.getListaEquipamentos());
            pstmt.setNull(6, java.sql.Types.BOOLEAN); // Coluna do projetor fica nula
        } else if (e instanceof Auditorio) {
            Auditorio aud = (Auditorio) e;
            pstmt.setNull(5, java.sql.Types.VARCHAR); // Coluna de equipamentos fica nula
            pstmt.setBoolean(6, aud.isPossuiProjetor());
        } else {
            // Para outros tipos de espaço, salvamos os campos extras como nulos
            pstmt.setNull(5, java.sql.Types.VARCHAR);

            pstmt.setNull(6, java.sql.Types.BOOLEAN);
        }
    }
}