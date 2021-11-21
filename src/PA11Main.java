import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PA11Main
{
    public static void main(String[] args) throws IOException {
	    File file = new File(args[0]);
        Scanner scanner = new Scanner(file);
        DGraph dGraphInt = new DGraph();

        //loadMatrix(scanner,dGraph);
        loadMatrix(scanner,dGraphInt);

        //THIS ONE WORKS

        System.out.println("Heuristic: "+heuristicSalesman(dGraphInt));
        System.out.println("Backtrack: "+backtrackSalesman(dGraphInt));
        System.out.println("Custom: ");
        time(dGraphInt);
    }

    public static void loadMatrix(Scanner scanner, DGraph graph)
    {
        boolean firstSet=true;
        while(scanner.hasNext())
        {
            String temp = scanner.nextLine();
            if(temp.charAt(0)=='%')
                continue;
            String[] nums = temp.split("\\s+");
            if(firstSet)
            {
                firstSet=false;
                continue;
            }
            int origin = Integer.parseInt(nums[0]);
            int destination = Integer.parseInt(nums[1]);
            double weight = Double.parseDouble(nums[2]);
            graph.addVertex(origin);
            graph.addVertex(destination);
            graph.addEdge(origin,destination,weight);
        }
        graph.sortVertexList();
    }

    public static String heuristicSalesman(DGraph graph)
    {
        List<Integer> vertices = graph.getVertices();
        List<Integer> visitedVertices = new ArrayList<>();
        List<Integer> order = new ArrayList<>();
        int currentVertex = 1;
        double cost = 0;
        double d = Double.MAX_VALUE;
        double m = Double.MAX_VALUE;
        int mPos=-1;

        //System.out.println("Number of Vertices: "+graph.numVertices());

        while(visitedVertices.size()<graph.numVertices())
        {

            visitedVertices.add(currentVertex);

            //System.out.println("Visited: "+visitedVertices);

            order.add(currentVertex);
            m = Double.MAX_VALUE;
            for(int i=0;i<vertices.size();i++)
            {

                int testValue = vertices.get(i);
                //Ignore possibility of self connections
                if(testValue==currentVertex)
                    continue;
                //Ignore edges to vertices we have already been to
                if(visitedVertices.contains(testValue))
                    continue;

                d = graph.getEdgeWeight(currentVertex,testValue);

                //System.out.println("Current: "+currentVertex+", Test: "+testValue+", Weight: "+d);

                if(d<m || vertices.size()-1==visitedVertices.size())
                {
                    m=d;
                    mPos=i;
                }

            }
            if(visitedVertices.size()<= graph.numVertices()-1)
                cost+=m;
            currentVertex=vertices.get(mPos);

            //System.out.println("The minimum is "+currentVertex+" with weight "+m);
            //System.out.println("Total cost: "+cost);
        }
        //cost-=d;

        int[] finalOrder = new int[vertices.size()];
        for(int i=0;i<visitedVertices.size();i++)
            finalOrder[i]=visitedVertices.get(i);
        //System.out.println("Final: "+finalOrder[finalOrder.length-1]+","+1+" weight: "+graph.getEdgeWeight(finalOrder[finalOrder.length-1],1));
        cost += graph.getEdgeWeight(finalOrder[finalOrder.length-1],1);
        //System.out.println("Cost: "+cost);

        cost = Math.round(cost*10)/10.0;

        String finalOutput ="cost = "+cost+", visitOrder = [1";
        for(int i=1;i<finalOrder.length;i++)
            finalOutput+=", "+finalOrder[i];
        finalOutput+="]";

        return finalOutput;
    }

    public static String backtrackSalesman(DGraph graph)
    {
        List<Integer> vertices = graph.getVertices();
        List<List<Integer>> paths = new ArrayList<>();
        List<Double> costs = new ArrayList<>();

        permute(graph,vertices,0,paths,costs);

        double minCost = Double.MAX_VALUE;
        List<Integer> minPath = new ArrayList<>();

        for(int i=0;i<paths.size();i++)
        {
            double temp = costs.get(i);
            if(minCost>temp)
            {
                minCost=temp;
                minPath = new ArrayList<>(paths.get(i));
            }
            //System.out.println(paths.get(i)+" cost = "+costs.get(i));
        }
        //System.out.println("cost = "+minCost+", visitOrder = "+minPath);
        return "cost = "+minCost+", visitOrder = "+minPath+"";
    }

    public static void permute(DGraph graph, List<Integer> vertices, int k, List<List<Integer>> storagePointer, List<Double> costPointer)
    {
        for(int i=k;i<vertices.size();i++)
        {
            Collections.swap(vertices,i,k);
            permute(graph, vertices,k+1,storagePointer,costPointer);
            Collections.swap(vertices,k,i);
        }
        if(vertices.get(0)!=1)
            return;
        if(k == vertices.size()-1)
        {
            //THESE ARE THE COMPLETE SOLUTIONS
            double cost = getCostFromVertexList(graph,vertices);
            List<Integer> copy = new ArrayList<>(vertices);
            storagePointer.add(copy);
            costPointer.add(cost);
            //System.out.println(copy+" cost = "+cost);
        }
    }

    public static double getCostFromVertexList(DGraph graph, List<Integer> vertices)
    {
        int size = vertices.size();
        double cost = 0;
        for(int i=0;i<size;i++)
        {
            if(i<size-1)
                cost+=graph.getEdgeWeight(vertices.get(i),vertices.get(i+1));

            if(i==size-1)
                cost+=graph.getEdgeWeight(vertices.get(i),vertices.get(0));
        }
        return Math.round(cost*10)/10.0;
    }

    public static String customSalesman(DGraph graph)
    {
        return "";
    }

    public static void time(DGraph graph) throws IOException {
        double divisor = 0.000001;

        long startH = System.nanoTime();
        String h = heuristicSalesman(graph);
        long endH = System.nanoTime();
        double totalH=(endH-startH)*divisor;

        long startB = System.nanoTime();
        String b = backtrackSalesman(graph);
        long endB = System.nanoTime();
        double totalB=(endB-startB)*divisor;

        long startC = System.nanoTime();
        String c = customSalesman(graph);
        long endC = System.nanoTime();
        double totalC=(endC-startC)*divisor;

        File out = new File("README.md");
        out.createNewFile();
        FileWriter writer = new FileWriter("README.md");
        writer.write("Heuristic: "+h+" time: "+totalH+" ms\n");
        writer.write("Backtrack: "+b+" time: "+totalB+" ms\n");
        writer.write("Custom: "+c+" time: "+totalC+" ms\n");
        writer.close();
    }
}
