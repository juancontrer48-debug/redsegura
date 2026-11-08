/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RedSeguraCoreTest {

    @Test
    void controlS02aS08CuestaVeinticuatro() {
        assertEquals(24, new RedSeguraCore().shortestCost("S02", "S08"));
    }

    @Test
    void bloquearR16MantieneCostoYCambiaCamino() {
        RedSeguraCore core = new RedSeguraCore();
        String antes = core.shortestRoute("S02", "S08");
        core.blockEdge("R16");
        String despues = core.shortestRoute("S02", "S08");
        assertEquals(24, core.shortestCost("S02", "S08"));
        assertNotEquals(antes, despues);
        assertTrue(despues.contains("S09"));
    }

    @Test
    void consultarVerticeInexistenteLanzaExcepcionControlada() {
        RedSeguraCore core = new RedSeguraCore();
        assertThrows(IllegalArgumentException.class, () -> core.describeVertex("S99"));
        assertThrows(IllegalArgumentException.class, () -> core.bfs("S99"));
        assertThrows(IllegalArgumentException.class, () -> core.shortestRoute("S02", "S99"));
    }

    @Test
    void avlUsaMenosPasosQueAbbConEtiquetasOrdenadas() {
        RedSeguraCore core = new RedSeguraCore();
        int[] r = core.compareSearch("UAJS Sede E");
        assertEquals(1, r[2]);
        assertTrue(r[1] <= r[0]);
        assertTrue(core.avlHeight() < core.abbHeight());
    }

    @Test
    void elSistemaTiene12VerticesY18Conexiones() {
        RedSeguraCore core = new RedSeguraCore();
        assertEquals(12, core.vertexCount());
        assertEquals(18, core.edgeCount());
    }
}