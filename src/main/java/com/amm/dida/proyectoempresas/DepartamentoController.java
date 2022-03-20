package com.amm.dida.proyectoempresas;

import dao.DepartamentoDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class DepartamentoController implements Initializable {
   
   @FXML private TextField nombreDpto, presupuesto;  
   @FXML private TableView<Departamento> tablaDepartamentos;
   @FXML private RadioButton finanzas, informatica, logistica, formacion;
   @FXML ToggleGroup group;
   @FXML private MenuBar menuBarDepartamentos;
   @FXML private Menu menuEmpleados;
   @FXML private Menu menuEmpresas;
  
    @FXML private TableColumn<Departamento, Integer> id;
    @FXML private TableColumn<Departamento, Enum> ambito_departamento;
    @FXML private TableColumn<Departamento, String> nombre_departamento;
    @FXML private TableColumn<Departamento, Double> presupuesto_departamento;

    private ObservableList<Menu> menus;
    private Departamento departamentosAModificar;
    private DepartamentoDAO departamentoDAO;
    private ObservableList<Departamento> departamentos = FXCollections.observableArrayList();
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        departamentoDAO = new DepartamentoDAO();
        mostrar();
        configurarRadioBox();
        controlarTamanoColumnas();
        
        menuEmpleados.showingProperty().addListener((obs, oldV, newV) ->{
                menuEmpleados.setStyle("-fx-background-color: darkgrey;");
        });
       
        menuEmpresas.showingProperty().addListener((obs, oldV, newV) ->{
                menuEmpresas.setStyle("-fx-background-color: darkgrey;");
        });
       
        menus = menuBarDepartamentos.getMenus();
               
       for(Menu menu : menuBarDepartamentos.getMenus()) { 
           menu.showingProperty().addListener((obs, oldV, newV) -> {
               cambiarEstilosMenu(menu);
           });
       }
    }  
    
    @FXML
    public void anadirModificar() throws SQLException{
          
            Departamento departamento = new Departamento();
            departamento.setId(departamentosAModificar == null ? 0 : departamentosAModificar.getId());
           
            if (finanzas.isSelected()) {
                       departamento.setAmbitoDpto(Departamento.AmbitoDpto.Finanzas);
                    }
            else if(informatica.isSelected()) {
                        departamento.setAmbitoDpto(Departamento.AmbitoDpto.Informática);
                    }
            else if (logistica.isSelected()) {
                        departamento.setAmbitoDpto(Departamento.AmbitoDpto.Logística);
                    }
            else if (formacion.isSelected()) {
                         departamento.setAmbitoDpto(Departamento.AmbitoDpto.Formación);
                    }
            departamento.setNombreDpto(nombreDpto.getText());
            departamento.setPresupuesto(Double.parseDouble(presupuesto.getText()));
            
            if(departamento.getId() == 0){
                departamentoDAO.anadir(departamento); 
            } else {
                departamentoDAO.modificar(departamento);
                departamentosAModificar = null;
            }
            
            mostrar();
            clear();
    }
    
    private void clear() {
        finanzas.setSelected(true);
        informatica.setSelected(false);
        logistica.setSelected(false);
        formacion.setSelected(false);
        nombreDpto.clear();
        presupuesto.clear();
    }
     
    @FXML
    public void prepararModificar() {
      Departamento departamento = tablaDepartamentos.getSelectionModel().getSelectedItem();
      Departamento.AmbitoDpto ambitoModif = departamento.getAmbitoDpto();
      switch(departamento.getAmbitoDpto().toString()) {
          case "Finanzas" :
              finanzas.setSelected(true);
              break;
          case "Informática" :
              informatica.setSelected(true);
              break;
          case "Logística" :
              logistica.setSelected(true);
              break;
          case "Formación" :
              formacion.setSelected(true);
              break;
      }
      nombreDpto.setText(departamento.getNombreDpto());
      presupuesto.setText(String.valueOf(departamento.getPresupuesto()));
      departamentosAModificar = departamento;
    }
    
    @FXML
    public void abrirEmpresas() throws IOException{
         App.setRoot("Empresa");
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
    public void atras() throws IOException{
        App.setRoot("Empresa");
        
    }
        
    private void mostrar(){
        
        tablaDepartamentos.getItems().clear();
        
        id.setCellValueFactory(new PropertyValueFactory<Departamento, Integer>("id"));
        ambito_departamento.setCellValueFactory(new PropertyValueFactory<Departamento, Enum>("ambitoDpto"));
        nombre_departamento.setCellValueFactory(new PropertyValueFactory<Departamento, String>("nombreDpto"));
        presupuesto_departamento.setCellValueFactory(new PropertyValueFactory<Departamento, Double>("presupuesto"));
  
        List<Departamento> departamentosAMostrar = departamentoDAO.buscarTodos();
        departamentos.addAll(departamentosAMostrar);
        tablaDepartamentos.setItems(departamentos);
    }
    
    @FXML
    public void eliminar() {
       Departamento departamento = tablaDepartamentos.getSelectionModel().getSelectedItem();
        departamentoDAO.eliminar(departamento);
        mostrar();
    }
    
     private void configurarRadioBox(){
        finanzas.setSelected(true);
     }
    
    private void controlarTamanoColumnas(){
        tablaDepartamentos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<Departamento, ?>> columnas = tablaDepartamentos.getColumns();
        
        columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 15); 
        columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 20); 
        columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 25); 
        columnas.get(3).setMaxWidth(1f * Integer.MAX_VALUE * 15); 
       
        }
    
    private void cambiarEstilosMenu(Menu menuMostrado) {
        for(Menu menu : menus) {
            if(!menu.equals(menuMostrado)) {
                menu.setStyle("-fx-background-color:black;");
            }
        }
    }

    }
    

