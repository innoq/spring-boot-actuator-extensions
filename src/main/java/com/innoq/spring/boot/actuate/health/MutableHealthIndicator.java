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

import static java.util.Objects.requireNonNull;

/**
 * A {@link HealthIndicator} implementation which decouples the query for the current health from
 * the actual health check execution.
 *
 * @author Michael Vitz
 * @since 0.1.0
 */
public final class MutableHealthIndicator implements HealthIndicator {

    private final HealthIndicator indicator;
    private Health health = Health.unknown().build();

    private MutableHealthIndicator(HealthIndicator indicator) {
        this.indicator = requireNonNull(indicator, "Indicator must not be null");
    }

    /**
     * Returns a {@link MutableHealthIndicator} instance which uses the given {@link
     * HealthIndicator} for health detection.
     *
     * @param indicator the indicator to use for health detection
     * @return a new instance which uses the given indicator for health detection
     */
    public static MutableHealthIndicator wrap(HealthIndicator indicator) {
        return new MutableHealthIndicator(indicator);
    }

    @Override
    public Health health() {
        return health;
    }

    /**
     * Trigger the actual health check.
     * <p>
     * This mutates the stored {@link Health} to use the new calculated one.
     */
    public void check() {
        this.health = indicator.health();
    }
}
