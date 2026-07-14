package Controlador;

//CATEGORIA
import Vista.frmCateg;
import Modelo.ConsultaCategoria;
import Modelo.Categoria;

//PRODUCTO
import Vista.frmProdu;
import Modelo.ConsultaProducto;
import Modelo.Producto;

//PROVEEDOR
import Vista.frmProve;
import Modelo.ConsultaProveedor;
import Modelo.Proveedor;

//CAJA
import Vista.frmCaja;
import Modelo.ConsultasCaja;
import Modelo.Caja;

//TURNO CAJA
import Vista.frmTurnoCaja;
import Modelo.ConsultasTurnoCaja;
import Modelo.TurnoCaja;

//METODO DE PAGO
import Vista.frmMetodoPago;
import Modelo.ConsultasMetodoPago;
import Modelo.MetodoPago;
        
//VENTA
import Vista.frmVenta;
import Modelo.ConsultaVenta;
import Modelo.Venta;

//DATOS DE ENVIO
import Vista.frmDatos_Envios;
import Modelo.Datos_Envio;
import Modelo.ConsultaDatos_Envio;

//PEDIDO VIRTUAL
import Vista.frmPedido_Virtual;
import Modelo.ConsultaPedido_Virtual;
import Modelo.Pedido_Virtual;

//CLIENTE
import Vista.frmCliente;
import Modelo.ConsultaCliente;
import Modelo.Cliente;

//TRABAJADOR
import Vista.frmTrabajador;
import Modelo.ConsultaTrabajador;
import Modelo.Trabajador;

//USUARIO
import Vista.frmUsuario;
import Modelo.ConsultaUsuario;
import Modelo.Usuario;

