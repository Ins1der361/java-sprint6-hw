package ru.yandex.sprint3.alltask.manager;

import ru.yandex.sprint3.alltask.entity.CustomLinkedList;
import ru.yandex.sprint3.alltask.entity.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public static class Node<T> {
        private final T task;
        private Node<T> next;
        private Node<T> pre;

        public Node(Node<T> pre, T task, Node<T> next) {
            this.task = task;
            this.next = next;
            this.pre = pre;
        }

        public T getTask() {
            return task;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setPre(Node<T> pre) {
            this.pre = pre;
        }

        public Node<T> getPre() {
            return pre;
        }
    }

    private final HashMap<Integer, Node<Task>> nodOfHistoryTask = new HashMap<>();
    CustomLinkedList customLinkedList = new CustomLinkedList();

    @Override
    public void addTask(Task task) {
        Node<Task> lastNode = customLinkedList.getFirst();
        Node<Task> newNode;

        if (nodOfHistoryTask.containsKey(task.getId())) {
            newNode = nodOfHistoryTask.get(task.getId());
            customLinkedList.distribution(newNode);
        } else {
            newNode = new Node<>(lastNode, task, null);
            nodOfHistoryTask.put(task.getId(), newNode);
        }
        customLinkedList.linkLast(newNode);
    }

    @Override
    public void remove(int id) {
        Node<Task> deletedNode = nodOfHistoryTask.get(id);
        if (deletedNode != null) {
            customLinkedList.distribution(deletedNode);
            nodOfHistoryTask.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> taskArrayList = new ArrayList<>();
        for (Node<Task> i = customLinkedList.getFirst(); i != null; i = i.getNext())
            taskArrayList.add(i.getTask());

        return taskArrayList;
    }

    @Override
    public String toString() {
        StringBuilder id = new StringBuilder();
        String strId = "";
        for (Task task : getHistory()) {
            id.append(task.getId()).append(',');
        }
        if (!id.isEmpty()) {
            strId = id.substring(0, id.length() - 1);
        }
        return strId;
    }
}