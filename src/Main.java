import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static int[][] pacientes = new int[50][5];
    public static int numPacientes = 0;

    public static void main(String[] args) {
        System.out.println("Bienvenido al programa del sistema sanitario de Castellon.");
        ejecutarPrograma();
    }

    static void ejecutarPrograma() {
        while (true) {
            System.out.println("Elija una de las siguientes opciones:");
            System.out.println("""
                1. Añadir paciente
                2. Mostrar pacientes
                3. Eliminar paciente
                4. Modificar paciente
                5. Ordenar pacientes
                6. Salir del programa""");
            int opcion = checkEntero("", 1, 6);

            switch (opcion) {
                case 1:
                    añadirPaciente();
                    break;
                case 2:
                    enseñarPacientes();
                    break;
                case 3:
                    eliminarPaciente();
                    break;
                case 4:
                    modificarPaciente();
                    break;
                case 5:
                    // ordenarPacientes();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    return;
            }
        }
    }

    static void añadirPaciente() {
        System.out.println("\nLos datos requeridos para añadir un nuevo paciente son:");
        System.out.println("""
                1. NUSS
                2. Síntoma
                3. Exploración
                4. Nivel de prioridad
                5. Temperatura actual""");

        if (numPacientes >= 50) {
            System.out.println("Error: Se ha alcanzado el maximo de pacientes admitidos.");
            return;
        }

        int nuss = checkEntero("NUSS?: ", 100000, 999999);

        for (int i = 0; i < numPacientes; i++) {
            if (pacientes[i][0] == nuss) {
                System.out.println("Error: Ya existe un paciente con este NUSS.");
                return;
            }
        }

        System.out.println("""
                ¿Síntoma?:
                    Dolor (0)
                    Lesion traumatica (1)
                    Fiebre alta (2)
                    Confusion o desorientacion (3)""");
        int sintoma = checkEntero("", 0, 3);

        int exploracion = exploracionSintoma(sintoma);

        int nivelPrioridad = checkEntero("¿Nivel de prioridad?: ", 0, 5);

        int temperaturaActual = checkEntero("¿Temperatura actual?: ", 27, 45);

        pacientes[numPacientes][0] = nuss;
        pacientes[numPacientes][1] = sintoma;
        pacientes[numPacientes][2] = exploracion;
        pacientes[numPacientes][3] = nivelPrioridad;
        pacientes[numPacientes][4] = temperaturaActual;

        numPacientes++;

        System.out.println("Paciente añadido correctamente.");
    }

    static void enseñarPacientes() {
        if (numPacientes == 0) {
            System.out.println("Error: No hay pacientes registrados.");
            return;
        }

        System.out.println("Enseñando pacientes...");

        System.out.printf("%-12s %-30s %-40s %-25s %-15s%n",
                "NUSS", "Síntoma", "Exploración", "Nivel de prioridad", "Temperatura actual");

        for (int i = 0; i < numPacientes; i++) {
            System.out.printf("%-12d %-30s %-40s %-25s %-15s%n",
                    pacientes[i][0],
                    nombreSintoma(pacientes[i][1]),
                    nombreExploracion(pacientes[i][1], pacientes[i][2]),
                    pacientes[i][3],
                    pacientes[i][4]);
        }
    }

    static void eliminarPaciente() {
        if (numPacientes == 0) {
            System.out.println("Error: No hay pacientes registrados.");
            return;
        }

        enseñarPacientes();

        int nuss = checkEntero("Indique el NUSS del paciente que quiere eliminar:", 100000, 999999);

        for (int i = 0; i < numPacientes; i++) {
            if (pacientes[i][0] == nuss) {
                for (int j = i; j < numPacientes - 1; j++) {
                    pacientes[j][0] = pacientes[j + 1][0];
                    pacientes[j][1] = pacientes[j + 1][1];
                    pacientes[j][2] = pacientes[j + 1][2];
                    pacientes[j][3] = pacientes[j + 1][3];
                    pacientes[j][4] = pacientes[j + 1][4];
                }
                numPacientes--;
                System.out.println("Paciente eliminado correctamente.");
                return;
            }
            System.out.println("Error: No se ha encontrado un paciente con el NUSS indicado.");
        }
    }

    static void modificarPaciente() {
        if (numPacientes == 0) {
            System.out.println("No hay pacientes registrados.");
            return;
        }

        enseñarPacientes();

        int nuss = checkEntero("Ingrese el NUSS del paciente a modificar: ", 100000, 999999);

        int index = -1;
        for (int i = 0; i < numPacientes; i++) {
            if (pacientes[i][0] == nuss) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Error: No se encontró un paciente con ese NUSS.");
            return;
        }

        System.out.println("""
                ¿Que desea modificar?:
                    Síntoma y exploración (1)
                    Nivel de prioridad (2)
                    Temperatura actual (3)""");
        int opcion = checkEntero("", 1, 3);

        switch (opcion) {
            case 1:
                System.out.println("""
                        ¿Síntoma?:
                            Dolor (0)
                            Lesión traumática (1)
                            Fiebre alta (2)
                            Confusión o desorientación (3)""");
                int sintoma = checkEntero("", 0, 3);
                int exploracion = exploracionSintoma(sintoma);
                pacientes[index][1] = sintoma;
                pacientes[index][2] = exploracion;
                break;
            case 2:
                int nivelPrioridad = checkEntero("Nuevo nivel de prioridad: ", 0, 5);
                pacientes[index][3] = nivelPrioridad;
                break;
            case 3:
                int temperatura = checkEntero("Nueva temperatura: ", 27, 45);
                pacientes[index][4] = temperatura;
                break;
        }

        System.out.println("Paciente modificado correctamente.");
    }






    // Metodos auxiliares
    static int checkEntero(String string, int min, int max) {
        boolean valido = false;
        int valor = 0;

        while (!valido) {
            if (!string.isEmpty()) {
                System.out.println(string);
            }

            if (scanner.hasNextInt()) {
                valor = scanner.nextInt();
                scanner.nextLine();

                if (valor >= min && valor <= max) {
                    valido = true;
                } else {
                    System.out.println("Error: El valor debe ser entre " + min + " y " + max + ".");
                }
            } else {
                System.out.println("Error: El valor debe ser un numero entero.");
                scanner.next();
            }
        }
        return valor;
    }

    static int exploracionSintoma(int sintoma) {
        int exploracion;

        switch (sintoma) {
            case 0:
                System.out.println("""
                        ¿Exploración inicial?:
                            Dolor torácico (0)
                            Dolor abdominal (1)
                            Dolor de cabeza (2)
                            Migraña (3)""");
                break;

            case 1:
                System.out.println("""
                        ¿Exploración?:
                            Fractura ósea (0)
                            Herida de bala (1)
                            Quemadura (2)
                            Lesión cerebral traumática (3)""");
                break;

            case 2:
                System.out.println("""
                        ¿Exploración?:
                            Neumonía (0)
                            Meningitis (1)
                            Infección viral (2)
                            Reacción alérgica (3)""");
                break;

            case 3:
                System.out.println("""
                        ¿Exploración?:
                            Intoxicación por drogas o alcohol (0)
                            Deshidratación severa (1)
                            Accidente cerebrovascular (2)
                            Hipoglucemia severa (3)""");
                break;
        }

        exploracion = checkEntero("", 0, 3);
        return exploracion;
    }

    static String nombreSintoma(int sintoma) {
        switch (sintoma) {
            case 0:
                return "Dolor";
            case 1:
                return "Lesion traumática";
            case 2:
                return "Fiebre alta";
            case 3:
                return "Confusion o desorientación";
        }
        return "";
    }

    static String nombreExploracion(int sintoma, int exploracion) {
        switch (sintoma) {
            case 0:
                switch (exploracion) {
                    case 0:
                        return "Dolor torácico";
                    case 1:
                        return "Dolor abdominal";
                    case 2:
                        return "Dolor de cabeza";
                    case 3:
                        return "Migraña";
                }

            case 1:
                switch (exploracion) {
                    case 0:
                        return "Fractura ósea";
                    case 1:
                        return "Herida de bala";
                    case 2:
                        return "Quemadura";
                    case 3:
                        return "Lesión cerebral traumática";
                }

            case 2:
                switch (exploracion) {
                    case 0:
                        return "Neumonía";
                    case 1:
                        return "Meningitis";
                    case 2:
                        return "Infección viral";
                    case 3:
                        return "Reacción alérgica";
                }

            case 3:
                switch (exploracion) {
                    case 0:
                        return "Intoxicación por drogas o alcohol";
                    case 1:
                        return "Deshidratación severa";
                    case 2:
                        return "Accidente cerebrovascular";
                    case 3:
                        return "Hipoglucemia severa";
                }
        }

        return "";
    }
}
