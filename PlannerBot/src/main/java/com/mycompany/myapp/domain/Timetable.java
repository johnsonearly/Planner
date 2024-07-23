package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.Importance;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "date_of_activity")
    private LocalDate dateOfActivity;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "activity")
    private String activity;

    @Column(name = "is_done")
    private Boolean isDone;

    @Enumerated(EnumType.STRING)
    @Column(name = "level_of_importance")
    private Importance levelOfImportance;

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

    public String getDayOfWeek() {
        return this.dayOfWeek;
    }

    public Timetable dayOfWeek(String dayOfWeek) {
        this.setDayOfWeek(dayOfWeek);
        return this;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDate getDateOfActivity() {
        return this.dateOfActivity;
    }

    public Timetable dateOfActivity(LocalDate dateOfActivity) {
        this.setDateOfActivity(dateOfActivity);
        return this;
    }

    public void setDateOfActivity(LocalDate dateOfActivity) {
        this.dateOfActivity = dateOfActivity;
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

    public Boolean getIsDone() {
        return this.isDone;
    }

    public Timetable isDone(Boolean isDone) {
        this.setIsDone(isDone);
        return this;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Importance getLevelOfImportance() {
        return this.levelOfImportance;
    }

    public Timetable levelOfImportance(Importance levelOfImportance) {
        this.setLevelOfImportance(levelOfImportance);
        return this;
    }

    public void setLevelOfImportance(Importance levelOfImportance) {
        this.levelOfImportance = levelOfImportance;
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
            ", dateOfActivity='" + getDateOfActivity() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", activity='" + getActivity() + "'" +
            ", isDone='" + getIsDone() + "'" +
            ", levelOfImportance='" + getLevelOfImportance() + "'" +
            "}";
    }
}
