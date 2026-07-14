package Controlador;

import Modelo.ConsultaCliente;
import Modelo.Cliente;
import Vista.frmCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlCliente implements ActionListener {
    
    private final Cliente modelo;
    private final ConsultaCliente consultas;
    private final frmCliente vista;

    public ControlCliente(Cliente modelo, ConsultaCliente consultas, frmCliente vista) {
        this.modelo = modelo;
        this.consultas = consultas;
        this.vista = vista;
        
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);
    }
    
    public void iniciar() {
        vista.setTitle("Mantenimiento de Clientes");
        vista.setLocationRelativeTo(null);
        consultas.cargarUsuarios(vista.cbxUsuario); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // ---------------- BOTÓN GUARDAR ----------------
        if (e.getSource() == vista.btnGuardar) {
            modelo.setDni(vista.txtDni.getText());
            modelo.setNombre(vista.txtNombre.getText());
            modelo.setTelefono(vista.txtTelefono.getText());
            modelo.setCorreo(vista.txtCorreo.getText());
            
            // Lógica del Usuario
            String seleccionUsuario = vista.cbxUsuario.getSelectedItem().toString();
            if (seleccionUsuario.equals("Sin Usuario / Seleccione...")) {
                modelo.setIdUsuario(0); 
            } else {
                String[] partes = seleccionUsuario.split(" - ");
                modelo.setIdUsuario(Integer.parseInt(partes[0])); 
            }
            
            if (consultas.guardar(modelo)) {
                JOptionPane.showMessageDialog(null, "Cliente guardado con éxito");
                agregarAlHistorial();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        }
        
        // ---------------- BOTÓN EDITAR ----------------
        if(e.getSource() == vista.btnEditar) {
            modelo.setDni(vista.txtDni.getText()); 
            modelo.setNombre(vista.txtNombre.getText());
            modelo.setTelefono(vista.txtTelefono.getText());
            modelo.setCorreo(vista.txtCorreo.getText());
            
            String seleccionUsuario = vista.cbxUsuario.getSelectedItem().toString();
            if (seleccionUsuario.equals("Sin Usuario / Seleccione...")) {
                modelo.setIdUsuario(0); 
            } else {
                String[] partes = seleccionUsuario.split(" - ");
                modelo.setIdUsuario(Integer.parseInt(partes[0])); 
            }
            
            if(consultas.editar(modelo)) { 
                JOptionPane.showMessageDialog(null, "Cliente modificado exitosamente");
                agregarAlHistorial();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }
        
        // ---------------- BOTÓN ELIMINAR ----------------
        if(e.getSource() == vista.btnEliminar) {
            modelo.setDni(vista.txtDni.getText()); 
            
            if(consultas.eliminar(modelo)) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente");
                agregarAlHistorial();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar");
            }
        }
        
        // ---------------- BOTÓN BUSCAR ----------------
        if(e.getSource() == vista.btnBuscar) {
            modelo.setDni(vista.txtDni.getText()); 
            
            if(consultas.buscar(modelo)) {
                vista.txtNombre.setText(modelo.getNombre());
                vista.txtTelefono.setText(modelo.getTelefono());
                vista.txtCorreo.setText(modelo.getCorreo()); 
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún Cliente con ese DNI");
               agregarAlHistorial();
                limpiar();
            }
        }
        
        // ---------------- BOTÓN LIMPIAR ----------------
        if(e.getSource() == vista.btnLimpiar) {
            limpiar();
        }
        
        // ---------------- BOTÓN MENÚ ----------------
        if(e.getSource() == vista.btnMenu){
            Vista.frmGestorVentas vistaMenu = new Vista.frmGestorVentas();
            Controlador.ControlGestor ctrlg = new Controlador.ControlGestor(vistaMenu);
            vistaMenu.setVisible(true);
            vistaMenu.setLocationRelativeTo(null);
            this.vista.dispose();
            
        }
    } 
    
    // =======================================================
    public void limpiar() {
        vista.txtDni.setText("");
        vista.txtNombre.setText("");
        vista.txtTelefono.setText("");
        vista.txtCorreo.setText("");
        
        if(vista.cbxUsuario.getItemCount() > 0) {
            vista.cbxUsuario.setSelectedIndex(0);
        }
    }
    public void agregarAlHistorial() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tbCliente.getModel();
        
        // Creamos un arreglo de 6 espacios porque tu tabla Cliente tiene 6 columnas
        Object[] fila = new Object[6]; 
        
        // Usamos los datos exactos del modelo Cliente
        fila[0] = modelo.getIdCliente(); 
        fila[1] = modelo.getIdUsuario(); // Puede ser 0 si no tiene cuenta web
        fila[2] = modelo.getDni();
        fila[3] = modelo.getNombre();
        fila[4] = modelo.getTelefono();
        fila[5] = modelo.getCorreo();
        
        // Agregamos la fila a la tabla visual
        modeloTabla.addRow(fila);
    }
}