/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.graph;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.redsegura.core.DataFactory;
import co.edu.redsegura.model.Edge;
import co.edu.redsegura.model.Vertex;
import java.util.List;
import org.junit.jupiter.api.Test;

class TraversalTest {

    private final MixedGraph g = DataFactory.createSincelejoGraph();

    @Test
    void bfsDesdeS02EmpiezaEnS02YAlcanzaTodosLosVertices() {
        BreadthFirstSearch.Result r = BreadthFirstSearch.run(g, "S02");
        assertEquals("S02", r.getOrder().get(0));
        assertEquals(12, r.getOrder().size());
        assertEquals(0, r.getLevel().get("S02"));
        assertNull(r.getParent().get("S02"));
    }

    @Test
    void bfsVisitaPorNiveles() {
        BreadthFirstSearch.Result r = BreadthFirstSearch.run(g, "S02");
        // S01 y S06 son vecinos directos de S02 (nivel 1)
        assertEquals(1, r.getLevel().get("S01"));
        assertEquals(1, r.getLevel().get("S06"));
    }

    @Test
    void bfsRespetaLaOrientacion() {
        BreadthFirstSearch.Result r = BreadthFirstSearch.run(g, "S08");
        assertEquals(1, r.getLevel().get("S10"));
        assertTrue(r.getLevel().get("S07") > 1);
    }

    @Test
    void dfsDesdeS02AlcanzaTodosLosVertices() {
        List<String> order = DepthFirstSearch.run(g, "S02");
        assertEquals("S02", order.get(0));
        assertEquals(12, order.size());
        assertEquals(12, order.stream().distinct().count());
    }

    @Test
    void verticeInexistenteLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> BreadthFirstSearch.run(g, "S99"));
        assertThrows(IllegalArgumentException.class, () -> DepthFirstSearch.run(g, "S99"));
    }

    @Test
    void grafoConectadoTieneUnaSolaComponenteDebil() {
        assertEquals(1, DepthFirstSearch.weakComponents(g).size());
    }

    @Test
    void grafoDesconectadoTieneDosComponentes() {
        MixedGraph h = new MixedGraph();
        h.addVertex(new Vertex("A", "A", "T"));
        h.addVertex(new Vertex("B", "B", "T"));
        h.addVertex(new Vertex("C", "C", "T"));
        h.addEdge(new Edge("R1", "x", "A", "B", 3, false));
        assertEquals(2, DepthFirstSearch.weakComponents(h).size());
        // BFS desde A no llega a C
        assertEquals(2, BreadthFirstSearch.run(h, "A").getOrder().size());
    }

    @Test
    void detectaCicloEnElGrafoDeSincelejo() {
        // R01 (S01->S07) y R02 (S07->S01) forman un ciclo dirigido
        assertTrue(DepthFirstSearch.hasCycle(g));
    }

    @Test
    void grafoDirigidoSinCicloNoLoDetecta() {
        MixedGraph h = new MixedGraph();
        h.addVertex(new Vertex("A", "A", "T"));
        h.addVertex(new Vertex("B", "B", "T"));
        h.addVertex(new Vertex("C", "C", "T"));
        h.addEdge(new Edge("R1", "x", "A", "B", 1, true));
        h.addEdge(new Edge("R2", "y", "B", "C", 1, true));
        h.addEdge(new Edge("R3", "z", "A", "C", 1, true));
        assertFalse(DepthFirstSearch.hasCycle(h));
    }
}
