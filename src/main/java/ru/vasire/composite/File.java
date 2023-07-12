package ru.vasire.composite;

import java.util.List;

public class File implements FileItem {

    protected Directory parent;
    private final String name;
    private final int size;

    public File(String name, int size, Directory parent) {
        this.name = name;
        this.size = size;
        parent.addChild(this);
    }

    @Override
    public String getName() {
        return parent != null ? parent.getName() + "/" + name : name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public List<FileItem> getChilds() {
        return null;
    }

    @Override
    public FileItem getParent() {
        return parent;
    }

    @Override
    public void setParent(FileItem parent) {
        if (parent instanceof Directory) {
            this.parent = (Directory) parent;
        }
    }
}
