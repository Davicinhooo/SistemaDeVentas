package Controlador;

import Modelo.Login;
import Modelo.ConsultaLogin;
import Vista.frmLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControlLogin implements ActionListener{
    
    private final Login modelo;
    private final ConsultaLogin consulta;
    private final frmLogin vista;

    public ControlLogin(Login modelo, ConsultaLogin consulta, frmLogin vista) {
        this.modelo = modelo;
        this.consulta = consulta;
        this.vista = vista;
        
        this.vista.btnLogin.addActionListener(this);
    }
    
    public void inicar(){
        vista.setTitle("Login");
        vista.setLocationRelativeTo(null);
        vista.txtId.setVisible(false);  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnLogin){
        String usuario = vista.txtUsuario1.getText();
        String contrasena = vista.txtContrasena.getText();
        
        if(usuario.isEmpty() || contrasena.isEmpty()){
            JOptionPane.showMessageDialog(null, "Porfavor, llenar todos los campos correspondientes", "AVISO IMPORTANTE", JOptionPane.WARNING_MESSAGE);
            return;
            
        }
        modelo.setUsuario(usuario);
        modelo.setContrasena(contrasena);
            
        if(consulta.Login(modelo)){
            JOptionPane.showMessageDialog(null, "Bienvenido");
            
            Vista.frmGestorVentas vistaMenu = new Vista.frmGestorVentas();
            Controlador.ControlGestor ctrlMenu = new Controlador.ControlGestor(vistaMenu);
            vistaMenu.setVisible(true);
            vistaMenu.setLocationRelativeTo(null);
            this.vista.dispose();
          
            vista.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseñas incorrectas");
        }
        }
    }
    }
    
