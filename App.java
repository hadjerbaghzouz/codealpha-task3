//task 1 
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

class Employee {
    private String id;
    private String name;
    private double hourlyRate;

    public Employee(String id, String name, double hourlyRate) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}

class PayrollSystem {
    private List<Employee> employees;

    public PayrollSystem() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public double calculateSalary(Employee employee, int hoursWorked) {
        return employee.getHourlyRate() * hoursWorked;
    }

    public Employee[] getEmployees() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmployees'");
    }
}



class PayrollSystemGUI {
    private JFrame frame;
    private JTextField employeeIdField;
    private JTextField hoursWorkedField;
    private JTextArea payStubArea;
    private PayrollSystem payrollSystem;

    public PayrollSystemGUI() {
        frame = new JFrame("Employee Payroll System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        payrollSystem = new PayrollSystem();

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel employeeIdLabel = new JLabel("Employee ID:");
        employeeIdLabel.setBounds(10, 20, 80, 25);
        panel.add(employeeIdLabel);

        employeeIdField = new JTextField(20);
        employeeIdField.setBounds(100, 20, 165, 25);
        panel.add(employeeIdField);

        JLabel hoursWorkedLabel = new JLabel("Hours Worked:");
        hoursWorkedLabel.setBounds(10, 50, 80, 25);
        panel.add(hoursWorkedLabel);

        hoursWorkedField = new JTextField(20);
        hoursWorkedField.setBounds(100, 50, 165, 25);
        panel.add(hoursWorkedField);

        JButton calculateButton = new JButton("Calculate Salary");
        calculateButton.setBounds(10, 80, 150, 25);
        panel.add(calculateButton);

        payStubArea = new JTextArea();
        payStubArea.setBounds(10, 110, 350, 120);
        panel.add(payStubArea);

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateSalary();
            }
        });
    }

    private void calculateSalary() {
        String employeeId = employeeIdField.getText();
        int hoursWorked = Integer.parseInt(hoursWorkedField.getText());

        for (Employee employee : payrollSystem.getEmployees()) {
            if (employee.getId().equals(employeeId)) {
                double salary = payrollSystem.calculateSalary(employee, hoursWorked);
                payStubArea.setText("Employee ID: " + employee.getId() +
                                    "\nName: " + employee.getName() +
                                    "\nHourly Rate: $" + employee.getHourlyRate() +
                                    "\nHours Worked: " + hoursWorked +
                                    "\nTotal Salary: $" + salary);
                return;
            }
        }

        payStubArea.setText("Employee not found.");
    }

    public static void main(String[] args) {
        new PayrollSystemGUI();
    }
}


//task2



class Car {
    private String id;
    private String brand;
    private String model;
    private boolean available;

    public Car(String id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class Reservation {
    private String userId;
    private Car car;
    private String reservationDate;

    public Reservation(String userId, Car car, String reservationDate) {
        this.userId = userId;
        this.car = car;
        this.reservationDate = reservationDate;
    }

    public String getUserId() {
        return userId;
    }

    public Car getCar() {
        return car;
    }

    public String getReservationDate() {
        return reservationDate;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Reservation> reservations;

    public CarRentalSystem() {
        this.cars = new ArrayList<>();
        this.reservations = new ArrayList<>();
        initializeCars();
    }

    private void initializeCars() {
        cars.add(new Car("1", "Toyota", "Camry"));
        cars.add(new Car("2", "Honda", "Accord"));
        cars.add(new Car("3", "Ford", "Mustang"));
    }

    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.isAvailable()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    public void makeReservation(String userId, Car car, String reservationDate) {
        car.setAvailable(false);
        reservations.add(new Reservation(userId, car, reservationDate));
    }

    public List<Reservation> getRentalHistory(String userId) {
        List<Reservation> userReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getUserId().equals(userId)) {
                userReservations.add(reservation);
            }
        }
        return userReservations;
    }
}





 class CarRentalSystemGUI extends Application {
    private CarRentalSystem carRentalSystem;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        carRentalSystem = new CarRentalSystem();
        primaryStage.setTitle("Car Rental System");

        VBox vbox = new VBox(10);
        Scene scene = new Scene(vbox, 400, 300);

        Label titleLabel = new Label("Available Cars:");
        ListView<Car> carListView = new ListView<>();
        carListView.getItems().addAll(carRentalSystem.getAvailableCars());

        Button reserveButton = new Button("Reserve Car");
        reserveButton.setOnAction(e -> {
            Car selectedCar = carListView.getSelectionModel().getSelectedItem();
            if (selectedCar != null) {
                makeReservation(selectedCar);
                updateCarList(carListView);
            } else {
                showAlert("Select a car to reserve.");
            }
        });

        vbox.getChildren().addAll(titleLabel, carListView, reserveButton);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void makeReservation(Car car) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reservation");
        dialog.setHeaderText("Enter your user ID:");
        dialog.setContentText("User ID:");

        String userId = dialog.showAndWait().orElse("");

        if (!userId.isEmpty()) {
            carRentalSystem.makeReservation(userId, car, "Current Date"); // You can modify the reservation date logic
            showAlert("Reservation successful!");
        } else {
            showAlert("Invalid user ID.");
        }
    }

    private void updateCarList(ListView<Car> carListView) {
        carListView.getItems().clear();
        carListView.getItems().addAll(carRentalSystem.getAvailableCars());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
