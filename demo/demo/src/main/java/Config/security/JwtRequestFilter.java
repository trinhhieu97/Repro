//package Config.security;
//
//import Config.jwt.JwtTokenUtil;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//    private final JwtTokenUtil jwtTokenUtil;
//    private final UserDetailsService jwtUserDetailsService;
//
//    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService jwtUserDetailsService) {
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.jwtUserDetailsService = jwtUserDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        final String requestTokenHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwtToken = null;
//
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            jwtToken = requestTokenHeader.substring(7);
//            try {
//                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//            } catch (Exception e) {
//                logger.error("Unable to get JWT Token", e);
//            }
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
//            if (jwtTokenUtil.validateToken(jwtToken)) {
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//
//}
