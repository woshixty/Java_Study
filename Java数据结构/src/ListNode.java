/**
 * @author qyyzxty@icloud.com
 * 2021/5/4
 **/
public class ListNode {
    //当前节点的值
    private int data;

    //下一个节点
    private ListNode next;

    public ListNode(int data) {
        this.data = data;
    }
    public void setData(int data) {
        this.data = data;
    }
    public int getData() {
        return data;
    }
    public void setNext(ListNode listNode) {
        this.next = listNode;
    }
    public ListNode getNext() {
        return this.next;
    }

    public int ListLength(ListNode headBode) {return 0;}

    ListNode InsertInLinkedList(ListNode headNode, ListNode nodeToInsert, int position) {
        //链表为空，插入
        if(headNode == null) {
            return nodeToInsert;
        }
        int size = ListLength(headNode);
        //在链表头插入
        if (position == 1) {
            nodeToInsert.next = headNode;
            return nodeToInsert;
        }
        //在链表中间或者末尾插入
        ListNode previousNode = headNode;
        int count = 1;
        while (count < position - 1) {
            previousNode = previousNode.getNext();
            count++;
        }
        ListNode currentNode = previousNode.getNext();
        nodeToInsert.setNext(currentNode);
        previousNode.setNext(nodeToInsert);
        return headNode;
    }

    class DLLNode {
        private int data;
        private DLLNode next;
        private DLLNode previous;
    }

    class ListNodeNew {
        private int data;
        private ListNodeNew ptrdiff;
    }

}
