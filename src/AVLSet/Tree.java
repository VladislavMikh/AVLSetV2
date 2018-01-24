package AVLSet;

import java.util.NoSuchElementException;

public class Tree<T extends Comparable<T>> {

    private Node<T> root;
    private int size = 0;
    private boolean wasChanged = false;

    public int getSize() {
        return size;
    }

   /* public int count(T from, T to, boolean withHigher) {
        int count = 0;
        if (contains(root, from)) count++;
        T current = next(from);
        if (current == null) return 0;
        do {
            current = next(current);
            count++;
        }
        while (current != null && current.compareTo(to) < 0);
        return count + ((withHigher) ? 1 : 0);
    }
    */

    // малый левый поворот от указанного узла
    private Node<T> turnLeft(Node<T> n) {
        Node<T> right = n.right;
        n.right = right.left;
        right.left = n;
        n.recount();
        right.recount();
        return right;
    }

    // малый правый поворот от указанного узла
    private Node<T> turnRight(Node<T> n) {
        Node<T> left = n.left;
        n.left = left.right;
        left.right = n;
        n.recount();
        left.recount();
        return left;
    }

    // выполнение балансировки дерева. вызывается после добавления/удаления узла
    private Node<T> balance(Node<T> n) {
        n.recount();
        if (n.bFactor() == 2) {
            if (n.right.bFactor() < 0)
                n.right = turnRight(n.right);
            return turnLeft(n);
        }
        if (n.bFactor() == -2) {
            if (n.left.bFactor() > 0)
                n.left = turnLeft(n.left);
            return turnRight(n);
        }
        return n;
    }

    // поиск min эл-та в левом поддереве, если левого поддерева нет, вернуть сам узел
    private Node<T> minimum(Node<T> n) {
        if (n.left == null) return n;
        else return minimum(n.left);
    }

    // поиск max эл-та в правом поддереве, если правого поддерева нет, вернуть сам узел
    private Node<T> maximum(Node<T> n) {
        if (n.right == null) return n;
        else return maximum(n.right);
    }

    //
    private Node<T> nextNode(Node<T> n, Node<T> prev, T key) {
        int comparison = key.compareTo(n.key);
        Node<T> closest = (comparison < 0 && n.key.compareTo(prev.key) < 0) ? n : prev;
        if (comparison < 0 && n.left != null) {
            closest = nextNode(n.left, closest, key);
        } else if (comparison > 0 && n.right != null) {
            closest = nextNode(n.right, closest, key);
        } else if (comparison == 0) {
            if (n.right != null) {
                return minimum(n.right);
            }
        }
        return closest;
    }

    private Node<T> prevBefore(Node<T> n, Node<T> prev, T key) {
        int comparison = key.compareTo(n.key);
        Node<T> closest = (comparison > 0 && n.key.compareTo(prev.key) > 0) ? n : prev;
        if (comparison < 0 && n.left != null) {
            closest = prevBefore(n.left, closest, key);
        } else if (comparison > 0 && n.right != null) {
            closest = prevBefore(n.right, closest, key);
        } else if (comparison == 0) {
            if (n.left != null) {
                return maximum(n.left);
            }
        }
        return closest;
    }

    // поиск указанного ключа в поддереве от указанного узла
    private boolean contains(Node<T> n, T key) {
        if (n == null) return false;
        int comparison =  key.compareTo(n.key);
        if (comparison < 0) {
            return contains(n.left, key);
        }
        else return comparison == 0 || contains(n.right, key);
    }

    // вставка узла в дерево по ключу и значению
    private Node<T> insert(Node<T> n, T key) {
        int comparison = key.compareTo(n.key);
        if (comparison < 0) {
            if (n.left != null) {
                n.left = insert(n.left, key);
            } else {
                n.left = new Node<T>(key);
                size++;
                wasChanged = true;
            }
        }
        if (comparison > 0) {
            if (n.right != null) {
                n.right = insert(n.right, key);
            } else {
                n.right = new Node<T>(key);
                size++;
                wasChanged = true;
            }
        }
        return balance(n);
    }

    // удаление min эл-та
    private Node<T> removeMinimum(Node<T> n) {
        if (n.left == null)
            return n.right;
        n.left = removeMinimum(n.left);
        return balance(n);
    }

    // удаление max эл-та,
    private Node<T> remove(Node<T> n, T key) {
        int comparison = key.compareTo(n.key);
        if (comparison < 0 && n.left != null) {
            n.left = remove(n.left, key);
        } else if (comparison > 0 && n.right != null) {
            n.right = remove(n.right, key);
        } else if (comparison == 0) {
            size--;
            wasChanged = true;
            if (n.right == null) return n.left;
            Node<T> swap = minimum(n.right);
            swap.right = removeMinimum(n.right);
            swap.left = n.left;
            return balance(swap);
        }
        return n;
    }

    // вставка нового ключа
    public void insert(T key) {
        if (root == null){
            root = new Node<T>(key);
            size++;
            wasChanged = true;
        }
        else
            root = insert(root, key);
    }

    public void clear() {
        root =null;
        size =0;
    }

    public void remove(T key) {
        root = (root != null) ? remove(root, key) : null;
    }

    public boolean contains(T key) {
        return (root != null && contains(root, key));
    }

    // поиск левого эл-та
    public T first() {
        if (root == null) throw new NoSuchElementException();
        return minimum(root).key;
    }

    // поиск последнего эл-та
    public T last() {
        if (root == null) throw new NoSuchElementException();
        return maximum(root).key;
    }

    public T next(T key) {
        if (root == null) return null;
        T next = nextNode(root, maximum(root), key).key;
        return (next.compareTo(key) > 0 ) ? next : null;
    }

    public T prev(T key) {
        if (root == null) return null;
        T prev = prevBefore(root, minimum(root), key).key;
        return (prev.compareTo(key) < 0) ? prev : null;
    }

    // вспомогательная ф-я. проверяет флаг wasChanged. Если он был, вернет true. иначе false.
    public boolean is_modified() {
        if (wasChanged) {
            wasChanged = false;
            return  true;
        }
        return false;
    }
}
