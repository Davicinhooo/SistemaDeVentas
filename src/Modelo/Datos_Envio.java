
package Modelo;
      
public class Datos_Envio {
    private int ID_Envio, ID_Pedido;
    private String Direccion, Referencia, Contacto;

    public int getID_Envio() {
        return ID_Envio;
    }

    public void setID_Envio(int ID_Envio) {
        this.ID_Envio = ID_Envio;
    }

    public int getID_Pedido() {
        return ID_Pedido;
    }

    public void setID_Pedido(int ID_Pedido) {
        this.ID_Pedido = ID_Pedido;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String Referencia) {
        this.Referencia = Referencia;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String Contacto) {
        this.Contacto = Contacto;
    }    
}
