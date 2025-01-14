package com.generation.postinoviaggiatore.utility;

import java.util.PriorityQueue;
import java.util.Arrays;

public class TSPBranchAndBound {
    private static final int INF = Integer.MAX_VALUE; // Costante per indicare un valore infinito (nessun percorso)

    // Definizione del nodo usato nel Branch and Bound
    static class Node implements Comparable<Node> {
        int[] path; // Percorso corrente (sequenza dei nodi visitati)
        boolean[] visited; // Array per tenere traccia dei nodi già visitati
        int level; // Livello del nodo nell'albero di ricerca (quanti nodi sono stati visitati)
        int cost; // Costo accumulato del percorso corrente
        int lowerBound; // Stima del costo minimo per completare il percorso da questo nodo

        // Costruttore per inizializzare un nodo
        public Node(int[] path, boolean[] visited, int level, int cost, int lowerBound) {
            this.path = path.clone(); // Copia il percorso corrente
            this.visited = visited.clone(); // Copia lo stato dei nodi visitati
            this.level = level; // Imposta il livello del nodo
            this.cost = cost; // Imposta il costo accumulato
            this.lowerBound = lowerBound; // Imposta il lower bound calcolato
        }

        // Metodo per confrontare i nodi in base al loro lower bound (usato per la coda prioritaria)
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.lowerBound, other.lowerBound); // Ordina per costo minimo stimato
        }
    }

    // Funzione per calcolare il Lower Bound per un nodo
    private static int calculateLowerBound(int[][] graph, Node node) {
        int bound = 0; // Inizializza il lower bound a 0

        // Itera su ogni nodo del grafo
        for (int i = 0; i < graph.length; i++) {
            if (!node.visited[i]) { // Considera solo i nodi non visitati
                int min = INF; // Trova il costo minimo della riga
                for (int j = 0; j < graph.length; j++) {
                    // Evita il nodo corrente e calcola il minimo costo possibile
                    if (!node.visited[j] && i != j && graph[i][j] < min) {
                        min = graph[i][j];
                    }
                }
                // Aggiungi il costo minimo della riga al lower bound
                bound += (min == INF) ? 0 : min;
            }
        }
        return bound; // Restituisce il lower bound calcolato
    }

    // Funzione per calcolare l'Upper Bound usando l'euristica Nearest Neighbor
    private static int calculateUpperBound(int[][] graph) {
        boolean[] visited = new boolean[graph.length]; // Inizializza i nodi non visitati
        int current = 0; // Inizia dal nodo 0
        int cost = 0; // Costo iniziale è 0

        Arrays.fill(visited, false); // Segna tutti i nodi come non visitati
        visited[0] = true; // Segna il nodo iniziale come visitato

        // Itera su tutti i nodi per costruire un percorso
        for (int i = 1; i < graph.length; i++) {
            int next = -1; // Nodo successivo da visitare
            int minCost = INF; // Trova il costo minimo per il prossimo nodo

            // Cerca il nodo più vicino non ancora visitato
            for (int j = 0; j < graph.length; j++) {
                if (!visited[j] && graph[current][j] < minCost) {
                    next = j;
                    minCost = graph[current][j];
                }
            }

            if (next == -1) break; // Nessun altro nodo da visitare
            cost += minCost; // Aggiungi il costo al percorso
            visited[next] = true; // Segna il nodo come visitato
            current = next; // Sposta il cursore al nodo successivo
        }

        // Aggiungi il costo per tornare al nodo iniziale
        cost += graph[current][0];
        return cost; // Restituisce il costo del percorso trovato
    }

    // Implementazione principale del Branch and Bound per il TSP
    public static int tsp(int[][] graph) {
        int n = graph.length; // Numero di nodi nel grafo

        // Calcola un limite superiore iniziale con l'euristica Nearest Neighbor
        int upperBound = calculateUpperBound(graph);
        PriorityQueue<Node> pq = new PriorityQueue<>(); // Coda prioritaria per i nodi

        // Inizializza il nodo radice
        int[] initialPath = new int[n];
        boolean[] visited = new boolean[n];
        initialPath[0] = 0; // Il percorso inizia dal nodo 0
        visited[0] = true; // Segna il nodo 0 come visitato
        Node root = new Node(initialPath, visited, 1, 0, calculateLowerBound(graph, new Node(initialPath, visited, 0, 0, 0)));

        pq.add(root); // Aggiungi il nodo radice alla coda

        int bestCost = upperBound; // Inizializza il miglior costo con il limite superiore

        // Esegui il Branch and Bound
        while (!pq.isEmpty()) {
            Node currentNode = pq.poll(); // Estrai il nodo con il lower bound più basso

            // Se il lower bound è maggiore o uguale al miglior costo attuale, ignora il nodo
            if (currentNode.lowerBound >= bestCost) {
                continue;
            }

            // Se è un nodo foglia (tutti i nodi visitati), aggiorna il miglior costo
            if (currentNode.level == n) {
                int totalCost = currentNode.cost + graph[currentNode.path[n - 1]][currentNode.path[0]];
                if (totalCost < bestCost) {
                    bestCost = totalCost; // Aggiorna il miglior costo trovato
                }
                continue;
            }

            // Espandi il nodo corrente generando i suoi figli
            for (int i = 0; i < n; i++) {
                if (!currentNode.visited[i]) { // Considera solo i nodi non visitati
                    int[] newPath = currentNode.path.clone(); // Crea un nuovo percorso
                    newPath[currentNode.level] = i; // Aggiungi il nodo al percorso

                    boolean[] newVisited = currentNode.visited.clone(); // Aggiorna lo stato dei nodi visitati
                    newVisited[i] = true; // Segna il nodo come visitato

                    // Calcola il nuovo costo accumulato
                    int newCost = currentNode.cost + graph[currentNode.path[currentNode.level - 1]][i];

                    // Crea il nuovo nodo con il percorso aggiornato
                    Node childNode = new Node(newPath, newVisited, currentNode.level + 1, newCost, calculateLowerBound(graph, new Node(newPath, newVisited, currentNode.level, newCost, 0)));

                    // Se il lower bound del nodo figlio è migliore del miglior costo attuale, aggiungilo alla coda
                    if (childNode.lowerBound < bestCost) {
                        pq.add(childNode);
                    }
                }
            }
        }

        return bestCost; // Restituisce il costo del miglior percorso trovato
    }
}