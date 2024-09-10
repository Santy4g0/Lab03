/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cronometro;

/**
 *
 * @author Juan David Ruiz Gomez - Elkin Santiago Rodriguez
 */
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cronometro {
    private final Timer timer;
    private final SimpleDateFormat sdf;

    public Cronometro() {
        timer = new Timer(true);
        sdf = new SimpleDateFormat("HH:mm:ss");
        startTimer();
    }

    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(sdf.format(new Date()));
            }
        }, 0, 1000);
    }

    public static void main(String[] args) {
        Cronometro cronometro = new Cronometro();
    }
}