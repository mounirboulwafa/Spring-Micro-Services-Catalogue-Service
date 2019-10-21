package com.bdcc.catalogueservice;

import com.bdcc.catalogueservice.dao.CategoryRepository;
import com.bdcc.catalogueservice.dao.ProductRepository;
import com.bdcc.catalogueservice.entities.Category;
import com.bdcc.catalogueservice.entities.Product;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringMicroServicesCatalogueServiceApplication {

    public static void main(String[] args) {
        System.out.println("********************* Works *********************");
        SpringApplication.run(SpringMicroServicesCatalogueServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            categoryRepository.deleteAll();

            Stream.of("C1 Ordinateur", "C2 Imprimente").forEach(c -> {
                categoryRepository.save(new Category(c.split(" ")[0], c.split(" ")[1], new ArrayList()));
            });

            categoryRepository.findAll().forEach(System.out::println);


            Category c1 = categoryRepository.findById("C1").get();
            Stream.of("Dell Xps", "Lenovo 720", "Macbook Pro 2019", "Hp 7556").forEach(name -> {
                Product p = productRepository.save(new Product(null, name, Math.random() * 1000, c1));
                c1.getProducts().add(p);
                categoryRepository.save(c1);
            });

            Category c2 = categoryRepository.findById("C2").get();
            Stream.of("Hp Lazer Jet 4561", "Konika", "Toshiba", "Hp AIO 7556").forEach(name -> {
                Product p = productRepository.save(new Product(null, name, Math.random() * 1000, c1));
                c2.getProducts().add(p);
                categoryRepository.save(c2);
            });


            productRepository.findAll().forEach(System.out::println);
        };
    }
}
