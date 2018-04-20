action
@[spackagepath]/@[spackage]/action/@[javaname]Action.java
true
package ${package}.${spackage}.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yhert.project.common.beans.Param;
import com.yhert.project.common.beans.Result;
import com.yhert.project.common.util.BeanUtils;
import com.yhert.project.common.util.CommonFunUtils;
import ${package}.${spackage}.beans.${javaname};
import ${package}.${spackage}.beans.vo.${javaname}Vo;
import ${package}.${spackage}.service.${javaname}Service;
import com.yhert.project.oa.common.yfw.action.AbstractAction;
import com.yhert.project.oa.common.yfw.action.IAction;

/**
 * ${tableComment}接口
 * 
 * @author ${author} ${newdate}
 *
 */
@Controller
@RequestMapping("${urlPrefix}${url}")
public class ${javaname}Action extends AbstractAction<${javaname}> implements IAction {
	/**
	 * ${tableComment}服务
	 */
	@Autowired
	private ${javaname}Service ${cname}Service;

	/**
	 * 列表
	 * 
	 * @return 列表
	 */
	@RequestMapping("list")
	public ModelAndView enter() {
		return getModelAndView();
	}

	/**
	 * 查询${tableComment}参数
	 * 
	 * @return 参数
	 */
	@RequestMapping("query")
	public ModelAndView query() {
		Result<${javaname}Vo> ${cname}Result = ${cname}Service.queryVo(getRequestParam());
		ModelAndView modelAndView = getModelAndView(${cname}Result);
		return modelAndView;
	}

	/**
	 * 修改界面
	 * 
	 * @param ${cname}
	 *            数据
	 * @return 结果
	 */
	@RequestMapping("modify")
	public ModelAndView modify(${javaname} ${cname}) {
		Param param = Param.getParam();
		if (!CommonFunUtils.isNe(${cname}.get${pk.ucname}())) {
			${cname} = ${cname}Service.get(${cname}.get${pk.ucname}());
			param.put(RESULT, ${cname});
		}
		return getModelAndView(param);
	}

	/**
	 * 删除
	 * 
	 * @param ${cname}
	 *            数据
	 * @return 结果
	 */
	@RequestMapping("delete")
	public ModelAndView delete(${javaname} ${cname}) {
		if (!CommonFunUtils.isNe(${cname}.get${pk.ucname}())) {
			${cname}Service.delete(${cname});
		}
		return getModelAndView(Param.getParam(RESULT, ${cname}));
	}

	/**
	 * 保存修改结果
	 * 
	 * @param ${cname}
	 *            数据
	 * @return 结果
	 */
	@RequestMapping("save")
	public ModelAndView save(${javaname} ${cname}) {
		if (CommonFunUtils.isNe(${cname}.get${pk.ucname}())) {
			${cname}.set${pk.ucname}(CommonFunUtils.getUUID(${cname}));
			BeanUtils.validate(${cname});
			${cname}Service.insert(${cname});
		} else {
			BeanUtils.validate(${cname});
			${cname}Service.update(${cname});
		}
		return getModelAndView(Param.getParam(RESULT, ${cname}));
	}
}
