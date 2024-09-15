
package loginfxml;


public class Table {
    private int tableId;
    private String status;
    private String name;
    private String phoneNumber;

    public Table(int tableId, String status, String name, String phoneNumber) {
        this.tableId = tableId;
        this.status = status;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
