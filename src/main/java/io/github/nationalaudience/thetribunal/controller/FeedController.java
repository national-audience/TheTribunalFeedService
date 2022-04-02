package io.github.nationalaudience.thetribunal.controller;

import io.github.nationalaudience.thetribunal.FeedResponse;
import io.github.nationalaudience.thetribunal.entity.Review;
import io.github.nationalaudience.thetribunal.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.nationalaudience.thetribunal.entity.User;
import io.github.nationalaudience.thetribunal.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FeedController {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public FeedController(UserRepository userRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping(value = "feed")
    public ResponseEntity<List<FeedResponse>> generateFeed(@RequestBody String loggedUser) {
        // Get logged user
        var user = userRepository.findByUsername(loggedUser);

        if (user.isPresent()) {
            List<User> usersFollow = user.get().getUsersFollow();
            List<FeedResponse> reviewsForFeed = new ArrayList<>();

            List<Review> reviewList;

            for (User u : usersFollow) {
                reviewList = (List<Review>) reviewRepository.findReviewsByUserOrderByDateDesc(u);
                for (Review r : reviewList) {
                    reviewsForFeed.add(new FeedResponse(u.getUsername(), r.getGame().getName(), r.getComment(), r.getScore(), r.getDate()));
                }
            }

            return new ResponseEntity<>(reviewsForFeed, HttpStatus.OK);
        }
        return null;
    }
}
