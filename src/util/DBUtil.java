package util;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    //O método a seguir destrói todas as tabelas no banco sempre quando é chamado.
    public static void dropTable() {
        Connection con = null;
        PreparedStatement st = null;
        Statement stmt = null;
        try {
            con = ConnectionFactory.getConnection();
            con.setAutoCommit(false);
            stmt = con.createStatement();
            stmt.execute("DROP TABLE produto;");
            con.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException sqlex) {
            }
        } finally {
            try {
                con.close();
            } catch (SQLException sqlex) {
            }
        }
    }

    //O método a seguir cria todas as tabelas no banco assim que é chamado
    public static void createTable() {
        Connection con = null;
        PreparedStatement st = null;
        Statement stmt = null;

        try {
            con = ConnectionFactory.getConnection();
            con.setAutoCommit(false);
            stmt = con.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS produto "
                    + "(id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "nome CHAR(150),"
                    + "preco DOUBLE);");
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException sqlex) {
                System.err.println("ERRO AO CRIAR TABELA!");
            }
        } finally {
            try {
                con.close();
            } catch (SQLException sqlex) {
                System.err.println("Erro ao fechar conexão!");
            }
        }
    }
}
