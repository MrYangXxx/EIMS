package site.jim97.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import site.jim97.entity.Goods;

@Service
public class GoodsService extends BaseService<Goods> {

	/**
	 * 库存报警，查询库存量小于预设最小库存量的商品
	 * @param t
	 * @param page
	 * @param filter
	 * @return
	 */
	public IPage<Goods> inventoryQuantityWarn(Goods t,Page<Goods> page,String...filter){
		QueryWrapper<Goods> wrapper = bulidWrapper(t, "like", filter);
		wrapper.like("code", 0);
		wrapper.last(" and min_num > inventory_quantity");
		return this.list(t, page, wrapper);
	}

}
