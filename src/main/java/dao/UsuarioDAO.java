package dao;

import com.amm.dida.proyectoempresas.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;

public class UsuarioDAO {
    
    public static final String URL_CONEXION = "jdbc:mysql://localhost:3306/empresa?zeroDateTimeBehavior=CONVERT_TO_NULL";
    public static final String USUARIO_BDD = "root";
    public static final String PASSWORD_BDD = "";
    
    public UsuarioDAO(){
       
       /* crearTablaSiNoexiste();*/
    }

    public void crearTablaSiNoexiste() {
        
        try (Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)){  
            Statement statement = conexionDataBase.createStatement(); 
            String sql = "CREATE TABLE IF NOT EXISTS usuario" +
                    "(id INTEGER(15) auto_increment PRIMARY KEY, " +
                    "nombre VARCHAR(45), " +
                    "contrasena VARCHAR(45))"; 
            statement.executeUpdate(sql);  

        } catch (SQLException ex) {
            System.out.println("Error al crear la tabla usuario");
            System.out.println(ex.getMessage());
        }
        
    }
    
    @FXML
    public void anadir(Usuario usuario){
           
        try (
            Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "INSERT INTO usuario(nombre, contrasena) " +
                   "VALUES ('" + usuario.getNombre() + "', ('" + usuario.getContrasena() + "'));";
            statement.executeUpdate(sql);  
            System.out.println("sql ===> " + sql);
            
          } catch (SQLException ex) {
              System.out.println(ex.getMessage());
                System.out.println("Error al introducir información en la tabla usuario.");
          }        
        
        }
    
    public void modificar(Usuario usuario) {
        
         try(
            Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "UPDATE usuario set nombre='" + usuario.getNombre() + "', "
                    + "contrasena='" + usuario.getContrasena() + "' WHERE id=" + usuario.getId();
            statement.executeUpdate(sql);
          } catch (SQLException ex) {
                System.out.println("Error al modificar la información en la tabla usuario");
            }      
            
        }
    
    public List<Usuario> buscarTodas(){
        
        List<Usuario> usuarios = new ArrayList<>();
        try{
            Connection conexionDataBase = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD);
            Statement  statement = conexionDataBase.createStatement();
            String sql = "SELECT * FROM usuario ORDER BY id";
            ResultSet resultset = statement.executeQuery(sql);
            
        while(resultset.next()){
             Usuario usuario = new Usuario();
             usuario.setNombre(resultset.getString("nombre"));
             usuario.setContrasena(resultset.getString("contrasena"));
             usuarios.add(usuario);
            }
          
        }catch (SQLException ex) {
          System.out.println("No posible mostrar los datos de la tabla usuario");
            System.out.println(ex.getMessage());
       }
        return usuarios;
    }
    
    @FXML
    public void eliminar(Usuario usuario) {
        try{
            Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD);
            Statement statement = conexionDB.createStatement();
            String sql = "DELETE FROM empresaTransporte WHERE id=" + usuario.getId();
            statement.executeUpdate(sql);
        }catch(Exception e){
            throw new RuntimeException("Ocurrió un error al eliminar la información " + e.getMessage());
        }
     }

    public boolean comprobarUsuario(Usuario usuario) throws SQLException {
        
            Connection conexionDataBase = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD);
            Statement  statement = conexionDataBase.createStatement();
            String sql = "SELECT * FROM usuario WHERE nombre = '" + usuario.getNombre() + 
                    "' and contrasena ='" + usuario.getContrasena() + "'";
            ResultSet resultset = statement.executeQuery(sql);
            return resultset.next();
        
    }

}
