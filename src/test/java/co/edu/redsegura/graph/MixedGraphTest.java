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

class MixedGraphTest {

    @Test
    void elGrafoDeSincelejoTiene12VerticesY18Conexiones() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        assertEquals(12, g.vertexCount());
        assertEquals(18, g.edgeCount());
    }

    @Test
    void vertexDuplicadoLanzaExcepcion() {
        MixedGraph g = new MixedGraph();
        g.addVertex(new Vertex("A", "Lugar A", "Tipo"));
        assertThrows(IllegalArgumentException.class,
                () -> g.addVertex(new Vertex("A", "Otro", "Tipo")));
    }

    @Test
    void edgeConVerticeInexistenteLanzaExcepcion() {
        MixedGraph g = new MixedGraph();
        g.addVertex(new Vertex("A", "Lugar A", "Tipo"));
        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge(new Edge("R1", "x", "A", "Z", 5, false)));
    }

    @Test
    void edgeDuplicadoLanzaExcepcion() {
        MixedGraph g = new MixedGraph();
        g.addVertex(new Vertex("A", "A", "T"));
        g.addVertex(new Vertex("B", "B", "T"));
        g.addEdge(new Edge("R1", "x", "A", "B", 5, false));
        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge(new Edge("R1", "y", "B", "A", 3, false)));
    }

    @Test
    void consultarVerticeInexistenteLanzaExcepcion() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        assertThrows(IllegalArgumentException.class, () -> g.getNeighbors("S99"));
        assertThrows(IllegalArgumentException.class, () -> g.getVertex("S99"));
    }

    @Test
    void aristaNoDirigidaSeVeDesdeAmbosExtremos() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        // R03: S01 -- S09 (no dirigida)
        assertTrue(g.getNeighbors("S01").contains("S09"));
        assertTrue(g.getNeighbors("S09").contains("S01"));
    }

    @Test
    void arcoDirigidoSoloSeVeDesdeElOrigen() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        // R16: S07 -> S08 (dirigida): S08 no puede volver a S07 por esa conexión
        assertTrue(g.getNeighbors("S07").contains("S08"));
        assertFalse(g.getNeighbors("S08").contains("S07"));
    }

    @Test
    void conexionBloqueadaNoApareceComoSalida() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        assertTrue(g.getNeighbors("S07").contains("S08"));
        g.blockEdge("R16");
        assertFalse(g.getNeighbors("S07").contains("S08"));
        g.unblockEdge("R16");
        assertTrue(g.getNeighbors("S07").contains("S08"));
    }

    @Test
    void losVerticesConservanElOrdenDeInsercion() {
        MixedGraph g = DataFactory.createSincelejoGraph();
        assertEquals("S01", g.getVertices().get(0).getId());
        assertEquals("S12", g.getVertices().get(11).getId());
        assertEquals("R01", g.getEdges().get(0).getId());
        assertEquals("R18", g.getEdges().get(17).getId());
    }
}