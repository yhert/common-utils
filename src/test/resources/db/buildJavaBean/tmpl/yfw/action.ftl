action
@[spackagepath]/@[spackage]/action/@[javaname]Action.java
true
package ${package}.${spackage}.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ${package}.${spackage}.beans.${javaname};
import ${package}.${spackage}.beans.condition.${javaname}Condition;
import ${package}.${spackage}.beans.vo.${javaname}Vo;
import ${package}.${spackage}.service.${javaname}Service;
import com.yhert.project.yfw.action.AbstractAction;
import com.yhert.project.yfw.security.annotations.HasRoleAndPower;
import com.yhert.project.common.beans.Param;
import com.yhert.project.common.beans.Result;
import com.yhert.project.common.util.BeanUtils;
import com.yhert.project.common.util.CommonFunUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ${tableComment}接口
 * 
 * @author ${author} ${newdate}
 *
 */
@RestController
@RequestMapping("${urlPrefix}${url}")
@HasRoleAndPower("ROLE_ADMIN")
@Api(value = "${@description}", tags = "${tableComment}")
public class ${javaname}Action extends AbstractAction<${javaname}> {
	@Autowired
	private ${javaname}Service ${cname}Service;

	/**
	 * 获取列表信息
	 * 
	 * @return 查询结果
	 */
	@ApiOperation(value = "查询${tableComment}列表", notes = "查询${tableComment}列表")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "正常响应信息"),
			@ApiResponse(code = 400, message = "请求无效", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 401, message = "无权访问", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 403, message = "禁止此操作", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 404, message = "资源未找到", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 405, message = "输入的数据不正确", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 410, message = "资源已删除", response = com.yhert.project.yfw.action.bean.Result.class) })
	@ApiImplicitParams(value = {
			@ApiImplicitParam(paramType = "form", dataTypeClass = ${javaname}Condition.class, name = "${cname}Condition", value = "${tableComment}查询条件") })
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Result<${javaname}Vo> queryVo(${javaname}Condition ${cname}Condition) {
		return ${cname}Service.queryVo(${cname}Condition);
	}

	/**
	 * 查询数据详情
	 * 
	 * @param ${cname}${pk.ucname}
	 *            主键
	 * @return 详细信息
	 */
	@ApiOperation(value = "查询单个${tableComment}", notes = "查询单个${tableComment}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "正常响应信息"),
			@ApiResponse(code = 400, message = "请求无效", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 401, message = "无权访问", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 403, message = "禁止此操作", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 404, message = "资源未找到", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 405, message = "输入的数据不正确", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 410, message = "资源已删除", response = com.yhert.project.yfw.action.bean.Result.class) })
	@ApiImplicitParams(value = {
			@ApiImplicitParam(paramType = "path", dataTypeClass = String.class, name = "${cname}${pk.ucname}", value = "${tableComment}查询条件") })
	@RequestMapping(value = "{${cname}${pk.ucname}}", method = RequestMethod.GET)
	public ${javaname}Vo getVo(@PathVariable("${cname}${pk.ucname}") String ${cname}${pk.ucname}) {
		return ${cname}Service.queryOneVoByPk(${cname}${pk.ucname});
	}

	/**
	 * 新增信息
	 * 
	 * @param ${cname}
	 *            内容
	 * @return 更新结果
	 */
	@ApiOperation(value = "新增${tableComment}", notes = "新增${tableComment}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "正常响应信息"),
			@ApiResponse(code = 400, message = "请求无效", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 401, message = "无权访问", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 403, message = "禁止此操作", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 404, message = "资源未找到", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 405, message = "输入的数据不正确", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 410, message = "资源已删除", response = com.yhert.project.yfw.action.bean.Result.class) })
	@ApiImplicitParams(value = {
			@ApiImplicitParam(paramType = "form", dataTypeClass = ${javaname}.class, name = "${cname}", value = "${tableComment}") })
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ${javaname} add(${javaname} ${cname}) {
		${cname}.set${pk.ucname}(CommonFunUtils.getUUID());
		BeanUtils.validate(${cname});
		${cname}Service.insert(${cname});
		return ${cname};
	}

	/**
	 * 更新信息
	 * 
	 * @param ${cname}${pk.ucname}
	 *            主键
	 * @param ${cname}
	 *            内容
	 * @return 更新后结果
	 */
	@ApiOperation(value = "更新${tableComment}", notes = "更新${tableComment}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "正常响应信息"),
			@ApiResponse(code = 400, message = "请求无效", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 401, message = "无权访问", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 403, message = "禁止此操作", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 404, message = "资源未找到", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 405, message = "输入的数据不正确", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 410, message = "资源已删除", response = com.yhert.project.yfw.action.bean.Result.class) })
	@ApiImplicitParams(value = {
			@ApiImplicitParam(paramType = "path", dataTypeClass = String.class, name = "${cname}${pk.ucname}", value = "${tableComment}主键"),
			@ApiImplicitParam(paramType = "form", dataTypeClass = ${javaname}.class, name = "${cname}", value = "${tableComment}") })
	@RequestMapping(value = "{${cname}${pk.ucname}}", method = RequestMethod.PUT)
	public ${javaname} update(@PathVariable("${cname}${pk.ucname}") String ${cname}${pk.ucname}, ${javaname} ${cname}) {
		CommonFunUtils.nullException(${cname}${pk.ucname}, "更新时${cname}${pk.ucname}不能为空");
		CommonFunUtils.nullException(${cname}Service.get(Param.getParam(${javaname}.FIELD_${pk.uname}, ${cname}${pk.ucname})), "未找到数据，不能更新");
		${cname}.set${pk.ucname}(${cname}${pk.ucname});
		BeanUtils.validate(${cname});
		${cname}Service.update(${cname});
		return ${cname};
	}

	/**
	 * 局部更新数据
	 * 
	 * @param ${cname}${pk.ucname}
	 *            主键
	 * @return 更新后结果
	 */
	@ApiOperation(value = "局部更新${tableComment}", notes = "不为空时进行更新")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "正常响应信息"),
			@ApiResponse(code = 400, message = "请求无效", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 401, message = "无权访问", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 403, message = "禁止此操作", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 404, message = "资源未找到", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 405, message = "输入的数据不正确", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 410, message = "资源已删除", response = com.yhert.project.yfw.action.bean.Result.class) })
	@ApiImplicitParams(value = {
			@ApiImplicitParam(paramType = "path", dataTypeClass = String.class, name = "${cname}${pk.ucname}", value = "${tableComment}主键"),
			@ApiImplicitParam(paramType = "form", dataTypeClass = ${javaname}.class, name = "${cname}", value = "${tableComment}") })
	@RequestMapping(value = "{${cname}${pk.ucname}}", method = RequestMethod.PATCH)
	public ${javaname} patch(@PathVariable("${cname}${pk.ucname}") String ${cname}${pk.ucname}, ${javaname} ${cname}Param) {
		CommonFunUtils.nullException(${cname}${pk.ucname}, "更新时${cname}${pk.ucname}不能为空");
		${javaname} ${cname} = ${cname}Service.get(Param.getParam(${javaname}.FIELD_${pk.uname}, ${cname}${pk.ucname}));
		CommonFunUtils.nullException(${cname}, "未找到数据，不能更新");
		BeanUtils.copyNotNeObject(${cname}, ${cname}Param);
		${cname}.set${pk.ucname}(${cname}${pk.ucname});
		BeanUtils.validate(${cname});
		${cname}Service.update(${cname});
		return ${cname};
	}

	/**
	 * 删除数据
	 * 
	 * @param ${cname}${pk.ucname}
	 *            主键
	 * @return 更新结果
	 */
	@ApiOperation(value = "删除${tableComment}", notes = "删除${tableComment}")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "正常响应信息"),
			@ApiResponse(code = 400, message = "请求无效", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 401, message = "无权访问", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 403, message = "禁止此操作", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 404, message = "资源未找到", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 405, message = "输入的数据不正确", response = com.yhert.project.yfw.action.bean.Result.class),
			@ApiResponse(code = 410, message = "资源已删除", response = com.yhert.project.yfw.action.bean.Result.class) })
	@ApiImplicitParams(value = {
			@ApiImplicitParam(paramType = "path", dataTypeClass = String.class, name = "${cname}${pk.ucname}", value = "${tableComment}主键") })
	@RequestMapping(value = "{${cname}${pk.ucname}}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("${cname}${pk.ucname}") String ${cname}${pk.ucname}) {
		${cname}Service.delete(Param.getParam(${javaname}.FIELD_${pk.uname}, ${cname}${pk.ucname}));
	}
}
