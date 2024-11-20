import java.util.*;

class Matrix extends Graph{
    ArrayList<ArrayList<Integer>> matrix=new ArrayList<>();

    ArrayList<Vertex> vertex=new ArrayList<>();
    HashMap<Vertex,Integer> vertexIndex=new HashMap<>();
    void insertEdge(String u,String v,int w){
        Vertex u1=vertex(u);
        Vertex v1=vertex(v);
        if(!vertexIndex.containsKey(u1)) {
            vertexIndex.put(u1, vertexIndex.size());
            vertex.add(u1);
        }
        if(!vertexIndex.containsKey(v1)) {
            vertexIndex.put(v1, vertexIndex.size());
            vertex.add(v1);
        }
        setMatrix(vertexIndex.get(u1),vertexIndex.get(v1),w);
    }

    void setMatrix(int i,int j,int w){
        while(matrix.size()<=i)matrix.add(new ArrayList<Integer>());
        ArrayList<Integer> row=matrix.get(i);
        while(row.size()<= j)row.add(null);
        row.set(j,w);
    }
    void setUniqueGroupMatrix(HashSet<HashSet<Vertex>> groupMatrix){
        ArrayList<Vertex> done = new ArrayList<>();

        for (int i=0; i<matrix.size();i++) {
            Vertex currentV1 = vertex.get(i);
            HashSet<Vertex> tGroup = new HashSet<>();

            if (!done.contains(currentV1)) {
                tGroup.add(currentV1);
                done.add(currentV1);
            }
            for (int j = 0; j < matrix.size(); j++) {
                Vertex currentV2 = vertex.get(j);
                if (done.contains(currentV2)) continue;
                if (tGroup.size() > 2) break;
                HashSet<Vertex> v1Ver = new HashSet<>();
                for (Vertex v : tGroup) {
                    Collection<Edge> v1Edges = outEdge(v);
                    v1Ver.add(v);
                    for (Edge e : v1Edges) {
                        v1Ver.add(e.to);
                    }
                }
                if (!v1Ver.contains(currentV2)) {
                    tGroup.add(currentV2);
                    done.add(currentV2);
                }
            }

            if (tGroup.isEmpty()) {
                done.removeAll(tGroup);
                continue;
            }
            groupMatrix.add(tGroup);
        }

    }
    void setGroupMatrix2(HashSet<HashSet<Vertex>> groupMatrix){
        ArrayList<Vertex> done = new ArrayList<>();

        for (int i=0; i<matrix.size();i++) {
            Vertex currentV1 = vertex.get(i);
            HashSet<Vertex> tGroup = new HashSet<>();
            tGroup.add(currentV1);

            for (int j = 0; j < matrix.size(); j++) {
                Vertex currentV2 = vertex.get(j);
                if (tGroup.contains(currentV2))continue;

                HashSet<Vertex> v1Ver = new HashSet<>();
                for (Vertex v : tGroup) {
                    Collection<Edge> v1Edges = outEdge(v);
                    v1Ver.add(v);
                    for (Edge e : v1Edges) {
                        v1Ver.add(e.to);
                    }
                }
                if (!v1Ver.contains(currentV2)) {
                    tGroup.add(currentV2);
                }
            }
            groupMatrix.add(tGroup);
        }
    }

    HashSet<HashSet<Vertex>> setGroupMatrix(){
        HashSet<HashSet<Vertex>> groupMatrix = new HashSet<>();
        ArrayList<Vertex> done = new ArrayList<>();

        for (int i=0; i<matrix.size();i++) {
            Vertex currentV1 = vertex.get(i);
            HashSet<Vertex> tGroup = new HashSet<>();
            tGroup.add(currentV1);
            done.add(currentV1);

            for (int j = 0; j < matrix.size(); j++) {
                Vertex currentV2 = vertex.get(j);
                if (tGroup.contains(currentV2))continue;
                if (done.contains(currentV2))continue;
                HashSet<Vertex> v1Ver = new HashSet<>();
                for (Vertex v : tGroup) {
                    Collection<Edge> v1Edges = outEdge(v);
                    v1Ver.add(v);
                    for (Edge e : v1Edges) {
                        v1Ver.add(e.to);
                    }
                }
                if (!v1Ver.contains(currentV2)) {
                    tGroup.add(currentV2);
                    done.add(currentV2);

                }
            }
            groupMatrix.add(tGroup);
        }

        HashSet<HashSet<Vertex>> removed = new HashSet<>();
        for (HashSet<Vertex> g: groupMatrix){
            for (HashSet<Vertex> g2 : groupMatrix) {
                if (g==g2)continue;
                if (!g.containsAll(g2))continue;
                removed.add(g2);
            }
        }
        groupMatrix.removeAll(removed);

        return groupMatrix;
    }



    /*

     */

    Collection<Edge> edges(){
        HashSet<Edge> edges=new HashSet<>();
        for(int i=0;i<matrix.size();i++){
            ArrayList<Integer> row=matrix.get(i);
            for(int j=0;j<row.size();j++){
                if(row.get(j)==null)continue;
                edges.add(new Edge(vertex.get(i), vertex.get(j), row.get(j)));
            }
        }
        return edges;
    }
    Collection<Edge> outEdge(Vertex v){
        ArrayList<Integer> row=matrix.get(vertexIndex.get(v));
        HashSet<Edge> edges=new HashSet<>();
        for(int j=0;j<row.size();j++){
            if(row.get(j)==null)continue;
            edges.add(new Edge(v,vertex.get(j),row.get(j)));
        }
        return edges;
    }



    @Override
    public ArrayList<Vertex> getVertex() {
        return vertex;
    }

    void printGraph() {
        //System.out.println(vertexIndex);
        System.out.print("     ");
        for(int i=0;i<matrix.size();i++) {
            //String længde
            StringBuilder verLength = new StringBuilder(vertex.get(i).toString());
            switch (verLength.length()){
                case 2:
                    verLength.insert(0," ");
                    verLength.append(" ");
                    break;
                case 3:
                    verLength.append(" ");
                    break;
                case 4:
                    break;
                default:
                    verLength.setLength(4);
            }
            System.out.print(" " + verLength);
        }
        System.out.println();
        for(int i=0;i<matrix.size();i++){
            //String længde
            StringBuilder verLength = new StringBuilder(vertex.get(i).toString());
            switch (verLength.length()){
                case 2:
                    verLength.append("  ");
                    break;
                case 3:
                    verLength.append(" ");
                    break;
                case 4:
                    break;
                default:
                    verLength.setLength(4);
            }
            System.out.print(verLength);
            ArrayList<Integer> row=matrix.get(i);
            for(int j=0;j<row.size();j++){
                if(row.get(j)==null) {
                    System.out.print("    0");
                }
                else {
                    StringBuilder intLength = new StringBuilder(row.get(j).toString());
                    switch (intLength.length()){
                        case 1:
                            System.out.print("  ");
                            break;
                        case 2:
                            System.out.print(" ");
                            break;
                        default:
                            break;
                    }
                    System.out.print("  " + intLength);
                }
            }
            System.out.println();
        }
    }


}