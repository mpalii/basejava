package ru.javaops.webapp.model;

import java.util.Date;

public class Position {
    private String activity;
    private String startDate;
    private String endDate;

    public Position(String activity, String startDate, String endDate) {
        this.activity = activity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (!activity.equals(position.activity)) return false;
        if (!startDate.equals(position.startDate)) return false;
        return endDate.equals(position.endDate);
    }

    @Override
    public int hashCode() {
        int result = activity.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startDate).append(" - ").append(endDate).append(" ").append(activity);
        return stringBuilder.toString();
    }
}
