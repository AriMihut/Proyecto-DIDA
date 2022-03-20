package dao;

import com.amm.dida.proyectoempresas.Empleado;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;

public class EmpleadoDAO {
    
    public static final String URL_CONEXION = "jdbc:mysql://localhost:3306/empresa?zeroDateTimeBehavior=CONVERT_TO_NULL";
    public static final String USUARIO_BDD = "root";
    public static final String PASSWORD_BDD = "";
    
    public EmpleadoDAO(){
       
        /*crearTablaSiNoExiste();*/
    }

    public void crearTablaSiNoExiste() {
        
        try (Connection conexionDataBase =
            DriverManager.getConnection(EmpleadoDAO.URL_CONEXION, EmpleadoDAO.USUARIO_BDD, EmpleadoDAO.PASSWORD_BDD)){  
            Statement statement = conexionDataBase.createStatement(); 
            String sql = "CREATE TABLE IF NOT EXISTS empleado" +
                    "(id INTEGER auto_increment PRIMARY KEY, " +
                    "nombreEmpleado VARCHAR(255), " +
                    "fechaAlta TIMESTAMP, " +
                    "sexo VARCHAR(255), " +
                    "idDepartamento INTEGER, " +
                    "idEmpresa INTEGER, " +
                    "esResponsable BOOLEAN," + 
                    "FOREIGN KEY (idDepartamento) REFERENCES departamento(id)," +
                    "FOREIGN KEY (idEmpresa) REFERENCES empresa(id))";
            System.out.println("sql =====> " + sql);
            
            statement.executeUpdate(sql);  
            
        } catch (SQLException ex) {
            System.out.println("Error al crear la tabla empleado" + ex.getMessage());
        }
        
    }
    
    @FXML
    public void anadir(Empleado empleado){
           
        try (
            Connection conexionDataBase =
            DriverManager.getConnection(EmpleadoDAO.URL_CONEXION, EmpleadoDAO.USUARIO_BDD, EmpleadoDAO.PASSWORD_BDD)){
            Statement statement = conexionDataBase.createStatement();
            System.out.println("iddepartamento = " + empleado.getIdDepartamento());
            
            String sql = "INSERT INTO empleado(nombreEmpleado, fecha_alta, sexo, idDepartamento, idEmpresa, es_responsable) " 
                    + "VALUES ('" + empleado.getNombreEmpleado() + "', '" 
                    + new Timestamp(empleado.getFechaAlta().getTime()) + "', '" 
                    + empleado.getSexo()+ "', " 
                    + empleado.getIdDepartamento() + ", " 
                    + empleado.getIdEmpresa() +", " 
                    + empleado.isEsResponsable()+ ")";
                 System.out.println("sql insertar en empleado ===> " + sql);
            statement.executeUpdate(sql);  
          } catch (SQLException ex) {
                System.out.println("Error al introducir información en la tabla empleado " + ex.getMessage());
          }        
        
        }
    
    public void modificar(Empleado empleado) {
        
         try(
            Connection conexionDataBase =
            DriverManager.getConnection(EmpleadoDAO.URL_CONEXION, EmpleadoDAO.USUARIO_BDD, EmpleadoDAO.PASSWORD_BDD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "UPDATE empleado set nombreEmpleado='" + empleado.getNombreEmpleado()+ 
                    "', fechaAlta='" + new Timestamp
                        (empleado.getFechaAlta().getTime()) + 
                    "', sexo='" + empleado.getSexo() +
                    "', idDepartamento=" + empleado.getIdDepartamento() +
                    ", idEmpresa=" + empleado.getIdEmpresa() +
                    ", esResponsable=" + empleado.isEsResponsable()+
                    " WHERE id=" + empleado.getId();
            statement.executeUpdate(sql);
          } catch (SQLException ex) {
                System.out.println("Error al modificar la tabla empleado " + ex.getMessage());
            }      
            
        }
    
    public List<Empleado> buscarTodos(){
        
        List<Empleado> empleados = new ArrayList<>();
        try{
            Connection conexionDataBase = DriverManager.getConnection(EmpleadoDAO.URL_CONEXION, EmpleadoDAO.USUARIO_BDD, EmpleadoDAO.PASSWORD_BDD);
            Statement  statement = conexionDataBase.createStatement();
            String sql = "SELECT * FROM empleado ORDER BY id";
            ResultSet resultset = statement.executeQuery(sql);
            
        while(resultset.next()){
             Empleado empleado = new Empleado();
             empleado.setNombreEmpleado(resultset.getString("nombreEmpleado"));
             empleado.setFechaAlta(resultset.getDate("fecha_alta"));
             empleado.setSexo(resultset.getString("sexo"));
             empleado.setIdDepartamento(resultset.getInt("idDepartamento"));
             empleado.setIdEmpresa(resultset.getInt("idEmpresa"));
             empleado.setEsResponsable(resultset.getBoolean("es_responsable"));
             empleado.setId(resultset.getInt("id"));
             empleados.add(empleado);
            }
          
        }catch (SQLException ex) {
          System.out.println("No posible mostrar la info de la tabla empleado" + ex.getMessage());
       }
        return empleados;
    }
    
    public void eliminar(Empleado empleado) {
        try{
            Connection conexionDB = DriverManager.getConnection(EmpleadoDAO.URL_CONEXION, EmpleadoDAO.USUARIO_BDD, EmpleadoDAO.PASSWORD_BDD);
            Statement statement = conexionDB.createStatement();
            String sql = "DELETE FROM empleado WHERE id=" + empleado.getId();
            statement.executeUpdate(sql);
        }catch(Exception e){
            throw new RuntimeException("Ocurrió un error al eliminar la información de la tabla empleado " + e.getMessage());
        }
    }
    
}
