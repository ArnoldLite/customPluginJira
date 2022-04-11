package ru.li.anton.jira.util;

public class WorkLogDto {

    private String issueKey;
    private Double timeSpent;

    public WorkLogDto(WorkLogDtoBuilder workLogDtoBuilder) {
        this.issueKey = workLogDtoBuilder.issueKey;
        this.timeSpent = workLogDtoBuilder.timeSpent;
    }

    public static WorkLogDtoBuilder builder(){return new WorkLogDtoBuilder();}

    public String getIssueKey() {
        return issueKey;
    }
    public Double getTimeSpent() {
        return timeSpent;
    }


    public static class WorkLogDtoBuilder {

        private String issueKey;
        private Double timeSpent;

        public WorkLogDtoBuilder setIssueKey(String issueKey) {
            this.issueKey = issueKey;
            return this;
        }

        public WorkLogDtoBuilder setTimeSpent(Long timeSpent) {
            this.timeSpent = timeSpent/3600D;
            return this;
        }

        public WorkLogDto build(){
            return new WorkLogDto(this);
        }
    }

}
