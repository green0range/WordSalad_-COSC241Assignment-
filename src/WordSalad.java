/* File: WordSalad.java - April 2018 */
package week09;

import java.util.Arrays;
import java.util.*;

/**
 *  Skeleton implementation of the WordSalad class.
 *
 *  @author Michael Albert
 */
public class WordSalad implements Iterable<String> {

    private WordNode first;
    private WordNode last;
     
    public WordSalad() {
        this.first = null;
        this.last = null;
    }

    public WordSalad(java.util.List<String> words) {
        for (String word : words) {
            addLast(word);
        }
    }

    public void add(String word) {
        if (this.first == null) {
            this.first = new WordNode(word, null);
            this.last = this.first;
            return;
        }
        WordNode newFirst = new WordNode(word, this.first);
        this.first = newFirst;
    }

    public void addLast(String word) {
        if (this.first == null) {
            add(word);
            return;
        }
        WordNode newLast = new WordNode(word, null);
        this.last.next = newLast;
        this.last = newLast; 
    }
  
    private class WordNode {
        private String word;
        private WordNode next;
                
        private WordNode(String word, WordNode next) {
            this.word = word;
            this.next = next;
        }
        
    }
  
    public java.util.Iterator<String> iterator() {
        return new java.util.Iterator<String>() {
            private WordNode current = first;
      
            public boolean hasNext() {
                return current != null;
            }
      
            public String next() {
                String result = current.word;
                current = current.next;
                return result;
            }
      
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
                
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        WordNode node = first;
        while (node != null) {
            result.append(node.word);
            result.append(node.next == null ? "" : ", ");
            node = node.next;
        }
        return result.toString() + "]";
    }


    // Method stubs to be completed for the assignment.
    // See the assignment description for specification of their behaviour.

    /**
     * Distributes the words into k blocks similar to card dealing.
     * @param k the number of blocks.
     * @return WordSalad[] array of split WordSalads.
     */
    public WordSalad[] distribute(int k) {
        int i = 0;
        WordSalad[] hands = new WordSalad[k];
        for (int j=0; j<k; j++){
            hands[j] = new WordSalad();
        }
        for (String s:this){
            if (s != null){
                hands[i].addLast(s);
            }
            if(i>=(k-1)){i=0;}else{i++;} // iterate over k
        }
        return hands;
        //I will start on this one ~ William
    }
        
    public WordSalad[] chop(int k) {

	int i = 0;
	WordNode curr = first;
	while(curr!=null){
	    curr=curr.next;
	    i++;
	}

	int ea = i/k;
	int rem = i%k;
	int m = 0;
	int z = 1;
	
	WordSalad[] cell = new WordSalad[k];
	for(int j = 0;j<k;j++){
	    cell[j] = new WordSalad();
	}

        for(String s: this){
	    if(s != null){
		cell[m].addLast(s);
	    }
	    if(z<ea+(m<rem?1:0)){
		z++;
	    }else{
		z = 1;
		m++;
	    }
	}
	return cell;
    }
        
    public WordSalad[] split(int k) {
	//count the number of words given
	int count = 0;
	for(String s:this){
	    count++;
	}
	
	//Count how many WordSalads there will be
	int saladCount = 0;
	while(count>0){
	    int checker = (int) Math.ceil((double)count/k);
	    saladCount++;
	    count-=checker;
	}

	WordSalad[] newCell = new WordSalad[saladCount];
	for(int p = 0;p<saladCount;p++){
	    newCell[p] = new WordSalad();
	}

	int q = 0;//which WordSalad block to add to

	//add all of the words to an arraylist
	List<String> wordz = new ArrayList<String>();
	for(String s:this){
	    wordz.add(s);
	}

	while(q<saladCount){
	    List<String> finalArr = new ArrayList<String>();
	    finalArr = addExtractor(wordz, k);
	    for(String s:finalArr){
		newCell[q].addLast(s);//add each word the the current WordSalad
	       	wordz.remove(s);//remove used words from original ArrayList
	  }
	    q++;//move to the next WordSalad
	}
	return newCell;
    }

    /**
     * Takes an arraylist, creates a new ArrayList and stores in it the words required.
     * @param arrList the ArrayList passed with the words required.
     * @param jump an int by which to iterate over the passed ArrayList with.
     * @return removed an ArrayList of the words extracted from the passed ArrayList.
     */
    public List<String> addExtractor(List<String> arrList, int jump){
	List<String> extracted = new ArrayList<String>();
	int arrSize = arrList.size();//size of passed arraylist
	int sizeChecker = 0;
	while(arrSize > sizeChecker){
	    extracted.add(arrList.get(sizeChecker));//add required word
	    sizeChecker += jump;//iterate by k
	}
	return extracted;//return arraylist of words
    }
        
    public static WordSalad merge(WordSalad[] blocks) {
	return null;
    }
        
    public static WordSalad join(WordSalad[] blocks) {
        WordSalad w = new WordSalad();
	for(WordSalad block: blocks){
	    for(String s:block){
		w.addLast(s);
	    }
	}
	return w;
    }
    public static WordSalad recombine(WordSalad[] blocks, int k) {
	return null;
    }

}
