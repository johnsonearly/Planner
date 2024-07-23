package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FreeTimeCriteriaTest {

    @Test
    void newFreeTimeCriteriaHasAllFiltersNullTest() {
        var freeTimeCriteria = new FreeTimeCriteria();
        assertThat(freeTimeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void freeTimeCriteriaFluentMethodsCreatesFiltersTest() {
        var freeTimeCriteria = new FreeTimeCriteria();

        setAllFilters(freeTimeCriteria);

        assertThat(freeTimeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void freeTimeCriteriaCopyCreatesNullFilterTest() {
        var freeTimeCriteria = new FreeTimeCriteria();
        var copy = freeTimeCriteria.copy();

        assertThat(freeTimeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(freeTimeCriteria)
        );
    }

    @Test
    void freeTimeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var freeTimeCriteria = new FreeTimeCriteria();
        setAllFilters(freeTimeCriteria);

        var copy = freeTimeCriteria.copy();

        assertThat(freeTimeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(freeTimeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var freeTimeCriteria = new FreeTimeCriteria();

        assertThat(freeTimeCriteria).hasToString("FreeTimeCriteria{}");
    }

    private static void setAllFilters(FreeTimeCriteria freeTimeCriteria) {
        freeTimeCriteria.id();
        freeTimeCriteria.start();
        freeTimeCriteria.end();
        freeTimeCriteria.distinct();
    }

    private static Condition<FreeTimeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getStart()) &&
                condition.apply(criteria.getEnd()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FreeTimeCriteria> copyFiltersAre(FreeTimeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getStart(), copy.getStart()) &&
                condition.apply(criteria.getEnd(), copy.getEnd()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
