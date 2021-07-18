/**
 * @author qyyzxty@icloud.com
 * 2021/5/19
 **/
public class DynArrayStack {
    private int top;
    private int capacity;
    private int[] array;
    //无参构造
    public DynArrayStack() {
        capacity = 1;
        array = new int[capacity];
        top = -1;
    }
    //判断是否栈满
    public boolean isStackFull() {
        //栈满返回1否则返回0
        return (top == capacity - 1);
    }
    //判断是否栈空
    public boolean isStackEmpty() {
        //栈空返回真否则返回假
        return top == -1;
    }
    //将栈的容量扩大两倍
    private void doubleStack() {
        int newArray[] = new int[capacity*2];
        System.arraycopy(array, 0, newArray, 0, capacity);
        capacity = capacity*2;
        array = newArray;
    }
    //入栈
    public void push(int data) {
        if (isStackFull()) {
            doubleStack();
        }
        array[++top] = data;
    }
    //出栈
    public int pop() {
        if (isStackEmpty()) {
            System.out.println("栈空");
        } else {
            return array[top--];
        }
        return 0;
    }
    //删除栈
    public void deleteStack() {
        top = -1;
    }
}
