/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.inject.Inject;

public class App extends Application {

    @Inject private AppFXML appFXML;

    public static void main(String[] args) {
        launch(App.class);
    }

    @Override
    public void init() throws Exception {
        DependencyInjection.init(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMaximized(true);
        appFXML.loadIn(stage);
    }
}
