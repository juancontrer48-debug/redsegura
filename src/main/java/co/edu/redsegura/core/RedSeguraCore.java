/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.core;

import co.edu.redsegura.graph.BreadthFirstSearch;
import co.edu.redsegura.graph.DepthFirstSearch;
import co.edu.redsegura.graph.DijkstraSolver;
import co.edu.redsegura.graph.GraphRepresentations;
import co.edu.redsegura.graph.MixedGraph;
import co.edu.redsegura.model.Vertex;
import co.edu.redsegura.tree.AvlTree;
import co.edu.redsegura.tree.BinarySearchTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Fachada del sistema RedSegura. La aplicación solo habla con esta clase.
 * Usa MixedGraph para la red y AvlTree/BinarySearchTree para indexar lugares.
 */
public class RedSeguraCore {

    private final MixedGraph graph;
    private final AvlTree<String> avl = new AvlTree<>();
    private final BinarySearchTree<String> abb = new BinarySearchTree<>();

    public RedSeguraCore() {
        this.graph = DataFactory.createSincelejoGraph();
        // Etiquetas en orden alfabético: el ABB se degenera, el AVL se balancea.
        List<Vertex> sorted = new ArrayList<>(graph.getVertices());
        sorted.sort(Comparator.comparing(Vertex::getLabel));
        for (Vertex v : sorted) {
            abb.insert(v.getLabel());
            avl.insert(v.getLabel());
        }
    }

    // ---------- Q1: búsqueda en ABB y AVL ----------

    /** Devuelve {pasosABB, pasosAVL, encontrado(1/0)}. */
    public int[] compareSearch(String label) {
        boolean foundAbb = abb.search(label);
        int stepsAbb = abb.getLastSearchSteps();
        avl.search(label);
        int stepsAvl = avl.getLastSearchSteps();
        return new int[]{stepsAbb, stepsAvl, foundAbb ? 1 : 0};
    }

    public int abbHeight() { return abb.height(); }
    public int avlHeight() { return avl.height(); }

    // ---------- Q2: rotaciones AVL ----------

    /** Inserta las claves en un AVL nuevo y devuelve el log de rotaciones y el recorrido preorden. */
    public String demoRotation(String name, int... keys) {
        AvlTree<Integer> t = new AvlTree<>();
        for (int k : keys) t.insert(k);
        return name + ": claves " + java.util.Arrays.toString(keys)
                + " -> rotación " + t.getRotationLog()
                + ", raíz final " + t.rootKey()
                + ", preorden " + t.preOrder();
    }

    // ---------- Q3: representaciones ----------

    public String adjacencyList() { return GraphRepresentations.adjacencyListToString(graph); }
    public String adjacencyMatrix() { return GraphRepresentations.adjacencyMatrixToString(graph); }
    public String incidenceMatrix() { return GraphRepresentations.incidenceMatrixToString(graph); }

    // ---------- Q4: recorridos ----------

    public List<String> bfs(String startId) {
        return BreadthFirstSearch.run(graph, startId).getOrder();
    }

    public List<String> dfs(String startId) {
        return DepthFirstSearch.run(graph, startId);
    }

    // ---------- Q5 y Q6: ruta mínima y bloqueos ----------

    /** Descripción de la ruta mínima: costo y camino con etiquetas. */
    public String shortestRoute(String fromId, String toId) {
        DijkstraSolver.Result r = DijkstraSolver.run(graph, fromId, toId);
        if (!r.isReachable(toId)) {
            return "No hay ruta de " + fromId + " a " + toId + ".";
        }
        List<String> path = r.pathTo(toId);
        StringBuilder sb = new StringBuilder();
        sb.append("Costo: ").append(r.distanceTo(toId)).append(" minutos\n");
        sb.append("Camino: ").append(String.join(" -> ", path)).append("\n");
        sb.append("Conexiones: ").append(r.edgesTo(toId)).append("\n");
        for (String id : path) {
            sb.append("  ").append(id).append(" = ").append(graph.getVertex(id).getLabel()).append("\n");
        }
        return sb.toString();
    }

    public int shortestCost(String fromId, String toId) {
        return DijkstraSolver.run(graph, fromId, toId).distanceTo(toId);
    }

    public void blockEdge(String edgeId) { graph.blockEdge(edgeId); }

    public void unblockEdge(String edgeId) { graph.unblockEdge(edgeId); }

    // ---------- Consultas varias ----------

    public String describeVertex(String id) {
        Vertex v = graph.getVertex(id);
        return v + " [" + v.getType() + "], vecinos: " + graph.getNeighbors(id);
    }

    public int vertexCount() { return graph.vertexCount(); }

    public int edgeCount() { return graph.edgeCount(); }

    public boolean hasCycle() { return DepthFirstSearch.hasCycle(graph); }

    public List<List<String>> weakComponents() { return DepthFirstSearch.weakComponents(graph); }
}