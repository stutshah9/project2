import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import student.TestableRandom;

/**
 * This class implements SkipList data structure with KVPairs, with
 * functionality to delete, and search for a given key in the list, as well as
 * removing by value. It also contains an Iterator and an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author Stuti and Jaydon
 * 
 * @version 2024-02-17
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipList<K extends Comparable<? super K>, V>
    implements Iterable<KVPair<K, V>> {

    private SkipNode head; // First element (Sentinel Node)
    private int size; // number of entries in the Skip List

    /**
     * Initializes the fields head (and its level) and size
     * This sets up our sentinel node for further use
     */
    public SkipList() {
        // Initialization of null and level 0
        head = new SkipNode(null, 0);
        size = 0;
    }


    /**
     * Returns a random level number which is used as the depth of the SkipNode.
     * This is done by randomly generating integers until the integer is odd.
     * 
     * @return a random level number
     */
    int randomLevel() {
        int lev;
        TestableRandom value = new TestableRandom();
        for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
            // Do nothing
        }
        return lev; // returns a random level
    }


    /**
     * Searches for all KVPairs using the key which is a Comparable object.
     * This goes through the entire list until it finds the value, or lack
     * thereof, and then moves down levels until it finds the very first value
     * to occur within the skiplist. It then iterates through all of the values
     * that may occur
     * 
     * @param key
     *            key to be searched for
     * @return an array list
     *         All of the found values with said key
     */
    public ArrayList<KVPair<K, V>> search(K key) {
        // Setting up an iteration node at head, a list of values with our key,
        // and a level iterator starting at the highest level
        SkipNode searchIter = head;
        ArrayList<KVPair<K, V>> foundValues = new ArrayList<>();
        int level = head.level();

        // Runs while the level is 0 or above. We cannot end at level 0 since we
        // may still be searching (say the list is entirely level 0), hence we
        // end at level -1 (which is not valid, but indicates we have found the
        // first value at the lowest level or did not find the value at all)
        while (level >= 0) {

            // Iterates through the list while the keys are less than the key we
            // are looking for and we still have values to go over (next
            // is not null)
            while (searchIter.forward()[level] != null && searchIter
                .forward()[level].element().getKey().compareTo(key) < 0) {
                searchIter = searchIter.forward()[level];
            }
            level--;
        }

        // Moves the level back up to zero, as it is negative due to the
        // searching logic explained above, and iterates forward to either the
        // first value that has our key or is null
        level++;
        searchIter = searchIter.forward()[level];

        // If the next value is not null and is equal to our key, saves it to
        // our list and then moves on forawrd to the next node, eventually
        // saving all of keys we are looking for to our list
        while (searchIter != null && searchIter.element().getKey().compareTo(
            key) == 0) {
            foundValues.add(searchIter.element());
            searchIter = searchIter.forward()[level];
        }

        // Returns the filled up list
        return foundValues;
    }


    /**
     * Getter method for the size of the SkipList
     * 
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }


    /**
     * Inserts the KVPair in the SkipList at its appropriate spot as designated
     * by its comparable order. This is done by finding it's appropriate place
     * on each level, locally inserting it on that level, and then moving up all
     * the way to the insertLevel.
     * 
     * @param it
     *            the KVPair to be inserted
     */
    public void insert(KVPair<K, V> it) {

        // Sets up the insert level and adjusts the head accordingly and then
        // sets up an insertion node and an iteration node
        int insertLevel = randomLevel();
        if (insertLevel > head.level()) {
            adjustHead(insertLevel);
        }
        SkipNode insertNode = new SkipNode(it, insertLevel);
        SkipNode iter;

        // For each level, inserts the node at the correct place and iterates
        // the level up until the insertLevel.
        for (int level = 0; level <= insertLevel; level++) {

            // Starts at the head, moves forward until the first available
            // insertion place on the current level, and then inserts the node
            // at said insertion place
            iter = head;
            while (iter.forward()[level] != null && iter.forward()[level]
                .element().getKey().compareTo(it.getKey()) < 0) {
                iter = iter.forward()[level];
            }

            // insertion
            insertNode.forward()[level] = iter.forward()[level];
            iter.forward()[level] = insertNode;
        }

        // Adjusts size as needed
        size++;

    }


    /**
     * Increases the number of levels in head so that no element has more
     * indices than the head.
     * 
     * @param newLevel
     *            the number of levels to be added to head
     */
    public void adjustHead(int newLevel) {

        // Sets up a new sentinel node, and then sets every index to its
        // respective index in head, but leaving new indices empty
        SkipNode newHead = new SkipNode(null, newLevel);
        for (int iter = 0; iter <= head.level(); iter++) {
            newHead.forward()[iter] = head.forward()[iter];
        }

        // Sets head to temp
        head = newHead;
    }


    /**
     * Removes the KVPair using the keythat is passed in as a parameter and
     * returns true if the pair was in the list and false if not. This is done
     * by finding the first KVPair in the list that matches our key, and then
     * systematically removing it from each level the node is in. If we do not
     * find it, we return null
     * 
     * @param key
     *            the KVPair to be removed
     * @return returns the removed pair if the pair was in the list and null if
     *         not
     */

    public KVPair<K, V> remove(K key) {

        // Sets up necessary variables, such as an empty removed pair, a
        // nodeToRemove iteration node that starts at head, and a level that
        // starts at heads top level
        KVPair<K, V> removed = null;
        SkipNode nodeToRemove = head;
        int level = head.level();

        // Runs while the level is 0 or above. We cannot end at level 0 since we
        // may still be searching for the removed value (say the list is
        // entirely level 0), hence we end at level -1 (which is not valid, but
        // indicates we have found the first value at the lowest level or did
        // not find the value at all)
        //
        // I decided to remove the first value because I believe that it is
        // important to standardize our approach in cases of multiple values
        while (level >= 0) {

            // Iterates through the list while the keys are less than the key we
            // are looking to remove and we still have values to go over (next
            // is not null)
            while (nodeToRemove.forward()[level] != null && nodeToRemove
                .forward()[level].element().getKey().compareTo(key) < 0) {
                nodeToRemove = nodeToRemove.forward()[level];
            }

            level--;
        }

        // If the next value at the lowest level is null or is greater than the
        // value we are looking to remove, return null (clearly not present, or
        // it would be next)
        if (nodeToRemove.forward()[0] == null || nodeToRemove.forward()[0]
            .element().getKey().compareTo(key) != 0)
            return removed;

        // Moves forward to the first value at the lowest level, and saves that
        // KVPair into removed
        nodeToRemove = nodeToRemove.forward()[0];
        removed = nodeToRemove.element();

        // Sets up another iteration for removal of the first value at the
        // lowest level, bringing the level back up to the highest level our
        // nodeToRemove is found and starting another iterator at head
        level = nodeToRemove.level();
        SkipNode updateIter = head;

        // Go through each level from the highest level nodeToRemove is
        // found to the lowest, find said node, and then update the list as
        // needed. This is done until we reach level zero
        while (level >= 0) {

            // Until we find the node to remove, we continue moving forward in
            // the list. Since we are starting at the highest level of the
            // nodeToRemove, it is guaranteed that we will find said node along
            // the path to the end if the skiplist is set up correctly.
            while (updateIter.forward()[level] != nodeToRemove) {
                updateIter = updateIter.forward()[level];
            }

            // Removes the node from the list and iterates downwards in the
            // list. We don't have to restart since it's guaranteed that in
            // lower levels the node will remain ahead of the current node
            updateIter.forward()[level] = updateIter.forward()[level]
                .forward()[level];
            level--;
        }

        // Decreases the size as needed and returns the removed KVPair
        size--;
        return removed;
    }


    /**
     * Removes the KVPair using the value that is passed in as a parameter and
     * returns true if the pair was in the list and false if not. This is done
     * by finding the first KVPair in the list that matches our value, and then
     * systematically removing it from each level the node is in. If we do not
     * find it, we return null
     * 
     * @param val
     *            the value of the KVPair to be removed
     * @return returns KVPair<K, V> if the removal was successful, null
     *         otherwise
     */
    public KVPair<K, V> removeByValue(V val) {

        // Sets up various variables for the removal, such as a KVPair to hold
        // the removed value, and an iteration node that starts at head
        KVPair<K, V> removed = null;
        SkipNode nodeToRemove = head;

        // Since we are removing by value, we cannot speed up the process with
        // our sorted algorithm. As such, we start at the lowest level and keep
        // moving forward until the next value is null
        while (nodeToRemove.forward()[0] != null) {

            // Iterates the node forward and compares the value stored in the
            // node to the value we are searching to remove. If we have found
            // it, we save the value to remove and break the loop, saving the
            // node we are at
            nodeToRemove = nodeToRemove.forward()[0];
            if (nodeToRemove.element().getValue().equals(val)) {
                removed = nodeToRemove.element();
                break;
            }
        }

        // If we couldn't find the node we are searching for, return null
        if (removed == null) {
            return removed;
        }

        // Sets up necessary variables for our update iteration, such as
        // starting at the first level our nodeToRemove is and starting our
        // update iteration variable at head
        int level = nodeToRemove.level();
        SkipNode updateIter = head;

        // Go through each level from the highest level nodeToRemove is
        // found to the lowest, find said node, and then update the list as
        // needed. This is done until we reach level zero
        while (level >= 0) {

            // Until we find the node to remove, we continue moving forward in
            // the list. Since we are starting at the highest level of the
            // nodeToRemove, it is guaranteed that we will find said node along
            // the path to the end if the skiplist is set up correctly.
            while (updateIter.forward()[level] != nodeToRemove) {
                updateIter = updateIter.forward()[level];
            }

            // Removes the node from the list and iterates downwards in the
            // list. We don't have to restart since it's guaranteed that in
            // lower levels the node will remain ahead of the current node
            updateIter.forward()[level] = updateIter.forward()[level]
                .forward()[level];
            level--;
        }

        // Decreases the size as needed and returns the removed KVPair
        size--;
        return removed;
    }


    /**
     * Prints out the SkipList in a human readable format to the console, going
     * through each node at the lowest level and printing its highest level and
     * KVPair. Also prints the total list size.
     */
    public void dump() {

        // Start message
        System.out.println("SkipList dump:");

        // Sets an iteration node, goes through each node and prints its depth
        // and the KVPair its holding
        SkipNode dumpIterator = head;
        while (dumpIterator != null) {
            if (dumpIterator.element() != null)
                System.out.println("Node has depth "
                    + dumpIterator.forward.length + ", Value " + dumpIterator
                        .element().getValue());
            else
                System.out.println("Node has depth "
                    + dumpIterator.forward.length + ", Value (null)");
            dumpIterator = dumpIterator.forward()[0];
        }

        // End message
        System.out.println("SkipList size is: " + size);

    }

    /**
     * This class implements a SkipNode for the SkipList data structure.
     * Each skipNode contains a pair, an integer representing the depth of
     * the node, and an array of references to subsequent skipNodes.
     * 
     * @author CS Staff
     * 
     * @version 2016-01-30
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // An array of pointers to subsequent nodes
        private SkipNode[] forward;
        // the level of the node
        private int level;

        /**
         * Initializes the fields with the required KVPair and the number of
         * levels from the random level method in the SkipList. Initializes the
         * array as well to an empty array of level + 1
         * 
         * @param tempPair
         *            the KVPair to be inserted
         * @param level
         *            the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,
                level + 1);
            this.level = level;
        }


        /**
         * Returns the KVPair stored in the SkipNode.
         * 
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }


        /**
         * Returns the int level stored in the SkipNode.
         * 
         * @return the level
         */
        public int level() {
            return level;
        }


        /**
         * Returns the array of forward SkipNodes stored in the SkipNode.
         * 
         * @return the array of forward SkipNodes
         */
        public SkipNode[] forward() {
            return forward;
        }

    }


    /**
     * This class implements a SkipListIterator for the SkipList data structure.
     * This ensures easy and fast iteration through the skipList and easier
     * testing for order and containment of values
     * 
     * @author CS Staff
     * 
     * @version 2016-01-30
     */
    private class SkipListIterator implements Iterator<KVPair<K, V>> {

        // The current SkipNode in our iterator, initially set to head
        private SkipNode current;

        /**
         * Initializes our current SkipNode to the head of the skipList
         */
        public SkipListIterator() {
            current = head;
        }


        /**
         * Returns whether the next node is null based on what is next in the
         * SkipNode.
         * 
         * @return true if there is a next node, false otherwise
         */
        public boolean hasNext() {
            return current.forward[0] != null;
        }


        /**
         * Returns the KVPair that is next in the SkipList by iterating forward
         * to the next node in the list and then returning its element
         * 
         * @return the KVPair that is next in the SkipList
         */
        public KVPair<K, V> next() {
            KVPair<K, V> elem = current.forward[0].element();
            current = current.forward[0];
            return elem;
        }

    }

    /**
     * Returns a SkipListIterator to go through each value in the skiplist
     * 
     * @return a SkipListIterator to go through each value in the list
     */
    public Iterator<KVPair<K, V>> iterator() {
        return new SkipListIterator();
    }

}
