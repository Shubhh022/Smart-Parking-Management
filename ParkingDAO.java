package dao;

import database.DatabaseConnection;
import model.Vehicle;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParkingDAO {
    private static final Logger logger = Logger.getLogger(ParkingDAO.class.getName());
    private final Connection conn;

    public ParkingDAO() {
        conn = DatabaseConnection.getConnection();
        if (conn == null) {
            logger.severe("Database connection failed!");
            throw new RuntimeException("Database connection failed!");
        }
    }

    public boolean parkVehicle(Vehicle vehicle) {
        String query = "INSERT INTO ParkingSlots (license_plate, owner_name) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vehicle.getLicensePlate());
            stmt.setString(2, vehicle.getOwnerName());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error inserting vehicle: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean removeVehicle(String plate) {
        String query = "DELETE FROM ParkingSlots WHERE license_plate = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, plate);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error removing vehicle: " + e.getMessage());
            return false;
        }
    }

    public String getParkingStatus() {
        StringBuilder status = new StringBuilder("Parking Status:\n");
        String query = "SELECT * FROM ParkingSlots";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                status.append("Slot ID: ").append(rs.getInt("slot_id"))
                        .append(" - License: ").append(rs.getString("license_plate"))
                        .append(" (Owner: ").append(rs.getString("owner_name"))
                        .append(")\n");
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving parking status: " + e.getMessage());
        }
        return status.toString();
    }
}