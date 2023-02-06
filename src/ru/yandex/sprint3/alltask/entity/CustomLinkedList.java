package ru.yandex.sprint3.alltask.entity;

import ru.yandex.sprint3.alltask.manager.InMemoryHistoryManager;

public class CustomLinkedList {
    private InMemoryHistoryManager.Node<Task> first;
    private InMemoryHistoryManager.Node<Task> last;

    public InMemoryHistoryManager.Node<Task> getFirst() {
        return first;
    }


    public void distribution(InMemoryHistoryManager.Node<Task> node) {
        InMemoryHistoryManager.Node<Task> prev = node.getPre();
        InMemoryHistoryManager.Node<Task> next = node.getNext();

        if (prev == null) {
            first = next;
        } else {
            prev.setNext(next);
            node.setPre(null);
        }

        if (next == null) {
            last = prev;
        } else {
            next.setPre(prev);
            node.setNext(null);
        }
    }

    public void linkLast(InMemoryHistoryManager.Node<Task> node) {
        InMemoryHistoryManager.Node<Task> nodeLast = last;
        last = node;

        if (nodeLast == null) {
            first = node;
        } else {
            nodeLast.setNext(node);
            node.setPre(nodeLast);
        }
    }

}