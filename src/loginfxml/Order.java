
 
package loginfxml;


public class Order {
    private int orderId;
    
    private int productId;
    private String userName;
    private String productName;
    
    // Constructor
    public Order(int orderId, int productId,String userName, String productName) {
        this.orderId = orderId;
        
        this.productId = productId;
        this.userName = userName;
this.productName = productName;
    }
    
    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

  

   

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getProductName() {
    return productName;
}

public void setProductName(String productName) {
    this.productName = productName;
}
}
/*public class Order {
    private int orderId;
    private int userId;
    private int productId;
    private String userName;

    public Order(int orderId, int userId, int productId, String userName) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.userName = userName;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
*/