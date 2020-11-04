package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles({"jpa", "postgres"})
public class JpaUserServiceTest extends AbstractUserServiceTest {
}
