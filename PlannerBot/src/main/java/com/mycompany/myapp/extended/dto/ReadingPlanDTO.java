package com.mycompany.myapp.extended.dto;

import com.mycompany.myapp.domain.enumeration.Difficulty;

import java.time.ZonedDateTime;

public class ReadingPlanDTO {
    private String courseName;
    private Difficulty courseDifficulty;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

    public ReadingPlanDTO(String courseName, Difficulty courseDifficulty, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.courseName = courseName;
        this.courseDifficulty = courseDifficulty;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ReadingPlanDTO() {
    }

    public String getCourseName() {
        return this.courseName;
    }

    public Difficulty getCourseDifficulty() {
        return this.courseDifficulty;
    }

    public ZonedDateTime getStartTime() {
        return this.startTime;
    }

    public ZonedDateTime getEndTime() {
        return this.endTime;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDifficulty(Difficulty courseDifficulty) {
        this.courseDifficulty = courseDifficulty;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ReadingPlanDTO)) return false;
        final ReadingPlanDTO other = (ReadingPlanDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$courseName = this.getCourseName();
        final Object other$courseName = other.getCourseName();
        if (this$courseName == null ? other$courseName != null : !this$courseName.equals(other$courseName))
            return false;
        final Object this$courseDifficulty = this.getCourseDifficulty();
        final Object other$courseDifficulty = other.getCourseDifficulty();
        if (this$courseDifficulty == null ? other$courseDifficulty != null : !this$courseDifficulty.equals(other$courseDifficulty))
            return false;
        final Object this$startTime = this.getStartTime();
        final Object other$startTime = other.getStartTime();
        if (this$startTime == null ? other$startTime != null : !this$startTime.equals(other$startTime)) return false;
        final Object this$endTime = this.getEndTime();
        final Object other$endTime = other.getEndTime();
        if (this$endTime == null ? other$endTime != null : !this$endTime.equals(other$endTime)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ReadingPlanDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $courseName = this.getCourseName();
        result = result * PRIME + ($courseName == null ? 43 : $courseName.hashCode());
        final Object $courseDifficulty = this.getCourseDifficulty();
        result = result * PRIME + ($courseDifficulty == null ? 43 : $courseDifficulty.hashCode());
        final Object $startTime = this.getStartTime();
        result = result * PRIME + ($startTime == null ? 43 : $startTime.hashCode());
        final Object $endTime = this.getEndTime();
        result = result * PRIME + ($endTime == null ? 43 : $endTime.hashCode());
        return result;
    }

    public String toString() {
        return "ReadingPlanDTO(courseName=" + this.getCourseName() + ", courseDifficulty=" + this.getCourseDifficulty() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ")";
    }
}
