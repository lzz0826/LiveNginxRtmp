package org.example.service;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.swing.*;

/**
 * 直播拉流实现 TODO
 */
public class PullStream {
    public void getPullStream(String inputPath) {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputPath);
        grabber.setOption("rtsp_transport", "tcp");
        grabber.setImageWidth(960);
        grabber.setImageHeight(540);

        CanvasFrame canvasFrame = new CanvasFrame("Live Stream");
        canvasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvasFrame.setAlwaysOnTop(true);

        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();

        try {
            grabber.start();

            while (true) {
                Frame frame = grabber.grabImage();
                if (frame == null) {
                    continue; // 如果没有抓取到帧，则继续循环
                }
                Mat mat = converter.convertToMat(frame);
                if (mat.empty()) {
                    continue; // 如果帧为空，则继续循环
                }
                canvasFrame.showImage(frame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 停止拉流并释放资源
            try {
                if (grabber != null) {
                    grabber.stop();
                    grabber.release();
                }
                if (canvasFrame != null) {
                    canvasFrame.dispose();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}