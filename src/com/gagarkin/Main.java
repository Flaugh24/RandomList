package com.gagarkin;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Main {

    public static void main(String[] args) throws IOException {

        ListNode node1 = new ListNode();
        node1.data = "1";


        ListNode node2 = new ListNode();
        node2.data = "2";
        node2.rand = node1;


        ListNode node3 = new ListNode();
        node3.data = "3";
        node3.rand = node1;

        ListNode node4 = new ListNode();
        node4.data = "4";
        node4.rand = node2;



        ListRand initialList = new ListRand();
        initialList.add(node1);
        initialList.add(node2);
        initialList.add(node3);
        initialList.add(node4);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        initialList.serialize(buffer);


        ListRand finalList = new ListRand();
        finalList.deserialize(buffer);
    }
}
