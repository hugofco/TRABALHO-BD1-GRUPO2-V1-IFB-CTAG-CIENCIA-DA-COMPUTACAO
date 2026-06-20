package model;

public abstract class Pessoa {
    private String nome;
    private int cpf; //Recomendo que seja trocado para String no MySQL ó

    public Pessoa() {
        }

    public Pessoa(String nome, int cpf) {
            this.nome = nome;
            this.cpf = cpf;
        }

        // Get - Set
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public int getCpf() {
            return cpf;
        }

        public void setCpf(int cpf) {
            this.cpf = cpf;
        }
    }
//-------------------------------------------