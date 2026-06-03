package Vista;

import Interpolacion.CalculadoraVelocidad;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class GraficaVelocidad {

    private static final double EPSILON = 0.000001;

    private GraficaVelocidad() {
    }

    public static Path generar(String metodo, double[] edades, double[] cubitos, double edadUsuario) throws IOException {
        ArrayList<PuntoVelocidad> puntos = crearPuntos(metodo, edades, cubitos, edadUsuario);

        imprimirGraficaConsola(metodo, puntos);

        Path carpeta = crearCarpetaEjecucion();
        Path archivo = carpeta.resolve("grafica_velocidad_cubito.svg");
        Files.writeString(archivo, crearSvg(metodo, puntos), StandardCharsets.UTF_8);

        System.out.println();
        System.out.println(FormatoConsola.negrita("Grafica SVG generada en:"));
        System.out.println(archivo.toAbsolutePath());

        return archivo;
    }

    private static ArrayList<PuntoVelocidad> crearPuntos(String metodo, double[] edades, double[] cubitos, double edadUsuario) {
        ArrayList<PuntoVelocidad> puntos = new ArrayList<>();
        boolean edadUsuarioYaExiste = false;

        for (double edad : edades) {
            boolean esUsuario = Math.abs(edad - edadUsuario) < EPSILON;
            if (esUsuario) {
                edadUsuarioYaExiste = true;
            }

            puntos.add(new PuntoVelocidad(
                    edad,
                    calcularVelocidad(metodo, edades, cubitos, edad),
                    esUsuario ? "estatica + usuario" : "estatica"));
        }

        if (!edadUsuarioYaExiste) {
            puntos.add(new PuntoVelocidad(
                    edadUsuario,
                    calcularVelocidad(metodo, edades, cubitos, edadUsuario),
                    "usuario"));
        }

        puntos.sort(Comparator.comparingDouble(PuntoVelocidad::getEdad));
        return puntos;
    }

    private static double calcularVelocidad(String metodo, double[] edades, double[] cubitos, double edad) {
        if ("Newton".equalsIgnoreCase(metodo)) {
            return CalculadoraVelocidad.velocidadNewton(edades, cubitos, edad);
        }

        return CalculadoraVelocidad.velocidadLagrange(edades, cubitos, edad);
    }

    private static void imprimirGraficaConsola(String metodo, ArrayList<PuntoVelocidad> puntos) {
        FormatoConsola.titulo("GRAFICA EN CONSOLA DE P′(x)");
        System.out.println(FormatoConsola.negrita("Metodo usado para la grafica: ") + metodo);
        System.out.println("Cada fila muestra la velocidad de crecimiento del cubito en una edad.");
        System.out.println("El simbolo | marca el cero; * marca el valor de P′(x).");
        System.out.println();

        double minimo = Math.min(0.0, obtenerMinimoVelocidad(puntos));
        double maximo = Math.max(0.0, obtenerMaximoVelocidad(puntos));

        if (Math.abs(maximo - minimo) < EPSILON) {
            minimo -= 1.0;
            maximo += 1.0;
        }

        int ancho = 50;
        int posicionCero = posicionEnBarra(0.0, minimo, maximo, ancho);

        System.out.printf("%-12s %-22s %-16s %s%n", "Edad", "Tipo", "P′(x)", "Grafica");
        FormatoConsola.linea();

        for (PuntoVelocidad punto : puntos) {
            char[] barra = new char[ancho + 1];
            for (int i = 0; i < barra.length; i++) {
                barra[i] = ' ';
            }

            barra[posicionCero] = '|';
            int posicionValor = posicionEnBarra(punto.getVelocidad(), minimo, maximo, ancho);

            int inicio = Math.min(posicionCero, posicionValor);
            int fin = Math.max(posicionCero, posicionValor);
            for (int i = inicio; i <= fin; i++) {
                if (barra[i] != '|') {
                    barra[i] = '-';
                }
            }
            barra[posicionValor] = '*';

            System.out.printf("%-12.2f %-22s %-16.6f %s%n",
                    punto.getEdad(), punto.getTipo(), punto.getVelocidad(), new String(barra));
        }
    }

    private static Path crearCarpetaEjecucion() throws IOException {
        String marcaTiempo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        Path carpeta = Path.of("graficas", "ejecucion_" + marcaTiempo);
        Files.createDirectories(carpeta);
        return carpeta;
    }

    private static String crearSvg(String metodo, ArrayList<PuntoVelocidad> puntos) {
        int ancho = 1000;
        int alto = 640;
        int margenIzquierdo = 90;
        int margenDerecho = 40;
        int margenSuperior = 70;
        int margenInferior = 100;

        double edadMinima = obtenerMinimoEdad(puntos);
        double edadMaxima = obtenerMaximoEdad(puntos);
        double velocidadMinima = Math.min(0.0, obtenerMinimoVelocidad(puntos));
        double velocidadMaxima = Math.max(0.0, obtenerMaximoVelocidad(puntos));

        if (Math.abs(edadMaxima - edadMinima) < EPSILON) {
            edadMinima -= 1.0;
            edadMaxima += 1.0;
        }

        if (Math.abs(velocidadMaxima - velocidadMinima) < EPSILON) {
            velocidadMinima -= 1.0;
            velocidadMaxima += 1.0;
        }

        StringBuilder svg = new StringBuilder();
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"")
                .append(ancho).append("\" height=\"").append(alto).append("\" viewBox=\"0 0 ")
                .append(ancho).append(" ").append(alto).append("\">\n");
        svg.append("<rect width=\"100%\" height=\"100%\" fill=\"#f8fafc\"/>\n");
        svg.append("<text x=\"").append(ancho / 2).append("\" y=\"35\" text-anchor=\"middle\" ")
                .append("font-family=\"Arial\" font-size=\"24\" font-weight=\"700\" fill=\"#111827\">")
                .append("Velocidad de crecimiento del cubito - ").append(escaparXml(metodo)).append("</text>\n");
        svg.append("<text x=\"").append(ancho / 2).append("\" y=\"60\" text-anchor=\"middle\" ")
                .append("font-family=\"Arial\" font-size=\"14\" fill=\"#475569\">")
                .append("P′(x) en edades estaticas y edad ingresada por el usuario</text>\n");

        int x0 = margenIzquierdo;
        int y0 = alto - margenInferior;
        int x1 = ancho - margenDerecho;
        int y1 = margenSuperior;

        svg.append("<line x1=\"").append(x0).append("\" y1=\"").append(y0).append("\" x2=\"")
                .append(x1).append("\" y2=\"").append(y0).append("\" stroke=\"#334155\" stroke-width=\"2\"/>\n");
        svg.append("<line x1=\"").append(x0).append("\" y1=\"").append(y0).append("\" x2=\"")
                .append(x0).append("\" y2=\"").append(y1).append("\" stroke=\"#334155\" stroke-width=\"2\"/>\n");

        double yCero = mapearY(0.0, velocidadMinima, velocidadMaxima, y0, y1);
        svg.append("<line x1=\"").append(x0).append("\" y1=\"").append(formato(yCero)).append("\" x2=\"")
                .append(x1).append("\" y2=\"").append(formato(yCero))
                .append("\" stroke=\"#94a3b8\" stroke-width=\"1.5\" stroke-dasharray=\"6 6\"/>\n");
        svg.append("<text x=\"").append(x0 - 12).append("\" y=\"").append(formato(yCero + 5))
                .append("\" text-anchor=\"end\" font-family=\"Arial\" font-size=\"12\" fill=\"#64748b\">0</text>\n");

        svg.append("<polyline fill=\"none\" stroke=\"#2563eb\" stroke-width=\"3\" points=\"");
        for (PuntoVelocidad punto : puntos) {
            svg.append(formato(mapearX(punto.getEdad(), edadMinima, edadMaxima, x0, x1))).append(",")
                    .append(formato(mapearY(punto.getVelocidad(), velocidadMinima, velocidadMaxima, y0, y1))).append(" ");
        }
        svg.append("\"/>\n");

        for (PuntoVelocidad punto : puntos) {
            double cx = mapearX(punto.getEdad(), edadMinima, edadMaxima, x0, x1);
            double cy = mapearY(punto.getVelocidad(), velocidadMinima, velocidadMaxima, y0, y1);
            String color = colorPunto(punto.getTipo());

            svg.append("<circle cx=\"").append(formato(cx)).append("\" cy=\"").append(formato(cy))
                    .append("\" r=\"7\" fill=\"").append(color).append("\" stroke=\"#ffffff\" stroke-width=\"2\"/>\n");
            svg.append("<text x=\"").append(formato(cx)).append("\" y=\"").append(formato(cy - 14))
                    .append("\" text-anchor=\"middle\" font-family=\"Arial\" font-size=\"12\" font-weight=\"700\" fill=\"#111827\">")
                    .append(formato(punto.getVelocidad())).append("</text>\n");
            svg.append("<text x=\"").append(formato(cx)).append("\" y=\"").append(y0 + 24)
                    .append("\" text-anchor=\"middle\" font-family=\"Arial\" font-size=\"12\" fill=\"#334155\">")
                    .append(formato(punto.getEdad())).append("</text>\n");
        }

        svg.append("<text x=\"").append((x0 + x1) / 2).append("\" y=\"").append(alto - 35)
                .append("\" text-anchor=\"middle\" font-family=\"Arial\" font-size=\"15\" font-weight=\"700\" fill=\"#111827\">Edad (anios)</text>\n");
        svg.append("<text x=\"25\" y=\"").append((y0 + y1) / 2)
                .append("\" transform=\"rotate(-90 25 ").append((y0 + y1) / 2)
                .append(")\" text-anchor=\"middle\" font-family=\"Arial\" font-size=\"15\" font-weight=\"700\" fill=\"#111827\">P′(x) cm/año</text>\n");

        svg.append("<rect x=\"").append(ancho - 260).append("\" y=\"").append(alto - 80)
                .append("\" width=\"220\" height=\"48\" fill=\"#ffffff\" stroke=\"#cbd5e1\"/>\n");
        svg.append("<circle cx=\"").append(ancho - 240).append("\" cy=\"").append(alto - 62)
                .append("\" r=\"6\" fill=\"#2563eb\"/><text x=\"").append(ancho - 225)
                .append("\" y=\"").append(alto - 58).append("\" font-family=\"Arial\" font-size=\"12\" fill=\"#334155\">estatica</text>\n");
        svg.append("<circle cx=\"").append(ancho - 240).append("\" cy=\"").append(alto - 42)
                .append("\" r=\"6\" fill=\"#dc2626\"/><text x=\"").append(ancho - 225)
                .append("\" y=\"").append(alto - 38).append("\" font-family=\"Arial\" font-size=\"12\" fill=\"#334155\">usuario / combinada</text>\n");

        svg.append("</svg>\n");
        return svg.toString();
    }

    private static double mapearX(double edad, double edadMinima, double edadMaxima, int x0, int x1) {
        return x0 + ((edad - edadMinima) / (edadMaxima - edadMinima)) * (x1 - x0);
    }

    private static double mapearY(double velocidad, double velocidadMinima, double velocidadMaxima, int y0, int y1) {
        return y0 - ((velocidad - velocidadMinima) / (velocidadMaxima - velocidadMinima)) * (y0 - y1);
    }

    private static int posicionEnBarra(double valor, double minimo, double maximo, int ancho) {
        int posicion = (int) Math.round(((valor - minimo) / (maximo - minimo)) * ancho);
        return Math.max(0, Math.min(ancho, posicion));
    }

    private static double obtenerMinimoEdad(ArrayList<PuntoVelocidad> puntos) {
        return puntos.stream().mapToDouble(PuntoVelocidad::getEdad).min().orElse(0.0);
    }

    private static double obtenerMaximoEdad(ArrayList<PuntoVelocidad> puntos) {
        return puntos.stream().mapToDouble(PuntoVelocidad::getEdad).max().orElse(1.0);
    }

    private static double obtenerMinimoVelocidad(ArrayList<PuntoVelocidad> puntos) {
        return puntos.stream().mapToDouble(PuntoVelocidad::getVelocidad).min().orElse(0.0);
    }

    private static double obtenerMaximoVelocidad(ArrayList<PuntoVelocidad> puntos) {
        return puntos.stream().mapToDouble(PuntoVelocidad::getVelocidad).max().orElse(1.0);
    }

    private static String colorPunto(String tipo) {
        if ("usuario".equals(tipo)) {
            return "#dc2626";
        }

        if ("estatica + usuario".equals(tipo)) {
            return "#7c3aed";
        }

        return "#2563eb";
    }

    private static String formato(double valor) {
        return String.format(java.util.Locale.US, "%.2f", valor);
    }

    private static String escaparXml(String texto) {
        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private static class PuntoVelocidad {
        private final double edad;
        private final double velocidad;
        private final String tipo;

        private PuntoVelocidad(double edad, double velocidad, String tipo) {
            this.edad = edad;
            this.velocidad = velocidad;
            this.tipo = tipo;
        }

        private double getEdad() {
            return edad;
        }

        private double getVelocidad() {
            return velocidad;
        }

        private String getTipo() {
            return tipo;
        }
    }
}
