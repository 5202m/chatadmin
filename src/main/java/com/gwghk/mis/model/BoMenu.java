package com.gwghk.mis.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：系统管理-菜单对象(系统管理)
 * @author Gavin.guo
 * @date   2015年2月4日
 */
@Document
public class BoMenu extends BaseModel{ 

	/**
	 * 菜单Id
	 */
	@Id
	private String menuId;

	/**
	 * 父类菜单Id
	 */
	private String parentMenuId;

	/**
	 * 菜单代码
	 */
	@Indexed
	private String code;

	/**
	 * 菜单类型 ,默认0(0菜单,1功能)
	 */
	private Integer type;

	/**
	 * 是否有效，默认有效
	 */
	private Integer valid;

	/**
	 * 简体名称
	 */
	private String nameCN;

	/**
	 * 繁体名称
	 */
	private String nameTW;

	/**
	 * 英文名称
	 */
	private String nameEN;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 菜单路径
	 */
	private String url;
	
	/**
	 * 角色
	 */
	private List<BoRole> roleList;

	public String getMenuId () { 
		return this.menuId;
	}

	public void setMenuId (String menuId) { 
		this.menuId = menuId;
	}

	public String getParentMenuId () { 
		return this.parentMenuId;
	}

	public void setParentMenuId (String parentMenuId) { 
		this.parentMenuId = parentMenuId;
	}

	public String getCode () { 
		return this.code;
	}

	public void setCode (String code) { 
		this.code = code;
	}

	public Integer getType () { 
		return this.type;
	}

	public void setType (Integer type) { 
		this.type = type;
	}

	public Integer getValid () { 
		return this.valid;
	}

	public void setValid (Integer valid) { 
		this.valid = valid;
	}

	public String getNameCN () { 
		return this.nameCN;
	}

	public void setNameCN (String nameCN) { 
		this.nameCN = nameCN;
	}

	public String getNameTW () { 
		return this.nameTW;
	}

	public void setNameTW (String nameTW) { 
		this.nameTW = nameTW;
	}

	public String getNameEN () { 
		return this.nameEN;
	}

	public void setNameEN (String nameEN) { 
		this.nameEN = nameEN;
	}

	public Integer getSort () { 
		return this.sort;
	}

	public void setSort (Integer sort) { 
		this.sort = sort;
	}

	public Integer getStatus () { 
		return this.status;
	}

	public void setStatus (Integer status) { 
		this.status = status;
	}

	public String getRemark () { 
		return this.remark;
	}

	public void setRemark (String remark) { 
		this.remark = remark;
	}

	public String getUrl () { 
		return this.url;
	}

	public void setUrl (String url) { 
		this.url = url;
	}

	public List<BoRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<BoRole> roleList) {
		this.roleList = roleList;
	}

}