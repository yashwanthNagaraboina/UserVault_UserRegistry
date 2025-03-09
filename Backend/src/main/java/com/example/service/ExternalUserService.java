package com.example.service;

import com.example.model.User;
import com.example.model.UserListResponse;
import com.example.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalUserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExternalUserService.class);

    @Autowired
    public ExternalUserService(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    public void loadUsersToDatabase() {
        String url = "https://dummyjson.com/users";
        try {
            // Fetch the response containing the list of users
            UserListResponse response = restTemplate.getForObject(url, UserListResponse.class);

            if (response != null && response.getUsers() != null) {
                User[] users = response.getUsers().toArray(new User[0]);

                logger.info("Fetched {} users from external API.", users.length);

                // Save each user to the database
                for (User user : users) {
                    userRepository.save(user);
                }

                logger.info("Users loaded successfully to the database.");
            } else {
                logger.error("No users were fetched from the external API.");
            }
        } catch (Exception e) {
            logger.error("Error while loading users from external API: ", e);
        }
    }
}
