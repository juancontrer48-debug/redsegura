/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.graph;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.redsegura.core.DataFactory;
import org.junit.jupiter.api.Test;

class GraphRepresentationsTest {

    private final MixedGraph g = DataFactory.createSincelejoGraph();

    @Test
    void matricesTienenLasDimensionesCorrectas() {
        assertEquals(12, GraphRepresentations.adjacencyMatrix(g).length);
        assertEquals(12, GraphRepresentations.adjacencyMatrix(g)[0].length);
        assertEquals(12, GraphRepresentations.incidenceMatrix(g).length);
        assertEquals(18, GraphRepresentations.incidenceMatrix(g)[0].length);
    }

    @Test
    void arcoDirigidoLlenaUnaSolaCeldaEnAdyacencia() {
        int[][] m = GraphRepresentations.adjacencyMatrix(g);
        // R16: S07 -> S08 (12). S07 = índice 6, S08 = índice 7
        assertEquals(12, m[6][7]);
        assertEquals(GraphRepresentations.NO_EDGE, m[7][6]);
    }

    @Test
    void aristaNoDirigidaLlenaDosCeldasEnAdyacencia() {
        int[][] m = GraphRepresentations.adjacencyMatrix(g);
        // R03: S01 -- S09 (5). S01 = 0, S09 = 8
        assertEquals(5, m[0][8]);
        assertEquals(5, m[8][0]);
    }

    @Test
    void incidenciaDeArcoUsaMenosUnoYMasUno() {
        int[][] m = GraphRepresentations.incidenceMatrix(g);
        // R01 (columna 0): S01 -> S07
        assertEquals(-1, m[0][0]);
        assertEquals(1, m[6][0]);
    }

    @Test
    void incidenciaDeAristaUsaMasUnoEnAmbosExtremos() {
        int[][] m = GraphRepresentations.incidenceMatrix(g);
        // R03 (columna 2): S01 -- S09
        assertEquals(1, m[0][2]);
        assertEquals(1, m[8][2]);
    }

    @Test
    void listaDeAdyacenciaCoincideConLaMatriz() {
        var list = GraphRepresentations.adjacencyList(g);
        int[][] m = GraphRepresentations.adjacencyMatrix(g);
        var vs = g.getVertices();
        for (int i = 0; i < vs.size(); i++) {
            int count = 0;
            for (int j = 0; j < vs.size(); j++) {
                if (m[i][j] != GraphRepresentations.NO_EDGE) count++;
            }
            assertEquals(count, list.get(vs.get(i).getId()).size(),
                    "Grado de salida distinto en " + vs.get(i).getId());
        }
    }
}