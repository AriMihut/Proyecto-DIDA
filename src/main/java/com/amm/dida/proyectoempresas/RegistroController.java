package com.amm.dida.proyectoempresas;
    
import dao.EmpresaDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RegistroController implements Initializable {

@FXML private TextField usuario;
@FXML private TextField contrasena;
@FXML private TextField repetirContrasena;
@FXML private Label etiquetaAviso;
@FXML private Button aceptar;
@FXML private Button volverALogin;
EmpresaDAO empresaDao;
UsuarioDAO usuarioDao;
int id;

@Override
    public void initialize(URL url, ResourceBundle rb) {

    try {
       aceptar();
    } catch (IOException ex) {
        System.out.println("ex");
        System.out.println("Error al registrarse");
        }
    }
    
    private boolean comprobarContraseñasCoincidentes(){
        return repetirContrasena.getText().equals(contrasena.getText());
    }
    
    private boolean todosCamposCubiertos(){
        return !usuario.getText().isEmpty() && !contrasena.getText().isEmpty() && 
                !repetirContrasena.getText().isEmpty();
    }
    
    private void cambiarEstilosError(TextField campo) {
        campo.getStyleClass().add("textField-error");
        System.out.println(campo.getStyleClass());
    }
    
    @FXML
    public void aceptar() throws IOException{
       if(compobacionesUsuario()){
          guardarUsuario(usuario.getText(), contrasena.getText());
            App.setRoot("Empresa"); 
       }
    }
    
    private boolean compobacionesUsuario() {
         if(!todosCamposCubiertos()) {
            comprobarTextFieldsVaciosError();
            mostrarAviso("Todos los campos son obligatorios");
            return false;
        } else {
            if(!comprobarContraseñasCoincidentes()) {
                cambiarEstilosError(contrasena);
                cambiarEstilosError(repetirContrasena);
                mostrarAviso("Las contraseñas no coinciden");
                return false;
            }
        }
        
       return todosCamposCubiertos() && comprobarContraseñasCoincidentes()? true :false;
           
    }
    
    private void comprobarTextFieldsVaciosError(){
        if(usuario.getText().isEmpty()){
            cambiarEstilosError(usuario);
        }
        
        if(contrasena.getText().isEmpty()){
            cambiarEstilosError(contrasena);
        }
        
        if(repetirContrasena.getText().isEmpty()){
            cambiarEstilosError(repetirContrasena);
        }
        
    }
    
    private void mostrarAviso(String text) {
        etiquetaAviso.setText(text);
        etiquetaAviso.setVisible(true);
    }
    
    @FXML
    public void volverALogin()  throws IOException {
       App.setRoot("Login");

    }
    
    private void guardarUsuario(String nombre, String contrasena) {
        //TODO no inserta
        usuarioDao = new UsuarioDAO();
        Usuario usuario = new Usuario(0, nombre, contrasena);
        usuarioDao.anadir(usuario);
       
    }

}

