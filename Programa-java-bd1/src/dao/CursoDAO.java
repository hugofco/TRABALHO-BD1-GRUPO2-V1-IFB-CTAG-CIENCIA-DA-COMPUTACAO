package dao;

import model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public void salvar(Curso curso) {
        String sql = "INSERT INTO Cursos (nome) VALUES (?)";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Troca a interrogação (?) pelo nome que está dentro do objeto
            stmt.setString(1, curso.getNome());

            stmt.executeUpdate(); // Executa o comando no banco
            System.out.println("Curso '" + curso.getNome() + "' inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar o curso: " + e.getMessage());
        }
    }

    //Busca todas as linhas da tabela Cursos e devolve uma Lista Java

    public List<Curso> listarTodos() {
        String sql = "SELECT * FROM Cursos";
        List<Curso> listaCursos = new ArrayList<>();

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) { // executeQuery é o método JDBC para SELECT

            while (rs.next()) {
                Curso curso = new Curso();
                
                // Puxa dados da coluna do banco e joga para o objeto
                curso.setId_curso(rs.getInt("id_curso"));
                curso.setNome(rs.getString("nome"));

                // Adiciona o objeto pronto na lista de cursos
                listaCursos.add(curso);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar os cursos: " + e.getMessage());
        }

        return listaCursos;
    }
}