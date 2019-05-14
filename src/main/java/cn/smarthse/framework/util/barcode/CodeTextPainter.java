package cn.smarthse.framework.util.barcode;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 */
public class CodeTextPainter {

  /**
   * 数字部分左右留白宽度
   */
  public static final int MARGIN = 10;
  public static final int FONT_SIZE = 11;
  private static final Font FONT = new Font("monospace", Font.PLAIN, FONT_SIZE);
  /**
   * 获取单例，枚举方式
   */
  private enum Singleton {
    /** 枚举单例 */
    INSTANCE;
    private CodeTextPainter textPainter;

    Singleton() {
      textPainter = new CodeTextPainter();
    }

    private CodeTextPainter getInstance() {
      return textPainter;
    }
  }

  public static Font getFont() {
    return FONT;
  }

  /**
   * 获取单实例
   * @return Ean13TextPainter
   */
  public static CodeTextPainter getInstance() {
    return CodeTextPainter.Singleton.INSTANCE.getInstance();
  }

  private CodeTextPainter() {
  }

  public void paintText(BufferedImage image, String code) {
    // 获取条码的图像宽高
    int width = image.getWidth();
    int height = image.getHeight();
    Graphics2D g = image.createGraphics();
    g.drawImage(image, 0, 0, width, height, null);
    // 设置数字的遮盖效果
    g.setColor(Color.WHITE);
    g.fillRect(0, height - FONT_SIZE, width, FONT_SIZE);
    g.setFont(getFont());
    g.setColor(Color.BLACK);
    int fontY = height - FONT_SIZE / 4;
    // 两端对齐
    drawString(g, code, 2 * MARGIN, fontY,
        width - 2 * MARGIN);
  }

  public static void drawString(Graphics g, String code, int x, int y, int maxWidth) {
    String[] split = code.split("");
    int len = split.length;
    int step = new Long(Math.round(maxWidth / len)).intValue();
    int tmpX = x;
    for (int i = 0; i < len; i++) {
      g.drawString(split[i], tmpX, y);
      tmpX = tmpX + step;
    }
  }
}