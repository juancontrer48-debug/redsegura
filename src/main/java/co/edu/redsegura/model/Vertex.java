/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.model;

import java.util.Objects;

public final class Vertex {

    private final String id;
    private final String label;
    private final String type;

    public Vertex(String id, String label, String type) {
        this.id = requireText(id, "El ID del vértice");
        this.label = requireText(label, "La etiqueta del vértice");
        this.type = requireText(type, "El tipo del vértice");
    }

    public String getId() { return id; }
    public String getLabel() { return label; }
    public String getType() { return type; }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " no puede ser nulo ni vacío.");
        }
        return value.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() { return id + " (" + label + ")"; }
}
