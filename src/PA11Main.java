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
        //heuristicSalesman(dGraph);

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
        System.out.println(graph);
    }

    public static void heuristicSalesman(DGraph graph)
    {

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
