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

package net.lirelab.acceptance.view;

import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChild;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class MenuViewObject extends FxRobot {

    public void checkMenuBar() {
        verifyThat("#menu-bar", isVisible());
        verifyThat("#menu-bar", hasChild("#file-menu"));
        verifyThat("#menu-bar", hasChild("#help-menu"));
    }

    public void checkFileMenu() {
        clickOn("#file-menu");
        verifyThat("#create-collection", isVisible());
        clickOn("#file-menu");
    }

    public void checkHelpMenu() {
        clickOn("#help-menu");
        verifyThat("#about", isVisible());
        clickOn("#help-menu");
    }

}
