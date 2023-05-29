package com.huawei.ibooking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.huawei.ibooking.mapper.StudentMapper;
import com.huawei.ibooking.model.StudentDO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public UserDetails loadUserByUsername(String stuNum) throws UsernameNotFoundException {
        List<StudentDO> users = studentMapper.getStudent(stuNum);
        
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        StudentDO user = users.get(0);
        return new org.springframework.security.core.userdetails.User(user.getStuNum(), user.getPassword(), new ArrayList<>());
    }
}
