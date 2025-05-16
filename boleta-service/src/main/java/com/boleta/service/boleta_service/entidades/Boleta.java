package com.boleta.service.boleta_service.entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private LocalDateTime fechaEmision;

    private BigDecimal total;

    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL)
    private List<ItemBoleta> items;

    public Boleta() {
    }

    public Boleta(Long usuarioId, LocalDateTime fechaEmision, BigDecimal total, List<ItemBoleta> items) {
        this.usuarioId = usuarioId;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<ItemBoleta> getItems() {
        return items;
    }

    public void setItems(List<ItemBoleta> items) {
        this.items = items;
        if (items != null) {
            for (ItemBoleta item : items) {
                item.setBoleta(this); 
            }
        }
    }
}
