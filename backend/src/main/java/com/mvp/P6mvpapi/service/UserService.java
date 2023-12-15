package com.mvp.P6mvpapi.service;
import java.util.Optional;

import com.mvp.P6mvpapi.models.Theme;
import com.mvp.P6mvpapi.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    User findById(int id);
    User findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
    String extractUsernameFromToken();
    String extractEmailFromToken(String token);
    List<Theme> getThemesByUser(User user);
    boolean isUserSubscribedToTheme(int userId, int themeId);
    void subscribeUserToTheme(int userId, int themeId);
    void unsubscribeUserFromTheme(int userId, int themeId);
    void saveUser(User user);
}
