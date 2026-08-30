package com.kahrs.app;

import java.sql.SQLException;

import com.kahrs.controller.GameController;
import com.kahrs.model.GameModel;
import com.kahrs.view.GameView;

/**
 * Classe Main par défaut.
 * 
 * 
 * @since 0.0
 * 
 * @version 0.0
 */
public class Main {

    /** Constructeur privé pour résoudre warning de Javadoc */
    private Main() {
    }

    /**
     * Méthode main(String[]) par défaut.
     * 
     * @param args Le tableau d'arguments
     * 
     * @throws SQLException Gestion de l'exception
     * 
     * 
     * @since 0.0
     */
    public static void main(String[] args) throws SQLException {
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view);

        model.setView(view);
        view.setController(controller);

        model.initDatas();
    }
}
