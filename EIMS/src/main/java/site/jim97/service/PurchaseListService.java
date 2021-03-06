package site.jim97.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.Goods;
import site.jim97.entity.PurchaseList;
import site.jim97.mapper.PurchaseListMapper;
import site.jim97.utils.StringUtil;

@Service
public class PurchaseListService extends BaseService<PurchaseList> {

	@Autowired
	private PurchaseListMapper mapper;
	@Autowired
	private GoodsService goodsService;

	@Override
	@Transactional
	public boolean save(PurchaseList t) {
		Goods goods = goodsService.findById(t.getGoodsId());
		//商品购买价钱更新
		goods.setLastPurchasingPrice(goods.getPurchasingPrice());
		goods.setPurchasingPrice(t.getUnitPrice());
		Object id = StringUtil.getFieldValueByName("id", t);
		if (id != null) { //更新时不update存货量，而是在商品编辑页设置一个更新库存按钮，简化逻辑
			mapper.updateById(t);
			goodsService.save(goods);
		} else {
			// 保存进货数据时要将商品存货量update
			goods.setInventoryQuantity(goods.getInventoryQuantity() + t.getPurchaseNumber());
			goodsService.save(goods);
			mapper.insert(t);
		}
		return true;
	}

	public int purchaseNumberSum(int goodsId) {
		return mapper.purchaseNumberSum(goodsId);
	}

	public List<Map<String, Integer>> countPurchaseNumber(int goodsId){
		return mapper.countPurchaseNumber(goodsId);
	}
	public List<Map<String, Integer>> countPurchaseTime(int goodsId){
		return mapper.countPurchaseTime(goodsId);
	}
	public List<Map<String, Integer>> countPurchasePrice(int goodsId){
		return mapper.countPurchasePrice(goodsId);
	}
}
