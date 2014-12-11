import java.io.*;
import java.util.*;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		Graph citations = new Graph();
		ArrayList<Node> reachedWords = new ArrayList<Node>();
		ArrayList<String> etymons = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("scientist_result.txt"));
		String line;
		String parsed[];
		boolean duplicate = false;
		//Writer egyszerusitett = new BufferedWriter(new FileWriter("scientist_result.txt", false));
		// Szotovek felvetel
		while ((line = br.readLine()) != null) {
			line = line.toLowerCase();
			parsed = line.split("=");
			parsed[0] = parsed[0].trim();
			// System.out.println(parsed[1]);
			if (!(parsed[0].contains(" "))) {
				etymons.add(parsed[0]);
			}

		}
		br.close();
		System.out.println("Etymons: OK - levalasztva");

		// Node-ok letrehozasa
		br = new BufferedReader(new FileReader("scientist_result.txt"));
		int counter = 0;
		while ((line = br.readLine()) != null) { // beolvassuk a köv sort
			line = line.toLowerCase(); // kisbetűre alakít
			parsed = line.split(" "); // splittel space mentén

			for (int i = 0; i < parsed.length; i++) {
				parsed[i] = parsed[i].trim();
				if (!parsed[i].equals("{") && !parsed[i].equals("}")&& !parsed[i].equals("=") && !parsed[i].equals(" ") && !parsed[i].equals("")) { // nem érdekel a {} és =
					duplicate = false;
					for (int j = 0; j < citations.nodes.size(); j++) {
						if (parsed[i].equals(citations.nodes.get(j).label)) {
							duplicate = true;
						}
					}
					if (!duplicate) {
						Node node = new Node(parsed[i]);
						if (i == 0) {
							node.etymon = true;
						}/* else {
							String simplierWord = null;
							// Magyarazo szavak vizsgalata
							// Itt lehetne szűrni a {, főnév, *jpg dolgokat
							int longestCorrespondence = 0;
							for (int k = 0; k < etymons.size(); k++) {
								if(!(etymons.get(k).equals(parsed[i]))){
									for (int l = 1; l < parsed[i].length(); l++) {
										if (etymons.get(k).startsWith((String) parsed[i].subSequence(0, l))) {
											if(longestCorrespondence < l){
												longestCorrespondence = l;
												simplierWord = (String) etymons.get(k);
											}
										}
									}
								}
							}
							// Ezen a ponton simplierWord a legjobban hasonlito
							// szot tartalmazza
							// System.out.println("simplierWord: " +
							// simplierWord);
							if (simplierWord != null) {
								node.label = simplierWord;
								parsed[i] = simplierWord;
							}
						}*/

						citations.addNode(node);
						System.out.println(counter++);

					}
				}
			}

			// Kapcsolatok kialakitasa
			for (int i = 1; i < parsed.length; i++) {
				if (!parsed[i].equals("{") && !parsed[i].equals("}")&& !parsed[i].equals("=") && !parsed[i].equals(" ") && !parsed[i].equals("")) // nem érdekel a {} és =
					citations.connectNode(citations.getNode(parsed[0]),citations.getNode(parsed[i]));
			}
			
/*			for (int k = 0; k < parsed.length; k++) {
				egyszerusitett.write(parsed[k]);
				egyszerusitett.write(" ");
			}
			egyszerusitett.write("\n");*/
			
			
		/*	FileOutputStream fileOut = new FileOutputStream("graph.txt");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(citations);
	        //out.close();
	        fileOut.close();
	        System.out.println("Szerializálás megtörtént.");*/
		}
		
		//egyszerusitett.close();
		br.close();
		
	/*	for (int i = 0; i < citations.nodes.size(); i++) {
			// System.out.println(citations.nodes.get(i).label);
			//for (int j = 0; j < citations.nodes.get(i).ishowTo.size(); j++) {
				System.out.println(citations.nodes.get(i).label);
				System.out.println(citations.nodes.get(i).startcounter);
				System.out.println(citations.nodes.get(i).endcounter);
				System.in.read();
			//}
		}*/
		System.out.println("Node-ok letrehozasa: OK");
		System.out.println("Kapcsolatok letrehozasa: OK");
		

		
		System.out.println("Graf bejarasa megkezdodott.");
/*		// Graf bejarasanak elkezdese // sima gráfbejárás
		citations.setRootNode(citations.nodes.get(0));
		reachedWords.add(citations.nodes.get(0));
		Writer output = new BufferedWriter(new FileWriter("result.txt", false));
		citations.bfs(output);

		for (int i = 0; i < citations.nodes.size(); i++) {
			if (citations.nodes.get(i).visited == false) {
				citations.setRootNode(citations.nodes.get(i));
				reachedWords.add(citations.nodes.get(i));
				citations.bfs(output); // Fajlba is kiirjuk
			}
		}*/
		int best = 0; // legnagyobb iShowTo alapú bejárás
		String bestLabel = null;
		boolean isUnvisited = true;
		Writer output = new BufferedWriter(new FileWriter("result.txt", false));
		while (isUnvisited){
			isUnvisited = false;
			
			for(int i = 0; i < citations.nodes.size(); i++){
				if(citations.nodes.get(i).ishowTo.size() > best && citations.nodes.get(i).visited == false ){
					best = citations.nodes.get(i).ishowTo.size();
					bestLabel = citations.nodes.get(i).label;
				}
			}
			
			if(best == 0){
				for (int i = 0; i < citations.nodes.size(); i++) {
					if (citations.nodes.get(i).visited == false) {
						bestLabel = citations.nodes.get(i).label;
					}
				}
			}
			
			citations.setRootNode(citations.getNode(bestLabel));
			reachedWords.add(citations.getNode(bestLabel));
			citations.bfs(output);
			//System.out.println(citations.getNode(bestLabel).label);
			//System.out.println(citations.getNode(bestLabel).ishowTo.size());
			//System.in.read();
			best = 0;
			for(int i = 0; i < citations.nodes.size(); i++){
				if(!citations.nodes.get(i).visited){
					isUnvisited = true;
				}
			}
		}
		

		//output.close();
		System.out.println("Graf bejarasa: OK");

		// Elert szavak fajlba irasa
		Writer words = new BufferedWriter(new FileWriter("words.txt", false));
		for (int i = 0; i < reachedWords.size(); i++) {
			//System.out.println(reachedWords.get(i).label);
			words.write(reachedWords.get(i).label + "\n");
		}
		words.close();

		// System.out.println("showToMe.size(): " +
		// citations.getNode("hordozo").showToMe.size());
		// Bejaras

		// for(int i=0; i < citations.nodes.size(); i++){
		// citations.nodes.get(i).ishowTo.
		// }

	}

}
