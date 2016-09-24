package com.sun.myzxing_scan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.zxing.activity.CaptureActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button bt_scan;
    Button bt_create;
    EditText bt_text;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_scan= (Button) findViewById(R.id.bt_scan);
        bt_text= (EditText) findViewById(R.id.et_text);
        bt_create= (Button) findViewById(R.id.bt_create);
        imageView= (ImageView) findViewById(R.id.imageView);
        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent,100);
            }
        });
        bt_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mCode=bt_text.getText().toString();
                Bitmap qrCode = getQRCode(mCode, 300);
                imageView.setImageBitmap(qrCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            if (resultCode==RESULT_OK){
                if (data!=null){
                    String result=data.getStringExtra("result");
                    Log.e("--result---",result);
                   Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                    intent.putExtra("result",result);
                    startActivity(intent);
                }
            }
        }
    }
    /**
     * 生成二维码
     *
     * @param str：生成二维码的信息
     * @param wAndH：二维码图片的宽和高
     * @return
     */

    public Bitmap getQRCode(String str, int wAndH) {

        //构建生成二维码的对象
        MultiFormatWriter formatWriter = new MultiFormatWriter();
        //调用其中encode方法生成的位图矩阵BitMatrix
        /**
         * 参数1:生成二维码需要的字符串信息
         * 参数2：生成码的格式(条形码，一维码，二维码)
         * 参数3:生成位图矩阵的宽
         * 参数4:生成位图矩阵的高
         * 参数5:对生成二维码过程的一些配置Map
         */
        Map<EncodeHintType, Object> map = new HashMap<>();
        //设置生成二维码的编码字符类型
        map.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            //把字符串信息转换成位图矩阵
            BitMatrix bitMatrix = formatWriter.encode(str, BarcodeFormat.QR_CODE, wAndH, wAndH, map);

            int[] p = new int[wAndH * wAndH];
            //把位图矩阵转化成一维数组(存储颜色信息)
            //外层控制行数
            for (int i = 0; i < wAndH; i++) {
                //j控制列数
                for (int j = 0; j < wAndH; j++) {
                    //说明在i行j列上有值
                    if (bitMatrix.get(i, j)) {
                        //表示有值
                        p[i * wAndH + j] = Color.BLACK;
                    } else {
                        //表示为空
                        p[i * wAndH + j] = Color.WHITE;
                    }

                }
            }

            //把颜色只变成像素点填充为图片Bitmap
            //先创建一个空的bitmap(宽和高，Config)
            Bitmap bmp = Bitmap.createBitmap(wAndH, wAndH, Bitmap.Config.ARGB_8888);

            //把颜色值当作像素点填到Bitmap
            /**
             * 参数1:把一维数组存储的值写入到Bitmap
             * 参数2:从一维数组中读取的第一个像素值的索引或者下标
             * 参数3:宽度
             * 参数4:写入图片的第一个像素点的x坐标
             * 参数5:写入图片的第一个像素点的y坐标
             * 参数6:宽度
             * 参数7:高度
             */
            bmp.setPixels(p, 0, wAndH, 0, 0, wAndH, wAndH);

            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;

    }
}
