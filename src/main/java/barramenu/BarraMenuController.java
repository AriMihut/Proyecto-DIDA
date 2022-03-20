package barramenu;

import com.amm.dida.proyectoempresas.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BarraMenuController implements Initializable {

    @FXML Button mostrarPantallaChart;
    @FXML Button mostrarPantallaDepartamentos;
    @FXML Button mostrarPantallaEmpleados;
      
     @FXML
    public void mostrarPantallaChart() throws IOException{
         App.setRoot("EmpleadosPorDepartamento");
    }
    
    @FXML
    public void mostrarPantallaDepartamentos() throws IOException{
        App.setRoot("Departamento");
    }
    
     @FXML
    public void mostrarPantallaEmpleados() throws IOException{
         App.setRoot("Empleado");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}