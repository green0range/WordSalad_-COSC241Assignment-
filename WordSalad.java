/* File: WordSalad.java - April 2018 */
package week09;

import java.util.*;

/**
 *  Skeleton implementation of the WordSalad class.
 *
 *  @author Michael Albert
 */
public class WordSalad implements Iterable<String> {

    /**
     * Data field for first
     * Node in a WordSalad.
     */
    private WordNode first;

    /**
     * Data field for last
     * Node in a WordSalad.
     */
    private WordNode last;

    /**
     * Data field for determining
     * size of WordSalad to aid
     * with merge method.
     */
    private int size;

    /**
     * Default constructor for
     * WordSalad. Sets first
     * and last to null and
     * size to 0.
     */
    public WordSalad() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * Adds each word in a list
     * of words into the WordSalad.
     * @param words list of words being added
     */
    public WordSalad(java.util.List<String> words) {
        for (String word : words) {
            addLast(word);
            size++;
        }
    }

    /**
     * Adds word to front
     * of WordSalad.
     * @param word the String being added
     */
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

    /**
     * Adds word to end
     * of WorldSalad.
     * @param word the String being added
     */
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

    /**
     * Private class for
     * constructing WordNodes.
     */  
    private class WordNode {

        /**
         * Data field for single
         * word in WordSalad.
         */
        private String word;

        /**
         * Reference to next
         * WordNode in
         * WordSalad.
         */
        private WordNode next;

        /**
         * Constructs WordNode with a String
         * and a refernce to the next WordNode.
         * @param word The word being added
         * @param next reference to the next WordNode
         */
        private WordNode(String word, WordNode next) {
            this.word = word;
            this.next = next;
        }
        
    }

    /**
     * Takes words in WordSalad and
     * turns it into a String list.
     * @return String list of each item
     * in WordSalad
     */
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

    /**
     * Returns String of all
     * the words in the WordSalad.
     * @return String consisting of
     * all the words in the WordSalad
     */
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

    /**
     * Returns the size of the wordSalad.
     * @return size int of size.
     */
    public int getSize() {
        return size;
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
        for (int j = 0; j < k; j++){
            hands[j] = new WordSalad();
        }
        for (String s : this) {
            if (s != null) {
                hands[i].addLast(s);
            }
            if (i >= (k - 1)) {
                i=0;
            } else {
                i++;
            } // iterate over k
        }
        return hands;
    }

    /**
     * Chops this into an array of k instances of WordSalad objects.
     * @param k an int of the number of WordSalad blocks.
     * @return cell an array of WordSalad objects.
     */
    public WordSalad[] chop(int k) {
        int i = 0;
        WordNode curr = first;
        while(curr != null){
            curr = curr.next;
            i++;
        }

        int ea = i/k;//how many words each WordSalad should have
        int rem = i%k;//remainder of words after each WordSalad is equal length
        int a = 0; //which object to add words to 
        int b = 1;
        
        WordSalad[] cell = new WordSalad[k];
        for(int j = 0;j<k;j++) {
            cell[j] = new WordSalad();
        }
        for(String s: this) {
            if(s != null) {
                cell[a].addLast(s);
            }
            if(b < ea + (a < rem ? 1 : 0)) {
                b++;
            } else {
                b = 1;
                a++;
            }
        }
        return cell;
    }

    /**
     * Splits this into an array of WordSalad objects.
     * @param k an int used to iterate through the words in this and find
     *        the number of WordSalad objects required.
     * @return splitArray a WordSalad[] array of split words.
     */
    public WordSalad[] split(int k) {
        //count the number of words given
        int count = 0;
        for (String s : this) {
            count++;
        }

        //count the number of WordSalad objects required
        int saladCount = 0;
        while(count>0){
            int checker = (int) Math.ceil((double) count / k);
            saladCount++;
            count -= checker;
        }
        WordSalad[] splitArray = new WordSalad[saladCount];
        for (int j = 0; j<saladCount; j++) {
            splitArray[j] = new WordSalad();
        }

        //adds all of the words in this into an ArrayList
        List<String> allWords = new ArrayList<String>();
        for(String s : this){
            allWords.add(s);
        }

        int i = 0; //current WordSalad object to add words to
        while(i<saladCount) {
            List<String> tempArr = new ArrayList<String>();
            tempArr = addExtractor(allWords, k);
            for (String s:tempArr) {
                splitArray[i].addLast(s);//add each word to current WordSalad
                allWords.remove(s);//remove used words from original ArrayList
            }
            i++;//move to the next WordSalad
        }
        return splitArray;
    }

    /**
     * Takes an ArrayList, creates a new
     * ArrayList and stores in it the words required.
     * @param arrList the ArrayList passed with the words required.
     * @param jump an int by which to iterate over the passed ArrayList with.
     * @return extracted an ArrayList of the
     * words extracted from the passed ArrayList.
     */
    public List<String> addExtractor(List<String> arrList, int jump) {
        List<String> extracted = new ArrayList<String>();
        int arrSize = arrList.size();//size of passed ArrayList
        int sizeChecker = 0;
        while (arrSize > sizeChecker) {
            extracted.add(arrList.get(sizeChecker));//add required word
            sizeChecker += jump;//iterate by k
        }
        return extracted;//return ArrayList of words
    }
        
    /** 
     * Reverses the distribute method.
     * @param blocks Array of wordSalads to join.
     * @return single joined WordSalad.
     */
    public static WordSalad merge(WordSalad[] blocks) {
        WordSalad w = new WordSalad();
        int k = blocks[0].getSize();
        for (int j=0; j<=k; j++) {
            for (int i=0; i<blocks.length; i++) {
                // adds the j'th index to w
                WordNode current = blocks[i].first;
                // to deal with uneven distributes.
                boolean skip = false;
                for (int a=0; a<j; a++){
                    if (current.next != null) {
                        current = current.next;
                    } else {
                        skip = true;
                    }
                }
                if (!skip) {
                    w.addLast(current.word);
                }
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
        for (WordSalad block: blocks) {
            for (String s:block){
                w.addLast(s);
            }
        }
        return w;
    }


    /** Recombines blocks that have been split.
     * @param blocks the blocks of words to recombined.
     * @param k the parameter used in there split.
     * @return the recombined wordSalad.
     */
    public static WordSalad recombine(WordSalad[] blocks, int k) {
        /* It expects the last two blocks to have one item each,
         * the only time this does not happen is split 1 */
        if (blocks.length == 1) {
            return blocks[0];
        }
        ArrayList<String> words = new ArrayList<>();
        for (int i=blocks.length-2; i>-1; i--) {
            /* Gives the intial starting point to add words around. */
            if (i == blocks.length-2) {
                words.add(blocks[i].first.word);
                words.add(blocks[i+1].first.word);
            } else {
                /* Adds blocks around the start according to k */
                int index = 0;
                for (String s:blocks[i]) {
                    words.add(index, s);
                    index += k;
                }
            }
        }
        WordSalad ws = new WordSalad(words);
        return ws;
    }
}
