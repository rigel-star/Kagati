package org.lemon.gui.node;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.lemon.gui.Layer;
import org.lemon.gui.LayerContainer;

public abstract class NodeComponent extends JInternalFrame {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private LayerContainer lycont = null;
	private Layer layer = null;
	
	/**
	 * 
	 * @param ly 	Layer attached with this internal frame.
	 * */
	public NodeComponent( LayerContainer lycont ) {
		this.lycont = lycont;
		
		WindowHandler wh = new WindowHandler();
		addInternalFrameListener( wh );
	}
	
	public void attachLayer( Layer ly ) {
		this.layer = ly;
	}
	
	public Layer getAttachedLayer() {
		return layer;
	}
	
	private class WindowHandler extends InternalFrameAdapter {
		
		@Override
		public void internalFrameClosing( InternalFrameEvent e ) {
			super.internalFrameClosing( e );
			lycont.removeLayer( layer );
		}
	}
}
