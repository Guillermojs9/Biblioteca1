package biblioteca1;

import java.util.Scanner;

public class LeerDatosTeclado {

    public static double leerDouble(String mensaje) {
        Scanner teclado = new Scanner(System.in);
        System.out.println(mensaje);
        while (!teclado.hasNextDouble()) {
            teclado.nextLine();
            System.out.println("No se ha introducido un double. Vuelve a introducir el valor ");
        }
        double valor = teclado.nextDouble();
        return valor;
    }

    public static double leerDouble(String mensaje, double minimo) {
        Scanner teclado = new Scanner(System.in);
        double valor;

        do {

            System.out.println(mensaje);
            while (!teclado.hasNextDouble()) {
                teclado.nextLine();
                System.out.println("No se ha introducido un double. Vuelve a introducir el valor ");
            }
            valor = teclado.nextDouble();
            if (valor < minimo) {
                System.out.println("El valor no tiene el rango adecuado.");
            }

        } while (valor < minimo);

        return valor;
    }

    public static int leerInt(String mensaje) {
        Scanner teclado = new Scanner(System.in);
        System.out.println(mensaje);
        while (!teclado.hasNextInt()) {
            teclado.nextLine();
            System.out.println("No se ha introducido un numero entero positivo. Vuelve a introducir el valor ");
        }
        int valor = teclado.nextInt();
        return valor;
    }

    public static int leerInt(String mensaje, int minimo, int maximo) {
        Scanner teclado = new Scanner(System.in);
        int valor;

        do {

            System.out.println(mensaje);
            while (!teclado.hasNextInt()) {
                teclado.nextLine();
                System.out.println("No se ha introducido un numero entero positivo. Vuelve a introducir el valor ");
            }
            valor = teclado.nextInt();
            if (valor < minimo) {
                System.out.println("El valor no tiene el rango adecuado.");
            }
            if (valor > maximo) {
                System.out.println("El valor no tiene el rango adecuado.");
            }

        } while (valor < minimo || valor > maximo);

        return valor;
    }

    public static int leerInt(String mensaje, int minimo) {
        Scanner teclado = new Scanner(System.in);
        int valor;
        do {
            System.out.println(mensaje);
            while (!teclado.hasNextInt()) {
                teclado.nextLine();
                System.out.println("No se ha introducido un numero entero positivo. Vuelve a introducir el valor ");
            }
            valor = teclado.nextInt();
            if (valor < minimo) {
                System.out.println("El valor no tiene el rango adecuado.");
            }
        } while (valor < minimo);
        return valor;
    }
    
    public static String leerString(String mensaje){
        System.out.println(mensaje);
        Scanner teclado = new Scanner(System.in);
        String cadena = teclado.nextLine();
        return cadena;
    }

}
