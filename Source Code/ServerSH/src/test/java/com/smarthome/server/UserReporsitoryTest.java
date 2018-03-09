package com.smarthome.server;

import com.smarthome.server.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.smarthome.server.repositories.UserRepository;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserReporsitoryTest {
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        User user1 = new User("Nicusor","Turcu","sbear","1234",21);
        User user2 = new User("Ionut", "Turcu", "ion", "1234", 26);

        assertNull(user1.getId());
        assertNull(user2.getId());
        this.userRepository.save(user1);
        this.userRepository.save(user2);
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
    }

    @Test
    public void testFetchData(){
        User userA = userRepository.findByFirstName("Ionut");
        assertNotNull(userA);

        Iterable<User> users = userRepository.findAll();
        int count = 0;
        for(User p : users){
            count ++;
        }
        assertEquals(count,2);
    }
}
