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
    private Timer timer;
    private Timer alarmTimer;
    private SimpleDateFormat sdf;
    private long alarmTimeMinutes;
    private long alarmIntervalSeconds;
    private boolean alarmTriggered;

    public Cronometro(long alarmTimeMinutes, long alarmIntervalSeconds) {
        this.alarmTimeMinutes = alarmTimeMinutes;
        this.alarmIntervalSeconds = alarmIntervalSeconds;
        timer = new Timer(true);
        alarmTimer = new Timer(true);
        sdf = new SimpleDateFormat("HH:mm:ss");
        alarmTriggered = false;
        startTimer();
        startAlarm(); // Estructura básica para la alarma
    }

    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(sdf.format(new Date()));
            }
        }, 0, 1000); // Actualiza cada segundo
    }

    private void startAlarm() {
        // Estructura básica para la alarma
        TimerTask alarmTask = new TimerTask() {
            @Override
            public void run() {
                if (!alarmTriggered) {
                    alarmTriggered = true;
                    System.out.println("¡Alarma activada!");
                }
                System.out.println("¡Ding!");
            }
        };

        // Este temporizador activará la alarma después de cierto tiempo
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                alarmTimer.scheduleAtFixedRate(alarmTask, 0, alarmIntervalSeconds * 1000);
            }
        }, alarmTimeMinutes * 60 * 1000); // Convertimos minutos a milisegundos
    }

    public static void main(String[] args) {
        // Configura la alarma para que se active después de 2 minutos y la alarma se repita cada 10 segundos
        new Cronometro(2, 10);
    }
}
