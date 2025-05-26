package model;

import java.util.logging.Logger;

public class ParkingSlot {
    private static final Logger logger = Logger.getLogger(ParkingSlot.class.getName());
    private final int slotId;
    private boolean isOccupied;
    private Vehicle vehicle;

    public ParkingSlot(int slotId) {
        this.slotId = slotId;
        this.isOccupied = false;
        this.vehicle = null;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.isOccupied = true;
        this.vehicle = vehicle;
        logger.info("Vehicle parked in slot " + slotId + ": " + vehicle);
    }

    public void removeVehicle() {
        if (isOccupied && vehicle != null) {
            logger.info("Vehicle removed from slot " + slotId + ": " + vehicle);
        } else {
            logger.warning("Attempt to remove vehicle from an empty slot: " + slotId);
        }
        this.isOccupied = false;
        this.vehicle = null;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getSlotId() {
        return slotId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return "Slot " + slotId + " -> " + (isOccupied ? vehicle.toString() : "Empty");
    }
}