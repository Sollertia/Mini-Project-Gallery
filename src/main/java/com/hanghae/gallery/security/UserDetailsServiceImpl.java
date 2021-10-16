package com.hanghae.gallery.security;

import com.hanghae.gallery.model.Artist;
import com.hanghae.gallery.model.User;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, ArtistRepository artistRepository) {
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));

        return new UserDetailsImpl(user);
    }
    public UserDetails loadArtistByArtistname(String username) throws UsernameNotFoundException {
        Artist artist = artistRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
        User user = new User(artist.getUsername(),artist.getPassword(),artist.getNickname(),artist.getRole());
        return new UserDetailsImpl(user);
    }
}
