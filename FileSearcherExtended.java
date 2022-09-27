/*
 * All of the methods that are named the same as the methods in the  
 * first file searcher class do the exact same thing
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSearcherExtended {
	
		static BinarySearchTree<Word> tree = new BinarySearchTree<Word>();
		
		//this is a second binary tree to hold the directory information
		static BinarySearchTree<Word> filetree = new BinarySearchTree<Word>();
		
		public static void main(String[] args) throws FileNotFoundException {
			// TODO Auto-generated method stuff

			Scanner sc = new Scanner(System.in);
			
			File folder = new File(args[0]);
			
			scanFiles(folder);
			
			/*
			 * call to the scan directory method that works the
			 * same as the scan files but for the directories
			 */
			scanDirectoryFiles(folder);
			
			outer: while(true) {
				
				System.out.println("Enter a command (a, s, q): ");
				
				String choice = sc.nextLine();
				
				if(choice.equals("a")) {
					
					inOrder(tree.root);
					
					//inorderFile call - prints all info for filetree
					inOrderFile(filetree.root);
					
				}
				
				else if(choice.equals("s")) {
					
					System.out.println("Enter Search Word: ");
					
					String wrd = sc.nextLine();
					
					System.out.println("Word to print>> "+wrd);
					
					Word example = new Word(wrd);
					
					System.out.println(example);
					
					/*
					 * These are all the options that can happen when the single search
					 * is chosen
					 * This sees if the search word is in the tree, the file tree ,
					 * the tree and the file tree or neither 
					 */
					if(tree.contains(example)) {
						
						if(filetree.contains(example)) {
							
							Word ret = tree.find(example);
							
							System.out.println("Files containing "+ret+": "+ret.getFileList());
							
							Word ret2 = filetree.find(example);
							
							System.out.println("Directories containing "+ret2+": "+ret2.getFileList());
							
						}
						else {
							
							Word ret = tree.find(example);
						
							System.out.println("Files containing "+ret+": "+ret.getFileList());
						}
					}
					
					else if(filetree.contains(example)) {
						
						Word ret = tree.find(example);
						
						System.out.println("Files containing "+ret+": "+ret.getFileList());
						
						Word ret2 = filetree.find(example);
						
						System.out.println("Directories containing "+ret2+": "+ret2.getFileList());
					}
					
					else
						System.out.println(example+" does not exist in tree.");
			
				}
				
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
		
		/*
		 * this method works similar to the scan files method 
		 * but works for the directory names and fills out the
		 * file tree binary search tree
		 * 
		 * This method works from the bottom up, first checking the 
		 * leaf files and then their sub directories (parents) and then
		 * the main directory (grandparent). This works similar to a 
		 * post order of the tree. It creates the list of directories that hold the other subdirectories and files
		 */
		public static void scanDirectoryFiles(File folder) throws FileNotFoundException {
			
			//get all files / subdirs in folder
			
			if(folder.isDirectory()) {
				
				File[] list = folder.listFiles(); 
				
				for(int i = 0; i < list.length; i++) {
					
					File current = list[i];
					
					char cr = current.getName().charAt(0);
					
					if(current.isHidden() == false && Character.isLetter(cr) || Character.isDigit(cr)) {
						if(current.isDirectory()) {
				
							scanDirectoryFiles(current);
						}
						else {
							
							Word c = new Word(current.getName());
							
							addFileToFile(current, c);
						}
					}
				}
				
				Word w = new Word(folder.getName());
				
				addFileToFile(folder, w);
			}				
		}
		
		/*
		 * This works very similar to the add text to file method but for the
		 * file tree and directories. This works recursively to check the word that is searched
		 * or the root for the all call. Then the method works its way up the 
		 * binary tree by finding the parents of each node and then their grandparents 
		 * and so on, building the Word c's fileList array list.
		 */
		public static void addFileToFile(File file, Word c) throws FileNotFoundException {
			
			String parent = file.getParent();
			
			if(parent!= null) {
			
				if(filetree.contains(c)) {
					
					Word p2 = filetree.find(c);
					
					c.addToList(fileComponent(parent));
					
					addFileToFile(file.getParentFile(), c);
				}
				
				else {
					
					c.addToList(fileComponent(parent));
					
					filetree.insert(c);
					
					addFileToFile(file.getParentFile(), c);
				}
			}
			
			else {
				
				Word fin = new Word(file.getName());
				
				if(filetree.contains(fin)) {
					
					Word p2 = filetree.find(fin);
					
					fin.addToList(fileComponent(file.getName()));
					
				}
				
				else {
					
					fin.addToList(fileComponent(file.getName()));
					
					filetree.insert(fin);
				}
			}
		}
		
		//same as the in order for the regular tree just with file tree
		public static void inOrderFile(BinaryNode<Word> node) {
			
			if(node != null) {
				inOrderFile(node.right);
				System.out.println("Directory Names containing "+node.element+": "+node.element.getFileList());
				inOrderFile(node.left);
			}
		}
		
		/*
		 * this basic method just cuts off the end of the path name for
		 * directories/sub directories that have parents
		 * eg. directory2/hws would just become hws
		 */
		
		public static String fileComponent(String filename) {
			   
			int i = filename.lastIndexOf(File.separator);
			
			   if(i > -1)
			      return filename.substring(i + 1);
			   else
			      return filename;
			}
	}


