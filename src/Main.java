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

        Graph groupGraph = new Matrix();


        System.out.println(" ");
        System.out.println(" ");

        int groupNr=1;
        for (HashSet<Vertex> groupG:g.setGroupMatrix()){
            int checkGroupNr=1;
            HashSet<Edge> groupEdges = new HashSet<>();
            for (Vertex v: groupG){
                groupEdges.addAll(g.outEdge(v));
            }
            System.out.println("group " + groupNr);
            for (HashSet<Vertex> gr:g.setGroupMatrix()){
                int groupW=0;
                HashSet<Edge> groupEdgesComp = new HashSet<>();
                for (Vertex v2:gr){
                    groupEdgesComp.addAll(g.outEdge(v2));
                }
                for (Edge e:groupEdges){
                    for (Edge e2:groupEdgesComp){
                        if (e.to==e2.from){
                            groupW+=e.weight;
                            break;
                        }
                    }
                }
                if (!(groupW ==0)) {
                    System.out.println("group "+checkGroupNr+" -> "+groupW);
                    groupGraph.insertEdge("Gr"+groupNr,"Gr"+checkGroupNr,groupW);
                }
                checkGroupNr++;
            }
            groupNr++;
            System.out.println(" ");
        }
        System.out.println(" ");
        System.out.println(" ");


        groupGraph.printGraph();

        System.out.println(" ");
        System.out.println(" ");

        for (Edge e:minOverlapGroups(groupGraph)){
            System.out.println(e);
        }
        System.out.println(" ");
        System.out.println(" ");

        HashSet<HashSet<Vertex>> added = new HashSet<>();
        HashSet<Vertex> spot = new HashSet<>();
        HashSet<Vertex> checked = new HashSet<>();


        for (Edge e:minOverlapGroups(groupGraph)){
            if (!checked.contains(e.to)) {
                spot.add(e.to);
                checked.add(e.to);
            }
            if (!checked.contains(e.from)) {
                spot.add(e.from);
                checked.add(e.from);
            }
            if (spot.size()<3)continue;
            added.add(new HashSet<>(spot));
            spot.clear();
        }


        System.out.println(" ");
        System.out.println(" ");

        for (HashSet<Vertex> v:added){
            System.out.print(v);
            int studSum=0;
            HashSet<Edge> checker = new HashSet<>();

            for (Vertex v2:v){
                checker.addAll(groupGraph.outEdge(v2));
            }
            HashSet<Edge> checker2 = new HashSet<>();
            for (Edge e : checker) {
                if (v.contains(e.to) && v.contains(e.from) && !checker2.contains(e)){
                    studSum+=e.weight;
                    checker2.add(e);
                }
            }
            System.out.println(" studerende tilfældes: "+studSum/2);
        }





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

    static Set<Edge> minOverlapGroups(Graph graph){

        Collection<Edge> edges=graph.edges();

        HashSet<Edge> tGroups = new HashSet<>();
        HashSet<Vertex> visited = new HashSet<>();

        Vertex current =null;

        for (Edge e:edges){
            current=e.from;break;
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