package Vista;

import java.util.List;
import java.util.Scanner;

public class NavegadorPasos {

    private NavegadorPasos() {
    }

    public static void mostrar(String titulo, List<String> pasos, Scanner scanner) {
        if (pasos.isEmpty()) {
            return;
        }

        int indice = 0;
        boolean navegando = true;

        while (navegando) {
            limpiarPantalla();
            System.out.println(FormatoConsola.negrita(titulo));
            System.out.printf("%s %d de %d%n", FormatoConsola.negrita("Parte"), indice + 1, pasos.size());
            FormatoConsola.linea();
            System.out.println(pasos.get(indice));
            FormatoConsola.linea();
            System.out.println(FormatoConsola.negrita("Controles:") + " a = retroceder, d = avanzar, q = salir del proceso");
            System.out.println("En la ultima parte, d tambien termina el proceso.");
            System.out.print("Tecla: ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String entrada = scanner.nextLine().trim().toLowerCase();
            if (entrada.isEmpty()) {
                continue;
            }

            char tecla = entrada.charAt(0);

            switch (tecla) {
                case 'a':
                    if (indice > 0) {
                        indice--;
                    }
                    break;

                case 'd':
                    if (indice < pasos.size() - 1) {
                        indice++;
                    } else {
                        navegando = false;
                    }
                    break;

                case 'q':
                    navegando = false;
                    break;

                default:
                    System.out.println("Comando no reconocido. Use a, d o q.");
                    pausar(scanner);
                    break;
            }
        }

        limpiarPantalla();
    }

    private static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void pausar(Scanner scanner) {
        System.out.print("Presione Enter para continuar...");
        scanner.nextLine();
    }
}
