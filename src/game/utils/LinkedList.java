package game.utils;

import game.Game;

public class LinkedList {
    // Instance Variables
    private Node head;
    public int length = 0;

    // Constructor
    public LinkedList() {
        head = null;
    }

    /*
     * Takes in an array and utilizes counting sort to create a new array with sorted values of the first array
     * Pre: Integer Array consisting of numbers that will get sorted
     */
    public void addToFront(Game data) {
        Node newNode = new Node(data);
        newNode.setNext(head);
        head = newNode;
        length++;
    }

    /*
     * Gets the first Node otherwise known as the head of the Linked List
     * Post: Returns an instance of a node
     */
    public Node getHead() {
        return head;
    }

    /*
     * Returns a string containing information about this instance of the object
     * Post: Returns a string
     */
    public String toString() {
        String string = "";
        Node node = head;

        if (node != null) {
            while (node != null) {
                string += String.format("%s => ", node);
                node = node.getNext();
            }
            string += "null";
        } else {
            return "No items in linked list.";
        }

        return string;
    }

    public static class Node {
        // Instance Variables
        private final Game data;
        private Node next;

        /*
         * Constructor
         * Pre: Integer Array consisting of numbers that will get sorted
         */
        public Node(Game data) {
            this.data = data;
            next = null;
        }

        /*
         * Returns the next Linked node
         * Post: Returns the next Node, either an instance of Node or null
         */
        public Node getNext() {
            return next;
        }

        /*
         * Sets the next Node otherwise known as the linked node
         * Pre: Takes in an instance of node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /*
         * Gets the game data located within the node
         * Post: Returns an instance of Game
         */
        public Game getData() {
            return data;
        }

        /*
         * Returns a string containing information about this instance of the object
         * Post: Returns a string
         */
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }
}
