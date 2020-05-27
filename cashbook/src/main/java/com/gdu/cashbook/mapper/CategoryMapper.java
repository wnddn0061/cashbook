package com.gdu.cashbook.mapper;

import com.gdu.cashbook.vo.Category;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryMapper {
	public int addCategory(Category categoryName);
}
