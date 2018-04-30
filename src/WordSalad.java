/* File: WordSalad.java - April 2018 */
package week09;

/**
 *  Implementation of the WordSalad class.
 *  
 *  Github repository: https://is.gd/MkkXL2
 *
 *  !! Comment the methods you have completed !!
 *  @author Michael Albert (Skeleton implementation)
 *  @author Jack Baucke
 *  @author Anaru Hudson (chop and join)
 *  @author William Satterthwaite (distribute and merge)
 */
public class WordSalad implements Iterable<String> {

    private WordNode first;
    private WordNode last;
    private int size; // I have added a record of the size such that merge can use it.
     
    public WordSalad() {
        this.first = null;
        this.last = null;
        size = 0;
    }

    public WordSalad(java.util.List<String> words) {
        for (String word : words) {
            addLast(word);
            size++;
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
        size++;
    }

    public void addLast(String word) {
        if (this.first == null) {
            add(word);
            return;
        }
        WordNode newLast = new WordNode(word, null);
        this.last.next = newLast;
        this.last = newLast;
        size++;
    }

    public int getSize(){
        return size;
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
     * Distributes words into k blocks as if dealing cards.
     * @param k the number of blocks.
     * @return WordSalad[] array of split WordSalads.
     */
    public WordSalad[] distribute(int k){
	// Uses distribute with a subset equal to the entire WordSalad.
	return distribute(k, this.first, this.last);
    }
    
    /**
     * Returns the size of the WordSalad.
     * @return size
     */
    public int getSize(){
        return size;
    }
    
    /**
     * Distributes words into k blocks as if dealing cards.
     * @param k the number of blocks.
     * @param start the first in the subset.
     * @param end the last in the subset.
     * @return WordSalad[] array of split WordSalads.
     */
    public WordSalad[] distribute(int k){
        // Uses distribute with a subset equal to the entire WordSalad.
        return distribute(k, this.first, this.last);
    }
    
    /**
     * Distributes a subset of words into k blocks as if dealing cards.
     * @param k the number of blocks.
     * @param start the first in the subset.
     * @param end the last in the subset.
     * @return WordSalad[] array of split WordSalads.
     */
    private WordSalad[] distribute(int k, WordNode start, WordNode end) {
        int i = 0;
        WordSalad[] hands = new WordSalad[k];
        for (int j=0; j<k; j++){
            hands[j] = new WordSalad();
        }
        WordNode pointer = start;
        String s;
        s = pointer.word;
        if (s != null){
            hands[i].addLast(s);
        }
        if(i>=(k-1)){i=0;}else{i++;}
        while (pointer.next != null || pointer != end){
            pointer = pointer.next;
            s = pointer.word;
            if (s != null){
                hands[i].addLast(s);
            }
            if(i>=(k-1)){i=0;}else{i++;} // iterate over k
        }
        return hands;
    }

    
    /**
* Distributes the words into k nearly even length blocks.
     * @param k the number of blocks.
     * @return cell is a WordSalad[] array of split words.
     */
    public WordSalad[] chop(int k) {
        int i = 0;
        WordNode curr = first;
        while(curr!=null){
            curr=curr.next;
            i++;
        }

        //how many words each WordSalad should have
        int each = i/k;
        //remainder of words after each WordSalad is of equal length
        int remainder = i%k;
        int a = 0;
        int b = 1;
        
        WordSalad[] cell = new WordSalad[k];
        for(int j = 0;j<k;j++){
            cell[j] = new WordSalad();
        }

        for(String s: this){
            if(s != null){
                cell[a].addLast(s);
            }
            if(b<each+(a<remainder?1:0)){
                b++;
            }else{
                b = 1;
                a++;
            }
        }
        return cell;
    }
    
    public WordSalad[] split(int k) {
        int numWords = 0;
        for (String s : this) {
            numWords++;
        }
        int numBlocks = 0;
        while (numWords > 0) {
            int division = (numWords / k);
            if ((numWords % k) != 0) {
                division++;
            }
            numWords -= division;
            numBlocks++;
        }
        WordSalad[] blocks = new WordSalad[numBlocks];        
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new WordSalad();
        }        
        WordNode pointer = this.first;           
        while (pointer.next != null) {            
            for (int i = 0; i < k; i++) {
                blocks[i].addLast(pointer.word);
                if (pointer.next != null) {
                    pointer = pointer.next;
                } else {
                    return blocks;
                }
            }
            k++;            
        }
        return blocks;
    }

    /** 
     * Reverses the distribute method.
     * @param blocks Array of wordSalads to join.
     * @return single joined WordSalad.
     */
    public static WordSalad merge(WordSalad[] blocks) {
        WordSalad w = new WordSalad();
        int k = blocks[0].getSize();
        for (int j=0; j<=k; j++){
            for (int i=0; i<blocks.length; i++){
                // adds the j'th index to w
                WordNode current = blocks[i].first;
                // to deal with uneven distributes.
                boolean skip = false;
                for (int a=0; a<j; a++){
                    if (current.next != null) current = current.next;
                    else skip = true;
                }
                if (!skip) w.addLast(current.word);
            }
        }
        return w;
    }

    /**
     * Rejoins a sequence of blocks one after the other.
     * @param blocks the blocks of words that are to be rejoined.
     * @return w the result of rejoining the blocks into one WordSalad.
     */    
    public static WordSalad join(WordSalad[] blocks) {
        WordSalad w = new WordSalad();
        for(WordSalad block: blocks){
            for(String s: block){
                w.addLast(s);
            }
        }
        return w;       
    }

    public static WordSalad recombine(WordSalad[] blocks, int k) {
        WordSalad w = new WordSalad();
        return w;
    }

}
