package model;

public class Vehicle {
    private final String licensePlate;
    private final String ownerName;

    public Vehicle(String licensePlate, String ownerName) {
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public String toString() {
        return licensePlate + " - " + ownerName;
    }
}