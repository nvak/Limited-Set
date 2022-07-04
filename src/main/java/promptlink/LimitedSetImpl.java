package promptlink;

import java.util.NoSuchElementException;
import java.util.Objects;

public class LimitedSetImpl<T> implements LimitedSet<T> {
    private static final int MAX_CAPACITY = 10;
    private static final int ZERO_COUNTER = 10;
    private Node[] elements;
    private int elementsInArray;

    public LimitedSetImpl() {
        elements = new Node[MAX_CAPACITY];
    }

    @Override
    public void add(T elementToAdd) {
        if (elementsInArray < MAX_CAPACITY) {
            elements[elementsInArray] = new Node(elementToAdd);
            elementsInArray++;
        } else {
            int min = Integer.MAX_VALUE;
            int pos = 0;
            for (int i = 0; i < MAX_CAPACITY; i++) {
                if (elements[i].getCount() < min) {
                    min = elements[i].getCount();
                    pos = i;
                }
            }
            elements[pos].setValue(elementToAdd);
            elements[pos].setCount(ZERO_COUNTER);
        }
    }

    @Override
    public boolean remove(T elementToRemove) {
        Object remove = null;
        int index = 0;
        for (int i = 0; i < elementsInArray; i++) {
            if (Objects.equals(elements[i].getValue(), elementToRemove)) {
                remove = elements[i].getValue();
                index = i;
                break;
            }
        }

        if (remove == null) {
            throw new NoSuchElementException("No such element in set");
        }

        Node[] tail = getTail(elements, index);
        Node[] tempArray = new Node[MAX_CAPACITY];

        System.arraycopy(elements, 0, tempArray, 0, index);

        if (tail.length > 0) {
            System.arraycopy(tail, 1, tempArray, index, tail.length - 1);
        }

        elements = tempArray;
        elementsInArray--;
        return true;
    }

    @Override
    public boolean contains(T t) {
        for (Node element : elements) {
            if (Objects.equals(element.getValue(), t)) {
                element.setCount(element.getCount() + 1);
                return true;
            }
        }
        return false;
    }

    private Node[] getTail(Node[] elements, int cutPosition) {
        Node[] tail = new Node[elementsInArray - cutPosition];
        if (elementsInArray - cutPosition >= 0) {
            System.arraycopy(elements, cutPosition, tail, 0, elementsInArray - cutPosition);
        }
        return tail;
    }

    private static class Node {
        private Object value;
        private int count;

        public Node(Object value) {
            this.value = value;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
