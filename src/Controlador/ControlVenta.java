package Controlador;

import Modelo.*;
import Vista.frmVenta;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlVenta implements ActionListener {
    private Venta modelo;
    private ConsultaVenta consultas;
    private frmVenta vista;
    private DefaultTableModel modeloTabla;

    public ControlVenta(Venta modelo, ConsultaVenta consultas, frmVenta vista) {
        this.modelo = modelo;
        this.consultas = consultas;
        this.vista = vista;
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnCobrar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);
        this.vista.btnQuitar.addActionListener(this);
        // Inicializamos el modelo de la tabla
        this.modeloTabla = (DefaultTableModel) vista.tbVenta.getModel();
    }

    public void iniciar() {
        vista.setTitle("Caja EcoMarket - Jean");
        vista.setLocationRelativeTo(null);
        vista.txtTotal.setText("0.00");
        vista.txtTotal.setEditable(false); // No queremos que el cajero escriba aquí
        llenarTabla();
        
        consultas.cargarClientes(vista.cbxCliente);
        consultas.cargarCajeros(vista.cbxUsuario);
        consultas.cargarTurnosAbiertos(vista.cbxTurno);
        consultas.cargarMetodoPago(vista.cbxMetodoPago);
        consultas.cargarProductos(vista.cbxProducto);
    }
    
    public void llenarTabla(){
        List<Venta> listaVen = consultas.listarVenta();
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tbVenta.getModel();
        modeloTabla.setRowCount(0);
        Object[] fila = new Object[8];
        
        for(int i = 0; i < listaVen.size(); i++){
            fila[0] = listaVen.get(i).getId_venta();
            fila[1] = listaVen.get(i).getId_cliente();
            fila[2] = listaVen.get(i).getId_usuario();
            fila[3] = listaVen.get(i).getId_turno_caja();
            fila[4] = listaVen.get(i).getId_metodo_pago();
            fila[5] = listaVen.get(i).getFecha_hora();
            fila[6] = listaVen.get(i).getTipo_venta();
            fila[7] = listaVen.get(i).getTotal();
            modeloTabla.addRow(fila);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // --- BOTÓN AGREGAR ---
        if (e.getSource() == vista.btnAgregar) {
            try {
                String seleccionado = vista.cbxProducto.getSelectedItem().toString();
                if (seleccionado.equals("Seleccione Producto...")) return;

                int id = Integer.parseInt(seleccionado.split(" - ")[0]);
                String nom = seleccionado.split(" - ")[1];
                int cant = Integer.parseInt(vista.txtCantidad.getText());
                double pre = consultas.obtenerPrecio(id);
                double subtotal = pre * cant;
                
                // Agregamos la fila
                modeloTabla.addRow(new Object[]{id, nom, pre, cant, subtotal});
                
                // LLAMAMOS A LA SUMA
                sumarTotal();
                
                vista.txtCantidad.setText("");
                vista.txtCantidad.requestFocus();
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(null, "Error: Verifique cantidad y producto."); 
            }
        }
        
        if (e.getSource() == vista.btnQuitar) {
    // 1. Verificar si el usuario seleccionó una fila
    int filaSeleccionada = vista.tbVenta.getSelectedRow();

    if (filaSeleccionada >= 0) {
        // 2. Preguntar para confirmar (Opcional, pero da seguridad)
        int confirmacion = JOptionPane.showConfirmDialog(null, 
                "¿Está seguro de quitar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // 3. Remover la fila del modelo
            modeloTabla.removeRow(filaSeleccionada);

            // 4. Recalcular el total automáticamente
            sumarTotal();
            
            JOptionPane.showMessageDialog(null, "Producto eliminado de la lista.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila de la tabla para quitar.");
    }
}
        
        

        // --- BOTÓN COBRAR ---
      if (e.getSource() == vista.btnCobrar) {
    if (modeloTabla.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "No hay productos para cobrar.");
        return;
    }

    try {
        // 1. Extraer IDs de los Combos
        int idCli = Integer.parseInt(vista.cbxCliente.getSelectedItem().toString().split("-")[0].trim());
        int idUsu = Integer.parseInt(vista.cbxUsuario.getSelectedItem().toString().split("-")[0].trim());
        int idTur = Integer.parseInt(vista.cbxTurno.getSelectedItem().toString().split("-")[0].trim());
        int idPag = Integer.parseInt(vista.cbxMetodoPago.getSelectedItem().toString().split("-")[0].trim());
        
        double totalVenta = Double.parseDouble(vista.txtTotal.getText().replace(",", "."));

        // 2. Llenar el modelo Venta
        modelo.setId_cliente(idCli);
        modelo.setId_usuario(idUsu);
        modelo.setId_turno_caja(idTur);
        modelo.setId_metodo_pago(idPag);
        modelo.setTipo_venta("Presencial");
        modelo.setTotal(totalVenta);

        // 3. Guardar en la tabla VENTA
        int idVentaGenerado = consultas.guardarVenta(modelo);
        
        if (idVentaGenerado > 0) {
            // 4. GUARDAR DETALLES (Aquí estaba el error, vamos a protegerlo)
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                
                // VALIDACIÓN ANTIFALLOS: Solo procesamos si la celda no es nula
                if (modeloTabla.getValueAt(i, 0) != null) {
                    int idP = Integer.parseInt(modeloTabla.getValueAt(i, 0).toString());
                    double pre = Double.parseDouble(modeloTabla.getValueAt(i, 2).toString());
                    int cant = Integer.parseInt(modeloTabla.getValueAt(i, 3).toString());
                    
                    consultas.guardarDetalle(idVentaGenerado, idP, cant, pre);
                }
            }
            
            JOptionPane.showMessageDialog(null, "¡Venta Realizada con Éxito! Ticket N°: " + idVentaGenerado);
            
            // 5. Limpiar todo
            modeloTabla.setRowCount(0);
            vista.txtTotal.setText("0.00");
            vista.txtCantidad.setText("");
            
        } else {
            JOptionPane.showMessageDialog(null, "Error: No se pudo registrar la venta en la base de datos.");
        }
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error técnico: " + ex.getMessage());
        ex.printStackTrace(); 
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

    // MÉTODO DE SUMA MEJORADO
    private void sumarTotal() {
        double t = 0;
        try {
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                // Obtenemos el valor de la columna 4 (Subtotal)
                Object valorFila = modeloTabla.getValueAt(i, 4);
                if (valorFila != null) {
                    t += Double.parseDouble(valorFila.toString());
                }
            }
            // Formateamos a 2 decimales y usamos punto como separador
            vista.txtTotal.setText(String.format("%.2f", t).replace(",", "."));
        } catch (Exception e) {
            System.err.println("Error sumando: " + e.getMessage());
        }
    }
}