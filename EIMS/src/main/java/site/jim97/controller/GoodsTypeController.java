package site.jim97.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.GoodsType;
import site.jim97.utils.AjaxUtil;

@RestController
@RequestMapping("/goodstype")
public class GoodsTypeController extends BaseController<GoodsType>{

	/**
	 * 商品类别层级关系，商品类别有大类和小类之分，小类依附于大类
	 * 用于类别编辑页面选择父类别下拉列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/root")
	public void root(HttpServletRequest request, HttpServletResponse response) throws Exception{
		GoodsType goodsType=new GoodsType();
		goodsType.setPId(0);
		List<GoodsType> list = service.list(goodsType, "eq");
		AjaxUtil.create().put("list", list).write(response); //code是前台layui要使用来判断是否接收到数据的标记值
	}
}
