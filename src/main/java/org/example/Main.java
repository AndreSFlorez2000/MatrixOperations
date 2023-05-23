package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {


    private final JTextField[][] matriz1;
    private final JTextField[][] matriz2;
    private final JTextField[][] resultado;


    public Main() {
        super("Operaciones con matrices");
        int size = 4;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(size, size));
        matriz1 = new JTextField[size][size];
        matriz2 = new JTextField[size][size];
        resultado = new JTextField[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matriz1[i][j] = new JTextField();
                matriz2[i][j] = new JTextField();
                resultado[i][j] = new JTextField();
                resultado[i][j].setEditable(false);
                panel.add(matriz1[i][j]);
            }
            panel.add(new JLabel(i == 1 ? "+" : ""));
            for (int j = 0; j < size; j++) {
                panel.add(matriz2[i][j]);
            }
            panel.add(new JLabel(i == 1 ? "=" : ""));
            for (int j = 0; j < size; j++) {
                panel.add(resultado[i][j]);
            }
        }
        JButton bAdd = new JButton("Sumar");
        bAdd.addActionListener(e -> {
            double[][] result = add(getValues(matriz1), getValues(matriz2));
            drawMatrix(result);
        });
        JButton bSubtract = new JButton("Restar");
        bSubtract.addActionListener(e -> {
            double[][] result = subtract(getValues(matriz1), getValues(matriz2));
            drawMatrix(result);
        });
        JButton bMultiply = new JButton("Multiplicar");
        bMultiply.addActionListener(e -> {
            double[][] result = multiply(getValues(matriz1), getValues(matriz2));
            drawMatrix(result);
        });
        JButton bStrassen = new JButton("Mtd.Straissen");
        bStrassen.addActionListener(e -> {
            double[][] result = strassenMultiply(getValues(matriz1), getValues(matriz2));
            drawMatrix(result);
        });

        JPanel panelBotones = new JPanel();

        panelBotones.add(bAdd);
        panelBotones.add(bSubtract);
        panelBotones.add(bMultiply);
        panelBotones.add(bStrassen);


        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        setSize(600, 300);
        setVisible(true);
    }
    private void drawMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                resultado[i][j].setText(String.format("%.2f",matrix[i][j]));
            }
        }
    }

    private double[][] add(double[][] m1, double[][] m2) {
        double[][] res = new double[m1.length][m1.length];

        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1.length; j++) {
                res[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return res;
    }

    private double[][] subtract(double[][] m1, double[][] m2) {
        double[][] res = new double[m1.length][m1.length];

        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1.length; j++) {
                res[i][j] = m1[i][j] - m2[i][j];
                resultado[i][j].setText(Double.toString(res[i][j]));
            }
        }
        return res;
    }

    private double[][] multiply(double[][] m1, double[][] m2) {
        double[][] res = new double[m1.length][m1.length];

        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1.length; j++) {
                for (int k = 0; k < m1.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }




    private double[][] strassenMultiply(double[][] m1, double[][] m2) {
        int n = m1.length;
        double[][] res = new double[n][n];

        if (n == 1) {
            res[0][0] = m1[0][0] * m2[0][0];
            return res;
        }

        int mid = n / 2;
        double[][] a11 = new double[mid][mid];
        double[][] a12 = new double[mid][mid];
        double[][] a21 = new double[mid][mid];
        double[][] a22 = new double[mid][mid];
        double[][] b11 = new double[mid][mid];
        double[][] b12 = new double[mid][mid];
        double[][] b21 = new double[mid][mid];
        double[][] b22 = new double[mid][mid];

        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                a11[i][j] = m1[i][j];
                a12[i][j] = m1[i][j + mid];
                a21[i][j] = m1[i + mid][j];
                a22[i][j] = m1[i + mid][j + mid];
                b11[i][j] = m2[i][j];
                b12[i][j] = m2[i][j + mid];
                b21[i][j] = m2[i + mid][j];
                b22[i][j] = m2[i + mid][j + mid];
            }
        }

        double[][] p1 = strassenMultiply(add(a11, a22), add(b11, b22));
        double[][] p2 = strassenMultiply(add(a21, a22), b11);
        double[][] p3 = strassenMultiply(a11, subtract(b12, b22));
        double[][] p4 = strassenMultiply(a22, subtract(b21, b11));
        double[][] p5 = strassenMultiply(add(a11, a12), b22);
        double[][] p6 = strassenMultiply(subtract(a21, a11), add(b11, b12));
        double[][] p7 = strassenMultiply(subtract(a12, a22), add(b21, b22));

        double[][] c11 = add(subtract(add(p1, p4), p5), p7);
        double[][] c12 = add(p3, p5);
        double[][] c21 = add(p2, p4);
        double[][] c22 = add(subtract(add(p1, p3), p2), p6);

        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                res[i][j] = c11[i][j];
                res[i][j + mid] = c12[i][j];
                res[i + mid][j] = c21[i][j];
                res[i + mid][j + mid] = c22[i][j];
            }
        }
        return res;
    }


    private double[][] getValues(JTextField[][] matriz) {
        double[][] res = new double[matriz.length][matriz.length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                res[i][j] = Double.parseDouble(matriz[i][j].getText());
            }
        }

        return res;
    }


    public static void main(String[] args) {
        Main gui = new Main();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}