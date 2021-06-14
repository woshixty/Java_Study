/**
 * @author qyyzxty@icloud.com
 * 2021/5/17
 **/
public class ArrayStack {
    //栈顶指针
    private int top;
    //站的大小
    private int capacity;
    //存储数据的数组
    private int[] array;

    public ArrayStack() {
        //初始化
        capacity = 1;
        array = new int[capacity];
        top = -1;
    }

    //判断栈是否为空
    public boolean isEmpty() {
        return (top == -1);
    }

    //判断是否栈满
    public boolean isStackFull() {
        return (top == capacity - 1);
    }

    //添加数据
    public void push(int data) {
        if (isStackFull()) System.out.println("Stack OverFlow");
        else array[++top] = data;
    }

    //删除数据-pop
    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack is Empty");
            return 0;
        } else {
            return (array[top--]);
        }
    }
}
