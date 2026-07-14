package Controlador;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import Modelo.Producto;
import Modelo.ConsultaProducto;
import Vista.frmProdu;


public class ControlProducto implements ActionListener{
    
    private final Producto modelo;
    private final ConsultaProducto consulta;
    private final frmProdu vista;

    public ControlProducto(Producto modelo, ConsultaProducto consulta, frmProdu vista) {
        this.modelo = modelo;
        this.consulta = consulta;
        this.vista = vista;
        
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);
        this.vista.cbCategoria.addActionListener(this);
        this.vista.cbProveedor.addActionListener(this);
    }
    
    public void iniciar(){
        vista.setTitle("Formulario de Proveedores");
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        llenarTabla();
        llenarComboBoxes();
    }
    
    public void llenarTabla(){
        List<Producto> listaPro = consulta.listarProducto();
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tblProductos.getModel();
        modeloTabla.setRowCount(0);
        Object[] fila = new Object[8];
        
        for(int i = 0; i < listaPro.size(); i++){
            fila[0] = listaPro.get(i).getId_producto();
            fila[1] = listaPro.get(i).getNombre();
            fila[2] = listaPro.get(i).getNombre_empresa();
            fila[3] = listaPro.get(i).getNombre_prod();
            fila[4] = listaPro.get(i).getDescripcion();
            fila[5] = listaPro.get(i).getPrecio();
            fila[6] = listaPro.get(i).getStock_disponible();
            modeloTabla.addRow(fila);
        }
    }
    
    public void llenarComboBoxes(){
        
        vista.cbCategoria.removeAllItems();
        vista.cbProveedor.removeAllItems();
        
        Modelo.ConsultaCategoria consCat = new Modelo.ConsultaCategoria();
        List<Modelo.Categoria> categorias = consCat.listarCategoria();
        for(Modelo.Categoria c : categorias){
            vista.cbCategoria.addItem(c.getNombre());
        }
        
        Modelo.ConsultaProveedor consPro = new Modelo.ConsultaProveedor();
        List<Modelo.Proveedor> Proveedor = consPro.listaProveedor();
        for(Modelo.Proveedor pr : Proveedor){
            vista.cbProveedor.addItem(pr.getNombre_empresa());
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnGuardar){
            String nombre = vista.txtNombre.getText();
            String descripcion = vista.txtDescripcion.getText();
            String precio = vista.txtPrecio.getText();
            String stock = vista.txtStock.getText();
            
            if(nombre.isEmpty() || descripcion.isEmpty() || precio.isEmpty() || stock.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Porfavor, completar los campos solicitados", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String nombreCat = vista.cbCategoria.getSelectedItem().toString();
            int idCatConvertidor = 0;
            Modelo.ConsultaCategoria consCat = new Modelo.ConsultaCategoria();
            for(Modelo.Categoria c : consCat.listarCategoria()){
                if(c.getNombre().equals(nombreCat)){
                    idCatConvertidor = c.getId_categoria();
                    break;
                }
            }
            
            String nombreProv = vista.cbProveedor.getSelectedItem().toString();
            int idProvConvertidor = 0;
            Modelo.ConsultaProveedor consProv = new Modelo.ConsultaProveedor();
            for(Modelo.Proveedor p : consProv.listaProveedor()){
                if(p.getNombre_empresa().equals(nombreProv)){
                    idProvConvertidor = p.getId_proveedor();
                    break;
                }
                    
            }
            
            modelo.setId_categoria(idCatConvertidor);
            modelo.setId_proveedor(idProvConvertidor);
            modelo.setNombre_prod(nombre);
            modelo.setDescripcion(descripcion);
            modelo.setPrecio(Double.parseDouble(precio));
            modelo.setStock_disponible(Integer.parseInt(stock));
            
            if(consulta.guardar(modelo)){
                JOptionPane.showMessageDialog(null, "Producto guardado exitosamente");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar el producto");
            }           
        }
        
        if(e.getSource() == vista.btnEliminar){
            modelo.setId_producto(Integer.parseInt(vista.txtId.getText()));
            
            if(consulta.eliminar(modelo)){
                JOptionPane.showMessageDialog(null, "Registro elimado exitosamente");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No existe el producto");
            }
        }
        
        if(e.getSource() == vista.btnModificar){
            
            String nombreCat = vista.cbCategoria.getSelectedItem().toString();
            int idCatConvertidor = 0;
            Modelo.ConsultaCategoria consCat = new Modelo.ConsultaCategoria();
            for(Modelo.Categoria c : consCat.listarCategoria()){
                if(c.getNombre().equals(nombreCat)){
                    idCatConvertidor = c.getId_categoria();
                    break;
        }
    }

            String nombreProv = vista.cbProveedor.getSelectedItem().toString();
            int idProvConvertidor = 0;
            Modelo.ConsultaProveedor consProv = new Modelo.ConsultaProveedor();
            for(Modelo.Proveedor p : consProv.listaProveedor()){
                if(p.getNombre_empresa().equals(nombreProv)){
                    idProvConvertidor = p.getId_proveedor();
                    break;
        }
    }

            modelo.setId_categoria(idCatConvertidor);
            modelo.setId_proveedor(idProvConvertidor);
            modelo.setNombre_prod(vista.txtNombre.getText());
            modelo.setDescripcion(vista.txtDescripcion.getText());
            modelo.setPrecio(Double.parseDouble(vista.txtPrecio.getText()));
            modelo.setStock_disponible(Integer.parseInt(vista.txtStock.getText()));
            
            

            if(consulta.modificar(modelo)){
                JOptionPane.showMessageDialog(null, "Se modificó el registro exitosamente");
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar el registro");
    }
}
        
        if(e.getSource() == vista.btnBuscar){
            String id = vista.txtId.getText();
            
            if(id.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            modelo.setId_producto(Integer.parseInt(id));
            
            if(consulta.buscar(modelo)){
                vista.txtNombre.setText(modelo.getNombre_prod());
                vista.txtDescripcion.setText(modelo.getDescripcion());
                vista.txtPrecio.setText(String.valueOf(modelo.getPrecio()));
                vista.txtStock.setText(String.valueOf(modelo.getStock_disponible()));
                
                Modelo.ConsultaProveedor consProv = new Modelo.ConsultaProveedor();
                for(Modelo.Proveedor p : consProv.listaProveedor()){
                    if(p.getId_proveedor() == modelo.getId_proveedor()){
                        vista.cbProveedor.setSelectedItem(p.getNombre_empresa());
                        break;
                    }
                }
            
                Modelo.ConsultaCategoria consCatBuscar = new Modelo.ConsultaCategoria();
                for(Modelo.Categoria cat : consCatBuscar.listarCategoria()){
                    if(cat.getId_categoria() == modelo.getId_categoria()){
                        vista.cbCategoria.setSelectedItem(cat.getNombre());
                        break;
                    }
                }
                    
            } else {
                JOptionPane.showMessageDialog(null, "No existe el registro");
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
            vista.txtNombre.setText(null);
            vista.txtDescripcion.setText(null);
            vista.txtPrecio.setText(null);
            vista.txtStock.setText(null);
        }
    }
