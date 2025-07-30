import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
// Atualiza lista de usuarios na tabela
public class UsuarioTableModel extends AbstractTableModel {
    private List<Usuario> usuarios = new ArrayList<>(); // atualiza visualizacao
    private final String[] colunas = {"ID", "Nome", "Email", "Tipo"};
// Retorna usuario na linha informada
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuarioAt(int linha) {
        return usuarios.get(linha);
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna];
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Usuario u = usuarios.get(linha);
        return switch (coluna) {
            case 0 -> u.getId();
            case 1 -> u.getNome();
            case 2 -> u.getEmail();
            case 3 -> u.getTipo();
            default -> null;
        };
    }
}
