package com.example.laba1_test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Habitat {
    private static final int N1 = 1; // интервал для физических лиц в секундах
    private static final double P1 = 0.5; // вероятность для физических лиц
    private static final int N2 = 1; // интервал для юридических лиц в секундах
    private static final double P2 = 0.5; // вероятность для юридических лиц

    private List<AbstractObject> objects;

    public Habitat() {
        objects = new ArrayList<>();
    }

    public void update(double elapsedTime) {
        Random rand = new Random();

        // Генерация физических лиц
        if (rand.nextDouble() < P1 && elapsedTime % N1 == 0) {
            objects.add(new PhysicalPerson(rand.nextDouble() * 800, rand.nextDouble() * 600));
            // Предположим, что ширина окна - 800, высота - 600 (можете использовать константы или параметры окна)
        }

        // Генерация юридических лиц
        if (rand.nextDouble() < P2 && elapsedTime % N2 == 0) {
            objects.add(new LegalPerson(rand.nextDouble() * 800, rand.nextDouble() * 600));
        }

        // Дополнительные действия по обновлению объектов и их положения
        for (AbstractObject obj : objects) {
            obj.move(elapsedTime);
        }
    }

    public List<AbstractObject> getObjects() {
        return objects;
    }

    // Дополнительные методы и поля
    // ...
}
