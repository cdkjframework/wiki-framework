package com.cdkjframework.util.tool;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.number.ConvertUtils;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: com.lesmarthome.iot
 * @Package: com.lesmarthome.iot.util
 * @ClassName: PositionUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2022/6/18 16:52
 * @Version: 1.0
 */
public class PositionUtils {

    /**
     * 地球半径
     */
    private final static double EARTH_RADIUS = 6378.137;

    /**
     * 多边型电子围栏围栏计算
     *
     * @param position     位置
     * @param positionList 位置列表
     * @return 返回是否在范围
     */
    public static boolean isInPolygonFence(Position position, List<Position> positionList) {
        // 位置信息
        Point2D.Double point = new Point2D.Double(position.latitude.doubleValue(), position.longitude.doubleValue());
        List<Point2D.Double> pointList = new ArrayList<>();
        for (Position pos : positionList) {
            double polygonPoint_x = pos.latitude.doubleValue();
            double polygonPoint_y = pos.longitude.doubleValue();
            Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x, polygonPoint_y);
            pointList.add(polygonPoint);
        }
        // 验证是否在圆上
        return isPtInPoly(point, pointList);
    }

    /**
     * 圆型电子围栏围栏计算
     *
     * @param center   点心点位置
     * @param position 位置
     * @return 返回是否在范围
     */
    public static BigDecimal circularFence(Position center, Position position) {
        double radCenterLatitude = rad(center.latitude.doubleValue());
        double radLatitude = rad(position.latitude.doubleValue());
        double distanceLatitude = radCenterLatitude - radLatitude;
        double longitude = rad(center.longitude.doubleValue()) - rad(position.longitude.doubleValue());
        double distance = IntegerConsts.TWO * Math.asin(Math.sqrt(Math.pow(Math.sin(distanceLatitude / IntegerConsts.TWO), IntegerConsts.TWO) +
                Math.cos(radCenterLatitude) * Math.cos(radLatitude) * Math.pow(Math.sin(longitude / IntegerConsts.TWO), IntegerConsts.TWO)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000d) / 10000d;
        distance = distance * IntegerConsts.ONE_THOUSAND;
        // 返回结果
        return BigDecimal.valueOf(distance);
    }

    /**
     * 将百度地图经纬度转换为腾讯/高德地图经纬度        用于大屏
     *
     * @param lng 经度
     * @param lat 纬度
     * @return 返回位置
     */
    public static Position baiduMapToAMap(BigDecimal lng, BigDecimal lat) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = lng.doubleValue() - 0.0065;
        double y = lat.doubleValue() - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double lngs = z * Math.cos(theta);
        double lats = z * Math.sin(theta);

        // 返回结果
        return new Position(BigDecimal.valueOf(lngs), BigDecimal.valueOf(lats));
    }

    /**
     * 将腾讯/高德地图经纬度转换为百度地图经纬度
     *
     * @param lng 经度
     * @param lat 纬度
     * @return 返回位置
     */
    public static Position aMapToBaiduMap(BigDecimal lng, BigDecimal lat) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = lng.doubleValue();
        double y = lat.doubleValue();
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double lngs = z * Math.cos(theta) + 0.0065;
        double lats = z * Math.sin(theta) + 0.006;

        // 返回结果
        return new Position(BigDecimal.valueOf(lngs), BigDecimal.valueOf(lats));
    }

    /**
     * 半径计算
     *
     * @param value 值
     * @return 返回结果
     */
    private static double rad(double value) {
        return value * Math.PI / 180.0;
    }

    /**
     * 判断点是否在多边形内，如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
     *
     * @param point     检测点
     * @param pointList 多边形的顶点
     * @return 点在多边形内返回true, 否则返回false
     */
    private static boolean isPtInPoly(Point2D.Double point, List<Point2D.Double> pointList) {
        int size = pointList.size();
        int intersectCount = IntegerConsts.ZERO;
        //浮点类型计算时候与0比较时候的容差
        double precision = 2e-10;
        Point2D.Double p1, p2;
        //当前点
        Point2D.Double p = point;
        p1 = pointList.get(IntegerConsts.ZERO);
        for (int i = IntegerConsts.ONE; i <= size; ++i) {
            if (p.equals(p1)) {
                return true;
            }

            p2 = pointList.get(i % size);//right vertex
            if (p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)) {//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if (p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)) {//ray is crossing over by the algorithm (common part of)
                if (p.y <= Math.max(p1.y, p2.y)) {//x is before of ray
                    if (p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)) {//overlies on a horizontal ray
                        return true;
                    }
                    if (p1.y == p2.y) {//ray is vertical
                        if (p1.y == p.y) {//overlies on a vertical ray
                            return true;
                        } else {//before ray
                            ++intersectCount;
                        }
                    } else {//cross point on the left side
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y
                        if (Math.abs(p.y - xinters) < precision) {//overlies on a ray
                            return true;
                        }

                        if (p.y < xinters) {//before ray
                            ++intersectCount;
                        }
                    }
                }
            } else {//special case when ray is crossing through the vertex
                if (p.x == p2.x && p.y <= p2.y) {//p crossing over p2
                    Point2D.Double p3 = pointList.get((i + IntegerConsts.ONE) % size); //next vertex
                    if (p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)) {//p.x lies between p1.x & p3.x
                        ++intersectCount;
                    } else {
                        intersectCount += IntegerConsts.TWO;
                    }
                }
            }
            p1 = p2;//next ray left point
        }
        // 奇数在多边形内 偶数在多边形外
        if (intersectCount % IntegerConsts.TWO == IntegerConsts.ZERO) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 位置
     */
    static final class Position {

        /**
         * 经度
         */
        private BigDecimal longitude;

        /**
         * 纬度
         */
        private BigDecimal latitude;

        /**
         * 构造函数
         */
        public Position() {
        }

        /**
         * 构造函数 2
         */
        public Position(BigDecimal longitude, BigDecimal latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }

    public static void main(String[] args) {
        Position p = baiduMapToAMap(BigDecimal.valueOf(31.830429), BigDecimal.valueOf(117.219115));
        List<Position> list = new ArrayList<>();
        String[] keyList = "31.839064_117.219116,31.83253_117.219403,31.828511_117.218146,31.826763_117.219259,31.826118_117.220517,31.822713_117.23586,31.822958_117.238375,31.838512_117.23798,31.839617_117.226194,31.839586_117.222925".split(",");
        for (String key :
                keyList) {
            String[] values = key.split("_");
            list.add(baiduMapToAMap(ConvertUtils.convertDecimal(values[0]), ConvertUtils.convertDecimal(values[1])));
        }
        System.out.printf(String.valueOf(isInPolygonFence(p, list)));
    }
}
