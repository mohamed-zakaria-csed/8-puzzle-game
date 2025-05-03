package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ArrayPrinterApp extends JFrame {
    private List<int[][]> arrayList;
    private Font font, btnFont;
    private int currentIndex;

    private JPanel arrayPanel;
    private JLabel arrayLabel;

    public ArrayPrinterApp(List<int[][]> arrayList) {
        this.arrayList = arrayList;
        this.currentIndex = 0;
        initUI();
    }

    private void initUI() {
        setTitle("2D Array Viewer");
        font = new Font("Arial", Font.PLAIN, 50);
        font = new Font("Arial", Font.PLAIN, 40);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the preferred size of the JFrame (width, height)
        setPreferredSize(new Dimension(600, 500));
        // Set the font for all components
        setFont(new Font("Arial", Font.PLAIN, 36));
        arrayPanel = new JPanel(new GridLayout(arrayList.get(0).length, arrayList.get(0)[0].length));
        arrayLabel = new JLabel();

        displayArray(arrayList.get(currentIndex));

        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton firstButton = new JButton("First");
        JButton lastButton = new JButton("Last");
        prevButton.setFont(btnFont);
        nextButton.setFont(btnFont);
        lastButton.setFont(btnFont);
        firstButton.setFont(btnFont);
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex - 1 + arrayList.size()) % arrayList.size();
                displayArray(arrayList.get(currentIndex));
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex + 1) % arrayList.size();
                if (currentIndex == 0) {
                    // Show a message or take action when you reach the first array
                    JOptionPane.showMessageDialog(null, "You reached the goal");
                } else {
                    displayArray(arrayList.get(currentIndex));
                }
            }
        });

        firstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = 0;
                displayArray(arrayList.get(currentIndex));
            }
        });

        lastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = arrayList.size() - 1;
                displayArray(arrayList.get(currentIndex));
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(firstButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        buttonPanel.add(lastButton);

        add(arrayPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void displayArray(int[][] array) {
        arrayPanel.removeAll();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                JLabel label = new JLabel(String.valueOf(array[i][j]));
                label.setFont(font);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if(array[i][j] == 0){
                    label.setText("");
                    label.setOpaque(true);
                    label.setForeground(Color.RED);
                }
                arrayPanel.add(label);
            }
        }
        arrayLabel.setText("Array " + (currentIndex + 1));
        arrayPanel.revalidate();
        arrayPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<int[][]> arrayList = new ArrayList<>();
            arrayList.add(new int[][]{{1,0,2},{3,5,4},{6,7,8}});
            arrayList.add(new int[][]{{1,0,2},{4,3,5},{6,7,8}});
            arrayList.add(new int[][]{{1,0,2},{3,4,5},{6,7,8}});
            arrayList.add(new int[][]{{0,1,2},{3,4,5},{6,7,8}});
            ArrayPrinterApp app = new ArrayPrinterApp(arrayList);
            app.setVisible(true);
        });
    }
}

