package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.FreeTime} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.FreeTimeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /free-times?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FreeTimeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter start;

    private ZonedDateTimeFilter end;

    private Boolean distinct;

    public FreeTimeCriteria() {}

    public FreeTimeCriteria(FreeTimeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.start = other.optionalStart().map(ZonedDateTimeFilter::copy).orElse(null);
        this.end = other.optionalEnd().map(ZonedDateTimeFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FreeTimeCriteria copy() {
        return new FreeTimeCriteria(this);
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

    public ZonedDateTimeFilter getStart() {
        return start;
    }

    public Optional<ZonedDateTimeFilter> optionalStart() {
        return Optional.ofNullable(start);
    }

    public ZonedDateTimeFilter start() {
        if (start == null) {
            setStart(new ZonedDateTimeFilter());
        }
        return start;
    }

    public void setStart(ZonedDateTimeFilter start) {
        this.start = start;
    }

    public ZonedDateTimeFilter getEnd() {
        return end;
    }

    public Optional<ZonedDateTimeFilter> optionalEnd() {
        return Optional.ofNullable(end);
    }

    public ZonedDateTimeFilter end() {
        if (end == null) {
            setEnd(new ZonedDateTimeFilter());
        }
        return end;
    }

    public void setEnd(ZonedDateTimeFilter end) {
        this.end = end;
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
        final FreeTimeCriteria that = (FreeTimeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(start, that.start) &&
            Objects.equals(end, that.end) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FreeTimeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalStart().map(f -> "start=" + f + ", ").orElse("") +
            optionalEnd().map(f -> "end=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
