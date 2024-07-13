package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.Day;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Timetable.
 */
@Entity
@Table(name = "timetable")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Timetable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "app_user_id")
    private Long appUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private Day dayOfWeek;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "activity")
    private String activity;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Timetable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppUserId() {
        return this.appUserId;
    }

    public Timetable appUserId(Long appUserId) {
        this.setAppUserId(appUserId);
        return this;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Day getDayOfWeek() {
        return this.dayOfWeek;
    }

    public Timetable dayOfWeek(Day dayOfWeek) {
        this.setDayOfWeek(dayOfWeek);
        return this;
    }

    public void setDayOfWeek(Day dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public ZonedDateTime getStartTime() {
        return this.startTime;
    }

    public Timetable startTime(ZonedDateTime startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return this.endTime;
    }

    public Timetable endTime(ZonedDateTime endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getActivity() {
        return this.activity;
    }

    public Timetable activity(String activity) {
        this.setActivity(activity);
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Timetable)) {
            return false;
        }
        return getId() != null && getId().equals(((Timetable) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Timetable{" +
            "id=" + getId() +
            ", appUserId=" + getAppUserId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", activity='" + getActivity() + "'" +
            "}";
    }
}
