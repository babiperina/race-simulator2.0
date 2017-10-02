package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import model.Carro;
import utils.Constantes;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Pane main;

    @FXML
    Pane colocacao;

    @FXML
    Circle primeiroColocado;

    @FXML
    Circle segundoColocado;

    @FXML
    Ellipse pista;

    @FXML
    Button desenhar;

    @FXML
    Button start;

    private int qtdeCarros;

    ArrayList<Carro> carros;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carros = new ArrayList<>();
        colocacao.setVisible(false);

        Runtime runtime = Runtime.getRuntime();
        qtdeCarros = runtime.availableProcessors();

        Constantes.centroX = (pista.getBoundsInParent().getMaxX() - pista.getBoundsInParent().getMinX()) / 2 + pista.getBoundsInParent().getMinX();
        Constantes.centroY = (pista.getBoundsInParent().getMaxY() - pista.getBoundsInParent().getMinY()) / 2 + pista.getBoundsInParent().getMinY();
        Constantes.radiusX = pista.getRadiusX();
        Constantes.radiusY = pista.getRadiusY();

    }

    public void desenharCarros() {

        for (int i = 0; i < qtdeCarros; i++) {
            carros.add(new Carro(qtdeCarros, Constantes.centroX + pista.getRadiusX() * Math.cos(0 - (02 * Math.PI / 50)), Constantes.centroY - pista.getRadiusY() * Math.sin(0 - (02 * Math.PI / 50))));
        }

        for (Carro carro : carros) {
            desenharCarro(carro);
        }

        desenhar.setDisable(true);
    }

    public void desenharCarrosCorrida() {

        for (Carro carro : carros) {
            desenharCarroCorrida(carro);
        }

    }

    public void atualizarInterface() {
        int size = main.getChildren().size();
        for (int i = size - 1; i >= size - qtdeCarros; i--) {
            main.getChildren().remove(i);
        }

//        for (Node objetoGrafico : main.getChildren()) {
//            if (objetoGrafico instanceof Circle) {
//                main.getChildren().remove(objetoGrafico);
//            }
//        }

        boolean pararCorrida = true;
        for (Carro carro : carros) {
            if (!carro.isParou())
                pararCorrida = false;
        }

        if (pararCorrida)
            Constantes.corridaRolando = false;

        desenharCarrosCorrida();
    }

    public void startarCorrida() {
        start.setDisable(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Constantes.corridaRolando) {
                    for (Carro carro : carros) {
                        carro.run();
                    }
                    Platform.runLater(() -> {
                        atualizarInterface();
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

//                colocacao.setVisible(true);
//                primeiroColocado.setVisible(true);
//                primeiroColocado.setFill(Paint.valueOf(Constantes.cor[0]));
//                segundoColocado.setVisible(true);
//                segundoColocado.setFill(Paint.valueOf(Constantes.cor[1]));
            }
        }).start();

    }

    public void desenharCarroCorrida(Carro carro) {
        double x = carro.getX();
        double y = carro.getY();
        Circle c = new Circle();
        c.setCenterX(x);
        c.setCenterY(y);
        c.setRadius(8);
        c.setFill(Paint.valueOf(carro.getCor()));
        main.getChildren().add(c);
    }

    public void desenharCarro(Carro carro) {
        main.getChildren().add(new Circle(Constantes.centroX + pista.getRadiusX() * Math.cos(0 - (02 * Math.PI / 50)), Constantes.centroY - pista.getRadiusY() * Math.sin(0 - (02 * Math.PI / 50)), 8, Paint.valueOf(carro.getCor())));
    }
}
