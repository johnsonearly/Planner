package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.AttentionSpan;
import com.mycompany.myapp.domain.enumeration.Chronotype;
import com.mycompany.myapp.domain.enumeration.Gender;
import com.mycompany.myapp.domain.enumeration.ReadingStrategy;
import com.mycompany.myapp.domain.enumeration.ReadingType;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A AppUser.
 */
@Entity
@Table(name = "app_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "app_user_id")
    private Long appUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "chronotype")
    private Chronotype chronotype;

    @Enumerated(EnumType.STRING)
    @Column(name = "reading_type")
    private ReadingType readingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "attention_span")
    private AttentionSpan attentionSpan;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "reading_strategy")
    private ReadingStrategy readingStrategy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AppUser name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public AppUser age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getAppUserId() {
        return this.appUserId;
    }

    public AppUser appUserId(Long appUserId) {
        this.setAppUserId(appUserId);
        return this;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Chronotype getChronotype() {
        return this.chronotype;
    }

    public AppUser chronotype(Chronotype chronotype) {
        this.setChronotype(chronotype);
        return this;
    }

    public void setChronotype(Chronotype chronotype) {
        this.chronotype = chronotype;
    }

    public ReadingType getReadingType() {
        return this.readingType;
    }

    public AppUser readingType(ReadingType readingType) {
        this.setReadingType(readingType);
        return this;
    }

    public void setReadingType(ReadingType readingType) {
        this.readingType = readingType;
    }

    public AttentionSpan getAttentionSpan() {
        return this.attentionSpan;
    }

    public AppUser attentionSpan(AttentionSpan attentionSpan) {
        this.setAttentionSpan(attentionSpan);
        return this;
    }

    public void setAttentionSpan(AttentionSpan attentionSpan) {
        this.attentionSpan = attentionSpan;
    }

    public Gender getGender() {
        return this.gender;
    }

    public AppUser gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ReadingStrategy getReadingStrategy() {
        return this.readingStrategy;
    }

    public AppUser readingStrategy(ReadingStrategy readingStrategy) {
        this.setReadingStrategy(readingStrategy);
        return this;
    }

    public void setReadingStrategy(ReadingStrategy readingStrategy) {
        this.readingStrategy = readingStrategy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUser)) {
            return false;
        }
        return getId() != null && getId().equals(((AppUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            ", appUserId=" + getAppUserId() +
            ", chronotype='" + getChronotype() + "'" +
            ", readingType='" + getReadingType() + "'" +
            ", attentionSpan='" + getAttentionSpan() + "'" +
            ", gender='" + getGender() + "'" +
            ", readingStrategy='" + getReadingStrategy() + "'" +
            "}";
    }
}
