/**
 * 
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import view.PokeListener;

/**
 * @author jimiford
 *
 */
@SuppressWarnings("serial")
public class PokePI extends JPanel {

	private PokeListener listen;
	private DefaultPieDataset typeDataset;
	private DefaultPieDataset weakDataset;
	private JFreeChart typeChart;
	private JFreeChart weakChart;
	private ChartPanel typeChartPanel;
	private ChartPanel weakChartPanel;
	
	public PokePI(PokeListener listen) {
		super(new BorderLayout());
		this.listen = listen;
	}
	
	public void setTypeDataSet(List<String> data) {
		typeDataset = new DefaultPieDataset();
		Iterator<String> itr = data.iterator();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String temp = "";
		int tempInt = 0;
		while(itr.hasNext()) {
			temp = itr.next();
			if(map.containsKey(temp)) {
				tempInt = map.get(temp);
				map.put(temp, tempInt + 1);
			} else {
				map.put(temp, 1);
			}
		}
		itr = map.keySet().iterator();
		while(itr.hasNext()) {
			temp = itr.next();
			typeDataset.setValue(temp, map.get(temp));
		}
		typeChart = ChartFactory.createPieChart3D("Types", typeDataset, true, true, false);
		System.out.println(map);
	}
}
