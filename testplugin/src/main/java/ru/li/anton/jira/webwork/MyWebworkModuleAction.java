package ru.li.anton.jira.webwork;


import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.worklog.WorklogManager;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;

import org.ofbiz.core.entity.GenericEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;

import ru.li.anton.jira.service.PluginGetLoggedUserService;
import ru.li.anton.jira.service.PluginSetTextInDbService;
import ru.li.anton.jira.util.WorkLogDto;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyWebworkModuleAction extends JiraWebActionSupport {

    private static final Logger log = LoggerFactory.getLogger(MyWebworkModuleAction.class);

    private final PluginSetTextInDbService pluginSetTextInDbService;
    private final PluginGetLoggedUserService pluginGetLoggedUserService;

    private final IssueManager issueManager;
    private final ProjectManager projectManager;
    private final WorklogManager worklogManager;

    private List<WorkLogDto> workLogsForLogUser;
    private String userName;
    private String text;

    @Inject
    public MyWebworkModuleAction(PluginSetTextInDbService pluginSetTextInDbService,
                                 PluginGetLoggedUserService pluginGetLoggedUserService) {

        this.projectManager = ComponentAccessor.getProjectManager();
        this.worklogManager = ComponentAccessor.getWorklogManager();
        this.issueManager = ComponentAccessor.getIssueManager();
        this.pluginSetTextInDbService = pluginSetTextInDbService;
        this.pluginGetLoggedUserService = pluginGetLoggedUserService;
    }

    @Override
    public String doExecute() throws Exception {
        userName = pluginGetLoggedUserService.getUser().getName();
        workLogsForLogUser = getList();
        return SUCCESS;
    }

    public String doSave() throws Exception {
        userName = pluginGetLoggedUserService.getUser().getName();
        workLogsForLogUser = getList();
        pluginSetTextInDbService.saveTextInDb(text);
        return SUCCESS;
    }


    public List<WorkLogDto> getWorkLogsForLogUser() {
        return workLogsForLogUser;
    }
    public void setWorkLogsForLogUser(List<WorkLogDto> workLogsForLogUser) {
        this.workLogsForLogUser = workLogsForLogUser;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() { return text; }
    public void setText(String text) {
        this.text = text;
    }



    public List<WorkLogDto> getList() throws GenericEntityException {

        Project project = projectManager.getProjectObjByKey("TESTDEV");
        return issueManager
                .getIssueIdsForProject(project.getId())
                .stream()
                .map(issueManager::getIssueObject)
                .map(worklogManager::getByIssue)
                .flatMap(Collection::stream)
                .filter(w->pluginGetLoggedUserService.getUser().equals(w.getAuthorObject()))
                .map(w->WorkLogDto.builder()
                        .setIssueKey(w.getIssue().getKey())
                        .setTimeSpent(w.getTimeSpent())
                        .build())
                .collect(Collectors.toList());
    }
}
