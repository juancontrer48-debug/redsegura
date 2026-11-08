/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.model;

import java.util.Objects;

public final class Edge {

    private final String id;
    private final String label;
    private final String sourceId;
    private final String targetId;
    private final int weight;
    private final boolean directed;
    private boolean blocked;

    public Edge(String id, String label, String sourceId, String targetId,
                int weight, boolean directed) {
        this.id = requireText(id, "El ID de la conexión");
        this.label = requireText(label, "La etiqueta de la conexión");
        this.sourceId = requireText(sourceId, "El origen de la conexión");
        this.targetId = requireText(targetId, "El destino de la conexión");
        if (weight <= 0) {
            throw new IllegalArgumentException(
                "El peso de la conexión " + this.id + " debe ser mayor que 0 (recibido: " + weight + ").");
        }
        this.weight = weight;
        this.directed = directed;
        this.blocked = false;
    }

    public String getId() { return id; }
    public String getLabel() { return label; }
    public String getSourceId() { return sourceId; }
    public String getTargetId() { return targetId; }
    public int getWeight() { return weight; }
    public boolean isDirected() { return directed; }
    public boolean isBlocked() { return blocked; }

    public void setBlocked(boolean blocked) { this.blocked = blocked; }

    /** Dado un extremo, devuelve el otro extremo de la conexión. */
    public String otherEnd(String vertexId) {
        if (vertexId.equals(sourceId)) return targetId;
        if (vertexId.equals(targetId)) return sourceId;
        throw new IllegalArgumentException(
            "El vértice " + vertexId + " no pertenece a la conexión " + id + ".");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " no puede ser nulo ni vacío.");
        }
        return value.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return id + " [" + sourceId + (directed ? " -> " : " -- ") + targetId
             + ", " + weight + " min" + (blocked ? ", BLOQUEADA" : "") + "]";
    }
}