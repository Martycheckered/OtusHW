package com.example.carSale;

import com.example.carSale.configurations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.carSale")
public class SpringConfig {

    @Bean
    public ClassicConfiguration classicConfiguration () {
        return new ClassicConfiguration();
    }

    @Bean
    public SportConfiguration sportConfiguration () {
        return new SportConfiguration();
    }

    @Bean
    public ConvertibleConfiguration convertibleConfiguration () {
        return new ConvertibleConfiguration();
    }

    @Bean
    public MinivanConfiguration minivanConfiguration () {
        return new MinivanConfiguration();
    }

    @Bean
    public SuvConfiguration suvConfiguration () {
        return new SuvConfiguration();
    }
    @Bean
    public Car classicCar () {
        return new Car(classicConfiguration());
    }

    @Bean
    public Car sportCar () {
        return new Car(sportConfiguration());
    }

    @Bean
    public Car convertibleCar () {
        return new Car(convertibleConfiguration());
    }
    @Bean
    public Car minivanCar () {
        return new Car(minivanConfiguration());
    }
    @Bean
    public Car suvCar () {
        return new Car(suvConfiguration());
    }
}
