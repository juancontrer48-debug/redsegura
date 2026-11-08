/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.graph;

import co.edu.redsegura.model.Edge;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Búsqueda por anchura (BFS) con cola y conjunto de visitados.
 * Respeta la orientación de los arcos y omite conexiones bloqueadas.
 */
public final class BreadthFirstSearch {

    /** Resultado del recorrido: orden, nivel de cada vértice y padre (árbol BFS). */
    public static final class Result {
        private final List<String> order = new ArrayList<>();
        private final Map<String, Integer> level = new LinkedHashMap<>();
        private final Map<String, String> parent = new LinkedHashMap<>();

        public List<String> getOrder() { return order; }
        public Map<String, Integer> getLevel() { return level; }
        public Map<String, String> getParent() { return parent; }
    }

    private BreadthFirstSearch() { }

    /**
     * @throws IllegalArgumentException si el vértice de inicio no existe
     */
    public static Result run(MixedGraph g, String startId) {
        if (!g.hasVertex(startId)) {
            throw new IllegalArgumentException("No existe el vértice " + startId + ".");
        }
        Result r = new Result();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        visited.add(startId);
        r.level.put(startId, 0);
        r.parent.put(startId, null);
        queue.add(startId);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            r.order.add(current);
            for (Edge e : g.getOutgoingEdges(current)) {
                String next = e.otherEnd(current);
                if (!visited.contains(next)) {
                    visited.add(next);              // se marca al encolar
                    r.level.put(next, r.level.get(current) + 1);
                    r.parent.put(next, current);
                    queue.add(next);
                }
            }
        }
        return r;
    }
}