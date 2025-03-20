package com.koce017.vbulutin;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import com.koce017.vbulutin.entity.Board;
import com.koce017.vbulutin.entity.Category;
import com.koce017.vbulutin.entity.Forum;
import com.koce017.vbulutin.entity.User;
import com.koce017.vbulutin.repository.BoardRepository;
import com.koce017.vbulutin.repository.CategoryRepository;
import com.koce017.vbulutin.repository.ForumRepository;
import com.koce017.vbulutin.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VBulutinApplication {

	public static void main(String[] args) {
		SpringApplication.run(VBulutinApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(
			Slugify slugify,
			UserRepository userRepository,
			BoardRepository boardRepository,
			CategoryRepository categoryRepository,
			ForumRepository forumRepository
	) {
		return args -> {
			Faker faker = Faker.instance();

			for (long i = 1; i <= 5; ++i) {
				User user = new User();
				user.setUsername(faker.name().username());
				user.setPassword(faker.animal().name());
				user.setEmail(user.getUsername() + "@mail.com");
				userRepository.save(user);

				Board board = new Board();
				board.setTitle(faker.name().title());
				board.setSlug(slugify.slugify(board.getTitle()) + "." + i);
				board.setOwner(user);
				boardRepository.save(board);

				for (long j = 1; j <= 3; ++j) {
					Category category = new Category();
					category.setTitle(faker.book().title());
					category.setSlug(slugify.slugify(category.getTitle()) + "." + j);
					category.setBoard(board);
					category.setPosition(j);
					categoryRepository.save(category);

					for (long k = 1; k <= 2; ++k) {
						Forum forum = new Forum();
						forum.setTitle(faker.name().title());
						forum.setSlug(slugify.slugify(forum.getTitle()) + "." + k);
						forum.setCategory(category);
						forum.setPosition(k);
						forumRepository.save(forum);
						category.getForums().add(forum);
					}
				}
			}
		};
	}

}
