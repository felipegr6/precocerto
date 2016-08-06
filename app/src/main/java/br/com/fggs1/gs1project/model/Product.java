package br.com.fggs1.gs1project.model;

public class Product {

    private long id;
    private String name;
    private double value;
    private String description;
    private String code;
    private String category;
    private String url;

    public Product() {
        // Empty Constructor
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    @Override public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", value=" + value +
            ", description='" + description + '\'' +
            ", code='" + code + '\'' +
            ", category='" + category + '\'' +
            ", url='" + url + '\'' +
            '}';
    }
}
