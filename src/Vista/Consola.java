package Vista;

import Data.Individuo;

import java.util.ArrayList;
import java.util.Scanner;

public class Consola {

    private final Scanner scanner;

    public Consola(Scanner scanner) {
        this.scanner = scanner;
    }

    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            if (scanner.hasNextInt()) {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            }

            System.out.println("Entrada invalida. Ingrese un numero entero.");
            scanner.nextLine();
        }
    }

    public double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            if (scanner.hasNextDouble()) {
                double valor = scanner.nextDouble();
                scanner.nextLine();
                return valor;
            }

            System.out.println("Entrada invalida. Ingrese un numero decimal.");
            scanner.nextLine();
        }
    }

    public void imprimirMuestra(ArrayList<Individuo> muestra) {
        FormatoConsola.seccion("DATOS DE LA MUESTRA");
        System.out.printf("%-15s %-15s %-18s %-15s%n",
                "Genero", "Altura(cm)", "Cubito(cm)", "Edad(anios)");
        FormatoConsola.linea();

        for (Individuo individuo : muestra) {
            System.out.printf("%-15s %-15.2f %-18.2f %-15.2f%n",
                    individuo.getGenero(),
                    individuo.getAltura(),
                    individuo.getMedidaCubito(),
                    individuo.getEdad());
        }
    }

    public void imprimirPuntos(ArrayList<Individuo> muestra) {
        FormatoConsola.seccion("PUNTOS PARA EL POLINOMIO");
        System.out.println("x = edad, y = medida del cubito");
        FormatoConsola.linea();
        System.out.printf("%-15s %-15s%n", "x Edad", "y Cubito");
        FormatoConsola.linea();

        for (Individuo individuo : muestra) {
            System.out.printf("%-15.2f %-15.2f%n",
                    individuo.getEdad(),
                    individuo.getMedidaCubito());
        }
    }
}
