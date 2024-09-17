/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cronometro1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Cronometro1 {

    private JLabel timeLabel;
    private Timer timer;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private boolean alarmActive = false;
    private int alarmSeconds = 0;
    private int alarmMinutes = 0;
    private int alarmHours = 0;
    private Timer alarmTimer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cronometro1().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Cronómetro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(timeLabel);

        JButton startButton = new JButton("Iniciar");
        startButton.addActionListener(e -> startTimer());
        frame.add(startButton);

        JButton stopButton = new JButton("Detener");
        stopButton.addActionListener(e -> stopTimer());
        frame.add(stopButton);

        JButton setAlarmButton = new JButton("Configurar Alarma");
        setAlarmButton.addActionListener(e -> setAlarm());
        frame.add(setAlarmButton);

        frame.setVisible(true);
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    seconds++;
                    if (seconds == 60) {
                        seconds = 0;
                        minutes++;
                        if (minutes == 60) {
                            minutes = 0;
                            hours++;
                        }
                    }
                    updateTimeLabel();
                    if (alarmActive && hours == alarmHours && minutes == alarmMinutes && seconds == alarmSeconds) {
                        startAlarm();
                        alarmActive = false; // Desactiva la alarma después de la primera activación
                    }
                }
            }, 0, 1000);
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (alarmTimer != null) {
            alarmTimer.cancel();
            alarmTimer = null;
        }
    }

    private void updateTimeLabel() {
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeLabel.setText(time);
    }

    private void setAlarm() {
        String alarmTime = JOptionPane.showInputDialog(null, "Ingrese la hora de la alarma (HH:mm:ss):", "Configurar Alarma", JOptionPane.QUESTION_MESSAGE);
        if (alarmTime != null) {
            String[] parts = alarmTime.split(":");
            if (parts.length == 3) {
                try {
                    alarmHours = Integer.parseInt(parts[0]);
                    alarmMinutes = Integer.parseInt(parts[1]);
                    alarmSeconds = Integer.parseInt(parts[2]);
                    alarmActive = true;
                    JOptionPane.showMessageDialog(null, "Alarma configurada para las " + alarmTime);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Formato de hora inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de hora inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void startAlarm() {
        if (alarmTimer == null) {
            alarmTimer = new Timer();
            alarmTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Toolkit.getDefaultToolkit().beep();
                }
            }, 0, 10000); // Repite cada 10 segundos
        }
    }
}
