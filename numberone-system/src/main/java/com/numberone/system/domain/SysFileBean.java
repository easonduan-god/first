/**
 * 
 */
package com.numberone.system.domain;

import com.numberone.common.base.BaseEntity;

/**
 * @author eason
 * @date 2020-02-20
 */
public class SysFileBean extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 文件id
	 */
	private String fileId;
	/**
	 * 文件原始名称
	 */
	private String originalFileName;
	/**
	 * 文件路径包括文件名 文件名是编码后的
	 */
	private String filePath;
	/**
	 * 文件大小K
	 */
	private String fileSize;
	private String downloadParam;
	
	
	public SysFileBean(String fileId) {
		this.fileId = fileId;
	}
	public SysFileBean() {
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getDownloadParam() {
		downloadParam = "?fileName="+this.filePath+"&originalFileName="+this.originalFileName;
		return downloadParam;
	}


	
}
