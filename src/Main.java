import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Graph g=new Matrix();

        for(String s: loadStrings("SubCombi//combi.txt")){
            String[] a= s.split(" , ");
            g.insertEdge(a[0],a[1],Integer.parseInt(a[2]));
            g.insertEdge(a[1],a[0],Integer.parseInt(a[2]));
        }
        g.printGraph();

        HashSet<Vertex> visited=new HashSet<Vertex>();
        System.out.println(" ");
        System.out.println(" ");
        g.visitDepthFirst(g.vertex("DATA"),visited);
        System.out.println(" ");
        System.out.println("Antal moduler: "+visited.size());
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Grupper med nul fælles studerende ");

        for (HashSet<Vertex> group: g.setGroupMatrix()){
            int studSum=0;
            for (Edge e:g.edges()){
                if (group.contains(e.to) && group.contains(e.from)){
                    studSum+=e.weight;
                }
            }
            System.out.println(group+" sum: "+studSum);
        }
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("gruppering 2:");
        HashSet<HashSet<Vertex>> groups2 =new HashSet<>();
        g.setGroupMatrix2(groups2);
        System.out.println(groups2);
        System.out.println(" ");

        for (HashSet<Vertex> group: groups2){
            int studSum=0;
            for (Edge e:g.edges()){
                if (group.contains(e.to) && group.contains(e.from)){
                    studSum+=e.weight;
                }
            }
            System.out.println(group+" sum: "+studSum);
        }
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Unikke grupper:");
        HashSet<HashSet<Vertex>> groups =new HashSet<>();
        g.setUniqueGroupMatrix(groups);
        System.out.println(groups);
        System.out.println(" ");

        for (HashSet<Vertex> group: groups){
            int studSum=0;
            for (Edge e:g.edges()){
                if (group.contains(e.to) && group.contains(e.from)){
                    studSum+=e.weight;
                }
            }
            System.out.println(group+" sum: "+studSum);
        }


        System.out.println(minOverlapGroups(g.setGroupMatrix(),g));

        //System.out.println(g.outEdge(g.vertex("DATA")));
        //System.out.println(g.getVertex());
        //System.out.println(g.vertices());
        //System.out.println(visited);
    }
    static ArrayList<String> loadStrings(String f){
        ArrayList<String> list=new ArrayList<>();
        try{
            BufferedReader in=new BufferedReader(new FileReader(f));
            while(true){
                String s=in.readLine();
                if(s==null)break;
                list.add(s);
            }
            in.close();
        }catch(IOException e){
            return null;
        }
        return list;
    }

    static Set<Edge> minOverlapGroups(HashSet<HashSet<Vertex>> groups, Graph graph){

        HashSet<HashSet<Edge>> edges = new HashSet<>();

        for (HashSet<Vertex> g:groups){
            HashSet<Edge> groupEdges = new HashSet<>();
            for (Vertex gV:g){
                groupEdges.addAll(graph.outEdge(gV));
            }
            edges.add(groupEdges);
        }

        HashSet<Edge> tGroups = new HashSet<>();
        HashSet<Vertex> visited = new HashSet<>();

        Vertex current =null;


        for (HashSet<Edge> e:edges){
            for (Edge e2:e){
                current=
                break;
            }
        }
        while (true){
            Edge nearest=null;
            for (Edge e:edges){
                if (e.from!=current)continue;
                if (visited.contains(e.to))continue;
                if (nearest==null || nearest.weight>e.weight)
                    nearest=e;
            }
            if (nearest==null)break;
            tGroups.add(nearest);
            visited.add(nearest.to);
            current=nearest.to;
        }
        return tGroups;
    }


}