package org.lemon.utils;

import java.util.Stack;

public class UndoRedo {

	private Stack<Object> undo = new Stack<>();
	private Stack<Object> redo = new Stack<>();
	
	
	
	/**
	 * Undo the edited process. It undo the last action and returns that lastly done action as a object.
	 * @return currently undoing object
	 * */
	public void undo() {
		if(undo.size() != 0)
			redo.add(undo.pop());
	}
	
	
	public void addToUndo(Object obj) {
		undo.add(obj);
	}
	
	
	public Object getUndoPeek() {
		return undo.peek();
	}
	
	
	
	public Stack<Object> getUndoStack(){
		return this.undo;
	}
	
	
	
	public void redo() {
		if(redo.size() != 0)
			undo.add(redo.pop());
	}
	
	
	public void addToRedo(Object obj) {
		redo.add(obj);
	}
	
	
	public Object getRedoPeek() {
		return redo.peek();
	}
	
	
	public Stack<Object> getRedoStack(){
		return this.redo;
	}
	
	
	
}
