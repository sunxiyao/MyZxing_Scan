package com.zxing.encoding;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * @author Ryan Tang
 */
public final class EncodingHandler {
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static Bitmap createQRCode(String str, int widthAndHeight) throws WriterException {
        //设置生成二维码的字符编码类型UTF-8
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //把要生成二维码的字符串读取到位图矩阵当中
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //将位图矩阵的数据存储到一维数组pixels中
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                } else {
                    pixels[y * width + x] = WHITE;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
/**
 * 参数1:写到位图中的颜色值
 * 参数2:从一维数组pixels中读取的第一个颜色值的索引
 * 参数3:位图的宽度
 * 参数4:被写入位图中第一个像素的X坐标
 * 参数5:被写入位图中第一个像素的Y坐标
 * 参数6:从pixels[]中拷贝的每行的颜色个数
 * 参数7：写入到位图中的行数
 */

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    //将两个bitmap合并成一个
    public static Bitmap createBitmap(Bitmap bmp01, Bitmap bmp02) {
        if (bmp01 == null) {
            return null;
        }
        int w1 = bmp01.getWidth();
        int h1 = bmp01.getHeight();
        int w2 = bmp02.getWidth();
        int h2 = bmp02.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w1, h1, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawBitmap(bmp01, 0, 0, null);
        c.drawBitmap(bmp02, w1 / 2 - w2 / 2, h1 / 2 - h2 / 2, null);
        c.save(Canvas.ALL_SAVE_FLAG);//保存
        c.restore();//存储
        return bmp;

    }
}
