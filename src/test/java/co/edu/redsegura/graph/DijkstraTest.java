/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.graph;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.redsegura.core.DataFactory;
import co.edu.redsegura.model.Edge;
import co.edu.redsegura.model.Vertex;
import org.junit.jupiter.api.Test;

class DijkstraTest {

    @Test
    void rutaS02aS08CuestaVeinticuatroMinutos() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        DijkstraSolver.Result r = DijkstraSolver.run(g, "S02");
        assertEquals(24, r.distanceTo("S08"));
        assertEquals("S02", r.pathTo("S08").get(0));
        assertEquals("S08", r.pathTo("S08").get(r.pathTo("S08").size() - 1));
    }

    @Test
    void rutaSinBloqueoPasaPorS07() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        DijkstraSolver.Result r = DijkstraSolver.run(g, "S02");
        System.out.println("Camino S02->S08: " + r.pathTo("S08") + " conexiones " + r.edgesTo("S08"));
        assertEquals(java.util.List.of("S02", "S01", "S07", "S08"), r.pathTo("S08"));
    }

    @Test
    void bloquearR16CambiaElCaminoPeroNoElCosto() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        g.blockEdge("R16");
        DijkstraSolver.Result r = DijkstraSolver.run(g, "S02");
        assertEquals(24, r.distanceTo("S08"));
        assertEquals(java.util.List.of("S02", "S01", "S09", "S10", "S08"), r.pathTo("S08"));
    }

    @Test
    void respetaLaOrientacion() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        // S02 solo sale hacia S01 (R04) y S06 (R05); nadie llega a S02 por arcos salvo R04 (no dirigida)
        DijkstraSolver.Result r = DijkstraSolver.run(g, "S08");
        // Desde S08 solo se sale a S10 (R10)
        assertEquals(9, r.distanceTo("S10"));
    }

    @Test
    void verticeInexistenteLanzaExcepcion() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        assertThrows(IllegalArgumentException.class, () -> DijkstraSolver.run(g, "S99"));
        assertThrows(IllegalArgumentException.class, () -> DijkstraSolver.run(g, "S02", "S99"));
    }

    @Test
    void grafoDesconectadoDevuelveInalcanzable() {
        MixedGraph h = new MixedGraph();
        h.addVertex(new Vertex("A", "A", "T"));
        h.addVertex(new Vertex("B", "B", "T"));
        h.addVertex(new Vertex("C", "C", "T"));
        h.addEdge(new Edge("R1", "x", "A", "B", 3, false));
        DijkstraSolver.Result r = DijkstraSolver.run(h, "A");
        assertEquals(3, r.distanceTo("B"));
        assertEquals(-1, r.distanceTo("C"));
        assertFalse(r.isReachable("C"));
        assertTrue(r.pathTo("C").isEmpty());
    }

    @Test
    void origenALaMismaDistanciaCero() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        DijkstraSolver.Result r = DijkstraSolver.run(g, "S02");
        assertEquals(0, r.distanceTo("S02"));
        assertEquals(java.util.List.of("S02"), r.pathTo("S02"));
    }
}