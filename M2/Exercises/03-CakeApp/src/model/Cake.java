package model;

public class Cake {
    private String cakeId;
    private String name;
    private String description;
    private double price;
    private String status;
    private String category;

    public Cake() {}

    public Cake(String cakeId, String name, String description, double price, String status, String category) {
        this.cakeId = cakeId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.category = category;
    }

    // Getters
    public String getCakeId() { return cakeId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
    public String getCategory() { return category; }

    // Setters
    public void setCakeId(String cakeId) { this.cakeId = cakeId; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
    public void setCategory(String category) { this.category = category; }

    // Convert to file format
    public String toFileString() {
        return cakeId + "##" + name + "##" + description + "##" + price + "##" + status + "##" + category;
    }

    // Create from file string
    public static Cake fromFileString(String fileString) {
        String[] parts = fileString.split("##");
        if (parts.length == 6) {
            return new Cake(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4], parts[5]);
        }
        return null;
    }

    @Override
    public String toString() {
        return name + " - ₱" + String.format("%.2f", price);
    }
}
