package com.wonders.core.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.druid.util.StringUtils;
import com.wonders.core.bp.exception.FileBpException;
import com.wonders.core.bp.model.BaseFileEntity;

/**
 * 断点续传处理工具
 * 
 * @author silent
 */
public class JFileUtils extends FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	public static final int UPLOADBP_BLOCK_LENGTH = 10240;

	/**
	 * 文件存储目录
	 */
	public final static String FOLDER_PATH = "E:/HLTHSPV/FILES";

	/**
	 * 断点续传文件缓存文件
	 */
	public final static String TEMP_PATH = "E:/HLTHSPV/TEMP";

	/**
	 * 断点续传保存
	 * 
	 * @param fileEntity
	 */
	public static void saveBrokenPointFile(BaseFileEntity fileEntity) {
		Assert.notNull(fileEntity, "fileEntity must not be null");
		Assert.notNull(fileEntity.getContent(), "FileContent must not be null");
		Assert.notNull(fileEntity.getFileSign(), "FileSign must be implement and must not be null");
		byte[] content = fileEntity.getContent();
		String temppath = TEMP_PATH + "/" + fileEntity.getFileSign();
		try {
			logger.debug("saveBrokenPointFile writing file data[" + content.length + "] to tmpfile[" + temppath + "]!");
			File file = new File(temppath);
			if(fileEntity.getStart() == 0) {
				deleteQuietly(file);
			}
			writeByteArrayToFile(file, content, fileEntity.isBroken());
			logger.debug("saveBrokenPointFile write file data[" + content.length + "] to tmpfile[" + temppath + "] success!");
		} catch (IOException e) {
			logger.error("saveBrokenPointFile", e);
			throw new FileBpException("文件写出错误");
		}
		completeBrokenPointFile(fileEntity);
	}

	private static void completeBrokenPointFile(BaseFileEntity fileEntity) {
		Assert.notNull(fileEntity, "fileEntity must not be null");
		try {
			String temppath = TEMP_PATH + "/" + fileEntity.getFileSign();
			if ((!fileEntity.isBroken()) || (fileEntity.isLast())) {
				// 非断点续传
				// 最后一次传输成功后，需将缓存文件转移到路径下，并删除缓存文件。
				String realpath;
				if (StringUtils.isEmpty(fileEntity.getFilepath())) {
					realpath = FOLDER_PATH + "/" + fileEntity.getFileSign();
					fileEntity.setFilepath("/" + fileEntity.getFileSign());
				} else {
					realpath = FOLDER_PATH + fileEntity.getFilepath();
				}
				logger.debug("completeBrokenPointFile deleting file [" + realpath + "]!");
				deleteQuietly(new File(realpath));
				logger.debug("completeBrokenPointFile delete file [" + realpath + "] success!");
				logger.debug("completeBrokenPointFile moving file [" + temppath + "] to [" + realpath + "]!");
				moveFile(new File(temppath), new File(realpath));
				fileEntity.setFilesize(sizeOf(new File(realpath)));
				logger.debug("completeBrokenPointFile move file [" + temppath + "] to [" + realpath + "] success!");
			}
		} catch (IOException e) {
			logger.error("completeBrokenPointFile", e);
			throw new FileBpException("上传文件完成后，缓存文件转移出错");
		}
	}

	public static BaseFileEntity getBrokenPointFile(BaseFileEntity fileEntity) {
		Assert.notNull(fileEntity, "fileEntity must not be null");
		Assert.notNull(fileEntity.getFilepath(), "Filepath must not be null");
		Assert.notNull(fileEntity.getFileSign(), "FileSign must not be null");
		try {
			File rfile = new File(FOLDER_PATH + fileEntity.getFilepath());
			long filesize = sizeOf(rfile);
			fileEntity.setFilesize(filesize);
			if (fileEntity.isBroken()) {
				if (fileEntity.getStart() >= filesize) {
					throw new FileBpException("start position out of range");
				}
				RandomAccessFile raf = new RandomAccessFile(rfile, "r");
				raf.seek(fileEntity.getStart());
				int downsize = (int) (((fileEntity.getStart() + UPLOADBP_BLOCK_LENGTH) > filesize)
						? (filesize - fileEntity.getStart())
						: UPLOADBP_BLOCK_LENGTH);
				byte[] bytes = new byte[downsize];
				raf.read(bytes, 0, downsize);
				raf.close();
				fileEntity.setContent(bytes);
			} else {
				byte[] fullContent = readFileToByteArray(rfile);
				fileEntity.setContent(fullContent);
				logger.debug("getBrokenPointFile get all file data success!");
			}
		} catch (Exception e) {
			logger.error("getBrokenPointFile", e);
			throw new FileBpException("下载文件出错");
		}
		return fileEntity;
	}

	public static void main(String[] args) throws IOException {
		String aa = readFileToString(new File("C:\\Users\\silent\\Desktop\\X9M8G396.txt"), "UTF-8");
		System.out.println(aa);
	}
}
