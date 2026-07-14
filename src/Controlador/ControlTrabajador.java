package Controlador;

import Modelo.ConsultaTrabajador;
import Modelo.Trabajador; 
import Vista.frmTrabajador;
import Vista.frmGestorVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlTrabajador implements ActionListener {
    
    private final Trabajador Modelo;
    private final ConsultaTrabajador consultas;
    private final frmTrabajador vista;

    public ControlTrabajador(Trabajador modelo, ConsultaTrabajador consultas, frmTrabajador vista) {
        this.Modelo = modelo;
        this.consultas = consultas;
        this.vista = vista;
        
        // Despertamos TODOS los botones de tu formulario
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);
    }
    
    public void iniciar() {
        vista.setTitle("Mantenimiento de Trabajadores");
        vista.setLocationRelativeTo(null);
        // Llenamos el ComboBox de usuarios al abrir la ventana
        consultas.cargarUsuarios(vista.cbxUsuario); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // ---------------- BOTÓN GUARDAR ----------------
        if (e.getSource() == vista.btnGuardar) {
            Modelo.setDni(vista.txtDni.getText());
            Modelo.setNombre(vista.txtNombre.getText());
            Modelo.setTelefono(vista.txtTelefono.getText());
            Modelo.setCargo(vista.txtCargo.getText()); // Como caja de texto según tu foto
            Modelo.setSueldo(Double.parseDouble(vista.txtSueldo.getText()));
            
            // Lógica del Usuario (Llave Foránea)
            String seleccionUsuario = vista.cbxUsuario.getSelectedItem().toString();
            if (seleccionUsuario.equals("Sin Usuario / Seleccione...")) {
                Modelo.setIdUsuario(0); 
            } else {
                String[] partes = seleccionUsuario.split(" - ");
                Modelo.setIdUsuario(Integer.parseInt(partes[0])); 
            }
            
            if (consultas.guardar(Modelo)) {
                JOptionPane.showMessageDialog(null, "Trabajador guardado con éxito");
                agregarAlHistorial();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el Trabajador");
            }
        }
        
        // ---------------- BOTÓN EDITAR ----------------
        if(e.getSource() == vista.btnEditar) {
            Modelo.setDni(vista.txtDni.getText()); // Usamos el DNI para saber a quién editar
            Modelo.setNombre(vista.txtNombre.getText());
            Modelo.setTelefono(vista.txtTelefono.getText());
            Modelo.setCargo(vista.txtCargo.getText());
            Modelo.setSueldo(Double.parseDouble(vista.txtSueldo.getText()));
            
            // Lógica del Usuario (Llave Foránea)
            String seleccionUsuario = vista.cbxUsuario.getSelectedItem().toString();
            if (seleccionUsuario.equals("Sin Usuario / Seleccione...")) {
                Modelo.setIdUsuario(0); 
            } else {
                String[] partes = seleccionUsuario.split(" - ");
                Modelo.setIdUsuario(Integer.parseInt(partes[0])); 
            }
            
            if(consultas.editar(Modelo)) { 
                JOptionPane.showMessageDialog(null, "Trabajador modificado exitosamente");
                agregarAlHistorial();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar el Trabajador");
            }
        }
        
        // ---------------- BOTÓN ELIMINAR ----------------
        if(e.getSource() == vista.btnEliminar) {
            // Solo necesitamos el DNI para eliminar
            Modelo.setDni(vista.txtDni.getText()); 
            
            if(consultas.Eliminar(Modelo)) {
                JOptionPane.showMessageDialog(null, "Trabajador eliminado exitosamente");
                agregarAlHistorial();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el Trabajador");
            }
        }
        
        // ---------------- BOTÓN BUSCAR ----------------
        if(e.getSource() == vista.btnBuscar) {
            // El administrador escribe el DNI y le da a buscar
            Modelo.setDni(vista.txtDni.getText()); 
            
            if(consultas.buscar(Modelo)) {
                // Llenamos las cajas con los datos encontrados
                vista.txtNombre.setText(Modelo.getNombre());
                vista.txtTelefono.setText(Modelo.getTelefono());
                vista.txtCargo.setText(Modelo.getCargo()); 
                vista.txtSueldo.setText(String.valueOf(Modelo.getSueldo()));
                // Nota: El combo de usuario se queda en la selección actual para simplificar
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún Trabajador con ese DNI");
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
    // MÉTODO EXTRA: Para limpiar las cajas después de una acción
    // =======================================================
    public void limpiar() {
        vista.txtDni.setText("");
        vista.txtNombre.setText("");
        vista.txtTelefono.setText("");
        vista.txtCargo.setText("");
        vista.txtSueldo.setText("");
        // Reiniciamos el ComboBox a la primera opción ("Sin Usuario...")
        if(vista.cbxUsuario.getItemCount() > 0) {
            vista.cbxUsuario.setSelectedIndex(0);
        }
    }
    public void agregarAlHistorial() {
    DefaultTableModel modeloTabla = (DefaultTableModel) vista.tbTrabajador.getModel();
    
    Object[] fila = new Object[6]; 
    // Usamos los datos del modelo Cliente
    fila[0] = Modelo.getIdTrabajador(); 
    fila[1] = Modelo.getDni();
    fila[2] = Modelo.getNombre();
    fila[3] = Modelo.getTelefono();
    fila[4] = Modelo.getCargo();
    fila[5] = Modelo.getIdUsuario();
    
    // Agregamos la fila a la tabla visual
    modeloTabla.addRow(fila);
}
}
