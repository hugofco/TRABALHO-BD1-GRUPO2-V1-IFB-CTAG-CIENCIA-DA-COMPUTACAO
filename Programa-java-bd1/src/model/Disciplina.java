package model;

public class Disciplina{
    private int id_disciplina;
    private String nome_disciplina;
    private Curso curso;

    public Disciplina() {
    }

    public Disciplina(int idDisciplina, String nome, Curso curso) {
        this.id_disciplina = idDisciplina;
        this.nome_disciplina = nome;
        this.curso = curso;
    }

    //Get - Set
    public int getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(int id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getNome_disciplina() {
        return nome_disciplina;
    }

    public void setNome_disciplina(String nome_disciplina) {
        this.nome_disciplina = nome_disciplina;
    }
    
      public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    //-------------------------------------

}
