package Controlador;

import Vista.frmProve;
import Modelo.Proveedor;
import Modelo.ConsultaProveedor;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

public class ControlProveedor implements ActionListener{
    private final Proveedor modelo;
    private final ConsultaProveedor consulta;
    private final frmProve vista;

    public ControlProveedor(Proveedor modelo, ConsultaProveedor consulta, frmProve vista) {
        this.modelo = modelo;
        this.consulta = consulta;
        this.vista = vista;
        
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);
    }
    
    public void iniciar(){
       vista.setTitle("Formulario Proveedor");
       vista.setResizable(true);
       vista.setLocationRelativeTo(null);
       llenarTabla();
        
    }
    public void llenarTabla(){
        List<Proveedor> listaPro = consulta.listaProveedor();
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tblProveedor.getModel();
        modeloTabla.setRowCount(0);
        Object[] fila = new Object[4];
        
        for(int i = 0; i < listaPro.size(); i++){
            fila[0] = listaPro.get(i).getId_proveedor();
            fila[1] = listaPro.get(i).getNombre_empresa();
            fila[2] = listaPro.get(i).getContacto();
            fila[3] = listaPro.get(i).getTelefono();
            modeloTabla.addRow(fila);
        }           
 }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnGuardar){
            String Nombre_empresa = vista.txtEmpresa.getText();
            String Contacto = vista.txtContacto.getText();
            String Telefono = vista.txtTelefono.getText();
            
            if(Nombre_empresa.isEmpty() || Contacto.isEmpty() || Telefono.isEmpty()){
                JOptionPane.showMessageDialog(null, "Porfavor, completar los campos vacios", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            
            modelo.setNombre_empresa(Nombre_empresa);
            modelo.setContacto(Contacto);
            modelo.setTelefono(Telefono);
            
            if(consulta.guardar(modelo)){
                JOptionPane.showMessageDialog(null, "Proveedor guardado!");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar el proveedor");
            }
        }
        
        if(e.getSource() == vista.btnModificar){
            modelo.setId_proveedor(Integer.parseInt(vista.txtId.getText()));
            modelo.setNombre_empresa(vista.txtEmpresa.getText());
            modelo.setContacto(vista.txtContacto.getText());
            modelo.setTelefono(vista.txtTelefono.getText());
            
            if(consulta.modificar(modelo)){
                JOptionPane.showMessageDialog(null, "Registro modificado exitosamente");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar el registro");
            }
        }
        
       if(e.getSource() == vista.btnEliminar){
            
            if(vista.txtId.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor, busque la empresa primero para obtener su ID.", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            modelo.setId_proveedor(Integer.parseInt(vista.txtId.getText()));
            
            if(consulta.eliminar(modelo)){
                JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente de la base de datos.");
                llenarTabla();  
                Limpiar();
            } else { 
                JOptionPane.showMessageDialog(null, "Error: No se pudo eliminar. Existen productos asociados a este proveedor.");
            }
        }
        
        if(e.getSource() == vista.btnBuscar){
            String id = vista.txtId.getText();
            
            if(id.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor ingrese el ID", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            modelo.setId_proveedor(Integer.parseInt(id));
              
            
            if(consulta.buscar(modelo)){
                vista.txtEmpresa.setText(modelo.getNombre_empresa());
                vista.txtContacto.setText(modelo.getContacto());
                vista.txtTelefono.setText(modelo.getTelefono());
            } else {
                JOptionPane.showMessageDialog(null, "No existe el proveedor");
                Limpiar();
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
        vista.txtEmpresa.setText(null);
        vista.txtContacto.setText(null);
        vista.txtTelefono.setText(null);
    }
    
}
