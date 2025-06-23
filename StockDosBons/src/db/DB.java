package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties prop = loadProperties(); 
                String url = prop.getProperty("dburl");
                conn = DriverManager.getConnection(url, prop);
            } catch (SQLException e) {
                throw new DbExeption(e.getMessage());
            }
        }
        return conn;
    }

    //Criando o metodo para pegar as propriedades do banco de dados
    private static Properties loadProperties(){
        System.out.println("Diretório atual: " + System.getProperty("user.dir"));
        try (FileInputStream fs = new FileInputStream("db.properties")){ //Pegando os inputs pelo arquivo db.properties
             Properties prop = new Properties(); //instanciando as propriedades
             prop.load(fs); // carregando as propriedades
             return prop; //retornando as propriedades carregadas
        } catch (IOException e) {
            throw new DbExeption(e.getMessage());
        }
    }

    //Fechando o banco

    public static void closeConnection () {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbExeption(e.getMessage());
            }
        }
    }

    public static void closeStatement (Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbExeption(e.getMessage());
            }
        }

    }

    public static void closeResultSet (ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbExeption(e.getMessage());
            }
        }

    }

}


