package com.hframe.basic.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * 文本操作工具类
 * @author Horgn黄小锤
 * @version v1.0
 * @date 2018-10-23 16:53:46
 */
public class FileUtils {
	
	/**文件字符编码*/
	public static final class FileCharset {
		public static final String UTF8 = "UTF-8";
		public static final String GBK = "GBK";
	}

    /**
     * 以GBK编码读取本地文件
     * @author Horgn黄小锤
     * @param path 文件路径，如：D:/test.txt
     * @return
     * @date 2018-10-29 11:11:11
     */
    public static String readGBK(String path){
        return read(path, FileCharset.GBK);
    }
    
    /**
     * 以UTF-8编码读取本地文件
     * @author Horgn黄小锤
     * @param path 文件路径，如：D:/test.txt
     * @return
     * @date 2018-10-29 11:11:11
     */
    public static String readUTF8(String path){
        return read(path, FileCharset.UTF8);
    }
    
    /**
     * 读取文件内容
     * @author Horgn黄小锤
     * @date 2019年1月31日 上午9:51:26
     * @param file 文件：classpath:templates/code.xxx
     * @param charset 字符编码，如：UTF-8、GBK
     * @return
     */
    public static String readFile(File file, String charset){
    	
    	if(null == file){
    		return null;
    	}
    	
    	if(StringUtils.isEmpty(charset)){
    		charset = FileCharset.UTF8;
    	}
    	
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis,charset);
			BufferedReader br = new BufferedReader(isr);
			
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine()) != null){
				sb.append(line+"\n");
			}
			
			br.close();
			isr.close();
			fis.close();
			return sb.toString();
			
		} catch (IOException e) {
//			e.printStackTrace();
			throw new RuntimeException("读取文件失败：" + e.getMessage());
		}
    }
	
	/**
	 * 读取指定字符编辑的本地文件
	 * @author Horgn黄小锤
	 * @param path 目标文件路径
	 * @param charset 字符编辑，参考FileUtils.FileCharset 类
	 * @return
	 * @date 2018-10-23 16:46:34
	 */
	public static String read(String path,String charset){
		
		File f = new File(path);
		try {
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis,charset);
			BufferedReader br = new BufferedReader(isr);
			
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine()) != null){
				sb.append(line+"\n");
			}
			
			br.close();
			isr.close();
			fis.close();
			return sb.toString();
			
		} catch (IOException e) {
//			e.printStackTrace();
			throw new RuntimeException("读取文件失败：" + e.getMessage());
		}
	}

    /**
     * 以GBK编辑写出到本地文件
     * @author Horgn黄小锤
     * @param str 文本内容
     * @param path 写出路径，如：D:/test.txt
     * @date 2018-10-29 11:12:57
     */
    public static void writerGBK(String str,String path){
        writer(str, path, FileCharset.GBK);
    }
    
    /**
     * 以UTF-8编辑写出到本地文件
     * @author Horgn黄小锤
     * @param str 文本内容
     * @param path 写出路径，如：D:/test.txt
     * @date 2018-10-29 11:12:57
     */
    public static void writerUTF8(String str,String path){
        writer(str, path, FileCharset.UTF8);
    }	

	/**
	 * 按指定字符编码将文本写入到本地文件
	 * @author Horgn黄小锤
	 * @param content 文本内容
	 * @param filePath 输出路径及文件名，如：D:/com/a.txt
	 * @param charset 字符编辑，参考FileUtils.FileCharset 类
	 * @date 2018-10-23 16:47:00
	 */
	public static void writer(String content, String filePath, String charset){
		
		File newFile = new File(filePath);
		try {
			FileOutputStream fos = new FileOutputStream(newFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.write(content);
			
			bw.close();
			osw.close();
			fos.close();
			
		} catch (IOException e) {
			throw new RuntimeException("写入文件失败：" + e.getMessage());
		}
	}
	
	
	
	/**
	 * 文件上传方法<br>
	 * 注：本方法使用的上传方式是 MultipartRequest 
	 * @author Horgn黄小锤
	 * @date 2019年2月23日 下午1:51:34
	 * @param request 请求
	 * @param paramName 图片在请求中的参数名，如：imgFile
	 * @param savePath 保存路径，如：D:/upload/images
	 * @param fileName 保存文件名，如：xxx_001
	 * @param fileType 保存文件类型，如：.jpg
	 */
	public static void uploadFile(HttpServletRequest request, String paramName, String savePath, String fileName, String fileType){
		
		if(StringUtils.isEmpty(paramName, savePath, fileName, fileType)){
			throw new RuntimeException("上传文件参数不能为空");
		}
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			// 图片，此处使用的是MultipartRequest请求，所以需要转换类型
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile(paramName);
			inputStream = file.getInputStream();
			
			String pathStr = transitionFilePath(savePath);//路径转换
			
			File path = new File(pathStr);//如果目录不存在 ，则创建目录
			if(!path.exists() && !path.isDirectory()){
				path.mkdirs();
			}
			
			if( ! fileType.substring(0, 1).equals(".")){//判断文件路径前是否有“.”号
				fileType = "." + fileType;
			}
			
			File saveFile = new File(pathStr + fileName + fileType);//输出文件及输出流
			outputStream = new FileOutputStream(saveFile);
			
			int byteread = -1;
			byte b[] = new byte[2048];
			while ((byteread = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, byteread);
			}
			
		} catch (IOException e) {
			throw new RuntimeException("图片上传异常");
		} finally {
			try {
				if(null != inputStream){
					inputStream.close();
				}
				if(null != outputStream){
					outputStream.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("图片上传关闭异常");
			}
		}
		
	}
	
	
	/**
	 * 转换文件路径，根据不同系统使用不同斜杠（正斜杠或反斜杠）<br>
	 * 注意：路径请使用单词字符：0-9a-zA-Z-_: <br>
	 * 例：（Windows系统下）<br>
	 * 原路径：D:/aa/bb<br>
	 * 新路径：D:\aa\bb\ (注意：此处会在末尾添加反斜杠)
	 * @author Horgn黄小锤
	 * @date 2019年2月25日 上午9:32:14
	 * @param path
	 * @return
	 */
	public static String transitionFilePath(String path){
		
		if(StringUtils.isEmpty(path)){
			throw new RuntimeException("参数不能为空");
		}
		
		StringBuilder newPath = new StringBuilder();
		if(path.substring(0,1).equals("/")){//如果是linux系统，以/开头，表示根目录
			newPath.append(File.separator);
		}
		
		String[] split = path.split("[^0-9a-zA-Z-_:]");//根据不同的系统转换不同的斜杠反斜杠
		for(String str : split){
			if(StringUtils.isNotEmpty(str)){
				newPath.append(str + File.separator);
			}
		}
		
		return newPath.toString();
	}


}
