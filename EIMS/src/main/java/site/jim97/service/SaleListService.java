package site.jim97.service;

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
	public SaleList save(SaleList t) {
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
		return t;
	}

	public int saleNumberSum(int goodsId) {
		return mapper.saleNumberSum(goodsId);
	}
}
