package cu.lacumbre.auditor.model;

import java.time.LocalDate;

public class Worker extends IDSuperClass {

    private String lastName;
    private String name;
    private Role role;
    private String dni;
    private String address;
    private String phonePrefix;
    private String phone;
    private String email;
    private final LocalDate enrollDate;
    private boolean activated;
    private String code;

    public Worker(String name, String lastName, Role role, String dni, String address, String phonePrefix, String phone, String email, LocalDate date, boolean activated, String code) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phonePrefix = phonePrefix;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.role = role;
        this.enrollDate = date;
        this.activated = activated;
        this.code = code;
    }

    public Worker(int id, String name, String lastName, Role role, String dni, String address, String phonePrefix, String phone, String email, LocalDate date, boolean activated, String code) {
        super(id);
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phonePrefix = phonePrefix;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.role = role;
        this.enrollDate = date;
        this.activated = activated;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return name + " " + lastName;
    }

    public String getDni() {
        return dni;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public void setFullName(String fullName) {
        String string = (String) fullName;
        String[] split = string.split(" ");
        this.name = split.length == 4 ? split[0] + " " + split[1] : split.length == 3 ? split[0] : "";
        this.lastName = split.length == 4 ? split[2] + " " + split[3] : split.length == 3 ? split[1] + " " + split[2] : "";
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name + " " + lastName;
    }

}
