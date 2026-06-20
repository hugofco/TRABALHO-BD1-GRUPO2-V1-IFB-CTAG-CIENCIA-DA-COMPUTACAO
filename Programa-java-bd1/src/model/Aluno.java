package model;

public class Aluno extends Pessoa{
    private int matricula;
    private Curso curso;

    public Aluno() {
        super();
    }

    public Aluno(String nome, int cpf, int matricula, Curso curso){
        
        super(nome, cpf);
        
        this.matricula = matricula;
        this.curso = curso;
    }
    // Get - Set

    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    //-------------------------------------------
}
