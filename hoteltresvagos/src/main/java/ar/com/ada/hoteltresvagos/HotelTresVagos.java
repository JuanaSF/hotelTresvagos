package ar.com.ada.hoteltresvagos;

import java.util.Scanner;

import ar.com.ada.hoteltresvagos.excepciones.*;
import ar.com.ada.hoteltresvagos.managers.*;
import ar.com.ada.hoteltresvagos.services.*;

public class HotelTresVagos {

    public static Scanner Teclado = new Scanner(System.in);

    protected HuespedManager ABMHuesped = new HuespedManager();
    protected HuespedService ayudanteHuesped = new HuespedService(ABMHuesped);

    protected ReservaManager ABMReserva = new ReservaManager();
    protected ReservaService ayudanteReserva = new ReservaService(ABMReserva, ABMHuesped);

    protected ReporteManager AMBReporte = new ReporteManager();
    protected ReporteService ayudanteReporte = new ReporteService(AMBReporte);
    

    public void iniciar() throws Exception {

        try {

            AMBReporte.setup();

            printOpcionesGeneral();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:
                        gestionarHuesped();
                        break;

                    case 2:
                        gestionarReserva();
                        break;

                    case 3:
                        gestionarReporte();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printOpcionesGeneral();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

        } catch (Exception e) {
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del sistema, bye bye...");

        }

    }

    public void gestionarHuesped() throws Exception {

        try {

            ABMHuesped.setup();

            printOpcionesHuesped();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:

                        try {
                            ayudanteHuesped.alta();
                        } catch (HuespedDNIException exdni) {
                            System.out.println("Error en el DNI. Indique uno valido");
                        }
                        break;

                    case 2:
                        ayudanteHuesped.baja();
                        break;

                    case 3:
                        ayudanteHuesped.modificar();
                        break;

                    case 4:
                        ayudanteHuesped.listar();
                        break;

                    case 5:
                        ayudanteHuesped.listarPorNombre();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printOpcionesHuesped();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

            // Hago un safe exit del manager
            ABMHuesped.exit();

        } catch (Exception e) {
            System.out.println("Ha Ocurrido un error");
            throw e;
        } finally {
            System.out.println("Saliendo de Huespedes");

        }

    }

    public void gestionarReserva() throws Exception {

        try {

            ABMReserva.setup();

            printOpcionesReserva();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {

                    case 1:
                        ayudanteReserva.cargarReserva();
                        break;

                    case 2:
                        ayudanteReserva.eliminarReserva();
                        break;

                    case 3:
                        ayudanteReserva.modificarReserva();
                        break;

                    case 4:
                        ayudanteReserva.listarReserva();
                        break;
                    case 5:
                        ayudanteReserva.listarReservaPorNombre();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printOpcionesReserva();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

            ABMReserva.exit();

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error");
            throw e;
        } finally {
            System.out.println("Saliendo de reservas..");

        }

    }

    public void gestionarReporte() throws Exception {

        try {

            AMBReporte.setup();

            printOpcionesReporte();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {

                    case 1:
                        ayudanteReporte.listarPorEstados();
                        break;

                    case 2:
                        ayudanteReporte.listarPorEstadoId();
                        break;

                    case 3:
                        ayudanteReporte.listarPorHuespedes();
                        break;

                    case 4:
                        ayudanteReporte.listarPorHuespedId();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printOpcionesReporte();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

            AMBReporte.exit();

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error");
            throw e;
        } finally {
            System.out.println("Saliendo de reportes..");

        }

    }

    public static void printOpcionesGeneral() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para huesped.");
        System.out.println("2. Para reserva.");
        System.out.println("3. Para reportes.");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }

    public static void printOpcionesHuesped() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para agregar un huesped.");
        System.out.println("2. Para eliminar un huesped.");
        System.out.println("3. Para modificar un huesped.");
        System.out.println("4. Para ver el listado.");
        System.out.println("5. Buscar un huesped por nombre especifico.");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }

    public static void printOpcionesReserva() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para cargar una reserva");
        System.out.println("2. Para eliminar una reserva");
        System.out.println("3. Para modificar una reserva");
        System.out.println("4. Para ver listado de reserva");
        System.out.println("5. Para buscar reserva(s) con el nombre");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }

    public static void printOpcionesReporte() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1.Para ver reporte de los estados.");
        System.out.println("2.Para ver reporte por estado.");
        System.out.println("3.Para ver reporte de los huespedes.");
        System.out.println("4.Para ver reporte por huesped.");
        System.out.println("0.Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }
}