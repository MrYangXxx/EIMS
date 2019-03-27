package site.jim97.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.Goods;
import site.jim97.entity.GoodsType;
import site.jim97.mapper.GoodsTypeMapper;

@Service
public class GoodsTypeService extends BaseService<GoodsType> {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsTypeMapper mapper;
	@Resource
	private RedisTemplate<String, List<GoodsType>> redisTemplate;

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
		if(redisTemplate.hasKey("goodsTypeTree")){ //删除掉redis缓存
			redisTemplate.delete("goodsTypeTree");
		}
		return "success";
	}
	
	@Override
	public boolean save(GoodsType t) {
		if(redisTemplate.hasKey("goodsTypeTree")){ //删除掉redis缓存
			redisTemplate.delete("goodsTypeTree");
		}
		return super.save(t);
	}



	public String getChildIds(int id){
		String childId="";
		String[] childIds = mapper.getChildIds(id);
		for (String child : childIds) {
			childId+=child+",";
		}
		return childId;
	}
	
	/**
	 * 采用redis缓存商品类型树
	 * 如果没有缓存，则查询并进行树层级的处理
	 * @return
	 */
	public List<GoodsType> typeTree(){
		List<GoodsType> root = new ArrayList<>();
		ValueOperations<String, List<GoodsType>> ops = redisTemplate.opsForValue();
		if(redisTemplate.hasKey("goodsTypeTree")){
			root = ops.get("goodsTypeTree");
		}else{
			GoodsType goodsType = new GoodsType();
			goodsType.setPId(0);
			List<GoodsType> fathers = this.list(goodsType, "eq"); //无父节点的商品类型
			for (GoodsType father : fathers) {
				GoodsType goodsType2 = new GoodsType();
				goodsType2.setPId(father.getId());    			//根据父节点id查询所属子节点
				List<GoodsType> children = this.list(goodsType2, "eq");
				father.setChildren(children);
			}
			GoodsType gtRoot = new GoodsType();   //搞一个根节点
			gtRoot.setId(0);
			gtRoot.setPId(0);
			gtRoot.setName("所有分类");
			gtRoot.setChildren(fathers);
			root.add(gtRoot);
			ops.set("goodsTypeTree", root);
		}
		return root;
	}

}
