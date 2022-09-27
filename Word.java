import java.util.ArrayList;

public class Word implements Comparable <Word>{

	//data fields:
	private String text; //actual word and key
	private ArrayList<String> fileList = new ArrayList<String>(); //list of filenames + containing this word
	
	
	//empty constructor class for Word object
	public Word() {
	
	}
	
	//Word object constructor with the name built in
	public Word(String string) {
		this.text = string;
	}
	
	//method to set the text value
	public void setText(String text) {
		this.text = text;
	}
	
	//method to set file list (if ever needed)
	public void setFileList(ArrayList<String> fileList) {
		this.fileList = fileList;
	}
	
	//method to get the text value
	public String getText() {
		return text;
	}
	
	//returns the file list
	public ArrayList<String> getFileList() {
		return this.fileList;
	}
	
	//add the element x to this.fileList
	public void addToList(String x) {
		
		if(this.fileList.contains(x));
			
		else
			this.fileList.add(x);
	}
	
	//override toString method for the Word object class
	@Override
	public String toString() {
		return this.text+"";
	}
	

	/*
	 * override compareTo method for the Word object class
	 * This works by comparing each character in the word to the text
	 * and only returns 0 if the words are completely equal.
	 */
	@Override
	public int compareTo(Word o) {
		
		if(this.text.equals(o.text))
			return 0;
		
		for(int i = 0; i < this.text.length(); i ++) {
			
			char ch = o.text.charAt(i);
			
			if(Character.isDigit(ch) == true) {
				
				if(ch == text.charAt(i));
				
				else if(ch < text.charAt(i))
					return -1;
				
				else
					return 1;
			}
			
			else if(Character.isLetter(ch)) {
				
				if(ch == text.charAt(i));
				
				else if(ch < text.charAt(i))
					return -1;
				
				else
					return 1;
			}
		}
		return 0;
	}	
}

	
	
