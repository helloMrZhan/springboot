package com.zjq.ocr.util;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImagePreprocessor {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * 身份证专用预处理：轻量级优化，避免过度处理
     */
    public static byte[] preprocessForIdCard(byte[] imageBytes) throws IOException {
        Mat src = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_COLOR);
        if (src.empty()) {
            throw new IOException("无法解码图像");
        }

        Mat gray = new Mat();
        Mat enhanced = new Mat();

        // 1. 转灰度（必须）
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // 2. 【关键】对比度拉伸（CLAHE）— 比普通直方图均衡更柔和
        CLAHE clahe = Imgproc.createCLAHE(2.0, new Size(8, 8));
        clahe.apply(gray, enhanced);

        // 3. 【可选】轻微中值滤波去噪（不模糊边缘）
        Mat denoised = new Mat();
        Imgproc.medianBlur(enhanced, denoised, 3); // 核大小=3，很轻

        // 4. 转回 PNG 字节（保留灰度，不二值化！）
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", denoised, buffer);
        return buffer.toArray();
    }

    /**
     * 【备选】如果图像本身质量很高（扫描件），直接灰度化即可
     */
    public static byte[] simpleGrayscale(byte[] imageBytes) throws IOException {
        Mat src = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_COLOR);
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", gray, buffer);
        return buffer.toArray();
    }
}