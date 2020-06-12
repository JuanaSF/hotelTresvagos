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

    protected ReservaManager ABMReserva;
    protected HuespedManager ABMHuesped;

    public ReservaService(ReservaManager ABMReserva, HuespedManager ABMHuesped) {
        this.ABMReserva = ABMReserva;
        this.ABMHuesped = ABMHuesped;
    }

    public void cargarReserva() throws HuespedDNIException {

        Reserva reserva = new Reserva();
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
            return;
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

    public void eliminarReserva() {

        System.out.println("Ingrese el Nro(Id) de Reserva:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Reserva reservaEncontrada = ABMReserva.read(id);

        if (reservaEncontrada == null) {
            System.out.println("Reserva no encontrada.");

        } else {

            try {

                ABMReserva.delete(reservaEncontrada);
                System.out.println(
                        "El registro de la reserva " + reservaEncontrada.getReservaId() + " ha sido eliminado.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error al eliminar una huesped. Error: " + e.getCause());
            }

        }
    }

    public void modificarReserva() {

        System.out.println("Ingrese el ID de la reserva a modificar:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Reserva reservaEncontrada = ABMReserva.read(id);

        if (reservaEncontrada != null) {

            System.out.println("Reserva: " + reservaEncontrada.getReservaId() + " seleccionada para modificacion.");

            System.out.println(
                    "Elija qué dato de la reserva que desea modificar: \n1: fecha de reserva \n2: fecha de ingreso \n3: fecha de egreso \n4: habitación \n5: importe de reserva \n6: importe total \n7: importe pagado \n8: Id del estado de pago");
            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            DateFormat dFormat = new SimpleDateFormat("dd/MM/yy");

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese la nueva fecha de reserva:");
                    Date fechaReserva = null;
                    try {
                        fechaReserva = dFormat.parse(Teclado.nextLine());

                    } catch (Exception ex) {
                        System.out.println("Ingreso una fecha invalida.");
                        System.out.println("Vuelva a e empezar");
                        return;
                    }
                    reservaEncontrada.setFechaReserva(fechaReserva);

                    break;
                case 2:
                    System.out.println("Ingrese la nueva fecha de ingreso:");
                    Date fechaIngreso = null;

                    try {
                        fechaIngreso = dFormat.parse(Teclado.nextLine());

                    } catch (Exception ex) {
                        System.out.println("Ingreso una fecha invalida.");
                        System.out.println("Vuelva a e empezar");
                        return;
                    }
                    reservaEncontrada.setFechaIngreso(fechaIngreso);

                    break;
                case 3:
                    System.out.println("Ingrese la nueva fecha de egreso:");
                    Date fechaEgreso = null;

                    try {
                        fechaEgreso = dFormat.parse(Teclado.nextLine());

                    } catch (Exception ex) {
                        System.out.println("Ingreso una fecha invalida.");
                        System.out.println("Vuelva a e empezar");
                        return;
                    }
                    reservaEncontrada.setFechaEgreso(fechaEgreso);

                    break;
                case 4:
                    System.out.println("Ingrese el nuevo numero de habitacion:");
                    Integer habitacion = Teclado.nextInt();
                    reservaEncontrada.setHabitacion(habitacion);

                    break;
                case 5:
                    System.out.println("Ingrese nuevo importe reserva:");
                    BigDecimal importeReserva = Teclado.nextBigDecimal();
                    Teclado.nextLine();
                    reservaEncontrada.setImporteReserva(importeReserva);

                    break;
                case 6:
                    System.out.println("Ingrese nuevo importe total:");
                    BigDecimal importeTotal = Teclado.nextBigDecimal();
                    Teclado.nextLine();
                    reservaEncontrada.setImporteTotal(importeTotal);

                    break;
                case 7:
                    System.out.println("Ingrese nuevo importe pagado:");
                    BigDecimal importepagado = Teclado.nextBigDecimal();
                    Teclado.nextLine();
                    reservaEncontrada.setImportePagado(importepagado);

                    break;
                case 8:
                    System.out.println("Ingrese el nuevo estado ID:");
                    Teclado.nextInt();
                    reservaEncontrada.setTipoEstadoId(Teclado.nextInt());

                    break;

                default:
                    break;
            }

            ABMReserva.update(reservaEncontrada);

            System.out.println("El registro de la reserva: " + reservaEncontrada.getReservaId() + " ha sido modificado.");

        } else {
            System.out.println("reserva no encontrada.");
        }

    }

    public void listarReserva() {

        List<Reserva> todos = ABMReserva.buscarTodos();
        for (Reserva r : todos) {
            mostrarReserva(r);
        }
    }

    public void listarReservaPorNombre() {
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

    /*
     * public Date ingresarFecha() throws Exception {
     * 
     * Date fecha = null;
     * 
     * DateFormat dFormat = new SimpleDateFormat("dd/MM/yy");
     * 
     * //System.out.println("Ingrese la fecha de ingreso(dd/mm/yy)");
     * 
     * try { fecha = dFormat.parse(Teclado.nextLine());
     * 
     * } catch (Exception ex) { System.out.println("Ingreso una fecha invalida.");
     * System.out.println("Vuelva a e empezar"); }
     * 
     * return fecha; }
     */

}