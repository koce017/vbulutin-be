package com.koce017.vbulutin.controller;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import com.koce017.vbulutin.data.entity.*;
import com.koce017.vbulutin.repository.*;
import com.koce017.vbulutin.util.SlugifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/fake")
@RequiredArgsConstructor
public class FakeDataController {

    private final Slugify slugify;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final ForumRepository forumRepository;
    private final TopicRepository topicRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public void populate() {
        Faker faker = new Faker();
        Random random = new Random();

        for (long i = 1; i <= 10; ++i) {
            String username = faker.name().username();

            User user = User.builder()
                    .username(username)
                    .email(username + "@mail.com")
                    .password(passwordEncoder.encode("password"))
                    .build();

            if (random.nextBoolean())
                user.setSignature(faker.lorem().paragraph());

            userRepository.save(user);
        }

        List<User> users = userRepository.findAll();

        for (long i = 1; i <= 5; ++i) {
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
                        .slug(SlugifyUtil.slugify(title))
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
                        .slug(SlugifyUtil.slugify(title))
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
                                .slug(SlugifyUtil.slugify(title))
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
                        .slug(SlugifyUtil.slugify(title))
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
                        .createdAt(LocalDateTime.now().plusMinutes(1))
                        .build();
                postRepository.save(post);
            }
        }
    }

    private String fakeTitle() {
        return Faker.instance().lorem().sentence().replace(".", "");
    }

}
