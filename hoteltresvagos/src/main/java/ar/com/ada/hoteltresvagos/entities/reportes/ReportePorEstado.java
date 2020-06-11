package ar.com.ada.hoteltresvagos.entities.reportes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReportePorEstado {

    @Id
    @Column(name = "tipo_estado_id")
    private int tipoEstadoId;
    private String descripcion;
    @Column(name = "cantidad_reservas")
    private int cantidadReservas;
    @Column(name = "pagos_reserva")
    private int pagosReserva;
    @Column(name = "total_pagos_recibidos")
    private int totalPagosRecibidos;
    @Column(name = "importe_total")
    private int importeTotal;
    //@Column(name = "pagos_faltantes")
    //private int pagosFaltantes;

    public int getTipoEstadoId() {
        return tipoEstadoId;
    }

    public void setTipoEstadoId(int tipoEstadoId) {
        this.tipoEstadoId = tipoEstadoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidadReservas() {
        return cantidadReservas;
    }

    public void setCantidadReservas(int cantidadReservas) {
        this.cantidadReservas = cantidadReservas;
    }

    public int getPagosReserva() {
        return pagosReserva;
    }

    public void setPagosReserva(int pagosReserva) {
        this.pagosReserva = pagosReserva;
    }

    public int getTotalPagosRecibidos() {
        return totalPagosRecibidos;
    }

    public void setTotalPagosRecibidos(int totalPagosRecibidos) {
        this.totalPagosRecibidos = totalPagosRecibidos;
    }

    public int getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(int importeTotal) {
        this.importeTotal = importeTotal;
    }

    /*
    public int getPagosFaltantes() {
        return pagosFaltantes;
    }

    public void setPagosFaltantes(int pagosFaltantes) {
        this.pagosFaltantes = pagosFaltantes;
    }*/

    
}