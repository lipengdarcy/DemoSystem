package cn.smarthse.framework.util.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoulj(周利军) [1217102780@qq.com]
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments: <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 44
 * @since 2019/3/11-9:44
 */
public class BrCodeUtil {

    public static final int BLACK = 0xFF000000;
    public static final int WHITE = 0xFFFFFFFF;
    /**
     * 条形码默认宽度和高度
     */
    private static final int BR_CODE_WIDTH = 120;
    private static final int BR_CODE_HEIGHT = 50;
    private static final String UTF8 = "UTF-8";

    public static final int MARGIN = 10;
    private static BitMatrix encode(String content,
                                    BarcodeFormat format,
                                    int width,
                                    int height) throws WriterException {
        final MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        // 提供给编码器的附加参数
        final Map<EncodeHintType, Object> hints = new HashMap<>(2);
        hints.put(EncodeHintType.CHARACTER_SET, UTF8);
        if (format == BarcodeFormat.QR_CODE) {
            hints.put(EncodeHintType.MARGIN, 1);
        } else {
            // 左右边距。实际宽度是width + margin
            hints.put(EncodeHintType.MARGIN, 2 * MARGIN);
        }
        return multiFormatWriter.encode(content, format, width, height, hints);
    }

    /**
     * {@link BitMatrix} 转 {@link BufferedImage}
     * @param matrix {@link BitMatrix}
     * @return {@link BufferedImage}
     */
    public static BufferedImage toImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void brEncode(String content, OutputStream outputStream) throws WriterException, IOException {
        final BitMatrix bitMatrix = encode(content, BarcodeFormat.CODE_39, BR_CODE_WIDTH, BR_CODE_HEIGHT);
        BufferedImage image = toImage(bitMatrix);
        CodeTextPainter.getInstance().paintText(image, content);
        ImageIO.write(image, "jpg", outputStream);
    }
}
