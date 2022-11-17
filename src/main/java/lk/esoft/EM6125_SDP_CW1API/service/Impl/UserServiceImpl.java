package lk.esoft.EM6125_SDP_CW1API.service.Impl;
/**
 * @author Udara San
 * @TimeStamp 11:43 AM | 11/9/2022 | 2022
 * @ProjectDetails ecom-api
 */

import lk.esoft.EM6125_SDP_CW1API.dto.UserDTO;
import lk.esoft.EM6125_SDP_CW1API.entity.User;
import lk.esoft.EM6125_SDP_CW1API.repository.UserRepository;
import lk.esoft.EM6125_SDP_CW1API.util.VarListUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }
    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return modelMapper.map(user,UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleCode().toString()));
        return authorities;
    }

    public String saveUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return VarListUtil.RSP_NO_DATA_FOUND;
        } else {
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarListUtil.RSP_SUCCESS;
        }
    }

    public String deleteUser(String username) {
        if (userRepository.existsByUsername(username)) {
            userRepository.deleteByUsername(username);
            return VarListUtil.RSP_SUCCESS;
        } else {
            return VarListUtil.RSP_NO_DATA_FOUND;
        }
    }
    public String updateUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            userRepository.updateUser(userDTO.getAddress(),userDTO.getEmail(),
                    userDTO.getIdPhoto(),userDTO.getName(),userDTO.getPassword(),
                    userDTO.getPhoneNo1(),userDTO.getPhoneNo2(),userDTO.getRemarks(),
                    userDTO.getRoleCode(),userDTO.getStatus(),userDTO.getUsername());
            return VarListUtil.RSP_SUCCESS;
        } else {
            return VarListUtil.RSP_NO_DATA_FOUND;
        }
    }

    public List<UserDTO> getAllUsers() {
        List<User> users=userRepository.findAll();
        return modelMapper.map(users, new TypeToken<ArrayList<UserDTO>>() {
        }.getType());
    }

    public UserDTO searchUser(String username) {
        if (userRepository.existsByUsername(username)) {
            User user=userRepository.findByUsername(username);
            return modelMapper.map(user,UserDTO.class);
        } else {
            return null;
        }
    }

}
