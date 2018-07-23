package com.gagarkin;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class ListRand {

    public ListNode head;
    public ListNode tail;
    public int count;

    public void serialize(ByteBuffer out) throws IOException {

        out.put(toByteArray(count));

        if (count != 0) {

            List<ListNode> temp = new ArrayList<>(count);

            ListNode curr = head;
            for (int i = 0; i < count; i++) {

                String data = curr.data;
                Integer indexOfRand = temp.indexOf(curr.rand);
                byte[] bytesData = data.getBytes();
                byte[] byteLength = toByteArray(bytesData.length);
                byte[] byteIndexOfRand = toByteArray(indexOfRand);

                out.put(byteLength);
                out.put(bytesData);
                out.put(byteIndexOfRand);


                temp.add(curr);
                curr = curr.next;
            }
        }
    }

    public void deserialize(ByteBuffer in) {

        byte[] array = in.array();
        byte[] byteCount = new byte[4];

        int cursorOfCurrentPos = 4;

        System.arraycopy(array, 0, byteCount, 0, 4);
        Integer count = fromByteArray(byteCount); // count nodes in the buffer

        List<ListNode> temp = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {

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
                listNode.rand = temp.get(indexOfRand);

            temp.add(listNode);

            add(listNode);
        }
    }


    public boolean add(ListNode e) {
        linkLast(e);
        return true;
    }

    private void linkLast(ListNode e) {

        if (head == null || tail == null) {
            head = e;
            tail = e;
            head.next = tail;
            head.prev = tail;
            tail.next = head;
            tail.prev = head;
        } else {
            tail.next = e;
            e.prev = tail;
            tail = e;
            tail.next = head;
            head.prev = tail;
        }
        count++;
    }

    private byte[] toByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    private int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public String toString() {

        StringBuilder builder = new StringBuilder();
        ListNode curr = head;

        for (int i = 0; i < count; i++) {

            builder.append("'").append(i).append("': data = ").append(curr.data).append("  random = ").append(curr.rand != null ? curr.rand.data : "NULL").append("\r\n");

            curr = curr.next;
        }
        return builder.toString();
    }
}
