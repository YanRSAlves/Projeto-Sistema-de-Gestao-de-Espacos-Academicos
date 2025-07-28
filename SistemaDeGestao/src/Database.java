import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;        

/**
 * Classe responsável por gerenciar a conexão com o banco de dados SQLite
 * e inicializar a estrutura de tabelas.
 */
public class Database {
    // Define o nome do arquivo do banco de dados que será criado na pasta do projeto.
    private static final String NOME_BANCO = "gestao_espacos.db";
    // Define a URL de conexão completa para o JDBC do SQLite.
    private static final String URL = "jdbc:sqlite:" + NOME_BANCO;

    /**
     * Obtém uma conexão ativa com o banco de dados.
     * Este é o método que todas as classes DAO usarão para se comunicar com o banco.
     * @return um objeto Connection, ou null em caso de falha.
     */
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Falha na conexão com o banco de dados: " + e.getMessage());
            return null;
        }
    }

    /**
     * Cria as tabelas do banco de dados se elas ainda não existirem.
     * Este método deve ser chamado uma vez, na inicialização do sistema.
     */
    public static void inicializar() {
        // SQL para criar a tabela de usuários
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                             "nome TEXT NOT NULL," +
                             "email TEXT NOT NULL UNIQUE," +
                             "senha TEXT NOT NULL," +
                             "tipo TEXT NOT NULL);";

        // SQL para criar a tabela de espaços, já com as colunas para herança
        String sqlEspacos = "CREATE TABLE IF NOT EXISTS espacos (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "nome TEXT NOT NULL," +
                            "tipo TEXT NOT NULL," +
                            "capacidade INTEGER NOT NULL," +
                            "descricao TEXT," +
                            "lista_equipamentos TEXT," +      // Campo para Laboratorio
                            "possui_projetor BOOLEAN);";     // Campo para Auditorio

        // SQL para criar a tabela de reservas
        String sqlReservas = "CREATE TABLE IF NOT EXISTS reservas (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                             "id_usuario INTEGER NOT NULL," +
                             "id_espaco INTEGER NOT NULL," +
                             "data TEXT NOT NULL," +
                             "hora_inicio TEXT NOT NULL," +
                             "hora_fim TEXT NOT NULL," +
                             "FOREIGN KEY (id_usuario) REFERENCES usuarios(id)," +
                             "FOREIGN KEY (id_espaco) REFERENCES espacos(id));";

        // SQL para criar a tabela de logs
        String sqlLogs = "CREATE TABLE IF NOT EXISTS logs (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "timestamp TEXT NOT NULL," +
                         "id_usuario INTEGER NOT NULL," +
                         "acao TEXT NOT NULL);";

        // Usando try-with-resources para garantir que a conexão e o statement sejam fechados
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            
            if (conn == null) {
                System.err.println("Não foi possível inicializar o banco de dados (conexão nula).");
                return;
            }

            // Executa a criação de cada tabela
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlEspacos);
            stmt.execute(sqlReservas);
            stmt.execute(sqlLogs);

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }
    
    public static void popularDadosIniciais() {
        // --- Bloco para verificar e inserir o usuário ---
        String sqlVerificaUsuario = "SELECT COUNT(*) FROM usuarios WHERE email = ?;";
        String sqlInsereUsuario = "INSERT INTO usuarios(nome, email, senha, tipo) VALUES(?, ?, ?, ?);";

        try (Connection conn = conectar();
             PreparedStatement pstmtVerifica = conn.prepareStatement(sqlVerificaUsuario)) {

            pstmtVerifica.setString(1, "admin@email.com");
            ResultSet rs = pstmtVerifica.executeQuery();

            // Se a contagem de usuários 'admin@email.com' for 0, insere
            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement pstmtInsere = conn.prepareStatement(sqlInsereUsuario)) {
                    pstmtInsere.setString(1, "Administrador");
                    pstmtInsere.setString(2, "admin@email.com");
                    pstmtInsere.setString(3, "admin");
                    pstmtInsere.setString(4, "admin");
                    pstmtInsere.executeUpdate();
                    System.out.println("INFO: Usuário 'admin' padrão criado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao popular usuário inicial: " + e.getMessage());
        }

        // --- Bloco separado para verificar e inserir o espaço ---
        String sqlVerificaEspaco = "SELECT COUNT(*) FROM espacos WHERE id = ?;";
        String sqlInsereEspaco = "INSERT INTO espacos(id, nome, tipo, capacidade, descricao) VALUES(?, ?, ?, ?, ?);";

        try (Connection conn = conectar();
             PreparedStatement pstmtVerifica = conn.prepareStatement(sqlVerificaEspaco)) {
            
            pstmtVerifica.setInt(1, 1);
            ResultSet rs = pstmtVerifica.executeQuery();

            // Se a contagem de espaços com ID 1 for 0, insere
            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement pstmtInsere = conn.prepareStatement(sqlInsereEspaco)) {
                    pstmtInsere.setInt(1, 1);
                    pstmtInsere.setString(2, "Sala de Reunião A-01");
                    pstmtInsere.setString(3, "Sala de Reunião");
                    pstmtInsere.setInt(4, 10);
                    pstmtInsere.setString(5, "Sala padrão com mesa e cadeiras.");
                    pstmtInsere.executeUpdate();
                    System.out.println("INFO: Espaço de teste (ID 1) criado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao popular espaço inicial: " + e.getMessage());
        }
    }
}
