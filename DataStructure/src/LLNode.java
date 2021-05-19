/**
 * @author qyyzxty@icloud.com
 * 2021/5/19
 **/
public class LLNode {
    private Integer data;
    private LLNode next;

    public LLNode(Integer data) {
        this.data = data;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public LLNode getNext() {
        return next;
    }

    public void setNext(LLNode next) {
        this.next = next;
    }
}
