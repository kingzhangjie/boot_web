package com.zj.boot_web.common.utils;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCode {

	//二维码存放目录
	public static String fileUploadPath = Const.FILE_UPLOAD_DIR; // 文件上传目录
	
	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "JPG";
	// 二维码尺寸
	private static final int QRCODE_SIZE = 300;
	// LOGO宽度
	private static final int WIDTH = 60;
	// LOGO高度
	private static final int HEIGHT = 60;
	
	public static void main(String[] args) throws Exception {
		//二维码中存放的链接
		String qrcode_url_parameter = "http://sh-sb.cn/s/YjqY3u";
		String exclusive_qrcode_url = "";
		
		String filePath = fileUploadPath + "WeChatQRcode\\";//二维码中镶嵌的logo地址
		String logoPath = filePath + "getheadimg2.jpg";
		//String filePath2 = fileUploadPath + "WeChatQRcode";
		//String filePath = "/mnt/getheadimg.jpg";//二维码中镶嵌的logo地址
		try {
			//生成二维码
			exclusive_qrcode_url = QrCode.encode(qrcode_url_parameter, logoPath, null, filePath, "测试二维码2", true);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("qrcode_url_parameter:" + exclusive_qrcode_url);
	}
	
	

	/**
	 * 生成二维码(内嵌LOGO)
	 * @param content 内容
	 * @param imgPath LOGO本地地址
	 * @param imgUrl LOGO网络地址
	 * @param destPath 存放目录
	 * @param fileName 文件名称
	 * @param needCompress 是否压缩LOGO
	 * @return 返回文件名
	 * @throws Exception
	 */
	public static String encode(String content, String imgPath, String imgUrl, 
			String destPath, String fileName, boolean needCompress)
			throws Exception {
		BufferedImage image = QrCode.createImage(content, imgPath, imgUrl, needCompress);
		FileUtil.mkdirs(destPath);
		fileName = fileName + ".jpg";
		ImageIO.write(image, FORMAT_NAME, new File(destPath + fileName));
		// System.out.println("destPath:"+destPath+file);
		return fileName;
	}

	/**
	 * 生成二维码(内嵌LOGO)并输出到页面
	 * @param content 内容
	 * @param imgPath LOGO地址
	 * @param output 输出流
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath, String imgUrl, 
			OutputStream output, boolean needCompress) throws Exception {
		BufferedImage image = QrCode.createImage(content, imgPath, imgUrl, needCompress);
		ImageIO.write(image, FORMAT_NAME, output);
	}
	

	private static BufferedImage createImage(String content, String imgPath, String imgUrl,
			boolean needCompress) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H ); // 矫错级别  
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1); // 二维码的边距
		
		//创建比特矩阵(位矩阵)的QR码编码的字符串  
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		if (ComUtil.isEmpty(imgPath) && ComUtil.isEmpty(imgUrl)) {
			return image;
		}
		// 插入图片
		QrCode.insertImage(image, imgPath, imgUrl, needCompress);
		return image;
	}

	/**
	 * 插入LOGO
	 * @param source 二维码图片
	 * @param imgPath LOGO图片地址
	 * @param needCompress 是否压缩
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String imgPath, String imgUrl, boolean needCompress) throws Exception {
		Image src = null;
		if (ComUtil.isEmpty(imgUrl)) {
			File file = new File(imgPath);
			if (!file.exists()) {
				System.err.println("" + imgPath + "   该文件不存在！");
				return;
			}
			src = ImageIO.read(new File(imgPath));
		} else {
			URL url = new URL(imgUrl);  
			InputStream is = url.openConnection().getInputStream();
			src = ImageIO.read(is);
		}
		
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > WIDTH) {
				width = WIDTH;
			}
			if (height > HEIGHT) {
				height = HEIGHT;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_SIZE - width) / 2;
		int y = (QRCODE_SIZE - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}


	/**
	 * 解析二维码
	 * 
	 * @param file 二维码图片
	 * @return
	 * @throws Exception
	 */
	public static String decode(File file) throws Exception {
		BufferedImage image;
		image = ImageIO.read(file);
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
				image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}

	/**
	 * 解析二维码
	 * @param path 二维码图片地址
	 * @return
	 * @throws Exception
	 */
	public static String decode(String path) throws Exception {
		return QrCode.decode(new File(path));
	}

}
