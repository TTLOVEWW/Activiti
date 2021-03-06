package com.voidforce.activiti.service.role.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.voidforce.activiti.bean.Role;
import com.voidforce.activiti.common.constant.CommonConstant;
import com.voidforce.activiti.exception.BaseException;
import com.voidforce.activiti.mapper.role.RoleMapper;
import com.voidforce.activiti.service.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Long insert(Role role) {
        Role dbRole = this.getByRole(role.getRole());
        if(dbRole == null) {
            role.setDeleted(CommonConstant.NOT_DELETED);
            roleMapper.insert(role);
            return role.getRoleId();
        }
        logger.warn("权限 {} 已存在", role.getRole());
        throw new BaseException("权限 " + role.getRole() + " 已存在");
    }

    @Override
    public Role getById(Long roleId) {
        return roleMapper.getById(roleId, CommonConstant.NOT_DELETED);
    }

    @Override
    public Role getByRole(String role) {
        return roleMapper.getByRole(role, CommonConstant.NOT_DELETED);
    }

    @Override
    public List<Role> findByUserInfoId(Long userInfoId) {
        return roleMapper.findByUserInfoId(userInfoId, CommonConstant.NOT_DELETED);
    }

    @Override
    public List<Role> findAll(Role role) {
        return roleMapper.findAll(role);
    }

    @Override
    public PageInfo<Role> findAllForPage(Integer page, Integer limit, Role role) {
        PageHelper.startPage(page, limit);
        List<Role> roles = this.findAll(role);
        return new PageInfo<>(roles);
    }

    @Override
    public void update(Role role) {
        Role dbRole = this.getByRole(role.getRole());
        if(dbRole != null && !dbRole.getRoleId().equals(role.getRoleId())) {
            logger.warn("权限 {} 已存在", role.getRole());
            throw new BaseException("权限 " + role.getRole() + " 已存在");
        }
        roleMapper.update(role);
    }

    @Override
    public void delete(Long roleId) {
        roleMapper.delete(roleId);
    }
}
