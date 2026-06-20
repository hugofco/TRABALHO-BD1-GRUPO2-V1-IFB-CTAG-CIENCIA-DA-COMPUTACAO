## Estrutura das Pastas

O projeto foi organizado utilizando o padrão de arquitetura MVC (focando nas camadas Model e DAO) para separar as responsabilidades do código e facilitar a manutenção.

```text
Programa-java-bd1/
├── .vscode/
│   └── settings.json             # Configurações VS Code (mapeamento JDBC)
├── lib/
│   └── mysql-connector-j-8.4.0.jar # Driver JDBC do MySQL
├── src/
│   ├── model/                    # Classes java padrão
│   │   ├── Pessoa.java           # Classe abstrata (Herança)
│   │   ├── Aluno.java            
│   │   ├── Curso.java            
│   │   └── Disciplina.java       
│   ├── dao/                      # Classes de acesso a dados (tradutor mysql)
│   │   ├── Conexao.java          # Gerenciador de conexão com o banco
│   │   └── CursoDAO.java         # Executa INSERT, SELECT, etc., para a tabela Cursos
│   └── App.java                  # Arquivo principal
└── README.md                     # Documentação do projeto

================================================================================
                GUIA JDBC - COMANDOS ESSENCIAIS (CRUD)
================================================================================

1. GERENCIAMENTO DE CONEXÃO (java.sql.Connection / DriverManager)
--------------------------------------------------------------------------------
DriverManager.getConnection(url, user, pass)
- O que faz: Abre a porta do banco de dados MySQL e devolve o objeto Connection.
- Exemplo: Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Universidade", "root", "senha");

conn.prepareStatement(String sql)
- O que faz: Prepara o comando SQL (com interrogações '?') para ser enviado de forma segura.
- Exemplo: PreparedStatement stmt = conn.prepareStatement("INSERT INTO Aluno (nome, cpf) VALUES (?, ?)");

conn.close()
- O que faz: Encerra a conexão com o banco (desnecessário se você usar o try-with-resources).

// --- Controle de Transação ---
conn.setAutoCommit(false)
- O que faz: Impede que o Java salve as alterações no banco instantaneamente. Útil se precisar salvar várias coisas juntas (ex: Aluno e Aluno_Curso ao mesmo tempo).

conn.commit()
- O que faz: Confirma e salva definitivamente as alterações no banco se tudo deu certo.

conn.rollback()
- O que faz: Desfaz todas as alterações se ocorreu algum erro crítico no meio do processo.


2. EXECUÇÃO DE COMANDOS (java.sql.PreparedStatement)
--------------------------------------------------------------------------------
stmt.executeUpdate()
- O que faz: Executa comandos que ALTERAM o banco de dados.
- Quando usar: Com INSERT, UPDATE ou DELETE.
- Retorno: Devolve um número inteiro (int) indicando quantas linhas foram afetadas.
- Exemplo: int linhasAfetadas = stmt.executeUpdate();

stmt.executeQuery()
- O que faz: Executa comandos que APENAS LÊEM o banco de dados.
- Quando usar: EXCLUSIVAMENTE com comandos SELECT.
- Retorno: Devolve um objeto 'ResultSet' contendo os resultados devolvidos pelo MySQL.
- Exemplo: ResultSet rs = stmt.executeQuery();

stmt.setString(int posicao, String valor)
- O que faz: Troca a interrogação '?' do SQL por um texto (VARCHAR no banco).
- Exemplo: stmt.setString(1, "Joao Pedro Dantas"); // Preenche a 1ª interrogação

stmt.setInt(int posicao, int valor)
- O que faz: Troca a interrogação '?' do SQL por um número inteiro (INT no banco).
- Exemplo: stmt.setInt(2, 66666666); // Preenche a 2ª interrogação


3. LEITURA DOS RESULTADOS (java.sql.ResultSet)
--------------------------------------------------------------------------------
rs.next()
- O que faz: Move o cursor para a próxima linha da tabela de resultados.
- Retorno: 'true' se encontrou uma linha, 'false' se acabaram as linhas.
- Como usar: Sempre dentro de um loop 'while' (para várias linhas) ou 'if' (para uma única linha).
- Exemplo: while (rs.next()) { ... }

rs.getString(String nomeDaColuna)
- O que faz: Extrai o valor em formato de texto da coluna especificada.
- Exemplo: String nomeDoAluno = rs.getString("nome");

rs.getInt(String nomeDaColuna)
- O que faz: Extrai o valor em formato numérico inteiro da coluna.
- Exemplo: int matricula = rs.getInt("matricula");

rs.getDouble(String nomeDaColuna)
- O que faz: Extrai valores com casas decimais.
- Exemplo: double nota = rs.getDouble("nota_final");

rs.getBoolean(String nomeDaColuna)
- O que faz: Extrai um valor verdadeiro/falso (no MySQL, geralmente uma coluna TINYINT 1 ou 0).
- Exemplo: boolean estaAtivo = rs.getBoolean("ativo");


================================================================================
                    COMANDOS BÁSICOS
================================================================================

// ➔ PARA SALVAR/ATUALIZAR/DELETAR (INSERT / UPDATE / DELETE)
String sql = "INSERT INTO Cursos (nome) VALUES (?)";

try (Connection conn = Conexao.obterConexao();
     PreparedStatement stmt = conn.prepareStatement(sql)) {
    
    stmt.setString(1, meuCurso.getNome());
    stmt.executeUpdate(); // Usa Update para alterar o banco
}


// ➔ PARA BUSCAR DADOS (SELECT)
String sql = "SELECT * FROM Cursos";

try (Connection conn = Conexao.obterConexao();
     PreparedStatement stmt = conn.prepareStatement(sql);
     ResultSet rs = stmt.executeQuery()) { // Usa Query para leitura
    
    while (rs.next()) {
        int id = rs.getInt("id_curso");
        String nome = rs.getString("nome");
        // ... usar os dados para montar o objeto Java ...
    }
}