/*
 * Copyright (c) 2016, 2017, LEEPENG and/or its affiliates. All rights reserved.
 * LEEPENG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.leepeng.wwfty.schema.facebook;

import cn.com.leepeng.wwfty.annotation.Data;
import cn.com.leepeng.wwfty.enums.OriginalProjectionType;
import cn.com.leepeng.wwfty.enums.UnpublishedContentType;

/**
 * 
 * 当发布到这个边缘时，将创建一个视频。
 * @author LEEPENG
 * @since 1.0
 * @version 1.0
 * @date 27th Aug,2017
 * /{user-id}/videos
 * /{event-id}/videos
 * /{page-id}/videos
 * /{group-id}/videos
 */
@Data
public class FacebookIndividualVideo implements Cloneable{
	private String publishType;
	/**
	 * 会话ID
	 */
	private String composerSessionID;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 视频文件的大小（以字节为单位）。在分块上传时使用 。
	 */
	private Integer fileSize;
	/**
	 * 文件网址
	 */
	private String fileUrl;
	/**
	 * 单个鱼眼视频是否被裁剪
	 */
	private boolean fisheyeVideoCropped;
	/**
	 * 单个鱼眼视频的前z旋转度数
	 */
	private float frontZRotation;
	/**
	 * 是否为明确的分享
	 */
	private boolean IsExplicitShare;
	/**
	 * 默认值： false  手动隐私
	 */
	private boolean manualPrivacy = false;
	/**
	 * 视频库的名称
	 */
	private String name;
	/**
	 * og动作类型id
	 */
	private Long ogActionTypeID;
	/**
	 * og图标ID
	 */
	private Long ogIconId;
	/**
	 * og对象id
	 */
	private String ogObjectID;
	/**
	 * og短语
	 */
	private String ogPhrase;
	/**
	 * og建议机制
	 */
	private String ogSuggestionMechanism;
	/**
	 * 原始摄像头的视野
	 */
	private Long originalFov;
	/**
	 * 正在上传的视频的原始投影类型,2.3 版本或以后
	 */
	private OriginalProjectionType originalProjectionType;
	/**
	 * 参考贴纸ID
	 */
	private Long referencedStickerID;
	/**
	 * 该视频编码为表单数据。有关视频格式的详细信息，请参阅 视频格式文档。
	 */
	private String source;
	
	/**
	 * 正在发送的块的开始位置（包括字节）。在 分块上传时使用
	 */
	private Long startOffset;
	/**
	 * 自原帖以来的时间
	 */
	private Long timeSinceOriginalPost;
	/**
	 * 正在上传的视频的名称。
	 */
	private String title;
	/**
	 * 未发布的内容类型
	 */
	private UnpublishedContentType unpublishedContentType;
	/**
	 * 此分块上传的会话ID。在分块上传时使用。
	 */
	private Long uploadSessionId;
	/**
	 * 
	 */
	private String uploadphase;
	/**
	 * 视频的大块，之间start_offset 和end_offset。在分块上传时使用 。
	 */
	private String videoFileChunk;
	
	public String getComposerSessionID() {
		return composerSessionID;
	}
	public void setComposerSessionID(String composerSessionID) {
		this.composerSessionID = composerSessionID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public boolean isFisheyeVideoCropped() {
		return fisheyeVideoCropped;
	}
	public void setFisheyeVideoCropped(boolean fisheyeVideoCropped) {
		this.fisheyeVideoCropped = fisheyeVideoCropped;
	}
	public float getFrontZRotation() {
		return frontZRotation;
	}
	public void setFrontZRotation(float frontZRotation) {
		this.frontZRotation = frontZRotation;
	}
	public boolean isIsExplicitShare() {
		return IsExplicitShare;
	}
	public void setIsExplicitShare(boolean isExplicitShare) {
		IsExplicitShare = isExplicitShare;
	}
	public boolean isManualPrivacy() {
		return manualPrivacy;
	}
	public void setManualPrivacy(boolean manualPrivacy) {
		this.manualPrivacy = manualPrivacy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getOgActionTypeID() {
		return ogActionTypeID;
	}
	public void setOgActionTypeID(Long ogActionTypeID) {
		this.ogActionTypeID = ogActionTypeID;
	}
	public Long getOgIconId() {
		return ogIconId;
	}
	public void setOgIconId(Long ogIconId) {
		this.ogIconId = ogIconId;
	}
	public String getOgObjectID() {
		return ogObjectID;
	}
	public void setOgObjectID(String ogObjectID) {
		this.ogObjectID = ogObjectID;
	}
	public String getOgPhrase() {
		return ogPhrase;
	}
	public void setOgPhrase(String ogPhrase) {
		this.ogPhrase = ogPhrase;
	}
	public String getOgSuggestionMechanism() {
		return ogSuggestionMechanism;
	}
	public void setOgSuggestionMechanism(String ogSuggestionMechanism) {
		this.ogSuggestionMechanism = ogSuggestionMechanism;
	}
	public Long getOriginalFov() {
		return originalFov;
	}
	public void setOriginalFov(Long originalFov) {
		this.originalFov = originalFov;
	}
	public OriginalProjectionType getOriginalProjectionType() {
		return originalProjectionType;
	}
	public void setOriginalProjectionType(OriginalProjectionType originalProjectionType) {
		this.originalProjectionType = originalProjectionType;
	}
	public Long getReferencedStickerID() {
		return referencedStickerID;
	}
	public void setReferencedStickerID(Long referencedStickerID) {
		this.referencedStickerID = referencedStickerID;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Long getStartOffset() {
		return startOffset;
	}
	public void setStartOffset(Long startOffset) {
		this.startOffset = startOffset;
	}
	public Long getTimeSinceOriginalPost() {
		return timeSinceOriginalPost;
	}
	public void setTimeSinceOriginalPost(Long timeSinceOriginalPost) {
		this.timeSinceOriginalPost = timeSinceOriginalPost;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public UnpublishedContentType getUnpublishedContentType() {
		return unpublishedContentType;
	}
	public void setUnpublishedContentType(UnpublishedContentType unpublishedContentType) {
		this.unpublishedContentType = unpublishedContentType;
	}
	public Long getUploadSessionId() {
		return uploadSessionId;
	}
	public void setUploadSessionId(Long uploadSessionId) {
		this.uploadSessionId = uploadSessionId;
	}
	public String getVideoFileChunk() {
		return videoFileChunk;
	}
	public void setVideoFileChunk(String videoFileChunk) {
		this.videoFileChunk = videoFileChunk;
	}
	public String getUploadphase() {
		return uploadphase;
	}
	public void setUploadphase(String uploadphase) {
		this.uploadphase = uploadphase;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
