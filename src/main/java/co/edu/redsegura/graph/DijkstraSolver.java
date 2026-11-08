/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.graph;

import co.edu.redsegura.model.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Ruta mínima con Dijkstra usando PriorityQueue, relajación y mapa de predecesores.
 * Respeta la orientación y omite conexiones bloqueadas.
 * Desempate: la relajación usa "<" estricto, así que ante dos caminos de igual
 * costo se conserva el primero que se encontró.
 */
public final class DijkstraSolver {

    /** Resultado: distancias y predecesores desde el origen. */
    public static final class Result {
        private final String source;
        private final Map<String, Integer> distance = new LinkedHashMap<>();
        private final Map<String, String> previous = new HashMap<>();
        private final Map<String, String> previousEdge = new HashMap<>();

        private Result(String source) { this.source = source; }

        public String getSource() { return source; }

        /** Distancia mínima al vértice, o -1 si es inalcanzable. */
        public int distanceTo(String id) {
            Integer d = distance.get(id);
            return (d == null || d == INF) ? -1 : d;
        }

        public boolean isReachable(String id) { return distanceTo(id) >= 0; }

        public Map<String, Integer> getDistances() { return distance; }

        /** Camino desde el origen hasta el destino; lista vacía si no hay camino. */
        public List<String> pathTo(String targetId) {
            if (!isReachable(targetId)) return new ArrayList<>();
            List<String> path = new ArrayList<>();
            for (String at = targetId; at != null; at = previous.get(at)) {
                path.add(at);
            }
            Collections.reverse(path);
            return path;
        }

        /** IDs de las conexiones usadas en el camino, en orden. */
        public List<String> edgesTo(String targetId) {
            List<String> ids = new ArrayList<>();
            if (!isReachable(targetId)) return ids;
            for (String at = targetId; previous.get(at) != null; at = previous.get(at)) {
                ids.add(previousEdge.get(at));
            }
            Collections.reverse(ids);
            return ids;
        }
    }

    private static final int INF = Integer.MAX_VALUE;

    private record Node(String id, int dist) { }

    private DijkstraSolver() { }

    /**
     * @throws IllegalArgumentException si el origen no existe
     */
    public static Result run(MixedGraph g, String sourceId) {
        if (!g.hasVertex(sourceId)) {
            throw new IllegalArgumentException("No existe el vértice " + sourceId + ".");
        }
        Result r = new Result(sourceId);
        for (var v : g.getVertices()) {
            r.distance.put(v.getId(), INF);
        }
        r.distance.put(sourceId, 0);
        r.previous.put(sourceId, null);

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.dist(), b.dist()));
        pq.add(new Node(sourceId, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (current.dist() > r.distance.get(current.id())) continue; // entrada obsoleta

            for (Edge e : g.getOutgoingEdges(current.id())) {
                String next = e.otherEnd(current.id());
                int candidate = current.dist() + e.getWeight();
                if (candidate < r.distance.get(next)) {      // relajación
                    r.distance.put(next, candidate);
                    r.previous.put(next, current.id());
                    r.previousEdge.put(next, e.getId());
                    pq.add(new Node(next, candidate));
                }
            }
        }
        return r;
    }

    /** Atajo: resultado para un origen y destino, validando ambos. */
    public static Result run(MixedGraph g, String sourceId, String targetId) {
        if (!g.hasVertex(targetId)) {
            throw new IllegalArgumentException("No existe el vértice " + targetId + ".");
        }
        return run(g, sourceId);
    }
}