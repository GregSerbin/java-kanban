package ru.yandex.task_manager.service;

import ru.yandex.task_manager.model.Task;

import java.util.*;

class InMemoryHistoryManager implements HistoryManager {

    private class Node {
        Task item;
        Node prev;
        Node next;

        Node(Node prev, Task item, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Map<Integer, Node> history = new HashMap<>();
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
        Node node;
        if (tail == null) {
            node = new Node(null, task, null);
            tail = node;
            head = node;
        } else {
            node = new Node(tail, task, null);
            tail.next = node;
            tail = node;
        }
        history.put(task.getId(), node);
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        Node currentNode = head;
        while (currentNode != null) {
            tasks.add(currentNode.item);
            currentNode = currentNode.next;
        }
        return tasks;
    }

    private void removeNode(Node node) {
        if (node == null) return;
        history.remove(node.item.getId());
        if (node == head) {
            head = head.next;
        } else if (node == tail) {
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
}
