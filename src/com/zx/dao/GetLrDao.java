package com.zx.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.zx.db.DBConn;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class GetLrDao {

	private static int inputSize; //输入特征数，不包括bias项
	private static int k; //类别数
	private static int dataSize; //样本数 
	private static int num_iters; //迭代次数
	private static double[][] theta; //学习得到的权值参数
	private static double alpha; //学习速率 
	private static double[][] x; //训练数据集
	private static double[][] y; //训练数据集对应的标号
	private static int K = 20; //分片数
	private static int T = 100; //目标数
	private static int M = 45; //资源数
	private static int S = 8; //特征数
	@SuppressWarnings("unused")
	private static int N = 120; //攻击者数
	
	public static void logisticRegression(int in, int out, int size, int iters, double learningRate) {
		
		inputSize = in;
		k = out;
		alpha = learningRate;  
		dataSize = size;
		num_iters = iters;
		
		theta = new double[k][inputSize];
		x = new double[dataSize][inputSize];
		y = new double[dataSize][k];
	}
	
	public static void softmax(double[] x) {
		
		double max = 0.0;
		double sum = 0.0;
		
		for (int i = 0; i < k; i++) {
			
			if (max < x[i]) {
				
				max = x[i];
			}
		}
		
		for (int i = 0; i < k; i++) {
			
			x[i] = Math.exp(x[i] - max);
			sum += x[i];
		}
		
		for (int i = 0; i < k; i++) {
			x[i] /= sum;
		}
	}
	
	public static void train() throws IOException {
		
		for (int n = 0; n < num_iters; n++) {
			
			for (int s = 0; s < dataSize; s++) {
				
				double[] py_x = new double[k];
				double[] dy = new double[k];
				
				//1.求出theta*x
				for (int i = 0; i < k; i++) {
					
					py_x[i] = 0;
					for (int j = 0; j < inputSize; j++) {
						
						py_x[i] += theta[i][j] * x[s][j];
					}
				}
				
				softmax(py_x);
				
				for (int i = 0; i < k; i++) {
					
					dy[i] = y[s][i] - py_x[i];//真实值与预测值的差异
					
					for (int j = 0; j<inputSize; j++) {  
						
						theta[i][j] += alpha * dy[i] * x[s][j] / dataSize;  
					}
				}
			}
		}
		
		//将theta输出到文件中
		File file = new File("/Users/zhangxin/Desktop/tiangang/theta.txt");
		FileWriter out = new FileWriter(file);
		
		for (int i = 0; i < k; i++) {
			
			for (int j = 0; j < inputSize; j++) {
				
				out.write(Double.toString(theta[i][j]) + " ");
			}
		}
		
		out.close();
	}
	
	public static double predict(double[] x) {
		
		double clsLabel = 0.0;  
		double[] predictY = new double[k];  
		for (int i = 0; i < k; i++) {  
			
			predictY[i] = 0;  
			for (int j = 0; j < inputSize; j++) {  
				
				predictY[i] += theta[i][j] * x[j];  
			}  
		}  
		
		softmax(predictY);  
		double max = 0; 
		
		for (int i = 0; i < k; i++)  
		{  
			System.out.println(predictY[i] + " ");//输出每个样本属于各个类别的概率
			
			if (predictY[i]>max) {  
				clsLabel = i;  
				max = predictY[i];  
			}  
		}  
		
		System.out.print("\n");
		return clsLabel;
	}
	
	public static double[][] getX() {
		
		return x;
	}
	
	public static double[][] getY() {
		return y;
	}
	
	/*
	public static boolean Data(String filename, int single) throws IOException {
		
		double[][] a = new double[dataSize][inputSize+1];
		int[][] num = new int[k][17];
		int i = 0, j = 0, b = 0;
		int[] max = new int[17];
		
		Random r = new Random();
		r.setSeed(new Date().getTime());
		
		FileReader fReader = new FileReader("/Users/zhangxin/Desktop/tiangang/5.txt");
		BufferedReader br = new BufferedReader(fReader);
		
		String line = br.readLine();
		String[] temp = new String[k*17];
		temp = line.split(" ");
		int tp = 0;
		for (i = 0; i < k; i++) {
			
			for (j = 0; j < 17; j++) {
				
				num[i][j] = Integer.parseInt(temp[tp]);
				tp++;
			}
		}
		
		br.close();
		

		for(i=0;i<17;i++)
		{
			
			for(j=0;j<9;j++)
			{
				
				if (i==4)
					max[i]=1000;
				else if (i==6)
					max[i]=10;
				else if(max[i]<num[j][i])
					max[i]=num[j][i];
			}
		}
		
		for (i = 0; i < dataSize; i++) {
			
			j = 0;
			b = (int)(Math.random() * k);
			a[i][j] = b;
			
			for (j = 1; j < 9; j++) {
				
				a[i][j] = (Math.random() * (num[b][2*j]-num[b][2*j-1]+1) + num[b][2*j-1]) / (double)max[2*j];
			}
		}
		
		File file = new File(filename);
		FileWriter out = new FileWriter(file);
		
		for (i = 0; i < dataSize; i++) {
			
			for (j = 0; j < inputSize+1; j++) {
				
				out.write(Double.toString(a[i][j]) + " ");
			}
		}
		
		out.close();
		
		//System.out.println("The data " + filename + " is loaded.");
		
		return true;
	}
	*/
	
	public static void putDataToTxt(Integer year) throws SQLException, IOException {
		
		float[][] data = new float[800][9];
		int start = year - 8;
		int end = year - 1;
		
		Connection conn = DBConn.getConnection();
		
		StringBuilder build = new StringBuilder();
		build.append("select * from parking_incident where parking_year between ? and ?");
		
		PreparedStatement ptmt = conn.prepareStatement(build.toString());
		ptmt.setInt(1, start);
		ptmt.setInt(2, end);
		ResultSet rs = ptmt.executeQuery();
		
		//DecimalFormat df = new DecimalFormat("######0.0000000");
		
		int i = 0;
		while (rs.next() && i <= 800) {
			
			data[i][0] = rs.getFloat("parking_state");
			data[i][1] = rs.getFloat("parking_attack_now");
			data[i][2] = rs.getFloat("parking_catch");
			data[i][3] = rs.getFloat("parking_attack_past");
			data[i][4] = rs.getFloat("parking_coverage");
			data[i][5] = rs.getFloat("parking_density");
			data[i][6] = rs.getFloat("parking_time");
			data[i][7] = rs.getFloat("parking_distance1");
		    data[i][8] = rs.getFloat("parking_distance2");
		    
		    i++;
		    
		}
		//System.out.println(data[0][4]);
		//归一化
		i = 0;
		float max_x1 = 0, min_x1 = 0;
		float max_x2 = 0, min_x2 = 0;
		float max_x3 = 0, min_x3 = 0;
		float max_x4 = 0, min_x4 = 0;
		
		for (i = 0; i < 800; i++) {
			
			if (max_x1 < data[i][5]) {

				max_x1 = data[i][5];

			}

			if (min_x1 > data[i][5]) {

				min_x1 = data[i][5];

			}

			if (max_x2 < data[i][6]) {

				max_x2 = data[i][6];

			}

			if (min_x2 > data[i][6]) {

				min_x2 = data[i][6];

			}

			if (max_x3 < data[i][7]) {

				max_x3 = data[i][7];

			}

			if (min_x3 > data[i][7]) {

				min_x3 = data[i][7];

			}

			if (max_x4 < data[i][8]) {

				max_x4 = data[i][8];

			}

			if (min_x4 > data[i][8]) {

				min_x4 = data[i][8];

			}
		}
		
		i = 0;
		for (i = 0; i < 800; i++) {
			
			data[i][5] = (data[i][5] - min_x1) / (max_x1 - min_x1);

			data[i][6] = (data[i][6] - min_x2) / (max_x2 - min_x2);

			data[i][7] = (data[i][7] - min_x3) / (max_x3 - min_x3);

			data[i][8] = (data[i][8] - min_x4) / (max_x4 - min_x4);
		}
		
		//data数组中的数据写入data.txt文档中
		File file = new File("/Users/zhangxin/Desktop/tiangang/data.txt");
		FileWriter out = new FileWriter(file);
		
		for (int j = 0; j < 800; j++) {
			
			for (int k = 0; k < 9; k++) {
				
				out.write(data[j][k] + " ");
			}
		}
		
		out.close();
		
	}
	
	public static boolean loadData(String filename, int single) throws IOException {
		
		FileReader fReader = new FileReader(filename);
//		BufferedReader br = new BufferedReader(fReader);
//		String line = br.readLine();
//		String[] temp = new String[90000];
//		temp = line.split(" ");
		Scanner scanner = new Scanner(fReader);
		//System.out.println(inputSize);
		int fileLength = dataSize * (inputSize+1);
		int rowIndex = 0;
		int t = 0;
		//int tp = 0;
			for (int i = 0; i < fileLength; i++) {
				
				if (i % (inputSize+1) == 0) {
					
					t = (int)(scanner.nextDouble());
					y[rowIndex][t] = 1;
					++rowIndex;
				} else {
					
					//System.out.println(rowIndex);
					x[rowIndex-1][i%(inputSize+1) - 1] = scanner.nextDouble();
					
					// if (rowIndex-1>= dataSize) break;
				}
				
				//tp++;
			}
		
		scanner.close();
		//br.close();
		//System.out.println("The data " + filename + "is loaded.");
		
		return true;
	}
	
	public static void printX() {
		
		for (int i = 0; i<dataSize; i++) { 
			
			for (int j = 0; j<inputSize; j++) {  
				
				System.out.print(x[i][j] + " ");  
			}  
			System.out.print("\n");
		}  
	}
	
	public static void printY() {
		
		for (int i = 0; i<dataSize; i++) { 
			
			for (int j = 0; j<k; j++) {  
				
				System.out.print(y[i][j] + " ");  
			}  
			System.out.print("\n");
		}  
	}
	
	public static void printTheta() {
		
		for (int i = 0; i<k; i++) { 
			
			for (int j = 0; j<inputSize; j++) {  
				
				System.out.print(theta[i][j] + " ");  
			}  
			System.out.print("\n");
		}  
	}
	
	public static float[] letter_recog(Integer year) throws IOException, SQLException {
		
		float[] result = new float[100];
		int getYear = year - 1;
		String filename = "/Users/zhangxin/Desktop/tiangang/data.txt";
		double learning_rate = 0.1;  
	    int num_iters = 10000;//迭代次数  
	    int train_N =800;//训练样本个数
	    //int test_N = 8;//测试样本个数  
		int n_in = 8;//输入特征维数
		int n_out = 100;//类别数
		
		putDataToTxt(year);
		logisticRegression(n_in, n_out, train_N, num_iters, learning_rate);
		loadData(filename, 1);
		train();
		printTheta();
		
		//测试用
//		double[][] test_X = {  
//		        { 0.73, 0.3, 0.5, 0.6, 1, 1, 0.1, 1},//0  ,M
//		        { 0.1, 0.05, 0.5, 0.4, 0.4, 0.1,1,1},//1 ,W 
//		        { 0.3, 0.3, 0.5, 0.6, 0.6, 0.5, 0.3, 0.6 },//2  ,N
//		        { 0.5, 0.3, 0.5, 0.6, 0.6, 0.6, 1, 0.2},//3  ,H
//		        { 0.7, 0.3, 0.5, 0.4, 0.8, 0.9, 1, 0.1},//4  ,R
//		        { 0.2, 0.15, 0.5, 0.6, 0.6, 0.3, 0.9,0.5}, //5 ,X 
//		        { 0.25, 0.35, 0.5, 1, 0.6, 0.7, 1, 1 },//6 ,P     
//		        { 0.4, 0.1, 0.5, 0.4, 0.4, 0.2, 0.5, 0.5}//7  ,Q
//		    };
		
//		for (int i = 0; i < test_N; i++) {
//			
//			double predict = predict(test_X[i]);
//			System.out.println("predict:" + predict);
//		}
		
		double[] c = new double[T];
		double[] d = new double[T];
		double[][] a = new double[T][K];
		double[][] b = new double[T][K];
		
		int[] r_li = new int[T];
		int count = 0;
		for (int i = 0; i < T; i++) {
			r_li[i] = 1;
			count++;
			
			if (count % 5 == 0) {
				r_li[i] = 2;
			}
		}
		
		double[][] x_i = new double[T][K+1];
		double[][] y1 = new double[T][K+1];
		double[][] y2 = new double[T][K+1];
		double[][] k1 = new double[T][K];
		double[][] k2 = new double[T][K];
		@SuppressWarnings("unused")
		double p = 1.0 / 3;
		double q = 1.0 / K;
		double[] s = new double[T];
		@SuppressWarnings("unused")
		double sum1=0,Low_bound=0,Upp_bound=15,r=0;
		double[][] theta = new double[T][S];
		double[][] num = new double[T][S-1];
		double[] sum2 = new double[T];
		double[] sum3 = new double[T];
		/************************************************************************************/
		
		/*读取theta的值*/
		String path = "/Users/zhangxin/Desktop/tiangang/theta.txt";
		FileReader fReader = new FileReader(path);
		Scanner scanner = new Scanner(fReader);
		for (int i = 0; i < T; i++) {
			
			for (int j = 0; j < S; j++) {
				theta[i][j] = scanner.nextDouble();
			}
		}
		scanner.close();
		
		/************************************************************************************/
		/*读取地域特征值*/
		Connection conn = DBConn.getConnection();
		StringBuilder build = new StringBuilder();
		build.append("select * from parking_incident where parking_year = ?");
		
		PreparedStatement ptmt = conn.prepareStatement(build.toString());
		ptmt.setInt(1, getYear);
		ResultSet rs = ptmt.executeQuery();
		int index = 0; 
		while (rs.next()) {
			
			num[index][0] = rs.getFloat("parking_attack_now");
			num[index][1] = rs.getFloat("parking_catch");
			num[index][2] = rs.getFloat("parking_attack_past");
			num[index][3] = rs.getFloat("parking_density");
			num[index][4] = rs.getFloat("parking_time");
			num[index][5] = rs.getFloat("parking_distance1");
			num[index][6] = rs.getFloat("parking_distance2");
		    
		    index ++;
		}
		
		index = 0;
		double max_1 = 0, min_1 = 0;
		double max_2 = 0, min_2 = 0;
		double max_3 = 0, min_3 = 0;
		double max_4 = 0, min_4 = 0;
		
		for (index = 0; index < 100; index++) {
			
			if (max_1 < num[index][3]) {

				max_1 = num[index][3];

			}

			if (min_1 > num[index][3]) {

				min_1 = num[index][3];

			}

			if (max_2 < num[index][4]) {

				max_2 = num[index][4];

			}

			if (min_2 > num[index][4]) {

				min_2 = num[index][4];

			}

			if (max_3 < num[index][5]) {

				max_3 = num[index][5];

			}

			if (min_3 > num[index][5]) {

				min_3 = num[index][5];

			}

			if (max_4 < num[index][6]) {

				max_4 = num[index][6];

			}

			if (min_4 > num[index][6]) {

				min_4 = num[index][6];

			}
		}
		
		index = 0;
		for (index = 0; index < 100; index++) {
			
			num[index][3] = (num[index][3] - min_1) / (max_1 - min_1);

			num[index][4] = (num[index][4] - min_2) / (max_2 - min_2);

			num[index][5] = (num[index][5] - min_3) / (max_3 - min_3);

			num[index][6] = (num[index][6] - min_4) / (max_4 - min_4);
		}
		
		/************************************************************************************/
		/* 求收益的下界 */
		Low_bound = -32;

		/************************************************************************************/
		/* 求函数斜率 */
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < K + 1; j++) {
				x_i[i][j] = j * (1.0 / K);// 定义每个分段节点值
			}
		}
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < K + 1; j++) {
				y1[i][j] = Math.exp(theta[i][0] * x_i[i][j]);// 定义函数y1
				y2[i][j] = x_i[i][j] * Math.exp(theta[i][0] * x_i[i][j]);// 定义函数y2
			}
		}
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < K; j++) {
				k1[i][j] = (y1[i][j + 1] - y1[i][j]) * K;// 求函数y1每个分段的斜率
				k2[i][j] = (y2[i][j + 1] - y2[i][j]) * K;// 求函数y2每个分段的斜率
			}
		}

		/************************************************************************************/
		// 求a[j][k]与b[j][k]的值
		for (int j = 0; j < T; j++) {
			for (int k = 0; k < K; k++) {
				a[j][k] = k1[j][k];
				b[j][k] = k2[j][k];
			}
		}

		/************************************************************************************/
		// 求d[]的值
		for (int i = 0; i < 100; i++) {
			for (int j = 1; j < 8; j++) {
				sum2[i] += theta[i][j] * num[i][j - 1];
			}
			d[i] = Math.exp(sum2[i]);
		}
		
		/************************************************************************************/
		/* 二分算法 */
		while (Upp_bound - Low_bound >= 10) {

			/**********************************************/
			/* 求r的值 */
			r = (Upp_bound + Low_bound) / 2;

			/**********************************************/
			// 求c[]的值
			for (int i = 0; i < 100; i++) {
				for (int j = 1; j < 8; j++) {
					sum3[i] += theta[i][j] * num[i][j - 1];
				}
				c[i] = Math.exp(sum3[i]) * (r + 1);
				//System.out.print(sum3[i] + " ");
				//System.out.print(c[i] + " ");
			}
			System.out.println();
			for (int i = 0; i < 9; i++) {
				sum3[i] = 0;
			}
			// IloEnv env;
			/**********************************************/
			/* 调用Cplex产生策略x */
			try {

				IloCplex cplex = new IloCplex();
				IloNumVar[][] x = new IloNumVar[100][];
				IloIntVar[][] z = new IloIntVar[100][];
				IloLinearNumExpr getSum = cplex.linearNumExpr();

				/**********************************************/
				// 策略矩阵x[][]与二值变量z[][]
				for (int j = 0; j < T; j++) {
					x[j] = cplex.numVarArray(K, 0, 1.0 / K);
					z[j] = cplex.boolVarArray(K);
				}

				/**********************************************/
				IloLinearNumExpr expr_min = cplex.linearNumExpr();
				// 产生需求解的表达式
				for (int i = 0; i < T; i++) {

					for (int k = 0; k < K; k++) {
						expr_min.addTerm(c[i] * a[i][k], x[i][k]);
						expr_min.addTerm(-d[i] * b[i][k], x[i][k]);

					}
				}
				
				cplex.addMinimize(expr_min);

				/**********************************************/
				// 添加约束不等式x[i][k]<M
				for (int i = 0; i < T; i++) {
					for (int k = 0; k < K; k++) {
						getSum.addTerm(1.0, x[i][k]);
					}

				}
				cplex.addLe(getSum, M);

				/**********************************************/
				// 将x[][]添加到model中

//				for (int i = 0; i < T; i++) {
//					for (int k = 0; k < K; k++) {
//						// model.add(0 <= x[i][k] <= q);
//						cplex.addLe(x[i][k], q);
//						cplex.addGe(x[i][k], 0);
//					}
//
//				}

				/**********************************************/
				// 将z[][]*(1/K)<=x[][]添加到model中
				for (int i = 0; i < T; i++) {
					for (int k = 0; k < K; k++) {
						// model.add(z[i][k]*q <= x[i][k]);
						cplex.addLe(cplex.prod(q,z[i][k]), x[i][k]);
					}

				}

				/**********************************************/
				// 添加约束不等式x[i][k+1]<z[i][k]到model中
				for (int i = 0; i < T; i++) {
					for (int k = 0; k < K - 1; k++) {
						// model.add(x[i][k+1] <= z[i][k]);
						cplex.addLe(x[i][k + 1], z[i][k]);
					}
				}

				// expr_min1.end();
				// expr_min2.end();
				// expr_min.end();
				// expr.end();

				/**********************************************/
				// 用cplex求解
				// IloCplex cplex(model);
				// if ( cplex.solve() )
				// {
				// env.error() << "Failed to optimize LP." << endl;
				// Upp_bound = r;
				// throw(-1);
				// }
				//
				// /**********************************************/
				// //result=cplex.getStatus();输出最终收益及解的性质
				// env.out() << "Solution status = " << cplex.getStatus() <<
				// endl;
				// env.out() << "Solution value = " << cplex.getObjValue() <<
				// endl;

				/**********************************************/
				// 更新r的值
				if (cplex.getStatus() != null)
					Low_bound = r;
				else
					Upp_bound = r;

				
				cplex.setParam(IloCplex.Param.Simplex.Display, 0);
				/**********************************************/
				// 策略结果输出
				if (cplex.solve()) {

					for (int i = 0; i < T; i++) {
						for (int k = 0; k < K; k++) {
							if (cplex.getValue(x[i][k]) >= 0)
								// s[i]+=1.0/K;
								s[i] += cplex.getValue(x[i][k]);
						}
						
						result[i] = (float)s[i];
						System.out.print(s[i] + " ");
					}
					System.out.println();
					for (int i = 0; i < T; i++)
						s[i] = 0;
				} else {
					System.out.println("Problem not solved");
				}
				/*
				 * for(int i=0;i<T;i++) { for (int k = 0; k < K; k++) {
				 * cout<<"z["<<i<<"]"<<"["<<k<<"]"<<"="<<cplex.getValue(z[i][k])
				 * <<"   "; } cout<<endl; }
				 */
				cplex.end();
			}

			/**********************************************/
			// 异常检测
			catch (IloException e) {
				e.printStackTrace();
			}

		}
		
		return result;
		
	}
	
}
