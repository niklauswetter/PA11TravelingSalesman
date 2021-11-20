import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PA11Main
{
    public static void main(String[] args) throws FileNotFoundException {
	    File file = new File(args[0]);
        Scanner scanner = new Scanner(file);
        DGraph dGraphInt = new DGraph();

        //loadMatrix(scanner,dGraph);
        loadMatrix(scanner,dGraphInt);

        heuristicSalesman(dGraphInt);

        //Set this to true before running to view matrix output
        boolean debugFlag = false;
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

    public static void heuristicSalesman(DGraph graph)
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

        System.out.println(finalOutput);
    }

    public static void backtrackSalesman(DGraph graph)
    {

    }

    public static void customSalesman(DGraph graph)
    {

    }

    public static void time()
    {

    }
}
