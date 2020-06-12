package ar.com.ada.hoteltresvagos.managers;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.hoteltresvagos.entities.reportes.*;

public class ReporteManager {

    protected SessionFactory sessionFactory;

    public void setup() {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
                                                                                                  // from
                                                                                                  // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw ex;
        }

    }

    public void exit() {
        sessionFactory.close();
    }

    public void create(Reporte reporte) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(reporte);

        session.getTransaction().commit();
        session.close();
    }

    public ReportePorEstado read(int tipoEstadoId) {
        Session session = sessionFactory.openSession();

        ReportePorEstado repo = session.get(ReportePorEstado.class, tipoEstadoId);

        session.close();

        return repo;
    }

    public void update(Reporte reporte) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(reporte);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Reporte reporte) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(reporte);

        session.getTransaction().commit();
        session.close();
    }

    public List<ReportePorEstado> reporteEstados() {

        Session session = sessionFactory.openSession();
 
        Query query = session.createNativeQuery(
                "select r.tipo_estado_id, e.descripcion, count(r.tipo_estado_id) cantidad_reservas,"+
                " sum(r.importe_reserva) pagos_reserva, sum(r.importe_pagado) total_pagos_recibidos,"+
                " sum(r.importe_total) importe_total from reserva r inner join tipo_estado e on r.tipo_estado_id = e.tipo_estado_id"+
                " group by e.tipo_estado_id, e.descripcion",
                ReportePorEstado.class);

        List<ReportePorEstado> reporteEstados = query.getResultList();

        return reporteEstados;

    }

    public List<ReportePorEstado> reporteEstadoId(int estadoId) {

        Session session = sessionFactory.openSession();
        // Forma sql query nativa con parametros
        Query query = session.createNativeQuery(
                "select r.tipo_estado_id, e.descripcion, count(r.tipo_estado_id) cantidad_reservas,"+
                " sum(r.importe_reserva) pagos_reserva, sum(r.importe_pagado) total_pagos_recibidos,"+
                " sum(r.importe_total) importe_total, sum(r.importe_total - r.importe_reserva - r.importe_pagado)"+
                " from reserva r inner join tipo_estado e on r.tipo_estado_id = e.tipo_estado_id"+
                " where e.tipo_estado_id = ? group by e.tipo_estado_id, e.descripcion",
                ReportePorEstado.class);
        query.setParameter(1, estadoId);

        List<ReportePorEstado> reporteEstadoId = query.getResultList();

        return reporteEstadoId;

    }

    public List<ReportePorHuesped> reporteHuespedes() {

        Session session = sessionFactory.openSession();
 
        Query query = session.createNativeQuery(
                "select h.huesped_id, h.nombre, count(r.reserva_id) cantidad_reservas, sum(r.importe_reserva) pagos_reserva, sum(r.importe_pagado) total_pagos_recibidos, sum(r.importe_total) importe_total from huesped h inner join reserva r on h.huesped_id = r.huesped_id group by h.huesped_id, h.nombre;",
                ReportePorHuesped.class);

        List<ReportePorHuesped> reporteHuespedes = query.getResultList();

        return reporteHuespedes;

    }

    public List<ReportePorHuesped> reporteHuespedId(int huespedId) {

        Session session = sessionFactory.openSession();
        // Forma sql query nativa con parametros
        Query query = session.createNativeQuery(
            "select h.huesped_id, h.nombre, count(r.reserva_id) cantidad_reservas,"+
            " sum(r.importe_reserva) pagos_reserva,"+
            " sum(r.importe_pagado) total_pagos_recibidos,"+
            " sum(r.importe_total) importe_total "+
            " from huesped h inner join reserva r on h.huesped_id = r.huesped_id"+
            " where h.huesped_id = ?"+
            " group by h.huesped_id, h.nombre",ReportePorHuesped.class);
        query.setParameter(1, huespedId);

        List<ReportePorHuesped> reporteHuespedId = query.getResultList();

        return reporteHuespedId;

    }
}