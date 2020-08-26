package org.lemon.tools.select;

import java.awt.geom.Area;

public class SelectedArea {
	
	private Area area;
	
	
	public SelectedArea(Area area) {
		this.area = area;
	}
	
	
	public Area getArea() {
		return area;
	}
}
