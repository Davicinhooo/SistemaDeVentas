package mainLogin;

import Modelo.Login;
import Modelo.ConsultaLogin;
import Vista.frmLogin;
import Controlador.ControlLogin;

public class mainLogin {

    public static void main(String[] args) {
        
        Login modelo = new Login();
        ConsultaLogin consulta = new ConsultaLogin();
        frmLogin vista = new frmLogin();
        
        ControlLogin ctrl = new ControlLogin(modelo, consulta, vista);
        ctrl.inicar();
        vista.setVisible(true);
    }    
}
