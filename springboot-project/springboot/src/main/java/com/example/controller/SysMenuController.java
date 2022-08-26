package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Mapper.DictMapper;
import com.example.common.Constants;
import com.example.entity.Dict;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import com.example.service.ISysMenuService;
import com.example.entity.SysMenu;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tjyy
 * @since 2022-08-26
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {
    @Resource
    private DictMapper dictMapper;

    @Resource
    private ISysMenuService sysMenuService;

    @PostMapping
    public Boolean save(@RequestBody SysMenu sysMenu) {
        return sysMenuService.saveOrUpdate(sysMenu);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return sysMenuService.removeById(id);
    }

    @GetMapping
    public List<SysMenu> findAll() {
//        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("name",name);
//        queryWrapper.orderByDesc("id");

        // 查询所有数据
        List<SysMenu> list = sysMenuService.list();
        //  找出一级菜单
        List<SysMenu> parentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (SysMenu menu : parentNode) {
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNode;
    }

    @GetMapping("/{id}")
    public SysMenu findOne(@PathVariable Integer id) {
        return sysMenuService.getById(id);
    }

    @GetMapping("/page")
    public Page<SysMenu> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        return sysMenuService.page(new Page<>(pageNum, pageSize));
    }

    @GetMapping("/icons")
    public List<Dict> getIcons(){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        List<Dict> dicts = dictMapper.selectList(queryWrapper);
        return dicts;
    }
}

