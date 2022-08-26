package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Mapper.RoleManuMapper;
import com.example.entity.RoleMenu;
import com.example.entity.SysRole;
import com.example.Mapper.SysRoleMapper;
import com.example.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tjyy
 * @since 2022-08-26
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private RoleManuMapper roleManuMapper;

    @Transactional
    @Override
    public void setRoleMenu(int roleId, List<Integer> menuIds) {
        roleManuMapper.deleteByRoleId(roleId);
        for (Integer menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleManuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(int roleId) {
        return roleManuMapper.selectByRoleId(roleId);
    }
}
