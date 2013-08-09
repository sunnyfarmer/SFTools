package sf.math;

import android.graphics.Point;

public class SFMath {

	/**
	 * 计算直线的长度
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
}
