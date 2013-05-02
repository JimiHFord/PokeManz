package view.pokemetrics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PokemetricsScreen extends JFrame implements ActionListener{
	PokeCardPanel pcp;
	
	public PokemetricsScreen(){
		super("Pokemetrics");
		initComponents();
	}
	
	private void initComponents(){
		pcp = new PokeCardPanel();
		pcp.pgp.metricsBtn.addActionListener(this);
		pcp.pmp.thruBtn.addActionListener(this);
		this.add(pcp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
