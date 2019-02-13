package site.jim97.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import site.jim97.utils.StringUtil;

/**
 * 基于mybatis-plus的通用service抽象类，包含了基本的增删查改功能，简化开发
 * 
 * @author Jim
 *
 * @param <T>
 */
public abstract class BaseService<T> {

	@Autowired
	BaseMapper<T> mapper;

	/**
	 * 封装wrapper设置条件值
	 * 
	 * @param t
	 * @param filter
	 * @return
	 */
	private QueryWrapper<T> bulidWrapper(T t, String style, String... filter) {
		QueryWrapper<T> wrap = new QueryWrapper<>();
		for (String filedName : filter) {
			if (filedName.equals("serialVersionUID")) {
				continue;
			}
			if (filedName.contains("orderBy_")) {
				this.orderBy(wrap, filedName);
				continue;
			}
			if (filedName.contains("eq_")) {
				String[] eqFiled = filedName.split("_");
				Object object = StringUtil.getFieldValueByName(eqFiled[1], t);
				wrap.eq(!StringUtil.isEmpty(object), StringUtil.HumpToUnderline(eqFiled[1]), object);
				continue;
			}
			if(filedName.contains("in_")){
				String[] inFiled = filedName.split("_");
				Object[] value = inFiled[2].split("\\,");
				wrap.in(StringUtil.HumpToUnderline(inFiled[1]), value);
				continue;
			}
			Object object = StringUtil.getFieldValueByName(filedName, t);
			if (style.equals("eq")) {
				wrap.eq(!StringUtil.isEmpty(object), StringUtil.HumpToUnderline(filedName), object);
			} else {
				wrap.like(!StringUtil.isEmpty(object), StringUtil.HumpToUnderline(filedName), object);
			}
		}
		return wrap;
	}

	/**
	 * 封装orderBy操作
	 * 
	 * @param wrap
	 * @param filedName
	 */
	private void orderBy(QueryWrapper<T> wrap, String filedName) {
		String[] orderByField = filedName.split("_");
		String style = orderByField[0];
		String[] field = Arrays.copyOfRange(orderByField, 1, orderByField.length);
		if (style.equals("orderBy") || style.equals("orderByAsc")) {
			wrap.orderByAsc(StringUtil.isNotEmpty(field), field);
		} else {
			wrap.orderByDesc(StringUtil.isNotEmpty(field), field);
		}
	}

	/**
	 * 根据对象中的条件寻找一条数据，如果有多条将抛出异常 ,一般请根据id查找数据，除非确定查询结果唯一
	 * 
	 * @param t
	 * @return
	 */
	public T findOne(T t) {
		// 直接在创建wrapper对象时把实体对象传进去，查询条件是等于，可能会有错误，但方法实际用处很少，等用到再排错
		Wrapper<T> wrap = new QueryWrapper<>(t);
		return mapper.selectOne(wrap);
	}

	/**
	 * 根据filter条件eq查询，filter为属性名,一般请根据id查找数据，除非确定查询结果唯一
	 * filter也可为oderBy_列名,可往后一直叠加_列名,
	 * 
	 * @param t
	 * @return
	 */
	public T findOne(T t, String... filter) {
		Wrapper<T> wrapper = bulidWrapper(t, "eq", filter);
		return mapper.selectOne(wrapper);
	}

	/**
	 * 根据id寻找对象
	 * 
	 * @param id
	 * @return
	 */
	public T findById(int id) {
		return mapper.selectById(id);
	}

	/**
	 * 根据对象中所有不为空的属性进行like查询,分页查询 对于条件少的建议使用重载方法(T t,Page<T>
	 * page,String...filter);
	 * 
	 * @param t
	 * @param pageBean
	 * @return
	 */
	public IPage<T> list(T t, Page<T> page) {
		String[] filedNames = StringUtil.getFiledName(t);
		Wrapper<T> wrapper = bulidWrapper(t, "like", filedNames);
		IPage<T> iPage = mapper.selectPage(page, wrapper);
		return iPage;
	}

	/**
	 * 根据对象中所有不为空的属性进行查询,分页查询 对于条件少的建议使用重载方法(T t,Page<T> page,String...filter);
	 * 根据style参数传入eq,like,确定查询方式
	 * 
	 * @param t
	 * @param pageBean
	 * @return
	 */
	public IPage<T> list(T t, Page<T> page, String style) {
		String[] filedNames = StringUtil.getFiledName(t);
		Wrapper<T> wrapper = bulidWrapper(t, style, filedNames);
		IPage<T> iPage = mapper.selectPage(page, wrapper);
		return iPage;
	}

