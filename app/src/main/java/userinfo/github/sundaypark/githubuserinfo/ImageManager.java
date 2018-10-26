package userinfo.github.sundaypark.githubuserinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageManager {

    private File Basedir;


    public ImageManager(Context context) {
        Basedir = context.getCacheDir();
    }



    /**
     * 파일이 있다면 파일 리턴
     * 없다면 다운로드후 Bitmap 리턴
     * @param url
     * @param
     * @return
     */
    public Bitmap getImageFile(String url) {
        Log.v("HTTP", "IAMGE = " + url);
        try {
            File imageFile = new File(Basedir, "user");
            if (imageFile.exists()) {
                return decodeFile(imageFile );
            }

            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = null;

            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(imageFile);
            final int buffer_size = 1024;
            byte[] bytes = new byte[1024];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
            os.close();
            conn.disconnect();
            return decodeFile(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 이미지 리싸이징후 반환
     *
     * @param f
     * @return
     */
    private Bitmap decodeFile(File f ) {
        try {
            // 이미지의 크기만 가져와서 싸이즈를 계산한뒤 불러온다
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();

            final int REQUIRED_SIZE =200;
            if(REQUIRED_SIZE == 0){
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                FileInputStream stream2 = new FileInputStream(f);
                Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
                stream2.close();
                return bitmap;
            }
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
