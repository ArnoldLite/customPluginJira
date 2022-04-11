package ru.li.anton.jira.service;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PluginGetLoggedUserService {

    private final JiraAuthenticationContext jiraAuthenticationContext;

    @Inject
    public PluginGetLoggedUserService(@ComponentImport JiraAuthenticationContext jiraAuthenticationContext) {
        this.jiraAuthenticationContext = jiraAuthenticationContext;
    }

    public ApplicationUser getUser(){
        return jiraAuthenticationContext.getLoggedInUser();
    }
}
