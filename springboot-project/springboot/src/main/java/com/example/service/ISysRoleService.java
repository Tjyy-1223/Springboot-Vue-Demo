package com.example.service;

import com.example.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tjyy
 * @since 2022-08-26
 */
public interface ISysRoleService extends IService<SysRole> {

    void setRoleMenu(int roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(int roleId);
}
