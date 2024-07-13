package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.Day;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Timetable} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.TimetableResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /timetables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimetableCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Day
     */
    public static class DayFilter extends Filter<Day> {

        public DayFilter() {}

        public DayFilter(DayFilter filter) {
            super(filter);
        }

        @Override
        public DayFilter copy() {
            return new DayFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter appUserId;

    private DayFilter dayOfWeek;

    private ZonedDateTimeFilter startTime;

    private ZonedDateTimeFilter endTime;

    private StringFilter activity;

    private Boolean distinct;

    public TimetableCriteria() {}

    public TimetableCriteria(TimetableCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.appUserId = other.optionalAppUserId().map(LongFilter::copy).orElse(null);
        this.dayOfWeek = other.optionalDayOfWeek().map(DayFilter::copy).orElse(null);
        this.startTime = other.optionalStartTime().map(ZonedDateTimeFilter::copy).orElse(null);
        this.endTime = other.optionalEndTime().map(ZonedDateTimeFilter::copy).orElse(null);
        this.activity = other.optionalActivity().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TimetableCriteria copy() {
        return new TimetableCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getAppUserId() {
        return appUserId;
    }

    public Optional<LongFilter> optionalAppUserId() {
        return Optional.ofNullable(appUserId);
    }

    public LongFilter appUserId() {
        if (appUserId == null) {
            setAppUserId(new LongFilter());
        }
        return appUserId;
    }

    public void setAppUserId(LongFilter appUserId) {
        this.appUserId = appUserId;
    }

    public DayFilter getDayOfWeek() {
        return dayOfWeek;
    }

    public Optional<DayFilter> optionalDayOfWeek() {
        return Optional.ofNullable(dayOfWeek);
    }

    public DayFilter dayOfWeek() {
        if (dayOfWeek == null) {
            setDayOfWeek(new DayFilter());
        }
        return dayOfWeek;
    }

    public void setDayOfWeek(DayFilter dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public ZonedDateTimeFilter getStartTime() {
        return startTime;
    }

    public Optional<ZonedDateTimeFilter> optionalStartTime() {
        return Optional.ofNullable(startTime);
    }

    public ZonedDateTimeFilter startTime() {
        if (startTime == null) {
            setStartTime(new ZonedDateTimeFilter());
        }
        return startTime;
    }

    public void setStartTime(ZonedDateTimeFilter startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTimeFilter getEndTime() {
        return endTime;
    }

    public Optional<ZonedDateTimeFilter> optionalEndTime() {
        return Optional.ofNullable(endTime);
    }

    public ZonedDateTimeFilter endTime() {
        if (endTime == null) {
            setEndTime(new ZonedDateTimeFilter());
        }
        return endTime;
    }

    public void setEndTime(ZonedDateTimeFilter endTime) {
        this.endTime = endTime;
    }

    public StringFilter getActivity() {
        return activity;
    }

    public Optional<StringFilter> optionalActivity() {
        return Optional.ofNullable(activity);
    }

    public StringFilter activity() {
        if (activity == null) {
            setActivity(new StringFilter());
        }
        return activity;
    }

    public void setActivity(StringFilter activity) {
        this.activity = activity;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TimetableCriteria that = (TimetableCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(appUserId, that.appUserId) &&
            Objects.equals(dayOfWeek, that.dayOfWeek) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(activity, that.activity) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appUserId, dayOfWeek, startTime, endTime, activity, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimetableCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalAppUserId().map(f -> "appUserId=" + f + ", ").orElse("") +
            optionalDayOfWeek().map(f -> "dayOfWeek=" + f + ", ").orElse("") +
            optionalStartTime().map(f -> "startTime=" + f + ", ").orElse("") +
            optionalEndTime().map(f -> "endTime=" + f + ", ").orElse("") +
            optionalActivity().map(f -> "activity=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
