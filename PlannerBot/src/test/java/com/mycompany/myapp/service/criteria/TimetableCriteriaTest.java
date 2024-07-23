package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TimetableCriteriaTest {

    @Test
    void newTimetableCriteriaHasAllFiltersNullTest() {
        var timetableCriteria = new TimetableCriteria();
        assertThat(timetableCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void timetableCriteriaFluentMethodsCreatesFiltersTest() {
        var timetableCriteria = new TimetableCriteria();

        setAllFilters(timetableCriteria);

        assertThat(timetableCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void timetableCriteriaCopyCreatesNullFilterTest() {
        var timetableCriteria = new TimetableCriteria();
        var copy = timetableCriteria.copy();

        assertThat(timetableCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(timetableCriteria)
        );
    }

    @Test
    void timetableCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var timetableCriteria = new TimetableCriteria();
        setAllFilters(timetableCriteria);

        var copy = timetableCriteria.copy();

        assertThat(timetableCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(timetableCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var timetableCriteria = new TimetableCriteria();

        assertThat(timetableCriteria).hasToString("TimetableCriteria{}");
    }

    private static void setAllFilters(TimetableCriteria timetableCriteria) {
        timetableCriteria.id();
        timetableCriteria.appUserId();
        timetableCriteria.dayOfWeek();
        timetableCriteria.dateOfActivity();
        timetableCriteria.startTime();
        timetableCriteria.endTime();
        timetableCriteria.activity();
        timetableCriteria.isDone();
        timetableCriteria.levelOfImportance();
        timetableCriteria.distinct();
    }

    private static Condition<TimetableCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getAppUserId()) &&
                condition.apply(criteria.getDayOfWeek()) &&
                condition.apply(criteria.getDateOfActivity()) &&
                condition.apply(criteria.getStartTime()) &&
                condition.apply(criteria.getEndTime()) &&
                condition.apply(criteria.getActivity()) &&
                condition.apply(criteria.getIsDone()) &&
                condition.apply(criteria.getLevelOfImportance()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TimetableCriteria> copyFiltersAre(TimetableCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getAppUserId(), copy.getAppUserId()) &&
                condition.apply(criteria.getDayOfWeek(), copy.getDayOfWeek()) &&
                condition.apply(criteria.getDateOfActivity(), copy.getDateOfActivity()) &&
                condition.apply(criteria.getStartTime(), copy.getStartTime()) &&
                condition.apply(criteria.getEndTime(), copy.getEndTime()) &&
                condition.apply(criteria.getActivity(), copy.getActivity()) &&
                condition.apply(criteria.getIsDone(), copy.getIsDone()) &&
                condition.apply(criteria.getLevelOfImportance(), copy.getLevelOfImportance()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
