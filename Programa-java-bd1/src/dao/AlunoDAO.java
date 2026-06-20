package dao;

import model.Aluno;
import model.Curso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    //Insere um novo aluno no banco de dados
    
    public void salvar(Aluno aluno) {
        // A matricula é AUTO_INCREMENT, então o banco gera sozinho
        String sql = "INSERT INTO Aluno (nome, cpf, id_curso) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getCpf());
            
            // Verifica se o aluno tem um curso antes de pegar o ID
            if (aluno.getCurso() != null) {
                stmt.setInt(3, aluno.getCurso().getId_curso());
            } else {
                // Se o aluno ainda não se matriculou em nenhum curso, manda NULL para o banco
                stmt.setNull(3, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();
            System.out.println("Aluno '" + aluno.getNome() + "' salvo com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar o aluno: " + e.getMessage());
        }
    }

    //TESTE 01: Busca e lista todos os alunos cadastrados no MySQL

    public List<Aluno> listarTodos() {
        String sql = "SELECT * FROM Aluno";
        List<Aluno> listaAlunos = new ArrayList<>();

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // 1. Cria um aluno vazio
                Aluno aluno = new Aluno();
                
                // 2. Preenche os atributos básicos
                aluno.setMatricula(rs.getInt("matricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getInt("cpf"));

                // 3. Chave Estrangeira do banco
                int idCursoFk = rs.getInt("id_curso");
                
                // Se idCurso não for nulo/zero, recria o objeto Curso
                if (idCursoFk != 0) {
                    Curso cursoAssociado = new Curso();
                    cursoAssociado.setId_curso(idCursoFk);
                    // Associa o curso ao aluno
                    aluno.setCurso(cursoAssociado);
                }

                // 4. Adiciona o aluno montado na lista final
                listaAlunos.add(aluno);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar os alunos: " + e.getMessage());
        }

        return listaAlunos;
    }

    //Item 5: Busca alunos filtrados pelo nome exato do curso utilizando JOIN

    public List<Aluno> buscarPorCurso(String nomeCurso) {
        String sql = "SELECT a.matricula, a.nome, a.cpf, a.id_curso " +
                     "FROM Aluno a " +
                     "INNER JOIN Cursos c ON a.id_curso = c.id_curso " +
                     "WHERE c.nome = ?";
        
        List<Aluno> listaFiltrada = new ArrayList<>();

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Substitui o '?' pelo nome do curso passado no parâmetro
            stmt.setString(1, nomeCurso);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setMatricula(rs.getInt("matricula"));
                    aluno.setNome(rs.getString("nome"));
                    aluno.setCpf(rs.getInt("cpf"));

                    // Monta o objeto Curso para associar ao aluno
                    Curso curso = new Curso();
                    curso.setId_curso(rs.getInt("id_curso"));
                    curso.setNome(nomeCurso);
                    aluno.setCurso(curso);

                    listaFiltrada.add(aluno);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao filtrar alunos por curso: " + e.getMessage());
        }

        return listaFiltrada;
    }
    //ITEM 06: Busca disciplina e Semestre
    public List<Aluno> buscarPorDisciplinaESemestre(String nomeDisciplina, String semestre) {
        String sql = "SELECT a.matricula, a.nome " +
                     "FROM Aluno a " +
                     "INNER JOIN Aluno_Disciplina ad ON a.matricula = ad.matricula " +
                     "INNER JOIN Disciplinas d ON ad.id_disciplina = d.id_disciplina " +
                     "WHERE d.nome = ? AND ad.semestre = ?";
        
        List<Aluno> listaFiltrada = new ArrayList<>();

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeDisciplina);
            stmt.setString(2, semestre);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setMatricula(rs.getInt("matricula"));
                    aluno.setNome(rs.getString("nome"));
                    listaFiltrada.add(aluno);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao filtrar por disciplina e semestre: " + e.getMessage());
        }

        return listaFiltrada;
    }

}

