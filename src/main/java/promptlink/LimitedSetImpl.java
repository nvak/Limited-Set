package promptlink;

import java.util.Objects;

public class LimitedSetImpl<T> implements LimitedSet<T> {
    private static final int MAX_CAPACITY = 10;
    private Node<T>[] elements = new Node[MAX_CAPACITY];
    private int elementsInArray;

    public LimitedSetImpl() {
    }

    @Override
    public void add(T elementToAdd) {
        if (elementsInArray < MAX_CAPACITY) {
            elements[elementsInArray] = new Node<>(elementToAdd);
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
            elements[pos].setCount(0);
        }
    }

    @Override
    public boolean remove(T elementToRemove) {
        int index = 0;
        for (int i = 0; i < MAX_CAPACITY; i++) {
            if (Objects.equals(elements[i].getValue(), elementToRemove)) {
                index = i;
                break;
            }
        }
        Node<T>[] tail = getTail(elements, index);
        Node<T>[] tempArray = new Node[MAX_CAPACITY];
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
        for (Node<T> element : elements) {
            if (Objects.equals(element.getValue(), t)) {
                element.setCount(element.getCount() + 1);
                return true;
            }
        }
        return false;
    }

    private Node<T>[] getTail(Node<T>[] elements, int cutPosition) {
        Node<T>[] tail = new Node[elementsInArray - cutPosition];
        if (elementsInArray - cutPosition >= 0) {
            System.arraycopy(elements, cutPosition, tail, 0, elementsInArray - cutPosition);
        }
        return tail;
    }

    private static class Node<T> {
        private T value;
        private int count;

        public Node(T value) {
            this.value = value;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }


        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
