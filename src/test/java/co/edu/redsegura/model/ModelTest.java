/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ModelTest {

    @Test
    void vertexValidoConservaSusDatos() {
        Vertex v = new Vertex("S01", "UAJS Sede C", "Universidad");
        assertEquals("S01", v.getId());
        assertEquals("UAJS Sede C", v.getLabel());
        assertEquals("Universidad", v.getType());
    }

    @Test
    void vertexConIdVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Vertex(" ", "X", "Y"));
        assertThrows(IllegalArgumentException.class, () -> new Vertex(null, "X", "Y"));
    }

    @Test
    void edgeValidoConservaOrientacionYPeso() {
        Edge e = new Edge("R01", "Corredor universitario", "S01", "S07", 4, true);
        assertEquals(4, e.getWeight());
        assertTrue(e.isDirected());
        assertFalse(e.isBlocked());
    }

    @Test
    void edgeConPesoInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Edge("R", "x", "A", "B", 0, true));
        assertThrows(IllegalArgumentException.class, () -> new Edge("R", "x", "A", "B", -5, false));
    }

    @Test
    void edgeConExtremoVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Edge("R", "x", "", "B", 3, false));
    }

    @Test
    void otherEndDevuelveElExtremoContrario() {
        Edge e = new Edge("R03", "Corredor peatonal", "S01", "S09", 5, false);
        assertEquals("S09", e.otherEnd("S01"));
        assertEquals("S01", e.otherEnd("S09"));
        assertThrows(IllegalArgumentException.class, () -> e.otherEnd("S05"));
    }

    @Test
    void bloqueoSePuedeActivarYDesactivar() {
        Edge e = new Edge("R16", "Ruta SETP al terminal", "S07", "S08", 12, true);
        e.setBlocked(true);
        assertTrue(e.isBlocked());
        e.setBlocked(false);
        assertFalse(e.isBlocked());
    }
}