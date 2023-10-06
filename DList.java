class Node {
    String topic;
    int qnNumber;
    double average;
    Node prev;
    Node next;

    public Node(String topic, int qnNumber, double average) {
        this.topic = topic;
        this.qnNumber = qnNumber;
        this.average = average;
    }

    @Override
    public String toString() {
        return topic + ", Qn " + qnNumber + ", " + String.format("%.1f", average);
    }
}

class DoublyLinkedList {
    Node head;
    Node tail;

    public void add(String topic, int qnNumber, double average) {
        Node newNode = new Node(topic, qnNumber, average);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HEAD <-> ");
        Node current = head;
        while (current != null) {
            sb.append(current.toString());
            current = current.next;
            if (current != null) {
                sb.append(" <--> ");
            }
        }
        sb.append(" <-> TAIL");
        return sb.toString();
    }
}
