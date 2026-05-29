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

    public Individuo.Genero leerGenero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim().toUpperCase();

            if (texto.equals("H") || texto.equals("HOMBRE")) {
                return Individuo.Genero.HOMBRE;
            }

            if (texto.equals("M") || texto.equals("MUJER")) {
                return Individuo.Genero.MUJER;
            }

            System.out.println("Genero invalido. Escriba HOMBRE/MUJER o H/M.");
        }
    }

    public ArrayList<Individuo> leerMuestraManual() {
        int n;

        do {
            n = leerEntero("Ingrese la cantidad de sujetos de la muestra: ");
            if (n < 2) {
                System.out.println("Se necesitan al menos 2 datos para interpolar.");
            }
        } while (n < 2);

        ArrayList<Individuo> muestra = new ArrayList<>();

        FormatoConsola.seccion("INGRESO DE DATOS");
        for (int i = 0; i < n; i++) {
            System.out.println("\nSujeto " + (i + 1));
            Individuo.Genero genero = leerGenero("Genero (H/M): ");
            double altura = leerDouble("Altura en cm: ");
            double edad = leerDouble("Edad en anios: ");
            double cubito = leerDouble("Medida del cubito en cm: ");

            muestra.add(new Individuo(altura, genero, cubito, edad));
        }

        return muestra;
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
