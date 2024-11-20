import java.util.*;

abstract class Graph{
    abstract void insertEdge(String v,String u,int w);
    abstract void printGraph();
    abstract void setUniqueGroupMatrix(HashSet<HashSet<Vertex>> groupMatrix);
    abstract void setGroupMatrix2(HashSet<HashSet<Vertex>> groupMatrix);
    abstract HashSet<HashSet<Vertex>> setGroupMatrix();

    private HashMap<String,Vertex> vertex=new HashMap<>();
    public Vertex vertex(String s){
        if(!vertex.containsKey(s))vertex.put(s,new Vertex(s));
        return vertex.get(s);
    }
    public Collection<Vertex> vertices(){return vertex.values();}
    abstract Collection<Edge> edges();
    abstract Collection<Edge> outEdge(Vertex v);
    void visitDepthFirst(Vertex v, Set<Vertex> visited){
        if(visited.contains(v))return;
        System.out.println("visited "+v);
        visited.add(v);
        for(Edge e: outEdge(v))
            visitDepthFirst(e.to,visited);
    }
    abstract ArrayList<Vertex> getVertex();

}