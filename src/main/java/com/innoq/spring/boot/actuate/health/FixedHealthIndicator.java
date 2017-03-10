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
import org.springframework.boot.actuate.health.Status;

import static java.util.Objects.requireNonNull;

/**
 * A {@link HealthIndicator} that returns a fixed health on every call to {@link #health()}.
 *
 * @author Michael Vitz
 * @since 0.1.0
 */
public final class FixedHealthIndicator implements HealthIndicator {

    private static final FixedHealthIndicator UNKNOWN = new FixedHealthIndicator(Status.UNKNOWN);
    private static final FixedHealthIndicator UP = new FixedHealthIndicator(Status.UP);
    private static final FixedHealthIndicator DOWN = new FixedHealthIndicator(Status.DOWN);
    private static final FixedHealthIndicator OUT_OF_SERVICE =
        new FixedHealthIndicator(Status.OUT_OF_SERVICE);

    private final Health health;

    private FixedHealthIndicator(Status status) {
        this(Health.status(status).build());
    }

    private FixedHealthIndicator(Health health) {
        this.health = requireNonNull(health, "Health must not be null");
    }

    /**
     * Creates a new {@link FixedHealthIndicator} with the given health.
     *
     * @param health the health to fix this instance to
     * @return an new instance which always returns the given health
     */
    public static FixedHealthIndicator withHealth(Health health) {
        return new FixedHealthIndicator(health);
    }

    /**
     * Returns an {@link FixedHealthIndicator} instance which always returns a {@link Health} with
     * the given status.
     *
     * @param status the status which will always be returned
     * @return an instance which always returns a health with the given status
     */
    public static FixedHealthIndicator withStatus(Status status) {
        if (status == Status.UNKNOWN) {
            return UNKNOWN;
        } else if (status == Status.UP) {
            return UP;
        } else if (status == Status.DOWN) {
            return DOWN;
        } else if (status == Status.OUT_OF_SERVICE) {
            return OUT_OF_SERVICE;
        }
        return new FixedHealthIndicator(status);
    }

    /**
     * Returns a {@link FixedHealthIndicator} with the {@link Status#UNKNOWN} status.
     *
     * @return {@link #UNKNOWN}
     */
    public static FixedHealthIndicator unknown() {
        return UNKNOWN;
    }

    /**
     * Returns a {@link FixedHealthIndicator} with the {@link Status#UP} status.
     *
     * @return {@link #UP}
     */
    public static FixedHealthIndicator up() {
        return UP;
    }

    /**
     * Returns a {@link FixedHealthIndicator} with the {@link Status#DOWN} status.
     *
     * @return {@link #DOWN}
     */
    public static FixedHealthIndicator down() {
        return DOWN;
    }

    /**
     * Returns a {@link FixedHealthIndicator} with the {@link Status#OUT_OF_SERVICE} status.
     *
     * @return {@link #OUT_OF_SERVICE}
     */
    public static FixedHealthIndicator outOfService() {
        return OUT_OF_SERVICE;
    }

    @Override
    public Health health() {
        return health;
    }

    /**
     * Returns the fixed {@link Status} this {@link FixedHealthIndicator} returns on every call to
     * {@link #health()}.
     *
     * @return the fixed status of this instance
     */
    public Status getStatus() {
        return health.getStatus();
    }
}
