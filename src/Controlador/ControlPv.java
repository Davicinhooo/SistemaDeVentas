package Controlador;

import Modelo.ConsultaPedido_Virtual;
import Modelo.Pedido_Virtual;
import Vista.frmPedido_Virtual;
import Vista.frmGestorVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlPv implements ActionListener {

    private final Pedido_Virtual modelo;
    private final ConsultaPedido_Virtual consultas;
    private final frmPedido_Virtual vista;

    public ControlPv(Pedido_Virtual modelo, ConsultaPedido_Virtual consultas, frmPedido_Virtual vista) {
        this.modelo = modelo;
        this.consultas = consultas;
        this.vista = vista;

        // Escuchadores de botones
        this.vista.btnAceptar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Gestión de Pedidos Virtuales");
        vista.setLocationRelativeTo(null);
        vista.setResizable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // --- BOTÓN ACEPTAR (GUARDAR) ---
        if (e.getSource() == vista.btnAceptar) {
            // Asegúrate de tener txtIdVenta en tu formulario
            modelo.setId_venta(Integer.parseInt(vista.txtID_Venta.getText()));
            modelo.setEstado_pedido(vista.cbxEstado.getSelectedItem().toString());

            if (consultas.guardar(modelo)) {
                JOptionPane.showMessageDialog(null, "Pedido guardado con éxito");
                agregarAlHistorial();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el pedido");
            }
        }

        // --- BOTÓN EDITAR ---
        if (e.getSource() == vista.btnEditar) {
            modelo.setId_pedido(Integer.parseInt(vista.txtID_Pedido.getText()));
            modelo.setId_venta(Integer.parseInt(vista.txtID_Venta.getText()));
            modelo.setEstado_pedido(vista.cbxEstado.getSelectedItem().toString());

            if (consultas.editar(modelo)) {
                JOptionPane.showMessageDialog(null, "Pedido modificado correctamente");
                agregarAlHistorial();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }

        // --- BOTÓN BUSCAR ---
        if (e.getSource() == vista.btnBuscar) {
            modelo.setId_pedido(Integer.parseInt(vista.txtID_Pedido.getText()));

            if (consultas.buscar(modelo)) {
                vista.txtID_Venta.setText(String.valueOf(modelo.getId_venta()));
                vista.cbxEstado.setSelectedItem(modelo.getEstado_pedido());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el pedido");
                limpiar();
            }
        }

        // --- BOTÓN ELIMINAR ---
        if (e.getSource() == vista.btnEliminar) {
            modelo.setId_pedido(Integer.parseInt(vista.txtID_Pedido.getText()));

            if (consultas.eliminar(modelo)) {
                JOptionPane.showMessageDialog(null, "Pedido eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar");
            }
        }

        // --- BOTÓN CANCELAR (LIMPIAR) ---
        if (e.getSource() == vista.btnCancelar) {
            limpiar();
        }

        // --- BOTÓN MENÚ ---
        if (e.getSource() == vista.btnMenu) {
            frmGestorVentas vistaMenu = new frmGestorVentas();
            Controlador.ControlGestor ctrlg = new Controlador.ControlGestor(vistaMenu);
            vistaMenu.setVisible(true);
            this.vista.dispose();
        }
    }

    public void limpiar() {
        vista.txtID_Pedido.setText("");
        vista.txtID_Venta.setText("");
        if (vista.cbxEstado.getItemCount() > 0) {
            vista.cbxEstado.setSelectedIndex(0);
        }
    }

    public void agregarAlHistorial() {
        // Verifica que el nombre de la tabla en el diseño sea tbPedidos
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tbPedidos.getModel();
        Object[] fila = new Object[3];
        fila[0] = modelo.getId_pedido();
        fila[1] = modelo.getId_venta();
        fila[2] = modelo.getEstado_pedido();
        modeloTabla.addRow(fila);
    }
}