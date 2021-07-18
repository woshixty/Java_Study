import java.util.EmptyStackException;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/19
 **/
public class LLStack {
    private LLNode headNode;
    public LLStack() {
        this.headNode = new LLNode(null);
    }
    public void Push(int data) {
        if (headNode == null) {
            headNode = new LLNode(data);
        } else if(headNode.getData() == null) {
            headNode.setData(data);
        } else {
            LLNode llNode = new LLNode(data);
            llNode.setNext(headNode);
            headNode = llNode;
        }
    }
    public Integer top() {
        if (headNode == null) {
            return null;
        } else {
            return headNode.getData();
        }
    }
    public int pop() {
        if (headNode == null) {
            throw new EmptyStackException();
        } else {
            int data = headNode.getData();
            headNode = headNode.getNext();
            return data;
        }
    }
}
