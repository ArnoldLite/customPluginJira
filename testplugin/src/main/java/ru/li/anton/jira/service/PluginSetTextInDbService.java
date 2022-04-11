package ru.li.anton.jira.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import ru.li.anton.jira.ao.TextTable;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PluginSetTextInDbService {

    private final ActiveObjects ao;

    @Inject
    public PluginSetTextInDbService(@ComponentImport ActiveObjects ao) {
        this.ao = ao;
    }

    public void saveTextInDb(String text){
        TextTable textTable = ao.create(TextTable.class);
        textTable.setText(text);
        textTable.save();
    }

}
