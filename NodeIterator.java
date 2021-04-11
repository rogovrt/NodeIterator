package com.rogovrt;

import java.util.*;

class Node {
    public int value; // unique
    public Collection<Node> siblings; // collection of nodes such that for each sibling in colection edge between this node and sibling exists
    public Iterator<Node> iterator;
    public Node previous;

    public Node (int value, Collection<Node> siblings) {
        this.value = value;
        this.siblings = siblings;
        this.iterator = siblings.iterator();
    }

    public String toString() {
        return Integer.toString(value);
    }
}

class NodeIterator implements Iterator<Node> {
    private Node current;
    private boolean isRootAcieved = false;
    private boolean isRootPrinted = false;

    public NodeIterator(Node start) {
        current = start;
        current.previous = null;
    }

    @Override
    public boolean hasNext() {
        if (isRootAcieved)
            return false;
        if (current.iterator.hasNext()) {
            Node t = current;
            current = current.iterator.next();
            current.previous = t;
            return true;
        }
        else {
            while (current.previous != null) {
                if (current.previous.iterator.hasNext()) {
                    Node t = current.previous;
                    current = current.previous.iterator.next();
                    current.previous = t;
                    return true;
                }
                else {
                    current = current.previous;
                }
            }
            isRootAcieved = true;
            return true;
        }
    }

    @Override
    public Node next() {
        if (!isRootPrinted || !isRootAcieved) {
            if (isRootAcieved)
                isRootPrinted = true;
            return current;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {

        Node start = new Node(1, Arrays.asList(
                new Node(2, Collections.emptyList()),
                new Node(3, Collections.singleton(
                        new Node(4, Collections.emptyList()))
                )
        ));

        NodeIterator iterator = new NodeIterator(start);
        // should print numbers 1-4 in any order
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
