package com.nameless.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MathStatic {

    public static Vector2 RotateVector2(Vector2 point, float degrees, Vector2 pivot)
    {
        float radians = MathUtils.degreesToRadians * degrees;
        float cosRadians = MathUtils.cos(radians);
        float sinRadians = MathUtils.sin(radians);

        Vector2 translatedPoint = new Vector2();
        translatedPoint.x = point.x - pivot.x;
        translatedPoint.y = point.y - pivot.y;

        Vector2 rotatedPoint = new Vector2();
        rotatedPoint.x = translatedPoint.x * cosRadians - translatedPoint.y * sinRadians + pivot.x;
        rotatedPoint.y = translatedPoint.x * sinRadians + translatedPoint.y * cosRadians + pivot.y;

        return rotatedPoint;
    }

    public static float sum(float[] values){
        float sum = 0;
        for(float value : values) sum += value;
        return sum;
    }

    public static Vector2 V2dividedByF(Vector2 vec, float f){
        return new Vector2(vec.x / f, vec.y / f);
    }

    public static Vector2 V2xf(Vector2 v, float value){
        return new Vector2(v.x*value, v.y*value);
    }

    public static Vector2 V2minusV2(Vector2 v1, Vector2 v2){
        return new Vector2(v1.x - v2.x, v1.y - v2.y);
    }

    public static Vector2 clampV2(Vector2 value, float min, float max){
        Vector2 result = new Vector2(value.x,value.y);
        if (value.x < min) result.x = min;
        if (value.x > max) result.x = max;

        if (value.y < min) result.y = min;
        if (value.y > max) result.y = max;

        return result;
    }

    public static float getDistancePointToPoint(Vector2 p1, Vector2 p2){
        float x1 = p1.x;
        float y1 = p1.y;
        float x2 = p2.x;
        float y2 = p2.y;
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static Vector2 getDirection(Vector2 target, Vector2 myPos){
        float x = target.x - myPos.x;
        float y = target.y - myPos.y;
        return new Vector2(x,y).nor();
    }
}
