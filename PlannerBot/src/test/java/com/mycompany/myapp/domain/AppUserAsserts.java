package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AppUserAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserAllPropertiesEquals(AppUser expected, AppUser actual) {
        assertAppUserAutoGeneratedPropertiesEquals(expected, actual);
        assertAppUserAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserAllUpdatablePropertiesEquals(AppUser expected, AppUser actual) {
        assertAppUserUpdatableFieldsEquals(expected, actual);
        assertAppUserUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserAutoGeneratedPropertiesEquals(AppUser expected, AppUser actual) {
        assertThat(expected)
            .as("Verify AppUser auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserUpdatableFieldsEquals(AppUser expected, AppUser actual) {
        assertThat(expected)
            .as("Verify AppUser relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getAge()).as("check age").isEqualTo(actual.getAge()))
            .satisfies(e -> assertThat(e.getAppUserId()).as("check appUserId").isEqualTo(actual.getAppUserId()))
            .satisfies(e -> assertThat(e.getChronotype()).as("check chronotype").isEqualTo(actual.getChronotype()))
            .satisfies(e -> assertThat(e.getReadingType()).as("check readingType").isEqualTo(actual.getReadingType()))
            .satisfies(e -> assertThat(e.getAttentionSpan()).as("check attentionSpan").isEqualTo(actual.getAttentionSpan()))
            .satisfies(e -> assertThat(e.getGender()).as("check gender").isEqualTo(actual.getGender()))
            .satisfies(e -> assertThat(e.getReadingStrategy()).as("check readingStrategy").isEqualTo(actual.getReadingStrategy()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserUpdatableRelationshipsEquals(AppUser expected, AppUser actual) {}
}
