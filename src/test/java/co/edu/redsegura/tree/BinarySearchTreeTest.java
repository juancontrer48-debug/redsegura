/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.tree;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {

    private BinarySearchTree<Integer> sample() {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        for (int k : new int[]{50, 30, 70, 20, 40, 60, 80}) t.insert(k);
        return t;
    }

    @Test
    void inOrderDevuelveClavesOrdenadas() {
        assertEquals(List.of(20, 30, 40, 50, 60, 70, 80), sample().inOrder());
    }

    @Test
    void preOrderYPostOrder() {
        BinarySearchTree<Integer> t = sample();
        assertEquals(List.of(50, 30, 20, 40, 70, 60, 80), t.preOrder());
        assertEquals(List.of(20, 40, 30, 60, 80, 70, 50), t.postOrder());
    }

    @Test
    void alturaDeArbolCompletoEsDos() {
        assertEquals(2, sample().height());
        assertEquals(-1, new BinarySearchTree<Integer>().height());
    }

    @Test
    void busquedaEncuentraYCuentaPasos() {
        BinarySearchTree<Integer> t = sample();
        assertTrue(t.search(60));
        assertEquals(3, t.getLastSearchSteps());   // 50 -> 70 -> 60
        assertFalse(t.search(99));
    }

    @Test
    void insertarDuplicadoSeIgnora() {
        BinarySearchTree<Integer> t = sample();
        assertFalse(t.insert(50));
        assertEquals(7, t.size());
    }

    @Test
    void eliminarHoja() {
        BinarySearchTree<Integer> t = sample();
        assertTrue(t.delete(20));
        assertEquals(List.of(30, 40, 50, 60, 70, 80), t.inOrder());
        assertEquals(6, t.size());
    }

    @Test
    void eliminarNodoConUnHijo() {
        BinarySearchTree<Integer> t = sample();
        t.delete(20);
        assertTrue(t.delete(30));   // ahora solo tiene hijo derecho (40)
        assertEquals(List.of(40, 50, 60, 70, 80), t.inOrder());
    }

    @Test
    void eliminarNodoConDosHijosYRaiz() {
        BinarySearchTree<Integer> t = sample();
        assertTrue(t.delete(50));   // raíz con dos hijos: la reemplaza el sucesor (60)
        assertEquals(60, t.rootKey());
        assertEquals(List.of(20, 30, 40, 60, 70, 80), t.inOrder());
        assertEquals(6, t.size());
    }

    @Test
    void eliminarClaveInexistenteDevuelveFalse() {
        BinarySearchTree<Integer> t = sample();
        assertFalse(t.delete(99));
        assertEquals(7, t.size());
    }

    @Test
    void insertarEnOrdenDegeneraEnLista() {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        for (int i = 1; i <= 10; i++) t.insert(i);
        assertEquals(9, t.height());   // peor caso: O(n)
    }

    @Test
    void claveNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new BinarySearchTree<Integer>().insert(null));
    }
}