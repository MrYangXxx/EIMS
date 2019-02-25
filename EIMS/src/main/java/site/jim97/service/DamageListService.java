package site.jim97.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.DamageList;
import site.jim97.entity.Goods;
import site.jim97.mapper.DamageListMapper;
import site.jim97.utils.StringUtil;

@Service
public class DamageListService extends BaseService<DamageList> {
	@Autowired
	private DamageListMapper mapper;
	@Autowired
	private GoodsService goodsService;

	@Override
	@Transactional
	public DamageList save(DamageList t) {
		Goods goods = goodsService.findById(t.getGoodsId());
		Object id = StringUtil.getFieldValueByName("id", t);
		if (id != null) { 
			// 商品报损更新时判断是否审核通过，通过则要将商品存货量update
			if(t.getStatus()==2){
				goods.setInventoryQuantity(goods.getInventoryQuantity() - t.getDamageNumber());
				goodsService.save(goods);
			}
			mapper.updateById(t);
		} else {
			mapper.insert(t);
		}
		return t;
	}

	public int purchaseNumberSum(int goodsId) {
		return mapper.damageNumberSum(goodsId);
	}
}
