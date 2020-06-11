package ar.com.ada.hoteltresvagos.services;

import java.util.List;

import ar.com.ada.hoteltresvagos.entities.reportes.ReportePorEstado;
import ar.com.ada.hoteltresvagos.managers.ReporteManager;

public class ReporteService {

    protected ReporteManager ABMReporte = new ReporteManager();

               
    public void listarRepo() {

        ABMReporte.setup();
        List<ReportePorEstado> todos = ABMReporte.listarReporte();
        for (ReportePorEstado r : todos) {
            mostrarReporte(r);
        }
    }

    public void mostrarReporte(ReportePorEstado repo){
        System.out.println("Cantidad de reservas: "+repo.getCantidadReservas()+" Descripcion: "+repo.getDescripcion()+" Id Estado: "+repo.getTipoEstadoId());
    }
    
}