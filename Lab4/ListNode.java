package Lab4;

public class ListNode {
    private Object m_data;
    private ListNode m_next;
    private ListNode m_prev;

    public ListNode(Object data, ListNode next, ListNode prev) {
        m_data = data;
        m_next = next;
        m_prev = prev;
    }

    public ListNode(Object data) {
        m_data = data;
        m_next = null;
        m_prev = null;
    }

    public void setData(Object data) {
        m_data = data;
    }

    public Object getData() {
        return m_data;
    }

    public void setNext(ListNode next) {
        m_next = next;
    }

    public ListNode getNext() {
        return m_next;
    }

    public void setPrev(ListNode prev) {
        m_prev = prev;
    }

    public ListNode getPrev() {
        return m_prev;
    }
}