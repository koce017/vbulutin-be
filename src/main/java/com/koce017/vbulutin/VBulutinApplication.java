package com.koce017.vbulutin;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import com.koce017.vbulutin.data.entity.*;
import com.koce017.vbulutin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@Slf4j
@SpringBootApplication
public class VBulutinApplication {

	public static void main(String[] args) {
		SpringApplication.run(VBulutinApplication.class, args);
	}

	private final Random random = new Random();
	private final Faker faker = Faker.instance();
	private final Slugify slugify = Slugify.builder().lowerCase(true).build();

	@Bean
	public CommandLineRunner runner(
			UserRepository userRepository,
			BoardRepository boardRepository,
			CategoryRepository categoryRepository,
			ForumRepository forumRepository,
			TopicRepository topicRepository,
			PostRepository postRepository
	) {
		return args -> {
			for (long i = 1; i <= 5; ++i) {
				User owner = User.builder()
						.username(faker.name().username())
						.password(faker.animal().name())
						.email(faker.internet().emailAddress())
						.build();
				userRepository.save(owner);
			}

			List<User> users = userRepository.findAll();

			for (long i = 1; i <= 2; ++i) {
				String title = sentence();
				Board board = Board.builder()
						.title(title)
						.slug(slugify.slugify(title))
						.description(faker.lorem().paragraph())
						.isVisible(random.nextBoolean())
						.owner(users.get(random.nextInt(users.size())))
						.build();
				boardRepository.save(board);
			}

			for (Board board : boardRepository.findAll()) {

				for (long i = 1; i <= 3; ++i) {
					String title = sentence();
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

					String title = sentence();
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
							title = sentence();
							Forum subforum = Forum.builder()
									.title(title)
									.slug(slugify.slugify(title))
									.description(faker.lorem().paragraph())
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

				for (long i = 1; i <= 3; ++i) {
					String title = sentence();
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

				for (long i = 1; i <= 3; ++i) {

					Post post = Post.builder()
							.topic(topic)
							.content(faker.lorem().paragraph())
							.user(users.get(random.nextInt(users.size())))
							.build();
					postRepository.save(post);

					if (random.nextBoolean() && topic.getSolution() == null) {
						topic.setSolution(post);
						topicRepository.save(topic);
					}
				}
			}

		};
	}
	
	private String sentence() {
		return faker.lorem().sentence().replace(".", "");
	}

}
