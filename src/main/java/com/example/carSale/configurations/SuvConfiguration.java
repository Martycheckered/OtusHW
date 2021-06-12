package com.example.carSale.configurations;

import com.example.carSale.Configurable;

public class SuvConfiguration implements Configurable {
    @Override
    public String makeConfiguration() {
        return "SUV";
    }
}

