package site.jim97.enums;

public enum ActionEnum {
	LOGIN_ACTION("登录操作"), LOGOUT_ACTION("退出操作"), ADD_ACTION("添加操作"), DELETE_ACTION("删除操作"), QUERY_ACTION("查询操作");

	private String info;

	private ActionEnum(String info) {
		this.info = info;
	}
}
