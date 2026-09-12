package ken.vivid.auth.adapter.in.web.security;

import ken.vivid.auth.application.port.out.LoadUser;
import ken.vivid.auth.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoadUser loadUser;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = loadUser.loadByEmailOrUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .disabled(!user.getActive())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
                .build();
    }
}