package com.demo.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class RandomCodeUtil
{
    private static Random random = new Random();

    private Color getRandColor(int fc, int bc)
    {
        if (fc > 255)
        {
            fc = 255;
        }
        if (bc > 255)
        {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private int getRandomIntColor()
    {
        int[] rgb = getRandomRgb();
        int color = 0;
        // for (int c : rgb)
        // {
        // color = color << 8;
        // color = color | c;
        // }
        int rgbSize = rgb.length;
        for (int i = 0; i < rgbSize; i++)
        {
            int c = rgb[i];
            color = color << 8;
            color = color | c;
        }

        return color;
    }

    private int[] getRandomRgb()
    {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++)
        {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private void shear(Graphics g, int w1, int h1, Color color)
    {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private void shearX(Graphics g, int w1, int h1, Color color)
    {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);
        double d = 0.0;
        int dd = 0;
        for (int i = 0; i < h1; i++)
        {
            d = (period >> 1) * Math.sin(i * 1.0 / period * 1.0 + (6.2831853071795862D * phase) / frames);
            dd = (int)d;//Integer.parseInt(d+""); (new Double(d)).intValue();
            g.copyArea(0, i, w1, 1, dd, 0);
            if (borderGap)
            {
                g.setColor(color);
                g.drawLine(dd, i, 0, i);
                g.drawLine(dd + w1, i, w1, i);
            }
        }

    }

    private void shearY(Graphics g, int w1, int h1, Color color)
    {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        double d = 0.0;
        int dd = 0;
        for (int i = 0; i < w1; i++)
        {
            d = (period >> 1) * Math.sin(i * 1.0 / period * 1.0 + (6.2831853071795862D * phase) / frames);
            dd = (int)d ;//Integer.parseInt(d+""); //(new Double(d)).intValue();
            g.copyArea(i, 0, 1, h1, 0, dd);
            if (borderGap)
            {
                g.setColor(color);
                g.drawLine(i, dd, i, 0);
                g.drawLine(i, dd + h1, i, h1);
            }

        }
    }

    /**
     * 生成指定长度的随机数字和字母
     * 
     * @param length
     * @return
     */
    public String getStringRandom(int length)
    {
        String val = "";
        StringBuffer valBuffer = new StringBuffer();
        Random random = new Random();
        int charOrNum = 0;
        for (int i = 0; i < length; i++)
        {
            charOrNum = random.nextInt(2) % 2 == 0 ? 1 : 2;
            switch (charOrNum)
            {
                case 1:
                    int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    valBuffer.append((char) (random.nextInt(26) + temp));
                    // val += (char) (random.nextInt(26) + temp);
                    break;
                default:
                      valBuffer.append(String.valueOf(random.nextInt(10)));
                    break;
            }
        }
        val = valBuffer.toString();

        return val;
    }

    /**
     * Base64编码的验证码图片
     * 
     * @param w
     * @param h
     * @param code
     * @return
     * @throws Exception
     */
    public String imageToBase64(int w, int h, String code) throws Exception
    {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]
        { Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++)
        {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h - 4);// zwx516073 改4

        // 绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色

        int x = 0;
        int y = 0;
        int xl = 0;
        int yl = 0;
        for (int i = 0; i < 20; i++)
        {
            x = random.nextInt(w - 1);
            y = random.nextInt(h - 1);
            xl = random.nextInt(6) + 1;
            yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        int x1 = 0;
        int y1 = 0;
        int rgb = 0;
        char[] chars;
        for (int i = 0; i < area; i++)
        {
            x1 = random.nextInt(w);
            y1 = random.nextInt(h);
            rgb = getRandomIntColor();
            image.setRGB(x1, y1, rgb);
        }

        shear(g2, w, h, c);// 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;// zwx516073 改4
        Font font = new Font("Arial", Font.ITALIC, fontSize);
        g2.setFont(font);
        chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++)
        {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4.0 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w / (verifySize*1.0)) * i + fontSize /2.0,
                    h / 2.0);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
        }
        g2.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return new Base64().encodeToString(baos.toByteArray());
    }

}
