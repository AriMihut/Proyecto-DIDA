package reporte;

import com.amm.dida.proyectoempresas.App;
import dao.DepartamentoDAO;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

public class EmpleadosPorDepartamentoController implements Initializable{
    
    DepartamentoDAO departamentoDao;
    @FXML 
    private PieChart chart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        departamentoDao = new DepartamentoDAO();  
            cargarDatosEnElChart();
            System.out.println("Mergi?");
        
         
    }
    
    @FXML
    public void atras() throws IOException{
        App.setRoot("Empleado");
        
    }

    private void cargarDatosEnElChart() {
       System.out.println("Mergi2?");
       Map<String, Integer> empleadosPorDepartamento = departamentoDao.contarEmpleadosPorDepartamento();
       System.out.println("Da?");
       ObservableList<PieChart.Data> datosParaElChart = FXCollections.observableArrayList();
       System.out.println("Da");
       empleadosPorDepartamento.forEach((nombreDpto, cantidad) ->
       {
           System.out.println("Hai, na!");
           PieChart.Data data = new PieChart.Data(nombreDpto, cantidad);
           System.out.println("Mergi25?");
           datosParaElChart.add(data);
           System.out.println("Daaa?");
       });
       System.out.println("Mergi3?");
       chart.setData(datosParaElChart);
       System.out.println("Mergi4?");
    }
}
