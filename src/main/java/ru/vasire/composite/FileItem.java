package ru.vasire.composite;

import java.util.List;

public interface FileItem {

    String getName();

    int getSize();

    List<FileItem> getChilds();

    FileItem getParent();

    void setParent(FileItem parent);

}
