package org.du6ak.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Reading{
//    private static List<String> readingTypes = new ArrayList<>(Arrays.asList("холодная вода", "горячая вода", "электричество"));//список типов
    private String type; // тип счетчика (отопление, горячая вода, холодная вода)
    private Long contractNumber; // номер договора
    private int value; // показание счетчика
    private int month; // дата подачи показаний

    public Reading(String type, Long contractNumber, int value, int month) {
        this.type = type;
        this.contractNumber = contractNumber;
        this.value = value;
        this.month = month;
    }
}