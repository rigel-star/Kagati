package org.lemon.utils;

import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Random;
import java.util.TreeMap;


/**
 * Class description: Made for generating random ID for each {@code Element} opened by user in application.
 * 
 * */
public class IDManager<E> {

	
	private NavigableMap<Integer, E> idStorage = new TreeMap<>();
	
	
	public IDManager() {
		
	}
	
	
	/**
	 * Add new ID for new {@code Element}.
	 * */
	public void add(int id, E e) {
		if(e != null)
			this.idStorage.put(id, e);
	}
	
	
	/**
	 * Continues to make ID for new {@code Element} from last {@code Element}'s id.
	 * <p>
	 * e.g newId = lastId + 1;
	 * @return id
	 * */
	public int next() {
		NavigableSet<Integer> keys = this.idStorage.descendingKeySet();		//e.g. 5 4 3 2 1
		int id = 1;
		
		if(!keys.isEmpty()) {
			id = keys.first() + 1;		//e.g. first(5) + 1 = 6
		}
		
		return id;
	}
	
	
	/**
	 * Get random ID between min and max.
	 * @return randId
	 * */
	public static int random(int min, int max) {
		return new Random().nextInt(max) + min;
	}
	
	
	/**
	 * Get all the assigned ids in application.
	 * @return idStorage
	 * */
	public NavigableMap<Integer, E> getIdSet(){
		return this.idStorage;
	}
	
}
