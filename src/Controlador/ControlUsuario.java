package Controlador;

import Modelo.ConsultaUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Modelo.Usuario;
import Vista.frmUsuario;
import java.util.List;


public class ControlUsuario implements ActionListener {
    
    private final Usuario modelo;
    private final ConsultaUsuario consultas;
    private final frmUsuario vista;

    public ControlUsuario(Usuario modelo, ConsultaUsuario consultas, frmUsuario vista) {
        this.modelo = modelo;
        this.consultas = consultas;
        this.vista = vista;
        
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        vista.btnMenu.addActionListener(this);
    }
    
    public void iniciar() {
        vista.setTitle("Mantenimiento de Usuarios");
        vista.setLocationRelativeTo(null);
        //vista.txtId.setVisible(false); // Ocultamos el ID
        llenarTabla();
        
    }
    
    public void llenarTabla(){
        List<Usuario> listaUsu = consultas.listarUsuario();
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tbUsuario.getModel();
        modeloTabla.setRowCount(0);
        Object[] fila = new Object[4];
        
        for(int i = 0; i < listaUsu.size(); i++){
            fila[0] = listaUsu.get(i).getIdUsuario();
            fila[1] = listaUsu.get(i).getUsername();
            fila[2] = listaUsu.get(i).getPassword();
            fila[3] = listaUsu.get(i).getRol();
            modeloTabla.addRow(fila);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // --- GUARDAR ---
        if(e.getSource() == vista.btnGuardar) {
            modelo.setUsername(vista.txtUsername.getText());
            modelo.setPassword(vista.txtPassword.getText());
            modelo.setRol(vista.cbxRol.getSelectedItem().toString());
            
            
            if (consultas.Buscar(modelo)) {
        
        // Si existe, mostramos error y detenemos el proceso
        JOptionPane.showMessageDialog(null, "Error: El Username ya existe.");
        vista.txtUsername.requestFocus();
        
    } else {
        
        // Si NO existe, procedemos a guardar usando el modelo que ya llenamos arriba
        if (consultas.Guardar(modelo)) {
            JOptionPane.showMessageDialog(null, "usuarioguardado exitosamente");
            agregarAlHistorial();
            llenarTabla();
            Limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar el Usuario");
        }
        
    }
}
        
        // --- MODIFICAR ---
        if(e.getSource() == vista.btnEditar) {
            //modelo.setId(Integer.parseInt(vista.txtId.getText())); // ID obligatorio
            modelo.setUsername(vista.txtUsername.getText());
            modelo.setPassword(vista.txtPassword.getText());
            modelo.setRol(vista.cbxRol.getSelectedItem().toString());
           
            
            if(consultas.Editar(modelo)) {
                JOptionPane.showMessageDialog(null, "Usuario modificado exitosamente");
                agregarAlHistorial();
                llenarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar el Usuario");
            }
        }
        
        // --- ELIMINAR ---
        if(e.getSource() == vista.btnEliminar) {
            //modelo.setId(Integer.parseInt(vista.txtId.getText()));
            
            if(consultas.Eliminar(modelo)) {
                JOptionPane.showMessageDialog(null, "Cliente Usuario exitosamente");
                Limpiar();
                llenarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el Usuario");
            }
        }
        
        // --- BUSCAR ---
        if(e.getSource() == vista.btnBuscar) {
            modelo.setUsername(vista.txtUsername.getText()); // Buscamos por Nombre de usuario
            
            if(consultas.Buscar(modelo)) {
               // vista.txtId.setText(String.valueOf(modelo.getId())); 
                vista.txtUsername.setText(modelo.getUsername());
                vista.txtPassword.setText(modelo.getPassword());
                modelo.setRol(vista.cbxRol.getSelectedItem().toString());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el usuario");
                agregarAlHistorial();
                llenarTabla();
                Limpiar();
            }
        }
        
        // --- LIMPIAR ---
        if(e.getSource() == vista.btnLimpiar) {
            Limpiar();
        }// --- BOTÓN REGRESAR AL MENÚ ---
   if(e.getSource() == vista.btnMenu){
            Vista.frmGestorVentas vistaMenu = new Vista.frmGestorVentas();
            Controlador.ControlGestor ctrlg = new Controlador.ControlGestor(vistaMenu);
            vistaMenu.setVisible(true);
            vistaMenu.setLocationRelativeTo(null);
            this.vista.dispose();
            
        }
     
    } 
    public void agregarAlHistorial() {
    DefaultTableModel modeloTabla = (DefaultTableModel) vista.tbUsuario.getModel();
    
    Object[] fila = new Object[5]; 
    // Usamos los datos del modelo Cliente
    fila[0] = modelo.getIdUsuario(); 
    fila[1] = modelo.getUsername();
    fila[2] = modelo.getPassword();
    fila[3] = modelo.getRol();
    
    // Agregamos la fila a la tabla visual
    modeloTabla.addRow(fila);
}
    
    public void Limpiar(){
        vista.txtUsername.setText(null);
        vista.txtPassword.setText(null);
        vista.txtUsername.setText(null);
        
}
}
