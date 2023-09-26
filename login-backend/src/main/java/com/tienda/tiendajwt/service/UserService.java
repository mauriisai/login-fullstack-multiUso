package com.tienda.tiendajwt.service;

import com.tienda.tiendajwt.dao.RoleDao;
import com.tienda.tiendajwt.dao.UserDao;
import com.tienda.tiendajwt.entity.Role;
import com.tienda.tiendajwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        // Asignando rol al nuevo usuario creado
        Role role = roleDao.findById("User").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);

        //Encriptando la clave del usuario para almacenarla en la BD.
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        // Guardando el new User en la BD
        return userDao.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }


    /*public void initRoleAndUsers() {
        User adminUser = new User();
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin00"));

        userDao.save(adminUser);

        User user = new User();
        user.setUserFirstName("daniel");
        user.setUserLastName("Roman");
        user.setUserName("daniel1");
        user.setUserPassword(getEncodedPassword("user00"));

        userDao.save(user);
    }
     */
}
