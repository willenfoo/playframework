package org.apache.playframework.domain;

import java.io.Serializable;

public class PageId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1216657660502085024L;

	private Long indexId; //分页的id值

	private Integer indexOffset = 1; // 如果小于0，是上一页，如果大于0，是下一页
	
	private Integer limit = 10; //每页显示条数
	
	public Long getIndexId() {
		return indexId;
	}

	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}
	
	public PageId() {
		
	}
	
	public PageId(Long indexId, Integer indexOffset, Integer limit) {
        this.indexId = indexId;
        this.indexOffset = indexOffset;
        this.limit = limit;
    }

	public Integer getIndexOffset() {
		return indexOffset;
	}

	public void setIndexOffset(Integer indexOffset) {
		this.indexOffset = indexOffset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		if (limit > 50) {
			limit = 50;
		}
		this.limit = limit;
	}
	
}
