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

import org.springframework.boot.actuate.health.AbstractHealthAggregator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.Status;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * {@link HealthAggregator} which returns a fixed {@link Status} instead of aggregating the real
 * statuses of the given healths.
 *
 * @author Michael Vitz
 * @since 0.1.0
 */
public final class FixedHealthAggregator extends AbstractHealthAggregator {

    /**
     * {@link FixedHealthAggregator} that always returns a health instance with status {@link
     * Status#UNKNOWN}.
     */
    public static final FixedHealthAggregator UNKNOWN = new FixedHealthAggregator(Status.UNKNOWN);

    /**
     * {@link FixedHealthAggregator} that always returns a health instance with status {@link
     * Status#UP}.
     */
    public static final FixedHealthAggregator UP = new FixedHealthAggregator(Status.UP);

    /**
     * {@link FixedHealthAggregator} that always returns a health instance with status {@link
     * Status#DOWN}.
     */
    public static final FixedHealthAggregator DOWN = new FixedHealthAggregator(Status.DOWN);

    /**
     * {@link FixedHealthAggregator} that always returns a health instance with status {@link
     * Status#OUT_OF_SERVICE}.
     */
    public static final FixedHealthAggregator OUT_OF_SERVICE =
        new FixedHealthAggregator(Status.OUT_OF_SERVICE);


    private final Status status;

    private FixedHealthAggregator(Status status) {
        this.status = requireNonNull(status, "Status must not be null");
    }

    /**
     * Returns a {@link FixedHealthAggregator} which always returns the given {@link Status}.
     *
     * @param status the status which is always returned by this aggregator
     * @return an instance which always returns a health with the given status
     */
    public static FixedHealthAggregator withStatus(Status status) {
        if (status == Status.UNKNOWN) {
            return UNKNOWN;
        } else if (status == Status.UP) {
            return UP;
        } else if (status == Status.DOWN) {
            return DOWN;
        } else if (status == Status.OUT_OF_SERVICE) {
            return OUT_OF_SERVICE;
        }
        return new FixedHealthAggregator(status);
    }

    /**
     * Returns a {@link FixedHealthAggregator} which always returns {@link Status#UNKNOWN}.
     *
     * @return {@link #UNKNOWN}
     */
    public static FixedHealthAggregator unknown() {
        return UNKNOWN;
    }

    /**
     * Returns a {@link FixedHealthAggregator} which always returns {@link Status#UP}.
     *
     * @return {@link #UP}
     */
    public static FixedHealthAggregator up() {
        return UP;
    }

    /**
     * Returns a {@link FixedHealthAggregator} which always returns {@link Status#DOWN}.
     *
     * @return {@link #DOWN}
     */
    public static FixedHealthAggregator down() {
        return DOWN;
    }

    /**
     * Returns a {@link FixedHealthAggregator} which always returns {@link Status#OUT_OF_SERVICE}.
     *
     * @return {@link #OUT_OF_SERVICE}
     */
    public static FixedHealthAggregator outOfService() {
        return OUT_OF_SERVICE;
    }

    @Override
    protected Status aggregateStatus(List<Status> candidates) {
        return status;
    }

    /**
     * Returns the fixed {@link Status} this {@link FixedHealthAggregator} returns on every call to
     * {@link #aggregate(Map)}.
     *
     * @return the fixed status of this instance
     */
    public Status getStatus() {
        return status;
    }
}
