package ru.yandex.task_manager.service;

import ru.yandex.task_manager.model.Task;

import java.util.*;

class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> history = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (task == null) return;
        Node node = history.get(task.getId());
        if (node != null) removeNode(node);
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        removeNode(history.get(id));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void linkLast(Task task) {
        Node node = new Node(tail, task, null);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        history.put(task.getId(), node);
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>(history.size());

        Node currentNode = head;
        while (currentNode != null) {
            tasks.add(currentNode.item);
            currentNode = currentNode.next;
        }
        return tasks;
    }

    private void removeNode(Node node) {
        if (node == null) return;
        if (node == head && node == tail) {
            head = tail = null;
        } else if (node == head) {
            head = head.next;
            head.prev = null;
        } else if (node == tail) {
            tail = tail.prev;
            tail.prev = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private static class Node {
        Task item;
        Node prev;
        Node next;

        Node(Node prev, Task item, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
