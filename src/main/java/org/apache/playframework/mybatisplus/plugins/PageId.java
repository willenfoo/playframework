package org.apache.playframework.mybatisplus.plugins;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;


/**
 * id分页需要满足的条件，  
 * 第一：表中最新的数据排在最前面（desc） 或者排在最后面（asc），无特殊排序需求
 * 第二：不能跳页，比如成第一页跳转到第三页
 * 
 * 实现思路
 * 第一：得到 from 的表名 及 as 的别名
 * 第二：得到排序是 desc 还是 asc， 人工是desc就用 id < 传入id值， 人工是asc 就用 id > 传入id值
 * 第三：得到sql中是否有where条件，把 id 加到where条件第一位，利用索引性能极高 
 * 
 * @author willenfoo
 * @param <T>
 */
public class PageId<T> extends Page<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1216657660502085024L;

	
	
	private Long indexId; //分页的id值 ,  current , 如果小于0，是上一页，如果大于0，是下一页
	
	public Long getIndexId() {
		return indexId;
	}

	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}
	
	public PageId() {
		super(0, 10);
	}

	public PageId(int offset, int pageSize, Long indexId) {
		super(offset, pageSize);
		this.indexId = indexId;
	}

	@Override
	public void setSize(int size) {
		if (size > 50) {
			size = 50;
		}
		super.setSize(size);
	}

	@Override
	public void setRecords(List<T> records) {
		List<T> list = new ArrayList<>();
		if (!isAsc() && getCurrent() > 0) {
			list = records;
		} else if (!isAsc() && getCurrent() < 0) {
			int size = records.size()-1;
			for (int i = size; i >= 0; i--) {
				list.add(records.get(i));
			}
		} else if (isAsc() && getCurrent() > 0) {
			list = records;
		} else if (isAsc() && getCurrent() < 0) {
			int size = records.size()-1;
			for (int i = size; i >= 0; i--) {
				list.add(records.get(i));
			}
		}
		super.setRecords(list);
	}
	
	public List<T> initRecords(List<T> records) {
		setRecords(records);
		return getRecords();
	}
	
}
