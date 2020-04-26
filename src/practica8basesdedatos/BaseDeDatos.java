/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica8basesdedatos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juang
 */
public class BaseDeDatos {

    private Connection conexion;
    private String url;
    private String usuario;
    private String contraseña;

    public BaseDeDatos(String url, String usuario, String contraseña) {
        this.url = url;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void bucarCervezaPorCervecera(String nombre) throws SQLException, IOException {
        /*Para hacer un sql injection en este caso podemos poner un nombre como
        el que espera la consulta y añadir algo del estilo "or 5=5 de este modo
        obtenemos todos los resultados de la tabla.
        Ejemplo: 'amy' or 5=5
         */
        boolean encontrado = false;
        BufferedWriter escritor = new BufferedWriter(new FileWriter("resultados.txt", true));
        Connection conexion = DriverManager.getConnection(this.getUrl(),
                this.getUsuario(), this.getContraseña());
        Statement st = conexion.createStatement();
        System.out.println("--------");
        ResultSet resultado = st.executeQuery("select * from beer where "
                + "Brewer =" + nombre);
        escritor.write("El resultado de la busqueda 'buscar cerveza por "
                + "cerveceria' con nombre: " + nombre + " son los siguientes");
        escritor.newLine();
        while (resultado.next()) {
            /*La primera vez siempre entraremos en la parte if del condicional
                debido a que encontrado siempre sera false en la primera iteración
                y a partir de la segunda sera true y ya pondremos la lista de cervezas
                separadas por comas*/
            if (encontrado == false) {
                System.out.println("Tenemos las siguientes "
                        + "cervezas disponibles de este cervecero: "
                        + resultado.getString(1));
                escritor.write("Tenemos las siguientes cervezas disponibles de "
                        + "este cervecero" + resultado.getString(1));
            } else {
                System.out.println(", " + resultado.getString(1));
                escritor.write(", " + resultado.getString(1));
            }
            encontrado = true;
        }
        if (encontrado == false) {
            escritor.write("No tenemos resultados");
            System.out.println("No tenemos resultados");
        }
        escritor.newLine();
        escritor.newLine();

        //cerramos las conexiones y escritor
        escritor.close();
        resultado.close();
        st.close();
        conexion.close();
    }

    public void buscarBarPorNombre(String nombre) throws SQLException, IOException {
        String buscar = "select * from bar where name =?";
        boolean encontrado = false;
        Connection conexion = DriverManager.getConnection(this.getUrl(),
                this.getUsuario(), this.getContraseña());
        BufferedWriter escritor = new BufferedWriter(new FileWriter("resultados.txt", true));
        PreparedStatement prueba = conexion.prepareStatement(buscar);
        prueba.setString(1, nombre);
        ResultSet resultados = prueba.executeQuery();
        escritor.write("El resultado de la busqueda 'buscar bar por nombre'"
                + " con el nombre " + nombre + " es el siguiente:");
        escritor.newLine();
        while (resultados.next()) {
            System.out.println("El nombre del bar es " + resultados.getString(1)
                    + " y la calle es " + resultados.getString(2));
            escritor.write("El nombre del bar es " + resultados.getString(1)
                    + " y la dirección es " + resultados.getString(2));
            encontrado = true;
        }
        if (encontrado == false) {
            System.out.println("No tenemos ningun bar con ese nombre");
            escritor.write("No tenemos ningun bar con ese nombre");
        }
        escritor.newLine();
        escritor.newLine();
        //cerramos todo
        prueba.close();
        escritor.close();
        conexion.close();

    }

    public void buscarBebedorPorCerveza(String nombre) throws SQLException, IOException {
        String buscar = "select drinker from likes where beer=?";
        Connection conexion = DriverManager.getConnection(this.getUrl(), this.getUsuario(),
                this.getContraseña());
        boolean encontrado = false;
        PreparedStatement st = conexion.prepareStatement(buscar);
        BufferedWriter escritor = new BufferedWriter(new FileWriter("resultados.txt", true));
        st.setString(1, nombre);
        ResultSet resultados = st.executeQuery();
        while (resultados.next()) {
            if (encontrado == false) {
                encontrado = true;
                System.out.println("A los clientes que les gusta la cerveza"
                        + nombre + "son los siguientes: ");
                System.out.println(resultados.getString(1));
                escritor.write("A los clientes que les gusta la cerveza"
                        + nombre + "son los siguientes:");
                escritor.newLine();
                escritor.write(resultados.getString(1));
                escritor.newLine();
            } else {
                System.out.println(resultados.getString(1));
                escritor.write(resultados.getString(1));
                escritor.newLine();
            }
        }
        if (encontrado == false) {
            System.out.println("No tenemos resultados");
            escritor.write("No tenemos resultados");
            escritor.newLine();
            escritor.newLine();
        }
        escritor.newLine();
        conexion.close();
        escritor.close();
    }

    public void actualizarFrecuentes(String nombre, String bar, int frec
    ,String campoActualizado) throws
            SQLException, IOException {
        String sentenciaSQL = "update frequents set ";
        sentenciaSQL+=campoActualizado+"=? where"
                + " drinker=? and bar=?";
        System.out.println(sentenciaSQL);
        Connection conexion = DriverManager.getConnection(this.getUrl(), this.getUsuario(),
                this.getContraseña());
        PreparedStatement st = conexion.prepareStatement(sentenciaSQL);
        st.setInt(1, frec);
        st.setString(2, nombre);
        st.setString(3, bar);
        int saber = st.executeUpdate();
        System.out.println(saber);
    }

    public void setenciaSimmple() throws SQLException {
        Scanner input = new Scanner(System.in);
        String setenciaSql = "UPDATE drinker SET address=? WHERE name=?";
        Connection conexion = DriverManager.getConnection(this.getUrl(),this.getUsuario(),
                this.getContraseña());
        PreparedStatement st = conexion.prepareStatement(setenciaSql);
        PreparedStatement st1 = conexion.prepareStatement(setenciaSql);
        //una vez obtenida la conexion pido al usuario los valores del primer 
        //update
        System.out.println("Introduce la direccion del bebedor");
        st.setString(1,input.nextLine());
        System.out.println("Introudce el nombre del bebedor al que quieres actu"
                + "alizar la dirección");
        st.setString(2, input.nextLine());
        System.out.println("Introduce la direccion del segundo bebedor");
        st1.setString(1, input.nextLine());
        System.out.println("Introduce el nombre del segundo bebedor");
        st1.setString(2, input.nextLine());
        //ejecuto las actualizaciones
        st.executeUpdate();
        st1.executeUpdate();
        //cierro la conexion con la base de datos
        conexion.close();
    }
    
}
