package org.du6ak.models;

import lombok.Data;

import java.time.Month;

/**
 * A class that represents a reading from a meter.
 */
@Data
public class Reading {

    /**
     * The type of meter (heating, hot water, cold water).
     */
    private String type;

    /**
     * The contract number associated with the meter.
     */
    private Long contractNumber;

    /**
     * The meter reading.
     */
    private int value;

    /**
     * The month the reading was taken.
     */
    private Month month;

    /**
     * Creates a new Reading object.
     *
     * @param type           The type of meter (heating, hot water, cold water).
     * @param contractNumber The contract number associated with the meter.
     * @param value          The meter reading.
     * @param month          The month the reading was taken.
     */
    public Reading(String type, Long contractNumber, int value, Month month) {
        this.type = type;
        this.contractNumber = contractNumber;
        this.value = value;
        this.month = month;
    }
}