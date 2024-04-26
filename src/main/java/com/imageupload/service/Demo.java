package com.imageupload.service;

import java.util.Random;

public class Demo {
    public static void main(String[] args) {
        String s = myMethod();
        System.out.println(s);
    }

    public static String myMethod() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            char randomL = (char) ('A' + random.nextInt(26));
            sb.append(randomL);
        }
        for (int i = 0; i < 5; i++) {
            int randomD = random.nextInt(10);
            sb.append(randomD);
        }
        return sb.toString();
    }
}
