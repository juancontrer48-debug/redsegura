/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.graph;

import co.edu.redsegura.model.Edge;
import co.edu.redsegura.model.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Grafo mixto: admite aristas no dirigidas y arcos dirigidos, todos con
 * etiqueta y peso. Conserva el orden de inserción de vértices y conexiones.
 */
public class MixedGraph {

    private final Map<String, Vertex> vertices = new LinkedHashMap<>();
    private final Map<String, Edge> edges = new LinkedHashMap<>();
    private final Map<String, List<Edge>> adjacency = new LinkedHashMap<>();

    /**
     * Agrega un vértice.
     * @throws IllegalArgumentException si es nulo o su ID ya existe
     */
    public void addVertex(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("El vértice no puede ser nulo.");
        }
        if (vertices.containsKey(vertex.getId())) {
            throw new IllegalArgumentException("Ya existe un vértice con ID " + vertex.getId() + ".");
        }
        vertices.put(vertex.getId(), vertex);
        adjacency.put(vertex.getId(), new ArrayList<>());
    }

    /**
     * Agrega una conexión entre dos vértices ya existentes.
     * @throws IllegalArgumentException si es nula, su ID ya existe o algún extremo no existe
     */
    public void addEdge(Edge edge) {
        if (edge == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula.");
        }
        if (edges.containsKey(edge.getId())) {
            throw new IllegalArgumentException("Ya existe una conexión con ID " + edge.getId() + ".");
        }
        requireVertex(edge.getSourceId());
        requireVertex(edge.getTargetId());

        edges.put(edge.getId(), edge);
        adjacency.get(edge.getSourceId()).add(edge);
        if (!edge.isDirected()) {
            adjacency.get(edge.getTargetId()).add(edge);
        }
    }

    /** Devuelve el vértice con ese ID o lanza excepción si no existe. */
    public Vertex getVertex(String id) {
        requireVertex(id);
        return vertices.get(id);
    }

    public boolean hasVertex(String id) {
        return id != null && vertices.containsKey(id);
    }

    /** Devuelve la conexión con ese ID o lanza excepción si no existe. */
    public Edge getEdge(String id) {
        Edge e = edges.get(id);
        if (e == null) {
            throw new IllegalArgumentException("No existe la conexión " + id + ".");
        }
        return e;
    }

    /** Vértices en orden de inserción (vista de solo lectura). */
    public List<Vertex> getVertices() {
        return Collections.unmodifiableList(new ArrayList<>(vertices.values()));
    }

    /** Conexiones en orden de inserción (vista de solo lectura). */
    public List<Edge> getEdges() {
        return Collections.unmodifiableList(new ArrayList<>(edges.values()));
    }

    /**
     * Conexiones que se pueden tomar saliendo de un vértice (ignora las bloqueadas).
     * @throws IllegalArgumentException si el vértice no existe
     */
    public List<Edge> getOutgoingEdges(String vertexId) {
        requireVertex(vertexId);
        List<Edge> result = new ArrayList<>();
        for (Edge e : adjacency.get(vertexId)) {
            if (!e.isBlocked()) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * IDs de los vecinos alcanzables desde un vértice, respetando orientación
     * y sin contar conexiones bloqueadas.
     */
    public List<String> getNeighbors(String vertexId) {
        List<String> result = new ArrayList<>();
        for (Edge e : getOutgoingEdges(vertexId)) {
            result.add(e.otherEnd(vertexId));
        }
        return result;
    }

    /** Bloquea una conexión (la consulta Q6 bloquea R16). */
    public void blockEdge(String edgeId) {
        getEdge(edgeId).setBlocked(true);
    }

    /** Desbloquea una conexión. */
    public void unblockEdge(String edgeId) {
        getEdge(edgeId).setBlocked(false);
    }

    public int vertexCount() { return vertices.size(); }

    public int edgeCount() { return edges.size(); }

    private void requireVertex(String id) {
        if (id == null || !vertices.containsKey(id)) {
            throw new IllegalArgumentException("No existe el vértice " + id + ".");
        }
    }
}