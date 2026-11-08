/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.graph;

import co.edu.redsegura.model.Edge;
import co.edu.redsegura.model.Vertex;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Construye las tres representaciones del grafo con el mismo orden:
 * vértices en orden de inserción y conexiones en orden de inserción.
 * Muestra la estructura completa, incluidas las conexiones bloqueadas.
 */
public final class GraphRepresentations {

    /** Valor que indica "sin conexión" en la matriz de adyacencia. */
    public static final int NO_EDGE = 0;

    private GraphRepresentations() { }

    /** Lista de adyacencia: para cada vértice, las conexiones que salen de él. */
    public static Map<String, List<Edge>> adjacencyList(MixedGraph g) {
        Map<String, List<Edge>> result = new LinkedHashMap<>();
        for (Vertex v : g.getVertices()) {
            result.put(v.getId(), new ArrayList<>());
        }
        for (Edge e : g.getEdges()) {
            result.get(e.getSourceId()).add(e);
            if (!e.isDirected()) {
                result.get(e.getTargetId()).add(e);
            }
        }
        return result;
    }

    /**
     * Matriz de adyacencia ponderada. m[i][j] = peso de la conexión de i a j,
     * o NO_EDGE (0) si no existe. Si hay varias conexiones entre el mismo par,
     * se conserva la de menor peso.
     */
    public static int[][] adjacencyMatrix(MixedGraph g) {
        List<Vertex> vs = g.getVertices();
        int n = vs.size();
        int[][] m = new int[n][n];
        for (Edge e : g.getEdges()) {
            int i = indexOf(vs, e.getSourceId());
            int j = indexOf(vs, e.getTargetId());
            put(m, i, j, e.getWeight());
            if (!e.isDirected()) {
                put(m, j, i, e.getWeight());
            }
        }
        return m;
    }

    /**
     * Matriz de incidencia (vértices x conexiones).
     * Arco dirigido: -1 en el origen y +1 en el destino.
     * Arista no dirigida: +1 en ambos extremos.
     */
    public static int[][] incidenceMatrix(MixedGraph g) {
        List<Vertex> vs = g.getVertices();
        List<Edge> es = g.getEdges();
        int[][] m = new int[vs.size()][es.size()];
        for (int k = 0; k < es.size(); k++) {
            Edge e = es.get(k);
            int i = indexOf(vs, e.getSourceId());
            int j = indexOf(vs, e.getTargetId());
            if (e.isDirected()) {
                m[i][k] = -1;
                m[j][k] = 1;
            } else {
                m[i][k] = 1;
                m[j][k] = 1;
            }
        }
        return m;
    }

    // ---------- Impresión ----------

    public static String adjacencyListToString(MixedGraph g) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Edge>> entry : adjacencyList(g).entrySet()) {
            sb.append(entry.getKey()).append(" -> ");
            List<String> parts = new ArrayList<>();
            for (Edge e : entry.getValue()) {
                parts.add(e.otherEnd(entry.getKey()) + " (" + e.getId() + ", "
                        + e.getWeight() + (e.isBlocked() ? ", bloqueada" : "") + ")");
            }
            sb.append(parts.isEmpty() ? "-" : String.join(", ", parts)).append("\n");
        }
        return sb.toString();
    }

    public static String adjacencyMatrixToString(MixedGraph g) {
        List<Vertex> vs = g.getVertices();
        int[][] m = adjacencyMatrix(g);
        StringBuilder sb = new StringBuilder("     ");
        for (Vertex v : vs) sb.append(String.format("%4s", v.getId()));
        sb.append("\n");
        for (int i = 0; i < vs.size(); i++) {
            sb.append(String.format("%-5s", vs.get(i).getId()));
            for (int j = 0; j < vs.size(); j++) {
                sb.append(String.format("%4s", m[i][j] == NO_EDGE ? "." : String.valueOf(m[i][j])));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String incidenceMatrixToString(MixedGraph g) {
        List<Vertex> vs = g.getVertices();
        List<Edge> es = g.getEdges();
        int[][] m = incidenceMatrix(g);
        StringBuilder sb = new StringBuilder("     ");
        for (Edge e : es) sb.append(String.format("%4s", e.getId()));
        sb.append("\n");
        for (int i = 0; i < vs.size(); i++) {
            sb.append(String.format("%-5s", vs.get(i).getId()));
            for (int k = 0; k < es.size(); k++) {
                sb.append(String.format("%4d", m[i][k]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // ---------- Auxiliares ----------

    private static int indexOf(List<Vertex> vs, String id) {
        for (int i = 0; i < vs.size(); i++) {
            if (vs.get(i).getId().equals(id)) return i;
        }
        throw new IllegalArgumentException("No existe el vértice " + id + ".");
    }

    private static void put(int[][] m, int i, int j, int weight) {
        if (m[i][j] == NO_EDGE || weight < m[i][j]) {
            m[i][j] = weight;
        }
    }
}