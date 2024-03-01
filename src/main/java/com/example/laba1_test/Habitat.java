package com.example.laba1_test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Habitat {
    private static final int N1 = 5; // интервал для физических лиц в секундах
    private static final double P1 = 0.7; // вероятность для физических лиц
    private static final int N2 =3; // интервал для юридических лиц в секундах
    private static final double P2 = 0.7; // вероятность для юридических лиц

    private List<AbstractObject> objects;
    private int physicalPersonCount;
    private int legalPersonCount;
    public Habitat() {

        objects = new ArrayList<>();
        physicalPersonCount = 0;
        legalPersonCount = 0;
    }

    public void update(int second) {
        Random rand = new Random();

        // Генерация физических лиц
        if ((rand.nextDouble() < P1) && (second % N1 == 0)) {
            objects.add(new PhysicalPerson(rand.nextDouble() * 1100, rand.nextDouble() * 650));
            physicalPersonCount++;
        }

        // Генерация юридических лиц
        if ((rand.nextDouble() < P2) && (second % N2 == 0)) {
            objects.add(new LegalPerson(rand.nextDouble() * 1100, rand.nextDouble() * 650));
            legalPersonCount++;
        }

        // Дополнительные действия по обновлению объектов и их положения
        for (AbstractObject obj : objects) {
            obj.move(second);
        }
    }
    // Геттеры для получения количества объектов
    public int getPhysicalPersonCount() {
        return physicalPersonCount;
    }

    public int getLegalPersonCount() {
        return legalPersonCount;
    }
    public List<AbstractObject> getObjects() {
        return objects;
    }

}
