/**
 * Niklaus Wetter
 * CSC 210
 *
 * This class will provide an implementation of a graph
 * data structure for use with our traveling salesman problem
 *
 */
import java.util.*;

public class DGraph
{
    //Fields
    /**
     * This map will store the information about our graph
     * The Vertex object will simply have a name, while the
     * Edge object will contain the source and destination
     * vertices, and the weight.
     *
     * For our usage the weight will be the distance between
     * each vertex in the edge.
     *
     * In this map the key will be the origin vertex, and the
     * value will be a list of all edges with a source of the
     * key vertex
     */
    private Map<Vertex, List<Edge>> adjacentVertices;
    /**
     * This map stores all the vertices using their string name
     * as a key for easy searching
     */
    private Map<String, Vertex> vertices;
    private List<String> vertexNames;

    //Methods

    //Default constructor
    public DGraph()
    {
        this.vertices = new HashMap<>();
        this.adjacentVertices = new HashMap<>();
        this.vertexNames = new ArrayList<>();
    }

    public void addVertex(String label)
    {
        this.vertices.putIfAbsent(label, new Vertex(label));
        this.vertexNames.add(label);
    }

    public void removeVertex(String label)
    {
        this.vertices.remove(label);
        this.vertexNames.remove(label);
    }

    public Vertex getVertexIfPresent(String label)
    {
        if(!this.vertices.containsKey(label))
            return null;
        return this.vertices.get(label);
    }

    public void addEdge(Vertex v1, Vertex v2, double weight)
    {
        //Exit if either vertex is null
        if(v1 == null || v2 == null)
            return;
        //If this is the first edge with this origin
        if(!this.adjacentVertices.containsKey(v1))
        {
            this.adjacentVertices.put(v1, new ArrayList<>());
        }
        //Now that the list has been created regardless, we add the edge
        //TODO: All weights are currently 0, add calculation functionality here
        this.adjacentVertices.get(v1).add(new Edge(v1,v2,weight));
    }

    public void removeEdge(Vertex v1, Vertex v2)
    {
        //Exit if either vertex is null
        if(v1 == null || v2 == null)
            return;
        //Exit if the map does not contain any edges originating at this vertex
        if(!this.adjacentVertices.containsKey(v1))
            return;
        //TODO:FINISH
    }

    /**
     * Returns a set of all vertices adjacent to the argument
     * @param v vertex to find all adjacent vertices for
     * @return a HashSet of Vertex objects; null if v is null,
     * or has no adjacent Vertex objects
     */
    public Set<Vertex> getAdjacentSet(Vertex v)
    {
        Set<Vertex> vertexSet = new HashSet<>();
        if(this.getDestinationSet(v)==null && this.getOriginSet(v)==null)
            return null;
        if(this.getDestinationSet(v)!=null)
        {
            for(Vertex t:this.getDestinationSet(v))
                vertexSet.add(t);
        }
        if(this.getOriginSet(v)!=null)
        {
            for (Vertex t:this.getOriginSet(v))
                vertexSet.add(t);
        }
        return vertexSet;
    }

    /**
     * Returns a set of all adjacent Vertex objects with v as the vertex
     * from which that edge came from, essentially returning only possible
     * destination Vertex objects of v
     * @param v the vertex for which to return the set
     * @return A HashSet of Vertex objects; null if v1 is null,
     * or has no adjacent Vertex objects
     */
    public Set<Vertex> getDestinationSet(Vertex v)
    {
        Set<Vertex> vertexSet = new HashSet<>();
        if(v==null)
            return null;
        if(this.adjacentVertices.containsKey(v))
        {
            for(Edge e:this.adjacentVertices.get(v))
                vertexSet.add(e.destination);
            return vertexSet;
        }
        return null;
    }

    public Set<Vertex> getOriginSet(Vertex v)
    {
        Set<Vertex> vertexSet = new HashSet<>();
        if(v==null)
            return null;
        for(String s:this.vertexNames)
        {
            if(this.adjacentVertices.containsKey(s))
            {
                Vertex temp = new Vertex(s);
                for(Edge e:this.adjacentVertices.get(temp))
                {
                    if(e.destination.equals(v))
                        vertexSet.add(e.source);
                    return vertexSet;
                }
            }
        }
        return null;
    }

    private boolean isAdjacent(Vertex v1, Vertex v2)
    {
        if(v1 == null || v2==null)
            return false;
        if(!this.vertices.containsValue(v1) || !this.vertices.containsValue(v2))
            return false;
        //At this point we know neither are null, and both are in the map
        if(this.adjacentVertices.containsKey(v1))
        {
            //If there is an edge with v1 as it's origin
            for(Edge e:this.adjacentVertices.get(v1))
            {
                if(e.destination.equals(v2))
                    return true;
            }
        }
        else if(this.adjacentVertices.containsKey(v2))
        {
            //If there is an edge with v2 as it's origin
            for(Edge e:this.adjacentVertices.get(v2))
            {
                if(e.destination.equals(v1))
                    return true;
            }
        }
        //If we reach this point and have not returned we return false
        return false;
    }

    @Override
    public String toString()
    {
        String temp = "Graph:\n  Vertices:\n";
        for(Vertex v: this.vertices.values())
            temp+="    - "+v+"\n";
        temp+= "  Edges:\n";
        for(List<Edge> e: this.adjacentVertices.values())
        {
            for(Edge ee: e)
                temp+="    - "+ee+"\n";
        }
        return temp;
    }

    //Inner Classes
    private class Vertex
    {
        String label;
        public Vertex(String label)
        {
            this.label=label;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return label.equals(vertex.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "label='" + label + '\'' +
                    '}';
        }
    }

    private class Edge
    {
        private Vertex source;
        private Vertex destination;
        private double weight;

        public Edge(Vertex source, Vertex destination, double weight)
        {
            this.source=source;
            this.destination=destination;
            this.weight=weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return Double.compare(edge.weight, weight) == 0 && source.equals(edge.source) && destination.equals(edge.destination);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, destination, weight);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "source=" + source +
                    ", destination=" + destination +
                    ", weight=" + weight +
                    '}';
        }
    }
}
