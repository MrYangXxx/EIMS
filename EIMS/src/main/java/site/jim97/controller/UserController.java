package site.jim97.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import site.jim97.entity.Menu;
import site.jim97.entity.User;
import site.jim97.entity.UserRole;
import site.jim97.geetest.GeetestUtil;
import site.jim97.service.MenuService;
import site.jim97.service.UserRoleService;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.CryptographyUtil;
import site.jim97.utils.HttpServletRequestUtil;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User>{
	@Autowired
	MenuService menuService;
	@Autowired
	UserRoleService userRoleService;

	@PostMapping("/login")
	public void login(User user, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		//前台是md5加密，真实密码经过二次盐加密
		user.setPassword(CryptographyUtil.md5(user.getPassword()));
		//获取token，用于用户shiro认证
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
		// geetest验证+shiro认证，返回必要信息
		JSONObject jsonObject = GeetestUtil.getResult(request,token);
		//验证状态
		String status = jsonObject.getString("status");
		//登录成功将用户信息设置在session中
		if(status.equals("success")){
			User userInfo = service.findOne(user);
			session.setAttribute("userInfo", userInfo);
		}
		AjaxUtil.write(response, jsonObject);
	}

	/**
	 * 获取登录用户的信息并返回，用于显示登录人信息
	 * 用shiro管理以后request将是shiro提供的，直接从系统获取HttpSession最方便
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/info")
	public void getUserInfo(HttpSession session, HttpServletResponse response) throws Exception {
		User user = (User) session.getAttribute("userInfo");
		AjaxUtil.create(user).exclude("password").write(response);
	}
	
	@GetMapping("/menu")
	public void getMenu(HttpSession session, HttpServletResponse response) throws Exception {
		User user = (User) session.getAttribute("userInfo"); //根据权限查询菜单
		List<Menu> listMenu = menuService.list(user.getId());
		List<Menu> rootMenu=new ArrayList<>();
		for (int i = 0; i < listMenu.size(); i++) {
			if(listMenu.get(i).getPId()==0){
				rootMenu.add(listMenu.get(i));
				listMenu.remove(i);
				i--;  //位置前挪了减回去就行了
			}
        }
		//JSONArray menuArray = JSONArray.parseArray(JSON.toJSONString(listMenu));
		AjaxUtil.create().put("children", listMenu).put("roots", rootMenu).write(response);
	}
	
	@Override
	@PostMapping("/save")
	public void save(User user,HttpServletResponse response,HttpSession session) throws Exception{
		if(user.getId() == null){
			boolean existed = service.existed(user, "userName","phone");
			if(existed){
				AjaxUtil.fail(response, "用户已存在");
				return;
			}
		}
		User newT = service.save(user);
		if(newT != null){
			AjaxUtil.success(newT,response);
		}else{
			AjaxUtil.fail(response);
		}
	}
	
	/**
	 * 为避免复杂的逻辑操作，且用户所关联角色并不多，这里的逻辑为删除所有用户的关联角色，然后全部重新保存
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@PostMapping("/auth")
	public void saveAuth(HttpServletRequest request,HttpServletResponse response) throws Exception{
		int userId = HttpServletRequestUtil.getInt(request, "userId");
		String roleIds = HttpServletRequestUtil.getString(request, "roleIds");
		String roleNames = HttpServletRequestUtil.getString(request, "roleNames");
		String[] roles = roleIds.split("\\,");
		UserRole deleteUR=new UserRole();
		deleteUR.setUserId(userId);
		userRoleService.delete(deleteUR, "userId");
		//保存用户-角色关联
		for (String roleId : roles) {
			UserRole userRole=new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(Integer.valueOf(roleId));
			userRoleService.save(userRole);
		}
		//更新用户bz信息 //bz字段为方便用户角色名展示字段
		User user=new User();
		user.setId(userId);
		user.setBz(roleNames);
		service.save(user);
		AjaxUtil.success(response);
	}
	
	@PostMapping("/changepassword")
	public void changePassword(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		String oldPassword = HttpServletRequestUtil.getString(request, "oldPassword");
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		User user = (User) session.getAttribute("userInfo");
		if(user.getPassword().equals(CryptographyUtil.md5(oldPassword))){ //密码跟传入的旧密码相等，修改密码
			user.setPassword(newPassword);
			service.save(user);
			AjaxUtil.success(response);
		}else{
			AjaxUtil.fail(response);
		}
	}
}
