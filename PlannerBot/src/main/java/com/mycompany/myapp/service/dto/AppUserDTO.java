package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.AttentionSpan;
import com.mycompany.myapp.domain.enumeration.Chronotype;
import com.mycompany.myapp.domain.enumeration.Gender;
import com.mycompany.myapp.domain.enumeration.ReadingStrategy;
import com.mycompany.myapp.domain.enumeration.ReadingType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.AppUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserDTO implements Serializable {

    private Long id;

    private String name;

    private Integer age;

    private Long appUserId;

    private Chronotype chronotype;

    private ReadingType readingType;

    private AttentionSpan attentionSpan;

    private Gender gender;

    private ReadingStrategy readingStrategy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Chronotype getChronotype() {
        return chronotype;
    }

    public void setChronotype(Chronotype chronotype) {
        this.chronotype = chronotype;
    }

    public ReadingType getReadingType() {
        return readingType;
    }

    public void setReadingType(ReadingType readingType) {
        this.readingType = readingType;
    }

    public AttentionSpan getAttentionSpan() {
        return attentionSpan;
    }

    public void setAttentionSpan(AttentionSpan attentionSpan) {
        this.attentionSpan = attentionSpan;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ReadingStrategy getReadingStrategy() {
        return readingStrategy;
    }

    public void setReadingStrategy(ReadingStrategy readingStrategy) {
        this.readingStrategy = readingStrategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserDTO)) {
            return false;
        }

        AppUserDTO appUserDTO = (AppUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserDTO{" +
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