	/**
	 * 根据filter条件模糊查询，filter为属性名,分页 filter也可为oderBy_列名,可往后一直叠加_列名,
	 * filter新增eq_属性名
	 * @param t
	 * @param pageBean
	 * @return
	 */
	public IPage<T> list(T t, Page<T> page, String... filter) {
		Wrapper<T> wrapper = bulidWrapper(t, "like", filter);
		IPage<T> iPage = mapper.selectPage(page, wrapper);
		return iPage;
	}

	/**
	 * 根据filter条件模糊查询，filter为属性名,分页 加上根据时间范围查询,timeColumnName为对应数据表时间列
	 * filter也可为oderBy_列名,可往后一直叠加_列名,
	 * 
	 * @param t
	 * @param pageBean
	 * @return
	 */
	public IPage<T> list(T t, Page<T> page, String timeColumnName, Map<String, Date> timeMap, String... filter) {
		QueryWrapper<T> wrapper = bulidWrapper(t, "like", filter);
		wrapper.between(timeMap.size() == 2, timeColumnName, timeMap.get("startTime"), timeMap.get("endTime"));
		IPage<T> iPage = mapper.selectPage(page, wrapper);
		return iPage;
	}

	/**
	 * 根据对象中所有不为空的属性进行模糊查询,不分页 filter也可为oderBy_列名,可往后一直叠加_列名,
	 * 
	 * @param t
	 * @return
	 */
	public List<T> list(T t, String... filter) {
		QueryWrapper<T> wrapper = bulidWrapper(t, "like", filter);
		return mapper.selectList(wrapper);
	}

	/**
	 * 根据对象属性模糊查询,不分页 对于条件少的建议使用重载方法(T t,String...filter);
	 * 
	 * @param t
	 * @return
	 */
	public List<T> list(T t) {
		String[] filedNames = StringUtil.getFiledName(t);
		QueryWrapper<T> wrapper = bulidWrapper(t, "like", filedNames);
		return mapper.selectList(wrapper);
	}

	/**
	 * 根据对象属性查询， 对于条件少的建议使用重载方法(T t,String style,String...filter);
	 * 根据style参数传入eq,like,确定查询方式
	 * 
	 * @param t
	 * @return
	 */
	public List<T> list(T t, String style) {
		String[] filedNames = StringUtil.getFiledName(t);
		QueryWrapper<T> wrapper = bulidWrapper(t, style, filedNames);
		return mapper.selectList(wrapper);
	}

	/**
	 * 根据对象中所有不为空的属性进行模糊查询 对于条件少的建议使用重载方法(T t,String...filter);
	 * 
	 * @param t
	 * @return
	 */
	public int count(T t) {
		String[] filedNames = StringUtil.getFiledName(t);
		QueryWrapper<T> wrapper = bulidWrapper(t, "like", filedNames);
		return mapper.selectCount(wrapper);
	}

	/**
	 * 根据filter条件查询，filter为属性名
	 * 
	 * @param t
	 * @param filter
	 * @return
	 */
	public int count(T t, String... filter) {
		QueryWrapper<T> wrapper = bulidWrapper(t, "like", filter);
		return mapper.selectCount(wrapper);
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param t
	 * @return
	 */
	public T save(T t) {
		Object id = StringUtil.getFieldValueByName("id", t);
		if (id != null) {
			mapper.updateById(t);
		} else {
			mapper.insert(t);
		}
		return t;
	}

	/**
	 * 批量删除数据
	 * 
	 * @param ids
	 * @return
	 */
	public String delete(String ids) {
		String[] idArr = ids.split("\\,");
		for (String id : idArr) {
			mapper.deleteById(Integer.valueOf(id));
		}
		return "success";
	}

	/**
	 * 根据id删除一条数据 一般推荐web层接口直接接受批量id，然后调用批量删除方法
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		return mapper.deleteById(id);
	}

	/**
	 * 根据filter条件删除数据，filter为属性名
	 * 
	 * @param t
	 * @return
	 */
	public int delete(T t, String... filter) {
		QueryWrapper<T> wrapper = bulidWrapper(t, "eq", filter);
		return mapper.delete(wrapper);
	}

	/**
	 * 根据对象中所有不为空的属性为条件进行删除
	 * 
	 * @param t
	 * @return
	 */
	public int delete(T t) {
		String[] filedNames = StringUtil.getFiledName(t);
		QueryWrapper<T> wrapper = bulidWrapper(t, "eq", filedNames);
		return mapper.delete(wrapper);
	}

	/**
	 * 根据对象中所有不为空的属性为条件进行查询，若存在返回true，不存在返回false
	 * 
	 * @param t
	 * @return
	 */
	public boolean existed(T t) {
		T findOne = this.findOne(t);
		if (findOne == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据filter条件查询数据，filter为属性名，若存在返回true，不存在返回false
	 * 
	 * @param t
	 * @return
	 */
	public boolean existed(T t, String... filter) {
		T findOne = this.findOne(t, filter);
		if (findOne == null) {
			return false;
		} else {
			return true;
		}
	}
}
