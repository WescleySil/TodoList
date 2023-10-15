package com.wescleyportfolio.java.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.wescleyportfolio.java.todolist.models.User;
import com.wescleyportfolio.java.todolist.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    private UserRepository userRepository;

    public FilterTaskAuth(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if(servletPath.startsWith("/tasks/")){
            String authorization = request.getHeader("Authorization");

            String authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

            String credentialsString= new String(authDecoded);

            String[] credentials = credentialsString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            User user = userRepository.findByUsername(username);
            if(user == null){
                response.sendError(401);
            }else{
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(result.verified){
                    request.setAttribute("idUser",user.getId());
                    filterChain.doFilter(request, response);
                }else{
                    response.sendError(401);
                }
            }

        }else{
            filterChain.doFilter(request,response);
        }




    }
}
