package inventario.service.inventario_service.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private long productoId;
    private long sucursalId;
    private int cantidad;

    public Inventario() {
    }

    public Inventario(long id, long productoId, long sucursalId, int cantidad) {
        this.id = id;
        this.productoId = productoId;
        this.sucursalId = sucursalId;
        this.cantidad = cantidad;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getProductoId() {
        return productoId;
    }
    public void setProductoId(long productoId) {
        this.productoId = productoId;
    }
    public long getSucursalId() {
        return sucursalId;
    }
    public void setSucursalId(long sucursalId) {
        this.sucursalId = sucursalId;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
