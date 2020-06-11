package ar.com.ada.hoteltresvagos.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ar.com.ada.hoteltresvagos.entities.*;
import ar.com.ada.hoteltresvagos.excepciones.HuespedDNIException;
import ar.com.ada.hoteltresvagos.managers.*;

public class ReservaService {

    public static Scanner Teclado = new Scanner(System.in);

    protected ReservaManager ABMReserva = new ReservaManager();
    protected HuespedManager ABMHuesped = new HuespedManager();

    public void cargarReserva() throws HuespedDNIException {

        Reserva reserva = new Reserva();
        ABMReserva.setup();
        System.out.println("Desea realizar la reserva con un huesped ya registrado?");
        String respuesta;
        System.out.println("Ingrese si o no");
        respuesta = Teclado.nextLine();

        if (respuesta.equalsIgnoreCase("no")) {
            Huesped huesped = new Huesped();
            System.out.println("Ingrese el nombre:");
            huesped.setNombre(Teclado.nextLine());
            System.out.println("Ingrese el DNI:");
            huesped.setDni(Teclado.nextInt());
            Teclado.nextLine();
            System.out.println("Ingrese la domicilio:");
            huesped.setDomicilio(Teclado.nextLine());

            System.out.println("Ingrese el Domicilio alternativo(OPCIONAL):");

            String domAlternativo = Teclado.nextLine();

            if (domAlternativo != null)
                huesped.setDomicilioAlternativo(domAlternativo);

            ABMHuesped.create(huesped);
            reserva.setHuesped(huesped);

        } else {
            System.out.println("Ingrese el dni para comenzar la reserva");
            int dni = (Teclado.nextInt());
            Teclado.nextLine();
            reserva.setHuesped(ABMReserva.buscarHuespedDNI(dni)); // Esta es la relacion bidireccional
        }

        BigDecimal importeReserva = new BigDecimal(1000);

        reserva.setImporteReserva(importeReserva); // Forma 1

        reserva.setImporteTotal(new BigDecimal(3000)); // Forma 2

        reserva.setImportePagado(new BigDecimal(0));

        reserva.setFechaReserva(new Date()); // Fecha actual

        System.out.println("Ingrese la fecha de ingreso(dd/mm/yy)");

        Date fechaIngreso = null;
        Date fechaEgreso = null;

        DateFormat dFormat = new SimpleDateFormat("dd/MM/yy");

        // Alternativa de leer fecha con try catch
        try {
            fechaIngreso = dFormat.parse(Teclado.nextLine());

        } catch (Exception ex) {
            System.out.println("Ingreso una fecha invalida.");
            System.out.println("Vuelva a e empezar");
            return;
        }

        System.out.println("Ingrese la fecha de egreso(dd/mm/yy)");
        try {
            fechaEgreso = dFormat.parse(Teclado.nextLine());
        } catch (ParseException e) {
            System.out.println("Ingreso una fecha invalida.");
            System.out.println("Vuelva a e empezar");
        }

        reserva.setFechaIngreso(fechaIngreso);
        reserva.setFechaEgreso(fechaEgreso); // por ahora 1 dia.
        reserva.setTipoEstadoId(20); // En mi caso, 20 significa pagado.

        System.out.println("Ingrese numero de habitacion(OPTATIVO)");

        Integer habitacion = Teclado.nextInt();

        if (habitacion != null)
            reserva.setHabitacion(habitacion);

        ABMReserva.create(reserva);

        System.out.println("Reserva generada con exito. [ Numero de reserva :" + reserva.getReservaId()
                + ", a nombre de " + reserva.getHuesped().getNombre() + "]");
    }

    public void listarReserva() {

        List<Reserva> todos = ABMReserva.buscarTodos();
        for (Reserva r : todos) {
            mostrarReserva(r);
        }
    }

    public void listarReservaPorNombre(){
        System.out.println("Ingrese nombre para traer sus reservas");
        String nombre = Teclado.nextLine();
        List<Reserva> reservas = ABMReserva.buscarPorNombreHuesped(nombre);
        for (Reserva r : reservas) {
            mostrarReserva(r);
        }
    }

    public void mostrarReserva(Reserva reserva) {

        System.out.print("Id: " + reserva.getReservaId() + " Fecha Reserva: " + reserva.getFechaReserva()
                + " Fecha Ingreso: " + reserva.getFechaIngreso() + " Fecha Egreso: " + reserva.getFechaEgreso() + "");

        if (reserva.getHabitacion() != null)
            System.out.println(" Habitacion n°: " + reserva.getHabitacion());
        else
            System.out.println();
    }
    
}