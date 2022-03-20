package com.amm.dida.proyectoempresas;

import dao.DepartamentoDAO;
import dao.EmpleadoDAO;
import dao.EmpresaDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LoginController implements Initializable {
    
    @FXML private TextField usuario;
    @FXML private TextField contrasena;
    @FXML private Label etiquetaAviso;
    @FXML private Button autentificarse;
    @FXML private Button registrarse;
    @FXML private HBox contenedorRegistro;
    private EmpresaDAO empresaDao = new EmpresaDAO();
    private UsuarioDAO usuarioDao = new UsuarioDAO();
    private EmpleadoDAO empleadoDao = new EmpleadoDAO();
    private DepartamentoDAO departamentoDao = new DepartamentoDAO();
    
    

@Override
    public void initialize(URL url, ResourceBundle rb) {

        usuarioDao.crearTablaSiNoexiste();
        empresaDao.crearTablaSiNoExiste();
        departamentoDao.crearTablaSiNoExiste();
        empleadoDao.crearTablaSiNoExiste();
    }    

    public void autentificarse() throws SQLException, IOException{
        
         Usuario nuevoUsuario = new Usuario(0, usuario.getText(), contrasena.getText());
         if(comprobacionesUsuario()) {
             if(usuarioDao.comprobarUsuario(nuevoUsuario)){
                App.setRoot("Empresa"); 
             }       
             else {
                 mostrarAviso("El usuario o la contraseña están erróneos");
             }
           
         } else{
            mostrarAviso("Todos los campos deben estar cubiertos!");
         }
      
    }
    
    private boolean comprobacionesUsuario() {
        if(!todosCamposCubiertos()) {
            mostrarAviso("Todos los campos son obligatorios");
            return false;
        } else{
            return true;
        }
    }
    
     private boolean todosCamposCubiertos(){
        return !usuario.getText().isEmpty() && !contrasena.getText().isEmpty();
    }
    
    private void mostrarAviso(String text) {
        etiquetaAviso.setText(text);
        etiquetaAviso.setVisible(true);
    }
    
    @FXML
    public void registrarse() throws IOException {
          App.setRoot("Registro");       
    }
    
}
