package br.com.fggs1.gs1project.model;

public class Product {

    private long id;
    private String name;
    private double value;

    public Product(long id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = value;
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
}
