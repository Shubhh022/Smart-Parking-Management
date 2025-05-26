package ui;

import dao.ParkingDAO;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;

import java.util.logging.Logger;

public class ParkingUI extends JFrame {
    private static final Logger logger = Logger.getLogger(ParkingUI.class.getName());
    private final ParkingDAO parkingDAO;
    private JTextField licensePlateField;
    private JTextField ownerField;
    private JTextArea statusArea;

    public ParkingUI() {
        parkingDAO = new ParkingDAO();

        setTitle("Parking Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        add(createInputPanel());
        add(createButtonPanel());
        add(createStatusArea());

        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("License Plate:"));
        licensePlateField = new JTextField(10);
        inputPanel.add(licensePlateField);
        inputPanel.add(new JLabel("Owner Name:"));
        ownerField = new JTextField(10);
        inputPanel.add(ownerField);
        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton parkButton = new JButton("Park Vehicle");
        JButton removeButton = new JButton("Remove Vehicle");
        JButton viewButton = new JButton("View Parking Status");

        parkButton.addActionListener(e -> parkVehicle());
        removeButton.addActionListener(e -> removeVehicle());
        viewButton.addActionListener(e -> viewParkingStatus());

        buttonPanel.add(parkButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewButton);

        return buttonPanel;
    }

    private JScrollPane createStatusArea() {
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        return new JScrollPane(statusArea);
    }

    private void parkVehicle() {
        String plate = licensePlateField.getText();
        String owner = ownerField.getText();

        if (plate.isEmpty() || owner.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both license plate and owner name!", "Error", JOptionPane.ERROR_MESSAGE);
            logger.warning("Attempt to park vehicle with missing details.");
            return;
        }

        Vehicle vehicle = new Vehicle(plate, owner);
        boolean parked = parkingDAO.parkVehicle(vehicle);

        JOptionPane.showMessageDialog(this, parked ? "Vehicle Parked Successfully!" : "No Available Parking Slots!", "Info", JOptionPane.INFORMATION_MESSAGE);
        logger.info("Parking attempt: " + plate + " - " + (parked ? "Success" : "Failed"));

        licensePlateField.setText("");
        ownerField.setText("");
    }

    private void removeVehicle() {
        String plate = licensePlateField.getText();

        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a license plate to remove!", "Error", JOptionPane.ERROR_MESSAGE);
            logger.warning("Attempt to remove vehicle without license plate.");
            return;
        }

        boolean removed = parkingDAO.removeVehicle(plate);
        JOptionPane.showMessageDialog(this, removed ? "Vehicle Removed!" : "Vehicle Not Found!", "Info", JOptionPane.INFORMATION_MESSAGE);
        logger.info("Vehicle removal attempt: " + plate + " - " + (removed ? "Success" : "Failed"));

        licensePlateField.setText("");
    }

    private void viewParkingStatus() {
        String status = parkingDAO.getParkingStatus();
        statusArea.setText(status);
        logger.info("Parking status viewed.");
    }
}