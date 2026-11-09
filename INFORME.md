# Informe breve: RedSegura Sincelejo

Autor: Juan Contreras

## Decisiones
1. Grafo mixto con una lista de adyacencia por vértice; las aristas no dirigidas se insertan en ambos extremos.
2. Bloqueo de conexiones mediante un estado en `Edge`; `getOutgoingEdges` las omite, así BFS, DFS y Dijkstra respetan el bloqueo sin código extra.
3. Dijkstra con PriorityQueue e ignorando entradas obsoletas; desempate por `<` estricto.
4. AVL con registro de rotaciones (LL, RR, LR, RL) para la demostración Q2.
5. Las etiquetas se insertan en orden alfabético en ABB y AVL para evidenciar la degeneración del ABB (Q1).

## Resultados
- Q1: "Hospital Universitario" se encuentra en 5 pasos en el ABB (altura 11) y en 4 en el AVL (altura 3).
- Q2: las cuatro secuencias dan raíz final 20.
- Q4: BFS = S02, S01, S06, S07, S09, S05, S08, S10, S12, S03, S04, S11.
  DFS = S02, S01, S07, S09, S05, S10, S08, S12, S03, S04, S11, S06.
- Q5: costo 24 minutos por S02, S01, S07, S08.
- Q6: al bloquear R16 el costo sigue en 24 y el camino pasa a S02, S01, S09, S10, S08.
- Q7: vértice inexistente manejado sin terminar el programa.

## Complejidades
Ver la tabla del README. Resumen: ABB O(h) (peor caso O(n)); AVL O(log n);
BFS/DFS O(V+E); Dijkstra O((V+E) log V); matriz de adyacencia O(V²); matriz de incidencia O(V·E).

## Medición
Ver la tabla del README: con datos ordenados el ABB necesita n pasos y el AVL ~log2 n.
