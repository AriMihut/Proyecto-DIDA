package com.amm.dida.proyectoempresas;

import dao.EmpresaDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmpresaController implements Initializable{
        
        @FXML private TextField nombre; 
        @FXML private TextField email; 
        @FXML private TextField telefono; 
        @FXML private TextField ambito; 
        @FXML private TableView<Empresa> tablaEmpresas;
        @FXML private CheckBox checkSi;
        @FXML private CheckBox checkNo;
        @FXML private ComboBox<String> comboboxEmpresas;
        @FXML private MenuBar menuBarEmpresas;
        @FXML private Menu menuDepartamentos;
        @FXML private Menu menuEmpleados;
        
        @FXML private TableColumn<Empresa, Integer> id ;
        @FXML private TableColumn<Empresa, String> nombreEmpresa;
        @FXML private TableColumn<Empresa, String> emailEmpresa;
        @FXML private TableColumn<Empresa, Integer> telefonoEmpresa;
        @FXML private TableColumn<Empresa, String> ambitoEmpresa ;
        @FXML private TableColumn<Empresa, Boolean> isInternacionalColumn;
        @FXML private TableColumn<Empresa, String> mercanciaTtaEmpresa;
        
        private ObservableList<Menu> menus;
        private ObservableList<Empresa> empresas = FXCollections.observableArrayList();
        private EmpresaDAO empresaDAO;
        private Empresa empresaParaModificar;
        
       @Override
    public void initialize(URL url, ResourceBundle rb) {
        empresaDAO = new EmpresaDAO();
        mostrar();
        controlarTamanoColumnas();
        configurarCheckBox();
        configurarComboBox();
       
        menuEmpleados.showingProperty().addListener((obs, oldV, newV) ->{
                menuEmpleados.setStyle("-fx-background-color: darkgrey;");
        });
       
        menuDepartamentos.showingProperty().addListener((obs, oldV, newV) ->{
                menuDepartamentos.setStyle("-fx-background-color: darkgrey;");
        });
       
        menus = menuBarEmpresas.getMenus();
               
       for(Menu menu : menuBarEmpresas.getMenus()) { 
           menu.showingProperty().addListener((obs, oldV, newV) -> {
               cambiarEstilosMenu(menu);
           });
       }
       
    }  
    
    @FXML
    public void anadirModificar() throws SQLException{
            
            Empresa empresa = new Empresa();
            empresa.setId(empresaParaModificar == null ? 0 : empresaParaModificar.getId());
            empresa.setNombre(nombre.getText());
            empresa.setEmail(email.getText());
            empresa.setTelefono(Integer.parseInt(telefono.getText()));
            empresa.setAmbito(ambito.getText());
            if(checkSi.isSelected()){
               empresa.setInternacional(true);
            }else{
               empresa.setInternacional(false);
            }
            empresa.setMercanciaTransportada(comboboxEmpresas.getSelectionModel().getSelectedItem());
     
            if(empresa.getId() == 0){
                empresaDAO.anadir(empresa); 
            } else {
                empresaDAO.modificar(empresa);
                empresaParaModificar = null;
            }
            mostrar();
            clear();
    }
    
    private void clear() {
        nombre.clear();
        email.clear();
        telefono.clear();
        ambito.clear();
        checkNo.setSelected(true);
        comboboxEmpresas.getSelectionModel().selectFirst();
    }
     
    @FXML
    public void prepararModificar() {
        Empresa empresa = tablaEmpresas.getSelectionModel().getSelectedItem();
        nombre.setText(empresa.getNombre());
        email.setText(empresa.getEmail());
        telefono.setText(String.valueOf(empresa.getTelefono()));
        ambito.setText(empresa.getAmbito());
        checkSi.setSelected(empresa.getInternacional());
        checkNo.setSelected(empresa.getInternacional() ? !empresa.getInternacional(): empresa.getInternacional());
        comboboxEmpresas.getSelectionModel().select(empresa.getMercanciaTransportada());
        empresaParaModificar = empresa;
    }
    
    private void configurarCheckBox() {
        checkNo.setSelected(true);
        checkSi.selectedProperty().addListener((obs, oldV, newV) -> {
            checkNo.setSelected(!newV);
        });
         checkNo.selectedProperty().addListener((obs, oldV, newV) -> {
            checkSi.setSelected(!newV);
           
        });
    }
    
    private void configurarComboBox(){
        comboboxEmpresas.getItems().addAll("Componentes Informática", "Libros", "Oficina", "Transporte");
        comboboxEmpresas.getSelectionModel().selectFirst();      
    }
    
    @FXML
    public void abrirDptos() throws IOException{
         App.setRoot("Departamento");
    
    }
    
    @FXML
    public void abrirEmpl() throws IOException{
        App.setRoot("Empleado");
    }
    
    @FXML
    public void salir() throws IOException{
        App.setRoot("Login");
    }
    
    @FXML
    public void puerta() throws IOException{
        App.setRoot("Login");
    }
     
    @FXML
    public void eliminar() {
        Empresa empresa = tablaEmpresas.getSelectionModel().getSelectedItem();
        empresaDAO.eliminar(empresa);
        mostrar();
    }
        
    private void mostrar(){
        tablaEmpresas.getItems().clear();
        
        id.setCellValueFactory(new PropertyValueFactory<Empresa, Integer>("id"));
        nombreEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nombre"));
        emailEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("email"));
        telefonoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, Integer>("telefono"));
        ambitoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("ambito"));
        isInternacionalColumn.setCellValueFactory(new PropertyValueFactory<Empresa, Boolean>("internacional"));
        
        isInternacionalColumn.setCellFactory(tc -> new TableCell<Empresa, Boolean>() {
          @Override
            protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty && item != null) {
                setText(item ? "Sí": "No");
            }
              
           }
        });

        mercanciaTtaEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("mercanciaTransportada"));
     
        List<Empresa> empresasAMostrar = empresaDAO.buscarTodas();
        empresas.addAll(empresasAMostrar);
        tablaEmpresas.setItems(empresas);
    }
    
    private void controlarTamanoColumnas(){
        tablaEmpresas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<Empresa, ?>> columnas = tablaEmpresas.getColumns();
        
        columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 5); 
        columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 10); 
        columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 10); 
        columnas.get(3).setMaxWidth(1f * Integer.MAX_VALUE * 15); 
        columnas.get(4).setMaxWidth(1f * Integer.MAX_VALUE * 15);
        columnas.get(5).setMaxWidth(1f * Integer.MAX_VALUE * 15);
        columnas.get(6).setMaxWidth(1f * Integer.MAX_VALUE * 30);
    }
    
     private void cambiarEstilosMenu(Menu menuMostrado) {
        for(Menu menu : menus) {
            if(!menu.equals(menuMostrado)) {
                menu.setStyle("-fx-background-color:black;");
            }
        }
    }
    
}
        


    

