package site.jim97.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.Goods;
import site.jim97.entity.GoodsType;
import site.jim97.mapper.GoodsTypeMapper;

@Service
public class GoodsTypeService extends BaseService<GoodsType> {
	@Autowired
	GoodsService goodsService;
	@Autowired
	GoodsTypeMapper mapper;

	@Override
	@Transactional
	public String delete(String ids) {
		int id = Integer.valueOf(ids); //商品类别删除固定只传一个id进来

		// 如果该类别是另一类别的父节点，则判断子节点类别是否有商品，有则不能删除，无则级联删除
		GoodsType goodsType = new GoodsType();
		goodsType.setPId(id);
		List<GoodsType> goodsTypes = this.list(goodsType);
		for (GoodsType goodsType2 : goodsTypes) {
			// 如果该类别下有商品不能删除
			Goods childGoods = new Goods();
			childGoods.setTypeId(goodsType2.getId());
			List<Goods> childGoodsList = goodsService.list(childGoods, "typeId");
			if (childGoodsList.size() > 0) {
				return "该类别下含有商品，请清空该类别下的商品后再删除";
			}
		}
		// 如果该类别下有商品不能删除
		Goods goods = new Goods();
		goods.setTypeId(Integer.valueOf(id));
		List<Goods> goodsList = goodsService.list(goods, "typeId");
		if (goodsList.size() > 0) {
			return "该类别下含有商品，请清空该类别下的商品后再删除";
		}
		//经过上面的排查还没有返回的话，说明该节点及其子节点无商品，级联删除
		delete(id);
		delete(goodsType,"pId");
		return "success";
	}
	
	public String getChildIds(int id){
		String childId="";
		String[] childIds = mapper.getChildIds(id);
		for (String child : childIds) {
			childId+=child+",";
		}
		return childId;
	}

}
