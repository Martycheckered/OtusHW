package com.example.carSale;

public class Car {

    private Configurable configuration;

    public Car(Configurable configuration) {
      this.configuration = configuration;
    }
    public String readyForSale () {

        return "Car Ready In : " + configuration.makeConfiguration();
    }

}
