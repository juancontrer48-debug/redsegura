/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.app;

import co.edu.redsegura.core.RedSeguraCore;

import java.util.Scanner;

/** Aplicación de consola de RedSegura. Solo se comunica con RedSeguraCore. */
public class RedSeguraApplication {

    public static void main(String[] args) {
        RedSeguraCore core = new RedSeguraCore();
        Scanner in = new Scanner(System.in);
        System.out.println("=== RedSegura Sincelejo ===");
        System.out.println("(Tiempos didácticos de simulación, no oficiales.)");

        boolean running = true;
        while (running) {
            printMenu();
            String option = in.nextLine().trim();
            try {
                switch (option) {
                    case "1" -> q1(core, in);
                    case "2" -> q2(core);
                    case "3" -> q3(core);
                    case "4" -> q4(core, in);
                    case "5" -> q5(core);
                    case "6" -> q6(core);
                    case "7" -> q7(core, in);
                    case "0" -> running = false;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error controlado: " + ex.getMessage());
            }
        }
        System.out.println("Hasta luego.");
    }

    private static void printMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Q1: Buscar lugar en ABB y AVL");
        System.out.println("2. Q2: Rotaciones AVL (LL, RR, LR, RL)");
        System.out.println("3. Q3: Lista y matrices del grafo");
        System.out.println("4. Q4: BFS y DFS desde un vértice");
        System.out.println("5. Q5: Ruta mínima S02 -> S08");
        System.out.println("6. Q6: Bloquear R16 y recalcular");
        System.out.println("7. Q7: Consultar un vértice");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private static void q1(RedSeguraCore core, Scanner in) {
        System.out.print("Etiqueta a buscar [Hospital Universitario]: ");
        String label = in.nextLine().trim();
        if (label.isEmpty()) label = "Hospital Universitario";
        int[] r = core.compareSearch(label);
        System.out.println(r[2] == 1 ? "Encontrado." : "No encontrado.");
        System.out.println("Pasos en ABB: " + r[0] + " (altura " + core.abbHeight() + ")");
        System.out.println("Pasos en AVL: " + r[1] + " (altura " + core.avlHeight() + ")");
    }

    private static void q2(RedSeguraCore core) {
        System.out.println(core.demoRotation("LL", 30, 20, 10));
        System.out.println(core.demoRotation("RR", 10, 20, 30));
        System.out.println(core.demoRotation("LR", 30, 10, 20));
        System.out.println(core.demoRotation("RL", 10, 30, 20));
    }

    private static void q3(RedSeguraCore core) {
        System.out.println("Lista de adyacencia:\n" + core.adjacencyList());
        System.out.println("Matriz de adyacencia ('.' = sin conexión):\n" + core.adjacencyMatrix());
        System.out.println("Matriz de incidencia (arco: -1 origen/+1 destino; arista: +1/+1):\n"
                + core.incidenceMatrix());
    }

    private static void q4(RedSeguraCore core, Scanner in) {
        System.out.print("Vértice de inicio [S02]: ");
        String start = in.nextLine().trim();
        if (start.isEmpty()) start = "S02";
        System.out.println("BFS: " + core.bfs(start));
        System.out.println("DFS: " + core.dfs(start));
        System.out.println("BFS explora por niveles con una cola; DFS se va por una rama hasta el fondo.");
        System.out.println("Por eso el orden puede diferir (y también depende del orden de los vecinos).");
    }

    private static void q5(RedSeguraCore core) {
        System.out.println(core.shortestRoute("S02", "S08"));
    }

    private static void q6(RedSeguraCore core) {
        System.out.println("Antes del bloqueo:\n" + core.shortestRoute("S02", "S08"));
        core.blockEdge("R16");
        System.out.println("R16 bloqueada. Después del bloqueo:\n" + core.shortestRoute("S02", "S08"));
        System.out.println("El costo se mantiene porque existe otra ruta del mismo costo (24 min)");
        System.out.println("por S09 y S10; lo que cambia es el camino.");
        core.unblockEdge("R16");
        System.out.println("(R16 se desbloqueó para continuar.)");
    }

    private static void q7(RedSeguraCore core, Scanner in) {
        System.out.print("ID del vértice [S99]: ");
        String id = in.nextLine().trim();
        if (id.isEmpty()) id = "S99";
        System.out.println(core.describeVertex(id));   // lanza excepción si no existe: se atrapa en main
    }
}