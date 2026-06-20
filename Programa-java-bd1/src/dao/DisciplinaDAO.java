package dao;

import model.Disciplina;
import model.Curso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    //Insere uma nova disciplina no banco de dados

    public void salvar(Disciplina disciplina) {
        // id_disciplina é AUTO_INCREMENT
        String sql = "INSERT INTO Disciplinas (nome, id_curso) VALUES (?, ?)";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disciplina.getNome_disciplina());
            
            // Verifica se a disciplina está vinculada a um curso para extrair o ID
            if (disciplina.getCurso() != null) {
                stmt.setInt(2, disciplina.getCurso().getId_curso());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();
            System.out.println("Disciplina '" + disciplina.getNome_disciplina() + "' salva com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar a disciplina: " + e.getMessage());
        }
    }

    //Busca todas as disciplina no MySQL e devolve uma lista java
     
    public List<Disciplina> listarTodas() {
        String sql = "SELECT * FROM Disciplinas";
        List<Disciplina> listaDisciplinas = new ArrayList<>();

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Disciplina disciplina = new Disciplina();
                
                disciplina.setId_disciplina(rs.getInt("id_disciplina"));
                disciplina.setNome_disciplina(rs.getString("nome"));

                // Chave estrangeira do curso
                int idCursoFk = rs.getInt("id_curso");
                if (idCursoFk != 0) {
                    Curso cursoAssociado = new Curso();
                    cursoAssociado.setId_curso(idCursoFk);
                    disciplina.setCurso(cursoAssociado);
                }

                listaDisciplinas.add(disciplina);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar as disciplinas: " + e.getMessage());
        }

        return listaDisciplinas;
    }
}