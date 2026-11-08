/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.tree;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class AvlTreeTest {

    private AvlTree<Integer> build(int... keys) {
        AvlTree<Integer> t = new AvlTree<>();
        for (int k : keys) t.insert(k);
        return t;
    }

    @Test
    void rotacionLL() {
        AvlTree<Integer> t = build(30, 20, 10);
        assertEquals(20, t.rootKey());
        assertEquals(List.of("LL en 30"), t.getRotationLog());
        assertTrue(t.isBalanced());
    }

    @Test
    void rotacionRR() {
        AvlTree<Integer> t = build(10, 20, 30);
        assertEquals(20, t.rootKey());
        assertEquals(List.of("RR en 10"), t.getRotationLog());
    }

    @Test
    void rotacionLR() {
        AvlTree<Integer> t = build(30, 10, 20);
        assertEquals(20, t.rootKey());
        assertEquals(List.of("LR en 30"), t.getRotationLog());
    }

    @Test
    void rotacionRL() {
        AvlTree<Integer> t = build(10, 30, 20);
        assertEquals(20, t.rootKey());
        assertEquals(List.of("RL en 10"), t.getRotationLog());
    }

    @Test
    void insertarEnOrdenMantieneAlturaLogaritmica() {
        AvlTree<Integer> t = new AvlTree<>();
        for (int i = 1; i <= 1000; i++) t.insert(i);
        assertTrue(t.isBalanced());
        assertTrue(t.height() <= 14);   // 1.44 * log2(1000) ~ 14
        assertEquals(1000, t.size());
    }

    @Test
    void recorridoInOrderEstaOrdenado() {
        AvlTree<Integer> t = build(50, 20, 80, 10, 30, 70, 90, 25);
        assertEquals(List.of(10, 20, 25, 30, 50, 70, 80, 90), t.inOrder());
    }

    @Test
    void busquedaCuentaPasosYEncuentra() {
        AvlTree<Integer> t = build(1, 2, 3, 4, 5, 6, 7);
        assertTrue(t.search(7));
        assertTrue(t.getLastSearchSteps() <= 3);
        assertFalse(t.search(99));
    }

    @Test
    void duplicadoSeIgnora() {
        AvlTree<Integer> t = build(10, 20, 30);
        assertFalse(t.insert(20));
        assertEquals(3, t.size());
    }

    @Test
    void eliminarMantieneBalanceYOrden() {
        AvlTree<Integer> t = new AvlTree<>();
        for (int i = 1; i <= 15; i++) t.insert(i);
        assertTrue(t.delete(1));
        assertTrue(t.delete(2));
        assertTrue(t.delete(3));
        assertTrue(t.isBalanced());
        assertEquals(12, t.size());
        assertEquals(List.of(4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), t.inOrder());
    }

    @Test
    void eliminarDesbalanceaYRota() {
        AvlTree<Integer> t = build(20, 10, 30, 25, 40);
        t.clearRotationLog();
        assertTrue(t.delete(10));   // el subárbol derecho queda más alto
        assertFalse(t.getRotationLog().isEmpty());
        assertTrue(t.isBalanced());
    }

    @Test
    void eliminarInexistenteDevuelveFalse() {
        AvlTree<Integer> t = build(1, 2, 3);
        assertFalse(t.delete(99));
        assertEquals(3, t.size());
    }

    @Test
    void avlEsMasRapidoQueAbbConDatosOrdenados() {
        BinarySearchTree<Integer> abb = new BinarySearchTree<>();
        AvlTree<Integer> avl = new AvlTree<>();
        for (int i = 1; i <= 100; i++) { abb.insert(i); avl.insert(i); }
        abb.search(100);
        avl.search(100);
        assertEquals(100, abb.getLastSearchSteps());
        assertTrue(avl.getLastSearchSteps() <= 8);
    }
}