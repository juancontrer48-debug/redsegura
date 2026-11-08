/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.graph;

import co.edu.redsegura.model.Edge;
import co.edu.redsegura.model.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Búsqueda en profundidad (DFS) recursiva con conjunto de visitados.
 * Incluye detección de ciclos y componentes débilmente conexas.
 */
public final class DepthFirstSearch {

    private DepthFirstSearch() { }

    /** Orden de visita DFS desde un vértice (respeta orientación y bloqueos). */
    public static List<String> run(MixedGraph g, String startId) {
        if (!g.hasVertex(startId)) {
            throw new IllegalArgumentException("No existe el vértice " + startId + ".");
        }
        List<String> order = new ArrayList<>();
        dfs(g, startId, new HashSet<>(), order);
        return order;
    }

    private static void dfs(MixedGraph g, String current, Set<String> visited, List<String> order) {
        visited.add(current);
        order.add(current);
        for (Edge e : g.getOutgoingEdges(current)) {
            String next = e.otherEnd(current);
            if (!visited.contains(next)) {
                dfs(g, next, visited, order);
            }
        }
    }

    /**
     * Componentes débilmente conexas: se ignora la orientación de los arcos
     * (pero se respetan los bloqueos).
     */
    public static List<List<String>> weakComponents(MixedGraph g) {
        List<List<String>> components = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (Vertex v : g.getVertices()) {
            if (!visited.contains(v.getId())) {
                List<String> component = new ArrayList<>();
                weakDfs(g, v.getId(), visited, component);
                components.add(component);
            }
        }
        return components;
    }

    private static void weakDfs(MixedGraph g, String current, Set<String> visited, List<String> component) {
        visited.add(current);
        component.add(current);
        for (Edge e : g.getEdges()) {
            if (e.isBlocked()) continue;
            String next = null;
            if (e.getSourceId().equals(current)) next = e.getTargetId();
            else if (e.getTargetId().equals(current)) next = e.getSourceId();
            if (next != null && !visited.contains(next)) {
                weakDfs(g, next, visited, component);
            }
        }
    }

    /**
     * Indica si el grafo contiene algún ciclo, considerando la orientación:
     * - Arco dirigido hacia un vértice en la pila de recursión: ciclo.
     * - Arista no dirigida hacia un visitado distinto de la conexión por la que se llegó: ciclo.
     */
    public static boolean hasCycle(MixedGraph g) {
        Set<String> visited = new HashSet<>();
        Set<String> onStack = new HashSet<>();
        for (Vertex v : g.getVertices()) {
            if (!visited.contains(v.getId())
                    && cycleDfs(g, v.getId(), null, visited, onStack)) {
                return true;
            }
        }
        return false;
    }

    private static boolean cycleDfs(MixedGraph g, String current, String viaEdgeId,
                                    Set<String> visited, Set<String> onStack) {
        visited.add(current);
        onStack.add(current);
        for (Edge e : g.getOutgoingEdges(current)) {
            if (e.getId().equals(viaEdgeId)) continue;   // no volver por la misma conexión
            String next = e.otherEnd(current);
            if (!visited.contains(next)) {
                if (cycleDfs(g, next, e.getId(), visited, onStack)) return true;
            } else if (e.isDirected() ? onStack.contains(next) : true) {
                return true;
            }
        }
        onStack.remove(current);
        return false;
    }
}