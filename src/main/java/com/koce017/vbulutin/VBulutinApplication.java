package com.koce017.vbulutin;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import com.koce017.vbulutin.data.entity.*;
import com.koce017.vbulutin.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class VBulutinApplication {

	public static void main(String[] args) {
		SpringApplication.run(VBulutinApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(
			UserRepository userRepository,
			PostRepository postRepository,
			BoardRepository boardRepository,
			ForumRepository forumRepository,
			TopicRepository topicRepository,
			CategoryRepository categoryRepository
	) {
		Faker faker = new Faker();
		Random random = new Random();
		Slugify slugify = Slugify.builder().lowerCase(true).build();

		return args -> {

			for (long i = 1; i <= 10; ++i) {
				User user = User.builder()
						.username(faker.name().username())
						.password(faker.animal().name())
						.email(faker.internet().emailAddress())
						.build();

				if (random.nextBoolean())
					user.setSignature(faker.lorem().paragraph());

				userRepository.save(user);
			}

			List<User> users = userRepository.findAll();

			for (long i = 1; i <= 2; ++i) {
				String title = fakeTitle();
				Board board = Board.builder()
						.title(title)
						.slug(slugify.slugify(title))
						.description(faker.lorem().paragraph())
						.isHidden(random.nextBoolean())
						.owner(users.get(random.nextInt(users.size())))
						.build();
				boardRepository.save(board);
			}

			for (Board board : boardRepository.findAll()) {

				for (long i = 1; i <= 3; ++i) {
					String title = fakeTitle();
					Category category = Category.builder()
							.title(title)
							.slug(slugify.slugify(title))
							.description(faker.lorem().paragraph())
							.board(board)
							.position(i)
							.build();
					categoryRepository.save(category);
				}

			}

			for (Category category : categoryRepository.findAll()) {

				for (long i = 1; i < 3; ++i) {

					String title = fakeTitle();
					Forum forum = Forum.builder()
							.title(title)
							.slug(slugify.slugify(title))
							.description(faker.lorem().paragraph())
							.isLocked(random.nextBoolean())
							.category(category)
							.position(i)
							.build();
					forumRepository.save(forum);

					if (random.nextBoolean()) {

						for (long j = 1; j <= random.nextInt(1, 3); ++j) {
							title = fakeTitle();
							Forum subforum = Forum.builder()
									.title(title)
									.slug(slugify.slugify(title))
									.description(faker.lorem().paragraph())
									.isLocked(random.nextBoolean())
									.category(category)
									.parent(forum)
									.position(j)
									.build();
							forumRepository.save(subforum);
						}

					}

				}
			}

			for (Forum forum : forumRepository.findAll()) {

				for (long i = 1; i <= random.nextInt(6); ++i) {
					String title = fakeTitle();
					Topic topic = Topic.builder()
							.title(title)
							.slug(slugify.slugify(title))
							.isLocked(random.nextBoolean())
							.forum(forum)
							.build();
					topicRepository.save(topic);
				}

			}

			for (Topic topic : topicRepository.findAll()) {

				for (long i = 1; i <= random.nextInt(1, 16); ++i) {
					Post post = Post.builder()
							.topic(topic)
							.content(faker.lorem().paragraph())
							.poster(users.get(random.nextInt(users.size())))
							.createdAt(LocalDateTime.now().plusMinutes(i))
							.build();
					postRepository.save(post);
				}
			}

		};
	}
	
	private String fakeTitle() {
		return Faker.instance().lorem().sentence().replace(".", "");
	}

}
