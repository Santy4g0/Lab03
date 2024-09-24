/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Cronometro1 {

    private JLabel timeLabel;
    private JLabel alarmStatusLabel; 
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
        frame.setSize(300, 250);
        frame.setLayout(new BorderLayout());

        timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(timeLabel, BorderLayout.NORTH);

       
        alarmStatusLabel = new JLabel("Alarma desactivada", SwingConstants.CENTER);
        alarmStatusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(alarmStatusLabel, BorderLayout.CENTER);

      
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 5, 5));

        JButton startButton = new JButton("Iniciar");
        startButton.addActionListener(e -> startTimer());
        buttonPanel.add(startButton);

        JButton stopButton = new JButton("Detener");
        stopButton.addActionListener(e -> stopTimer());
        buttonPanel.add(stopButton);

        JButton setAlarmButton = new JButton("Configurar Alarma");
        setAlarmButton.addActionListener(e -> setAlarm());
        buttonPanel.add(setAlarmButton);

        JButton resetButton = new JButton("Restablecer");
        resetButton.addActionListener(e -> resetTimer());
        buttonPanel.add(resetButton);

   
        JButton disableAlarmButton = new JButton("Desactivar Alarma");
        disableAlarmButton.addActionListener(e -> disableAlarm());
        buttonPanel.add(disableAlarmButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

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
        alarmStatusLabel.setText("Alarma desactivada");
    }

    private void updateTimeLabel() {
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeLabel.setText(time);
    }

   
    private void resetTimer() {
        stopTimer(); 
        seconds = 0;
        minutes = 0;
        hours = 0;
        updateTimeLabel();
        alarmStatusLabel.setText("Alarma desactivada");
        timeLabel.setForeground(Color.BLACK); 
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
                    alarmStatusLabel.setText("Alarma activada para las " + alarmTime);
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
                    timeLabel.setForeground(Color.RED); 
                    timeLabel.setText("¡ALARMA!"); 
                }
            }, 0, 10000); 
        }
    }

    // Método para desactivar la alarma
    private void disableAlarm() {
        if (alarmActive) {
            alarmActive = false;
            if (alarmTimer != null) {
                alarmTimer.cancel();
                alarmTimer = null;
            }
            alarmStatusLabel.setText("Alarma desactivada");
            timeLabel.setForeground(Color.BLACK); 
            updateTimeLabel(); 
        }
    }
}

