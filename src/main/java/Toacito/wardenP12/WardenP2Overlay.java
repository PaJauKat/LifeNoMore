package Toacito.wardenP12;

import Toacito.ToacitoConfig;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class WardenP2Overlay extends Overlay {
	@Inject
	private Client client;

	@Inject
	private WardenP2 wardenP2;

	@Inject
	private ToacitoConfig toacitoConfig;

	@Inject
	WardenP2Overlay(Client client, WardenP2 wardenP2){
		this.client=client;
		this.wardenP2=wardenP2;
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.HIGH);
	}

	@Override
	public Dimension render(Graphics2D graphics) { //ponerle condiciones
		if(WardenP2.enPelea && toacitoConfig.pikachusConfig()) {
			for (int a = 0; a < WardenP2.endPos.length; a++) {
				if (WardenP2.tickLeft[a] > 0) {
					Polygon poly = Perspective.getCanvasTilePoly(client, WardenP2.endPos[a]);
					graphics.setPaintMode();
					graphics.setColor(Color.RED);
					graphics.draw(poly);
				}
			}
		}


		return null;
	}
}
