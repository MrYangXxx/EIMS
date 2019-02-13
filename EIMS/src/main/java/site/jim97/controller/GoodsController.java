package site.jim97.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import site.jim97.entity.Goods;
import site.jim97.entity.GoodsType;
import site.jim97.service.GoodsTypeService;
import site.jim97.service.PurchaseListService;
import site.jim97.service.ReturnListService;
import site.jim97.service.SaleListService;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.HttpServletRequestUtil;

@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController<Goods> {

	@Autowired
	private GoodsTypeService goodsTypeService;
	@Autowired
	private PurchaseListService purchaseListService;
	@Autowired
	private ReturnListService returnListService;
	@Autowired
	private SaleListService saleListService;

	/**
	 * 要根据商品类别树的选择进行eq查询，重写此方法
	 */
	@Override
	@GetMapping("/list")
	public void list(Goods goods, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int size = HttpServletRequestUtil.getInt(request, "limit");
		int page = HttpServletRequestUtil.getInt(request, "page");
		int typePId = HttpServletRequestUtil.getInt(request, "typePId");
		IPage<Goods> list;
		// 如果点击的是父节点，要查找子节点包含的商品
		if (typePId != 0) {
			list = service.list(goods, new Page<>(page, size), "code", "name", "supplierName", "eq_typeId");
		} else if (goods.getTypeId() != 0) {
			String childId = goodsTypeService.getChildIds(goods.getTypeId());
			list = service.list(goods, new Page<>(page, size), "code", "name", "supplierName", "in_typeId_" + childId);
		} else {
			list = service.list(goods, new Page<>(page, size), "code", "name", "supplierName");
		}
		AjaxUtil.create().put("code", 0).put("list", list).write(response); // code是前台layui要使用来判断是否接收到数据的标记值
	}

	/**
	 * 商品列表页树显示
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/types")
	public void type(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GoodsType goodsType = new GoodsType();
		goodsType.setPId(0);
		List<GoodsType> fathers = goodsTypeService.list(goodsType, "eq");
		for (GoodsType father : fathers) {
			GoodsType goodsType2 = new GoodsType();
			goodsType2.setPId(father.getId());
			List<GoodsType> children = goodsTypeService.list(goodsType2, "eq");
			father.setChildren(children);
		}
		GoodsType gtRoot = new GoodsType();
		gtRoot.setId(0);
		gtRoot.setPId(0);
		gtRoot.setName("所有分类");
		gtRoot.setChildren(fathers);
		List<GoodsType> root = new ArrayList<>();
		root.add(gtRoot);
		AjaxUtil.create().put("code", 0).put("list", root).write(response);
	}

	/**
	 * 因为可以自行更改库存量，设置一个根据进货单进货量-退货单退货量的矫正库存量方法
	 * 
	 * @throws Exception
	 */
	@GetMapping("/refreshquantity/{id}")
	public void refreshQuantity(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		int purchaseNumberSum = purchaseListService.purchaseNumberSum(id);
		int returnNumberSum = returnListService.returnNumberSum(id);
		int saleNumberSum = saleListService.saleNumberSum(id);
		int quantity = purchaseNumberSum - returnNumberSum - saleNumberSum;
		AjaxUtil.create().put("inventoryQuantity", quantity).write(response);
	}

}
