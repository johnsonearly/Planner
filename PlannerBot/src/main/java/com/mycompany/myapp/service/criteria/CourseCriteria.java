package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.Difficulty;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Course} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /courses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourseCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Difficulty
     */
    public static class DifficultyFilter extends Filter<Difficulty> {

        public DifficultyFilter() {}

        public DifficultyFilter(DifficultyFilter filter) {
            super(filter);
        }

        @Override
        public DifficultyFilter copy() {
            return new DifficultyFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter appUserId;

    private StringFilter courseName;

    private DifficultyFilter difficulty;

    private Boolean distinct;

    public CourseCriteria() {}

    public CourseCriteria(CourseCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.appUserId = other.optionalAppUserId().map(LongFilter::copy).orElse(null);
        this.courseName = other.optionalCourseName().map(StringFilter::copy).orElse(null);
        this.difficulty = other.optionalDifficulty().map(DifficultyFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CourseCriteria copy() {
        return new CourseCriteria(this);
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

    public StringFilter getCourseName() {
        return courseName;
    }

    public Optional<StringFilter> optionalCourseName() {
        return Optional.ofNullable(courseName);
    }

    public StringFilter courseName() {
        if (courseName == null) {
            setCourseName(new StringFilter());
        }
        return courseName;
    }

    public void setCourseName(StringFilter courseName) {
        this.courseName = courseName;
    }

    public DifficultyFilter getDifficulty() {
        return difficulty;
    }

    public Optional<DifficultyFilter> optionalDifficulty() {
        return Optional.ofNullable(difficulty);
    }

    public DifficultyFilter difficulty() {
        if (difficulty == null) {
            setDifficulty(new DifficultyFilter());
        }
        return difficulty;
    }

    public void setDifficulty(DifficultyFilter difficulty) {
        this.difficulty = difficulty;
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
        final CourseCriteria that = (CourseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(appUserId, that.appUserId) &&
            Objects.equals(courseName, that.courseName) &&
            Objects.equals(difficulty, that.difficulty) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appUserId, courseName, difficulty, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalAppUserId().map(f -> "appUserId=" + f + ", ").orElse("") +
            optionalCourseName().map(f -> "courseName=" + f + ", ").orElse("") +
            optionalDifficulty().map(f -> "difficulty=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
