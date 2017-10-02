package model;

import utils.Constantes;

import java.util.Random;

public class Carro implements Runnable {

    private int qtdeVolta;
    private int velocidade;
    private Double x;
    private Double y;
    private boolean parouPitStop;
    private boolean parou = false;
    private double theta = 0;
    private String cor;

    public Carro(int qtdeVolta, double x, double y) {
        this.qtdeVolta = qtdeVolta;
        this.x = x;
        this.y = y;
        setVelocidade();
        setCor();
        parouPitStop = false;
    }

    @Override
    public void run() {
        if (qtdeVolta > -1) {
            correr();
        } else {
            for (int i = 0; i < Constantes.cor.length; i++) {
                if (Constantes.cor[i] == null)
                    Constantes.cor[i] = cor;
                else if (Constantes.cor[i].equalsIgnoreCase(Constantes.cor[0]))
                    Constantes.cor[i] = cor;
            }
            parou = true;
        }
//        verificarSeDevePararNoPitStop();
//        contabilizarVolta();
//        verificarSeDeveParar();
    }

    public boolean isParou() {
        return parou;
    }

    public void setParou(boolean parou) {
        this.parou = parou;
    }

    public void setVelocidade() {
        do {
            velocidade = new Random().nextInt(51);
        } while (velocidade < 20 || velocidade > 50);
    }

    public int getQtdeVolta() {
        return qtdeVolta;
    }

    public void setQtdeVolta(int qtdeVolta) {
        this.qtdeVolta = qtdeVolta;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public boolean isParouPitStop() {
        return parouPitStop;
    }

    public void setParouPitStop(boolean parouPitStop) {
        this.parouPitStop = parouPitStop;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setCor() {
        this.cor = getRandomColor();
    }

    public void correr() {
        theta += 02 * Math.PI / velocidade;

        setX(Constantes.centroX + Constantes.radiusX * Math.cos(theta));
        setY(Constantes.centroY - Constantes.radiusY * Math.sin(theta));

        if (theta >= 2 * Math.PI) {
            theta = 0;
            setX(Constantes.centroX + Constantes.radiusX * Math.cos(theta - (02 * Math.PI / 50)));
            setY(Constantes.centroY + Constantes.radiusX * Math.sin(theta - (02 * Math.PI / 50)));
            qtdeVolta--;
            correr();
        }

        setVelocidade();
    }

    public String getRandomColor() {
        final Random random = new Random();
        final String[] letters = "0123456789ABCDEF".split("");
        String color = "#";
        for (int i = 0; i < 6; i++) {
            color += letters[Math.round(random.nextFloat() * 15)];
        }
        return color;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "qtdeVolta=" + qtdeVolta +
                ", velocidade=" + velocidade +
                ", x=" + x +
                ", y=" + y +
                ", parou=" + parouPitStop +
                ", theta=" + theta +
                ", cor='" + cor + '\'' +
                '}';
    }
}
