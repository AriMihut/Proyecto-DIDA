package dao;

import com.amm.dida.proyectoempresas.Empresa;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.fxml.FXML;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {
    
    public static final String URL_CONEXION = "jdbc:mysql://localhost:3306/empresa?zeroDateTimeBehavior=CONVERT_TO_NULL";
    public static final String USUARIO_BDD = "root";
    public static final String PASSWORD_BDD = "";
    
    public EmpresaDAO(){
       
       /* crearTablaSiNoExiste();*/
    }

    public void crearTablaSiNoExiste() {
        
        try (Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)){  
            Statement statement = conexionDataBase.createStatement(); 
            String sql = "CREATE TABLE IF NOT EXISTS empresa" +
                    "(id INTEGER(11) auto_increment PRIMARY KEY, " +
                    "nombre VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "telefono INTEGER(10), " +
                    "ambito VARCHAR(255), " +
                    "internacional BOOLEAN, " +
                    "mercanciaTransportada VARCHAR(255))";
            statement.executeUpdate(sql);  

        } catch (SQLException ex) {
            System.out.println("Error al crear la tabla empresa");
            System.out.println(ex.getMessage());
        }
        
    }
    
    public void anadir(Empresa empresa){
           
        try (
            Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "INSERT INTO empresa(nombre, email, telefono, ambito, internacional, mercanciaTransportada) " +
                   "VALUES ('" + empresa.getNombre() + 
                    "', '" + empresa.getEmail() + 
                    "', " + empresa.getTelefono() + 
                    ", '" + empresa.getAmbito() + 
                    "', " + empresa.getInternacional() +
                    ", '" + empresa.getMercanciaTransportada() +"');";
                 System.out.println("Insertar en empresa: " + sql);
            statement.executeUpdate(sql);  
            
          } catch (SQLException ex) {
                System.out.println("Error al introducir información en la tabla empresa" + ex.getMessage());
          }        
        
        }
    
    public void modificar(Empresa empresa) {
        
         try(
            Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "UPDATE empresa set nombre='" + empresa.getNombre() + 
                    "', email='" + empresa.getEmail() + 
                    "', telefono=" + empresa.getTelefono() + 
                    ", ambito='" + empresa.getAmbito() + 
                    "', internacional=" + empresa.getInternacional() + 
                    ", mercanciaTransportada='" + empresa.getMercanciaTransportada() + 
                    "' WHERE id=" + empresa.getId();
            statement.executeUpdate(sql);
          } catch (SQLException ex) {
                System.out.println("Error al modificar la tabla empresa en DAO" + ex.getMessage());
            }      
            
        }
    
    public List<Empresa> buscarTodas(){
        
        List<Empresa> empresas = new ArrayList<>();
        try{
            Connection conexionDataBase = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD);
            Statement  statement = conexionDataBase.createStatement();
            String sql = "SELECT * FROM empresa ORDER BY id";
            ResultSet resultset = statement.executeQuery(sql);
            
        while(resultset.next()){
             Empresa empresa = new Empresa();
             empresa.setId(resultset.getInt("id"));
             empresa.setNombre(resultset.getString("nombre"));
             empresa.setEmail(resultset.getString("email"));
             empresa.setTelefono(resultset.getInt("telefono"));
             empresa.setAmbito(resultset.getString("ambito"));
             empresa.setInternacional(resultset.getBoolean("internacional"));
             empresa.setMercanciaTransportada(resultset.getString("mercanciaTransportada"));
             empresas.add(empresa);
            }
          
        }catch (SQLException ex) {
          System.out.println("No posible mostrar los datos de la tabla empresa");
            System.out.println(ex.getMessage());
       }
        return empresas;
    }
    
    @FXML
    public void eliminar(Empresa empresa) {
        try{
            Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD);
            Statement statement = conexionDB.createStatement();
            String sql = "DELETE FROM empresa WHERE id=" + empresa.getId();
            statement.executeUpdate(sql);
        }catch(Exception e){
            throw new RuntimeException("Ocurrió un error al eliminar la información " + e.getMessage());
        }
     }
    
}
    

