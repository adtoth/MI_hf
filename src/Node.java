import java.util.ArrayList;
import java.util.Comparator;

public class Node implements Comparable<Node>
{
	public String label;
	public boolean visited = false;
	public boolean etymon = false;
	public double startcounter = 0;
	public double endcounter = 0;
	public ArrayList<Node> showToMe = new ArrayList<Node>();
	public ArrayList<Node> ishowTo = new ArrayList<Node>();
	public Node(String name)
	{
		this.label = name;
	}

		@Override
		public int compareTo(Node arg0) {
			// TODO Auto-generated method stub
			if(arg0.endcounter < this.endcounter) return 1;
			if(arg0.endcounter > this.endcounter) return -1;
			else return 0;
		}
		
		public static Comparator<Node> NodeEndCounter 
        = new Comparator<Node>() {
			public int compare(Node n1, Node n2){
				return n1.compareTo(n2);
			}
		};
	
		
	
}
