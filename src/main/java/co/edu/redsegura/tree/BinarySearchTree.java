/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Árbol binario de búsqueda (ABB) sin balanceo.
 * Ignora claves duplicadas. Altura: árbol vacío = -1, un nodo = 0.
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private static final class Node<T> {
        T key;
        Node<T> left;
        Node<T> right;
        Node(T key) { this.key = key; }
    }

    private Node<T> root;
    private int size;
    private int lastSearchSteps;

    /** Inserta una clave. Devuelve false si ya existía. */
    public boolean insert(T key) {
        if (key == null) {
            throw new IllegalArgumentException("La clave no puede ser nula.");
        }
        if (root == null) {
            root = new Node<>(key);
            size++;
            return true;
        }
        Node<T> current = root;
        while (true) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) return false;
            if (cmp < 0) {
                if (current.left == null) { current.left = new Node<>(key); size++; return true; }
                current = current.left;
            } else {
                if (current.right == null) { current.right = new Node<>(key); size++; return true; }
                current = current.right;
            }
        }
    }

    /** Busca una clave y guarda en lastSearchSteps cuántos nodos visitó. */
    public boolean search(T key) {
        lastSearchSteps = 0;
        Node<T> current = root;
        while (current != null) {
            lastSearchSteps++;
            int cmp = key.compareTo(current.key);
            if (cmp == 0) return true;
            current = (cmp < 0) ? current.left : current.right;
        }
        return false;
    }

    /** Elimina una clave. Devuelve true si existía. */
    public boolean delete(T key) {
        int before = size;
        root = delete(root, key);
        return size < before;
    }

    private Node<T> delete(Node<T> node, T key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            size--;
            if (node.left == null) return node.right;     // hoja o solo hijo derecho
            if (node.right == null) return node.left;     // solo hijo izquierdo
            Node<T> successor = node.right;               // dos hijos: sucesor inorden
            while (successor.left != null) successor = successor.left;
            node.key = successor.key;
            size++;                                       // se compensa: la llamada siguiente resta 1
            node.right = delete(node.right, successor.key);
        }
        return node;
    }

    public List<T> inOrder() {
        List<T> out = new ArrayList<>();
        inOrder(root, out);
        return out;
    }

    private void inOrder(Node<T> n, List<T> out) {
        if (n == null) return;
        inOrder(n.left, out);
        out.add(n.key);
        inOrder(n.right, out);
    }

    public List<T> preOrder() {
        List<T> out = new ArrayList<>();
        preOrder(root, out);
        return out;
    }

    private void preOrder(Node<T> n, List<T> out) {
        if (n == null) return;
        out.add(n.key);
        preOrder(n.left, out);
        preOrder(n.right, out);
    }

    public List<T> postOrder() {
        List<T> out = new ArrayList<>();
        postOrder(root, out);
        return out;
    }

    private void postOrder(Node<T> n, List<T> out) {
        if (n == null) return;
        postOrder(n.left, out);
        postOrder(n.right, out);
        out.add(n.key);
    }

    /** Altura del árbol: vacío = -1, un solo nodo = 0. */
    public int height() { return height(root); }

    private int height(Node<T> n) {
        if (n == null) return -1;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    /** Cantidad de nodos visitados en la última llamada a search. */
    public int getLastSearchSteps() { return lastSearchSteps; }

    /** Clave de la raíz, o null si está vacío (útil para pruebas y demostraciones). */
    public T rootKey() { return root == null ? null : root.key; }
}