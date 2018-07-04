package com.gagarkin;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class ListRand {

    public ListNode head;
    public ListNode tail;
    public int count;

    public void serialize(ByteBuffer buffer) throws IOException {
        if (count != 0) {

            List<ListNode> arr = new ArrayList<>(count);
            ListNode curr = head;
            do {
                arr.add(curr);
                curr = curr.next;
            } while (curr != null);

            buffer.put(toByteArray(count));

            for (ListNode node : arr) {
                String data = node.data;
                Integer indexOfRand = arr.indexOf(node.rand);
                byte[] bytesData = data.getBytes();
                byte[] byteLength = toByteArray(bytesData.length);
                byte[] byteIndexOfRand = toByteArray(indexOfRand);

                buffer.put(byteLength);
                buffer.put(bytesData);
                buffer.put(byteIndexOfRand);
            }
        }
    }

    public void deserialize(ByteBuffer in) {

        byte[] array = in.array();
        byte[] byteCount = new byte[4];

        int cursorOfCurrentPos = 4;

        System.arraycopy(array, 0, byteCount, 0, 4);
        Integer count = fromByteArray(byteCount); // count nodes in the buffer

        List<ListNode> nodeList = new ArrayList<>(count);
        while (count > 0) {

            byte[] byteLength = new byte[4];
            System.arraycopy(array, cursorOfCurrentPos, byteLength, 0, 4);
            int length = fromByteArray(byteLength); // length of the data

            cursorOfCurrentPos = cursorOfCurrentPos + 4;

            byte[] byteData = new byte[length];
            System.arraycopy(array, cursorOfCurrentPos, byteData, 0, length);
            String data = new String(byteData); // data

            cursorOfCurrentPos = cursorOfCurrentPos + length;

            byte[] byteIndexOfRand = new byte[4];
            System.arraycopy(array, cursorOfCurrentPos, byteIndexOfRand, 0, 4);
            int indexOfRand = fromByteArray(byteIndexOfRand); // index of the random node

            cursorOfCurrentPos = cursorOfCurrentPos + 4;

            ListNode listNode = new ListNode();
            listNode.data = data;
            if (indexOfRand >= 0)
                listNode.rand = nodeList.get(indexOfRand);

            nodeList.add(listNode);

            add(listNode);

            count--;
        }
    }


    public boolean add(ListNode e) {
        linkLast(e);
        return true;
    }

    private void linkLast(ListNode e) {

        if (head == null && tail == null) {
            head = e;
            tail = e;
        } else {
            tail.next = e;
            e.prev = tail;
            tail = e;
        }
        count++;
    }

    byte[] toByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
}
