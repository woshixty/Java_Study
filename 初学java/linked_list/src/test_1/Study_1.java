package test_1;

import java.util.Scanner;

public class Study_1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HeroNode head = new HeroNode(in.nextInt(), in.next(), in.next());
        HeroNode pointHeroNode = head;
        HeroNode nextHeroNode = new HeroNode(in.nextInt(), in.next(), in.next());
        head.next = nextHeroNode;
        HeroNode.reversePrint(pointHeroNode);
        in.close();
    }

//	class Heronode {
//		public int no;
//		public String name;
//		public String nickname;
//		public HeroNode next;
//	}

}
