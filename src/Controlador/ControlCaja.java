package Controlador;

import Modelo.Caja;
import Modelo.ConsultasCaja;
import Vista.frmCaja;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlCaja implements ActionListener {
    private final frmCaja vista;
    private final ConsultasCaja modelo;
    private final Caja caja;

    public ControlCaja(frmCaja vista, ConsultasCaja modelo, Caja caja) {
        this.vista = vista;
        this.modelo = modelo;
        this.caja = caja;
        // Escuchar botones según tu diseño
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);
        actualizarTabla();
    }
    
    public void iniciar(){
       vista.setTitle("Formulario del turno de caja");
       vista.setResizable(true);
       vista.setLocationRelativeTo(null);
       actualizarTabla();
    
    }

    @Override
public void actionPerformed(ActionEvent e) {
    // --- BOTÓN GUARDAR ---
    if (e.getSource() == vista.btnGuardar) {
        // Validamos si el usuario escribió un ID manualmente
        if (!vista.txtId.getText().isEmpty()) {
            try {
                int idIngresado = Integer.parseInt(vista.txtId.getText());
                // Verificamos si ya existe en la BD
                if (modelo.existeCajaPorId(idIngresado)) {
                    JOptionPane.showMessageDialog(null, "Error: El ID " + idIngresado + " ya existe.");
                    return; // Detiene el proceso para que no registre
                }
                caja.setId(idIngresado);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "El ID debe ser un número.");
                return;
            }
        }

        // Si el ID es válido o está vacío (Auto-increment), procedemos
        caja.setNombre(vista.txtNombre.getText());
        caja.setEstado(vista.txtEstado.getText());
        caja.setSucursal(vista.txtSucursal.getText());

        if (modelo.guardar(caja)) {
            JOptionPane.showMessageDialog(null, "Caja Registrada");
            limpiar();
            actualizarTabla();
        }
    }

    // --- BOTÓN BUSCAR ---
    if (e.getSource() == vista.btnBuscar) {
        if (vista.txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID para buscar");
        } else {
            caja.setId(Integer.parseInt(vista.txtId.getText()));
            if (modelo.buscar(caja)) {
                vista.txtNombre.setText(caja.getNombre());
                vista.txtEstado.setText(caja.getEstado());
                vista.txtSucursal.setText(caja.getSucursal());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el ID");
            }
        }
    }

    // --- BOTÓN ELIMINAR ---
    if (e.getSource() == vista.btnEliminar) {
        if (vista.txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un ID para eliminar");
        } else {
            caja.setId(Integer.parseInt(vista.txtId.getText()));
            if (modelo.eliminar(caja)) {
                JOptionPane.showMessageDialog(null, "Caja Eliminada");
                limpiar();
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "No se puede eliminar: Caja vinculada a otros datos.");
            }
        }
    }

    // --- BOTÓN EDITAR ---
    if (e.getSource() == vista.btnEditar) {
        if (vista.txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Busque un registro para editar");
        } else {
            caja.setId(Integer.parseInt(vista.txtId.getText()));
            caja.setNombre(vista.txtNombre.getText());
            caja.setEstado(vista.txtEstado.getText());
            caja.setSucursal(vista.txtSucursal.getText());
            if (modelo.modificar(caja)) {
                JOptionPane.showMessageDialog(null, "Caja Actualizada");
                actualizarTabla();
                limpiar();
            }
        }
    }
    
    if(e.getSource() == vista.btnMenu){
            Vista.frmGestorVentas vistaMenu = new Vista.frmGestorVentas();
            Controlador.ControlGestor ctrlg = new Controlador.ControlGestor(vistaMenu);
            vistaMenu.setVisible(true);
            vistaMenu.setLocationRelativeTo(null);
            this.vista.dispose();
            
        }
}

public void limpiar() {
        vista.txtId.setText("");
        vista.txtNombre.setText("");
        vista.txtEstado.setText("");
        vista.txtSucursal.setText("");
    }
    public void actualizarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.tablaCaja.getModel();
        model.setRowCount(0);
        List<Caja> lista = modelo.listar();
        for (Caja c : lista) {
            model.addRow(new Object[]{c.getId(), c.getNombre(), c.getEstado(), c.getSucursal()});
        }
    }
}