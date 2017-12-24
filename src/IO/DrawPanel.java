package IO;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import Values.Center;
import Values.Data;
import Values.Parameter;

public class DrawPanel {

	private Graphics g;
	private double sizeX;
	private double sizeY;
	private double addNumber;
	private Data data = Data.getInstance();
	
	public void drawpanel(JPanel show)
	{
		this.setParameter(show);
		//*****JPanel轉畫布*****//
		this.g = show.getGraphics();
				
		//*****設定顏色*****//
		this.g.setColor(Color.white);
								
		//*****清空畫布*****//
		this.g.fillRect(0, 0, show.getWidth(), show.getHeight());
				
		//*****設定顏色*****//
		this.g.setColor(Color.black);
				
		//*****畫點*****//
		for(int i = 0; i < this.data.total; i++)
		{
			int x = (int)((this.data.x[i]+addNumber)*sizeX);
			int y = (int)((this.data.y[i]+addNumber)*sizeY);
			this.g.fillRect(x, y, 3, 3);
		}
	}
	
	public void drawGrouping(Center[] centers, JPanel show) {
		// 設定參數
		this.setParameter(show);
		
		//*****JPanel轉畫布*****//
		this.g = show.getGraphics();
				
		//*****設定顏色*****//
		this.g.setColor(Color.white);
								
		//*****清空畫布*****//
		this.g.fillRect(0, 0, show.getWidth(), show.getHeight());
		
		int pointSize = this.data.total; // 記錄點的總數
		int groupSize = centers.length; // 記錄群集數
		Color[] colors = new Color[groupSize]; // 新增Color物件陣列
		// 隨機產生顏色
		for (int c=0; c<groupSize; c++) {
			colors[c] = makeRandomColor(); // 隨機產生與群集數相同數量之隨機顏色
		}
		// 畫點
		for(int i = 0; i < pointSize; i++)
		{
			this.g.setColor(colors[this.data.group[i]]);
			int x = (int)((this.data.x[i]+addNumber)*sizeX);
			int y = (int)((this.data.y[i]+addNumber)*sizeY);
			this.g.fillRect(x, y, 3, 3);
		}
		
		for (int c=0; c<groupSize; c++) {
			// Panel上畫群心點
			this.g.setColor(Color.black);
			int x = (int)((centers[c].x+addNumber)*sizeX);
			int y = (int)((centers[c].y+addNumber)*sizeY);
			this.g.fillRect(x, y, 6, 6);
		}
		
	}
	
	// 設定參數
	private void setParameter(JPanel show) {
		Parameter parameter = Parameter.getInstance();
		this.sizeX = parameter.getSizeX();
		this.sizeY = parameter.getSizeY();
		this.addNumber = parameter.getAddNumber();
		this.g = show.getGraphics();
		this.g.setColor(Color.black);
	}
	
	// 隨機產生顏色
	private Color makeRandomColor() {
		Random random = new Random();
		int red = 128 + random.nextInt(128);
	    int green = 128 + random.nextInt(128);
	    int blue = 128 + random.nextInt(128);
	    return new Color(red, green, blue);
	}
}
