import java.util.*;

public class DGraph
{
    private Map<Integer, List<Edge>> edges;
    private List<Integer> vertices;

    public DGraph()
    {
        this.edges = new HashMap<>();
        this.vertices = new ArrayList<>();
    }

    public void addVertex(int id)
    {
        if(!this.vertices.contains(id))
            this.vertices.add(id);
    }

    public boolean vertexExists(int id)
    {
        return this.vertices.contains(id);
    }

    public void addEdge(int v1, int v2, double weight)
    {
        if(!this.vertexExists(v1) || !this.vertexExists(v2))
            return;
        if(!this.edges.containsKey(v1))
            this.edges.put(v1,new ArrayList<>());
        this.edges.get(v1).add(new Edge(v1,v2,weight));
    }

    public Edge getEdgeIfPresent(int origin, int destination)
    {
        if(!this.vertexExists(origin) || !this.vertexExists(destination))
            return null;
        for(Edge e: this.edges.get(origin))
        {
            if(e.destination==destination)
                return e;
        }
        return null;
    }

    public List<Edge> getEdgesFromOrigin(int vertex)
    {
        return this.edges.get(vertex);
    }

    public double getEdgeWeight(Edge e){return e.weight;}
    public double getEdgeWeight(int origin, int destination)
    {
        return this.getEdgeIfPresent(origin,destination).weight;
    }

    public Set<Integer> getDestinationSet(int vertex)
    {
        Set<Integer> vertexSet = new LinkedHashSet<>();
        if(!this.vertexExists(vertex))
            return null;
        if(this.edges.containsKey(vertex))
        {
            for(Edge e:this.edges.get(vertex))
                vertexSet.add(e.destination);
            return vertexSet;
        }
        return null;
    }

    public int numVertices()
    {
        return this.vertices.size();
    }

    public void sortVertexList()
    {
        Collections.sort(this.vertices);
    }

    @Override
    public String toString()
    {
        String temp = "Graph:\n  Vertices:\n";
        for(int i:this.vertices)
            temp+="    - "+i+"\n";
        temp+="  Edges:\n";
        for(List<Edge> e:this.edges.values())
        {
            for (Edge ee: e)
                temp+="    - "+ee+"\n";
        }
        return temp;
    }

    //Internal Classes
    private class Edge
    {
        private int origin;
        private int destination;
        private double weight;

        public Edge(int origin, int destination, double weight)
        {
            this.origin=origin;
            this.destination=destination;
            this.weight=weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return origin == edge.origin && destination == edge.destination && Double.compare(edge.weight, weight) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(origin, destination, weight);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "origin=" + origin +
                    ", destination=" + destination +
                    ", weight=" + weight +
                    '}';
        }
    }
}