package com.huawei.ibooking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.ibooking.mapper.StudentMapper;
import com.huawei.ibooking.model.StudentDO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public UserDetails loadUserByUsername(String stuNum) throws UsernameNotFoundException {
        List<StudentDO> users = studentMapper.getStudent(stuNum);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        StudentDO user = users.get(0);

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            authorities.add(new SimpleGrantedAuthority("STUDENT"));

        } else {
            authorities.add(new SimpleGrantedAuthority("STUDENT"));
        }
        // logger.warn("读取student");
        return new User(user.getStuNum(), user.getPassword(), authorities);
    }
}
