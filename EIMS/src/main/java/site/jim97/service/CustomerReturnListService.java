package site.jim97.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.CustomerReturnList;
import site.jim97.entity.Goods;
import site.jim97.mapper.CustomerReturnListMapper;
import site.jim97.utils.StringUtil;

@Service
public class CustomerReturnListService extends BaseService<CustomerReturnList>{
	@Autowired
	private CustomerReturnListMapper mapper;
	@Autowired
	private GoodsService goodsService;

	@Override
	@Transactional
	public CustomerReturnList save(CustomerReturnList t) {
		Goods goods = goodsService.findById(t.getGoodsId());
		Object id = StringUtil.getFieldValueByName("id", t);
		if (id != null) { //更新时不update存货量，而是在商品编辑页设置一个更新库存按钮，简化逻辑
			mapper.updateById(t);
		} else {
			// 保存顾客退货数据时要将商品存货量update
			goods.setInventoryQuantity(goods.getInventoryQuantity() + t.getCustomerReturnNumber());
			goodsService.save(goods);
			mapper.insert(t);
		}
		return t;
	}

	public int purchaseNumberSum(int goodsId) {
		return mapper.customerReturnNumberSum(goodsId);
	}
}
