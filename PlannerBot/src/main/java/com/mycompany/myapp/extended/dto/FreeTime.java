package com.mycompany.myapp.extended.dto;

import java.time.ZonedDateTime;

public class FreeTime {
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

    public FreeTime(ZonedDateTime startTime, ZonedDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public FreeTime() {
    }

    public ZonedDateTime getStartTime() {
        return this.startTime;
    }

    public ZonedDateTime getEndTime() {
        return this.endTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FreeTime)) return false;
        final FreeTime other = (FreeTime) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$startTime = this.getStartTime();
        final Object other$startTime = other.getStartTime();
        if (this$startTime == null ? other$startTime != null : !this$startTime.equals(other$startTime)) return false;
        final Object this$endTime = this.getEndTime();
        final Object other$endTime = other.getEndTime();
        if (this$endTime == null ? other$endTime != null : !this$endTime.equals(other$endTime)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FreeTime;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $startTime = this.getStartTime();
        result = result * PRIME + ($startTime == null ? 43 : $startTime.hashCode());
        final Object $endTime = this.getEndTime();
        result = result * PRIME + ($endTime == null ? 43 : $endTime.hashCode());
        return result;
    }

    public String toString() {
        return "FreeTime(startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ")";
    }
}