//OTROS
import Vista.frmGestorVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControlGestor implements ActionListener {

    private final frmGestorVentas vistaMenu;

    public ControlGestor(frmGestorVentas vistaMenu) {
        this.vistaMenu = vistaMenu;

        this.vistaMenu.btnCaja.addActionListener(this);
        this.vistaMenu.btnCategoria.addActionListener(this);
        this.vistaMenu.btnCliente.addActionListener(this);
        this.vistaMenu.btndatosEnvio.addActionListener(this);
        this.vistaMenu.btnmetodoPago.addActionListener(this);
        this.vistaMenu.btnpedidoVirtual.addActionListener(this);
        this.vistaMenu.btnProducto.addActionListener(this);
        this.vistaMenu.btnProveedor.addActionListener(this);
        this.vistaMenu.btnTrabajador.addActionListener(this);
        this.vistaMenu.btnturnoCaja.addActionListener(this);
        this.vistaMenu.btnUsuario.addActionListener(this);
        this.vistaMenu.btnVenta.addActionListener(this);
        this.vistaMenu.btnSalir.addActionListener(this);
    }
    
    public void iniciar(){
        vistaMenu.setTitle("Gestor MiniMarket");
        vistaMenu.setLocationRelativeTo(vistaMenu);
        vistaMenu.setVisible(true);
        vistaMenu.setSize(855, 457);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaMenu.btnCategoria){
            Categoria cat = new Categoria();
            ConsultaCategoria cc = new ConsultaCategoria();
            frmCateg vcat = new frmCateg();
            
            ControlCategoria ctrlCat = new ControlCategoria(cat, cc, vcat);
            ctrlCat.iniciar();
            
            vcat.setVisible(true);
            this.vistaMenu.dispose();
        }
        
        if(e.getSource() == vistaMenu.btnProducto){
            Producto pro = new Producto();
            ConsultaProducto cp = new ConsultaProducto();
            frmProdu vistap = new frmProdu();
            
            ControlProducto ctrlPro = new ControlProducto(pro, cp, vistap);
            ctrlPro.iniciar();
            
            vistap.setVisible(true);
            this.vistaMenu.dispose();
        }
        
        if(e.getSource() == vistaMenu.btnProveedor){
            Proveedor prov = new Proveedor();
            ConsultaProveedor cprov = new ConsultaProveedor();
            frmProve vprov = new frmProve();
            
            ControlProveedor ctrlProv = new ControlProveedor(prov, cprov, vprov);
            ctrlProv.iniciar();
            
            vprov.setVisible(true);
            this.vistaMenu.dispose();
        }
        
        if(e.getSource() == vistaMenu.btnCaja){
            Caja objetoCaja = new Caja();
            ConsultasCaja consultaCaj = new ConsultasCaja();
            frmCaja visCaja = new frmCaja();
            
            ControlCaja ctrlCaja = new ControlCaja(visCaja, consultaCaj, objetoCaja);
            ctrlCaja.iniciar();
            
            visCaja.setVisible(true);
            this.vistaMenu.dispose();
        }
        
        if(e.getSource() == vistaMenu.btnturnoCaja){
            TurnoCaja tcaja = new TurnoCaja();
            ConsultasTurnoCaja ctucaja = new ConsultasTurnoCaja();
            frmTurnoCaja vtcaja = new frmTurnoCaja();
            
            ControlTurnoCaja ctrlturnoCaja = new ControlTurnoCaja(vtcaja, ctucaja, tcaja);
            ctrlturnoCaja.iniciar();
            
            vtcaja.setVisible(true);
            this.vistaMenu.dispose();
        }
        
        if(e.getSource() == vistaMenu.btnmetodoPago){
            MetodoPago mp = new MetodoPago();
            ConsultasMetodoPago cmp = new ConsultasMetodoPago();
            frmMetodoPago vmp = new frmMetodoPago();
            
            ControlMetodoPago ctrlmp = new ControlMetodoPago(vmp, cmp, mp);
            ctrlmp.iniciar();
            
            vmp.setVisible(true);
            this.vistaMenu.dispose();
        }
        
        if(e.getSource() == vistaMenu.btnVenta){
            Venta v = new Venta();
            ConsultaVenta cv = new ConsultaVenta();
            frmVenta vv = new frmVenta();
            
            ControlVenta ctrlv = new ControlVenta(v, cv, vv);
            ctrlv.iniciar();
            
            vv.setVisible(true);
            this.vistaMenu.dispose();
        }
        
        if(e.getSource() == vistaMenu.btndatosEnvio){
            Datos_Envio de = new Datos_Envio();
            ConsultaDatos_Envio cde = new ConsultaDatos_Envio();
            frmDatos_Envios vde = new frmDatos_Envios();
            
            Control_Datos_Envios ctrlde = new Control_Datos_Envios(de, cde, vde);
            ctrlde.iniciar();
            
            vde.setVisible(true);
            this.vistaMenu.dispose();
            
        }
        
        if(e.getSource() == vistaMenu.btnpedidoVirtual){
            Pedido_Virtual pv = new Pedido_Virtual();
            ConsultaPedido_Virtual cpv = new ConsultaPedido_Virtual();
            frmPedido_Virtual vpv = new frmPedido_Virtual();
            
            ControlPv ctrlpv = new ControlPv(pv, cpv, vpv);
            ctrlpv.iniciar();
            
            vpv.setVisible(true);
            this.vistaMenu.dispose();
        }
        
        if(e.getSource() == vistaMenu.btnCliente){
            Cliente cli = new Cliente();
            ConsultaCliente cc = new ConsultaCliente();
            frmCliente vc = new frmCliente();
            
            ControlCliente ctrlc = new ControlCliente(cli, cc, vc);
            ctrlc.iniciar();
            
            vc.setVisible(true);
            this.vistaMenu.dispose();
        }
        
        if(e.getSource() == vistaMenu.btnTrabajador){
            Trabajador tra = new Trabajador();
            ConsultaTrabajador ct = new ConsultaTrabajador();
            frmTrabajador vt = new frmTrabajador();
            
            ControlTrabajador ctrlt = new ControlTrabajador(tra, ct, vt);
            ctrlt.iniciar();
            
            vt.setVisible(true);
            this.vistaMenu.dispose();
        }
        
        if(e.getSource() == vistaMenu.btnUsuario){
            Usuario user = new Usuario();
            ConsultaUsuario cuser = new ConsultaUsuario();
            frmUsuario vu = new frmUsuario();
            
            ControlUsuario ctrlu = new ControlUsuario(user, cuser, vu);
            ctrlu.iniciar();
            
            vu.setVisible(true);
            this.vistaMenu.dispose();
            
        }
        
        if(e.getSource() == vistaMenu.btnSalir){
            System.exit(0);
            
        }
    }    
}
