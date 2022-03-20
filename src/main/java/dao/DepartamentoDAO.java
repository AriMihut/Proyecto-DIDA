package dao;

import com.amm.dida.proyectoempresas.Departamento;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;

public class DepartamentoDAO {
    
    public static final String URL_CONEXION = "jdbc:mysql://localhost:3306/empresa?zeroDateTimeBehavior=CONVERT_TO_NULL";
    public static final String USUARIO_BDD = "root";
    public static final String PASSWORD_BDD = "";
    
    public DepartamentoDAO(){
       
        /*crearTablaSiNoExiste();*/
    }

    public void crearTablaSiNoExiste() {
        
        try (Connection conexionDataBase =
            DriverManager.getConnection(DepartamentoDAO.URL_CONEXION, DepartamentoDAO.USUARIO_BDD, DepartamentoDAO.PASSWORD_BDD)){  
            Statement statement = conexionDataBase.createStatement(); 
            String sql = "CREATE TABLE IF NOT EXISTS departamento" +
                    "(id INTEGER auto_increment PRIMARY KEY, " +
                    "ambitoDpto ENUM('Finanzas', 'Informática', 'Logística', 'Formación'), " +
                    "nombreDpto VARCHAR(255), " +
                    "presupuesto DOUBLE )"; 
       
            System.out.println("Crear tabla dpto sql: " + statement.executeUpdate(sql));

        } catch (SQLException ex) {
            System.out.println("Error al crear la tabla departamento");
            System.out.println(ex.getMessage());
        }
    }
    
    public Map<String, Integer> contarEmpleadosPorDepartamento() {
        List<Departamento> departamentos = buscarTodos();
        Map<String, Integer> empleadosPorDepartamento = new HashMap<>();
        
        try (
            Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "SELECT idDepartamento, count(*) as cantidad FROM "
                    + "empleado GROUP BY idDepartamento";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                int idDepartamento = resultSet.getInt("idDepartamento");
                int cantidadEmpleados = resultSet.getInt("cantidad");
                for(Departamento departamento : departamentos){
                    if(departamento.getId() == idDepartamento){
                        empleadosPorDepartamento.put(departamento.getNombreDpto(), cantidadEmpleados);
                        break;
                    }
                }
                
            }    
        }catch(Exception e){
            System.out.println("Error al mostrar el gráfico: " + e.getMessage());
        }
        return empleadosPorDepartamento;
    }
    
    public void anadirModificar(Departamento departamento){
        if(departamento.getId() == 0){
            anadir(departamento);
        }else{
            modificar(departamento);
        }
    }
    
    @FXML
    public void anadir(Departamento departamento){
        try (
            Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "INSERT INTO departamento(ambitoDpto, nombreDpto, presupuesto) " +
                "VALUES ('" + departamento.getAmbitoDpto() + 
                    "', '" + departamento.getNombreDpto() + 
                    "', " + departamento.getPresupuesto() + ")"; 
            
            statement.executeUpdate(sql);  
            System.out.println("sql insertar dpto===> " + sql);
            
          } catch (SQLException ex) {
                System.out.println("Error al introducir información en la tabla departamento" + ex.getMessage());
          }        
        
        }
    
    public void modificar(Departamento departamento) {
        
         try(
            Connection conexionDataBase =
            DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)){
            Statement statement = conexionDataBase.createStatement();
            String sql = "UPDATE departamento set ambitoDpto='" + departamento.getAmbitoDpto() + 
                    "', nombreDpto='" + departamento.getNombreDpto()+ 
                    "', presupuesto=" + departamento.getPresupuesto()+
                    " WHERE id=" + departamento.getId();
            statement.executeUpdate(sql);
          } catch (SQLException ex) {
                System.out.println("Error al modificar la tabla departamento" + ex.getMessage());
            }      
            
        }
    
    public List<Departamento> buscarTodos(){
        
        List<Departamento> departamentos = new ArrayList<>();
        try{
            Connection conexionDataBase = DriverManager.getConnection(DepartamentoDAO.URL_CONEXION, DepartamentoDAO.USUARIO_BDD, DepartamentoDAO.PASSWORD_BDD);
            Statement  statement = conexionDataBase.createStatement();
            String sql = "SELECT * FROM departamento ORDER BY id";
            ResultSet resultset = statement.executeQuery(sql);
            
        while(resultset.next()){
             Departamento departamento = new Departamento();
             departamento.setAmbitoDpto(Departamento.AmbitoDpto.valueOf(resultset.getString("ambitoDpto")));
             departamento.setNombreDpto(resultset.getString("nombreDpto"));
             departamento.setPresupuesto(resultset.getDouble("presupuesto"));
             departamento.setId(resultset.getInt("id"));
             departamentos.add(departamento);
            }
          
        }catch (SQLException ex) {
          System.out.println("No posible mostrar la info de la tabla departamento");
            System.out.println("Error al mostrar los dptos " + ex.getMessage());
       }
        return departamentos;
    }
    
    @FXML
    public void eliminar(Departamento departamento) {
        try{
            Connection conexionDB = DriverManager.getConnection(DepartamentoDAO.URL_CONEXION, DepartamentoDAO.USUARIO_BDD, DepartamentoDAO.PASSWORD_BDD);
            Statement statement = conexionDB.createStatement();
            String sql = "DELETE FROM departamento WHERE id=" + departamento.getId();
            statement.executeUpdate(sql);
        }catch(Exception e){
            throw new RuntimeException("Ocurrió un error al eliminar la información de la tabla departamento " + e.getMessage());
        }
     }
    
}
