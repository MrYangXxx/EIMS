package site.jim97.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.Goods;
import site.jim97.entity.ReturnList;
import site.jim97.mapper.ReturnListMapper;
import site.jim97.utils.StringUtil;

@Service
public class ReturnListService extends BaseService<ReturnList> {
	@Autowired
	private ReturnListMapper mapper;
	@Autowired
	private GoodsService goodsService;

	@Override
	@Transactional
	public ReturnList save(ReturnList t) {
		Object id = StringUtil.getFieldValueByName("id", t);
		if (id != null) { //更新时不update存货量，而是在商品编辑页设置一个更新库存按钮，简化逻辑
			mapper.updateById(t);
		} else {
			// 保存退货数据时要将商品存货量update
			Goods goods = goodsService.findById(t.getGoodsId());
			goods.setInventoryQuantity(goods.getInventoryQuantity() - t.getReturnNumber());
			goodsService.save(goods);
			mapper.insert(t);
		}
		return t;
	}
	
	public int returnNumberSum(int goodsId){
		return mapper.returnNumberSum(goodsId);
	}
	
	public List<Map<String, Integer>> countReturnNumber(int goodsId){
		return mapper.countReturnNumber(goodsId);
	}
	public List<Map<String, Integer>> countReturnTime(int goodsId){
		return mapper.countReturnTime(goodsId);
	}
}
