package ru.vasire.composite;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileItem {

    private final String name;

    private Directory parent;

    private final List<FileItem> childs = new ArrayList<>();

    public Directory(String name, Directory parent) {
        this.name = name;
        if (parent != null) {
            parent.addChild(this);
        }
    }

    @Override
    public String getName() {
        return parent != null ? parent.getName() + "/" + name : name;
    }

    @Override
    public int getSize() {
        if (childs.size() == 0) {
            return 0;
        } else {
            return childs.stream().mapToInt(FileItem::getSize).sum();
        }
    }

    @Override
    public List<FileItem> getChilds() {
        return childs;
    }

    @Override
    public FileItem getParent() {
        return parent;
    }

    public void setParent(FileItem fileItem) {
        if (fileItem instanceof Directory) {
            this.parent = (Directory) fileItem;
        }
    }

    public void addChild(FileItem fileItem) {
        fileItem.setParent(this);
        if (!this.childs.contains(fileItem)) {
            this.childs.add(fileItem);
        }
    }
}