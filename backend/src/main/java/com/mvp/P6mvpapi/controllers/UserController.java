package com.mvp.P6mvpapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mvp.P6mvpapi.models.Theme;
import com.mvp.P6mvpapi.models.User;
import com.mvp.P6mvpapi.repository.UserRepository;
import com.mvp.P6mvpapi.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private String extractTokenFromRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attr != null) {
            HttpServletRequest request = attr.getRequest();
            String token = request.getHeader("Authorization");
            return token;
        }
        return null;
    }

    @PutMapping("auth/{id}/update")
    public ResponseEntity<?> updateUserDetails(@RequestBody User newUser, @PathVariable("id") int id) {
        User existingUser = userService.findById(id);
        if (existingUser != null) {
            logger.info("Ancien username: " + existingUser.getUsername());
            logger.info("Ancien email: " + existingUser.getEmail());

            existingUser.setUsername(newUser.getUsername());
            existingUser.setEmail(newUser.getEmail());
            userService.saveUser(existingUser);

            logger.info("Nouveau username: " + existingUser.getUsername());
            logger.info("Nouveau email: " + existingUser.getEmail());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            user.setUsername(newUser.getUsername());
            user.setEmail(newUser.getEmail());

            return ResponseEntity.ok().body(Map.of("message", "User mis à jour avec succès"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User non trouvé"));
        }
    }

    @GetMapping("auth")
    public ResponseEntity<User> getUser() {
        String token = extractTokenFromRequest();
        String usernameOrEmail = userService.extractEmailFromToken(token);
        User user = userService.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username or email : " + usernameOrEmail));
        return ResponseEntity.ok(user);
    }

    @PostMapping("auth/{userId}/themes/{themeId}/subscribe")
    public ResponseEntity<String> subscribeUserToTheme(@PathVariable int userId, @PathVariable int themeId) {
        boolean isSubscribed = userService.isUserSubscribedToTheme(userId, themeId);
        if (isSubscribed) {
            return ResponseEntity.badRequest().body("Vous êtes déjà abonné à ce thème.");
        } else {
            userService.subscribeUserToTheme(userId, themeId);
            return ResponseEntity.ok("Vous êtes abonné avec succès au thème.");
        }
    }

    @DeleteMapping("auth/{userId}/themes/{themeId}/unsubscribe")
    public ResponseEntity<Map<String, String>> unsubscribeUserFromTheme(@PathVariable int userId, @PathVariable int themeId) {
        boolean isSubscribed = userService.isUserSubscribedToTheme(userId, themeId);
        if (!isSubscribed) {
            return ResponseEntity.badRequest().body(Map.of("error", "L'utilisateur n'est pas abonné à ce thème."));
        } else {
            userService.unsubscribeUserFromTheme(userId, themeId);
            return ResponseEntity.ok(Map.of("message", "L'abonnement a été suspendu avec succès."));
        }
    }

    @GetMapping("auth/{userId}/themes")
    public ResponseEntity<List<Theme>> getThemesByUser(@PathVariable int userId) {
        User user = userService.findById(userId);
        List<Theme> themes = userService.getThemesByUser(user);
        return ResponseEntity.ok(themes);
    }

}
