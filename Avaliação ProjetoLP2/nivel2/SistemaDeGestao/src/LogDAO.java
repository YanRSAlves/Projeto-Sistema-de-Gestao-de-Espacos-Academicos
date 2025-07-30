import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogDAO {

    public static void registrar(int idUsuario, String acao) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        String sql = "INSERT INTO logs(timestamp, id_usuario, acao) VALUES(?, ?, ?);";

        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, timestamp);
            pstmt.setInt(2, idUsuario);
            pstmt.setString(3, acao);

            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            // Imprime o erro no console de erros, que é mais apropriado.
            System.err.println("Erro ao registrar log no banco de dados: " + e.getMessage());
        }
    }
}
