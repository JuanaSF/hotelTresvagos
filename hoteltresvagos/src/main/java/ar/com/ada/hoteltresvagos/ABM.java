package ar.com.ada.hoteltresvagos;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.exception.ConstraintViolationException;

import ar.com.ada.hoteltresvagos.entities.*;
import ar.com.ada.hoteltresvagos.excepciones.*;
import ar.com.ada.hoteltresvagos.managers.*;

public class ABM {

    public static Scanner Teclado = new Scanner(System.in);

    protected HuespedManager ABMHuesped = new HuespedManager();
    protected ReservaManager ABMReserva = new ReservaManager();

    public void iniciar() throws Exception {

        try {

            ABMHuesped.setup();
            ABMReserva.setup();

            printOpciones();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:

                        try {
                            alta();
                        } catch (HuespedDNIException exdni) {
                            System.out.println("Error en el DNI. Indique uno valido");
                        }
                        break;

                    case 2:
                        baja();
                        break;

                    case 3:
                        modifica();
                        break;

                    case 4:
                        listar();
                        break;

                    case 5:
                        listarPorNombre();
                        break;

                    case 6:
                        cargarReserva();
                        break;

                    case 7:
                        listarReservaPorNombre();
                        break;

                    case 8:
                        listarReserva();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printOpciones();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

            // Hago un safe exit del manager
            ABMHuesped.exit();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del sistema, bye bye...");

        }

    }

    public void alta() throws Exception {
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

        /*
         * Si concateno el OBJETO directamente, me trae todo lo que este en el metodo
         * toString() mi recomendacion es NO usarlo para imprimir cosas en pantallas, si
         * no para loguear info Lo mejor es usar:
         * System.out.println("Huesped generada con exito.  " + huesped.getHuespedId);
         */

        System.out.println(
                "Huesped generada con exito. [dni=" + huesped.getDni() + ", nombre=" + huesped.getNombre() + "]");

    }

    public void baja() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el ID de Huesped:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {

            try {

                ABMHuesped.delete(huespedEncontrado);
                System.out
                        .println("El registro del huesped " + huespedEncontrado.getHuespedId() + " ha sido eliminado.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error al eliminar una huesped. Error: " + e.getCause());
            }

        }
    }

    public void bajaPorDNI() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el DNI de Huesped:");
        int dni = Teclado.nextInt();
        Huesped huespedEncontrado = ABMHuesped.readByDNI(dni);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {
            ABMHuesped.delete(huespedEncontrado);
            System.out.println("El registro del DNI " + huespedEncontrado.getDni() + " ha sido eliminado.");
        }
    }

    public void modifica() throws Exception {
        // System.out.println("Ingrese el nombre de la huesped a modificar:");
        // String n = Teclado.nextLine();

        System.out.println("Ingrese el ID de la huesped a modificar:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        if (huespedEncontrado != null) {

            // RECOMENDACION NO USAR toString(), esto solo es a nivel educativo.
            System.out.println(huespedEncontrado.toString() + " seleccionado para modificacion.");

            System.out.println(
                    "Elija qué dato de la huesped desea modificar: \n1: nombre, \n2: DNI, \n3: domicilio, \n4: domicilio alternativo");
            int selecper = Teclado.nextInt();

            switch (selecper) {
                case 1:
                    System.out.println("Ingrese el nuevo nombre:");
                    Teclado.nextLine();
                    huespedEncontrado.setNombre(Teclado.nextLine());

                    break;
                case 2:
                    System.out.println("Ingrese el nuevo DNI:");
                    Teclado.nextLine();
                    huespedEncontrado.setDni(Teclado.nextInt());
                    Teclado.nextLine();

                    break;
                case 3:
                    System.out.println("Ingrese el nuevo domicilio:");
                    Teclado.nextLine();
                    huespedEncontrado.setDomicilio(Teclado.nextLine());

                    break;
                case 4:
                    System.out.println("Ingrese el nuevo domicilio alternativo:");
                    Teclado.nextLine();
                    huespedEncontrado.setDomicilioAlternativo(Teclado.nextLine());

                    break;

                default:
                    break;
            }

            // Teclado.nextLine();

            ABMHuesped.update(huespedEncontrado);

            System.out.println("El registro de " + huespedEncontrado.getNombre() + " ha sido modificado.");

        } else {
            System.out.println("Huesped no encontrado.");
        }

    }

    public void listar() {

        List<Huesped> todos = ABMHuesped.buscarTodos();
        for (Huesped c : todos) {
            mostrarHuesped(c);
        }
    }

    public void listarPorNombre() {

        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();

        List<Huesped> huespedes = ABMHuesped.buscarPor(nombre);
        for (Huesped huesped : huespedes) {
            mostrarHuesped(huesped);
        }
    }

    public void mostrarHuesped(Huesped huesped) {

        System.out.print("Id: " + huesped.getHuespedId() + " Nombre: " + huesped.getNombre() + " DNI: "
                + huesped.getDni() + " Domicilio: " + huesped.getDomicilio());

        if (huesped.getDomicilioAlternativo() != null)
            System.out.println(" Alternativo: " + huesped.getDomicilioAlternativo());
        else
            System.out.println();
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

    public static void printOpciones() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para agregar un huesped.");
        System.out.println("2. Para eliminar un huesped.");
        System.out.println("3. Para modificar un huesped.");
        System.out.println("4. Para ver el listado.");
        System.out.println("5. Buscar un huesped por nombre especifico(SQL Injection)).");
        System.out.println("6. Para cargar una reserva");
        System.out.println("7. Para buscar reserva con el nombre");
        System.out.println("8. Para ver listado de reserva");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }
}