package sf.math;

import android.graphics.Point;
import android.graphics.PointF;

public class SFMath {

	/**
	 * ����ֱ�ߵĳ���
	 * @param beginPoint
	 * @param endPoint
	 * @return
	 */
	public static double lengthOfLine(Point beginPoint, Point endPoint) {
		double length = 0.0f;
		double gapOfX = endPoint.x-beginPoint.x;
		double gapOfY = endPoint.y-beginPoint.y;
		length = Math.sqrt(gapOfX*gapOfX + gapOfY*gapOfY);
		return length;
	}

	public static double lengthOfLine(PointF beginPoint, PointF endPoint) {
		double length = 0.0f;
		double gapOfX = endPoint.x - beginPoint.x;
		double gapOfY = endPoint.y - beginPoint.y;
		length = Math.sqrt(gapOfX*gapOfX + gapOfY*gapOfY);
		return length;
	}

	public static int getPower(double num) {
		int power = 0;
		if (num >= 1.0f) {
			int cot = 1;
			while (true) {
				double powerNumber = Math.pow(10.0f, cot);
				if (num/powerNumber<1.0f) {
					power = cot;
					break;
				}
				cot++;
			}
		} else {
			int cot = -1;
			while (true) {
				double powerNumber = Math.pow(10.0f, cot);
				if (num/powerNumber>=1.0f) {
					power = cot;
					break;
				}
				cot--;
			}
		}
		return power;
	}

	public static boolean dataInRange(int min, int max, int data) {
		boolean rs = false;
		if (min > max) {
			int tmp = max;
			max = min;
			min = tmp;
		}
		if (data >= min && data <= max) {
			rs = true;
		}
		return rs;
	}
	public static int[] getMinMax(int d1, int d2) {
		int[] data = new int[2];
		if (d1 > d2) {
			data[0] = d2;
			data[1] = d1;
		} else {
			data[0] = d1;
			data[1] = d2;
		}
		return data;
	}

	public static boolean dataInRange(float min, float max, float data) {
		boolean rs = false;
		if (min > max) {
			float tmp = max;
			max = min;
			min = tmp;
		}
		if (data >= min && data <= max) {
			rs = true;
		}
		return rs;
	}
	public static float[] getMinMax(float d1, float d2) {
		float[] data = new float[2];
		if (d1 > d2) {
			data[0] = d2;
			data[1] = d1;
		} else {
			data[0] = d1;
			data[1] = d2;
		}
		return data;
	}
	public static boolean dataInRange(double min, double max, double data) {
		boolean rs = false;
		if (min > max) {
			double tmp = max;
			max = min;
			min = tmp;
		}
		if (data >= min && data <= max) {
			rs = true;
		}
		return rs;
	}
	public static double[] getMinMax(double d1, double d2) {
		double[] data = new double[2];
		if (d1 > d2) {
			data[0] = d2;
			data[1] = d1;
		} else {
			data[0] = d1;
			data[1] = d2;
		}
		return data;
	}
}
