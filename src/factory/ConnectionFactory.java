package factory;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    public static Connection getConnection() {
        Connection conection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Informamos o driver do MySQL.
            if (conection == null || (conection != null && conection.isClosed())) {
                conection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/vendas", "root", "");
                
                //Antes de prosseguimos com a execução do programa, criamos a bade de dados Vendas.
                Statement stm = conection.createStatement();
                stm.executeUpdate("CREATE DATABASE IF NOT EXISTS vendas");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conection;
    }
}
