package ru.vasire;

import ru.vasire.composite.Directory;
import ru.vasire.composite.FileObj;

public class CompositeToTest {
    public static void main(String[] args) {
        Directory root = new Directory("root", null);
        Directory level1 = new Directory("level1", root);

        FileObj fileObj = new FileObj("First.docx", 1111, level1);
        System.out.println("fileObj = " + fileObj.getName() + ", size = " + fileObj.getSize());


        FileObj fileObj2 = new FileObj("Second.docx", 1111, level1);
        System.out.println("fileObj = " + level1.getName() + ", size = " + level1.getSize());

        FileObj fileObj3 = new FileObj("Second.docx", 1111, root);

        System.out.println("fileObj = " + root.getName() + ", size = " + root.getSize());
    }
}
