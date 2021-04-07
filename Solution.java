package com.javarush.task.task18.task1809;

/*
Реверс файла
*/

import java.io.*;

public class Solution {
    public static void main(String[] args) {
        boolean test = true; // TODO must be false
        String fileNameIn = "";
        String fileNameOut = "";
        long start = 0;
        if (test) {
            start = System.currentTimeMillis();
            fileNameIn = "2.JavaCore/src/com/javarush/task/task18/task1809/In.txt";
            fileNameOut = "2.JavaCore/src/com/javarush/task/task18/task1809/Out.txt";
        } else {
            try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))){
                fileNameIn = consoleReader.readLine();
                fileNameOut = consoleReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileInputStream fileInputStream = new FileInputStream(fileNameIn);
//             InputStreamReader fileInputStreamReader = new InputStreamReader(new FileInputStream(fileNameIn), "UTF-8"); // TODO must be commented
//             RandomAccessFile randomAccessFileInput = new RandomAccessFile(fileNameIn, "r"); // "r" mode read only  // TODO must be commented
             FileOutputStream fileOutputStream = new FileOutputStream(fileNameOut)) {

            // Revers read with buffer realization ~10 sec
//            int fileSize = fileInputStream.available();
//            int bufferSize = 8 * 1024;
//            if (test) System.out.println("buffer size = " + bufferSize + " bytes");
//            byte[] buffer = new byte[bufferSize];
//            int pointer = fileSize - 1;
//            int count = 0;
//            while (pointer >= 0) {
//                for (int i = 0; i < bufferSize; i++) {
//                    if (pointer < 0) {
//                        break;
//                    }
//                    randomAccessFileInput.seek(pointer);
//                    buffer[i] = randomAccessFileInput.readByte();
//                    count = i + 1;
//                    pointer--;
//                    if (test) System.out.print((char) buffer[i]);
//                }
//                fileOutputStream.write(buffer, 0, count);
//            }

            // Revers read with buffer realization version 2 - ~10 sec - Maybe Validator won't accept it!
//            long fileSize = randomAccessFileInput.length();
//            int bufferSize = 8 * 1024;
//            if (test) System.out.println("buffer size = " + bufferSize);
//            byte[] buffer = new byte[bufferSize];
//            long pointer = fileSize - 1;
//            int count = 0;
//            while (pointer >= 0) {
//                for (int i = 0; i < bufferSize; i++) {
//                    if (pointer < 0) {
//                        break;
//                    }
//                    randomAccessFileInput.seek(pointer--);
//                    byte current = randomAccessFileInput.readByte();
//                    // to resolve LF CR - CR LF problem
//                    if (current == 10) {
//                        current = 13; // LF - Line Feed (перевод строки) - код символа 10
//                    } else if (current == 13) {
//                        current = 10; // CR - Carriage Return (возврат каретки) - код символа 13
//                    }
//                    buffer[i] = current;
//                    count = i + 1;
//                    if (test) System.out.print((char) buffer[i]);
//                }
//                fileOutputStream.write(buffer, 0, count);
//            }

            // Revers read all file in buffer realization 7 sec
            int length = fileInputStream.available();
            byte[] bytes = new byte[length];
            int cursor = -1;
            while ((cursor = fileInputStream.read()) != -1) {
                bytes[--length] = (byte) cursor;
            }
            fileOutputStream.write(bytes);

            // Task author realization 30 sec
//            List<Integer> inputBytes = new ArrayList<>();
//            while (fileInputStream.available() > 0) {
//                inputBytes.add(fileInputStream.read());
//            }
//            Collections.reverse(inputBytes);
//            for (Integer aByte : inputBytes) {
//                fileOutputStream.write(aByte);
//            }

            // Stack realization 30 sec
//            Stack<Integer> stack = new Stack<>();
//            while (fileInputStream.available() > 0) {
//                stack.push(fileInputStream.read());
//            }
//            while (stack.size() > 0) {
//                fileOutputStream.write(stack.pop());
//            }

            // The Right revers file realization ~ 300 milliseconds! Validator won't accept it!
//            RandomAccessFile randomAccessFile = new RandomAccessFile(fileNameOut, "rw");
//            int fileSize = fileInputStream.available();
//            int bufferSize = 8 * 1024;
//            if (test) System.out.println("buffer size = " + bufferSize);
//            byte[] bytesBuffer = new byte[bufferSize];
//            char[] charsBuffer = new char[bufferSize];
//            int writeCount = 0;
//            while (fileInputStream.available() > 0) {
////                if (fileInputStream.available() < bufferSize) {
////                    bufferSize = fileInputStream.available();
////                }
////                fileInputStream.read(buffer, 0, bufferSize);
//                bufferSize = fileInputStream.read(bytesBuffer);
//                reverseBytes(bytesBuffer, bufferSize);
//                writeCount += bufferSize;
//                randomAccessFile.seek(fileSize - writeCount);
//                randomAccessFile.write(bytesBuffer, 0, bufferSize);
//            }
//            randomAccessFile.close();

            // The Right chars revers file realization. Validator won't accept it!
//            RandomAccessFile randomAccessFile = new RandomAccessFile(fileNameOut, "rw");
//            int fileSize = fileInputStream.available();
//            int bufferSize = 8 * 1024;
//            if (test) System.out.println("buffer size = " + bufferSize);
//            byte[] bytesBuffer;
//            char[] charsBuffer = new char[bufferSize];
//            int writeCount = 0;
//            while (fileInputStream.available() > 0) {
//                bufferSize = fileInputStreamReader.read(charsBuffer);
//                if (bufferSize < 0) break;
//                bytesBuffer = reverseCharsToBytes(charsBuffer, bufferSize);
//                writeCount += bufferSize;
//                randomAccessFile.seek(fileSize - writeCount);
//                randomAccessFile.write(bytesBuffer, 0, bufferSize);
//            }
//            randomAccessFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (test) {
            long elapsed = System.currentTimeMillis() - start;
            System.out.println("time = " + elapsed + " milliseconds");
        }
    }

    public static void reverseBytes(byte[] bytes, int size) {
        for (int i = 0; i < size / 2; i++) {
            byte c = bytes[i];
            bytes[i] = bytes[size - i - 1];
            bytes[size - i - 1] = c;
        }
    }

    public static byte[] reverseCharsToBytes(char[] chars, int size) {
        byte[] bytes = new byte[size];
        int i = 0;
        int end = size;
        while (i != end) {
            bytes[--size] = (byte) chars[i];
            i++;
        }
        return bytes;
    }
}
