package ar.com.ada.hoteltresvagos.services;

import java.util.List;
import java.util.Scanner;

import ar.com.ada.hoteltresvagos.entities.reportes.*;
import ar.com.ada.hoteltresvagos.managers.ReporteManager;

public class ReporteService {

    public static Scanner Teclado = new Scanner(System.in);

    protected ReporteManager ABMReporte;
    public ReporteService(ReporteManager ABMReporte){
        this.ABMReporte = ABMReporte;
    }

               
    public void listarPorEstados() {

        List<ReportePorEstado> todos = ABMReporte.reporteEstados();
        for (ReportePorEstado r : todos) {
            mostrarReporte(r);
        }
    }

    public void listarPorEstadoId() {

        int estadoId;
        System.out.println("Ingrese Id del estado");
        estadoId = Teclado.nextInt();
        Teclado.nextLine();
        List<ReportePorEstado> todos = ABMReporte.reporteEstadoId(estadoId);
        for (ReportePorEstado r : todos) {
            mostrarReporte(r);
        }
    }

    public void listarPorHuespedes() {

        List<ReportePorHuesped> todos = ABMReporte.reporteHuespedes();
        for (ReportePorHuesped r : todos) {
            mostrarReporte(r);
        }
    }

    public void listarPorHuespedId() {

        int huespedId;
        System.out.println("Ingrese Id del estado");
        huespedId = Teclado.nextInt();
        Teclado.nextLine();
        List<ReportePorHuesped> todos = ABMReporte.reporteHuespedId(huespedId);
        for (ReportePorHuesped r : todos) {
            mostrarReporte(r);
        }
    }


    public void mostrarReporte(ReportePorEstado repo){

        System.out.println("Tipo estado Id: " + repo.getTipoEstadoId() + " Descripcion: " + repo.getDescripcion() + " Cantidad de reservas: " + repo.getCantidadReservas() + 
        " Pagos reserva: " + repo.getPagosReserva() + " Total pagos recibidos: " + repo.getTotalPagosRecibidos() + " Importe total: " + repo.getImporteTotal());
    }

    public void mostrarReporte(ReportePorHuesped repo){
        
        System.out.println("Huesped Id: " + repo.getHuespedId() + " Nombre: " + repo.getNombre() + " Cantidad de reservas: " + repo.getCantidadReservas() + 
        " Pagos reserva: " + repo.getPagosReserva() + " Total pagos recibidos: " + repo.getTotalPagosRecibidos() + " Importe total: " + repo.getImporteTotal());
    }
    
}