package com.generation.postinoviaggiatore.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    static class Vertex {
        int index;
        double value;
    }

    static class Edge {
        Vertex from, to;
        double weight;
        String name;
    }

    List<Vertex> vertices;
    HashMap<Vertex, List<Edge>> adjacencyList;

    public Graph() {
        vertices = new ArrayList<>();
        adjacencyList = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
        adjacencyList.put(v, new ArrayList<>());
    }

    public void addEdge(Vertex v,Vertex u, Edge e) {
       if( areAdjacent(v,u) ) {
           removeEdge(v,u);
       }
       e = new Edge();
       adjacencyList.get(v).add(e);
    }

    public boolean areAdjacent(Vertex v, Vertex u){
        if(vertices.contains(v) && vertices.contains(u)) {
            for(Edge edge : adjacencyList.get(v)) {
                if(edge.to == u) {
                    return true;
                }
            }
            return false;
        }
    }


}
