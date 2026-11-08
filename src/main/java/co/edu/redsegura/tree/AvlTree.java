/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Árbol AVL: ABB autobalanceado con rotaciones LL, RR, LR y RL.
 * Altura: árbol vacío = -1, un nodo = 0. Ignora claves duplicadas.
 */
public class AvlTree<T extends Comparable<T>> {

    private static final class Node<T> {
        T key;
        Node<T> left;
        Node<T> right;
        int height;   // altura del nodo (hoja = 0)
        Node(T key) { this.key = key; }
    }

    private Node<T> root;
    private int size;
    private int lastSearchSteps;
    private final List<String> rotationLog = new ArrayList<>();

    // ---------- Altura y balance ----------

    private int height(Node<T> n) { return n == null ? -1 : n.height; }

    private void updateHeight(Node<T> n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    /** Factor de balance = altura(izq) - altura(der). */
    private int balance(Node<T> n) { return n == null ? 0 : height(n.left) - height(n.right); }

    // ---------- Rotaciones ----------

    /** Rotación simple a la derecha (caso LL). */
    private Node<T> rotateRight(Node<T> y) {
        Node<T> x = y.left;
        y.left = x.right;
        x.right = y;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    /** Rotación simple a la izquierda (caso RR). */
    private Node<T> rotateLeft(Node<T> x) {
        Node<T> y = x.right;
        x.right = y.left;
        y.left = x;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    /** Actualiza la altura y rebalancea el nodo si es necesario. */
    private Node<T> rebalance(Node<T> n) {
        updateHeight(n);
        int b = balance(n);

        if (b > 1) {
            if (balance(n.left) >= 0) {                  // LL
                rotationLog.add("LL en " + n.key);
                return rotateRight(n);
            } else {                                     // LR
                rotationLog.add("LR en " + n.key);
                n.left = rotateLeft(n.left);
                return rotateRight(n);
            }
        }
        if (b < -1) {
            if (balance(n.right) <= 0) {                 // RR
                rotationLog.add("RR en " + n.key);
                return rotateLeft(n);
            } else {                                     // RL
                rotationLog.add("RL en " + n.key);
                n.right = rotateRight(n.right);
                return rotateLeft(n);
            }
        }
        return n;
    }

    // ---------- Inserción ----------

    /** Inserta una clave. Devuelve false si ya existía. */
    public boolean insert(T key) {
        if (key == null) {
            throw new IllegalArgumentException("La clave no puede ser nula.");
        }
        int before = size;
        root = insert(root, key);
        return size > before;
    }

    private Node<T> insert(Node<T> node, T key) {
        if (node == null) {
            size++;
            return new Node<>(key);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = insert(node.left, key);
        else if (cmp > 0) node.right = insert(node.right, key);
        else return node;                                // duplicado
        return rebalance(node);
    }

    // ---------- Búsqueda ----------

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

    // ---------- Eliminación ----------

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
            if (node.left == null || node.right == null) {
                size--;
                Node<T> child = (node.left != null) ? node.left : node.right;
                return child;                            // null si era hoja
            }
            Node<T> successor = node.right;              // dos hijos: sucesor inorden
            while (successor.left != null) successor = successor.left;
            node.key = successor.key;
            node.right = delete(node.right, successor.key);
        }
        return rebalance(node);
    }

    // ---------- Recorridos ----------

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

    // ---------- Consultas ----------

    public int height() { return height(root); }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    public int getLastSearchSteps() { return lastSearchSteps; }

    public T rootKey() { return root == null ? null : root.key; }

    /** Rotaciones aplicadas hasta ahora, en orden (para la demostración Q2). */
    public List<String> getRotationLog() { return new ArrayList<>(rotationLog); }

    public void clearRotationLog() { rotationLog.clear(); }

    /** Verifica que todos los nodos cumplan |balance| <= 1 (útil en pruebas). */
    public boolean isBalanced() { return isBalanced(root); }

    private boolean isBalanced(Node<T> n) {
        if (n == null) return true;
        return Math.abs(balance(n)) <= 1 && isBalanced(n.left) && isBalanced(n.right);
    }
}