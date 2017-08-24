package com.zx.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.zx.db.DBConn;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class GetEmDao {

	private static final int MAX_LOOP = 500000;
	private static final float THRESHLD = 0.000001f;
	private static final int TIME = 8;
	private static final int TARGET = 100;
	private static final int FEATURE = 8;
	private static final int TEMP = 6;
	private static final int K = 5;

	private static final float[] LAMBDA = new float[] { 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f };
	private static final float[] OMEGA = new float[] { 1.0f, 1.0f, 1.0f, 1.0f, 1.0f };

	// E1_Step，计算各个概率值。
	public static float computeProbabilityOfE(float a_t, float o_t, float a_t1, float c_t, float x1, float x2, float x3,
			float x4) {

		float prob = 0.0f;

		// 参数lambda' * features
		float e_lambda = (float) Math.exp(LAMBDA[0] * a_t1 + LAMBDA[1] * c_t + LAMBDA[2] * x1 + LAMBDA[3] * x2
				+ LAMBDA[4] * x3 + LAMBDA[5] * x4 + LAMBDA[6] * 1.0);

		// 参数omega' * features
		float e_omega = (float) Math
				.exp(OMEGA[0] * x1 + OMEGA[1] * x2 + OMEGA[2] * x3 + OMEGA[3] * x4 + OMEGA[4] * 1.0);

		if (c_t != 0) {

			if (a_t == 1.0 && o_t == 1.0) {

				prob = 1.0f;

			} else if (a_t == 0.0 && o_t == 1.0) {

				prob = 0.0f;

			} else if (a_t == 1.0 && o_t == 0.0) {

				prob = (1 - c_t * e_omega / (1 + e_omega)) * e_lambda
						/ ((1 - c_t * e_omega / (1 + e_omega)) * e_lambda + 1);

			} else {

				prob = 1 / ((1 - c_t * e_omega / (1 + e_omega)) * e_lambda + 1);

			}

		} else {

			if (a_t == 1.0 && o_t == 0.0) {

				prob = e_lambda / (1 + e_lambda);

			} else if (a_t == 0.0 && o_t == 0.0) {

				prob = 1 / (1 + e_lambda);

			} else {

				prob = 0.0f;

			}

		}

		return prob;
	}

	// 计算出E基础上，计算M1，找到最优omega，使得M1中的概率目标函数最大。
	public static float[] optimalEM_1(float[][][] data) {

		float[] omega_update = new float[6]; // 前五个存储更新的参数值，最后一个存储最优函数值。

		// 每次调用optimalEM_1函数时，update数组都调用更新后的OMEGA值。
		for (int i = 0; i < 5; i++) {

			omega_update[i] = OMEGA[i];
		}

		int t = 0, i = 0;

		float alpha = 0.005f;// 梯度下降的步长。
		float difference = 50.0f;// 每次更新omega前后，目标函数变化的值。
		int iteration = 0;// 迭代次数。

		float function_omega_old = 0.0f;

		while (difference > THRESHLD && iteration < MAX_LOOP) {

			difference = 0.0f;
			float function_omega_new = 0.0f;// M1步骤中，最后得到的关于Omega的函数，是每个目标i所得到的omega函数的总和，目标是最大化该函数。

			// 为了更新omega值，设置的临时变量。
			float a1o1_x1 = 0.0f;
			float a1o1_x2 = 0.0f;
			float a1o1_x3 = 0.0f;
			float a1o1_x4 = 0.0f;
			float a1o1_x5 = 0.0f;

			float a1o0_1 = 0.0f;
			float a1o0_2 = 0.0f;
			float a1o0_3 = 0.0f;
			float a1o0_4 = 0.0f;
			float a1o0_5 = 0.0f;

			float a1o0_x1 = 0.0f;
			float a1o0_x2 = 0.0f;
			float a1o0_x3 = 0.0f;
			float a1o0_x4 = 0.0f;
			float a1o0_x5 = 0.0f;

			// 为了更新omega。
			for (t = 0; t < TIME; t++) {

				for (i = 0; i < TARGET; i++) {

					float probability_E1 = computeProbabilityOfE(data[t][i][0], data[t][i][1], data[t][i][2],
							data[t][i][3], data[t][i][4], data[t][i][5], data[t][i][6], data[t][i][7]);

					// M1中，对参数omega进行迭代，找到函数最大值。
					float omega_iteration = (float) (omega_update[0] * data[t][i][4] + omega_update[1] * data[t][i][5]
							+ omega_update[2] * data[t][i][6] + omega_update[3] * data[t][i][7]
							+ omega_update[4] * 1.0);

					float e_omega = (float) Math.exp(omega_iteration);

					if (data[t][i][3] != 0.0) {

						if (data[t][i][0] == 1.0 && data[t][i][1] == 1.0) {

							a1o1_x1 += probability_E1 * data[t][i][4] / (1 + e_omega);
							a1o1_x2 += probability_E1 * data[t][i][5] / (1 + e_omega);
							a1o1_x3 += probability_E1 * data[t][i][6] / (1 + e_omega);
							a1o1_x4 += probability_E1 * data[t][i][7] / (1 + e_omega);
							a1o1_x5 += probability_E1 / (1 + e_omega);

						} else if (data[t][i][0] == 1.0 && data[t][i][1] == 0.0) {

							a1o0_1 += probability_E1 * data[t][i][4] * e_omega / (1 + e_omega);
							a1o0_2 += probability_E1 * data[t][i][5] * e_omega / (1 + e_omega);
							a1o0_3 += probability_E1 * data[t][i][6] * e_omega / (1 + e_omega);
							a1o0_4 += probability_E1 * data[t][i][7] * e_omega / (1 + e_omega);
							a1o0_5 += probability_E1 * e_omega / (1 + e_omega);

							a1o0_x1 += probability_E1 * data[t][i][4] * (1 - data[t][i][3]) * e_omega
									/ (1 + (1 - data[t][i][3]) * e_omega);
							a1o0_x2 += probability_E1 * data[t][i][5] * (1 - data[t][i][3]) * e_omega
									/ (1 + (1 - data[t][i][3]) * e_omega);
							a1o0_x3 += probability_E1 * data[t][i][6] * (1 - data[t][i][3]) * e_omega
									/ (1 + (1 - data[t][i][3]) * e_omega);
							a1o0_x4 += probability_E1 * data[t][i][7] * (1 - data[t][i][3]) * e_omega
									/ (1 + (1 - data[t][i][3]) * e_omega);
							a1o0_x5 += probability_E1 * (1 - data[t][i][3]) * e_omega
									/ (1 + (1 - data[t][i][3]) * e_omega);

						} else {

							continue;

						}
					} else {

						continue;

					}

				}

			}

			// 更新omega的值。
			omega_update[0] = omega_update[0] + alpha * (a1o1_x1 + a1o0_x1 - a1o0_1);
			omega_update[1] = omega_update[1] + alpha * (a1o1_x2 + a1o0_x2 - a1o0_2);
			omega_update[2] = omega_update[2] + alpha * (a1o1_x3 + a1o0_x3 - a1o0_3);
			omega_update[3] = omega_update[3] + alpha * (a1o1_x4 + a1o0_x4 - a1o0_4);
			omega_update[4] = omega_update[4] + alpha * (a1o1_x5 + a1o0_x5 - a1o0_5);

			float function_omega_each = 0.0f; // 对于每个目标所得到的omega函数

			for (t = 0; t < TIME; t++) {

				for (i = 0; i < TARGET; i++) {

					float probability_E1 = computeProbabilityOfE(data[t][i][0], data[t][i][1], data[t][i][2],
							data[t][i][3], data[t][i][4], data[t][i][5], data[t][i][6], data[t][i][7]);

					float omega_iteration = (float) (omega_update[0] * data[t][i][4] + omega_update[1] * data[t][i][5]
							+ omega_update[2] * data[t][i][6] + omega_update[3] * data[t][i][7]
							+ omega_update[4] * 1.0);

					float e_omega = (float) Math.exp(omega_iteration);

					if (data[t][i][3] != 0.0) {

						if (data[t][i][0] == 1.0 && data[t][i][1] == 1.0) {

							function_omega_each = (float) (probability_E1
									* (Math.log(data[t][i][3]) + omega_iteration - Math.log(1 + e_omega)));

						} else if (data[t][i][0] == 1.0 && data[t][i][1] == 0.0) {

							function_omega_each = (float) (probability_E1
									* (Math.log(1 - data[t][i][3] * e_omega / (1 + e_omega))));

						} else if (data[t][i][0] == 0.0 && data[t][i][1] == 0.0) {

							function_omega_each = 0.0f;

						} else {

							function_omega_each = 0.0f;

						}
					} else {

						if (data[t][i][0] == 1.0 && data[t][i][1] == 0.0) {

							function_omega_each = 0.0f;

						} else if (data[t][i][0] == 0.0 && data[t][i][1] == 0.0) {

							function_omega_each = 0.0f;

						} else {

							function_omega_each = 0.0f;

						}

					}

					function_omega_new += function_omega_each;

				}
			}

			difference = (float) (Math.abs(function_omega_new - function_omega_old));

			function_omega_old = function_omega_new;

			iteration += 1;

		}

		omega_update[5] = function_omega_old;

		return omega_update;
	}

	public static float[] optimalEM_2(float[][][] data) {

		float[] lambda_update = new float[8];

		for (int i = 0; i < 7; i++) {

			lambda_update[i] = LAMBDA[i];

		}

		int t = 0, i = 0;

		float alpha = 0.005f;// 梯度下降的步长。
		float difference = 50.0f;// 每次更新lambda前后，目标函数变化的值。
		int iteration = 0;// 迭代次数。

		float function_lambda_old = 0.0f;

		while (difference > THRESHLD && iteration < MAX_LOOP) {

			difference = 0.0f;
			float function_lambda_new = 0.0f;

			// 为了更新lambda值，设置的临时变量。
			float a1_a = 0.0f;
			float a1_c = 0.0f;
			float a1_x1 = 0.0f;
			float a1_x2 = 0.0f;
			float a1_x3 = 0.0f;
			float a1_x4 = 0.0f;
			float a1_x5 = 0.0f;

			float a0_a = 0.0f;
			float a0_c = 0.0f;
			float a0_x1 = 0.0f;
			float a0_x2 = 0.0f;
			float a0_x3 = 0.0f;
			float a0_x4 = 0.0f;
			float a0_x5 = 0.0f;

			// 为了更新lambda
			for (t = 0; t < TIME; t++) {

				for (i = 0; i < TARGET; i++) {

					float probability_E2 = computeProbabilityOfE(data[t][i][0], data[t][i][1], data[t][i][2],
							data[t][i][3], data[t][i][4], data[t][i][5], data[t][i][6], data[t][i][7]);

					float lambda_iteration = (float) (lambda_update[0] * data[t][i][2]
							+ lambda_update[1] * data[t][i][3] + lambda_update[2] * data[t][i][4]
							+ lambda_update[3] * data[t][i][5] + lambda_update[4] * data[t][i][6]
							+ lambda_update[5] * data[t][i][7] + lambda_update[6] * 1.0);

					float e_lambda = (float) Math.exp(lambda_iteration);

					if (data[t][i][3] != 0.0) {

						if (data[t][i][0] == 1.0 && data[t][i][1] == 1.0) {

							a1_a += probability_E2 * (data[t][i][2] - data[t][i][2] * e_lambda / (1 + e_lambda));
							a1_c += probability_E2 * (data[t][i][3] - data[t][i][3] * e_lambda / (1 + e_lambda));
							a1_x1 += probability_E2 * (data[t][i][4] - data[t][i][4] * e_lambda / (1 + e_lambda));
							a1_x2 += probability_E2 * (data[t][i][5] - data[t][i][5] * e_lambda / (1 + e_lambda));
							a1_x3 += probability_E2 * (data[t][i][6] - data[t][i][6] * e_lambda / (1 + e_lambda));
							a1_x4 += probability_E2 * (data[t][i][7] - data[t][i][7] * e_lambda / (1 + e_lambda));
							a1_x5 += probability_E2 * (1.0 - e_lambda / (1 + e_lambda));

						} else if (data[t][i][0] == 1.0 && data[t][i][1] == 0.0) {

							a1_a += probability_E2 * (data[t][i][2] - data[t][i][2] * e_lambda / (1 + e_lambda));
							a1_c += probability_E2 * (data[t][i][3] - data[t][i][3] * e_lambda / (1 + e_lambda));
							a1_x1 += probability_E2 * (data[t][i][4] - data[t][i][4] * e_lambda / (1 + e_lambda));
							a1_x2 += probability_E2 * (data[t][i][5] - data[t][i][5] * e_lambda / (1 + e_lambda));
							a1_x3 += probability_E2 * (data[t][i][6] - data[t][i][6] * e_lambda / (1 + e_lambda));
							a1_x4 += probability_E2 * (data[t][i][7] - data[t][i][7] * e_lambda / (1 + e_lambda));
							a1_x5 += probability_E2 * (1.0 - e_lambda / (1 + e_lambda));

						} else if (data[t][i][0] == 0.0 && data[t][i][1] == 0.0) {

							a0_a += -probability_E2 * data[t][i][2] * e_lambda / (1 + e_lambda);
							a0_c += -probability_E2 * data[t][i][3] * e_lambda / (1 + e_lambda);
							a0_x1 += -probability_E2 * data[t][i][4] * e_lambda / (1 + e_lambda);
							a0_x2 += -probability_E2 * data[t][i][5] * e_lambda / (1 + e_lambda);
							a0_x3 += -probability_E2 * data[t][i][6] * e_lambda / (1 + e_lambda);
							a0_x4 += -probability_E2 * data[t][i][7] * e_lambda / (1 + e_lambda);
							a0_x5 += -probability_E2 * e_lambda / (1 + e_lambda);

						} else {

							continue;

						}

					} else {

						if (data[t][i][0] == 1.0 && data[t][i][1] == 0.0) {

							a1_a += probability_E2 * (data[t][i][2] - data[t][i][2] * e_lambda / (1 + e_lambda));
							a1_c += probability_E2 * (data[t][i][3] - data[t][i][3] * e_lambda / (1 + e_lambda));
							a1_x1 += probability_E2 * (data[t][i][4] - data[t][i][4] * e_lambda / (1 + e_lambda));
							a1_x2 += probability_E2 * (data[t][i][5] - data[t][i][5] * e_lambda / (1 + e_lambda));
							a1_x3 += probability_E2 * (data[t][i][6] - data[t][i][6] * e_lambda / (1 + e_lambda));
							a1_x4 += probability_E2 * (data[t][i][7] - data[t][i][7] * e_lambda / (1 + e_lambda));
							a1_x5 += probability_E2 * (1.0 - e_lambda / (1 + e_lambda));

						} else if (data[t][i][0] == 0.0 && data[t][i][1] == 0.0) {

							a0_a += -probability_E2 * data[t][i][2] * e_lambda / (1 + e_lambda);
							a0_c += -probability_E2 * data[t][i][3] * e_lambda / (1 + e_lambda);
							a0_x1 += -probability_E2 * data[t][i][4] * e_lambda / (1 + e_lambda);
							a0_x2 += -probability_E2 * data[t][i][5] * e_lambda / (1 + e_lambda);
							a0_x3 += -probability_E2 * data[t][i][6] * e_lambda / (1 + e_lambda);
							a0_x4 += -probability_E2 * data[t][i][7] * e_lambda / (1 + e_lambda);
							a0_x5 += -probability_E2 * e_lambda / (1 + e_lambda);

						} else {

							continue;

						}
					}

				}
			}

			// 更新，梯度上升法。
			lambda_update[0] = lambda_update[0] + alpha * (a1_a + a0_a);
			lambda_update[1] = lambda_update[1] + alpha * (a1_c + a0_c);
			lambda_update[2] = lambda_update[2] + alpha * (a1_x1 + a0_x1);
			lambda_update[3] = lambda_update[3] + alpha * (a1_x2 + a0_x2);
			lambda_update[4] = lambda_update[4] + alpha * (a1_x3 + a0_x3);
			lambda_update[5] = lambda_update[5] + alpha * (a1_x4 + a0_x4);
			lambda_update[6] = lambda_update[6] + alpha * (a1_x5 + a0_x5);

			float function_lambda_each = 0.0f;

			for (t = 0; t < TIME; t++) {

				for (i = 0; i < TARGET; i++) {

					float probability_E2 = computeProbabilityOfE(data[t][i][0], data[t][i][1], data[t][i][2],
							data[t][i][3], data[t][i][4], data[t][i][5], data[t][i][6], data[t][i][7]);

					float lambda_iteration = (float) (lambda_update[0] * data[t][i][2]
							+ lambda_update[1] * data[t][i][3] + lambda_update[2] * data[t][i][4]
							+ lambda_update[3] * data[t][i][5] + lambda_update[4] * data[t][i][6]
							+ lambda_update[5] * data[t][i][7] + lambda_update[6] * 1.0);

					float e_lambda = (float) Math.exp(lambda_iteration);

					if (data[t][i][3] != 0.0) {

						if (data[t][i][0] == 1.0 && data[t][i][1] == 1.0) {

							function_lambda_each = (float) (probability_E2 * ((e_lambda - Math.log(1 + e_lambda))));

						} else if (data[t][i][0] == 1.0 && data[t][i][1] == 0.0) {

							function_lambda_each = (float) (probability_E2 * ((e_lambda - Math.log(1 + e_lambda))));

						} else if (data[t][i][0] == 0.0 && data[t][i][1] == 0.0) {

							function_lambda_each = (float) (probability_E2 * ((-Math.log(1 + e_lambda))));

						} else {

							function_lambda_each = 0.0f;

						}

					} else {

						if (data[t][i][0] == 1.0 && data[t][i][1] == 0.0) {

							function_lambda_each = (float) (probability_E2 * ((e_lambda - Math.log(1 + e_lambda))));

						} else if (data[t][i][0] == 0.0 && data[t][i][1] == 0.0) {

							function_lambda_each = (float) (probability_E2 * ((-Math.log(1 + e_lambda))));

						} else {

							function_lambda_each = 0.0f;

						}

					}

					function_lambda_new += function_lambda_each;

				}
			}

			difference = (float) Math.abs(function_lambda_new - function_lambda_old);

			function_lambda_old = function_lambda_new;

			iteration += 1;
		}

		lambda_update[7] = function_lambda_old;

		System.out.println("迭代次数为：" + iteration);

		return lambda_update;
	}

	public static void optimalFunction(float[][][] data) {

		float sumFunctionValues_old = 0.0f;
		float difference = 50.0f;
		int iteration = 0;

		while (difference > THRESHLD && iteration < 10) {

			float sumFunctionValues_new = 0.0f;
			difference = 0.0f;

			float[] omega_update = new float[6];
			float[] lambda_update = new float[8];

			omega_update = optimalEM_1(data);

			for (int i = 0; i < 5; i++) {

				OMEGA[i] = omega_update[i];
				// System.out.print(omega_update[i] + " ");

			}

			lambda_update = optimalEM_2(data);

			for (int i = 0; i < 7; i++) {

				LAMBDA[i] = lambda_update[i];
				// System.out.print(lambda_update[i] + " ");

			}

			// System.out.print("\n");

			sumFunctionValues_new = omega_update[5] + lambda_update[7];// 返回的两个函数的值的和。

			difference = (float) (Math.abs(sumFunctionValues_old - sumFunctionValues_new));

			// System.out.println(difference);

			sumFunctionValues_old = sumFunctionValues_new;

			// iteration ++;
		}
	}

	// 把参数omega和lambda打印出来
	public static void printParameters() {

		float[] getOmega = new float[5];
		float[] getLambda = new float[7];

		for (int i = 0; i < 5; i++) {

			getOmega[i] = OMEGA[i];

		}

		for (int i = 0; i < 7; i++) {

			getLambda[i] = LAMBDA[i];

		}

		int i = 0, j = 0;

		System.out.println("最优参数Omega为：");

		for (i = 0; i < 5; i++) {

			System.out.print(getOmega[i] + ",");

		}

		System.out.println("\n最优参数Lambda为：");

		for (j = 0; j < 7; j++) {

			System.out.print(getLambda[j] + ",");

		}

		System.out.println("\n");

	}

	// 将数据导出到txt中，为cplex提供数据，解得混合整数规划的最优解。
	public static void giveDatasToCplex(Integer year) throws IOException {

		// 定义动态数组存储2015年的数据。
		float[][] tempArray = new float[TARGET][FEATURE];
		int i = 0, j = 0;

		float[][] dataToCplex = new float[TEMP][TARGET];
		float[][] gammaToCplex = new float[TARGET][K];
		float[][] muToCplex = new float[TARGET][K];

		Integer pYear = year - 1;
		String getYear = pYear.toString();
		BufferedReader bf1 = new BufferedReader(new FileReader("/Users/zhangxin/Desktop/log/" + getYear));
		String lineContent1 = null;

		while ((lineContent1 = bf1.readLine()) != null && i < TARGET) {

			String[] str = lineContent1.split(" ");

			for (j = 0; j < str.length; j++) {

				tempArray[i][j] = Float.parseFloat(str[j]);

			}

			i++;
		}

		bf1.close();

		// 对2015中数据进行归一化处理。
		float max_x1 = 0, min_x1 = 0;
		float max_x2 = 0, min_x2 = 0;
		float max_x3 = 0, min_x3 = 0;
		float max_x4 = 0, min_x4 = 0;

		for (i = 0; i < TARGET; i++) {

			if (max_x1 < tempArray[i][4]) {

				max_x1 = tempArray[i][4];

			}

			if (min_x1 > tempArray[i][4]) {

				min_x1 = tempArray[i][4];

			}

			if (max_x2 < tempArray[i][5]) {

				max_x2 = tempArray[i][5];

			}

			if (min_x2 > tempArray[i][5]) {

				min_x2 = tempArray[i][5];

			}

			if (max_x3 < tempArray[i][6]) {

				max_x3 = tempArray[i][6];

			}

			if (min_x3 > tempArray[i][6]) {

				min_x3 = tempArray[i][6];

			}

			if (max_x4 < tempArray[i][7]) {

				max_x4 = tempArray[i][7];

			}

			if (min_x4 > tempArray[i][7]) {

				min_x4 = tempArray[i][7];

			}

		}

		for (i = 0; i < TARGET; i++) {

			tempArray[i][4] = (tempArray[i][4] - min_x1) / (max_x1 - min_x1);

			tempArray[i][5] = (tempArray[i][5] - min_x2) / (max_x2 - min_x2);

			tempArray[i][6] = (tempArray[i][6] - min_x3) / (max_x3 - min_x3);

			tempArray[i][7] = (tempArray[i][7] - min_x4) / (max_x4 - min_x4);

		}

		float[][] getRewards = new float[TARGET][2];

		BufferedReader bf2 = new BufferedReader(new FileReader("/Users/zhangxin/Desktop/getDataToCplex/rewards.txt"));
		String lineContent2 = null;
		i = 0;

		while ((lineContent2 = bf2.readLine()) != null && i < TARGET) {

			String[] str = lineContent2.split(" ");

			for (j = 0; j < str.length; j++) {

				getRewards[i][j] = Float.parseFloat(str[j]);

			}

			i++;
		}

		bf2.close();

		// 计算cplex要用到的参数。
		int h = 0;
		i = 0;
		float function_gamma_1 = 0.0f;
		float function_mu_1 = 0.0f;
		float function_gamma = 0.0f;
		float function_mu = 0.0f;

		float[] getM = new float[TARGET];
		float[] getAlpha = new float[TARGET];
		float[] getTheta = new float[TARGET];
		float[] getP = new float[TARGET];
		float[][] getGamma = new float[TARGET][K];
		float[][] getMu = new float[TARGET][K];

		for (i = 0; i < TARGET; i++) {

			float e_lambda = (float) Math.exp(LAMBDA[0] * tempArray[i][2] + LAMBDA[1] * tempArray[i][3]
					+ LAMBDA[2] * tempArray[i][4] + LAMBDA[3] * tempArray[i][5] + LAMBDA[4] * tempArray[i][6]
					+ LAMBDA[5] * tempArray[i][7] + LAMBDA[6] * 1.0);

			float e_omega = (float) Math.exp(OMEGA[0] * tempArray[i][4] + OMEGA[1] * tempArray[i][5]
					+ OMEGA[2] * tempArray[i][6] + OMEGA[3] * tempArray[i][7] + OMEGA[4] * 1.0);

			getM[i] = (float) Math
					.exp(LAMBDA[0] * tempArray[i][0] + LAMBDA[2] * tempArray[i][4] + LAMBDA[3] * tempArray[i][5]
							+ LAMBDA[4] * tempArray[i][6] + LAMBDA[5] * tempArray[i][7] + LAMBDA[6] * 1.0);

			if (tempArray[i][0] == 1 && tempArray[i][1] == 1) {

				getAlpha[i] = 1;

			} else if (tempArray[i][0] == 0 && tempArray[i][1] == 1) {

				getAlpha[i] = 0;

			} else if (tempArray[i][0] == 1 && tempArray[i][1] == 0) {

				getAlpha[i] = (1 - tempArray[i][3] * e_omega / (1 + e_omega)) * e_lambda
						/ ((1 - tempArray[i][3] * e_omega / (1 + e_omega)) * e_lambda + 1);

			} else {

				getAlpha[i] = 1 / ((1 - tempArray[i][3] * e_omega / (1 + e_omega)) * e_lambda + 1);

			}

			getTheta[i] = (e_omega / (1 + e_omega)) * (getRewards[i][0] - getRewards[i][1]);

			getP[i] = getRewards[i][1];

			for (h = 0; h < K; h++) {

				function_gamma_1 = (float) ((getM[i] * ((h + 1) / (K * 1.0))
						* Math.exp(LAMBDA[1] * (h + 1) / (K * 1.0)))
						/ (1 + getM[i] * Math.exp(LAMBDA[1] * (h + 1) / (K * 1.0))));
				function_gamma = (float) ((getM[i] * (h / (K * 1.0)) * Math.exp(LAMBDA[1] * h / (K * 1.0)))
						/ (1 + getM[i] * Math.exp(LAMBDA[1] * h / (K * 1.0))));

				function_mu_1 = (float) ((getM[i] * Math.exp(LAMBDA[1] * (h + 1) / (K * 1.0)))
						/ (1 + (getM[i] * Math.exp(LAMBDA[1] * (h + 1) / (K * 1.0)))));
				function_mu = (float) ((getM[i] * Math.exp(LAMBDA[1] * h / (K * 1.0)))
						/ (1 + (getM[i] * Math.exp(LAMBDA[1] * h / (K * 1.0)))));

				getGamma[i][h] = K * (function_gamma_1 - function_gamma);
				getMu[i][h] = K * (function_mu_1 - function_mu);

			}

		}

		for (i = 0; i < 7; i++) {

			dataToCplex[0][i] = LAMBDA[i];

		}

		for (i = 0; i < 5; i++) {

			dataToCplex[1][i] = OMEGA[i];
		}

		for (i = 0; i < TARGET; i++) {

			dataToCplex[2][i] = getM[i];
			dataToCplex[3][i] = getAlpha[i];
			dataToCplex[4][i] = getTheta[i];
			dataToCplex[5][i] = getP[i];

		}

		for (i = 0; i < TARGET; i++) {

			for (j = 0; j < K; j++) {

				gammaToCplex[i][j] = getGamma[i][j];

				muToCplex[i][j] = getMu[i][j];

			}

		}

		File file1 = new File("/Users/zhangxin/Desktop/getDataToCplex/getParameters.txt");
		FileWriter out1 = new FileWriter(file1);

		for (i = 0; i < TEMP; i++) {

			for (j = 0; j < TARGET; j++) {

				if (j < TARGET - 1) {

					out1.write(Float.toString(dataToCplex[i][j]) + ",");

				} else {

					out1.write(Float.toString(dataToCplex[i][j]));

				}
			}

			if (i < TIME - 1) {

				out1.write("\n");

			} else {

				continue;

			}
		}

		out1.close();

		File file2 = new File("/Users/zhangxin/Desktop/getDataToCplex/getSlope_gamma.txt");
		FileWriter out2 = new FileWriter(file2);

		for (i = 0; i < TARGET; i++) {

			for (j = 0; j < K; j++) {

				if (j < K - 1) {

					out2.write(Float.toString(gammaToCplex[i][j]) + ",");

				} else {

					out2.write(Float.toString(gammaToCplex[i][j]));

				}
			}

			if (i < TARGET - 1) {

				out2.write("\n");

			} else {

				continue;

			}
		}

		out2.close();

		File file3 = new File("/Users/zhangxin/Desktop/getDataToCplex/getSlope_mu.txt");
		FileWriter out3 = new FileWriter(file3);

		for (i = 0; i < TARGET; i++) {

			for (j = 0; j < K; j++) {

				if (j < K - 1) {

					out3.write(Float.toString(gammaToCplex[i][j]) + ",");

				} else {

					out3.write(Float.toString(gammaToCplex[i][j]));

				}
			}

			if (i < TARGET - 1) {

				out3.write("\n");

			} else {

				continue;

			}
		}

		out3.close();
	}

	public static float[][] getParameters(float[][] dataArray) {

		FileReader file;

		try {
			file = new FileReader("/Users/zhangxin/Desktop/getDataToCplex/getParameters.txt");
			BufferedReader buffer = new BufferedReader(file);

			String line = null;
			String[] temp = null;
			int i = 0;

			while ((line = buffer.readLine()) != null) {
				temp = line.split(",");

				for (int j = 0; j < temp.length; j++) {
					dataArray[i][j] = Float.parseFloat(temp[j]);
				}
				i++;
			}

			buffer.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataArray;
	}

	public static float[][] getGamma(float[][] gammaArray) {

		FileReader file;

		try {
			file = new FileReader("/Users/zhangxin/Desktop/getDataToCplex/getSlope_gamma.txt");
			BufferedReader buffer = new BufferedReader(file);

			String line = null;
			String[] temp = null;
			int i = 0;

			while ((line = buffer.readLine()) != null) {
				temp = line.split(",");

				for (int j = 0; j < temp.length; j++) {
					gammaArray[i][j] = Float.parseFloat(temp[j]);
				}
				i++;
			}

			buffer.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gammaArray;
	}

	public static float[][] getMu(float[][] muArray) {

		FileReader file;

		try {
			file = new FileReader("/Users/zhangxin/Desktop/getDataToCplex/getSlope_mu.txt");
			BufferedReader buffer = new BufferedReader(file);

			String line = null;
			String[] temp = null;
			int i = 0;

			while ((line = buffer.readLine()) != null) {
				temp = line.split(",");

				for (int j = 0; j < temp.length; j++) {
					muArray[i][j] = Float.parseFloat(temp[j]);
				}
				i++;
			}

			buffer.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return muArray;
	}

	public static float[] getEM(Integer year) throws SQLException, IOException {

		float[] result = new float[100];
		float[][][] data = new float[TIME][TARGET][FEATURE];
		int start = year - TIME;

		for (int time = start; time < year; time++) {

			int i = 0;
			int t = time - start;

			Connection conn = DBConn.getConnection();
			StringBuilder build = new StringBuilder();
			build.append("select * from parking_incident where parking_year = ?");

			PreparedStatement ptmt = conn.prepareStatement(build.toString());
			ptmt.setInt(1, time);
			ResultSet rs = ptmt.executeQuery();

			while (rs.next()) {

				data[t][i][0] = rs.getFloat("parking_attack_now");
				data[t][i][1] = rs.getFloat("parking_catch");
				data[t][i][2] = rs.getFloat("parking_attack_past");
				data[t][i][3] = rs.getFloat("parking_coverage");
				data[t][i][4] = rs.getFloat("parking_density");
				data[t][i][5] = rs.getFloat("parking_time");
				data[t][i][6] = rs.getFloat("parking_distance1");
				data[t][i][7] = rs.getFloat("parking_distance2");
				i++;
			}

			while (i < 100) {

				for (int j = 0; j < 8; j++) {
					data[t][i][j] = 0.0f;
				}

				i++;
			}
		}

		// 对数据进行预处理，归一化。
		int t = 0;
		int i = 0;
		int j = 0;
		float max_x1 = 0, min_x1 = 0;
		float max_x2 = 0, min_x2 = 0;
		float max_x3 = 0, min_x3 = 0;
		float max_x4 = 0, min_x4 = 0;

		for (t = 0; t < TIME; t++) {

			for (i = 0; i < TARGET; i++) {

				if (max_x1 < data[t][i][4]) {

					max_x1 = data[t][i][4];

				}

				if (min_x1 > data[t][i][4]) {

					min_x1 = data[t][i][4];

				}

				if (max_x2 < data[t][i][5]) {

					max_x2 = data[t][i][5];

				}

				if (min_x2 > data[t][i][5]) {

					min_x2 = data[t][i][5];

				}

				if (max_x3 < data[t][i][6]) {

					max_x3 = data[t][i][6];

				}

				if (min_x3 > data[t][i][6]) {

					min_x3 = data[t][i][6];

				}

				if (max_x4 < data[t][i][7]) {

					max_x4 = data[t][i][7];

				}

				if (min_x4 > data[t][i][7]) {

					min_x4 = data[t][i][7];

				}

			}

		}

		for (t = 0; t < TIME; t++) {

			for (i = 0; i < TARGET; i++) {

				data[t][i][4] = (data[t][i][4] - min_x1) / (max_x1 - min_x1);

				data[t][i][5] = (data[t][i][5] - min_x2) / (max_x2 - min_x2);

				data[t][i][6] = (data[t][i][6] - min_x3) / (max_x3 - min_x3);

				data[t][i][7] = (data[t][i][7] - min_x4) / (max_x4 - min_x4);

			}
		}

		optimalFunction(data);

		printParameters();

		giveDatasToCplex(year);

		// cplex部分
		// 读取文件中的数据并存储到数组中
		float[][] dataArray = new float[10][100];
		float[][] gammaArray = new float[100][10];
		float[][] muArray = new float[100][10];

		dataArray = getParameters(dataArray);
		gammaArray = getGamma(gammaArray);
		muArray = getMu(muArray);

		// System.out.println(muArray[0][4]);

		int n = 100; // 目标数目
		int K = 5; // piecewise数目
		int B = 45; // 总资源数目
		float[] alpha = new float[n];
		float[] theta = new float[n];
		float[][] gamma = new float[n][K];
		float[][] mu = new float[n][K];
		float[] p = new float[n];
		float[] m = new float[n];

		for (i = 0; i < n; i++) {

			m[i] = dataArray[2][i];
			alpha[i] = dataArray[3][i];
			theta[i] = dataArray[4][i];
			p[i] = dataArray[5][i];
		}

		for (i = 0; i < n; i++) {

			for (j = 0; j < K; j++) {

				gamma[i][j] = gammaArray[i][j];
				mu[i][j] = muArray[i][j];
			}
		}

		double[] getValues = new double[K];
		double[] getC = new double[n];

		try {

			IloCplex cplex = new IloCplex();

			IloNumVar[][] c = new IloNumVar[n][];
			IloIntVar[][] z = new IloIntVar[n][];

			for (i = 0; i < n; i++) {
				c[i] = cplex.numVarArray(K, 0, 1.0 / K);
				z[i] = cplex.boolVarArray(K);
			}

			IloLinearNumExpr[] sum = new IloLinearNumExpr[1];

			IloLinearNumExpr objective = cplex.linearNumExpr();

			for (i = 0; i < n; i++) {
				for (int h = 0; h < K; h++) {
					objective.addTerm(alpha[i] * theta[i] * gamma[i][h], c[i][h]);
					objective.addTerm(alpha[i] * p[i] * mu[i][h], c[i][h]);
				}
			}

			// define objective
			cplex.addMaximize(objective);
			sum[0] = cplex.linearNumExpr();

			// constraints
			for (i = 0; i < n; i++) {
				for (int h = 0; h < K; h++) {
					sum[0].addTerm(1.0, c[i][h]);
				}
			}
			cplex.addLe(sum[0], B);

			for (i = 0; i < n; i++) {
				for (int h = 0; h < K - 1; h++) {
					cplex.addLe(cplex.sum(cplex.prod(1.0 / K, z[i][h]), cplex.negative(c[i][h])), 0);
					cplex.addLe(cplex.sum(c[i][h + 1], cplex.negative(z[i][h])), 0);
				}
			}

			cplex.setParam(IloCplex.Param.Simplex.Display, 0);

			if (cplex.solve()) {

				for (i = 0; i < n; i++) {
					int getI = i + 1;
					getValues = cplex.getValues(c[i]);

					for (int h = 0; h < K; h++) {
						getC[i] += getValues[h];
					}
					result[i] = (float) getC[i];
					System.out.println("[区域" + getI + "]" + getC[i]);
				}
			} else {
				System.out.println("Problem not solved");
			}

			cplex.end();

		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return result;
	}
}
