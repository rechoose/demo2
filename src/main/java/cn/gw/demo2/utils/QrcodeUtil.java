package cn.gw.demo2.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class QrcodeUtil {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private QrcodeUtil() {
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void createQrcodeToFile(String content, String file) throws Exception {
        if (StringUtils.isEmpty(file)) {
            throw new IllegalArgumentException("arg file is null");
        }
        File test = CommonUtils.getFile(file, "qrcode_test.jpg");
        String path = test.getPath();
        String format = path.substring(path.lastIndexOf(".") + 1);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix matrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 900, 900, hints);
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, test)) {
            throw new IOException("Could not write an image of format " + format);
        }
        System.out.println("二维码已生成！");
    }

    public static void createQrcodeToStream(String content, String format, OutputStream stream) throws Exception {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix matrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 900, 900, hints);
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
        System.out.println("二维码已生成！");
    }

    /**
     * 读二维码并输出携带的信息
     */
    public static String readQrCode(InputStream inputStream) throws IOException {
        //从输入流中获取字符串信息
        BufferedImage image = ImageIO.read(inputStream);
        //将图像转换为二进制位图源
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(bitmap);
        } catch (ReaderException e) {
            e.printStackTrace();
        }
        if (result == null) {
            throw new RuntimeException("Nothing is acceptable");
        }
        System.out.println(result.getText());
        return result.getText();
    }


    public static void main(String[] args) throws Exception {
        QrcodeUtil.createQrcodeToFile("http://weixin.qq.com/r/dkgOFovEh9b4rc919x1k", "f:\\test\\4.png");
//        QrcodeUtil.readQrCode(new FileInputStream("f:\\test\\qq2.png"));
//        QrcodeUtil.readQrCode(new FileInputStream("f:\\test\\qq.png"));
    }
}
