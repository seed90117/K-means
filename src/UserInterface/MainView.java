package UserInterface;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import IO.DrawPanel;
import IO.LoadFile;
import Program.MainMethod;
import Values.Center;
import Values.Parameter;

public class MainView extends JFrame {
	
	/**
	 * M10456012
	 * Kevin Yen
	 * kelly10056040@gmail.com
	 */
	private static final long serialVersionUID = 1L;
	private boolean isLoad = false;
	private String dataSetName = "";
	private DrawPanel drawPanel = new DrawPanel();
	
	//*****宣告介面*****//
	Container cp = this.getContentPane();
	
	//*****宣告元件*****//
	JLabel clustertionLabel = new JLabel("Clustertion:");
	JLabel dataSetLabel = new JLabel("DataSet: ");
	JLabel runTimeLabel = new JLabel("Running Time: ");
	JLabel avgRunTimeLabel = new JLabel("Avg Time: ");
	
	JTextField clustertionTextField = new JTextField("3");
	JTextField computerRunTextField = new JTextField("30");
	
	JButton loadFileButton = new JButton("Load File");
	JButton startButton = new JButton("Starts");
	JButton drawButton = new JButton("Draw");
	
	JCheckBox isMacCheckBox = new JCheckBox("Using Mac");
	JCheckBox isComputerRunCheckBox = new JCheckBox("Computer Run");
	JPanel show = new JPanel();
	JFileChooser open = new JFileChooser();
	
	MainView()
	{
		//*****設定介面*****//
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setTitle("K-Means");
		
		//*****設定元件位置*****//
		clustertionLabel.setBounds(700, 30, 100, 30);
		clustertionTextField.setBounds(800, 30, 100, 30);
		
		isMacCheckBox.setBounds(700, 80, 120, 30);
		loadFileButton.setBounds(700, 130, 200, 30);
		startButton.setBounds(700, 180, 200, 30);
		drawButton.setBounds(700, 230, 200, 30);
		isComputerRunCheckBox.setBounds(700, 280, 150, 30);
		computerRunTextField.setBounds(820, 280, 40, 30);
		dataSetLabel.setBounds(700, 330, 250, 30);
		runTimeLabel.setBounds(700, 380, 250, 30);
		avgRunTimeLabel.setBounds(700, 430, 250, 30);
		
		show.setBounds(20, 20, 650, 650);
		
		//*****設定JPanel邊框*****//
		show.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		cp.add(clustertionLabel);
		cp.add(clustertionTextField);
		cp.add(startButton);
		cp.add(loadFileButton);
		cp.add(show);
		cp.add(runTimeLabel);
		cp.add(isMacCheckBox);
		cp.add(isComputerRunCheckBox);
		cp.add(computerRunTextField);
		cp.add(dataSetLabel);
		cp.add(avgRunTimeLabel);
		cp.add(drawButton);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		loadFileButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				LoadFile loadFile = new LoadFile();
				String tmpStr = loadFile.loadfile(open, show, isMacCheckBox.isSelected());
				if (!tmpStr.equals("")) {
					dataSetName = tmpStr;
				}
				dataSetLabel.setText("DateSet: " + dataSetName);
				if (!dataSetLabel.getText().equals("DateSet: "))
					isLoad = true;
			}});
		
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (isLoad) {
					initialOutPut();
					if (isComputerRunCheckBox.isSelected()) {
						computerRunAlgorithm();
					}
					else
						runAlgorithm();
				}
			}});
		drawButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}});
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainView();
	}

	private void runAlgorithm() {
		// 記錄開始時間
		long startTime = System.currentTimeMillis();
		// 設定參數
		setParameter();
		// 執行K-means
		MainMethod mainMethod = new MainMethod();
		Center[] centers = mainMethod.mainProgram();
		showResult(centers);
		// 記錄結束時間
		long endTime = System.currentTimeMillis();
		runTimeLabel.setText("Running Time: " + getRunTime(startTime, endTime) + " s");
	}
	
	private void computerRunAlgorithm() {
		int runTime = Integer.parseInt(computerRunTextField.getText());
		long startTime = 0;
		long endTime = 0;
		double totalTime = 0;
		double bestTime = 0;
		for (int i=0; i<runTime; i++) {
			// 記錄開始時間
			startTime = System.currentTimeMillis();
			// 設定參數
			setParameter();
			// 執行K-Means
			MainMethod mainMethod = new MainMethod();
			Center[] centers = mainMethod.mainProgram();
			showResult(centers);
			// 記錄結束時間
			endTime = System.currentTimeMillis();
			// 記錄最佳
			if (i==0) {
				bestTime = getRunTime(startTime, endTime);
			} else {
				bestTime = getRunTime(startTime, endTime);
			}
			totalTime += getRunTime(startTime, endTime);
		}
		this.runTimeLabel.setText("Running Time: " + bestTime + " s");
		this.avgRunTimeLabel.setText("Avg Time: " + totalTime/runTime + " s");
	}
	
	private void setParameter() {
		Parameter parameter = Parameter.getInstance();
		parameter.setClustertion(Integer.parseInt(clustertionTextField.getText()));
	}
	
	private void initialOutPut() {
		runTimeLabel.setText("Run Time: ");
		avgRunTimeLabel.setText("Avg Time: ");
	}

	private void showResult(Center[] centers) {
		this.drawPanel.drawGrouping(centers, show);
	}
	
	private double getRunTime(long start, long end) {
		double startTime = start;
		double endTime = end;
		return (endTime - startTime)/1000;
	}
}
