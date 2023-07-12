package ru.vasire;

import ru.vasire.composite.Directory;
import ru.vasire.composite.File;

public class CompositeToTest {
    public static void main(String[] args) {
        Directory root = new Directory("root", null);
        Directory level1 = new Directory("level1", root);

        File file = new File("First.docx", 1111, level1);
        System.out.println("file = " + file.getName() + ", size = " + file.getSize());


        File file2 = new File("Second.docx", 1111, level1);
        System.out.println("file = " + level1.getName() + ", size = " + level1.getSize());

        File file3 = new File("Second.docx", 1111, root);

        System.out.println("file = " + root.getName() + ", size = " + root.getSize());
    }
}
