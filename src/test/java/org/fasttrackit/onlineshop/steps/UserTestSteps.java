package org.fasttrackit.onlineshop.steps;

import org.fasttrackit.onlineshop.domain.User;
import org.fasttrackit.onlineshop.domain.UserRole;
import org.fasttrackit.onlineshop.service.UserService;
import org.fasttrackit.onlineshop.transfer.user.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
@Component
public class UserTestSteps {
    @Autowired
    private UserService userService;

    public User createUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setRole(UserRole.CUSTOMER);
        request.setFirstName("Test First Name");
        request.setLastName("Test Last Name");

        User user = userService.createUser(request);

        assertThat(user, notNullValue());
        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getRole(), is(request.getRole()));
        assertThat(user.getFirstName(), is(request.getFirstName()));
        assertThat(user.getLastName(), is(request.getLastName()));

        return user;
    }
}
