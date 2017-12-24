package Program;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Values.Center;
import Values.Data;
import Values.Parameter;

public class MainMethod {

	private Data data = Data.getInstance();
	private Parameter  parameter = Parameter.getInstance();
	private Center[] centerPoint = null;
	private Center[] oldCenterPoint = null;
	
	public Center[] mainProgram () {
		// 未達終止條件則繼續執行演算法
		while(!isTermination()) {
			// 產生群心點
			getCenterPoint();
			// 分群
			groupingPoint();
		}
		return this.centerPoint;
	}
	
	// 產生群心點
	private void getCenterPoint() {
		int groupSize = this.parameter.getClustertion(); // 取得群集數
		  if (this.centerPoint == null) { // 尚未建立群心點
			  this.centerPoint = new Center[groupSize]; // new一個Center型態陣列出來
			  Random random = new Random(); // 亂數物件
			  ArrayList<Integer> list = createList(); // 建立ArrayList取的所有點的List
			  for (int i=0; i<groupSize; i++) {
				  int tmp = random.nextInt(list.size()); // 亂數List大小的數值
				  // 隨機從資料集中找出群心點
				  this.centerPoint[i] = new Center(); // 陣列中每個位置需要new一個Center物件
				  this.centerPoint[i].x = this.data.x[list.get(tmp)];
				  this.centerPoint[i].y = this.data.y[list.get(tmp)];
				  list.remove(tmp); // 從List中移除已經用過的點
			  }
		  } else { // 已有群星點
			  Center[] newCenter = new Center[groupSize]; // 建立新的群心點物件
			  int pointSize = this.data.total; // 記錄所有點的總數
			  for (int i=0; i<pointSize; i++) {
				  int pointGroup = this.data.group[i]; // 取得群集索引
				  // 判斷第pointGroup位置的newCenter是否為空值
				  if (newCenter[pointGroup] == null) {
					  newCenter[pointGroup] = new Center(); // 陣列中每個位置需要new一個Center物件
				  }
				  // 將每一個群集內的座標x,y加總
				  newCenter[pointGroup].x += this.data.x[i]; // 加總X座標值
				  newCenter[pointGroup].y += this.data.y[i]; // 加總Y座標值
				  newCenter[pointGroup].groupSize++; // 記錄群集內點的數量
			  }
			  // 取得新的群心點
			  for (int i=0; i<groupSize; i++) {
				  newCenter[i].x = newCenter[i].x / newCenter[i].groupSize; // 取得新的群心點X值
				  newCenter[i].y = newCenter[i].y / newCenter[i].groupSize; // 取得新的群心點Y值
				  newCenter[i].groupSize = 0;
			  }
			  this.oldCenterPoint = this.centerPoint; // 記錄原有群心點
			  this.centerPoint = newCenter; // 新產生的群心點取代現有群心點
		  }
	}
	
	// 分群
	private void groupingPoint() {
		int pointSize = this.data.total; // 記錄所有點的總數
		int groupSize = this.parameter.getClustertion(); // 取得群集數
		for (int p=0; p<pointSize; p++) {
			int group = -1;
			double dis = -1;
			for (int g=0; g<groupSize; g++) {
				double tmpDis = Point.distance(this.centerPoint[g].x, this.centerPoint[g].y, 
												this.data.x[p], this.data.y[p]); // 距離公式
				// dis等於-1時則紀錄點，dis大於新比對的距離則取代
				if (dis == -1 || dis > tmpDis) {
					dis = tmpDis; // 記錄與群心點的距離
					group = g; // 記錄當前應屬哪一群集
				}
			}
			this.data.group[p] = group; // 將第p個點分至第group個群集
			this.centerPoint[group].groupSize++; 
		}
	}
	
	// 判斷終止程式條件
	private boolean isTermination() {
		boolean termination = false; // 預設為false
		int groupSize = this.parameter.getClustertion(); // 取得參數物件
		int count = 0; // 初始化計數
		// 判斷是否為空
		if (this.oldCenterPoint != null && this.centerPoint != null) {
			// 比對新舊群心點是否相同
			for (int i=0; i<groupSize; i++) {
				if (this.centerPoint[i].x == this.oldCenterPoint[i].x && 
						this.centerPoint[i].y == this.oldCenterPoint[i].y) {
					count++;
				}
			}
		}
		// 新舊群心點相同則將回傳值設為True
		if (count >= groupSize) {
			termination = true;;
		}
		return termination; // 回傳回傳值
	}
	// 產生總點數清單
	private ArrayList<Integer> createList() {
		ArrayList<Integer> list = new ArrayList<>();
		int size = data.total;
		for (int i=0; i<size; i++) {
			list.add(i);
		}
		return list;
	}
}
