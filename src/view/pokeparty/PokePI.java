/**
 * PokePI.java
 */
package view.pokeparty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import data.PokeUtils;

import view.PokeListener;

/**
 * Panel containing the pie charts
 * @author Jimi Ford
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
	private Integer current;
	
	/**
	 * constructor
	 * @param listen parent component to report back to
	 */
	public PokePI(PokeListener listen) {
		super(new GridLayout(1,2));
		this.listen = listen;
		current = null;
	}
	
	/**
	 * sets the data to represent in the pie chart
	 */
	public void setTypeDataSet(List<String> data) {
		if(typeChartPanel != null) {
			this.remove(typeChartPanel);
		}
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
		PiePlot3D plot = (PiePlot3D) typeChart.getPlot();
		plot.setCircular(true);
		plot.setForegroundAlpha(0.8f);
		typeChartPanel = new ChartPanel(typeChart);
		this.setWeakDataSetFrom(data);
		this.updateCharts();
		this.fillComponents();
	}
	
	/**
	 * adds components to this panel
	 */
	private void fillComponents() {
		typeChartPanel.setPreferredSize(new Dimension(200,200));
		weakChartPanel.setPreferredSize(new Dimension(200,200));
		typeChartPanel.setMaximumSize(new Dimension(200,200));
		weakChartPanel.setMaximumSize(new Dimension(200,200));
//		typeChartPanel.setBackground(Color.white);
//		weakChartPanel.setBackground(Color.white);
		this.add(typeChartPanel);
		this.add(weakChartPanel);
	}

	/**
	 * private method calculates and sets weakness pie chart
	 */
	private void setWeakDataSetFrom(List<String> data) {
		if(weakChartPanel != null) {
			this.remove(weakChartPanel);
		}
		weakDataset = new DefaultPieDataset();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		LinkedList<String> result = new LinkedList<String>();
		Iterator<String> itr = data.iterator();
		String temp = "";
		String[] weakArray;
		while(itr.hasNext()) {
			temp = itr.next();
			weakArray = PokeUtils.getWeakness(temp);
			for(int i = 0; i < weakArray.length; i++) {
				result.add(weakArray[i]);
			}
		}
		int tempInt;
		itr = result.iterator();
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
			weakDataset.setValue(temp, map.get(temp));
		}
		weakChart = ChartFactory.createPieChart3D("Weakness", weakDataset, true, true, false);
		PiePlot3D plot = (PiePlot3D) weakChart.getPlot();
		plot.setCircular(true);
		plot.setForegroundAlpha(0.8f);
		weakChartPanel = new ChartPanel(weakChart);
	}
	
}
