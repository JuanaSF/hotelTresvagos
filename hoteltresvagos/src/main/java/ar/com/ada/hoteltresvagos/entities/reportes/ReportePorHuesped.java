package ar.com.ada.hoteltresvagos.entities.reportes;

import javax.persistence.*;
@Entity
public class ReportePorHuesped extends Reporte {

    @Id
    @Column(name = "huesped_id")
    private int huespedId;
    private String nombre;
    @Column(name = "cantidad_reservas")
    private int cantidadReservas;
    @Column(name = "pagos_reserva")
    private int pagosReserva;
    @Column(name = "total_pagos_recibidos")
    private int totalPagosRecibidos;
    @Column(name = "importe_total")
    private int importeTotal;

    public int getHuespedId() {
        return huespedId;
    }

    public void setHuespedId(int huespedId) {
        this.huespedId = huespedId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

}