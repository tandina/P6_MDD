package com.mvp.P6mvpapi.service.impl;
import java.util.Optional;
import com.mvp.P6mvpapi.models.Article;
import com.mvp.P6mvpapi.models.Theme;
import com.mvp.P6mvpapi.models.User;
import com.mvp.P6mvpapi.repository.ThemeRepository;
import com.mvp.P6mvpapi.repository.UserRepository;
import com.mvp.P6mvpapi.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("utilisateur introuvable"));
            }
        };
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
    @Override
    public String extractEmailFromToken(String token) {
    String secretKey = "piCdjyVY1fiZavME8QHZymVx2nU6xGsEsK89L+/DRpU="; 
    Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(token.replace("Bearer ", ""))
                        .getBody();
    return claims.getSubject();
}

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
    }
    @Override
    public List<Theme> getThemesByUser(User user) {
        return new ArrayList<>(user.getThemes());
    }
    @Override
    public String extractUsernameFromToken() {
        return "username";
    }
    @Override
    public void subscribeUserToTheme(int userId, int themeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Theme theme = themeRepository.findById(themeId).orElseThrow(() -> new RuntimeException("Theme not found"));

        if (!user.getThemes().contains(theme)) {
            user.getThemes().add(theme);
            userRepository.save(user);
        } else {
            throw new RuntimeException("L'utilisateur est déjà abonné à ce thème.");
        }
    }
    @Override
    public boolean isUserSubscribedToTheme(int userId, int themeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Theme theme = themeRepository.findById(themeId).orElseThrow(() -> new RuntimeException("Theme not found"));
        return user.getThemes().contains(theme);
    }
    @Override
    public void unsubscribeUserFromTheme(int userId, int themeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Theme theme = themeRepository.findById(themeId).orElseThrow(() -> new RuntimeException("Theme not found"));
        user.getThemes().remove(theme);
        userRepository.save(user);
    }



}
