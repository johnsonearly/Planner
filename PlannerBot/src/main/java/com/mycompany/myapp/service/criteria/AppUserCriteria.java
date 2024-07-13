package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.AttentionSpan;
import com.mycompany.myapp.domain.enumeration.Chronotype;
import com.mycompany.myapp.domain.enumeration.Gender;
import com.mycompany.myapp.domain.enumeration.ReadingType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.AppUser} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AppUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /app-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Chronotype
     */
    public static class ChronotypeFilter extends Filter<Chronotype> {

        public ChronotypeFilter() {}

        public ChronotypeFilter(ChronotypeFilter filter) {
            super(filter);
        }

        @Override
        public ChronotypeFilter copy() {
            return new ChronotypeFilter(this);
        }
    }

    /**
     * Class for filtering ReadingType
     */
    public static class ReadingTypeFilter extends Filter<ReadingType> {

        public ReadingTypeFilter() {}

        public ReadingTypeFilter(ReadingTypeFilter filter) {
            super(filter);
        }

        @Override
        public ReadingTypeFilter copy() {
            return new ReadingTypeFilter(this);
        }
    }

    /**
     * Class for filtering AttentionSpan
     */
    public static class AttentionSpanFilter extends Filter<AttentionSpan> {

        public AttentionSpanFilter() {}

        public AttentionSpanFilter(AttentionSpanFilter filter) {
            super(filter);
        }

        @Override
        public AttentionSpanFilter copy() {
            return new AttentionSpanFilter(this);
        }
    }

    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {}

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter age;

    private LongFilter appUserId;

    private ChronotypeFilter chronotype;

    private ReadingTypeFilter readingType;

    private AttentionSpanFilter attentionSpan;

    private GenderFilter gender;

    private Boolean distinct;

    public AppUserCriteria() {}

    public AppUserCriteria(AppUserCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.age = other.optionalAge().map(IntegerFilter::copy).orElse(null);
        this.appUserId = other.optionalAppUserId().map(LongFilter::copy).orElse(null);
        this.chronotype = other.optionalChronotype().map(ChronotypeFilter::copy).orElse(null);
        this.readingType = other.optionalReadingType().map(ReadingTypeFilter::copy).orElse(null);
        this.attentionSpan = other.optionalAttentionSpan().map(AttentionSpanFilter::copy).orElse(null);
        this.gender = other.optionalGender().map(GenderFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AppUserCriteria copy() {
        return new AppUserCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public Optional<IntegerFilter> optionalAge() {
        return Optional.ofNullable(age);
    }

    public IntegerFilter age() {
        if (age == null) {
            setAge(new IntegerFilter());
        }
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
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

    public ChronotypeFilter getChronotype() {
        return chronotype;
    }

    public Optional<ChronotypeFilter> optionalChronotype() {
        return Optional.ofNullable(chronotype);
    }

    public ChronotypeFilter chronotype() {
        if (chronotype == null) {
            setChronotype(new ChronotypeFilter());
        }
        return chronotype;
    }

    public void setChronotype(ChronotypeFilter chronotype) {
        this.chronotype = chronotype;
    }

    public ReadingTypeFilter getReadingType() {
        return readingType;
    }

    public Optional<ReadingTypeFilter> optionalReadingType() {
        return Optional.ofNullable(readingType);
    }

    public ReadingTypeFilter readingType() {
        if (readingType == null) {
            setReadingType(new ReadingTypeFilter());
        }
        return readingType;
    }

    public void setReadingType(ReadingTypeFilter readingType) {
        this.readingType = readingType;
    }

    public AttentionSpanFilter getAttentionSpan() {
        return attentionSpan;
    }

    public Optional<AttentionSpanFilter> optionalAttentionSpan() {
        return Optional.ofNullable(attentionSpan);
    }

    public AttentionSpanFilter attentionSpan() {
        if (attentionSpan == null) {
            setAttentionSpan(new AttentionSpanFilter());
        }
        return attentionSpan;
    }

    public void setAttentionSpan(AttentionSpanFilter attentionSpan) {
        this.attentionSpan = attentionSpan;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public Optional<GenderFilter> optionalGender() {
        return Optional.ofNullable(gender);
    }

    public GenderFilter gender() {
        if (gender == null) {
            setGender(new GenderFilter());
        }
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
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
        final AppUserCriteria that = (AppUserCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(age, that.age) &&
            Objects.equals(appUserId, that.appUserId) &&
            Objects.equals(chronotype, that.chronotype) &&
            Objects.equals(readingType, that.readingType) &&
            Objects.equals(attentionSpan, that.attentionSpan) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, appUserId, chronotype, readingType, attentionSpan, gender, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalAge().map(f -> "age=" + f + ", ").orElse("") +
            optionalAppUserId().map(f -> "appUserId=" + f + ", ").orElse("") +
            optionalChronotype().map(f -> "chronotype=" + f + ", ").orElse("") +
            optionalReadingType().map(f -> "readingType=" + f + ", ").orElse("") +
            optionalAttentionSpan().map(f -> "attentionSpan=" + f + ", ").orElse("") +
            optionalGender().map(f -> "gender=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
