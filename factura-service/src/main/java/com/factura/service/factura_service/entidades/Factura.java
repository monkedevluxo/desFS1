package com.factura.service.factura_service.entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boletaId;

    private String rutCliente;

    private LocalDateTime fecha;

    private BigDecimal totalNeto;

    private BigDecimal iva;

    private BigDecimal totalFinal;

    public Factura() {
    }

    public Factura(Long boletaId, String rutCliente, LocalDateTime fecha, BigDecimal totalNeto, BigDecimal iva, BigDecimal totalFinal) {
        this.boletaId = boletaId;
        this.rutCliente = rutCliente;
        this.fecha = fecha;
        this.totalNeto = totalNeto;
        this.iva = iva;
        this.totalFinal = totalFinal;
    }

    public Long getId() {
        return id;
    }

    public Long getBoletaId() {
        return boletaId;
    }

    public void setBoletaId(Long boletaId) {
        this.boletaId = boletaId;
    }

    public String getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(BigDecimal totalNeto) {
        this.totalNeto = totalNeto;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(BigDecimal totalFinal) {
        this.totalFinal = totalFinal;
    }
}
