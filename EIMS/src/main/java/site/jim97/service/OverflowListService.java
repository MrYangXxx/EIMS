package site.jim97.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.Goods;
import site.jim97.entity.OverflowList;
import site.jim97.mapper.OverflowListMapper;
import site.jim97.utils.StringUtil;

@Service
public class OverflowListService extends BaseService<OverflowList> {
	@Autowired
	private OverflowListMapper mapper;
	@Autowired
	private GoodsService goodsService;

	@Override
	@Transactional
	public boolean save(OverflowList t) {
		Goods goods = goodsService.findById(t.getGoodsId());
		Object id = StringUtil.getFieldValueByName("id", t);
		if (id != null) { // 更新时不update存货量，而是在商品编辑页设置一个更新库存按钮，简化逻辑
			mapper.updateById(t);
		} else {
			// 保存商品报损数据时要将商品存货量update
			goods.setInventoryQuantity(goods.getInventoryQuantity() + t.getOverflowNumber());
			goodsService.save(goods);
			mapper.insert(t);
		}
		return true;
	}

	public int overflowNumberSum(int goodsId) {
		return mapper.overflowNumberSum(goodsId);
	}
}
