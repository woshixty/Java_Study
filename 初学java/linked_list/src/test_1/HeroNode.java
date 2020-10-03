package test_1;

import java.util.Stack;

public class HeroNode {

    public int no;
    public String name;
    public String nickname;
    public HeroNode next = null;
    public static Stack<HeroNode> stack = new Stack<HeroNode>();

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + ", nickname=" + nickname + ", next=" + next + "]";
    }

    public static void reversePrint(HeroNode head) {
        HeroNode cur = head.next;
        if (head.next == null)
            return;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        while (stack.size() > 0) {
            System.out.println(stack.toString());
            System.out.println(stack.pop());
        }
    }

    //	public add()
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
