package ru.li.anton.jira.ao;

import net.java.ao.Entity;


public interface TextTable extends Entity {

    String getText();
    void setText(String text);
}
