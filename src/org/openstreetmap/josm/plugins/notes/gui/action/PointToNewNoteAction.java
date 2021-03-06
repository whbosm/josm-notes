/* Copyright (c) 2013, Ian Dees
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.openstreetmap.josm.plugins.notes.gui.action;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JToggleButton;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.notes.NotesPlugin;

import org.openstreetmap.josm.data.coor.LatLon;

public class PointToNewNoteAction extends AbstractAction implements MouseListener {

    private static final long serialVersionUID = 1L;

    private JToggleButton button;

    private NotesPlugin plugin;

    private Cursor previousCursor;

    public PointToNewNoteAction(JToggleButton button, NotesPlugin plugin) {
        super(tr("New note"));
        this.button = button;
        this.plugin = plugin;
    }

    private void reset() {
        Main.map.mapView.setCursor(previousCursor);
        Main.map.mapView.removeMouseListener(this);
        button.setSelected(false);
    }

    public void mouseClicked(MouseEvent e) {
        addNewIssue(e);
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        addNewIssue(e);
    }

    private void addNewIssue(MouseEvent e) {
    	LatLon latlon = Main.map.mapView.getLatLon(e.getPoint().x, e.getPoint().y);
    	NewNoteAction nia = new NewNoteAction(plugin, latlon);
    	nia.actionPerformed(new ActionEvent(this, 0, ""));
    	reset();
    }

    public void mouseReleased(MouseEvent e) {}

    public void actionPerformed(ActionEvent e) {
        if(button.isSelected()) {
            previousCursor = Main.map.mapView.getCursor();
            Main.map.mapView.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            Main.map.mapView.addMouseListener(this);
        } else {
            reset();
        }
    }
}
