package Controlador;

import Modelo.Categoria;
import Modelo.ConsultaCategoria;
import Vista.frmCateg;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControlCategoria implements ActionListener{
    
    private final Categoria modelo;
    private final ConsultaCategoria consulta;
    private final frmCateg vista;

    public ControlCategoria(Categoria modelo, ConsultaCategoria consulta, frmCateg vista) {
        this.modelo = modelo;
        this.consulta = consulta;
        this.vista = vista;
        
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
    }
    
    public void iniciar(){
        vista.setTitle("Tabla de categorias");
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        llenarTabla();
    }
    
    public void llenarTabla(){
        List<Categoria> listaCat = consulta.listarCategoria();
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tblcategoria.getModel();
        modeloTabla.setRowCount(0);
        Object[] fila = new Object[2];
        
        for(int i = 0; i < listaCat.size(); i++){
            fila[0] = listaCat.get(i).getId_categoria();
            fila[1] = listaCat.get(i).getNombre();
            modeloTabla.addRow(fila);
        }           
 }  

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnGuardar){  
            String nombre = vista.txtnombre.getText();
            
            if(nombre.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor completar el campo", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            modelo.setNombre(nombre);
            
            if(consulta.guardar(modelo)){
                JOptionPane.showMessageDialog(null, "Categoría guardada con éxito");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Categoría no añadida");    
            }
        }
        
        if(e.getSource() == vista.btnBuscar){
            String id = vista.txtId.getText();
            if(id.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor ingrese el nombre a buscar", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            modelo.setId_categoria(Integer.parseInt(id));
            
            if(consulta.buscar(modelo)){
                vista.txtnombre.setText(modelo.getNombre());
            } else {
                JOptionPane.showMessageDialog(null, "No existe la categoria");
            }
        }
        
        if(e.getSource() == vista.btnModificar) {
            modelo.setId_categoria(Integer.parseInt(vista.txtId.getText()));
            modelo.setNombre(vista.txtnombre.getText());
            
            
            if(consulta.modificar(modelo)){
                JOptionPane.showMessageDialog(null, "Categoria modificada exitosamente");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar la categoria");
            }
        }
        
        if(e.getSource() == vista.btnEliminar) {
            
            String id_categoria = vista.txtId.getText();
            String nombre = vista.txtnombre.getText();
            
            if(id_categoria.isEmpty() || nombre.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor completar el campo", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            modelo.setId_categoria(Integer.parseInt(id_categoria));
            modelo.setNombre(nombre);
            
            if(consulta.eliminar(modelo)){
                JOptionPane.showMessageDialog(null, "Categoría eliminada con éxito");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar");
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
        vista.txtnombre.setText(null);
    } 
}
