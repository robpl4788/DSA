package Lab4;

public class ListNode {
    private Object m_data;
    private ListNode m_next;

    public ListNode(Object data, ListNode next) {
        m_data = data;
        m_next = next;
    }

    public ListNode(Object data) {
        m_data = data;
        m_next = null;
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
}