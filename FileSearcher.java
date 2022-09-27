import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSearcher {

	static BinarySearchTree<Word> tree = new BinarySearchTree<Word>();
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stuff

		Scanner sc = new Scanner(System.in);
		
		File folder = new File(args[0]);
		
		//scan the folder and create the tree
		scanFiles(folder);
		
		//outer loop to allow the user to keep entering commands
		outer: while(true) {
			
			System.out.println("Enter a command (a, s, q): ");
			
			String choice = sc.nextLine();
			
			//if the user chooses all this prints out the tree in order but from right to left(like the example)
			if(choice.equals("a")) {
				
				inOrder(tree.root);
				
			}
			
			// if the choice is s allows the user to search for the word in the tree
			else if(choice.equals("s")) {
				
				System.out.println("Enter Search Word: ");
				
				String wrd = sc.nextLine();
				
				Word example = new Word(wrd);
				
				if(tree.contains(example)) {
					
					Word ret = tree.find(example);
					
					System.out.println("Files containing "+ret+": "+ret.getFileList());
				}
				
				else
					System.out.println(example+" does not exist in tree.");
				
			}
			
			//enter q to break outer loop and quit program
			else if(choice.equals("q"))
				
				break outer;
	
		}
		
	}
	
	//recursive function that scans the folders and creates the binary search tree
	public static void scanFiles(File folder) throws FileNotFoundException {
		
		//get all files / subdirs in folder
		File[] list = folder.listFiles(); 
		
		for(int i = 0; i < list.length; i++) {
			
			File current = list[i];
			
			char fl = current.getName().charAt(0);
			
			if(current.isHidden() == false && Character.isLetter(fl) || Character.isDigit(fl)) {
				if(current.isDirectory())
					scanFiles(current);
				else
					addTextToFile(current);
			} 
		}
	}
	
	/*
	 * This method is called from the scan files method above
	 * This method fills out the binary search tree through insert commands
	 * This method also sets and fills out the fileList array lists for each
	 * word in the files read
	 */
	public static void addTextToFile(File file) throws FileNotFoundException {
		
		Scanner scan = new Scanner(file);
		
		String filename = file.getName();
		
		while(scan.hasNext()) {
			
			String word = scan.next().replaceAll("\\p{Punct}", "");
			
			Word wr = new Word(word);
			
			if(tree.contains(wr) == true) {
				
				Word w = tree.find(wr);
				
				w.addToList(filename);
				
			}
			
			else {
				
				wr.addToList(filename);
				
				tree.insert(wr);
				
			}
			
		}
		
	}
	
	/*
	 * this is the in order method that prints all of the nodes of the tree
	 * when the all (a) call is made. Prints right to left instead of 
	 * left to right to match the example
	 */
	public static void inOrder(BinaryNode<Word> node) {
		
		if(node != null) {
			inOrder(node.right);
			System.out.println("Files containing "+node.element+": "+node.element.getFileList());
			inOrder(node.left);
		}
	}
}
