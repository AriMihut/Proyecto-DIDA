package com.amm.dida.proyectoempresas;

import dao.DepartamentoDAO;
import dao.EmpleadoDAO;
import dao.EmpresaDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmpleadoController implements Initializable {

   @FXML private TextField nombreEmpleado;  
   @FXML private TableView<Empleado> tablaEmpleados;
   @FXML private DatePicker fechaAlta;
   @FXML private CheckBox checkSi;
   @FXML private ComboBox<Departamento> departamentos;
   @FXML private ComboBox<Empresa> empresas;
   @FXML private ComboBox<String> comboboxSexo;
   @FXML private Menu menuEmpleado;
   @FXML private MenuBar menuBar;
   @FXML private Menu menuDepartamentos;
   @FXML private Menu menuEmpresas;
   
   private ObservableList<Menu> menus;
   private EmpresaDAO empresaDAO;
   private EmpleadoDAO empleadoDAO;
   private DepartamentoDAO departamentoDAO;
   
   int id;
  
    @FXML private TableColumn<Empleado, Integer> id_columna;
    @FXML private TableColumn<Empleado, String> nombre_empleado;
    @FXML private TableColumn<Empleado, DatePicker> fecha_alta;
    @FXML private TableColumn<Empleado, String> sexo_columna;
    @FXML private TableColumn<Empleado, Integer> departamentos_columna;
    @FXML private TableColumn<Empleado, Integer> empresas_columna;
    @FXML private TableColumn<Empleado, Boolean> es_responsable;
    
    private Empleado empleadosAModificar;
    private ObservableList<Empleado> empleados = FXCollections.observableArrayList();
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        empresaDAO = new EmpresaDAO();
        departamentoDAO = new DepartamentoDAO();
        empleadoDAO = new EmpleadoDAO();
        mostrar();
        configurarCheckBox();
        configurarDepartamentos();
        configurarEmpresas();
        configurarComboBox();
        controlarTamanoColumnas();
        
        List<Departamento> departamentosEncontrados = departamentoDAO.buscarTodos();
        departamentos.getItems().addAll(departamentosEncontrados);
        configurarDepartamentos();
        
        List<Empresa> empresasEncontradas = empresaDAO.buscarTodas();
        empresas.getItems().addAll(empresasEncontradas);
        configurarEmpresas();
        
        comboboxSexo.setCellFactory(listView -> new ImagenListCell());
        comboboxSexo.setButtonCell(new ImagenListCell());
        
        menuEmpleado.showingProperty().addListener((obs, oldV, newV) ->{
                menuEmpleado.setStyle("-fx-background-color: darkgrey;");
        });
       
        menuEmpresas.showingProperty().addListener((obs, oldV, newV) ->{
                menuEmpresas.setStyle("-fx-background-color: darkgrey;");
        });
       
        menuDepartamentos.showingProperty().addListener((obs, oldV, newV) ->{
                menuDepartamentos.setStyle("-fx-background-color: darkgrey;");
        });
       
        menus = menuBar.getMenus();
               
       for(Menu menu : menuBar.getMenus()) { 
           menu.showingProperty().addListener((obs, oldV, newV) -> {
               cambiarEstilosMenu(menu);
           });
       }
       
    }  
    
    @FXML
    public void anadirModificar() throws SQLException{
          
            Empleado empleado = new Empleado();
            empleado.setId(empleadosAModificar == null ? 0 : empleadosAModificar.getId());
            empleado.setNombreEmpleado(nombreEmpleado.getText());
            LocalDate localDate = fechaAlta.getValue();
            empleado.setFechaAlta(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            empleado.setSexo(comboboxSexo.getSelectionModel().getSelectedItem());
            empleado.setIdDepartamento(departamentos.getValue().getId());
            empleado.setIdEmpresa(empresas.getValue().getId());
            if(checkSi.isSelected()){
               empleado.setEsResponsable(true);
            }else{
               empleado.setEsResponsable(false);
            }
            if(empleado.getId() == 0){
                empleadoDAO.anadir(empleado); 
            } else {
                empleadoDAO.modificar(empleado);
                empleadosAModificar = null;
            }
            
            mostrar();
            clear();
    }
    
    private void clear() {
        nombreEmpleado.clear();
        fechaAlta.setValue(null);
        comboboxSexo.getSelectionModel().selectFirst();
        departamentos.setValue(null);
        empresas.setValue(null);
        checkSi.setSelected(true);
    }
    
    private void configurarDepartamentos(){
        departamentos.getSelectionModel().selectFirst();      
    }
    
     private void configurarEmpresas(){
        empresas.getSelectionModel().selectFirst();      
    }
     
    @FXML
    public void prepararModificar() {
        Empleado empleado = tablaEmpleados.getSelectionModel().getSelectedItem();
        nombreEmpleado.setText(empleado.getNombreEmpleado());
        Date date = empleado.getFechaAlta();
        LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
        fechaAlta.setValue(localDate);
        comboboxSexo.setValue(empleado.getSexo());
        checkSi.setSelected(empleado.isEsResponsable());
        empleadosAModificar = empleado;
        id = empleado.getId();
        
        List<Departamento> itemsDepartamento = departamentos.getItems();
        for(Departamento departamento: itemsDepartamento){
            if(departamento.getId()== empleado.getIdDepartamento()){
                departamentos.setValue(departamento);
                return;
            }
        }
        
        List<Empresa> itemsEmpresa = empresas.getItems();
        for(Empresa empresa: itemsEmpresa){
            if(empresa.getId()== empleado.getIdEmpresa()){
                empresas.setValue(empresa);
                return;
            }
        }
    }
    
     private void configurarCheckBox() {
        checkSi.setSelected(true);
    }
     
     private void configurarComboBox(){
        comboboxSexo.getItems().addAll("Femenino", "Masculino");
        comboboxSexo.getSelectionModel().selectFirst();      
    }
    
    @FXML
    public void abrirDptos() throws IOException{
         App.setRoot("Departamento");
    
    }
    
    @FXML
    public void abrirEmpresas() throws IOException{
        App.setRoot("Empresa");
    }
    
    @FXML
    public void abrirGrafico() throws IOException{
        App.setRoot("EmpleadosPorDepartamento");
    }
    
    @FXML
    public void salir() throws IOException{
        App.setRoot("Login");
    }
    
    @FXML
    public void atras() throws IOException{
        App.setRoot("Departamento");
        
    }
        
    private void mostrar(){
        
        tablaEmpleados.getItems().clear();
        
        id_columna.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("id"));
        nombre_empleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
        fecha_alta.setCellValueFactory(new PropertyValueFactory<Empleado, DatePicker>("fechaAlta"));
        sexo_columna.setCellValueFactory(new PropertyValueFactory<Empleado, String>("sexo"));
        departamentos_columna.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("idDepartamento"));
        empresas_columna.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("idEmpresa"));
        es_responsable.setCellValueFactory(new PropertyValueFactory<Empleado, Boolean>("esResponsable"));
        es_responsable.setCellFactory(tc -> new TableCell<Empleado, Boolean>() {
          @Override
            protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty && item != null) {
                setText(item ? "Sí": "No");
            }
              
           }
        });    
  
        ObservableList<Empleado> empleados = FXCollections.observableArrayList();
        List<Empleado> empleadosAMostrar = empleadoDAO.buscarTodos();
        empleados.addAll(empleadosAMostrar);
        tablaEmpleados.setItems(empleados);
    }
    
    @FXML
    public void eliminar() {
       Empleado empleado = tablaEmpleados.getSelectionModel().getSelectedItem();
        empleadoDAO.eliminar(empleado);
        mostrar();
    }
    
    private void controlarTamanoColumnas(){
       tablaEmpleados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<Empleado, ?>> columnas = tablaEmpleados.getColumns();
        
        columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 5); 
        columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 15); 
        columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 15); 
        columnas.get(3).setMaxWidth(1f * Integer.MAX_VALUE * 10); 
        columnas.get(4).setMaxWidth(1f * Integer.MAX_VALUE * 20);
        columnas.get(5).setMaxWidth(1f * Integer.MAX_VALUE * 15);
        columnas.get(6).setMaxWidth(1f * Integer.MAX_VALUE * 20);
        }

    private void cambiarEstilosMenu(Menu menuMostrado) {
        for(Menu menu : menus) {
            if(!menu.equals(menuMostrado)) {
                menu.setStyle("-fx-background-color:black;");
            }
        }
    }
    
}
