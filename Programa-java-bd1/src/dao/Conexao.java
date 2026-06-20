//Salva o usuário e a senha pra que não precise se repetir em toda classe DAO

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3307/Universidade";
    private static final String USER = "root";
    private static final String PASS = "admin";

    public static Connection obterConexao() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {

            System.out.println("Não foi possível conectar ao banco de dados");
            throw new RuntimeException(e);
        }
    }
}