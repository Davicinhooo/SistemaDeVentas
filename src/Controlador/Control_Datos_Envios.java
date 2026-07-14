package Controlador;

import Modelo.Categoria;
import Modelo.ConsultaDatos_Envio;
import Modelo.Datos_Envio;
import Vista.frmDatos_Envios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Control_Datos_Envios implements ActionListener{
    
    private final Datos_Envio modelo;
    private final ConsultaDatos_Envio consulta;
    private final frmDatos_Envios vista;
    
    public Control_Datos_Envios(Datos_Envio modelo, ConsultaDatos_Envio consulta, frmDatos_Envios vista) {
        this.modelo = modelo;
        this.consulta = consulta;
        this.vista = vista;
        
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);
    }
    
    public void iniciar(){
        vista.setTitle("Tabla sobre datos de envio");
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        llenarTabla();
    }
    
    public void llenarTabla(){
        List<Datos_Envio> listadt = consulta.listarDatosEnvio();
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tblDatosEnvio.getModel();
        modeloTabla.setRowCount(0);
        Object[] fila = new Object[5];
        
        for(int i = 0; i <listadt.size(); i++){
            fila[0] = listadt.get(i).getID_Envio();
            fila[1] = listadt.get(i).getID_Pedido();
            fila[2] = listadt.get(i).getDireccion();
            fila[3] = listadt.get(i).getReferencia();
            fila[4] = listadt.get(i).getContacto();
            modeloTabla.addRow(fila);
        } 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnGuardar){
            String direccion = vista.txtDireccion.getText();
            String referencia = vista.txtReferencia.getText();
            String contacto = vista.txtContacto.getText();
            
            if(direccion.isEmpty() || referencia.isEmpty() || contacto.isEmpty()){
                JOptionPane.showMessageDialog(null, "Llenar los campos correspondientes, por favor", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            modelo.setDireccion(direccion);
            modelo.setReferencia(referencia);
            modelo.setContacto(contacto);
            
            if(consulta.guardar(modelo)){
                JOptionPane.showMessageDialog(null, "Datos de envio guardados exitosamente");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudieron guardar los datos de envio");
            }
        }
        if(e.getSource() == vista.btnModificar){
            modelo.setID_Envio(Integer.parseInt(vista.txtId.getText()));
            modelo.setDireccion(vista.txtDireccion.getText());
            modelo.setReferencia(vista.txtReferencia.getText());
            modelo.setContacto(vista.txtContacto.getText());
            
            if(consulta.modificar(modelo)){
                JOptionPane.showMessageDialog(null, "Registro modificado exitosamente");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se logro modificar el registro");
            }
        }
        if(e.getSource() == vista.btnBuscar){
            String id = vista.txtId.getText();
            
            if(id.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor colocar un ID", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            modelo.setID_Envio(Integer.parseInt(id));
            
            if(consulta.buscar(modelo)){
                vista.txtDireccion.setText(modelo.getDireccion());
                vista.txtReferencia.setText(modelo.getReferencia());
                vista.txtContacto.setText(modelo.getContacto());
            } else {
                JOptionPane.showMessageDialog(null, "No eciste el ID proporcionado");
            }
        }
        if(e.getSource() == vista.btnEliminar){
            String id = vista.txtId.getText();
            
            if(id.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor, busque la empresa primero para obtener su ID.", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            modelo.setID_Envio(Integer.parseInt(id));
            
            if(consulta.eliminar(modelo)){
                JOptionPane.showMessageDialog(null, "Datos de envio eliminados correctamente");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar los datos de envio");
            }
        }
        if(e.getSource() == vista.btnLimpiar){
            Limpiar();
    }
        if(e.getSource() == vista.btnMenu){
            Vista.frmGestorVentas vistaMenu = new Vista.frmGestorVentas();
            Controlador.ControlGestor ctrlg = new Controlador.ControlGestor(vistaMenu);
            vistaMenu.setVisible(true);
            vistaMenu.setLocationRelativeTo(null);
            this.vista.dispose();
        }
    }
    public void Limpiar(){
        vista.txtId.setText(null);
        vista.txtDireccion.setText(null);
        vista.txtReferencia.setText(null);
        vista.txtContacto.setText(null);
    } 
}
    