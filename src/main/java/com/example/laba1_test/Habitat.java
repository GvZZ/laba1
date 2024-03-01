package com.example.laba1_test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Habitat {
    private static final int N1 = 1;
    private static final double P1 = 1;
    private static final int N2 =1;
    private static final double P2 = 1;

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

        if ((rand.nextDouble() < P1) && (second % N1 == 0)) {
            objects.add(new PhysicalPerson(rand.nextDouble() * 1000, rand.nextDouble() * 600));
            physicalPersonCount++;
        }

        if ((rand.nextDouble() < P2) && (second % N2 == 0)) {
            objects.add(new LegalPerson(rand.nextDouble() * 1000, rand.nextDouble() * 600));
            legalPersonCount++;
        }

        for (AbstractObject obj : objects) {
            obj.move(second);
        }
    }

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
