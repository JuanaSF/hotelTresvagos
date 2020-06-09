package ar.com.ada.hoteltresvagos.managers;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.hoteltresvagos.entities.*;

public class ReservaManager {

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

    public void create(Reserva reserva) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(reserva);

        session.getTransaction().commit();
        session.close();
    }

    //Tendra que ir reservaId??
    public Huesped read(int huespedId) {
        Session session = sessionFactory.openSession();

        Huesped huesped = session.get(Huesped.class, huespedId);

        session.close();

        return huesped;
    }
    //Este no es necesario para reservas, a menos que se quiera buscar algo con el dni
    public Huesped buscarHuespedDNI(int dni) {
        Session session = sessionFactory.openSession();

        Huesped huesped = session.byNaturalId(Huesped.class).using("dni", dni).load();

        session.close();

        return huesped;
    }

    public void update(Reserva reserva) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(reserva);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Reserva reserva) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(reserva);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Este metodo en la vida real no debe existir ya qeu puede haber miles de
     * usuarios
     * 
     * @return
     */
    public List<Reserva> buscarTodos() {

        Session session = sessionFactory.openSession();

        /// NUNCA HARCODEAR SQLs nativos en la aplicacion.
        // ESTO es solo para nivel educativo
        Query query = session.createNativeQuery("SELECT * FROM reserva", Reserva.class);
        //query = session.createQuery("From Obse")
        List<Reserva> todos = query.getResultList();

        return todos;

    }

    public List<Reserva> buscarPorNombreHuesped(String nombre) {

        Session session = sessionFactory.openSession();
        //Forma sql query nativa con parametros
        Query queryForma1 = session.createNativeQuery(
            "SELECT *  FROM reserva r inner join huesped h on h.huesped_id = r.huesped_id where nombre = ?", Reserva.class);
            queryForma1.setParameter(1, nombre);

        //Forma query utilizando JPQL (a traves del lenguaje de java). select sobre los objetos.
        Query queryForma2 = session.createQuery("Select r from Reserva r where r.huesped.nombre = :nombre", Reserva.class);
        queryForma2.setParameter("nombre", nombre);

        List<Reserva> reservas = queryForma1.getResultList();

        return reservas;


    }

}
