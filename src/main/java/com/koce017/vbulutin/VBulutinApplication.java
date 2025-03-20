package com.koce017.vbulutin;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import com.koce017.vbulutin.data.entity.Board;
import com.koce017.vbulutin.data.entity.Category;
import com.koce017.vbulutin.data.entity.Forum;
import com.koce017.vbulutin.data.entity.User;
import com.koce017.vbulutin.repository.BoardRepository;
import com.koce017.vbulutin.repository.CategoryRepository;
import com.koce017.vbulutin.repository.ForumRepository;
import com.koce017.vbulutin.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class VBulutinApplication {

	public static void main(String[] args) {
		SpringApplication.run(VBulutinApplication.class, args);
	}

	private final Random random = new Random();

	@Bean
	public CommandLineRunner runner(
			UserRepository userRepository,
			BoardRepository boardRepository,
			CategoryRepository categoryRepository,
			ForumRepository forumRepository
	) {
		return args -> {
			Faker faker = Faker.instance();
			Slugify slugify = Slugify.builder().lowerCase(true).build();

			for (long i = 1; i <= 5; ++i) {
				User user = new User();
				user.setUsername(faker.name().username());
				user.setPassword(faker.animal().name());
				user.setEmail(faker.internet().emailAddress());
				userRepository.save(user);

				Board board = new Board();
				board.setTitle(faker.book().title());
				board.setSlug(slugify.slugify(board.getTitle()) + "." + System.currentTimeMillis());
				board.setOwner(user);
				board.setVisible(random.nextBoolean());
				boardRepository.save(board);

				for (long j = 1; j <= 2; ++j) {
					Category category = new Category();
					category.setTitle(faker.book().title());
					category.setSlug(slugify.slugify(category.getTitle()) + "." + System.currentTimeMillis());
					category.setBoard(board);
					category.setPosition(j);
					categoryRepository.save(category);

					for (long k = 1; k <= 2; ++k) {
						Forum forum = new Forum();
						forum.setTitle(faker.book().title());
						forum.setSlug(slugify.slugify(forum.getTitle()) + "." + System.currentTimeMillis());
						forum.setCategory(category);
						forum.setPosition(k);
						forumRepository.save(forum);

						if (k % 2 == 0) {
							Forum subforum1 = new Forum();
							subforum1.setTitle(faker.book().title());
							subforum1.setSlug(slugify.slugify(subforum1.getTitle()) + "." + System.currentTimeMillis());
							subforum1.setCategory(category);
							subforum1.setPosition(1L);
							subforum1.setParent(forum);
							forumRepository.save(subforum1);

							Forum subforum2 = new Forum();
							subforum2.setTitle(faker.book().title());
							subforum2.setSlug(slugify.slugify(subforum2.getTitle()) + "." + System.currentTimeMillis());
							subforum2.setCategory(category);
							subforum2.setPosition(2L);
							subforum2.setParent(forum);
							forumRepository.save(subforum2);
						}

					}

				}
			}
		};
	}

}
