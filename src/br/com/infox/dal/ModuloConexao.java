/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.dal;

import java.sql.*;

/**
 *
 * @author Fabio
 */
public class ModuloConexao {
    //metodo responsavel por estabelecer a conexão com o banco
    
    public static Connection conector(){
        java.sql.Connection conexao = null;
        // a linha abaixo chama o drive
        
        String driver = "com.mysql.jdbc.Driver";
        // armazenando iformações referente ao banco
        
        String url = "jdbc:mysql://localhost:3306/dbinfox";
        String user = "root";
        String password = "";
        //estabelecendo conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
            
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
