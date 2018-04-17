/* File: WordSalad.java - April 2018 */
package week09;

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
        return null;
    }
        
    public WordSalad[] split(int k) {
        return null;
    }
        
    public static WordSalad merge(WordSalad[] blocks) {
        return null;
    }
        
    public static WordSalad join(WordSalad[] blocks) {
        return null;
    }

    public static WordSalad recombine(WordSalad[] blocks, int k) {
        return null;
    }

}
