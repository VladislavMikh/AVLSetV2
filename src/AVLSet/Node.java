package AVLSet;

class Node<T extends Comparable<T>> {

    T key; // значение, которое хранит в себе узел
    int height; // высота поддерева с корнем в данном узле
    Node<T> left; // ссылка на левого потомка
    Node<T> right; // сслыка на правого потомка

    // конструктор узла
    Node (T key) {
        this.key = key;
        height = 1;
    }

    // геттер, возвращает высоту поддерева с вершиной в узле, от которого вызвали геттер
    int getHeight() {
        return height;
    }

    void recount() {
        // Пересчитываем значение высоты поддерева от текущего узла
        int leftHeight = (left != null) ? left.getHeight() : 0; // высота поддерева от левого потомка, если он есть
        int rightHeight = (right != null) ? right.getHeight() : 0; // высота поддерева от правого потомка, если он есть
        this.height = ((leftHeight > rightHeight) ? leftHeight : rightHeight) + 1; // max высота поддерева потомка + данный узел
    }

    int bFactor() {
        // Считаем разницу высот поддеревьев относительно правого поддрева
        int leftHeight = (left != null) ? left.getHeight() : 0;
        int rightHeight = (right != null) ? right.getHeight() : 0;
        return rightHeight - leftHeight;
    }
}