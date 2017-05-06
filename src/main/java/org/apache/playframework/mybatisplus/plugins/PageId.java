package org.apache.playframework.mybatisplus.plugins;


import com.baomidou.mybatisplus.plugins.Page;

public class PageId<T> extends Page<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1216657660502085024L;

	private Long indexId; //分页的id值

	private Integer indexOffset; // 如果小于0，是下一页，如果大于0，是上一页
	
	public Long getIndexId() {
		return indexId;
	}

	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}
	
	public PageId(int current, int size, Long indexId, Integer indexOffset) {
        super(current, size);
        this.indexId = indexId;
        this.indexOffset = indexOffset;
    }

	public Integer getIndexOffset() {
		return indexOffset;
	}

	public void setIndexOffset(Integer indexOffset) {
		this.indexOffset = indexOffset;
	}
	
}
