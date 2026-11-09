/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.app;

import co.edu.redsegura.tree.AvlTree;
import co.edu.redsegura.tree.BinarySearchTree;

/** Medición breve: inserta n claves en orden creciente y busca la última. */
public class Benchmark {

    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10000, 20000};
        System.out.println("n | ABB insert ms | AVL insert ms | ABB pasos | AVL pasos");
        for (int n : sizes) {
            BinarySearchTree<Integer> abb = new BinarySearchTree<>();
            AvlTree<Integer> avl = new AvlTree<>();

            long t0 = System.nanoTime();
            for (int i = 1; i <= n; i++) abb.insert(i);
            long abbMs = (System.nanoTime() - t0) / 1_000_000;

            t0 = System.nanoTime();
            for (int i = 1; i <= n; i++) avl.insert(i);
            long avlMs = (System.nanoTime() - t0) / 1_000_000;

            abb.search(n);
            avl.search(n);
            System.out.println(n + " | " + abbMs + " | " + avlMs + " | "
                    + abb.getLastSearchSteps() + " | " + avl.getLastSearchSteps());
        }
    }
}