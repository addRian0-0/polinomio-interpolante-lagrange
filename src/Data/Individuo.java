package Data;

public class Individuo {

    public enum Genero {
        HOMBRE,
        MUJER
    }

    private Genero genero;
    private double altura;
    private double medidacubit;
    private double edad;

    public Individuo(double altura, Genero genero, double medidacubit, double edad) {
        this.altura = altura;
        this.genero = genero;
        this.medidacubit = medidacubit;
        this.edad = edad;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getMedidacubit() {
        return medidacubit;
    }

    public void setMedidacubit(double medidacubit) {
        this.medidacubit = medidacubit;
    }

    public double getEdad() {
        return edad;
    }

    public void setEdad(double edad) {
        this.edad = edad;
    }

    @Override
    public String toString(){
        return "Individuo {" +
                "Genero: " + genero +
                "Altura: " + altura +
                "Medida cubito: " + medidacubit +
                "Edad: " + edad +
                "}";
    }
}