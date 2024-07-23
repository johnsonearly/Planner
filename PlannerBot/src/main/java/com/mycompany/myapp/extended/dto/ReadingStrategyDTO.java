package com.mycompany.myapp.extended.dto;

import com.mycompany.myapp.domain.enumeration.ReadingStrategy;

public class ReadingStrategyDTO {
    private ReadingStrategy strategy;
    private String description;

    public ReadingStrategyDTO() {
    }

    public ReadingStrategy getStrategy() {
        return this.strategy;
    }

    public String getDescription() {
        return this.description;
    }

    public void setStrategy(ReadingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ReadingStrategyDTO)) return false;
        final ReadingStrategyDTO other = (ReadingStrategyDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$strategy = this.getStrategy();
        final Object other$strategy = other.getStrategy();
        if (this$strategy == null ? other$strategy != null : !this$strategy.equals(other$strategy)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ReadingStrategyDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $strategy = this.getStrategy();
        result = result * PRIME + ($strategy == null ? 43 : $strategy.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        return result;
    }

    public String toString() {
        return "ReadingStrategyDTO(strategy=" + this.getStrategy() + ", description=" + this.getDescription() + ")";
    }
}
