package com.example.carSale.configurations;

import com.example.carSale.Configurable;

public class ClassicConfiguration  implements Configurable {

    @Override
    public String makeConfiguration() {
        return "Classic";
    }
}
