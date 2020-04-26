/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica8basesdedatos;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juang
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    /*
    Preguntas y respuestas sobre transacciones
    -Actualización simplre
        Si la priemra falla no se ejeuta la segunda
        Si la segunda falla se guarda el cambio de la primera
    -Transaccion_1
        ¿Se actualiza la tabla si falla la primera,segunda,tercera setencia?
        ¿Y si se ejecuta correctamente las tres primeras setencias que forman
        parte de la transacción y falla la última qué ocurre?
        ¿Qué occurre si dejas el autocommit a false y ejecutas el apartado b y 
        luego el a?
    
    */
    public static void main(String[] args) {
        // TODO code application logic here
        //creo objeto baseDeDatos que contiene todos los metodos necesarios para 
        //conectarnos a la base de datos

        BaseDeDatos conexionBBDD = new BaseDeDatos("jdbc:mysql://localhost:3306/mysql",
                "root", "");
        Boolean salir = false;
        int opcionMenu;
        int opcionMenuSecundario;
        Scanner lector = new Scanner(System.in);

        while (salir == false) {
            try {
                impirmirMenu();
                opcionMenu = lector.nextInt();
                switch (opcionMenu) {
                    case 1:
                        impirmirMenuConsulta();
                        opcionMenuSecundario = lector.nextInt();
                        lector.nextLine();
                        switch (opcionMenuSecundario) {
                            case 1:
                                System.out.println("Introduce el cirterio de busqueda");
                                String nombre = lector.nextLine();
                                conexionBBDD.bucarCervezaPorCervecera(nombre);
                                break;
                            case 2:
                                System.out.println("Introduce el nombre del bar");
                                String nombreBar = lector.nextLine();
                                conexionBBDD.buscarBarPorNombre(nombreBar);
                                break;
                            case 3:
                                System.out.println("Introduce el nombre de la cerveza");
                                String nombreCerveza = lector.nextLine();
                                conexionBBDD.buscarBebedorPorCerveza(nombreCerveza);
                                break;
                        }
                        break;
                    case 2:
                        imprimirMenuActualizacion();
                        opcionMenuSecundario = lector.nextInt();
                        lector.nextLine();
                        switch(opcionMenuSecundario){
                            case 1:
                                System.out.println("Introduce el campo a actualizar");
                                String campoActualizado = lector.nextLine();
                                System.out.println("Introduce el nombre del cliente: ");
                                String cliente = lector.nextLine();
                                System.out.println("Introduce el nombre del bar: ");
                                String bar = lector.nextLine();
                                System.out.println("Introduce la frecuencia nuave");
                                int frecuencia = lector.nextInt();
                                lector.nextLine();
                                conexionBBDD.actualizarFrecuentes(cliente, bar, frecuencia,
                                        campoActualizado);
                                break;
                        }
                        break;
                    case 3:
                        imprimirMenuTransacciones();
                        opcionMenuSecundario = lector.nextInt();
                        lector.nextLine();
                        switch(opcionMenuSecundario){
                            case 1:
                                conexionBBDD.setenciaSimmple();
                                break;
                            case 2:
                                
                                break;
                        }
                        break;
                    case 4:
                        salir = true;
                        break;
                }
            } catch (SQLException ex) {
                System.out.println("Se ha producido un error:\n"+
                        "sql State: "+ex.getSQLState()+"\n Message: "+ex.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void impirmirMenu() {
        System.out.println("1)Consula");
        System.out.println("2)Actualización");
        System.out.println("3)transacciones");
        System.out.println("4)Salir");
        System.out.println("---------");
        System.out.println("Selecciona una opción");
    }

    public static void impirmirMenuConsulta() {
        System.out.println("1)Buscar por nombre de cervecera");
        System.out.println("2)Buscar bar por nombre");
        System.out.println("3)Buscar gente a la que le gusta un tipo determinado"
                + "de cerveza");
        System.out.println("------------------");
        System.out.println("Selecciona una opción:");
    }

    public static void imprimirMenuActualizacion(){
        System.out.println("1)Actualizar tabla frecuentes");
        System.out.println("----------------------");
        System.out.println("Selecciona una opción: ");
    }
    
    public static void imprimirMenuTransacciones(){
        System.out.println("1) transacción simple");
        System.out.println("2)Transaccion_1");
        System.out.println("--------------------");
        System.out.println("Selecciona una opición: ");
    }
}
