import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public static List<Reserva> listarReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas;";

        try (Connection conn = Database.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reserva r = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_espaco"),
                    rs.getString("data"),
                    rs.getString("hora_inicio"),
                    rs.getString("hora_fim")
                );
                reservas.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar reservas: " + e.getMessage());
        }
        return reservas;
    }

    public static void adicionarReserva(Reserva r) {
        String sql = "INSERT INTO reservas(id_usuario, id_espaco, data, hora_inicio, hora_fim) VALUES(?, ?, ?, ?, ?);";

        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, r.getIdUsuario());
            pstmt.setInt(2, r.getIdEspaco());
            pstmt.setString(3, r.getData());
            pstmt.setString(4, r.getHoraInicio());
            pstmt.setString(5, r.getHoraFim());

            pstmt.executeUpdate();
            System.out.println("Reserva para o espaço " + r.getIdEspaco() + " adicionada com sucesso!");

        } catch (SQLException ex) {
            System.out.println("Erro ao adicionar reserva: " + ex.getMessage());
        }
    }
}