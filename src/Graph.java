import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Graph 
{
	public Node rootNode;
	public ArrayList<Node> nodes=new ArrayList<Node>();
	public int[][] adjMatrix;// ide j�n a szomsz�doss�gi m�trix
	int size;
	public void setRootNode(Node n) // be�ll�tjuk a csom�pontot
	{
		this.rootNode=n;
	}
	
	public Node getRootNode() // elvesz�nk egy csom�pontot
	{
		return this.rootNode;
	}
	
	public void addNode(Node n) // hozz�adunk egy csom�pontot
	{
		size++;
		nodes.add(n);
	}
	
	public Node getNode(String n){
		for(int i = 0; i < nodes.size(); i++){
			if(nodes.get(i).label.compareTo(n) == 0){
				return nodes.get(i);
			}
		}
		return null;
	}

	public void connectNode(Node start,Node end)  //�sszek�tj�k a csom�pontokat
	{
		//if(adjMatrix==null)
		//{
			//size=nodes.size();
			//adjMatrix=new int[size][size];
		//}
		end.showToMe.add(start);
		start.ishowTo.add(end);
		end.endcounter++;
		start.startcounter++;
		//int startIndex=nodes.indexOf(start);
		//int endIndex=nodes.indexOf(end);
		//adjMatrix[startIndex][endIndex]=1;
		//adjMatrix[endIndex][startIndex]=1;
	}
	
	private Node getUnvisitedChildNode(Node n) //visszaadjuk a m�g be nem j�rt pontokat
	{
		
		//int index=nodes.indexOf(n);
		int j=0;
		int index;
		while(j<n.ishowTo.size())
		{
			index = nodes.indexOf(n.ishowTo.get(j));
			if(((Node)nodes.get(index)).visited==false)
			{
				return (Node)nodes.get(index);
			}
			j++;
		}
		return null;
	}
	
	
	public void bfs(Writer txt) throws IOException
	{
		
		if(rootNode.visited != true){
			
		Queue<Node> q = new LinkedList<Node>(); 
		q.add(this.rootNode); 
		printNode(this.rootNode,txt); 
		rootNode.visited=true; 
		while(!q.isEmpty()) 
		{
			Node n=(Node)q.remove();
			Node child=null;
			while((child=getUnvisitedChildNode(n))!=null)
			{
				child.visited=true; 
				printNode(child,txt);
				q.add(child); 
			}
		}
		
		//clearNodes();
		}
	}
	

	
	
	//minden pontot bej�ratlanra �ll�tunk
	private void clearNodes()
	{
		int i=0;
		while(i<size)
		{
			Node n=(Node)nodes.get(i);
			n.visited=false;
			i++;
		}
	}
	
	//ki�rjuk a csom�pontot
	private void printNode(Node n, Writer txt) throws IOException
	{
		txt.write(n.label + "\n");
	}	
	

}
