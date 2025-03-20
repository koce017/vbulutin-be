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
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@Slf4j
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

			for (long i = 1; i <= 2; ++i) {
				User owner = User.builder()
						.username(faker.name().username())
						.password(faker.animal().name())
						.email(faker.internet().emailAddress())
						.build();
				userRepository.save(owner);

				Board board = Board.builder()
						.owner(owner)
						.title(faker.book().title())
						.isVisible(random.nextBoolean())
						.build();
				board.setSlug(slugify.slugify(board.getTitle()) + "." + System.currentTimeMillis());
				boardRepository.save(board);

				for (long j = 1; j <= 3; ++j) {
					Category category = Category.builder()
							.position(j)
							.title(faker.book().title())
							.board(board)
							.build();
					category.setSlug(slugify.slugify(category.getTitle()) + "." + System.currentTimeMillis());
					categoryRepository.save(category);

					for (long k = 1; k <= 5; ++k) {
						Forum forum = Forum.builder()
								.title(faker.book().title())
								.category(category)
								.position(k)
								.build();
						forum.setSlug(slugify.slugify(forum.getTitle()) + "." + System.currentTimeMillis());
						forumRepository.save(forum);

						Forum subforum = Forum.builder()
								.title(faker.book().title())
								.category(category)
								.position(1L)
								.build();
						subforum.setSlug(slugify.slugify(subforum.getTitle()) + "." + System.currentTimeMillis());
						forumRepository.save(subforum);
					}

				}
			}
		};
	}

}
