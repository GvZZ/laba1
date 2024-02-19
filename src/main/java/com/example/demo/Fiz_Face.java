package com.example.demo;

import java.util.Random;

public class Fiz_Face extends abstractive {
    public Fiz_Face(int i) {
        this.Chance = i;
        this.Amount =0;
    }

    @Override

    void Birth() {
        Random r = new Random();
        int rand = r.nextInt(100) + 1;
        if (rand >= this.Chance)
        {
            this.Amount += 1;
        }
    }
}