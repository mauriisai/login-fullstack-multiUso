package com.tienda.tiendajwt.service;

import com.tienda.tiendajwt.dao.RoleDao;
import com.tienda.tiendajwt.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role){
        return roleDao.save(role);
    }
}
