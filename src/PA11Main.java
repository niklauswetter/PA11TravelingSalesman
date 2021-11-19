import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PA11Main
{

    public static void main(String[] args) throws FileNotFoundException {
	    File file = new File(args[0]);
        Scanner scanner = new Scanner(file);
        DGraph graph = new DGraph();

        loadMatrix(scanner,graph);
        System.out.println(graph);
    }

    public static void loadMatrix(Scanner scanner, DGraph graph)
    {
        boolean firstSet = true;
        while(scanner.hasNext())
        {
            String temp = scanner.nextLine();
            //Skip over comment lines
            if(temp.charAt(0)=='%')
                continue;
            String[] nums = temp.split("\\s+");
            //Skip the description line
            if(firstSet)
            {
                firstSet=false;
                continue;
            }
            //Load the graph
            String origin = nums[0];
            String destination = nums[1];
            double weight = Double.parseDouble(nums[2]);
            graph.addVertex(origin);
            graph.addVertex(destination);
            graph.addEdge(graph.getVertexIfPresent(origin),graph.getVertexIfPresent(destination),weight);
        }
    }

}
