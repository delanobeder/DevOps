package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public abstract class GenericDAO<T> {

    protected Connection connection;

    public GenericDAO() {
        try {

            /* Setup para uso do banco de dados MySQL */

            Class.forName("com.mysql.cj.jdbc.Driver");

            String host = System.getenv().getOrDefault("MYSQL_HOST", "localhost");
            String user = System.getenv().getOrDefault("MYSQL_USER", "root");
            String password = System.getenv().getOrDefault("MYSQL_PASSWORD", "root");

            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/Cidades", user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract public void save(T t);

    abstract List<T> getAll();
}