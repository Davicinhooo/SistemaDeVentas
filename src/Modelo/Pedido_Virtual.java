package Modelo;

public class Pedido_Virtual {
    private int id_pedido,id_venta;
   
    private String Estado_pedido;

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public String getEstado_pedido() {
        return Estado_pedido;
    }

    public void setEstado_pedido(String Estado_pedido) {
        this.Estado_pedido = Estado_pedido;
    }



}