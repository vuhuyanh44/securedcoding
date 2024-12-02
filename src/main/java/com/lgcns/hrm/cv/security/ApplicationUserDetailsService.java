package com.lgcns.hrm.cv.security;

import com.lgcns.hrm.cv.common.utils.ObjectUtil;
import com.lgcns.hrm.cv.entity.Permission;
import com.lgcns.hrm.cv.entity.Group;
import com.lgcns.hrm.cv.repository.UserRepository;
import com.lgcns.hrm.cv.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author pigx
 */
@Service("userDetailsService")
@RequiredArgsConstructor
@Transactional
public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Couldn't find user by email " + email));
        var groups = ObjectUtil.isEmpty(user.getGroups()) ? Collections.singletonList(groupRepository.findByName("USER")) : user.getGroups();
        return new User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, getAuthorities(groups));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Group> groups) {
        return getGrantedAuthorities(getPrivileges(groups));
    }

    private List<String> getPrivileges(Collection<Group> groups) {

        var privileges = new ArrayList<String>();
        var collection = new ArrayList<Permission>();
        for (Group group : groups) {
            privileges.add("GROUP_"+ group.getName());
            collection.addAll(group.getPermissions());
        }
        for (var item : collection) {
            privileges.add(item.getComponentName()+":"+item.getAction());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        var authorities = new ArrayList<GrantedAuthority>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}