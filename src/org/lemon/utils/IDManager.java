package org.lemon.utils;

import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

/**
 * Class description: Specially made for generating random ID for each component opened by user in application.
 * <p>
 * eg for ImageView, Drawing Canvas etc.
 * */
public class IDManager {

	
	private NavigableMap<Integer, JComponent> idStorage = new TreeMap<Integer, JComponent>();
	
	
	
	public IDManager() {
		
	}
	
	
	/**
	 * Add new ID for new component.
	 * */
	public void addId(int id, JComponent comp) {
		if(comp == null)
			comp = new JCheckBox();
		this.idStorage.put(id, comp);
	}
	
	
	/**
	 * Continues to make ID for new component from last components id.
	 * <p>
	 * e.g newId = lastId + 1;
	 * @return id
	 * */
	public int getSequentialId() {
		NavigableSet<Integer> keys = this.idStorage.descendingKeySet();
		int id = 1;
		
		if(!keys.isEmpty()) {
			id = keys.first() + 1;
		}
		
		return id;
	}
	
	
	/**
	 * Get random ID between min and max.
	 * @return randId
	 * */
	public static int getRandomIntId(int min, int max) {
		return new Random().nextInt(max) + min;
	}
	
	
	/**
	 * Get all the assigned ids in application.
	 * @return idStorage
	 * */
	public NavigableMap<Integer, JComponent> getIdStorage(){
		return this.idStorage;
	}
	
}
