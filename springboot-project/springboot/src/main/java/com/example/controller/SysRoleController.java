package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.management.relation.Role;
import java.util.List;
import com.example.service.ISysRoleService;
import com.example.entity.SysRole;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tjyy
 * @since 2022-08-26
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @PostMapping
    public Boolean save(@RequestBody SysRole sysRole) {
        return sysRoleService.saveOrUpdate(sysRole);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return sysRoleService.removeById(id);
    }

    @GetMapping
    public List<SysRole> findAll() {
        return sysRoleService.list();
    }

    @GetMapping("/{id}")
    public SysRole findOne(@PathVariable Integer id) {
        return sysRoleService.getById(id);
    }

    @GetMapping("/page")
    public Page<SysRole> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        return sysRoleService.page(new Page<>(pageNum, pageSize));
    }

    @PostMapping("/roleMenu/{roleId}")
    public Boolean roleMenu(@PathVariable int roleId, @RequestBody List<Integer> menuIds) {
        sysRoleService.setRoleMenu(roleId,menuIds);
        return true;
    }


    @GetMapping("/roleMenu/{roleId}")
    public List<Integer> roleMenu(@PathVariable int roleId) {
        return sysRoleService.getRoleMenu(roleId);
    }

}

