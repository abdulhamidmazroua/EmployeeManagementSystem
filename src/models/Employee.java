package models;

public class Employee {
    private String employee_id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String gender;
    private String phone;
    private String position;
    private String image;
    private String date;

    public Employee(String employee_id, String username, String password, String first_name, String last_name, String gender, String phone, String position, String date) {
        this.employee_id = employee_id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.phone = phone;
        this.position = position;
        this.date = date;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getPosition() {
        return position;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }
}
