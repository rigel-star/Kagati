package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.lemon.gui.texture.TextureList;
import org.lemon.lang.LemonObject;


/**
 * 
 * Texture chooser helps choosing texture for image.
 * 
 * */
@LemonObject( type = LemonObject.GUI_CLASS )
public class TextureChooser extends JDialog {
	
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private final static int V_GAP = 10;
	private final static int H_GAP = 10;
	private final static int BORDER = 12;
	
	private static TextureList list = null;
	private JPanel buttonPanel = null;
	
	private JButton okBttn = null, cancelBttn = null;
	
	
	static {
		list = new TextureList();
	}
	
	
	public TextureChooser() {
		
		this.init();
		
		setTitle( "Textures" );
		setSize( 500, 400 );
		setModalityType( ModalityType.DOCUMENT_MODAL );
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		setLocationRelativeTo( null );	
		
		var main = new JPanel();
		main.setBorder( BorderFactory.createEmptyBorder( BORDER, BORDER, BORDER, BORDER ) );
		
		var pos = new GridBagConstraintsHelper();
		
		buttonPanel.add( okBttn );
		buttonPanel.add( cancelBttn );
		
		main.add( list, pos.nextCol() );
		main.add( new Gap(), pos.nextCol() );
		main.add( buttonPanel, pos.nextCol() );
		
		add( main, BorderLayout.CENTER );
		
		setVisible( true );
	}
	
	
	/**
	 * 
	 * Initialize the widgets.
	 * 
	 * */
	private void init() {
		
		okBttn = new JButton( "Ok" );
		cancelBttn = new JButton( "Cancel" );
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout( new GridLayout( 2, 1, H_GAP, V_GAP ));
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( 300, 200 );
	}
	
	
	public static void main(String[] args) {
		
		new TextureChooser();
		
	}
	
}
