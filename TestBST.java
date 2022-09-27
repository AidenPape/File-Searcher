
public class TestBST {


	public static void main(String[] args)
	{
		
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
		
		tree.insert(6);
		tree.insert(2);
		tree.insert(4);
		tree.insert(3);
		tree.insert(1);
		tree.insert(8);
		tree.insert(11);
	
		
		System.out.println(tree.contains(3));
		System.out.println(tree.contains(5));
		
		tree.remove(2);
		tree.insert(8);
		tree.insert(9);

		System.out.println(tree.contains(2));
		System.out.println(tree.contains(8));
		System.out.println(tree.contains(9));
		System.out.println(tree.contains(10));
		
		tree.printTree();
		
		
		
	}
	
}


