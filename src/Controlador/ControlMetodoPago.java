package Controlador;

import Modelo.ConsultasMetodoPago;
import Modelo.MetodoPago;
import Vista.frmMetodoPago;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlMetodoPago implements ActionListener {

    private final frmMetodoPago vista;
    private final ConsultasMetodoPago modelo;
    private final MetodoPago objetoMetodo;

    public ControlMetodoPago(frmMetodoPago vista, ConsultasMetodoPago modelo, MetodoPago objetoMetodo) {
        this.vista = vista;
        this.modelo = modelo;
        this.objetoMetodo = objetoMetodo;

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);
        
        actualizarTabla();
    }
    
    public void iniciar(){
       vista.setTitle("Formulario del metodo de pago");
       vista.setResizable(true);
       vista.setLocationRelativeTo(null);
       actualizarTabla();
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // --- GUARDAR ---
        if (e.getSource() == vista.btnGuardar) {
            // Si el campo ID no está vacío, validamos si ya existe para no duplicar
            if (!vista.txtId.getText().trim().isEmpty()) {
                int idTemp = Integer.parseInt(vista.txtId.getText());
                if (modelo.existeRegistro(idTemp)) {
                    JOptionPane.showMessageDialog(null, "El ID " + idTemp + " ya existe.");
                    return;
                }
            }

            objetoMetodo.setTipoPago(vista.cbxTipoPago.getSelectedItem().toString());
            objetoMetodo.setDescripcion(vista.txtDescripcion.getText());

            if (modelo.registrar(objetoMetodo)) {
                JOptionPane.showMessageDialog(null, "Método de Pago Guardado Exitosamente");
                limpiar();
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el registro");
            }
        }

        // --- BUSCAR ---
        if (e.getSource() == vista.btnBuscar) {
            if (vista.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un ID para buscar");
            } else {
                objetoMetodo.setId(Integer.parseInt(vista.txtId.getText()));
                if (modelo.buscar(objetoMetodo)) {
                    vista.cbxTipoPago.setSelectedItem(objetoMetodo.getTipoPago());
                    vista.txtDescripcion.setText(objetoMetodo.getDescripcion());
                } else {
                    JOptionPane.showMessageDialog(null, "Registro no encontrado");
                }
            }
        }

        // --- EDITAR ---
        if (e.getSource() == vista.btnEditar) {
            if (vista.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Primero busque el registro que desea editar");
                return;
            }
            objetoMetodo.setId(Integer.parseInt(vista.txtId.getText()));
            objetoMetodo.setTipoPago(vista.cbxTipoPago.getSelectedItem().toString());
            objetoMetodo.setDescripcion(vista.txtDescripcion.getText());

            if (modelo.modificar(objetoMetodo)) {
                JOptionPane.showMessageDialog(null, "Registro Actualizado");
                limpiar();
                actualizarTabla();
            }
        }

      // --- ELIMINAR ---
if (e.getSource() == vista.btnEliminar) {
    if (vista.txtId.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Ingrese un ID para eliminar");
        return;
    }

    // Se agrega el parámetro YES_NO_OPTION para cambiar los botones
    int r = JOptionPane.showConfirmDialog(
            null, 
            "¿Está seguro de eliminar este método de pago?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION, // Esto activa los botones Sí/No
            JOptionPane.QUESTION_MESSAGE
    );

    if (r == JOptionPane.YES_OPTION) { // YES_OPTION es el botón "SÍ"
        objetoMetodo.setId(Integer.parseInt(vista.txtId.getText()));
        if (modelo.eliminar(objetoMetodo)) {
            JOptionPane.showMessageDialog(null, "Eliminado");
            limpiar();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se puede eliminar: El registro está en uso en otra tabla.");
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

    public void actualizarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tablaMetodos.getModel();
        modeloTabla.setRowCount(0);
        List<MetodoPago> lista = modelo.listarMetodos();
        for (MetodoPago m : lista) {
            Object[] fila = { m.getId(), m.getTipoPago(), m.getDescripcion() };
            modeloTabla.addRow(fila);
        }
    }

    public void limpiar() {
        vista.txtId.setText(null);
        vista.cbxTipoPago.setSelectedIndex(0);
        vista.txtDescripcion.setText(null);
    }
}