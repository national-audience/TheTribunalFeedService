package io.github.nationalaudience.thetribunal.repository;

import io.github.nationalaudience.thetribunal.entity.Game;
import io.github.nationalaudience.thetribunal.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.nationalaudience.thetribunal.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
        Optional<Review> findByUserAndGame(User user, Game game);

        Collection<? extends Review> findReviewsByUserOrderByDateDesc(User user);
}
