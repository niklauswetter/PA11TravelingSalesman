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

        int testSize = 11;
        for(int i = 1;i<=testSize;i++)
        {
            for(int j = testSize;j>0;j--)
            {
                if(i==j)
                    continue;
                String v1 = String.valueOf(i);
                String v2 = String.valueOf(j);
                System.out.println("Vertices "+i+", "+j+" are adjacent: "+graph.isAdjacent(graph.getVertexIfPresent(v1),graph.getVertexIfPresent(v2)));
            }
        }
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
