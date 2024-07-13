package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Day;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Timetable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimetableDTO implements Serializable {

    private Long id;

    private Long appUserId;

    private Day dayOfWeek;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private String activity;

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

    public Day getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Day dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", activity='" + getActivity() + "'" +
            "}";
    }
}
