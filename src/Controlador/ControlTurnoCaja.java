package Controlador;

import Modelo.TurnoCaja;
import Modelo.ConsultasTurnoCaja;
import Vista.frmTurnoCaja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlTurnoCaja implements ActionListener {

    private final frmTurnoCaja vista;
    private final ConsultasTurnoCaja modelo;
    private final TurnoCaja turno;
    private final DefaultTableModel modeloTabla;

    public ControlTurnoCaja(frmTurnoCaja vista, ConsultasTurnoCaja modelo, TurnoCaja turno) {
        this.vista = vista;
        this.modelo = modelo;
        this.turno = turno;

        this.modeloTabla = (DefaultTableModel) vista.tablaHistorial.getModel();

        this.vista.btnAbrirTurno.addActionListener(this);
        this.vista.btnCerrarTurno.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnMenu.addActionListener(this);

    }
    
    public void iniciar(){
       vista.setTitle("Formulario del turno de caja");
       vista.setResizable(true);
       vista.setLocationRelativeTo(null);
       actualizarTabla();
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // --- ACCIÓN ABRIR TURNO (SOLO PARA REGISTROS EXISTENTES) ---
        if (e.getSource() == vista.btnAbrirTurno) {
            if (vista.txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar o buscar un ID primero.");
                return;
            }

            try {
                turno.setId(Integer.parseInt(vista.txtId.getText()));
                turno.setIdCaja(Integer.parseInt(vista.txtIdCaja.getText()));
                turno.setFechaApertura(vista.txtFechaApertura.getText());
                turno.setMontoInicial(Double.parseDouble(vista.txtMontoInicial.getText()));

                if (modelo.abrirTurno(turno)) {
                    JOptionPane.showMessageDialog(null, "Turno ID " + turno.getId() + " abierto correctamente.");
                    limpiar();
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Error: No se encontró el registro para abrir.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Revise los campos numéricos.");
            }
        }

        // --- ACCIÓN BUSCAR ---
        if (e.getSource() == vista.btnBuscar) {
            if (vista.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un ID para buscar.");
                return;
            }
            turno.setId(Integer.parseInt(vista.txtId.getText()));
            if (modelo.buscar(turno)) {
                vista.txtIdCaja.setText(String.valueOf(turno.getIdCaja()));
                vista.txtFechaApertura.setText(turno.getFechaApertura());
                vista.txtFechaCierre.setText(turno.getFechaCierre());
                vista.txtMontoInicial.setText(String.valueOf(turno.getMontoInicial()));
                vista.txtMontoFinal.setText(String.valueOf(turno.getMontoFinal()));
                vista.txtEstado.setText(turno.getEstado());
            } else {
                JOptionPane.showMessageDialog(null, "Turno no registrado.");
            }
        }

        // --- ACCIÓN CERRAR TURNO ---
        if (e.getSource() == vista.btnCerrarTurno) {
            if (vista.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Busque el turno que desea cerrar.");
                return;
            }

            try {
                turno.setId(Integer.parseInt(vista.txtId.getText()));
                turno.setFechaCierre(vista.txtFechaCierre.getText());
                turno.setMontoFinal(Double.parseDouble(vista.txtMontoFinal.getText()));

                if (modelo.cerrarTurno(turno)) {
                    JOptionPane.showMessageDialog(null, "Turno ID " + turno.getId() + " finalizado.");
                    limpiar();
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al cerrar el registro.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Revise el formato del Monto Final.");
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
        modeloTabla.setRowCount(0);
        List<TurnoCaja> lista = modelo.listarTurnos();
        for (TurnoCaja t : lista) {
            Object[] fila = {
                t.getId(),
                t.getIdCaja(),
                t.getFechaApertura(),
                t.getFechaCierre(),
                t.getMontoInicial(),
                t.getMontoFinal(),
                t.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    }

    public void limpiar() {
        vista.txtId.setText(null);
        vista.txtIdCaja.setText(null);
        vista.txtFechaApertura.setText(null);
        vista.txtFechaCierre.setText(null);
        vista.txtMontoInicial.setText(null);
        vista.txtMontoFinal.setText(null);
        vista.txtEstado.setText(null);
    }
}