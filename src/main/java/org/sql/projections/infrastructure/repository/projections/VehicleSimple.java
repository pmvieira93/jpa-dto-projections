package org.sql.projections.infrastructure.repository.projections;

import java.util.Objects;

/**
 *
 */
public class VehicleSimple {

    private String brand;
    private String model;
    private int year;
    private int month;

    public VehicleSimple() {
    }

    public VehicleSimple(String brand, String model, int year, int month) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.month = month;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleSimple that = (VehicleSimple) o;
        return year == that.year && month == that.month && Objects.equals(brand, that.brand) && Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, year, month);
    }
}
