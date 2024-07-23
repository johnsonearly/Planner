package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Importance;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Timetable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimetableDTO implements Serializable {

    private Long id;

    private Long appUserId;

    private String dayOfWeek;

    private LocalDate dateOfActivity;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private String activity;

    private Boolean isDone;

    private Importance levelOfImportance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDate getDateOfActivity() {
        return dateOfActivity;
    }

    public void setDateOfActivity(LocalDate dateOfActivity) {
        this.dateOfActivity = dateOfActivity;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Importance getLevelOfImportance() {
        return levelOfImportance;
    }

    public void setLevelOfImportance(Importance levelOfImportance) {
        this.levelOfImportance = levelOfImportance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimetableDTO)) {
            return false;
        }

        TimetableDTO timetableDTO = (TimetableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, timetableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimetableDTO{" +
            "id=" + getId() +
            ", appUserId=" + getAppUserId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", dateOfActivity='" + getDateOfActivity() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", activity='" + getActivity() + "'" +
            ", isDone='" + getIsDone() + "'" +
            ", levelOfImportance='" + getLevelOfImportance() + "'" +
            "}";
    }
}
