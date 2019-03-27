package site.jim97.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.Goods;
import site.jim97.entity.SaleList;
import site.jim97.mapper.SaleListMapper;
import site.jim97.utils.StringUtil;

@Service
public class SaleListService extends BaseService<SaleList> {
	@Autowired
	private SaleListMapper mapper;
	@Autowired
	private GoodsService goodsService;

	@Override
	@Transactional
	public boolean save(SaleList t) {
		Object id = StringUtil.getFieldValueByName("id", t);
		if (id != null) { // 更新时不update存货量，而是在商品编辑页设置一个更新库存按钮，简化逻辑
			mapper.updateById(t);
		} else {
			// 保存销售数据时要将商品存货量update
			Goods goods = goodsService.findById(t.getGoodsId());
			goods.setInventoryQuantity(goods.getInventoryQuantity() - t.getSaleNumber());
			goodsService.save(goods);
			mapper.insert(t);
		}
		return true;
	}

	public int saleNumberSum(int goodsId) {
		return mapper.saleNumberSum(goodsId);
	}

	public List<Map<String, Integer>> countSalePrice(Integer goodsId) {
		return mapper.countSalePrice(goodsId);
	}

	public List<Map<String, Integer>> countSaleNumber(int goodsId) {
		return mapper.countSaleNumber(goodsId);
	}

	public List<Map<String, Integer>> countSaleTime(int goodsId) {
		return mapper.countSaleTime(goodsId);
	}

	public List<Map<String, Integer>> countDayRevenue(Integer goodsId) {
		return mapper.countDayRevenue(goodsId);
	}

	public List<Map<String, Integer>> countMonthRevenue(Integer goodsId) {
		return mapper.countMonthRevenue(goodsId);
	}

}
