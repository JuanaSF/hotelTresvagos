package ar.com.ada.hoteltresvagos;

import java.util.Scanner;

import ar.com.ada.hoteltresvagos.excepciones.*;
import ar.com.ada.hoteltresvagos.managers.*;
import ar.com.ada.hoteltresvagos.services.HuespedService;
import ar.com.ada.hoteltresvagos.services.ReservaService;
import ar.com.ada.hoteltresvagos.services.ReporteService;

public class HotelTresVagos {

    public static Scanner Teclado = new Scanner(System.in);

    protected HuespedManager ABMHuesped = new HuespedManager();
    protected HuespedService ayudanteHuesped = new HuespedService();
    protected ReservaManager ABMReserva = new ReservaManager();
    protected ReservaService ayudanteReserva = new ReservaService();
    protected ReporteService ayudanteReporte = new ReporteService();
    protected ReporteManager AMBRepo = new ReporteManager();

    public void iniciar() throws Exception {

        try {

            //ABMHuesped.setup();
            ABMReserva.setup();
            AMBRepo.setup();

            printOpciones();

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

                    case 6:
                        ayudanteReserva.cargarReserva();
                        break;

                    case 7:
                        ayudanteReserva.listarReservaPorNombre();
                        break;

                    case 8:
                        ayudanteReserva.listarReserva();
                        break;

                    case 9:
                        ayudanteReporte.listarRepo();
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
            ABMReserva.exit();
            AMBRepo.exit();

        } catch (Exception e) {
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del sistema, bye bye...");

        }

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
        System.out.println("9. Para ver Estado");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }
}