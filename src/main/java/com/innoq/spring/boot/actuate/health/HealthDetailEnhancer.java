/**
 * Copyright 2017 innoQ Deutschland GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.innoq.spring.boot.actuate.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

/**
 * This class can be used to enhance a given {@link HealthIndicator} with additional details.
 *
 * @author Michael Vitz
 * @since 0.1.0
 */
public final class HealthDetailEnhancer implements HealthIndicator {

    private final HealthIndicator indicator;
    private final Map<String, Supplier<?>> details;

    private HealthDetailEnhancer(HealthIndicator indicator, Map<String, Supplier<?>> details) {
        this.indicator = requireNonNull(indicator, "Indicator must not be null");
        this.details = unmodifiableMap(requireNonNull(details, "Details must not be null"));
    }

    /**
     * Creates a new {@link Builder} for constructing a new {@link HealthDetailEnhancer}.
     *
     * @return a new builder for creating a new enhancer instance
     */
    public static Builder create() {
        return new Builder();
    }

    @Override
    public Health health() {
        final Health health = indicator.health();

        final Health.Builder builder = new Health.Builder(health.getStatus());
        details.forEach((k, v) -> builder.withDetail(k, v.get()));
        health.getDetails().forEach(builder::withDetail);

        return builder.build();
    }

    /**
     * Builder for constructing a new {@link HealthDetailEnhancer} instance.
     */
    public static final class Builder {

        private final Map<String, Supplier<?>> details = new LinkedHashMap<>();

        private Builder() {
        }

        /**
         * Adds a fixed detail.
         *
         * @param key   the key used to store the details value into the health object
         * @param value the details value to store
         * @return this builders instance for method chaining
         */
        public Builder withDetail(String key, Object value) {
            requireNonNull(value, "Value must not be null");
            return withDetail(key, () -> value);
        }

        /**
         * Adds a dynamic detail. The {@link Supplier} is used for constructing the details value
         * every time the health is calculated.
         *
         * @param key   the key used to store the details value into the health object
         * @param value a supplier which is used to calculate the details value
         * @return this builders instance for method chaining
         */
        public Builder withDetail(String key, Supplier<?> value) {
            requireNonNull(key, "Key must not be null");
            requireNonNull(value, "Value must not be null");
            details.put(key, value);
            return this;
        }

        /**
         * Returns a new {@link HealthDetailEnhancer} with the former added details which uses the
         * given {@link HealthIndicator} for the actual health check.
         *
         * @param indicator the indicator to enhance with the details
         * @return a new enhancer which enhances the given indicator with the former set details
         */
        public HealthDetailEnhancer enhance(HealthIndicator indicator) {
            return new HealthDetailEnhancer(indicator, details);
        }
    }
}
