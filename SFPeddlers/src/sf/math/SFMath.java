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
		if (num > 1.0f) {
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
			double powerNumber = Math.pow(10.0f, cot);
			while (true) {
				if (num/powerNumber>=1.0f) {
					power = cot;
					break;
				}
				cot--;
			}
		}
		return power;
	}
}
